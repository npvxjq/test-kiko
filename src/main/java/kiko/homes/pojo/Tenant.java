package kiko.homes.pojo;

import kiko.homes.Config;

public class Tenant {
    private static final IDao dao = Config.dao;
    private int id;
    private String name;

    Tenant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tenant(String name) throws DaoException {
        this.id = 0;
        this.name = name;
        dao.addTenant(this);
    }

    public Viewing requestViewing(Flat f, long s) throws KikoLogicException, DaoException {
        return new Viewing(f, this, s);
    }

    public void cancelRequestViewing(Viewing v) throws KikoLogicException, DaoException {
        if (v.getTenant().equals(this) ) {
            v.getOptCurrentTenant().ifPresent(t -> t.addMessageNotifyViewingCancel(v.getFlat(), v.getTimeSlot()));
            dao.deleteViewing(v);
        } else {
            throw new KikoLogicException("This viewing is not requested by this tenant");
        }
    }

    public void acceptViewing(Viewing v, boolean accept) {
        if (this.equals(v.getOptCurrentTenant().orElse(null)) &&
                v.getViewingState()==ViewingState.REQUESTED) {
            v.setAccepted(accept);

        } else {
            throw new KikoLogicException("Forbidden");
        }
    }


    public void addMessage(String msg) {
        KLogger.info("Message to: " + this + " msg:" + msg);
    }

    public void addMessageNotifyViewing(Flat flat, long timeSlot) {
        addMessage("Viewing notification, " + flat + " time:" + TimeSlot.asString(timeSlot));
    }

    public void addMessageNotifyViewingCancel(Flat flat, long timeSlot) {
        addMessage("Viewing cancel notification, " + flat + " time:" + TimeSlot.asString(timeSlot));
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Tenant " + getId() + " " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj!=null && obj.getClass().isAssignableFrom(Tenant.class)) {
            return ((Tenant) obj).getId() == getId();
        }
        return false;
    }
}

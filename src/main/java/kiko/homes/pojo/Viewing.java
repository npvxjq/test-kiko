package kiko.homes.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kiko.homes.Config;

import java.time.LocalDateTime;
import java.util.Optional;

public class Viewing {
    private long id;
    private Flat flat;
    private long timeSlot;
    private Tenant tenant;
    private Optional<Tenant> pcurrentTenant;
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime reservedAt;
    private ViewingState viewingState;
    private static IDao dao = Config.dao;


    public Viewing(Flat flat, Tenant tenant, long timeSlot) throws KikoLogicException, DaoException {
        if (!dao.isViewingPossible(flat, tenant, timeSlot)) {
            throw new KikoLogicException("Time slot not available");
        }
        pcurrentTenant = dao.findOccupation(flat, timeSlot).flatMap(
                occupation -> {
                    return Optional.of(occupation.getTenant());
                }
        );
        this.flat = flat;
        this.timeSlot = timeSlot;
        this.tenant = tenant;
        this.reservedAt = LocalDateTime.now();
        viewingState = ViewingState.REQUESTED;
        pcurrentTenant.ifPresent(ct -> {
            ct.addMessageNotifyViewing(flat, timeSlot);
        }); //TODO: Whom to notify, if there is no Current Tenant??
        dao.addViewing(this);
    }

    @JsonIgnore
    public Optional<Tenant> getOptCurrentTenant() {
        return pcurrentTenant;
    }

    public long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    public Tenant getCurrentTenant() {
        return pcurrentTenant.orElse(null);
    }

    public Flat getFlat() {
        return flat;
    }

    public long getTimeSlot() {
        return timeSlot;
    }

    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    public LocalDateTime getTime() {
        return TimeSlot.getDateTime(timeSlot);
    }

    public Tenant getTenant() {
        return tenant;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setRejected() {
        setAccepted(false);
    }

    void setAccepted(boolean accept) {
        String msg;
        if (accept) {
            viewingState = ViewingState.ACCEPTED;
            msg = "accepted";
        } else {
            viewingState = ViewingState.REJECTED;
            msg = "rejected";
        }
        tenant.addMessage( "Viewing request " + msg + " flat:" + flat + " time:" + TimeSlot.asString(timeSlot));
    }

    @JsonIgnore
    public boolean isRejected() {
        return viewingState == ViewingState.REJECTED;
    }

    @JsonIgnore
    public boolean isAccepted() {
        return viewingState == ViewingState.ACCEPTED;
    }

    public ViewingState getViewingState() {
        return viewingState;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(Viewing.class)) {
            return ((Viewing) obj).getId() == getId();
        }
        return false;
    }
}

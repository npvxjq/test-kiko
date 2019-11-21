package kiko.homes.pojo;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kiko.homes.Config;

import java.time.LocalDateTime;

public class Occupation {
    private static final IDao dao= Config.dao;
    private Tenant tenant;
    private Flat flat;
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime start;
    @JsonSerialize(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime end;

    Occupation(Tenant tenant, Flat flat, LocalDateTime start, LocalDateTime end) throws KikoLogicException, DaoException {
        if(start.isAfter(end)) {
            throw new KikoLogicException("invalid period");
        }
        if (dao.isOccupied(flat, TimeSlot.getOffset(start), TimeSlot.getOffset(end))) {
            throw new KikoLogicException("flat occupied in this period");
        }
        this.tenant = tenant;
        this.flat = flat;
        this.start = start;
        this.end = end;
        dao.addOccupation(this);
    }

    public Occupation(Tenant tenant, Flat flat, long start, long end) throws KikoLogicException, DaoException {
        this(tenant,flat,TimeSlot.getDateTime(start),TimeSlot.getDateTime(end));
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Flat getFlat() {
        return flat;
    }


    public long getStartSlot(){ return  TimeSlot.getOffset(start); }
    public long getEndSlot(){
        return TimeSlot.getOffset(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(Occupation.class)) {
            Occupation o=(Occupation) obj;
            return o.getFlat().equals(flat) &&
                    o.getStartSlot() == getStartSlot() &&
                    o.getEndSlot() == getEndSlot() &&
                    o.getTenant().equals(getTenant());
        }
        return false;
    }
}

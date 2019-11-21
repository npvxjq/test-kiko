package kiko.homes.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * IDao implementation for testing
 * NOT synchronized
 */
public class Dao implements IDao {
    private final ArrayList<Occupation> occupations;
    private final ArrayList<Tenant> tenants ;
    private final ArrayList<Flat> flats ;
    private final ArrayList<Viewing> viewings ;

    public Dao() {
        occupations = new ArrayList<>();
        tenants = new ArrayList<>();
        flats = new ArrayList<>();
        viewings = new ArrayList<>();
    }

    @Override
    public  Stream<Occupation> getOccupations()throws DaoException {
        return occupations.stream();
    }

    @Override
    public  Stream<Occupation> getOccupations(Flat f) throws DaoException{
        return occupations.stream().filter((o) -> {
            return o.getFlat().equals(f);
        });
    }

    @Override
    public  Optional<Occupation> findOccupation(Flat f, long timeSlot) throws DaoException{
        return
                getOccupations(f).filter((o) -> {
                    return timeSlot >= o.getStartSlot() && timeSlot <= o.getEndSlot();
                }).findAny();
    }

    @Override
    public boolean isOccupied(Flat f, long start, long end) throws  DaoException{

        return
                !getOccupations(f).allMatch((o) -> {
                    return end < o.getStartSlot() || start > o.getEndSlot();
                });
    }

    @Override
    public void addOccupation(Occupation o) throws  DaoException{
            occupations.add(o);
    }


    @Override
    public Stream<Tenant> getTenants()throws DaoException {
        return tenants.stream();
    }

    @Override
    public Optional<Tenant> getTenant(int id) throws DaoException{
        return getTenants().filter((t) -> {
            return t.getId() == id;
        }).findAny();
    }

    @Override
    public void addTenant(Tenant t) throws DaoException {
        if(t.getName().length()>60) {
            throw new DaoException("Name is too long");
        }
        int i = getTenants().map(Tenant::getId).max(Comparator.naturalOrder()).orElse(0);
        t.setId(i+1);
        tenants.add(t);

    }

    @Override
    public Stream<Flat> getFlats()throws DaoException {
        return flats.stream();
    }
    @Override
    public Optional<Flat> getFlat(int id)throws DaoException {
        return flats.stream().filter((t) -> {
            return t.getId() == id;
        }).findAny();
    }

    @Override
    public void addFlat(Flat flat) throws DaoException {
        if (getFlats().anyMatch(f -> {
            return f.getAddress().equals(flat.getAddress());
                })) {
            throw new DaoException("This address already present");
        }
        int i = getFlats().map(Flat::getId).max(Comparator.naturalOrder()).orElse(0);
        flat.setId(i+1);
        flats.add(flat);
    }


    @Override
    public  Stream<Viewing> getViewings() throws DaoException {
        return viewings.stream();
    }

    @Override
    public  Stream<Viewing> getViewings(Flat f) throws DaoException {
        return viewings.stream().filter((v) -> {
            return v.getFlat().equals(f);
        });
    }
    @Override
    public  Stream<Viewing> getViewings(Tenant t) throws DaoException {
        return viewings.stream().filter((v) -> {
            return t.equals(v.getCurrentTenant());
        });
    }
    @Override
    public  Optional<Viewing> getViewing(long id) throws DaoException {
        return viewings.stream().filter((v) -> {
            return v.getId()==id;
        }).findAny();
    }
    @Override
    public  void addViewing(Viewing view) throws DaoException {
        long i = getViewings().map(Viewing::getId).max(Comparator.naturalOrder()).orElse(0L);
        view.setId(i+1);
        viewings.add(view);
    }

    @Override
    public  boolean isViewingPossible(Flat flat, Tenant tenant, long timeSlot) throws DaoException {
        return getViewings(flat).noneMatch((v) -> {
            return  v.getTimeSlot() == timeSlot ;
        });
    }
    @Override
    public  void deleteViewing(Viewing v) throws DaoException{
        viewings.remove(v);
    }


}

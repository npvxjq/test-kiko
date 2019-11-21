package kiko.homes.pojo;

import java.util.Optional;
import java.util.stream.Stream;

public interface IDao {
    Stream<Occupation> getOccupations() throws DaoException;

    Stream<Occupation> getOccupations(Flat f) throws DaoException;

    Optional<Occupation> findOccupation(Flat f, long timeSlot) throws DaoException;

    boolean isOccupied(Flat f, long start, long end) throws  DaoException;

    void addOccupation(Occupation o) throws  DaoException;

    Stream<Tenant> getTenants()throws DaoException;

    Optional<Tenant> getTenant(int id) throws DaoException;

    void addTenant(Tenant t) throws DaoException;

    Stream<Flat> getFlats()throws DaoException;

    Optional<Flat> getFlat(int id)throws DaoException;

    void addFlat(Flat flat) throws DaoException;

    Stream<Viewing> getViewings() throws DaoException;

    Stream<Viewing> getViewings(Flat f) throws DaoException;

    Stream<Viewing> getViewings(Tenant t) throws DaoException;

    Optional<Viewing> getViewing(long id) throws DaoException;

    void addViewing(Viewing view) throws DaoException;

    boolean isViewingPossible(Flat flat, Tenant tenant, long timeSlot) throws DaoException;

    void deleteViewing(Viewing v) throws DaoException;
}

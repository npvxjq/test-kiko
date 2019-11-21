package kiko.homes.pojo;
import kiko.homes.*;

public class Flat {
    private static final IDao dao= Config.dao;
    private int id;
    private String address;


    Flat(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public Flat(String address) throws DaoException {
        this.id = 0;
        this.address = address;
        dao.addFlat(this);

    }


    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {

        return "Flat id="+id;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().isAssignableFrom(Flat.class)) {
            return ((Flat) obj).getId() == getId();
        }
        return false;
    }
}

package kiko.homes;

import kiko.homes.pojo.*;

public class Config {
    public static IDao dao;
    public final static String dateFormat = "MM/dd/yyyy 'at' hh:mm a";

    static void initConfig() {
        dao = new Dao();
        init_test_data();
    }

    /**initial data for testing
     *
     */
    private static  void init_test_data() {
        try {

            new Tenant("Vasya");
            new Tenant("John");
            new Tenant("Victor");
            new Flat("Lenina 1");
            new Flat("12 Downing Street");
            Tenant t = dao.getTenant(1).get();
            Tenant ot = dao.getTenant(2).get();
            Flat f = dao.getFlats().findFirst().get();
            new Occupation(ot, f, 1, 5);
            t.requestViewing(f,  4);
            t.requestViewing(f,  8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

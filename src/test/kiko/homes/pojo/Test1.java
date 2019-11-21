package kiko.homes.pojo;


import kiko.homes.Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Test1 {
    static IDao dao;


    @BeforeAll
    static void  setUp() throws KikoLogicException {
        System.out.println("before all");
        Config.dao = new Dao();
        dao=Config.dao;
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

    @Test
    void occupations() throws KikoLogicException, DaoException {
        Tenant ot=dao.getTenant(2).get();
        Flat f=dao.getFlat(2).get();

        assertTrue(dao.getOccupations().count()==1);
        new Occupation(ot,f,9,90 );
        assertTrue(dao.getOccupations().count()==2);
        assertTrue(dao.isOccupied(f,56,56));
        Optional<Occupation> oo=dao.findOccupation(f,56);
        assertTrue(oo.isPresent());
        assertNotNull(oo.get().getTenant());
        assertThrows(KikoLogicException.class,
                ()->{
                    new Occupation(ot,f,4,17 );
                });


        dao.getOccupations().forEach(oc->System.out.println(oc));

        assertTrue(dao.getOccupations().count()==2);
    }
    @Test
    void view() throws DaoException, KikoLogicException {
        Flat f=dao.getFlat(2).get();
        new Occupation(dao.getTenant(2).get(),f,9,90 );
        Viewing v=dao.getTenant(1).get().requestViewing(f,56);
        assertThrows(KikoLogicException.class,()->{
            dao.getTenant(3).get().requestViewing(f,56);
        });

        v.getOptCurrentTenant().ifPresent(t->{t.acceptViewing(v,true);});
        assertThrows(KikoLogicException.class,()->{
            v.getOptCurrentTenant().ifPresent(t->{t.acceptViewing(v,true);});
        });
        assertThrows(KikoLogicException.class,()->{
            dao.getTenant(3).get().requestViewing(f,56);
        });
        Viewing v1=dao.getTenant(1).get().requestViewing(f,57);
        assertThrows(KikoLogicException.class,()->{
                    dao.getTenant(3).get().requestViewing(f,57);
                });

        v.getOptCurrentTenant().ifPresent(t->{t.acceptViewing(v,false);});
        assertThrows(KikoLogicException.class,()->{
            dao.getTenant(3).get().requestViewing(f,57);
        });
    }


    @Test
    void getTenants() throws DaoException {
        dao.getTenants().forEach(t->System.out.println(t));
        assertTrue(dao.getTenants().count()==3);

    }
    @Test
    void flat_same() throws DaoException {
        dao.getFlats().forEach(t->System.out.println(t));
        long i=dao.getFlats().count();
        assertThrows(DaoException.class,
                ()->{ new Flat("Lenina 1");});

        assertTrue(dao.getFlats().count()==i);

    }

    @Test
    void tenant_long() throws DaoException {
        long i=dao.getTenants().count();
        assertThrows(DaoException.class,()-> {
                    new Tenant("VictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictorVictor");
                });
        assertTrue(dao.getTenants().count()==i);

    }
}
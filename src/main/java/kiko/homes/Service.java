package kiko.homes;

import kiko.homes.pojo.*;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Stream;

@Path("/")
public class Service {

    private static IDao dao = Config.dao;

    @Context
    ServletContext context;

    private IDao getDao() {
        if (dao == null) {
            Config.initConfig();
            dao = Config.dao;
        }
        return dao;
    }


    @GET
    @Path("flats.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flats() {
        Stream<Flat> f = null;
        try {
            f = getDao().getFlats();
        } catch (Exception e) {
            Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity(f.toArray()).build();
    }

    @GET
    @Path("tenants.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tenants() {
        Stream<Tenant> f = null;
        try {
            f = getDao().getTenants();
        } catch (Exception e) {
            Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity(f.toArray()).build();

    }

    @GET
    @Path("occupations.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response occupations() {
        Stream<Occupation> f = null;
        try {
            f = getDao().getOccupations();
        } catch (Exception e) {
            Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity(f.toArray()).build();
    }

    @GET
    @Path("viewings.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewings() {
        Stream<Viewing> f = null;
        try {
            f = getDao().getViewings();
        } catch (Exception e) {
            Response.status(500).entity(e.getMessage()).build();
        }
        return Response.status(200).entity(f.toArray()).build();
    }

    @GET
    @Path("cmd")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cmd(
            @QueryParam("action") final String action,
            @QueryParam("flat") final int flat,
            @QueryParam("slot") final long slot,
            @QueryParam("tenant") final int tenant,
            @QueryParam("viewing") final long viewing
    ) {
        String result;
        if (action == null) {
            result = getErrorResponse("Parameters", "no action");
        } else {

            try {
                result = "{\"Result\": \"Ok\"}";
                switch (action) {
                    case "requestViewing":
                        getTenant(tenant).requestViewing(getDao().getFlat(flat).get(), slot);
                        break;
                    case "cancelRequestViewing":
                        getTenant(tenant).cancelRequestViewing(getViewing(viewing));
                        break;
                    case "acceptViewing":
                        getTenant(tenant).acceptViewing(getViewing(viewing), true);
                        break;
                    case "rejectViewing":
                        getTenant(tenant).acceptViewing(getViewing(viewing), false);
                        break;
                    default:
                        result = getErrorResponse("Parameters", "invalid action");
                }
            } catch (KikoLogicException e) {
                result = getErrorResponse("Logic", e.getMessage());
            } catch (DaoException e) {
                result = getErrorResponse("Dao", e.getMessage());
            } catch (Exception e) {
                result = getErrorResponse("Unhandled", e.getMessage());
            }
        }

        return Response.status(200).entity(result).build();
    }

    private String getErrorResponse(String kind, String msg) {
        JSONObject o = new JSONObject();
        o.put("Error", kind);
        o.put("Message", msg);
        return o.toString(2);

    }
    private Viewing getViewing(long id) {
        Optional<Viewing> ov=getDao().getViewing(id);
        if(ov.isPresent()) {
            return ov.get();
        } else {
            throw new KikoLogicException("no such viewing");
        }
    }
    private Tenant getTenant(int id) {
        Optional<Tenant> ov=getDao().getTenant(id);
        if(ov.isPresent()) {
            return ov.get();
        } else {
            throw new KikoLogicException("no such Tenant");
        }
    }
    private Flat getFlat(int id) {
        Optional<Flat> ov=getDao().getFlat(id);
        if(ov.isPresent()) {
            return ov.get();
        } else {
            throw new KikoLogicException("no such Flat");
        }
    }
}

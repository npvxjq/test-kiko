package kiko.homes;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext ctx = sce.getServletContext();
        Config.initConfig();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {


    }
}

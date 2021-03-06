package com.nik2;

import com.nik2.dao.AccountDAO;
import com.nik2.service.TrafficFortuneService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class AroundWithLoggerDemoApp {

    private static Logger myLogger =
            Logger.getLogger(AroundWithLoggerDemoApp.class.getName());

    public static void main(String[] args) {

        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        // get the bean from spring container
        AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);

        //get bean from container
        TrafficFortuneService trafficFortuneService =
                context.getBean("trafficFortuneService", TrafficFortuneService.class);


        myLogger.info("\nMain : AroundApp");

        myLogger.info("Calling fortune..");

        String data = trafficFortuneService.getFortune();

        myLogger.info("\nMy fortune is : " + data);

        myLogger.info("..finished");

        // close the context
        context.close();
    }

}


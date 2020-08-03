package com.nik2;

import com.nik2.dao.AccountDAO;
import com.nik2.service.TrafficFortuneService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AroundDemoApp {

    public static void main(String[] args) {

        // read spring config java class
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoConfig.class);

        // get the bean from spring container
        AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);

        //get bean from container
        TrafficFortuneService trafficFortuneService =
                context.getBean("trafficFortuneService", TrafficFortuneService.class);


        System.out.println("\nMain : AroundApp");

        System.out.println("Calling fortune..");

        String data = trafficFortuneService.getFortune();

        System.out.println("\nMy fortune is : " + data);

        System.out.println("..finished");

        // close the context
        context.close();
    }

}


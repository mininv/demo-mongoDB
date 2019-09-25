package com.yarm.vlad.demomongoDB;


import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

@SpringBootApplication
public class DemoMongoDbApplication {
    @Autowired
    PhoneService phoneService;


    public static void main(String[] args) {
        SpringApplication.run(DemoMongoDbApplication.class, args);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        return Persistence.createEntityManagerFactory("ogm-mongodb");
    }

    @Bean
    public TransactionManager transactionManager() {
        return com.arjuna.ats.jta.TransactionManager.transactionManager();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Telephone telephone = new Telephone();
            telephone.setPhoneName("HUAWEI");
            telephone.setPhoneDescription("p30 pro");
            telephone.getParameters().put("разрешение экрана", "2340x1080");
            telephone.getParameters().put("аккумулятор", "4000 мАч");
            phoneService.save(telephone);

            Telephone telephone2 = new Telephone();
            telephone2.setPhoneName("Samsung");
            telephone2.setPhoneDescription("a30");
            telephone2.getParameters().put("разрешение экрана", "3340x2080");
            telephone2.getParameters().put("аккумулятор", "3500 мАч");
            phoneService.save(telephone2);
        };
    }

}

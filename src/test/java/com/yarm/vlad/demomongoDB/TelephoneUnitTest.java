package com.yarm.vlad.demomongoDB;
import com.yarm.vlad.demomongoDB.domain.Parameter;
import com.yarm.vlad.demomongoDB.domain.Telephone;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import static org.junit.Assert.assertTrue;

@Ignore
public class TelephoneUnitTest {

    private String testDescription = "описания";
    private String  testName = "имя телефона";

    @Test
    public void given_WhenEntitiesCreated_thenCanBeRetrieved() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ogm-mongodb");
        Telephone telephone = generateTestData();
        persistTestData(entityManagerFactory, telephone);
        loadAndVerifyTestData(entityManagerFactory, telephone);
    }

    private void persistTestData(EntityManagerFactory entityManagerFactory, Telephone telephone) throws Exception {
        TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
        EntityManager entityManager;

        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(telephone);
        entityManager.close();
        transactionManager.commit();
    }

    private void loadAndVerifyTestData(EntityManagerFactory entityManagerFactory, Telephone telephone) throws Exception {
        TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
        EntityManager entityManager;

        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        Telephone loadedTelephone = entityManager.find(Telephone.class, telephone.getPhoneId());
        assertTrue(loadedTelephone!=null);
        assertTrue(loadedTelephone.getPhoneDescription().equals(testDescription));
        assertTrue(loadedTelephone.getPhoneName().equals(testName));

        System.out.println(telephone.getParameters());
        entityManager.close();
        transactionManager.commit();
    }

    private Telephone generateTestData() {
        Telephone telephone = new Telephone();
        telephone.setPhoneDescription(testDescription);
        telephone.setPhoneName(testName);
        telephone.getParameters().put("тест", "тест2");
        return telephone;
    }
}

package com.yarm.vlad.demomongoDB.service.impl;

import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Telephone telephone) throws Exception {
        transactionManager.begin();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(telephone);
        entityManager.close();
        transactionManager.commit();
    }

    @Override
    public Telephone getByPhoneId(String id) {
        return entityManagerFactory.createEntityManager().find(Telephone.class, id);
    }

    @Override
    public List<Telephone> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        String query = "db.Telephone.find({})";
        List<Telephone> telephones = em.createNativeQuery(query, Telephone.class).getResultList();
        return telephones;
    }

    @Override
    public List<Telephone> findByPhoneName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        //  db.getCollection('Telephone').find({ 'phoneName' : 'adas' })
//db.getCollection('Telephone').find({ "parameters.Разрешение экрана": {$exists : true} })
        String query2 = "db.Telephone.find( { phoneName : '" + name + "' })";
        List<Telephone> telephones = em.createNativeQuery(query2, Telephone.class).getResultList();
        return telephones;
    }

    @Override
    public List<Telephone> findByParameterAndValue(String parameter, String value) {
        List<Telephone> results = entityManagerFactory.createEntityManager()
                .createNativeQuery("{ \"parameters." + parameter + "\": {$exists : true} }", Telephone.class)
                .getResultList();
        List<Telephone> telephoneList = results.stream().
                filter(telephone -> telephone.getParameters().containsValue(value)).collect(Collectors.toList());
        return telephoneList;
    }


}

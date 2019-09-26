package com.yarm.vlad.demomongoDB.service.impl;

import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Telephone telephone) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            transactionManager.begin();
            entityManager.persist(telephone);
            entityManager.close();
            transactionManager.commit();
        }catch (Exception ex){
            try {
                if(transactionManager.getStatus() == Status.STATUS_ACTIVE||
                transactionManager.getStatus()==Status.STATUS_MARKED_ROLLBACK){
                    transactionManager.rollback();
                }
            } catch (SystemException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(ex);
        }finally {
            if(entityManager != null && entityManager.isOpen()){
                entityManager.close();
            }
        }

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

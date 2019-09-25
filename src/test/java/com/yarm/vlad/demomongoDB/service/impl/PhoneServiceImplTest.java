package com.yarm.vlad.demomongoDB.service.impl;

import com.yarm.vlad.demomongoDB.BaseIntegrationTest;
import com.yarm.vlad.demomongoDB.domain.Parameter;
import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.exception.TelephoneManagementException;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PhoneServiceImplTest extends BaseIntegrationTest {

    Telephone telephone = new Telephone("имя", "описание");


    @Test
    public void save() {
        try {
            phoneService.save(telephone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Telephone byPhoneId = phoneService.getByPhoneId(telephone.getPhoneId());
        Assert.assertNotNull("The telephone retrieved from the DB should not be null", byPhoneId);
    }

    @Test
    public void getByPhoneId() {
        Telephone anyID = phoneService.getByPhoneId("anyID");
        Assert.assertNull("By this id should be null", anyID);
    }

    @Test
    public void findAll() {
        Telephone telephone1 = new Telephone("имя1", "описание1");
        try {
            phoneService.save(telephone1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Telephone telephoneFromDb = phoneService.findAll().stream().findFirst().orElse(null);
        Assert.assertNotNull("The telephone from method findAll should not be null", telephoneFromDb);
    }

    @Test
    public void findByTelephoneName() throws Exception {
        Telephone telephone = new Telephone("имя12", "описание2");
        phoneService.save(telephone);
        List<Telephone> byPhoneName = phoneService.findByPhoneName(telephone.getPhoneName());
        Assert.assertFalse("List of telephone from db cant be empty", byPhoneName.isEmpty());
    }

    @Test
    public void findByParameterAndValue(){
        Telephone telephone = new Telephone("samsung", "a30");
        String key = "количество ядер";

        String value = "2";
        telephone.getParameters().put(key, value);
        try {
            phoneService.save(telephone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Telephone> byParameterAndValue = phoneService.findByParameterAndValue(key, value);
        Assert.assertTrue(byParameterAndValue.size()==1);
    }
}
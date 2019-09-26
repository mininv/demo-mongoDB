package com.yarm.vlad.demomongoDB.service;

import com.yarm.vlad.demomongoDB.domain.Telephone;

import javax.transaction.*;
import java.util.List;
import java.util.Optional;

public interface PhoneService {
    void save(Telephone telephone);

    Telephone getByPhoneId(String id);

    List<Telephone> findAll();

    List<Telephone> findByPhoneName(String name);

    List<Telephone> findByParameterAndValue(String parameter, String value);
}

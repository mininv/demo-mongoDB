package com.yarm.vlad.demomongoDB.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Telephone {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String phoneId;

    private String phoneName;

    private String phoneDescription;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name = "key", length = 50)
    private Map<String, String> parameters = new HashMap<>();

    public Telephone() {
    }

    public Telephone(String phoneName, String phoneDescription) {
        this.phoneName = phoneName;
        this.phoneDescription = phoneDescription;
    }

    public Telephone(String phoneName, String phoneDescription, Map<String, String> parameters) {
        this.phoneName = phoneName;
        this.phoneDescription = phoneDescription;
        this.parameters = parameters;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneDescription() {
        return phoneDescription;
    }

    public void setPhoneDescription(String phoneDescription) {
        this.phoneDescription = phoneDescription;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}

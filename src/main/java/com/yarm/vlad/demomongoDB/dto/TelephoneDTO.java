package com.yarm.vlad.demomongoDB.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarm.vlad.demomongoDB.domain.Telephone;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TelephoneDTO {
    @JsonProperty("phoneId")
    private String phoneId;

    @NotNull
    @JsonProperty("phoneName")
    private String phoneName;

    @JsonProperty("phoneDescription")
    private String phoneDescription;

    @JsonProperty("parameters")
    private Map<String, String> parameters = new HashMap<>();

    public TelephoneDTO() {
    }

    public TelephoneDTO(@JsonProperty @NotNull String phoneName,
                        @JsonProperty String phoneDescription, @JsonProperty Map<String, String> parameters) {
        this.phoneName = phoneName;
        this.phoneDescription = phoneDescription;
        this.parameters = parameters;
    }

    public TelephoneDTO(@JsonProperty String phoneId, @JsonProperty @NotNull String phoneName,
                        @JsonProperty String phoneDescription, @JsonProperty Map<String, String> parameters) {
        this.phoneId = phoneId;
        this.phoneName = phoneName;
        this.phoneDescription = phoneDescription;
        this.parameters = parameters;
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

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public static TelephoneDTO toDto(Telephone telephone) {
        return telephone != null ?
                new TelephoneDTO(telephone.getPhoneId(),telephone.getPhoneName(), telephone.getPhoneDescription(), telephone.getParameters()) :
                new TelephoneDTO();
    }
    public static Telephone fromDTO(TelephoneDTO telephoneDTO) {
        return new Telephone(telephoneDTO.getPhoneName(), telephoneDTO.getPhoneDescription(), telephoneDTO.getParameters());
    }
}

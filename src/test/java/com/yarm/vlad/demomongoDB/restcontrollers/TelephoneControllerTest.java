package com.yarm.vlad.demomongoDB.restcontrollers;

import com.yarm.vlad.demomongoDB.BaseIntegrationTest;
import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.dto.TelephoneDTO;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TelephoneControllerTest extends BaseIntegrationTest {

    private HashMap<String, String> parameters;
    String samsung = "samsung";


    private String key = "параметр";
    private String value = "значение";

    {
        parameters = new HashMap<>();
        parameters.put(key, value);
    }

    @Test
    public void testAddPhone() throws Exception {
        TelephoneDTO telephoneDTO = new TelephoneDTO("phone1", "description",
                parameters);
        String jsonContent = mapper.writeValueAsString(telephoneDTO);
        String response = mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/phone/").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        ResponseMsg expected = new ResponseMsg(ResponseMsg.Status.SUCCESS,
                ResponseMsg.Message.SUCCESS.getMessage());
        ResponseMsg receivedResponse = mapper.readValue(response, ResponseMsg.class);

        Assert.assertThat(receivedResponse, SamePropertyValuesAs.samePropertyValuesAs(expected));
    }

    @Test
    public void testFindPhones() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/v1/phone"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains(samsung));
    }

    @Test
    public void testFindByName() throws Exception {
        this.mockMvc.perform(get("/v1/phone/?name=" + samsung))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform(get("/v1/phone/?name=" + "sdasa"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testFindByParameterAndValue() throws Exception {
        this.mockMvc.perform(get("/v1/phone/filter/?parameter=" + key + "&value=" + value))
                .andExpect(status().isOk())
                .andReturn();
        this.mockMvc.perform(get("/v1/phone/filter/?parameter=dsfsd&value=sadas"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testFindById() throws Exception {
        Telephone telephone = phoneService.findByPhoneName(samsung).stream().findFirst().get();
        String contentAsString = this.mockMvc.perform(get("/v1/phone/" + telephone.getPhoneId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Telephone telephone1 = mapper.readValue(contentAsString, Telephone.class);
        Assert.assertThat(telephone, SamePropertyValuesAs.samePropertyValuesAs(telephone1));
        this.mockMvc.perform(get("/v1/phone/asdasdasd"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Before
    public void prepareData() throws Exception {
        Telephone telephone = new Telephone();
        telephone.setPhoneName(samsung);
        telephone.setPhoneDescription("a30");
        telephone.setParameters(parameters);
        phoneService.save(telephone);
    }


}
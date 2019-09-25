package com.yarm.vlad.demomongoDB.restcontrollers;

import com.yarm.vlad.demomongoDB.domain.Telephone;
import com.yarm.vlad.demomongoDB.dto.TelephoneDTO;
import com.yarm.vlad.demomongoDB.exception.TelephoneManagementException;
import com.yarm.vlad.demomongoDB.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TelephoneController {
    @Autowired
    private PhoneService phoneService;

    /**
     * curl -d '{"phoneName":"phone1","phoneDescription":"description","parameters":{"параметр":"значение"}}' -H 'Content-Type: application/json' http://localhost:8080/v1/phone/
     *
     * @param telephoneDTO
     * @return SUCCESS message or FAIL message with exception stace trace
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/phone/",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseMsg addTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO) {
        ResponseMsg responseMsg = new ResponseMsg(ResponseMsg.Status.SUCCESS, ResponseMsg.Message.SUCCESS.getMessage());
        Telephone telephone = TelephoneDTO.fromDTO(telephoneDTO);
        try {
            phoneService.save(telephone);
        } catch (Exception e) {
            responseMsg.setStatus(ResponseMsg.Status.FAIL);
            responseMsg.setMessage(ResponseMsg.Message.FAIL.getMessage() + e.getMessage());
        }
        return responseMsg;
    }

    /**
     * curl -v http://localhost:8080/v1/phone
     *
     * @return json array of dto telephone objects
     */
    @RequestMapping(
            value = "/phone",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TelephoneDTO> getList() {
        return phoneService.findAll().stream()
                .map(TelephoneDTO::toDto)
                .collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/v1/phone/?name=HUAWEI
     * curl -v http://localhost:8080/v1/phone/?name=HUAWEI
     *
     * @param name
     * @return array of dto telephone by name
     */
    @RequestMapping(
            value = "/phone/",
            method = RequestMethod.GET
    )
    public List<TelephoneDTO> getByName(
            @RequestParam("name") String name
    ) {
        List<TelephoneDTO> collect = phoneService.findByPhoneName(name).stream()
                .map(TelephoneDTO::toDto)
                .collect(Collectors.toList());
        if (collect.isEmpty()) throw new TelephoneManagementException("");
        return collect;
    }

    /**
     * curl "http://localhost:8080/v1/phone/filter/?parameter=аккумулятор&value=3500 мАч"
     *
     * @param parameter
     * @param value
     * @return array of dto telephone by parameter and value
     */
    @RequestMapping(
            value = "/phone/filter/",
            method = RequestMethod.GET
    )
    public List<TelephoneDTO> getByParameterAndValue(
            @RequestParam("parameter") String parameter,
            @RequestParam("value") String value
    ) {
        List<TelephoneDTO> collect = phoneService.findByParameterAndValue(parameter, value)
                .stream()
                .map(TelephoneDTO::toDto)
                .collect(Collectors.toList());
        if (collect.isEmpty()) throw new TelephoneManagementException("");
        return collect;
    }

    /**
     *
     * @param id
     * @return dto telephone by id
     */
    @RequestMapping(
            value = "/phone/{id}",
            method = RequestMethod.GET
    )
    public TelephoneDTO getById(
            @PathVariable("id") String id
    ) {
        Telephone byPhoneId = phoneService.getByPhoneId(id);
        if (byPhoneId == null) {
            throw new TelephoneManagementException("");
        }
        return TelephoneDTO.toDto(byPhoneId);
    }

    /**
     *
     * @param ex
     * @return bad request with text - Not found
     */
    @ExceptionHandler(TelephoneManagementException.class)
    public ResponseEntity<String> handleNotEnoughFunds(TelephoneManagementException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }

    /**
     *
     * @return OK if controller works
     */
    @RequestMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}

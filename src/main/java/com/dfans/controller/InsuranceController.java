package com.dfans.controller;


import com.dfans.model.Insurance;
import com.dfans.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InsuranceController {

    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping(method = RequestMethod.POST, value = "/insurance")
    @ResponseBody
    public Object addInsurance(@RequestBody Insurance insurance){

        orderService.sendInsuranceMail(insurance);
        return insurance;

    }
}

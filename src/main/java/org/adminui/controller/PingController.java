package org.adminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class PingController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(Map<String, Object> model){
        model.put("response","in working");
        return "ping";
    }

}

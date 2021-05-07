package org.adminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getErrorPage(@RequestParam String error, Map<String, Object> model){
        model.put("error", error);
        return "error";
    }

}

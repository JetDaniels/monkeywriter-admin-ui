package org.adminui.controller;

import org.adminui.component.OutsideSystemDTO;
import org.adminui.component.RoleDTO;
import org.adminui.component.client.HttpClient;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class SystemController {

    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/systems", method = RequestMethod.GET)
    public String getSystemsPage(Map<String, Object> model) throws IOException {
        List<OutsideSystemDTO> systems = httpClient.getSystems("/api/systems");
        model.put("systems", systems);
        return "systems";
    }

    @RequestMapping(value = "/addSystem", method = RequestMethod.POST)
    public String addSystem(@ModelAttribute("OutsideSystemDTO") OutsideSystemDTO system) throws IOException {
        JSONObject jo = new JSONObject(system);
        httpClient.sendPost("/api/addSystem", String.format("%s",jo.toString()));
        return "redirect:/systems";
    }

}

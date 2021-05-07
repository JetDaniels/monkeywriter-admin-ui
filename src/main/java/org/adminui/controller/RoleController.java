package org.adminui.controller;

import org.adminui.component.RoleDTO;
import org.adminui.component.client.HttpClient;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String getRolesPage(Map<String, Object> model) throws IOException {
        List<RoleDTO> roles = httpClient.getRoles("/api/roles");
        model.put("roles", roles);
        return "roles";
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(@ModelAttribute("RoleDTO") RoleDTO role) throws IOException {
        JSONObject jo = new JSONObject(role);
        httpClient.sendPost("/api/addRole", String.format("%s",jo.toString()));
        return "redirect:/roles";
    }
}

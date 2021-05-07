package org.adminui.controller;

import org.adminui.component.RoleDTO;
import org.adminui.component.UserDTO;
import org.adminui.component.client.HttpClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final HttpClient httpClient = HttpClient.getInstance();

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsersPage(Map<String, Object> model) throws IOException {
        List<UserDTO> users = httpClient.getUsers("/api/users");
        List<RoleDTO> roles = httpClient.getRoles("/api/roles");
        model.put("users", users);
        model.put("roles", roles);
        return "users";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addRole(@ModelAttribute("UserDTO") UserDTO user) throws IOException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsActive(true);
        JSONObject jo = new JSONObject(user);
        httpClient.sendPost("/api/addUser", String.format("%s",jo.toString()));
        return "redirect:/users";
    }
}

package org.adminui.controller;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adminui.component.ConnectionDTO;
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
public class ConnectionController {

    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/connections", method = RequestMethod.GET)
    public String getConnectionsPage(Map<String, Object> model) throws IOException {
        List<ConnectionDTO> connections = httpClient.getConnections("/api/connections");
        model.put("connections", connections);
        return "connections";
    }

    @RequestMapping(value = "/addConnection", method = RequestMethod.POST)
    public String addConnection(@ModelAttribute("ConnectionDTO") ConnectionDTO connection) throws IOException {
        JSONObject jo = new JSONObject(connection);
        httpClient.sendPost("/api/addConnection", String.format("%s",jo.toString()));
        return "redirect:/connections";
    }
}

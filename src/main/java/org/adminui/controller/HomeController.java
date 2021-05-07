package org.adminui.controller;

import org.adminui.component.CreateDocumentsRequestLabelDTO;
import org.adminui.component.client.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getMainPage(Map<String, Object> model) throws IOException {
        List<CreateDocumentsRequestLabelDTO> requests = httpClient.getCDRLs("/api/requests?_page=0");
        model.put("req", requests);
        return "home";
    }

}

package org.adminui.controller;

import org.adminui.component.DocumentDTO;
import org.adminui.component.OutsideSystemDTO;
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
public class DocumentController {

    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public String getDocumentsPage(Map<String, Object> model) throws IOException {
        List<DocumentDTO> documents = httpClient.getDocuments("/api/documents");
        model.put("documents", documents);
        return "documents";
    }

    @RequestMapping(value = "/addDocument", method = RequestMethod.POST)
    public String addDocument(@ModelAttribute("DocumentDTO") DocumentDTO document) throws IOException {
        JSONObject jo = new JSONObject(document);
        httpClient.sendPost("/api/addDocument", String.format("%s",jo.toString()));
        return "redirect:/documents";
    }
}

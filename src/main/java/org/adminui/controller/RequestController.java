package org.adminui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.adminui.component.*;
import org.adminui.component.client.HttpClient;
import org.adminui.component.client.KafkaProducer;
import org.adminui.component.client.SOAPClient;
import org.adminui.component.templates.CreateDocumentsRequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class RequestController {

    @Autowired
    KafkaProducer kafkaProducer;
    private final HttpClient httpClient = HttpClient.getInstance();

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String getMainPage(Map<String, Object> model, @RequestParam(name = "_id") Integer id) throws IOException {
        CreateDocumentsRequestDTO cdr = httpClient.getCDR("/api/request?_id=" + id);
        List<CreateDocumentsRequestsStepsDTO> steps = httpClient.getSteps("/api/steps?_cdrId=" + id);
        System.out.println("REQUEST: " + cdr.getId() + " " + steps.size());
        model.put("cdr", cdr);
        model.put("steps", steps);
        model.put("id", id);
        return "request";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String getSendRequestPage(Map<String, Object> model) throws IOException {
        List<ConnectionDTO> connections = httpClient.getConnections("/api/connections");
        List<OutsideSystemDTO> systems = httpClient.getSystems("/api/systems");
        model.put("connections", connections);
        model.put("systems", systems);
        return "send";
    }

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public String getSendRequestPage(
            @RequestParam String id, @RequestParam String system, @RequestParam String connection,
            @RequestParam String documents, @RequestParam String xmlData, Map<String, Object> model) throws Exception {
        /*CreateDocumentsRequestTemplate template = new CreateDocumentsRequestTemplate(
                id, system, connection, xmlData, Arrays.asList(documents.split(";"))
        );
        SOAPClient soapClient = new SOAPClient(SOAPClient.DEFAULT_URL);
        soapClient.sendMessageToSOAPServer(template.applyTemplate(),"createDocumentsRequest");*/
        CreateDocumentsRequest request = new CreateDocumentsRequest();
        request.setId(id);
        request.setConnection(connection);
        request.setSystem(system);
        request.setXmlData(xmlData);
        request.setDocument(Arrays.asList(documents.split(";")));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        kafkaProducer.sendMessage(ow.writeValueAsString(request));
        model.put("message", "sending success");
        return "success";
    }

}

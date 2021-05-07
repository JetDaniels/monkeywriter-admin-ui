package org.adminui.component.client;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SOAPClient {
    public static final String DEFAULT_URL = "http://localhost:8080/monkeywriter/ws/create-documents/documents.wsdl";
    private static final String PREFIX = "doc";
    private static final String NAMESPACE = "http://doccreator.org";
    private static final String CHATSET = "UTF-8";

    private final String urlSOAPServer;
    private SOAPConnection SOAPConnection = null;

    public SOAPClient(String url) throws SOAPException {
        this.urlSOAPServer = url;
        createSOAPConnection();
    }

    private void createSOAPConnection() throws SOAPException {
        SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
        SOAPConnection = factory.createConnection();
    }

    public String sendMessageToSOAPServer(String request, String operation) throws Exception {
        SOAPElement soapElement = stringToSOAPElement(request);

        SOAPMessage soapResponse = SOAPConnection.call(
                createSOAPRequest(soapElement, operation),
                urlSOAPServer
        );
        return soapResponse.toString();
    }

    private SOAPMessage createSOAPRequest(SOAPElement body, String operation) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        soapEnvelope.addNamespaceDeclaration(PREFIX, NAMESPACE);

        SOAPBody soapBody = soapEnvelope.getBody();
        soapBody.addChildElement(body);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", urlSOAPServer + operation);

        soapMessage.saveChanges();
        System.out.println("Request: " + soapMessage.toString());
        return soapMessage;
    }

    private SOAPElement stringToSOAPElement(String request) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        InputStream is = new ByteArrayInputStream(request.getBytes());
        Document doc = builderFactory.newDocumentBuilder().parse(is);
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPBody soapBody = soapMessage.getSOAPBody();
        return soapBody.addDocument(doc);
    }

}

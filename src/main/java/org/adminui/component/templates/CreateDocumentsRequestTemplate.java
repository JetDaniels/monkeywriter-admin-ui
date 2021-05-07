package org.adminui.component.templates;

import java.util.List;

public class CreateDocumentsRequestTemplate implements RequestTemplate {

    private static final String template =
                    "<doc:createDocumentsRequest xmlns:doc=\"http://doccreator.org\">\n" +
                    "     \t<doc:Id>%s</doc:Id>\n" +
                    "     \t<doc:System>%s</doc:System>\n" +
                    "     \t<doc:Connection>%s</doc:Connection>\n" +
                    "     \t<doc:XmlData><![CDATA[%s]]></doc:XmlData>\n" +
                    "     %s" +
                    " </doc:createDocumentsRequest>";
    private String id;
    private String system;
    private String connection;
    private String xmlData;
    private List<String> documents;

    public CreateDocumentsRequestTemplate(String id, String system, String connection, String xmlData, List<String> documents){
        this.id = id;
        this.system = system;
        this.connection = connection;
        this.xmlData = xmlData;
        this.documents = documents;
    }

    @Override
    public String applyTemplate() {
        StringBuilder xmlDocuments = new StringBuilder();
        for(String document: documents){
            String xmlDocument = String.format("     \t<doc:Document>%s</doc:Document>\n", document);
            xmlDocuments.append(xmlDocument);
        }
        return String.format(template, id, system, connection, xmlData, xmlDocuments.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public void addDocument(String document){
        this.documents.add(document);
    }
}

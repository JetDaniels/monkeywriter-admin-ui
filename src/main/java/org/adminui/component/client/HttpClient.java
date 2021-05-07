package org.adminui.component.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adminui.component.*;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpClient{

    private final String serverUrl;
    private CloseableHttpClient client;
    private static HttpClient instance = null;

    private HttpClient(String serverUrl){
        this.serverUrl = serverUrl;
        this.client = HttpClients.createDefault();
    }

    public static HttpClient getInstance(){
        if(instance == null)
            instance = new HttpClient("http://localhost:8080/monkeywriter");
        return instance;
    }

    public List<CreateDocumentsRequestLabelDTO> getCDRLs(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), CreateDocumentsRequestLabelDTO[].class));
    }

    public List<CreateDocumentsRequestsStepsDTO> getSteps(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), CreateDocumentsRequestsStepsDTO[].class));
    }

    public List<UserDTO> getUsers(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), UserDTO[].class));
    }

    public List<RoleDTO> getRoles(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), RoleDTO[].class));
    }

    public List<DocumentDTO> getDocuments(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), DocumentDTO[].class));
    }

    public List<ConnectionDTO> getConnections(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), ConnectionDTO[].class));
    }

    public List<OutsideSystemDTO> getSystems(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(sendGet(serverUrl + url), OutsideSystemDTO[].class));
    }

    public CreateDocumentsRequestDTO getCDR(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(sendGet(serverUrl + url), CreateDocumentsRequestDTO.class);
    }

    public String sendGet(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
    }

    public String sendPost(String url, String[] params) throws IOException {
        HttpPost post = new HttpPost(serverUrl + url);
        List<NameValuePair> paramsList = new ArrayList<>();
        for(String param: params){
            paramsList.add(new BasicNameValuePair(param.split(":")[0], param.split(":")[1]));
        }

        post.setEntity(new UrlEncodedFormEntity(paramsList));
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 200) return null;
        return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
    }

    public String sendPost(String url, String json) throws IOException {
        HttpPost post = new HttpPost(serverUrl + url);
        post.addHeader("content-type", "application/json");
        post.setEntity(new StringEntity(json));
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 200) return null;
        return IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
    }

}

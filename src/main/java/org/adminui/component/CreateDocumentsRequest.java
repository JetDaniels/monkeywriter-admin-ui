package org.adminui.component;

import lombok.Data;

import java.util.List;

@Data
public class CreateDocumentsRequest {

    private String Id;

    private String System;

    private String Connection;

    private String XmlData;

    private List<String> Document;

}

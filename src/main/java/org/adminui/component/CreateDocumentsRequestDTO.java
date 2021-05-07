package org.adminui.component;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class CreateDocumentsRequestDTO {

    private Integer id;

    private String requestId;

    private String outsideSystem;

    private String connection;

    private Integer docsCount;

    private String xmlData;

    private long duration;

    private Timestamp timeBegin;
}

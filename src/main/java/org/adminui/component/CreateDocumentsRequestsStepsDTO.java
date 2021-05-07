package org.adminui.component;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class CreateDocumentsRequestsStepsDTO {
    private CreateDocumentsRequestsStepsId id;

    private String stage;

    private String message;

    private Timestamp timeBegin;

    @Data
    private static class CreateDocumentsRequestsStepsId{
        private Integer cdrId;

        private Integer step;
    }
}


package org.adminui.component;

import lombok.Data;

@Data
public class ConnectionDTO {
    private String connectionName;

    private String type;

    private String url;
}

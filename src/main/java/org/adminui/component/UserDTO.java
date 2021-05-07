package org.adminui.component;

import lombok.Data;

@Data
public class UserDTO {
    private String login;

    private String password;

    private String role;

    private Boolean isActive;
}

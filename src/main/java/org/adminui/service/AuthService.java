package org.adminui.service;

import lombok.SneakyThrows;
import org.adminui.component.client.HttpClient;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService implements UserDetailsService {

    private final HttpClient client;

    public AuthService(){
        this.client = HttpClient.getInstance();
    }

    @SneakyThrows({IOException.class, UsernameNotFoundException.class})
    @Override
    public UserDetails loadUserByUsername(String username) {
        String response = client.sendPost("/api/auth", new String[]{"username:" + username});
        JSONObject jo = new JSONObject(response);
        return User.builder()
                .username(jo.getString("login"))
                .password(jo.getString("password"))
                .accountExpired(!jo.getBoolean("isActive")).credentialsExpired(!jo.getBoolean("isActive"))
                .accountLocked(!jo.getBoolean("isActive")).disabled(!jo.getBoolean("isActive"))
                .roles(jo.getString("role"))
                .build();
    }
}

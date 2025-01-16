package com.server.gym_serverapplication.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Getter
@Setter
public class EndpointsConfig {
    @JsonProperty("PublicEndpoints")
    private List<String> PublicEndpoints;

    @JsonProperty("PUBLIC_ENDPOINTS_METHOD")
    private List<String> PUBLIC_ENDPOINTS_METHOD;

    @JsonProperty("PUBLIC_ENDPOINTS_ADMIN")
    private List<String> PUBLIC_ENDPOINTS_ADMIN;

    @JsonProperty("PUBLIC_ENDPOINTS_AUTHOR")
    private List<String> PUBLIC_ENDPOINTS_AUTHOR;

    @JsonProperty("PUBLIC_ENDPOINTS_ALL_ROLES")
    private List<String> PUBLIC_ENDPOINTS_ALL_ROLES;


    private static EndpointsConfig loadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("EndpointsConfig.json");
            InputStream inputStream = resource.getInputStream();
            return mapper.readValue(inputStream, EndpointsConfig.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static String[] getPublicEndPointsAllRoles() {
        EndpointsConfig config = loadConfig();
        if (config != null && config.PUBLIC_ENDPOINTS_ALL_ROLES != null) {
            return config.PUBLIC_ENDPOINTS_ALL_ROLES.toArray(new String[0]);
        }
        return new String[0];
    }

    public static String[] getPublicEndpointsAdmin() {
        EndpointsConfig config = loadConfig();
        if (config != null && config.PUBLIC_ENDPOINTS_ADMIN != null) {
            return config.PUBLIC_ENDPOINTS_ADMIN.toArray(new String[0]);
        }
        return new String[0];
    }

    public static String[] getPublicEndpointsAuthor() {
        EndpointsConfig config = loadConfig();
        if (config != null && config.PUBLIC_ENDPOINTS_AUTHOR != null) {
            return config.PUBLIC_ENDPOINTS_AUTHOR.toArray(new String[0]);
        }
        return new String[0];
    }

    public static String[] getPublicEndpoints() {
        EndpointsConfig config = loadConfig();
        if (config != null && config.PublicEndpoints != null) {
            return config.PublicEndpoints.toArray(new String[0]);
        }
        return new String[0];
    }

    public static String[] getPublicEndpointsMethod() {
        EndpointsConfig config = loadConfig();
        if (config != null && config.PUBLIC_ENDPOINTS_METHOD != null) {
            return config.PUBLIC_ENDPOINTS_METHOD.toArray(new String[0]);
        }
        return new String[0];
    }
}

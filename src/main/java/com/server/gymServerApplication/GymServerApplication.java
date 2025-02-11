package com.server.gymServerApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
//@EnableJpaRepositories(basePackages = {"com.server.gymServerApplication.repository"})
@EntityScan(basePackages = {"com.server.gymServerApplication.entity"})
public class GymServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymServerApplication.class, args);
        openSwaggerUI();
    }

    /**
     * auto mở swagger
     */
    private static void openSwaggerUI() {
        String url = "http://localhost:8082/swagger-ui/index.html";
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url).start();
        } catch (IOException ignored) {
            throw new RuntimeException();
        }
    }


}

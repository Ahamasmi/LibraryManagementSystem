package com.example.LibraryManagementSystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "library-manager.defaults")
@Component
@Getter
@Setter
public class DefaultConfig {
    private int pageSize;
}

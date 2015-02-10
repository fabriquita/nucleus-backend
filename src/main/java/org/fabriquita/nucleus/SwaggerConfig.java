package org.fabriquita.nucleus;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springboot.swagger.SpringBootSwaggerConfig;

import com.google.common.collect.Lists;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;

@Configuration
@Import(SpringSwaggerConfig.class)
public class SwaggerConfig extends SpringBootSwaggerConfig {

    @Override
    protected List<String> getIncludePatterns() {
        return Lists.newArrayList("/group.*","/user.*");
    }

    @Override
    protected String getSwaggerGroup() {
        return "group";
    }

}

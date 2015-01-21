package org.fabriquita.nucleus;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Lists;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;

import springboot.swagger.SpringBootSwaggerConfig;

@Configuration
@Import(SpringSwaggerConfig.class)
public class SwaggerConfig extends SpringBootSwaggerConfig {

    @Override
    protected List<String> getIncludePatterns() {
        return Lists.newArrayList("/group.*");
    }

    @Override
    protected String getSwaggerGroup() {
        return "group";
    }

}

package org.fabriquita.nucleus;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springboot.swagger.SpringBootSwaggerConfig;

import com.google.common.collect.Lists;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.core.SwaggerPathProvider;

@Configuration
@Import(SpringSwaggerConfig.class)
public class SwaggerConfig extends SpringBootSwaggerConfig {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    private ServletContext servletContext;

    @Override
    protected List<String> getIncludePatterns() {
        return Lists.newArrayList("/group.*","/user.*","/role.*","/resource.*","/auth.*");
    }

    @Override
    protected String getSwaggerGroup() {
        return "group";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    public SwaggerPathProvider pathProvider() {
        return new NucleusSwaggerPathProvider(springSwaggerConfig.defaultSwaggerPathProvider(), servletContext);
    }

}

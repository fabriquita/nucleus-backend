package org.fabriquita.nucleus;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import springboot.swagger.ServletContextPathProvider;

import com.mangofactory.swagger.core.SwaggerPathProvider;

public class NucleusSwaggerPathProvider extends ServletContextPathProvider {

    @Autowired
    ApplicationContext applicationContext;

    private final ServletContext servletContext;

    public NucleusSwaggerPathProvider(SwaggerPathProvider swaggerPathProvider, ServletContext servletContext) {
        super(swaggerPathProvider, servletContext);
        this.servletContext = servletContext;
    }

    public String getAppBasePath() {
        return UriComponentsBuilder
                .fromHttpUrl(applicationContext.getEnvironment().getProperty("swagger.url"))
                .path(servletContext.getContextPath())
                .build()
                .toString();
    }

}

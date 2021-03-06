package org.fabriquita.nucleus;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.fabriquita.nucleus.shiro.ShiroRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class ShiroWebConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/forbidden");
        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
        filterChainDefinitionMapping.put("/", "anon");
        filterChainDefinitionMapping.put("/home", "authc,roles[guest]");
        filterChainDefinitionMapping.put("/admin", "authc,roles[admin]");
        filterChainDefinitionMapping.put("/**", "securityFilter");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
        shiroFilter.setSecurityManager(securityManager());
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("anon", new AnonymousFilter());
        filters.put("authc", new FormAuthenticationFilter());
        filters.put("logout", new LogoutFilter());
        filters.put("roles", new RolesAuthorizationFilter());
        filters.put("user", new UserFilter());
        filters.put("securityFilter", new ShiroSecurityFilter());
        shiroFilter.setFilters(filters);
        System.out.println(shiroFilter.getFilters().size());
        return shiroFilter;
    }

    @Bean(name = "securityManager")
    public WebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm realm() {
        ShiroRealm realm = new ShiroRealm();
        realm.init();
        return realm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @DependsOn("lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
}

package org.fabriquita.nucleus.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class NucleusToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
    private String credentials;
    private String host;
    private Long expiration;
    private boolean rememberMe;

    public NucleusToken() {
    }

    public NucleusToken(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public NucleusToken(String user, String password, String host) {
        this.user = user;
        this.password = password;
        this.host = host;
    }

    public NucleusToken(String user, String password, boolean rememberMe) {
        this.user = user;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public NucleusToken(String user, String password, String host, boolean rememberMe) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.rememberMe = rememberMe;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

}

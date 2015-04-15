package org.fabriquita.nucleus;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.fabriquita.nucleus.shiro.NucleusToken;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;

public class ShiroSecurityFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String principal = request.getHeader("principal");
        String credentials = request.getHeader("credentials");
        if (principal != null && credentials != null) {
            NucleusToken nucleusToken = ShiroSecurityUtils.getToken(principal);
            if (nucleusToken.getCredentials().equals(credentials)) {
                //TODO grisaf validate expiration
                UsernamePasswordToken token = new UsernamePasswordToken(nucleusToken.getUser(), nucleusToken.getPassword());
                ShiroSecurityUtils.login(token);
            }
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}

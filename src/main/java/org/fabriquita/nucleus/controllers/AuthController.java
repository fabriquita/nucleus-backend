package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.fabriquita.nucleus.services.UserService;
import org.fabriquita.nucleus.shiro.NucleusToken;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/auth")
@Api("Login and Logout Rest Services")
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody Map<String, Object> data) {
        String username = (String)data.get("user");
        String password = (String)data.get("password");
        String hashedPassword = new Sha256Hash(password).toString();
        UsernamePasswordToken token = new UsernamePasswordToken(username, hashedPassword);
        ShiroSecurityUtils.login(token);
        NucleusToken nucleusToken = ShiroSecurityUtils.generateToken(username, hashedPassword, null);
        ShiroSecurityUtils.addToken(username, nucleusToken);
        return nucleusToken;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody Map<String, Object> data) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

}

package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.fabriquita.nucleus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/")
@Api("User Rest Services")
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object login(@RequestBody Map<String, Object> data) {
        String username = (String)data.get("user");
        String password = (String)data.get("password");
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        currentUser.login(token);
        return token;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody Map<String, Object> data) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

}

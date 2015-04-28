package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fabriquita.nucleus.NucleusConstants;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/user")
@Api("User Rest Services")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresAuthentication
    @RequiresPermissions("user:r")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = NucleusConstants.PAGE_SIZE;
        }
        return userService.list(page, size);
    }

    @RequiresAuthentication
    @RequiresPermissions("user:r")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable(value = "id") Long id) {
        return userService.get(id);
    }

    @RequiresAuthentication
    @RequiresPermissions("user:c")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User add(@RequestBody Map<String, Object> data) {
        String name = null;
        String lastName = null;
        String userName = null;
        String password = null;
        Long groupId = null;
        Long roleId = null;
        String email = null;
        Boolean archived = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        } else {
            throw new IllegalArgumentException(
                    "'name' must not be null or empty");
        }
        if (data.get("lastName") != null) {
            lastName = (String) data.get("lastName");
        } else {
            throw new IllegalArgumentException(
                    "'lastName' must not be null or empty");
        }
        if (data.get("userName") != null) {
            userName = (String) data.get("userName");
        } else {
            throw new IllegalArgumentException(
                    "'userName' must not be null or empty");
        }
        if (data.get("password") != null) {
            password = (String) data.get("password");
        } else {
            throw new IllegalArgumentException(
                    "'password' must not be null or empty");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        if (data.get("role_id") != null){
            roleId = new Long(data.get("role_id").toString());
        }
        if (data.get("email") != null) {
            email = (String) data.get("email");
        } else {
            throw new IllegalArgumentException(
                    "'email' must not be null or empty");
        }
        if (data.get("archived") != null){
            archived = Boolean.valueOf(data.get("archived").toString());
        }
        return userService.add(name, lastName, userName, password, groupId, roleId, email, archived);
    }

    @RequiresAuthentication
    @RequiresPermissions("user:u")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@PathVariable(value = "id") Long id,
            @RequestBody Map<String, Object> data) {
        String name = null;
        String lastName = null;
        String userName = null;
        String password = null;
        String email = null;
        Long groupId = null;
        Long roleId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("lastName") != null) {
            lastName = (String) data.get("lastName");
        }
        if (data.get("userName") != null) {
            userName = (String) data.get("userName");
        }
        if (data.get("password") != null) {
            password = (String) data.get("password");
        }
        if (data.get("email") != null) {
            email = (String) data.get("email");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        if (data.get("role_id") != null) {
            roleId = new Long(data.get("role_id").toString());
        }
        return userService.update(id, name, lastName, userName, password, email, groupId, roleId);
    }

    @RequiresAuthentication
    @RequiresPermissions("user:d")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        userService.delete(id);
    }

}

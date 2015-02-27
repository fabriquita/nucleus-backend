package org.fabriquita.nucleus.controllers;

import java.util.List;
import java.util.Map;

import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/role")
@Api("User Rest Services")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> list() {
        return roleService.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role get(@PathVariable(value = "id") Long id) {
        return roleService.get(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role add(@RequestBody Map<String, Object> data) {
        String name = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        } else {
            throw new IllegalArgumentException(
                    "'name' must not be null or empty");
        }
        return roleService.add(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role update(@PathVariable(value = "id") Long id,
            @RequestBody Map<String, Object> data) {
        String name = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        return roleService.update(id, name);
    }

    @RequestMapping(value = "/{id}/resource/{resource_id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role addResource(@PathVariable(value = "id") Long id,
            @PathVariable(value = "resource_id") Long resourceId,
            @RequestBody Map<String, Object> data) {
        return roleService.addResource(id, resourceId);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        roleService.delete(id);
    }

}

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
        Long groupId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        return roleService.add(name, groupId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role update(@PathVariable(value = "id") Long id,
            @RequestBody Map<String, Object> data) {
        String name = null;
        Long groupId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        return roleService.update(id, name, groupId);
    }
    
    @RequestMapping(value = "/{id}/resource/{resource_id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role addResource(@PathVariable(value = "id") Long id,
            @PathVariable(value = "resource_id") Long resourceId,
            @RequestBody Map<String, Object> data) {
        String permissions = null;
        if( data.get("permissions") != null ){
            permissions = (String) data.get("permissions");
        }
        return roleService.addRoleResource(id, resourceId, permissions);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        roleService.delete(id);
    }

}

package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fabriquita.nucleus.NucleusConstants;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.services.RoleResourceService;
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
@RequestMapping(value = "/roleResource")
@Api("User Rest Services")
public class RoleResourceController {

    @Autowired
    private RoleResourceService roleResourceService;

    @RequiresAuthentication
    @RequiresPermissions("roleresource:r")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RoleResource> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = NucleusConstants.PAGE_SIZE;
        }
        return roleResourceService.list(page, size);
    }

    @RequiresAuthentication
    @RequiresPermissions("roleresource:r")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleResource get(@PathVariable(value = "id") Long id) {
        return roleResourceService.get(id);
    }
    
    @RequiresAuthentication
    @RequiresPermissions("roleresource:c")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleResource add(@RequestBody Map<String, Object> data) {
        Long roleId = null;
        Long resourceId = null;
        String permissions = null;
       
        if (data.get("role_id") != null) {
            roleId = new Long(data.get("role_id").toString());
        }
        if (data.get("resource_id") != null){
            resourceId = new Long(data.get("resource_id").toString());
        }
        if (data.get("permissions") != null) {
            permissions = (String) data.get("permissions");
        } else {
            throw new IllegalArgumentException(
                    "'permissions' must not be null or empty");
        }
        return roleResourceService.add(roleId, resourceId, permissions);
    }

    @RequiresAuthentication
    @RequiresPermissions("roleresource:u")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleResource update(@PathVariable(value = "id") Long id,
            @RequestBody Map<String, Object> data) {
        String permissions = null;
        if (data.get("permissions") != null) {
            permissions = (String) data.get("permissions");
        }
        return roleResourceService.update(id, permissions);
    }

    @RequiresAuthentication
    @RequiresPermissions("roleresource:d")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        roleResourceService.delete(id);
    }

}

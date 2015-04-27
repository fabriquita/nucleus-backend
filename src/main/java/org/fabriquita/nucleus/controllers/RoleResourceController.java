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
    @RequiresPermissions("roleresources:r")
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
    @RequiresPermissions("roleresources:r")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleResource get(@PathVariable(value = "id") Long id) {
        return roleResourceService.get(id);
    }

    @RequiresAuthentication
    @RequiresPermissions("roleresources:u")
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
    @RequiresPermissions("roleresources:d")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        roleResourceService.delete(id);
    }

}

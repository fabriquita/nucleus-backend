package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fabriquita.nucleus.NucleusConstants;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.services.GroupService;
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
@RequestMapping(value = "/group")
@Api("Group Rest Services")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequiresAuthentication
    @RequiresPermissions("group:r")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Group> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = NucleusConstants.PAGE_SIZE;
        }
        return groupService.list(page, size);
    }

    @RequiresAuthentication
    @RequiresPermissions("group:r")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Group get(@PathVariable(value = "id") Long id) {
        return groupService.get(id);
    }

    @RequiresAuthentication
    @RequiresPermissions("group:c")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Group add(@RequestBody Map<String, Object> data) {
        String name = null;
        String description = null;
        Long parentId = null;
        Boolean archived = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        } else {
            throw new IllegalArgumentException(
                    "'name' must not be null or empty");
        }
        if (data.get("description") != null) {
            description = (String) data.get("description");
        }
        if (data.get("parent_id") != null) {
            parentId = new Long(data.get("parent_id").toString());
        }
        if (data.get("archived") != null){
            archived = Boolean.valueOf(data.get("archived").toString());
        }
        return groupService.add(name, parentId, description, archived);
    }

    @RequiresAuthentication
    @RequiresPermissions("group:u")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Group update(@PathVariable(value = "id") Long id,
            @RequestBody Map<String, Object> data) {
        String name = null;
        String description = null;
        Long parentId = null;
        Boolean archived = null;
        if (data.get("parent_id") != null) {
            parentId = new Long(data.get("parent_id").toString());
        }
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("description") != null) {
            description = (String) data.get("description");
        }
        if (data.get("archived") != null){
            archived = Boolean.valueOf(data.get("archived").toString());
        }
        return groupService.update(id, name, parentId, description, archived);
    }

    @RequiresAuthentication
    @RequiresPermissions("group:d")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    // @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        groupService.delete(id);
    }

}

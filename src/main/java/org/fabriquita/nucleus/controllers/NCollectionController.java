package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fabriquita.nucleus.NucleusConstants;
import org.fabriquita.nucleus.models.NCollection;
import org.fabriquita.nucleus.services.NCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/collections")
public class NCollectionController {
    
    @Autowired
    private NCollectionService nCollectionService;
    
    @RequiresAuthentication
    @RequiresPermissions("collection:r")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<NCollection> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = NucleusConstants.PAGE_SIZE;
        }
        return nCollectionService.list(page, size);
    }
    
    @RequiresAuthentication
    @RequiresPermissions("collection:c")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NCollection add(@RequestBody Map<String, Object> data) {
        String name = null;
        Long groupId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        return nCollectionService.add(name, groupId);
    }
    
    @RequiresAuthentication
    @RequiresPermissions("collection:u")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NCollection update(@PathVariable(value = "id") Long id, @RequestBody Map<String, Object> data) {
        String name = null;
        Long groupId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        return nCollectionService.update(id, name, groupId);
    }
}

package org.fabriquita.nucleus.controllers;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fabriquita.nucleus.NucleusConstants;
import org.fabriquita.nucleus.models.NEntity;
import org.fabriquita.nucleus.services.NEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/entities")
public class NEntityController {
    
    @Autowired
    private NEntityService nEntityService;

    @RequiresAuthentication
    @RequiresPermissions("entity:r")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<NEntity> list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = NucleusConstants.PAGE_SIZE;
        }
        return nEntityService.list(page, size);
    }
    
    @RequiresAuthentication
    @RequiresPermissions("entity:c")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NEntity add(@RequestBody Map<String, Object> data) {
        String name = null;
        Long groupId = null;
        if (data.get("name") != null) {
            name = (String) data.get("name");
        }
        if (data.get("group_id") != null) {
            groupId = new Long(data.get("group_id").toString());
        }
        return nEntityService.add(name, groupId);
    }
}

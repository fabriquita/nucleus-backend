package org.fabriquita.nucleus.controllers;

import java.util.List;
import java.util.Map;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/group")
@Api("Group Rest Services")
public class GroupController {

    @Autowired
	private GroupService groupService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Group> list() {
	    return groupService.list();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Group get(@PathVariable(value="id") Long id) {
		return groupService.get(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Group add(@RequestBody Map<String, Object> data) {
		String name = (String)data.get("name");
	    String description = (String)data.get("description");
	    String archived = (String)data.get("archived");
	    Long parentId = null;
	    if(data.get("parent_id") != null){
	    	parentId = new Long(data.get("parent_id").toString());
	    }
	    return groupService.add(name, parentId, description, archived);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Group update(@PathVariable(value="id") Long id, @RequestBody Map<String, Object> data) {
		String name = null;
	    String description = null;
	    String archived = null;
		Long parentId = null;
	    if((String)data.get("parent_id") != null){
	    	parentId = new Long(data.get("parent_id").toString());
	    }
	    if(data.get("name") != null){
	    	name = (String) data.get("name");
	    }
	    if(data.get("description") != null){
	    	description = (String) data.get("description");
	    }
	    if(data.get("archived") != null){
	    	archived = (String) data.get("archived");
	    }
	    return groupService.update(id, name, parentId, description, archived);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value="id") Long id) {
	    groupService.delete(id);
	}

}

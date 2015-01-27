package org.fabriquita.nucleus.controllers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.mysql.fabric.Server;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GroupControllerTest {
	
	@Autowired
	GroupRepository repository;
	
	RestTemplate restTemplate = new RestTemplate();

	Group root;
	
	String serverUrl = "http://localhost:";

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        root = new Group();
        root.setName("Root");
        root.setLevel(1L);
        repository.deleteAll();
        repository.save(Arrays.asList(root));
    }
	
    @Test
    public void allTest() {
    	Long id = root.getId();
    	Group group = restTemplate.getForObject(serverUrl+port+"/group/"+id, Group.class);
    	System.out.println(root.getName());
    	System.out.println(group.getName());
    	System.out.println(group.getId());
    	System.out.println(port);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", "Root++");
    	Group updatedGroup = restTemplate.postForObject("http://localhost:"+port+"/group/"+id, map , Group.class);
    	System.out.println(updatedGroup.getName());
    	
    	restTemplate.delete("http://localhost:"+port+"/group/"+id);
    }
    
	@Test
    public void getTest() {
    	Vector<Map<String,Object>> groups = restTemplate.getForObject(serverUrl+port+"/group/", Vector.class);
    	for(Map<String,Object> group : groups){
    		System.out.println(group.get("id"));
    	}
    }
	
	@Test
	public void getByIdTest() {
		Long id = root.getId();
    	Group group = restTemplate.getForObject(serverUrl+port+"/group/"+id, Group.class);
    	assertNotNull(group);
	}
	
//	@Test
//	public void delete() {
//		
//	}

}

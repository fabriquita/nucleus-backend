package org.fabriquita.nucleus;

import org.fabriquita.nucleus.popdb.GroupPopDB;
import org.fabriquita.nucleus.popdb.ResourcePopDB;
import org.fabriquita.nucleus.popdb.RolePopDB;
import org.fabriquita.nucleus.popdb.RoleResourcePopDB;
import org.fabriquita.nucleus.popdb.UserPopDB;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PopDB implements CommandLineRunner {

    @Autowired
    GroupPopDB groupPopDB;

    @Autowired
    RolePopDB rolePopDB;

    @Autowired
    ResourcePopDB resourcePopDB;

    @Autowired
    RoleResourcePopDB roleResourcePopDB;

    @Autowired
    UserPopDB userPopDB;

    public static boolean isPopDBExecuted = false;

    public static void main(String[] args) {
        isPopDBExecuted = true;
        ShiroSecurityUtils.setPopdb(true);
		SpringApplication.run(PopDB.class, args);
	}

    public void run(String... args) {
        if (isPopDBExecuted) {
            groupPopDB.popDB();
            rolePopDB.popDB();
            resourcePopDB.popDB();
            roleResourcePopDB.popDB();
            userPopDB.popDB();
            System.exit(0);
        }
    }

}

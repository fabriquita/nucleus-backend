package org.fabriquita.nucleus;

import org.fabriquita.nucleus.popdb.GroupPopDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class PopDB implements CommandLineRunner {

    @Autowired
    GroupPopDB groupPopDB;

    public static boolean isPopDBExecuted = false;

    public static void main(String[] args) {
        isPopDBExecuted = true;
		SpringApplication.run(PopDB.class, args);
	}

    public void run(String... args) {
        if (isPopDBExecuted) {
            groupPopDB.popDB();
            System.exit(0);
        }
    }

}

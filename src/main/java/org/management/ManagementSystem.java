package org.management;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(ManagementSystem.class,args);
        System.out.println("Campaign Management System Application Started");
    }

}
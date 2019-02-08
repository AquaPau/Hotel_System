package com.epam.hotel;

import com.epam.hotel.daos.UserDao;
import com.epam.hotel.daos.UserDaoJdbcImpl;
import com.epam.hotel.daos.UserDaoTemplateImpl;
import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import com.epam.hotel.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your awesome hotel system!");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        UserService userService = (UserService) context.getBean("userService");


        System.out.println(userService.getById(8));

        System.out.println();

        userService.getAll().forEach(System.out::println);

    }
}

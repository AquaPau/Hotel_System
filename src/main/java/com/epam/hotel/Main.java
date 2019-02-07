package com.epam.hotel;

import com.epam.hotel.daos.UserDaoTemplateImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your awesome hotel system!");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        UserDaoTemplateImpl userDao = (UserDaoTemplateImpl) context.getBean("userDao");
        System.out.println(userDao.getUserCount());
        userDao.getAll().forEach(System.out::println);

    }
}

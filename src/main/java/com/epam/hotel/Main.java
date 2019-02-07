package com.epam.hotel;

import com.epam.hotel.daos.UserDao;
import com.epam.hotel.daos.UserDaoJdbcImpl;
import com.epam.hotel.daos.UserDaoTemplateImpl;
import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your awesome hotel system!");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        UserDao userDao = (UserDao) context.getBean("userDaoJdbc");
        userDao.getAll().forEach(System.out::println);

    }
}

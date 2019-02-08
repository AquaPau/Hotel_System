package com.epam.hotel;

import com.epam.hotel.daos.RoomDaoJDBCImpl;
import com.epam.hotel.daos.RoomDaoTemplateImpl;
import com.epam.hotel.daos.UserDaoTemplateImpl;
import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your awesome hotel system!");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-config.xml");

        RoomDaoTemplateImpl roomDao = (RoomDaoTemplateImpl) context.getBean("roomDaoTemplate");
        UserDaoTemplateImpl userDao = (UserDaoTemplateImpl) context.getBean("userDaoTemplate");
        RoomDaoJDBCImpl roomJDBC = new RoomDaoJDBCImpl();
        Room room1 = new Room();
        room1.setRoomNumber(1);
        room1.setCapacity(Capacity.SINGLE);
        room1.setClassID(ClassID.ECONOM);
        room1.setPrice((BigDecimal.valueOf(500)));
        roomJDBC.create(room1);
/*        int count = roomDao.checkIfNumberExists(1);
        System.out.println(count);*/




    }
}

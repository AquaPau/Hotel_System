package com.epam.hotel.daos;

import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoTemplateImplTest {

    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        jdbcTemplate = context.getBean("testJdbcTemplate", JdbcTemplate.class);
        userDao = new UserDaoTemplateImpl(jdbcTemplate);
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new ClassPathResource("test-database-create.sql"));
    }

    @After
    public void after() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new ClassPathResource("test-database-drop.sql"));
    }

    private User createTestUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test".toCharArray());
        user.setPermission(Permission.USER);
        user.setFirstName("test");
        user.setLastName("test");
        return user;
    }

    @Test
    public void create() {
        User user = createTestUser();
        userDao.create(user);
        assertEquals(user.getId(), 1);
    }

    @Test
    public void delete() {
        User user = createTestUser();
        userDao.create(user);
        assertTrue(userDao.delete(1));
    }

    @Test
    public void update() {
        User user = createTestUser();
        userDao.create(user);

        user.setLogin("testUpdate");
        user.setPassword("testUpdate".toCharArray());
        user.setFirstName("testUpdate");
        user.setLastName("testUpdate");
        user.setPermission(Permission.ADMIN);
        userDao.update(user);

        User updatedUser = userDao.getById(1);
        assertEquals(user.toString(), updatedUser.toString());
    }

    @Test
    public void getAll() {
        User user = createTestUser();
        userDao.create(user);
        user.setLogin("testGetAll");
        userDao.create(user);
        List<User> userList = userDao.getAll();
        assertEquals(userList.size(), 2);
    }

    @Test
    public void getById() {
        User user = createTestUser();
        userDao.create(user);
        User receivedUser = userDao.getById(1);
        assertEquals(user.toString(), receivedUser.toString());
    }

}

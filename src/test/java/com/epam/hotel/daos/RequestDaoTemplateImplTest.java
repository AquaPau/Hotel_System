package com.epam.hotel.daos;

import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.enums.PaymentStatus;
import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.Request;

import com.epam.hotel.model.User;
import org.junit.*;

import static org.junit.Assert.*;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


public class RequestDaoTemplateImplTest {
    private static RequestDao requestDao;
    private static JdbcTemplate jdbcTemplate;


    @BeforeClass
    public static void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        jdbcTemplate = context.getBean("testJdbcTemplate", JdbcTemplate.class);
        requestDao = new RequestDaoTemplateImpl(jdbcTemplate);
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-create.sql"));
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-drop.sql"));
    }

    @Before
    public void set() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-create.sql"));

    }

    @After
    public void tearDown() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-drop.sql"));
    }

    private Request createTestRequest() {
        Request request = new Request();
        request.setUserID(1L);
        request.setCapacity(Capacity.SINGLE);
        request.setClassID(ClassID.STANDARD);
        request.setPaymentStatus(PaymentStatus.NOBILL);
        request.setCheckIn(Timestamp.valueOf(LocalDateTime.of(2018, 1, 1, 1, 0,
                0, 0)));
        request.setCheckOut(Timestamp.valueOf(LocalDateTime.of(2018, 1, 2, 1, 0,
                0, 0)));
        return request;
    }


    @Test
    public void create() {
        Request testRequest = createTestRequest();
        requestDao.create(testRequest);
        assertEquals(testRequest.getRequestID(), 1);
    }


    @Test
    public void update1() {
        Request testRequest = createTestRequest();
        requestDao.create(testRequest);
        testRequest.setPaymentStatus(PaymentStatus.BILLSENT);

        boolean isUpdated = requestDao.update(testRequest);
        assertTrue(isUpdated);

    }

    @Test
    public void update2() {
        Request testRequest = createTestRequest();
        requestDao.create(testRequest);
        long createdId = testRequest.getRequestID();
        testRequest.setPaymentStatus(PaymentStatus.BILLSENT);
        requestDao.update(testRequest);
        Request receivedRequest = requestDao.getById(createdId);
        assertEquals(testRequest, receivedRequest);
    }


    @Test
    public void getById1() {
        Request testRequest = createTestRequest();
        testRequest = requestDao.create(testRequest);
        long createdId = testRequest.getRequestID();
        Request receivedRequest = requestDao.getById(createdId);
        assertEquals(testRequest, receivedRequest);

    }


    @Test
    public void delete1() {
        Request testRequest = createTestRequest();
        testRequest = requestDao.create(testRequest);
        long createdId = testRequest.getRequestID();
        boolean isDeleted = requestDao.delete(createdId);
        assertTrue(isDeleted);
    }


    @Test
    public void getAll() {
        Request testRequest1 = createTestRequest();
        requestDao.create(testRequest1);
        Request testRequest2 = createTestRequest();
        testRequest2.setPaymentStatus(PaymentStatus.BILLSENT);
        requestDao.create(testRequest2);
        List<Request> testScope = Arrays.asList(testRequest1, testRequest2);
        List<Request> receivedScope = requestDao.getAll();
        assertEquals(testScope, receivedScope);
    }


    @Test
    public void getUserRequests() {
        Request testRequest = createTestRequest();
        testRequest = requestDao.create(testRequest);
        List<Request> testScope = Arrays.asList((testRequest));
        List<Request> receivedScope = requestDao.getUserRequests(1);
        assertEquals(testScope, receivedScope);
    }

}
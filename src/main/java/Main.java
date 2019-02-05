import daos.UserDao;
import model.Room;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Your awesome hotel system!");
        ApplicationContext context = new ClassPathXmlApplicationContext();
        UserDao userDao= (UserDao) context.getBean("userDao");
        System.out.println(userDao.getUserCount());
    }
}

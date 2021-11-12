package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      Car car1 = new Car("Nissan", 303);
      Car car2 = new Car("BMW", 208);
      Car car3 = new Car("Porshe", 3455);
      Car car4 = new Car("Audi", 25);

      userService.add(new User("Алексей", "Игоревич", "aa@mail.ru", car1));
      userService.add(new User("Алина", "Владимировна", "yy@mail.ru", car2));
      userService.add(new User("Юлия", "Константиновна", "ii@mail.ru", car3));
      userService.add(new User("Игорь", "Викторович", "nn@mail.ru", car4));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getUserCar());
         System.out.println();
      }

      //Определение юзера по модели и серии машины
      System.out.println(userService.getUserByCar("Porshe", 3455));

      context.close();
   }
}

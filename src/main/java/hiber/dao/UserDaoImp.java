package hiber.dao;

import hiber.model.User;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String hql = "FROM User u " +
              "LEFT JOIN FETCH u.userCar " +
              "WHERE u.userCar.model =:model AND u.userCar.series =:series";

      try {
         TypedQuery<User> query = sessionFactory.openSession().createQuery(hql);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.getSingleResult();
      } catch (NoResultException | NonUniqueResultException e) {
         e.printStackTrace();
      }
      return null;
   }
}

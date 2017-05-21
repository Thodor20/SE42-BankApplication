package auction.dao;

import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = this.em.createNamedQuery("User.count", User.class);
        int result = ((Long) q.getSingleResult()).intValue();
        return result;
    }

    @Override
    public void create(User user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        this.em.getTransaction().begin();
        this.em.persist(user);
        this.em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }

        this.em.persist(user);
    }

    @Override
    public List<User> findAll() {
        Query q = this.em.createNamedQuery("User.getAll", User.class);
        List<User> result = q.getResultList();
        return result;
    }

    @Override
    public User findByEmail(String email) {
        Query q = this.em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        User result = null;
        try {
            result = (User) q.getSingleResult();
        }catch(Exception e){
            
        }
        return result;
    }

    @Override
    public void remove(User user) {
        em.remove(user);
    }

}

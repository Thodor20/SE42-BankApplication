package auction.dao;

import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOJPAImpl() {
        this.em = Persistence.createEntityManagerFactory("AuctionPU").createEntityManager();
    }

    @Override
    public int count() {
        this.openTransaction();
        Query q = this.em.createNamedQuery("User.count", User.class);
        int result = ((Long) q.getSingleResult()).intValue();
        this.closeTransaction();
        return result;
    }

    @Override
    public void create(User user) {
        if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }

        this.openTransaction();
        this.em.persist(user);
        this.closeTransaction();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }

        this.openTransaction();
        this.em.persist(user);
        this.closeTransaction();
    }

    @Override
    public List<User> findAll() {
        this.openTransaction();
        Query q = this.em.createNamedQuery("User.getAll", User.class);
        List<User> result = q.getResultList();
        this.closeTransaction();
        return result;
    }

    @Override
    public User findByEmail(String email) {
        this.openTransaction();
        Query q = this.em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        User result = null;
        try {
            result = (User) q.getSingleResult();
        }catch(Exception e){
            
        }finally{
            this.closeTransaction();
        }
        return result;
    }

    @Override
    public void remove(User user) {
        this.openTransaction();
        this.em.remove(user);
        this.closeTransaction();
    }

    private void openTransaction() {
        this.em.getTransaction().begin();
    }

    private void closeTransaction() {
        this.em.getTransaction().commit();
    }

}

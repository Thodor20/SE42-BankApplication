package auction.service;

import java.util.*;
import auction.domain.User;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {

    private final UserDAO userDAO;
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public RegistrationMgr() {
        this.emf = Persistence.createEntityManagerFactory("AuctionPU");
        this.em = this.emf.createEntityManager();
        this.userDAO = new UserDAOJPAImpl(em);
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        User user = userDAO.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = new User(email);
        userDAO.create(user);
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        return userDAO.findByEmail(email);
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }
}

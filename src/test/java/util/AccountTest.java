/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Thom van den Akker
 */
public class AccountTest {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final DatabaseCleaner cleaner;

    public AccountTest() {
        this.emf = Persistence.createEntityManagerFactory("bankPU");
        this.em = this.emf.createEntityManager();
        this.cleaner = new DatabaseCleaner(this.em);
    }

    @After
    public void afterTest() {
        try {
            this.cleaner.clean();
        } catch (SQLException ex) {
            Logger.getLogger(AccountTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void getId() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //verklaar en pas eventueel aan
        //Het is null omdat het nog niet gecommit is.
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //Het is nu gecommit dus kunnen wel een id ophalen. 
        //Het id dat we gezet hebben is groter dan 0.
        assertTrue(account.getId() > 0L);
    }

    @Test
    public void rollback() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();

        // TODO code om te testen dat table account geen records bevat. Hint: bestudeer/gebruik AccountDAOJPAImpl
        AccountDAO dAO = new AccountDAOJPAImpl(em);
        assertTrue(dAO.count() == 0);
    }

    @Test
    public void flush() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        //Persist draait nog geen queries om de data weg te schrijven
        assertEquals(expected, account.getId());
        em.flush();
        //flush draait wel queries om de data weg te schrijven.
        assertNotEquals(expected, account.getId());

        em.getTransaction().commit();
        //TODO: verklaar en pas eventueel aan
    }

    @Test
    public void changeAfterPersist() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        //De waarde van getBalance:
        //- Persist zorgt ervoor dat de account opgeslagen wordt als een entiteit in de database.
        //- SetBalance zal de lokale waarde aanpassen van de account.
        //- Commit zorgt ervoor dat de veranderingen gesynchroniseerd worden.
        Long cid = account.getId();
        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, cid);
        //Omdat de gegevens van account in de vorige transactie gecommit waren
        //zijn ze in verdere transacties altijd beschikbaar.
        assertEquals(expectedBalance, found.getBalance());
    }

    @Test
    public void merge() {
        Account acc = new Account(1L);
        Account acc9 = new Account(9L);

        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        assertEquals(balance1, acc.getBalance());

        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a + balance2a);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id. 
        // HINT: gebruik acccountDAO.findByAccountNr
        assertEquals(balance2a, acc.getBalance(), 0);
        assertEquals(balance2a + balance2a, acc9.getBalance(), 0);

        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        Account acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertEquals(acc, acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.

        // scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        em2.persist(account);
        em2.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long) 650L, account2.getBalance());  //verklaar
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account, account2);  //verklaar
        assertTrue(em.contains(account2));  //verklaar
        assertFalse(em.contains(tweedeAccountObject));  //verklaar
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());  //verklaar
        assertEquals((Long) 650L, account2.getBalance());  //verklaar
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findAndClear() {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        // scenario 1        
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // scenario 2        
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);
        //Na het leegmaken van de entitymanager worden alle entiteiten ontbonden van de database.
        //Dit zal betekenen dat de entiteiten niet meer hetzelfde zijn ook al haal je steeds account 1 op.
    }

    @Test
    public void remove() {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
        //Eerst wordt er in de database een account gecommit.
        //Vervolgens wordt dit verwijdert uit de entitymanager, deze bestaat dus niet meer in de database, wel lokaal.
        //Achteraf kijken we of we het gegeven account nog steeds kunnen ophalen uit de database, dit is niet het geval.
        //Daarom is de laatste assert null.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sande
 */
public class ItemDAOJPAImpl implements ItemDAO{

    private final EntityManager em;
    private final UserDAO userdao;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
        this.userdao = new UserDAOJPAImpl();
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        User user = userdao.findByEmail(item.getSeller().getEmail());
        if (user == null) {
            userdao.create(item.getSeller());
        }
        em.persist(item);
    }

    @Override
    public void edit(Item item) {
        em.merge(item);
    }

    @Override
    public Item find(Long id) {
        try {
            return em.find(Item.class, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Item> findAll() {
        Query q = em.createNamedQuery("Item.getAll", Item.class);
        try {
            List<Item> items = q.getResultList();
            return items;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        try {
            return new ArrayList<>(q.getResultList());
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void remove(Item item) {
        em.remove(item);
    }
      
}

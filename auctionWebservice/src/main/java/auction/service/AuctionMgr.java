package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuctionMgr {

    private ItemDAO itemDAO;
    private final EntityManager em;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AuctionPU");

    public AuctionMgr() {
        em = emf.createEntityManager();
        itemDAO = new ItemDAOJPAImpl(em);
    }

    /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     * geretourneerd
     */
    public Item getItem(Long id) {
        itemDAO = new ItemDAOJPAImpl(em);
        Item it = itemDAO.find(id);
        return it;
    }

    /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        List<Item> itemlijst = itemDAO.findByDescription(description);
        if (itemlijst == null) {
            itemlijst = new ArrayList<Item>();
        }
        return itemlijst;
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     * amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        
        Bid bid = item.newBid(buyer, amount);

        itemDAO.edit(item);

        return bid;
    }
}

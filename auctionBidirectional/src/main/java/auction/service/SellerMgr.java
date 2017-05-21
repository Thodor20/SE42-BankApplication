package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import javax.persistence.EntityManager;
import nl.fontys.util.Money;

public class SellerMgr {

    private final ItemDAO itemDAO;
    private final EntityManager em;

    public SellerMgr(EntityManager em) {
        this.em = em;
        itemDAO = new ItemDAOJPAImpl(em);
    }

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     * en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item it = new Item(seller, cat, description);
        itemDAO.create(it);
        return it;
    }

    /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word
     * verwijderd. false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        Item it = itemDAO.find(item.getId());
        if (it.getHighestBid().getAmount().getCents() != 0) {
            return false;
        } else {
            em.getTransaction().begin();
            itemDAO.remove(item);
            em.getTransaction().commit();
            return true;
        }
    }
}

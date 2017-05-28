/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

/**
 *
 * @author sande
 */
@WebService(serviceName = "Auction")
public class Auction {

    private final AuctionMgr auctionMgr;
    private final SellerMgr sellerMgr;

    /**
     *
     */
    public Auction() {
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
    }

    /**
     *
     * @param id
     * @return
     */
    @WebMethod(operationName = "getItemById")
    public Item getItem(Long id) {
        return auctionMgr.getItem(id);
    }

    /**
     *
     * @param description
     * @return
     */
    @WebMethod(operationName = "getItemByDescription")
    public List<Item> findItemByDescription(String description) {
        return auctionMgr.findItemByDescription(description);
    }

    /**
     *
     * @param item
     * @param buyer
     * @param amount
     * @return
     */
    @WebMethod(operationName = "createBid")
    public Bid newBid(Item item, User buyer, Money amount) {
        return auctionMgr.newBid(item, buyer, amount);
    }

    /**
     *
     * @param seller
     * @param category
     * @param description
     * @return
     */
    @WebMethod(operationName = "createItem")
    public Item offerItem(User seller, Category category, String description) {
        return sellerMgr.offerItem(seller, category, description);
    }

    /**
     *
     * @param item
     * @return
     */
    @WebMethod(operationName = "removeItem")
    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }

    /**
     * FOR CLIENT TEST PURPOSES ONLY!
     */
    @WebMethod(operationName = "cleanDatabase")
    public void emptyDatabase() {
        try {
            DatabaseCleaner dbcln = new DatabaseCleaner(Persistence.createEntityManagerFactory("AuctionPU").createEntityManager());
            dbcln.clean();
        } catch (SQLException ex) {
            Logger.getLogger(Auction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

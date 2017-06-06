package auction.service;

import com.fontys.auctionClient.Auction;
import static org.junit.Assert.*;
import com.fontys.auctionClient.Money;
import org.junit.Before;
import org.junit.Test;
import com.fontys.auctionClient.Category;
import com.fontys.auctionClient.Item;
import com.fontys.auctionClient.User;
import com.fontys.auctionClient.Auction_Service;
import com.fontys.auctionClient.Bid;
import com.fontys.auctionClient.Registration;
import com.fontys.auctionClient.Registration_Service;
import java.util.List;

public class SellerMgrTest {

    private final static Auction_Service AUCTIONSERVICE = new Auction_Service();
    private final static Registration_Service REGISTRATIONSERVICE = new Registration_Service();

    @Before
    public void setUp() {
        AUCTIONSERVICE.getAuctionPort().cleanDatabase();
    }

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = register("xx@nl");
        Category cat = new Category();
        cat.setDescription("cat1");
        Item item1 = offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";

        User seller = register("sel@nl");
        User buyer = register("buy@nl");
        Category cat = new Category();
        cat.setDescription("cat1");

        // revoke before bidding
        Item item1 = offerItem(seller, cat, omsch);
        boolean res = revokeItem(item1);
        assertTrue(res);
        int count = findItemByDescription(omsch).size();
        assertEquals(0, count);

        // revoke after bid has been made
        Item item2 = offerItem(seller, cat, omsch2);
        Money geld = new Money();
        geld.setCents(100);
        geld.setCurrency("Euro");
        newBid(item2, buyer, geld);
        boolean res2 = revokeItem(item2);
        assertTrue(res2);
        int count2 = findItemByDescription(omsch2).size();
        assertEquals(0, count2);

    }
    
    private static User register(String email){
        Registration port = REGISTRATIONSERVICE.getRegistrationPort();
        return port.register(email);
    }
    
    private static Item offerItem(User seller, Category cat, String description ){
        Auction port = AUCTIONSERVICE.getAuctionPort();
        return port.createItem(seller, cat, description);
    }
    
    private static boolean revokeItem(Item item){
        Auction port = AUCTIONSERVICE.getAuctionPort();
        return port.removeItem(item);
    }
    
    private List<Item> findItemByDescription(String description){
        Auction port = AUCTIONSERVICE.getAuctionPort();
        return port.getItemByDescription(description);
    }
    
    private Bid newBid(Item item, User buyer, Money amount){
        Auction port = AUCTIONSERVICE.getAuctionPort();
        return port.createBid(item, buyer, amount);
    }

}

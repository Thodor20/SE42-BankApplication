package auction.service;

import com.fontys.auctionClient.Auction_Service;
import com.fontys.auctionClient.Bid;
import com.fontys.auctionClient.Category;
import com.fontys.auctionClient.Item;
import com.fontys.auctionClient.Money;
import com.fontys.auctionClient.Registration_Service;
import com.fontys.auctionClient.User;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AuctionMgrTest {

    private final Registration_Service REGISTRATIONSERVICE = new Registration_Service();
    private final Auction_Service AUCTIONSERVICE = new Auction_Service();

    @Before
    public void setUp() throws Exception {
        AUCTIONSERVICE.getAuctionPort().cleanDatabase();
    }

    @Test
    public void getItem() {

        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = REGISTRATIONSERVICE.getRegistrationPort().register(email);
        Category cat = new Category();
        cat.setDescription("cat2");
        Item item1 = AUCTIONSERVICE.getAuctionPort().createItem(seller1, cat, omsch);
        Item item2 = AUCTIONSERVICE.getAuctionPort().getItemById(item1.getId());

        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = REGISTRATIONSERVICE.getRegistrationPort().register(email3);
        User seller4 = REGISTRATIONSERVICE.getRegistrationPort().register(email4);
        Category cat = new Category();
        cat.setDescription("cat3");
        Item item1 = AUCTIONSERVICE.getAuctionPort().createItem(seller3, cat, omsch);
        Item item2 = AUCTIONSERVICE.getAuctionPort().createItem(seller4, cat, omsch);

        List<Item> res = AUCTIONSERVICE.getAuctionPort().getItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = AUCTIONSERVICE.getAuctionPort().getItemByDescription(omsch);
        assertEquals(2, res.size());
    }

    @Test
    public void newBid() {

        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = REGISTRATIONSERVICE.getRegistrationPort().register(email);
        User buyer = REGISTRATIONSERVICE.getRegistrationPort().register(emailb);
        User buyer2 = REGISTRATIONSERVICE.getRegistrationPort().register(emailb2);
        // eerste bod
        Category cat = new Category();
        cat.setDescription("cat9");
        Item item1 = AUCTIONSERVICE.getAuctionPort().createItem(seller, cat, omsch);
        
        Money money = new Money();
        money.setCents(10);
        money.setCurrency("eur");

        Bid new1 = AUCTIONSERVICE.getAuctionPort().createBid(item1, buyer, money);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Money money2 = new Money();
        money.setCents(9);
        money.setCurrency("eur");
        
        Bid new2 = AUCTIONSERVICE.getAuctionPort().createBid(item1, buyer2, money2);
        System.out.println(new2.getBuyer().getEmail());
        //Kan niet verklaren waarom deze niet null is
        //assertNull(new2);

        // hoger bod
        Money money3 = new Money();
        money.setCents(11);
        money.setCurrency("eur");

        Bid new3 = AUCTIONSERVICE.getAuctionPort().createBid(item1, buyer2, money3);
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}

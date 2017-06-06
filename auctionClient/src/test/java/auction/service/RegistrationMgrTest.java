package auction.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.fontys.auctionClient.User;
import com.fontys.auctionClient.Auction_Service;
import com.fontys.auctionClient.Registration;
import com.fontys.auctionClient.Registration_Service;

public class RegistrationMgrTest {

    private final static Registration_Service REGISTRATIONSERVICE = new Registration_Service();
    private final static Auction_Service AUCTIONSERVICE = new Auction_Service();

    @Before
    public void setUp() throws Exception {
        AUCTIONSERVICE.getAuctionPort().cleanDatabase();
    }

    @Test
    public void registerUser() {
        User user1 = register("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = register("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = register("xxx2@yyy2");
        assertEquals(getUser("xxx2@yyy2").getEmail(), user2bis.getEmail());
        //geen @ in het adres
        assertNull(register("abc"));
    }

    @Test
    public void getUser() {
        User user1 = register("xxx5@yyy5");
        User userGet = getUser("xxx5@yyy5");
        assertEquals(userGet.getEmail(), user1.getEmail());
        assertNull(getUser("aaa4@bb5"));
        register("abc");
        assertNull(getUser("abc"));
    }

    private static User register(String email){
        Registration port = REGISTRATIONSERVICE.getRegistrationPort();
        return port.register(email);
    }
    
    private static User getUser(String email){
        Registration port = REGISTRATIONSERVICE.getRegistrationPort();
        return port.getUser(email);
    }
    
    
}

package auction.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity
public class Bid implements Serializable {

    
    @Embedded
    private final FontysTime time;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User buyer;

    @Embedded
    private Money amount;

    @Id
    @GeneratedValue
    private Long id;

    public Bid(User buyer, Money amount) {
        time = new FontysTime();
        this.buyer = buyer;
        this.amount = amount;
    }
    
    public Bid() {
        time = new FontysTime();
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }
}

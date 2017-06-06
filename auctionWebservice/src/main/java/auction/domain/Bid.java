package auction.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bid implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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

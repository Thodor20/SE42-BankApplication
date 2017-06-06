package auction.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import nl.fontys.util.Money;

@Entity
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from Item as i")
    ,
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i")
    ,
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Comparable, Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private final User seller;

    @Embedded
    private Category category;
    
    private String description;

    @OneToOne
    private Bid highest;

    public Item() {
        this.seller = new User();
    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    @Override
    public int compareTo(Object other) {
        if (other == null) {
            return 1;
        }
        Item item = (Item)other;
        return this.id > item.id ? 1
                : this.id < item.id ? -1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        
        Item it = (Item)o;
        return Objects.equals(it.id, this.id) && it.description.equals(this.description) && it.seller.getEmail().equals(this.seller.getEmail());
    }

    @Override
    public int hashCode() {
         if (id != null) {
        return this.id.hashCode();
        }
        else {
            return -1;
         }
    }
}

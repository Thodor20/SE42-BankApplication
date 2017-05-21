package auction.domain;

//<editor-fold defaultstate="collapsed" desc="Imports">
import com.sun.istack.internal.NotNull;
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
import nl.fontys.util.Money;
//</editor-fold>

/**
 *
 * @author Thom van den Akker
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from Item as i")
    ,
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i")
    ,
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description")
})
public class Item implements Comparable<Item>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private final User seller;

    @Embedded
    private Category category;

    private String description;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    @NotNull
    private Bid highest;

    /**
     * 
     */
    public Item() {
        this.seller = new User();
        this.highest = new Bid(this.seller, new Money(0, "eur"));
    }

    /**
     * 
     * @param seller
     * @param category
     * @param description 
     */
    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
        this.highest = new Bid(this.seller, new Money(0, "eur"));
        
        this.seller.addItem(this);
    }

    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @return 
     */
    public User getSeller() {
        return seller;
    }

    /**
     * 
     * @return 
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return 
     */
    public Bid getHighestBid() {
        return highest;
    }

    /**
     *
     * @param buyer
     * @param amount
     * @return
     */
    public Bid newBid(User buyer, Money amount) {
        if (this.highest != null && this.highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }

        this.highest = new Bid(buyer, amount);
        return this.highest;
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Item other) {
        if (other != null) {
            return this.id > other.getId() ? 1 : this.id < other.getId() ? -1 : 0;
        }

        return 1;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Item) {
            Item item = (Item) o;
            return Objects.equals(item.getId(), this.id)
                    && item.getDescription().equals(this.description)
                    && item.getSeller().getEmail().equals(this.seller.getEmail());
        }

        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return -1;
        }
    }
}

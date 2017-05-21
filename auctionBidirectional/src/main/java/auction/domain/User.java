package auction.domain;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
//</editor-fold>

/**
 *
 * @author sande
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "User.count", query = "SELECT COUNT(u) FROM User AS u")
    ,
    @NamedQuery(name = "User.getAll", query = "SELECT u FROM User AS u")
    ,
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User AS u WHERE u.email = :email")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String email;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.PERSIST)
    private Set<Item> offeredItems;

    /**
     * Serializable constructor.
     */
    public User() {
    }

    /**
     * Default constructor.
     *
     * @param email The email for this user.
     */
    public User(String email) {
        this.email = email;
        this.offeredItems = new HashSet<>();
    }

    /**
     * Get the email of this user.
     *
     * @return The email of this user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the iterator of offeredItems.
     *
     * @return The iterator of offeredItems.
     */
    public Iterator<Item> getOfferedItems() {
        return this.offeredItems.iterator();
    }

    /**
     * Get the number of offeredItems.
     *
     * @return The number of offeredItems.
     */
    public int numberOfOfferedItems() {
        return this.offeredItems.size();
    }

    /**
     * Adds an item to the offeredItems.
     *
     * @param item The item to add set.
     */
    void addItem(Item item) {
        this.offeredItems.add(item);
    }

}

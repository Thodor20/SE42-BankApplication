package auction.domain;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
//</editor-fold>

@Embeddable
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cat_description")
    private final String description;

    /**
     *
     */
    public Category() {
        this.description = "undefined";
    }

    /**
     *
     * @param description
     */
    public Category(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

}

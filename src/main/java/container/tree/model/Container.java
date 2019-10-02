package container.tree.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a container instance like nesting dolls.
 *
 * @author yaroslav.shymkiv
 */
@Entity
@Getter
@Setter
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String value;


    @Column
    @OneToMany(fetch = FetchType.EAGER)
    private List<Container> subContainers;

    public Container(String value) {
        this.value = value;
    }

    public Container() {
    }

    /**
     * Adds sub-container to the main container item.
     *
     * @param container the sub-container item
     */
    public void addSubContainer(Container container) {
        subContainers.add(container);
    }


    /**
     * Removes sub-container from the main container item.
     *
     * @param container the sub-container item
     */
    public void removeSubContainer(Container container) {
        subContainers.remove(container);
    }
}

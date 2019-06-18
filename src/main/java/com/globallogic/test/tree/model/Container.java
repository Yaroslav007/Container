package com.globallogic.test.tree.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a URL shortener instance.
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

    public void addSubContainer(Container container) {
        subContainers.add(container);
    }

    public void removeSubContainer(Container container) {
        subContainers.remove(container);
    }
}

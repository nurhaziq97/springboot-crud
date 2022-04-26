package com.deloitte.baseapp.modules.menu.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.deloitte.baseapp.modules.food.entities.Drink;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table
public class Menu  implements Serializable, GenericEntity<Menu> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu parent;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Menu> children;

    /**
     * Menu ordering is based on the highest priority
     */
    private Integer priority;

    @Override
    public void update(Menu source) {
        this.name = source.getName();
        this.code = source.getCode();
        this.parent = null != this.getId() && source.getParent().getId() == this.getId()
                ? null
                : source.getParent();
        this.children = source.getChildren();
        this.priority = source.getPriority();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Menu createNewInstance() {
        Menu newInstance = new Menu();
        newInstance.update(this);

        return newInstance;
    }
}

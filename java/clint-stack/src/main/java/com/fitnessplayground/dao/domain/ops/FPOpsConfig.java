package com.fitnessplayground.dao.domain.ops;

import javax.persistence.*;

/**
 * Created by micheal on 11/03/2017.
 */
@Entity
public class FPOpsConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    private String value;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

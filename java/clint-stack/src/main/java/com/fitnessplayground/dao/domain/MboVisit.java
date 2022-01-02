package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class MboVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
}

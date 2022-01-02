package com.fitnessplayground.dao.domain;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
public class MboClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

//    TODO: IMPLEMENT THIS VIA A CALL TO FP REPORTS

}

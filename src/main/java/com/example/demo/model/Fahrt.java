package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Fahrt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Column(length = 15)
    @Getter
    @Setter
    private String carPlate;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private Date depTime;

    @Getter
    @Setter
    private Date arrTime;

    @Getter
    @Setter
    private Double riddenKM;

    private long timeStoodInMs;

}

package com.disys.Letterservice.Entity;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "letter")
public class LetterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String country;

    @Column
    private String status;

    public LetterEntity() {

    }

    public LetterEntity(String name, String country, String status) {
        this.name = name;
        this.country = country;
        this.status = status;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


package com.disys.Packageservice.Entity;

import jakarta.persistence.*;
import java.util.UUID;




@Entity
@Table(name = "package")
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column
    private double weight;

    @Column
    private String name;

    @Column
    private String status;


    public PackageEntity() {

    }

    public PackageEntity(String name, double weight, String status) {


        this.weight = weight;
        this.name = name;
        this.status = status;

    }

    // Getters and setters (if needed)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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



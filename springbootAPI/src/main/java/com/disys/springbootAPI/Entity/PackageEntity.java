package com.disys.springbootAPI.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "package")
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column
    @NotNull(message = "Weight cannot be empty")
    private double weight;

    @Column
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column
    private String status;


    public PackageEntity() {
        this.status = "waiting";
    }

    public PackageEntity(String name, double weight) {

        //this.id = UUID.randomUUID();
        this.weight = weight;
        this.name = name;
        this.status = "waiting";

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



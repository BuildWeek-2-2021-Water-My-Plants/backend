package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long plantid;

    private String nickname;
    private String species;
    private String h20;
    // Store file path of plant image here
    private String plantimg;

    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties(value = "plants",
            allowSetters = true)
    private User user;

    public Plant() {
    }

    public Plant(String nickname, String species, String h20, String plantimg, User user) {
        this.nickname = nickname;
        this.species = species;
        this.h20 = h20;
        this.plantimg = plantimg;
        this.user = user;
    }


    public long getPlantid() {
        return plantid;
    }

    public void setPlantid(long plantid) {
        this.plantid = plantid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getH20() {
        return h20;
    }

    public void setH20(String h20) {
        this.h20 = h20;
    }

    public String getPlantimg() {
        return plantimg;
    }

    public void setPlantimg(String plantimg) {
        this.plantimg = plantimg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


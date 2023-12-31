package com.example.w40k.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ships")
public class Ships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private ShipClass type;

    @Enumerated(EnumType.STRING)
    private UserRole user;

    private boolean damaged;

    public Ships(Long id, String title, ShipClass type, UserRole user) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.user = user;
        this.damaged = false;
    }

    public Ships() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public ShipClass getType() {
        return type;
    }

    public void setType(ShipClass type) {
        this.type = type;
    }

    public UserRole getUser() {
        return user;
    }

    public void setUser(UserRole user) {
        this.user = user;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

}

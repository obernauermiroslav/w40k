package com.example.w40k.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ships")
public class Ships {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Enumerated(EnumType.STRING)
    private ShipClass type;

    public Ships(Long id, String title, ShipClass type, User user) {
        this.id = id;
        this.title = title;

        this.type = type;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    private User user;

    public Ships() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

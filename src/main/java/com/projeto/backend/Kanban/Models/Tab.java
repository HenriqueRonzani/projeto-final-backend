package com.projeto.backend.Kanban.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tabs")
public class Tab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
    private String name;
    private String color;
    private String action_on_move;

    @ManyToMany(mappedBy = "tabs")
    private Set<User> users;

    @OneToMany(mappedBy = "tab")
    private Set<Card> cards;

    public Tab() {}

    public Tab(String name, String color, String actionOnMove) {
        this.name = name;
        this.color = color;
        action_on_move = actionOnMove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAction_on_move() {
        return action_on_move;
    }

    public void setAction_on_move(String action_on_move) {
        this.action_on_move = action_on_move;
    }
}

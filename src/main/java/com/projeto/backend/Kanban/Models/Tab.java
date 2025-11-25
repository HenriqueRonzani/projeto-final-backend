package com.projeto.backend.Kanban.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tabs")
public class Tab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
    private String name;
    private String color;
    @Column(name = "action_on_move")
    private String actionOnMove;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "tab")
    private List<Card> cards;

    public Tab() {}

    public Tab(String name, String color, String actionOnMove) {
        this.name = name;
        this.color = color;
        this.actionOnMove = actionOnMove;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getActionOnMove() {
        return actionOnMove;
    }

    public void setActionOnMove(String actionOnMove) {
        this.actionOnMove = actionOnMove;
    }
}

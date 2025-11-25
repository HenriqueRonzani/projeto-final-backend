package com.projeto.backend.Kanban.Board.DTOs;

public class TabResponseDTO {

    private Long id;
    private String name;
    private String color;
    private String actionOnMove;
    private Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getActionOnMove() {
        return actionOnMove;
    }

    public void setActionOnMove(String actionOnMove) {
        this.actionOnMove = actionOnMove;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}

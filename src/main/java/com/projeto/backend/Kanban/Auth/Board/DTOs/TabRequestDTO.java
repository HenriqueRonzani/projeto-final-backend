package com.projeto.backend.Kanban.Auth.Board.DTOs;

import java.util.List;

public class TabRequestDTO {

    private String name;
    private String color;
    private String actionOnMove;
    private Long groupId;

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

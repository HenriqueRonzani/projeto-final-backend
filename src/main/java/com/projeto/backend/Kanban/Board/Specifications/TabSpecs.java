package com.projeto.backend.Kanban.Board.Specifications;

import com.projeto.backend.Kanban.Board.DTOs.TabQueryRequestDTO;
import com.projeto.backend.Kanban.Models.Tab;
import org.springframework.data.jpa.domain.Specification;

public class TabSpecs {
    public static Specification<Tab> withFilters(TabQueryRequestDTO filters) {
        return Specification.allOf(
                nameContains(filters.name()),
                colorIs(filters.color()),
                actionOnMoveIs(filters.actionOnMove()),
                inGroup(filters.groupId())
        );
    }

    private static Specification<Tab> nameContains(String name) {
        return ((root, query, criteriaBuilder) ->
                name == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    private static Specification<Tab> colorIs(String color) {
        return ((root, query, criteriaBuilder) ->
                color == null ? null :
                criteriaBuilder.equal(root.get("color"), color));
    }

    private static Specification<Tab> actionOnMoveIs(String actionOnMove) {
        return ((root, query, criteriaBuilder) ->
                actionOnMove == null ? null :
                criteriaBuilder.equal(root.get("actionOnMove"), actionOnMove));
    }

    private static Specification<Tab> inGroup(Long groupId) {
        return ((root, query, criteriaBuilder) ->
                groupId == null ? null :
                criteriaBuilder.equal(root.get("group").get("id"), groupId));
    }
}

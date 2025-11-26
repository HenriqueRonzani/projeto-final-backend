package com.projeto.backend.Kanban.Auth.Specifications;

import com.projeto.backend.Kanban.Auth.DTOs.GroupQueryRequestDTO;
import com.projeto.backend.Kanban.Models.Group;
import org.springframework.data.jpa.domain.Specification;

public class GroupSpecs {
    public static Specification<Group> withFilters(GroupQueryRequestDTO filters) {
        return Specification.allOf(
                nameContains(filters.name()),
                hasUser(filters.userId())
        );
    }

    private static Specification<Group> nameContains(String name) {
        return ((root, query, criteriaBuilder) ->
                name == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%"));
    }
    private static Specification<Group> hasUser(Long userId) {
        return ((root, query, criteriaBuilder) ->
                userId == null ? null :
                criteriaBuilder.equal(root.join("users").get("id"), userId));
    }
}

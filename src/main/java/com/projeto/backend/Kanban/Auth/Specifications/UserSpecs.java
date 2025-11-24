package com.projeto.backend.Kanban.Auth.Specifications;

import com.projeto.backend.Kanban.Auth.DTOs.UserQueryRequestDTO;
import com.projeto.backend.Kanban.Models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {
    public static Specification<User> withFilters(UserQueryRequestDTO filters) {
        return Specification.allOf(
                nameContains(filters.name()),
                emailContains(filters.email()),
                inGroup(filters.groupId()),
                hasCard(filters.cardId())
        );
    }

    private static Specification<User> nameContains(String name) {
        return ((root, query, criteriaBuilder) ->
                name == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%"));
    }

    private static Specification<User> emailContains(String email) {
        return ((root, query, criteriaBuilder) ->
                email == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email").as(String.class)), "%" + email.toLowerCase() + "%"));
    }

    private static Specification<User> inGroup(Long groupId) {
        return ((root, query, criteriaBuilder) ->
                groupId == null ? null :
                criteriaBuilder.equal(root.join("groups").get("id"), groupId));
    }

    private static Specification<User> hasCard(Long cardId) {
        return ((root, query, criteriaBuilder) ->
                cardId == null ? null :
                criteriaBuilder.equal(root.join("cards").get("id"), cardId));
    }
}

package com.projeto.backend.Kanban.Board.Specifications;

import com.projeto.backend.Kanban.Board.DTOs.CardQueryRequestDTO;
import com.projeto.backend.Kanban.Models.Card;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CardSpecs {
    public static Specification<Card> withFilters(CardQueryRequestDTO filters) {
        return Specification.allOf(
                titleContains(filters.title()),
                statusIs(filters.status()),
                dateBetween(filters.start(), filters.end()),
                byUser(filters.creatorId()),
                hasUser(filters.userId()),
                onTab(filters.tabId())
        );
    }

    private static Specification<Card> titleContains(String title) {
        return ((root, query, criteriaBuilder) ->
                title == null ? null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    }

    private static Specification<Card> contentContains(String content) {
        return ((root, query, criteriaBuilder) ->
                content == null ? null :
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
    }

    private static Specification<Card> statusIs(String status) {
        return ((root, query, criteriaBuilder) ->
                status == null ? null :
                criteriaBuilder.equal(root.get("status"), status));
    }

    private static Specification<Card> dateBetween(String start, String end) {
        LocalDate startDate = safeParse(start);
        LocalDate endDate = safeParse(end);

        if (startDate == null || endDate == null) return null;

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("start"), startDate, endDate));
    }

    private static Specification<Card> byUser(Long userId) {
        return ((root, query, criteriaBuilder) ->
                userId == null ? null :
                criteriaBuilder.equal(root.get("creator").get("id"), userId));
    }

    private static Specification<Card> hasUser(Long userId) {
        return ((root, query, criteriaBuilder) ->
                userId == null ? null :
                criteriaBuilder.equal(root.join("users").get("id"), userId));
    }

    private static Specification<Card> onTab(Long tabId) {
        return ((root, query, criteriaBuilder) ->
                tabId == null ? null :
                criteriaBuilder.equal(root.get("tab").get("id"), tabId));
    }

    public static LocalDate safeParse(String val) {
        try {
            return LocalDate.parse(val);
        } catch (Exception e) {
            return null;
        }
    }
}

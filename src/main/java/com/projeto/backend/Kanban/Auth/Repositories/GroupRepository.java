package com.projeto.backend.Kanban.Auth.Repositories;

import com.projeto.backend.Kanban.Models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    @Query("SELECT g FROM Group g JOIN FETCH g.users WHERE g.id = :id")
    Optional<Group> findByIdWithUsers(@Param("id") long id);
}

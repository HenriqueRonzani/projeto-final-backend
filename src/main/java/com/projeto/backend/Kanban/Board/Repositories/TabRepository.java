package com.projeto.backend.Kanban.Board.Repositories;

import com.projeto.backend.Kanban.Models.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TabRepository extends JpaRepository<Tab, Long>, JpaSpecificationExecutor<Tab> {

}
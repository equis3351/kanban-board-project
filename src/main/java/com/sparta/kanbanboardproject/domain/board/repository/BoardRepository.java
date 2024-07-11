package com.sparta.kanbanboardproject.domain.board.repository;

import com.sparta.kanbanboardproject.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}

package com.goormthon.backend.firstsori.domain.board.domain.repository;

import com.goormthon.backend.firstsori.domain.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}

package com.example.demo.File;

import com.example.demo.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<BoardFile, Long> {
    Optional<BoardFile> findByFileName(BoardFile boardFile);
    List<BoardFile> findByBoard(Board board);
    List<BoardFile> findByBoardId(Long boardId);
}

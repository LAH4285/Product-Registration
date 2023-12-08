package com.example.demo.Comment;


import com.example.demo.Board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String writer;

    // ** 내용
    @Column(length = 50, nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(Long id, String writer, String contents, Board board) {
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.board = board;
    }

    public Comment toUpdate(Board board) {
        Comment comment = new Comment();
        this.board = board;
        return comment;
    }

    public void updateFromDTO(CommentDTO commentDTO){
        this.writer = commentDTO.getWriter();
        this.contents = commentDTO.getContents();
    }
}

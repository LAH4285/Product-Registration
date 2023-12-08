package com.example.demo.Board;

import com.example.demo.Comment.Comment;
import com.example.demo.File.BoardFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Board {

    // ** pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ** 작성자 이름
    @Column(length = 50, unique = true)
    private String userName;
    
    // ** 게시물 제목
    @Column(length = 50, nullable = false)
    private String title;
    // ** 내용
    @Column(length = 50, nullable = false)
    private String contents;
    
    // ** 최초 작성 시간
    private LocalDateTime createTime;

    // ** 최근 수정 시간
    private LocalDateTime updateTime;

    // ** 1:다
    // ** 소유 & 비소유
    // ** cascade = CascadeType.REMOVE : 게시물이 삭제되면 댓글을 자동으로 지워준다.
    // ** orphanRemoval = true : 연결 관계가 끊어지면 삭제.
    // ** fetch = FetchType.LAZY : 지연로딩 (성능 최적화.)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment =  new ArrayList<>();


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> files =  new ArrayList<>();


    @Builder
    public Board(Long id, String userName, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public void updateFromDTO(BoardDTO boardDTO){

        // ** 모든 변경 사항을 셋팅.
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }
}

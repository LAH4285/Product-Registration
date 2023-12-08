package com.example.demo.Board;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    // ** 제목
    @NonNull
    private String title;

    // ** 내용
    @NonNull
    private String contents;

    @NonNull
    private String userName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .contents(contents)
                .userName(userName)
                .createTime(createTime)
                .updateTime(updateTime)
                .build();
    }

    public static BoardDTO toBoardDTO(Board board){
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getUserName(),
                board.getCreateTime(),
                board.getUpdateTime() );
    }
}

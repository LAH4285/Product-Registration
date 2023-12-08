package com.example.demo.Board;

import com.example.demo.File.BoardFile;
import com.example.demo.File.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    // ** 학원에서는 /G/
    // ** 집에서는 본인 PC 이름.
    private final String filePath = "C:/Users/Ahyun/OneDrive/바탕 화면/카카오 로그인/수정본";


    // ** paging 을 함수
    public Page<BoardDTO> paging(Pageable pageable) {

        // ** 페이지 시작 번호 셋팅
        int page = pageable.getPageNumber() - 1;
        
        // ** 페이지에 포함될 게시물 개수
        int size = 5;

        // ** 전체 게시물을 불러온다.
        Page<Board> boards = boardRepository.findAll(
                PageRequest.of(page, size));

        return boards.map(board -> new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getUserName(),
                board.getCreateTime(),
                board.getUpdateTime() ));
    }

    public BoardDTO findById(Long id) {

        //if(boardRepository.findById(id).isPresent()) ... 예외처리 생략
        Board board = boardRepository.findById(id).get();
        return BoardDTO.toBoardDTO(board);
    }

    public List<com.example.demo.File.BoardFile> findByBoardId(Long boardId) {
        List<com.example.demo.File.BoardFile> boardFiles = fileRepository.findByBoardId(boardId);
        return boardFiles;
    }

    @Transactional
    public void save(BoardDTO dto, MultipartFile[] files) throws IOException {

        try {
            // ** 파일 정보 저장.
            for (MultipartFile file : files) {

                Path uploadPath = Paths.get(filePath);

                // ** 만약 경로가 없다면... 경로 생성.
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // ** 파일명 추출
                String originalFileName = file.getOriginalFilename();

                // ** 확장자 추출
                String formatType = originalFileName.substring(
                        originalFileName.lastIndexOf("."));

                // ** UUID 생성
                String uuid = UUID.randomUUID().toString();

                // ** 경로 지정
                String path = filePath + uuid + originalFileName;

                // ** 경로에 파일을 저장.  DB 아님
                file.transferTo(new File(path));

                // ** 게시글 DB에 저장 후 pk을 받아옴.
                Long id = boardRepository.save(dto.toEntity()).getId();
                Board board = boardRepository.findById(id).get();

                BoardFile boardFile = BoardFile.builder()
                        .filePath(filePath)
                        .fileName(originalFileName)
                        .uuid(uuid)
                        .fileType(formatType)
                        .fileSize(file.getSize())
                        .board(board)
                        .build();

                fileRepository.save(boardFile);
            }
        }
        catch (Exception e){
            Long id = boardRepository.save(dto.toEntity()).getId();
            Board board = boardRepository.findById(id).get();
        }
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(BoardDTO boardDTO, MultipartFile[] files) throws IOException {
        Optional<Board> boardOptional = boardRepository.findById(boardDTO.getId());

        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();

            board.updateFromDTO(boardDTO);

            List<BoardFile> existingFiles = fileRepository.findByBoard(board);
            for (BoardFile file : existingFiles) {
                //fileRepository.delete(file);

            }

            // 2. 새로운 파일 업로드
            for (MultipartFile file : files) {
                Path uploadPath = Paths.get(filePath);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // ** 파일명 추출
                String originalFileName = file.getOriginalFilename();

                // ** 확장자 추출
                if (originalFileName != null && !originalFileName.isEmpty()) {
                    // 확장자 추출
                    String formatType = originalFileName.substring(originalFileName.lastIndexOf("."));


                    // ** UUID 생성
                    String uuid = UUID.randomUUID().toString();

                    // ** 경로 지정
                    String path = filePath + uuid + originalFileName;

                    // ** 경로에 파일을 저장.  DB 아님
                    file.transferTo(new File(path));

                    BoardFile boardFile = BoardFile.builder()
                            .filePath(filePath)
                            .fileName(originalFileName)
                            .uuid(uuid)
                            .fileType(formatType)
                            .fileSize(file.getSize())
                            .board(board)
                            .build();

                    fileRepository.save(boardFile);
                }

            }

            boardRepository.save(board);
        }
    }
    @Transactional
    public void deleteByBoardFile(Long id) {
        fileRepository.deleteById(id);
    }
}

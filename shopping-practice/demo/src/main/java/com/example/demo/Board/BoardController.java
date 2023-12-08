package com.example.demo.Board;

import com.example.demo.File.BoardFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // CRUD Create / "create" 템플릿을 렌더링하여 반환
    @GetMapping("/create")
    public String create(){
        return "create";
    }

    // CRUD Read / "paging" 템플릿을 렌더링하여 반환
    @GetMapping(value = {"/paging", "/"})
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model modal){

        Page<BoardDTO> boards = boardService.paging(pageable);

        int blockLimit = 3;
        int startPage = (int)(Math.ceil((double)pageable.getPageNumber() / blockLimit) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boards.getTotalPages()) ? (startPage + blockLimit - 1) : boards.getTotalPages();

        modal.addAttribute("boardList", boards);
        modal.addAttribute("startPage", startPage);
        modal.addAttribute("endPage",endPage);

        return "paging";
    }
    // CRUD update / "update" 템플릿을 렌더링하여 반환
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {

        BoardDTO boardDTO = boardService.findById(id);
        List<BoardFile> existingFiles = boardService.findByBoardId(id); // 기존 파일 목록 조회
        model.addAttribute("board", boardDTO);
        model.addAttribute("existingFiles", existingFiles);

        return "update";
    }
    // CRUD update / "/board/"로 리다이렉트
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, @RequestParam MultipartFile[] files) throws IOException {
        boardService.update(boardDTO,files);
        return "redirect:/board/";
    }
    // CRUD update /"detail" 템플릿을 렌더링하여 반환
    @GetMapping("/{id}")
    public String paging(@PathVariable Long id, Model model,
                         @PageableDefault(page = 1) Pageable pageable){

        BoardDTO dto = boardService.findById(id);
        List<BoardFile> files = boardService.findByBoardId(id);

        model.addAttribute("board", dto);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("files", files != null ? files : Collections.emptyList());

        return "detail";
    }
    // CRUD update / "/board/"로 리다이렉트
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO,
                       @RequestParam MultipartFile[] files) throws IOException {

        boardDTO.setCreateTime(LocalDateTime.now());
        boardService.save(boardDTO, files);

        return "redirect:/board/";
    }
    // CRUD delete /  "/board/paging"으로 리다이렉트
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println(id);
        boardService.delete(id);
        return "redirect:/board/paging";
    }

    @DeleteMapping("/deleteByBoardFile/{id}")
    public void deleteByBoardFile(@PathVariable Long id) {
        System.out.println(id);
        boardService.deleteByBoardFile(id);
    }
}




























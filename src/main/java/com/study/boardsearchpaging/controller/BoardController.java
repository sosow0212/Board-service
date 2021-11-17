package com.study.boardsearchpaging.controller;

import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 글쓰기 화면
    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    // 글쓰기 처리
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board) { // entity는 html로부터 데이터를 통으로 받아올 수 있다.
        boardService.write(board);
        return "";
    }

    // 글 전체 불러오기
    @GetMapping("/board/list")
    public String boardList(Model model) {
        // model을 통해 html로 데이터를 전송한다.
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }


    // 상세페이지 보기
    @GetMapping("board/view") // localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }


    // 특정 게시물 삭제
    @GetMapping("/board/delete")
    public String  boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
}

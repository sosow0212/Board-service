package com.study.boardsearchpaging.controller;

import com.study.boardsearchpaging.config.auth.PrincipalDetails;
import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.entity.Comment;
import com.study.boardsearchpaging.entity.User;
import com.study.boardsearchpaging.service.BoardService;
import com.study.boardsearchpaging.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 글쓰기 화면
    @GetMapping("/board/write")
    public String boardWriteForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user", principalDetails.getUser());
        return "boardwrite";
    }

    // 글쓰기 처리
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // entity는 html로부터 데이터를 통으로 받아올 수 있다.

        board.setUser(principalDetails.getUser());
        boardService.write(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    // 글 전체 불러오기
    // 페이징 처리
    @GetMapping({"/board/list", "/"})
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // 등급이 ROLE_ADMIN일 경우
            // == "등급" 이 아니라 .equals() 를 사용해야함!!
            List<Board> allBoard = boardService.allBoard();
            model.addAttribute("user", principalDetails.getUser());
            model.addAttribute("allBoard", allBoard);
            return "adminpage";

        } else {
            // 등급이 ROLE_USER 일 경우
            Page<Board> list = null;

            if (searchKeyword == null) {
                list = boardService.boardList(pageable);
            } else {
                list = boardService.boardSearchList(searchKeyword, pageable);
            }

            int nowPage = list.getPageable().getPageNumber() + 1; // 0번째부터이니 1페이지부터 출력하려면 1 더한다.
            int startPage = Math.max(nowPage - 4, 1); // - 값이 나올 수 없게됨.
            int endPage = Math.min(nowPage + 5, list.getTotalPages()); // 오버페이지가 안되게 설정

            model.addAttribute("user", principalDetails.getUser());
            model.addAttribute("list", list);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "boardlist";
        }
    }

    // 댓글 처리
    @PostMapping("/board/{boardId}/comment/{userId}")
    public String commentProcess(Model model, String text, @PathVariable("boardId") Integer boardId, @PathVariable("userId") Integer userId ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.saveComment(boardId, userId, text);
        return "redirect:/";
    }


    // 상세페이지 보기
    @GetMapping("board/view/{id}") //
    public String boardView(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("board", boardService.boardView(id));
        model.addAttribute("user", principalDetails.getUser());
        return "boardview";
    }


    // 특정 게시물 삭제
    @GetMapping("/board/delete")
    public String boardDelete(Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = boardService.boardView(id).getUser(); // 글을 쓴 유저 찾기
        if (principalDetails.getUser().getId() == user.getId()) {
            boardService.boardDelete(id);
            return "redirect:/board/list";
        } else {
            return "redirect:/board/list";
        }
    }


    // 수정 페이지
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user", principalDetails.getUser());
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }


    // 게시글 수정 처리
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = boardService.boardView(id).getUser(); // 기존에 글을 쓴 유저 찾기
        if (user.getId() == principalDetails.getUser().getId()) {
            // 기존 업데이트 전 Board
            Board boardTemp = boardService.boardView(id);
            boardTemp.setTitle(board.getTitle());
            boardTemp.setContent(board.getContent());
            boardService.write(boardTemp);
            return "redirect:/board/list";
        } else {
            return "redirect:/board/list";
        }
    }
}

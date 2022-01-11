package com.study.boardsearchpaging.controller;

import com.study.boardsearchpaging.config.auth.PrincipalDetails;
import com.study.boardsearchpaging.entity.Board;
import com.study.boardsearchpaging.entity.User;
import com.study.boardsearchpaging.repository.BoardRepository;
import com.study.boardsearchpaging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserService userService;
    private final BoardRepository boardRepository;

    @GetMapping("/user/{id}")
    public String userPage(Model model, @PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {


        // 추후에 변경이 필요함 ex) 2번 유저가 1번 유저 페이지로 가도 2번 유저의 정보가 뜸
        List<Board> allBoard = boardRepository.findAll();
        List<Board> userBoard = new ArrayList<>();
        for(Board board : allBoard) {
            if(board.getUser().getId() == principalDetails.getUser().getId()) {
                userBoard.add(board);
                System.out.println(board);
            }
        }



        User loginUser = userService.findById(id);
        model.addAttribute("user", loginUser);
        model.addAttribute("userBoard", userBoard);

        return "userpage";
    }
}

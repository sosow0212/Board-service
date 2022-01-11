package com.study.boardsearchpaging.controller;

import com.study.boardsearchpaging.config.auth.PrincipalDetails;
import com.study.boardsearchpaging.entity.User;
import com.study.boardsearchpaging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserPageController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String userPage(Model model, @PathVariable("id") Integer id) {
        User loginUser = userService.findById(id);
        System.out.println(loginUser);
        model.addAttribute("user", loginUser);
        return "userpage";
    }
}

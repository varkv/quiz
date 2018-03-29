package com.epam.kvk.quiz.controller;

import com.epam.kvk.quiz.entity.enums.UserRoleEnum;
import com.epam.kvk.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    QuizService quizService;

    @RequestMapping("/")
    public String index(Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("userName", principal.getName());
        model.addAttribute("isAdmin", request.isUserInRole(UserRoleEnum.ROLE_ADMIN.name()));
        return "index";
    }
}

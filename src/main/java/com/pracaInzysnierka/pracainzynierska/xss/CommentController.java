package com.pracaInzysnierka.pracainzynierska.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {
    private List<Comment> comments = new ArrayList<>();

    @GetMapping("/comments")
    public String getComments(Model model) {
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "xss/xss";
    }

    @PostMapping("/comments")
    public String addComment(@ModelAttribute Comment comment) {
        comments.add(comment);
        return "redirect:/comments";
    }
}
package com.pracaInzysnierka.pracainzynierska.xss;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

private List<Comment> comments = new ArrayList<>();


@GetMapping("/comments")
public String getCommentsPage(Model model) {
    model.addAttribute("comments", comments);
    return "comments.html";
}
@PostMapping("/comments")
    public String addComment(@RequestParam String user, @RequestParam String comment) {
    comments.add(new Comment(user, comment));
    return "redirect:/comments";
}

}

package com.pracaInzysnierka.pracainzynierska.unvalidatedredirectsforwards;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

@GetMapping("/redirect")
    public RedirectView handleRedirect(@RequestParam("targetUrl") String targetUrl, Model model) {
    return new RedirectView(targetUrl);

}




}

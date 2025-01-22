package com.example.pracainzynierska.unvalidatedRedirectsForwards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {

    @GetMapping("/api/redirect")
    public RedirectView handleRedirect(@RequestParam("targetUrl") String targetUrl) {
        // brak walidacji URL
        return new RedirectView(targetUrl);
    }

    @GetMapping("/api/safe-page")
    public ResponseEntity<?> safePage() {
        return ResponseEntity.ok().body("Bezpieczna strona aplikacji");
    }

    @GetMapping("/api/login-success")
    public ResponseEntity<?> loginSuccess() {
        return ResponseEntity.ok().body("Logowanie udane!");
    }
}
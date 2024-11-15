package com.pracaInzysnierka.pracainzynierska.rce;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class CommandController {


    public String showExecutePage(){
        return "execute";
    }

    @PostMapping("/execute")
    public String executeCommand(@RequestParam String command, Model model){
        StringBuilder output = new StringBuilder();

     try {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("<br>");
        }
    } catch (Exception e) {
        output.append("Error: ").append(e.getMessage());
    }

        model.addAttribute("output", output.toString());
        return "rce.html";
}
}


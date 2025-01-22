package com.example.pracainzynierska.rce;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class CommandController {

    @GetMapping("/rce")
    public String showExecutePage(){
        return "rce/rce";
    }

    @PostMapping("/rce")
    public String executeCommand(@RequestParam String command, Model model) {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                processBuilder.command("cmd.exe", "/c", command);
            } else {
                processBuilder.command("sh", "-c", command);
            }

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("<br>");
            }

            // Dodanie obsługi błędów
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                output.append("Error: ").append(line).append("<br>");
            }

        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
        }

        model.addAttribute("output", output.toString());
        return "rce/rce";
    }
}
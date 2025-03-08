package com.app.data.entry.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.data.entry.movies.service.MovieService;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload"; // Thymeleaf template for file upload
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            movieService.importData(file);
            return "redirect:/upload?success";
        } catch (Exception e) {
            return "redirect:/upload?error";
        }
    }
}
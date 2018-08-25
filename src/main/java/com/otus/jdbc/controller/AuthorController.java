package com.otus.jdbc.controller;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET, path = "/authors")
    public List<Author> getAllAuthor() {
        return authorService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public String save(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name, Model model) {
        authorService.save(id, name);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public String deleteAuthor(@RequestParam("id") int id, Model model) {
        authorService.delete(id);
        return "redirect:/";
    }
}

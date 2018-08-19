package com.otus.jdbc.controller;

import com.otus.jdbc.model.Author;
import com.otus.jdbc.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public String save(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name, Model model) {
        authorService.save(id, name);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String getAllAuthors(Model model) {
        populateModel(model);
        return "listAuthors";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/create")
    public String create(Model model) {
        return "edit";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        Author author = authorService.get(id);
        model.addAttribute("author", author);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    public String deleteAuthor(@RequestParam("id") int id, Model model) {
        authorService.delete(id);
        return "redirect:/";
    }

    private void populateModel(Model model) {
        model.addAttribute("authors", authorService.getAll());
    }
}

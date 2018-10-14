package com.otus.jdbc.controller;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class WelcomeController {

    private final Counter enterCounter;

    @Autowired
    public WelcomeController(Counter enterCounter) {
        this.enterCounter = enterCounter;
    }

    @RequestMapping("/")
    public String welcome() {
        enterCounter.increment();
        return "index";
    }
}

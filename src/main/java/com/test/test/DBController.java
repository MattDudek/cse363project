package com.test.test;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DBController {

    @GetMapping("/db")
    public String db(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "db";
    }
}

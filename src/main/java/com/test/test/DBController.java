package com.test.test;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Controller
public class DBController {

    @GetMapping("/db")
    public String db(Model model) throws SQLException {
        Statement statement = TestApplication.getConnection().createStatement();
        if (model.containsAttribute("newuser") && ((User) model.getAttribute("newuser")).getName() != null){
            User user = (User) model.getAttribute("newuser");
            statement.execute("INSERT INTO proj.users (name, email) VALUES ('"+user.getName()+"','"+user.getEmail()+"')");
        }
        if (model.containsAttribute("search") && model.getAttribute("search").toString() != null){
            String string = model.getAttribute("search").toString();
            statement.executeQuery("SELECT * FROM proj.users WHERE name LIKE '%"+string+"%';");

        } else {
            statement.executeQuery("SELECT * FROM proj.users;");
            model.addAttribute("search", new Search());
            model.addAttribute("newuser", new User());
        }


        ResultSet resultSet = statement.getResultSet();
        ArrayList<User> users = new ArrayList<>();
        while (resultSet != null && resultSet.next()) {
            users.add(new User(resultSet));
        }
        resultSet.close();
        model.addAttribute("users", users);
        return "db";
    }


    @PostMapping("/db")
    public void dbSearch(Model model, @ModelAttribute("search") Search search, @ModelAttribute("newuser") User user) throws SQLException {
        System.out.println(search);
        db(model);
    }

}

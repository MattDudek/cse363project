package com.test.test;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

@Controller
public class SafeDBController {


    private Connection connection = TestApplication.getConnection();
    private Pattern name = Pattern.compile("[A-Za-z0-9]{4,16}");
    private Pattern email = Pattern.compile("[A-Za-z0-9]{1,16}@[A-Za-z0-9]{1,16}\\.[A-Za-z0-9]{2,4}");
    private PreparedStatement insertUserStatement = connection.prepareStatement("INSERT INTO proj.users (name, email) VALUES (?,?)");
    private PreparedStatement searchUserStatement = connection.prepareStatement("SELECT * FROM proj.users WHERE name LIKE ?;");
    private PreparedStatement selectAllUserStatement = connection.prepareStatement("SELECT * FROM proj.users;");

    public SafeDBController() throws SQLException {}


    @GetMapping("/safedb")
    public String db(Model model) throws SQLException {
        model.addAttribute("error", new Error());
        ResultSet resultSet = null;
        if (model.containsAttribute("newuser") && ((User) model.getAttribute("newuser")).getName() != null){
            User user = (User) model.getAttribute("newuser");
            if (name.matcher(user.getName()).matches() && email.matcher(user.getEmail()).matches()) {
                insertUserStatement.setString(1, user.getName());
                insertUserStatement.setString(2, user.getEmail());
                insertUserStatement.executeUpdate();
                resultSet = insertUserStatement.getResultSet();
            } else {
                ((Error) Objects.requireNonNull(model.getAttribute("error"))).setError(1);
            }

        }
        if (model.containsAttribute("search") && model.getAttribute("search").toString() != null){
            String string = model.getAttribute("search").toString();

            if (name.matcher(string).matches()) {
                searchUserStatement.setString(1, "%" + string + "%");
                searchUserStatement.executeQuery();
                resultSet = searchUserStatement.getResultSet();
            } else {
                ((Error) Objects.requireNonNull(model.getAttribute("error"))).setError(1);
            }

        } else {
            selectAllUserStatement.executeQuery();
            resultSet = selectAllUserStatement.getResultSet();
            model.addAttribute("search", new Search());
            model.addAttribute("newuser", new User());
        }


        ArrayList<User> users = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                users.add(new User(resultSet));
            }
            resultSet.close();

        }
        model.addAttribute("users", users);

        return "safedb";
    }

    @PostMapping("/safedb")
    public void dbSearch(Model model, @ModelAttribute("search") Search search, @ModelAttribute("newuser") User user,  @ModelAttribute("error") Error error) throws SQLException {
        System.out.println(search);
        db(model);
    }

}

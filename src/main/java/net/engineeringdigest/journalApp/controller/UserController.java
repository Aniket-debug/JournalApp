package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    @GetMapping("/all")
    public List<User> getUser(){
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        users.put(user.getId(), user);
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable int id){
        return users.get(id);
    }
}

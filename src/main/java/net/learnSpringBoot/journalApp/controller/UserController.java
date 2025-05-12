package net.learnSpringBoot.journalApp.controller;

import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> getUser(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable ObjectId id){
        return userService.findById(id).orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public void deleteUser(@PathVariable ObjectId id){
        userService.deleteById(id);
    }

//    @PutMapping("/id/{id}")
//    public void updateUser(@PathVariable int id, @RequestBody User updatedUser){
//        userService.update(id, updatedUser);
//    }
}

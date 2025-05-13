package net.learnSpringBoot.journalApp.controller;

import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUser(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> createUser(@RequestBody User user){
        userService.saveUser(user);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable ObjectId id){
        Optional<User> user = userService.findById(id);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable ObjectId id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable ObjectId id, @RequestBody User updatedUser){
        userService.update(id, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

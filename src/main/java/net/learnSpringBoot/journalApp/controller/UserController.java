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
    public ResponseEntity<List<User>> getUser() {
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable ObjectId id) {
        try {
            Optional<User> user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getByUserName(@PathVariable String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> createUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable ObjectId id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable ObjectId id, @RequestBody User updatedUser) {
        try {
            if (userService.update(id, updatedUser))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

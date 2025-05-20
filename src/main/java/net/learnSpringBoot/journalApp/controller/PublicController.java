package net.learnSpringBoot.journalApp.controller;

import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("health-check")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>("healthy", HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<Boolean> createUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

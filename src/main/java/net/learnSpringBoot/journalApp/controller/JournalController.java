package net.learnSpringBoot.journalApp.controller;

import net.learnSpringBoot.journalApp.entity.Journal;
import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.service.JournalService;
import net.learnSpringBoot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(journalService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getJournalOfUser(@PathVariable String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user != null) {
                return new ResponseEntity<>(user.getJournals(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userName}")
    @Transactional
    public ResponseEntity<?> addJournal(@RequestBody Journal journal, @PathVariable String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (user != null) {
                journalService.save(journal);
                user.getJournals().add(journal);
                userService.saveUser(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userName}/{jId}")
    @Transactional
    public ResponseEntity<?> deleteJournal(@PathVariable String userName, @PathVariable ObjectId jId) {
        try {
            User user = userService.findByUserName(userName);
            if (user != null) {
                user.getJournals().removeIf(x -> x.getId().equals(jId));
                userService.saveUser(user);
                journalService.deleteById(jId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userName}/{jId}")
    @Transactional
    public ResponseEntity<?> update(
            @PathVariable String userName,
            @PathVariable ObjectId jId,
            @RequestBody Journal newJournal) {
        try {
            User user = userService.findByUserName(userName);
            Journal journal = journalService.getById(jId).orElse(null);
            if (user != null && journal != null) {
                if (newJournal.getContent() != null) {
                    journal.setContent(newJournal.getContent());
                }
                journal.setTitle(newJournal.getTitle());
                journalService.save(journal);
                user.getJournals().removeIf(x -> x.getId().equals(jId));
                user.getJournals().add(journal);
                userService.saveUser(user);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

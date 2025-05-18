package net.learnSpringBoot.journalApp.controller;

import net.learnSpringBoot.journalApp.entity.Journal;
import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.service.JournalService;
import net.learnSpringBoot.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getJournalOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            if (user != null) {
                return new ResponseEntity<>(user.getJournals(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            if (user != null) {
                List<Journal> journals = user.getJournals().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
                return new ResponseEntity<>(journals.get(0), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<?> addJournal(@RequestBody Journal journal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
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

    @DeleteMapping("/{jId}")
    @Transactional
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId jId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            if (user != null) {
                boolean removed =  user.getJournals().removeIf(x -> x.getId().equals(jId));
                if (removed){
                    userService.saveUser(user);
                    journalService.deleteById(jId);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{jId}")
    @Transactional
    public ResponseEntity<?> update(
            @PathVariable ObjectId jId,
            @RequestBody Journal newJournal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            if (user != null) {
                Journal journal = null;
                for (Journal j : user.getJournals()) {
                    if (j.getId().equals(jId)) {
                        journal = j;
                    }
                }
                if (journal != null) {
                    if (newJournal.getContent() != null) {
                        journal.setContent(newJournal.getContent());
                    }
                    journal.setTitle(newJournal.getTitle());
                    journalService.save(journal);
                    user.getJournals().removeIf(x -> x.getId().equals(jId));
                    user.getJournals().add(journal);
                    userService.saveUser(user);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

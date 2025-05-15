package net.learnSpringBoot.journalApp.service;

import net.learnSpringBoot.journalApp.entity.Journal;
import net.learnSpringBoot.journalApp.repository.JournalRepo;
import net.learnSpringBoot.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalService {
    @Autowired
    JournalRepo journalRepo;
    @Autowired
    UserRepo userRepo;

    public void save(Journal journal){
        journalRepo.save(journal);

    }

    public Optional<Journal> getById(ObjectId id){
        return journalRepo.findById(id);
    }

    public List<Journal> getAll(){
        return journalRepo.findAll();
    }

    public void deleteById(ObjectId journalId){
        journalRepo.deleteById(journalId);
    }

    public void update(ObjectId id, Journal newJournal){
        Journal journal = journalRepo.findById(id).orElse(null);
        if (journal != null){
            if (newJournal.getContent()!=null){
                journal.setContent(newJournal.getContent());
            }
            journal.setTitle(newJournal.getTitle());
            journalRepo.save(journal);
        }
    }

}

package net.learnSpringBoot.journalApp.service;

import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;

    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public boolean update(ObjectId id, User newUser){
        User user = userRepo.findById(id).orElse(null);
        if (user != null){
            user.setEmail(newUser.getEmail());
            user.setUserName(newUser.getUserName());
            user.setPassword(newUser.getPassword());
            userRepo.save(user);
            return true;
        }
        return false;
    }
}

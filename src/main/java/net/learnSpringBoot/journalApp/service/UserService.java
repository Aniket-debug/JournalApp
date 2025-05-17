package net.learnSpringBoot.journalApp.service;

import net.learnSpringBoot.journalApp.entity.User;
import net.learnSpringBoot.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepo.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList());
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

    public void deleteByUserName(String userName){
        userRepo.deleteByUserName(userName);
    }

    public boolean update(String userName, User newUser){
        User user = userRepo.findByUserName(userName);
        if (user != null){
            user.setEmail(newUser.getEmail());
            user.setUserName(newUser.getUserName());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepo.save(user);
            return true;
        }
        return false;
    }
}

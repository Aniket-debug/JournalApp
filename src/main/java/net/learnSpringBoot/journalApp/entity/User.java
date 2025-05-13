package net.learnSpringBoot.journalApp.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

// POJO (plain old java object)
@Document
@Data
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;


    public User(ObjectId id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

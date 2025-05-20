package net.learnSpringBoot.journalApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

// POJO (plain old java object)
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @DBRef
    private ArrayList<Journal> journals = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
}

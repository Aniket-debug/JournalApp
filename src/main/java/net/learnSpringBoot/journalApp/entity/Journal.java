package net.learnSpringBoot.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document (collection = "journal")
@NoArgsConstructor
public class Journal {
    @Id
    ObjectId id;
    @NonNull
    String title;
    String content;
}

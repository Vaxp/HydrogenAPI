package me.topu.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data @AllArgsConstructor @NoArgsConstructor @Document(collection = "chatfilter")
public class ChatFilter {

    @Id private String id;
    @Indexed private String regex;

}

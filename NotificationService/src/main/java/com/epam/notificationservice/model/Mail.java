package com.epam.notificationservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "mail")
@AllArgsConstructor
@Builder
public class Mail {

    @Id
    private String id;
    private List<String> recipients;
    private String subject;
    private String body;

    private String status;

    private String remarks;

}

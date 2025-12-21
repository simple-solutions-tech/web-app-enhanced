package com.experiment.simple.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Document("audit_logs")
public class AuditLog {

    @Id
    private String id;

    private String eventType;
    private String entity;
    private String entityId;
    private String actor;
    private Instant timestamp;
    private Map<String, Object> data;

}
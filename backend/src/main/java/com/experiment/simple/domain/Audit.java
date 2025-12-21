package com.experiment.simple.domain;

import com.experiment.simple.data.entity.AuditLog;
import com.experiment.simple.data.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Audit {

    private final AuditLogRepository auditLogRepository;

    public void log(String eventType, String entity, String entityId, String actor, Map<String, Object> data) {
        AuditLog log = new AuditLog();
        log.setEventType(eventType);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setActor(actor);
        log.setTimestamp(Instant.now());
        log.setData(data);
        auditLogRepository.save(log);
    }
}


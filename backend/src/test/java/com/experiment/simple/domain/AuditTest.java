package com.experiment.simple.domain;

import com.experiment.simple.data.entity.AuditLog;
import com.experiment.simple.data.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditTest {

    @Mock AuditLogRepository auditLogRepository;

    @InjectMocks Audit audit;

    @Test
    void log_savesAuditLogWithCorrectFields() {
        Map<String, Object> data = Map.of("name", "Alice Wonder", "salary", 75000);

        audit.log("CREATE", "EMPLOYEE", "42", "admin", data);

        ArgumentCaptor<AuditLog> captor = ArgumentCaptor.forClass(AuditLog.class);
        verify(auditLogRepository).save(captor.capture());
        AuditLog saved = captor.getValue();

        assertThat(saved.getEventType()).isEqualTo("CREATE");
        assertThat(saved.getEntity()).isEqualTo("EMPLOYEE");
        assertThat(saved.getEntityId()).isEqualTo("42");
        assertThat(saved.getActor()).isEqualTo("admin");
        assertThat(saved.getData()).isEqualTo(data);
        assertThat(saved.getTimestamp()).isNotNull();
    }
}

package com.experiment.simple.domain;

import com.experiment.simple.application.dto.AddEmployeeRequest;
import com.experiment.simple.application.mapper.EmployeeMapper;
import com.experiment.simple.data.entity.DepartmentEntity;
import com.experiment.simple.data.entity.EmployeeEntity;
import com.experiment.simple.data.repository.DepartmentRepository;
import com.experiment.simple.data.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeTest {

    @Mock EmployeeRepository employeeRepository;
    @Mock DepartmentRepository departmentRepository;
    @Mock Audit audit;
    @Mock EmployeeMapper mapper;

    @InjectMocks Employee employee;

    @Test
    void getEmployee_whenFound_returnsEntity() {
        EmployeeEntity entity = new EmployeeEntity();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertThat(employee.getEmployee(1L)).contains(entity);
    }

    @Test
    void getEmployee_whenNotFound_returnsEmpty() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(employee.getEmployee(99L)).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    void addEmployee_savesEntityAndLogsAudit() {
        AddEmployeeRequest request = new AddEmployeeRequest();
        request.firstName = "Alice";
        request.lastName = "Wonder";
        request.salary = 75000;
        request.departmentId = 1L;

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1L);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(departmentEntity));

        EmployeeEntity mappedEntity = new EmployeeEntity();
        EmployeeEntity savedEntity = new EmployeeEntity();
        savedEntity.setId(1L);
        savedEntity.setSalary(75000);

        when(mapper.fromRequest(request)).thenReturn(mappedEntity);
        when(employeeRepository.save(mappedEntity)).thenReturn(savedEntity);

        EmployeeEntity result = employee.addEmployee(request);

        assertThat(result).isSameAs(savedEntity);

        ArgumentCaptor<Map<String, Object>> dataCaptor = ArgumentCaptor.forClass(Map.class);
        verify(audit).log(eq("CREATE"), eq("EMPLOYEE"), eq("1"), eq(""), dataCaptor.capture());

        Map<String, Object> auditData = dataCaptor.getValue();
        assertThat(auditData.get("name")).isEqualTo("Alice Wonder");
        assertThat(auditData.get("salary")).isEqualTo(75000);
    }
}

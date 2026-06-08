package com.Jitu.Employees.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Jitu.Employees.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.Jitu.Employees.dto.EmployeeDTO;
import com.Jitu.Employees.entities.EmployeeEntity;
import com.Jitu.Employees.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO inputEmployee) {
//        if (!isExistsByEmployeeId(employeeId)) {
//            throw new ResourceNotFoundException("Employee with id: " + employeeId + " does not exist");
//        }
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean isExistsByEmployeeId(Long employeeId) {
            boolean exists = employeeRepository.existsById(employeeId);
        if(!exists)  throw new ResourceNotFoundException("Employee with id: " + employeeId + " does not exist");
        return true;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
         // Optional <EmployeeEntity> employeeEntity =
         // employeeRepository.findById(employeeId);
         // return employeeEntity.map(employee -> modelMapper.map(employee,
         // EmployeeDTO.class));
         return employeeRepository.findById(employeeId).map(employee -> modelMapper.map(employee, EmployeeDTO.class));
     }

    public boolean deleteEmployeeById(Long employeeId) {
//         if (!isExistsByEmployeeId(employeeId)) {
//             throw new ResourceNotFoundException("Employee with id: " + employeeId + " does not exist");
//         }
        isExistsByEmployeeId(employeeId);
         employeeRepository.deleteById(employeeId);
         return true;
     }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean exists = isExistsByEmployeeId(employeeId);
//        if(!exists) return null;
//        if(!exists) throw new ResourceNotFoundException("Employee not found with id "+employeeId);
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}

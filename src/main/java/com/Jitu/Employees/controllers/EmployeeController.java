package com.Jitu.Employees.controllers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.Jitu.Employees.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Jitu.Employees.dto.EmployeeDTO;
import com.Jitu.Employees.services.EmployeeService;

import jakarta.validation.Valid;
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



   @GetMapping(path = "/getMessage")
   public String getMessage() {
       return "Server is running fine";
   }

   @GetMapping(path="/{employeeId}")
   public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long employeeId) {
       Optional <EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
       return employeeDTO.map(employeeData -> ResponseEntity.ok(employeeData)).
//       orElse(ResponseEntity.notFound().build()); //no expcetion thrown
//       orElseThrow(()->new NoSuchElementException("Employee not found")); // using exception
       orElseThrow(()->new ResourceNotFoundException("Employee not found")); //using custom exceptions
   }

    @GetMapping
   public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age) {
       List <EmployeeDTO> employeeDTO = employeeService.getAllEmployees();
       return ResponseEntity.ok(employeeDTO);
   }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long employeeId, 
                        @RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeId, inputEmployee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployeeById(employeeId);
        if(deleted) return ResponseEntity.ok().body(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployee(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        EmployeeDTO updatedEmployee = employeeService.updatePartialEmployeeById(employeeId, updates);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

}

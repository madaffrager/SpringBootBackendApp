package com.example.demo.controller;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
// get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }
// create employee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
// get Employee by ID
@GetMapping("/employees/{id}")
    public ResponseEntity <Employee> getEmployeeById(@PathVariable Long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee doesnt exist with id "+id));

    return ResponseEntity.ok(employee);
    }

//update Employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee doesnt exist with id "+id));

employee.setFirstname(employeeDetails.getFirstname());
employee.setLastname(employeeDetails.getLastname());
employee.setEmail(employeeDetails.getEmail());
Employee updatedEmployee=employeeRepository.save(employee);
return ResponseEntity.ok(updatedEmployee);
    }
//delete employee
@DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
    Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee doesnt exist with id "+id));
employeeRepository.delete(employee);
Map<String,Boolean>response=new HashMap<>();
response.put("deleted",Boolean.TRUE);
return ResponseEntity.ok(response);
}


}

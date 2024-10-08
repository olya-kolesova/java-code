package com.javacode.projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.projections.controller.EmployeeController;
import com.javacode.projections.dto.EmployeeDto;
import com.javacode.projections.model.Department;
import com.javacode.projections.model.Employee;
import com.javacode.projections.service.DepartmentService;
import com.javacode.projections.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(EmployeeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    Employee employee;

    Department department;

    @BeforeEach
    public void setup(){

        department = new Department();
        department.setId(1L);
        department.setName("Cleaning");

        given(departmentService.findById(1L)).willReturn(department);

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Dmitrij");
        employee.setLastName("Kovalenko");
        employee.setPosition("Cleaner");
        employee.setSalary(70000.0);
        employee.setDepartment(department);

    }

    @Test
    @Order(1)
    public void saveEmployeeTest_employee_isCreated() throws Exception {

        given(employeeService.save(any(Employee.class))).willReturn(employee);

        ResultActions response = mockMvc.perform(post("/api/projections/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.position", is(employee.getPosition())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())));


    }

    @Test
    @Order(2)
    public void findAllByPosition_positionCleaner_returnListOfSize2() throws Exception{
        List<EmployeeDto> employeesList = new ArrayList<>();
        EmployeeDto employeeDto = new EmployeeDto("Dmitrij Kovalenko", "Cleaner", "Cleaning");
        EmployeeDto anotherEmployeeDto = new EmployeeDto("Ivan Miheenko", "Cleaner", "Cleaning");
        employeesList.add(employeeDto);
        employeesList.add(anotherEmployeeDto);
        given(employeeService.findAllByPosition(employee.getPosition())).willReturn(employeesList);


        ResultActions response = mockMvc.perform(get("/api/projections/employee/position?position=Cleaner"));


        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(employeesList.size())));

    }

    @Test
    @Order(3)
    public void getById_id1_returnDmitrij() throws Exception{

        given(employeeService.findById(employee.getId())).willReturn(employee);

        ResultActions response = mockMvc.perform(get("/api/projections/employee/{id}", employee.getId()));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
            .andExpect(jsonPath("$.position", is(employee.getPosition())));

    }


    @Test
    @Order(4)
    public void updateEmployeeTest() throws Exception{
        given(employeeService.findById(employee.getId())).willReturn(employee);
        employee.setPosition("Head of department");
        employee.setSalary(150000.0);
        employee.setDepartment(department);
        given(employeeService.update(employee.getId(), employee)).willReturn(employee);

        ResultActions response = mockMvc.perform(put("/api/projections/employee/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
            .andExpect(jsonPath("$.position", is(employee.getPosition())))
            .andExpect(jsonPath("$.salary", is(employee.getSalary())));

    }


    @Test
    @Order(5)
    public void deleteById_id1_isOk() throws Exception{

        given(employeeService.findById(employee.getId())).willReturn(employee);
        willDoNothing().given(employeeService).deleteById(employee.getId());

        ResultActions response = mockMvc.perform(delete("/api/projections/employee/{id}", employee.getId()));

        response.andExpect(status().isOk());

    }
}



package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertCustomerToCustomerDTO(userService.saveCustomer(convertCustomerDTOToCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return convertToCustomerDTOList(userService.getAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomerToCustomerDTO(userService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertEmployeeToEmployeeDTO(userService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(userService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return convertToEmployeeDTOList(userService.findEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills()));
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "petIds");

        List<Pet> pets = customer.getPets();
        List<Long> petIds = new ArrayList<>();

        if (pets != null) {
            for (Pet pet: pets) {
                petIds.add(pet.getId());
            }

            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    private List<EmployeeDTO> convertToEmployeeDTOList (List<Employee> employees) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee: employees) {
            employeeDTOList.add(convertEmployeeToEmployeeDTO(employee));
        }

        return employeeDTOList;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO, "skills", "daysAvailable");
        Set<EmployeeSkill> skills = employee.getSkills();
        employeeDTO.setSkills(skills);
        Set<DayOfWeek> daysAvailable = employee.getDaysAvailable();
        employeeDTO.setDaysAvailable(daysAvailable);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "skills", "daysAvailable");

        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());

        return employee;
    }

    private List<CustomerDTO> convertToCustomerDTOList(List<Customer> customers){
        List<CustomerDTO> customersDTO = new ArrayList<>();

        for (Customer customer: customers) {
            customersDTO.add(convertCustomerToCustomerDTO(customer));
        }

        return customersDTO;
    }

}

package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);

        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void setAvailability(Set<DayOfWeek> dayOfWeeks, Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(dayOfWeeks);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(LocalDate localDate, Set<EmployeeSkill> skills) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        List<Employee> employeeList = getAllEmployees();
        List<Employee> availableEmployees = new ArrayList<>();

        //Iterates through all employees and returns a list of employees that have the skills and are available on the date passed through the method
        for (Employee employee: employeeList) {
            if (employee.getDaysAvailable().contains(dayOfWeek) && employee.getSkills().containsAll(skills)) {
                availableEmployees.add(employee);
            }
        }

        return availableEmployees;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        Customer customer = optionalCustomer.orElseThrow(CustomerNotFoundException::new);

        return customer;
    }

    public Customer getOwnerByPet(Long petId) {
        Customer customer = customerRepository.findByPetsId(petId);
        List<Pet> pets = petRepository.findByOwnerId(customer.getId());

        customer.setPets(pets);

        return customer;
    }

}

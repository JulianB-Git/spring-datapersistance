package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    UserService userService;
    @Autowired
    PetService petService;
    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertScheduleListToScheduleDTOList(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertScheduleListToScheduleDTOList(scheduleService.findScheduleByPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertScheduleListToScheduleDTOList(scheduleService.findScheduleByEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertScheduleListToScheduleDTOList(scheduleService.findScheduleByCustomer(customerId));
    }

    private List<ScheduleDTO> convertScheduleListToScheduleDTOList(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for (Schedule schedule: schedules) {
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOList;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO, "employeeIds", "petIds", "activities");

        List<Employee> employees = schedule.getEmployeeIds();
        List<Pet> pets = schedule.getPetIds();

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        if (employees != null) {
            for (Employee employee: employees) {
                employeeIds.add(employee.getId());
            }

            scheduleDTO.setEmployeeIds(employeeIds);
        }

        if (pets != null) {
            for (Pet pet: pets) {
                petIds.add(pet.getId());
            }

            scheduleDTO.setPetIds(petIds);
        }

        Set<EmployeeSkill> skills = schedule.getActivities();
        scheduleDTO.setActivities(skills);

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule, "employeeIds", "petIds", "activities");

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        if (employeeIds != null) {
            for (Long id: employeeIds) {
                employees.add(userService.getEmployeeById(id));
            }

            schedule.setEmployeeIds(employees);
        }

        if (petIds != null) {
            for (Long id: petIds) {
                pets.add(petService.getPetById(id));
            }

            schedule.setPetIds(pets);
        }

        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }
}

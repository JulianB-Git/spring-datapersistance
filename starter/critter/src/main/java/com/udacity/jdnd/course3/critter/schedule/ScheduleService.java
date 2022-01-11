package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPet(Long id) {
        List<Schedule> allSchedules = getAllSchedules();
        List<Schedule> schedulesForPet = new ArrayList<>();

        //Iterates through each schedules and then each pet to find matching pet id
        for (Schedule schedule: allSchedules) {
            List<Pet> pets = schedule.getPetIds();
            for (Pet pet: pets) {
                if (pet.getId().equals(id)) {
                    schedulesForPet.add(schedule);
                }
            }
        }

        return schedulesForPet;
    }

    public List<Schedule> findScheduleByEmployee(Long id) {
        List<Schedule> allSchedules = getAllSchedules();
        List<Schedule> scheduleForEmployee = new ArrayList<>();

        //Iterates through each schedules and then each employee to find matching employee id
        for (Schedule schedule: allSchedules) {
            List<Employee> employees = schedule.getEmployeeIds();
            for (Employee employee: employees) {
                if (employee.getId().equals(id)) {
                    scheduleForEmployee.add(schedule);
                }
            }
        }

        return scheduleForEmployee;
    }

    public List<Schedule> findScheduleByCustomer(Long id) {
        List<Schedule> allSchedules = getAllSchedules();
        List<Schedule> scheduleForCustomer = new ArrayList<>();

        for (Schedule schedule: allSchedules) {
            List<Pet> pets = schedule.getPetIds();
            for (Pet pet: pets) {
                if (pet.getOwner().getId().equals(id)) {
                    scheduleForCustomer.add(schedule);
                }
            }
        }

        //Remove Duplicates and return list
        return new ArrayList<>(new HashSet<>(scheduleForCustomer));
    }

}

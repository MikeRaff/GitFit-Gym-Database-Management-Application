package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;

public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;
    

    @Transactional
    public List<Instructor> getAllInstructors()
    {
         List<Instructor> instructors = instructorRepository.findAll();
        if(instructors.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No Instructors found in the system.");
        }
        return instructors;
    }
}

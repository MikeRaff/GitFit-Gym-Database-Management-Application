package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;

public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    InstructorRegistrationRepository instructorRegistrationRepository;
    /**
     * Create a new instructor registeration to add an instructor to a class
     * @param session
     * @param email
     * @return new registration
     * @throws GRSException Invalid email or already registered
     */
    public  InstructorRegistration registerInstructorForClass(Session session, String email)
    {
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        if (instructor == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid email");
        }
        InstructorRegistration instructorRegistration = instructorRegistrationRepository.findInstructorRegistrationByInstructorAndSession(instructor, session);
        if(instructorRegistration !=null)
        {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "already registered");
        }
        instructorRegistration = new InstructorRegistration(session.getDate(),instructor, session);
        instructorRegistrationRepository.save(instructorRegistration);
        return instructorRegistration;
    }
    /**
     * Remove an instructor from a class if there is another class
     * @param Session
     * @param email
     * @throws GRSException not able to remove instructor
     */
    @Transactional
    public void removeInstructorFromClass(Session Session, String email)
    {
        List<InstructorRegistration> instructorRegistrations = instructorRegistrationRepository.findInstructorRegistrationsBySession(Session);
        if(instructorRegistrations.size() <2)
            throw new GRSException(HttpStatus.UNAUTHORIZED, "not enough instructors registered");
        for(InstructorRegistration r: instructorRegistrations )
        {
            if(r.getInstructor().getEmail().equals(email))
                instructorRegistrationRepository.delete(r);
            return;
        }
        throw new GRSException(HttpStatus.UNAUTHORIZED, "instructor not teaching course");
    }
}

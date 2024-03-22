package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;


@Service
public class InstructorRegistrationService {
    
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    InstructorRegistrationRepository instructorRegistrationRepository;
    
    
    /**
     * Create a new instructor registeration to add an instructor to a class
     * @param sessionid
     * @param instructorid
     * @return new registration
     * @throws GRSException Invalid email or already registered
     */
    public InstructorRegistration registerInstructorForClass(int sessionId, int instructorId)
    {
        Instructor instructor = instructorRepository.findInstructorById(instructorId);
        Session session = sessionRepository.findSessionById(sessionId);
        if (instructor == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Instructor not found");
        }
        InstructorRegistration instructorRegistration = instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(instructorId, sessionId);
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
    public void removeInstructorFromClass(int sessionId, int instructorId)
    {
        List<InstructorRegistration> instructorRegistrations = instructorRegistrationRepository.findInstructorRegistrationsBySession_id(sessionId);
        if(instructorRegistrations.size() <2)
            throw new GRSException(HttpStatus.UNAUTHORIZED, "not enough instructors registered");
        for(InstructorRegistration r: instructorRegistrations )
        {
            if(r.getInstructor().getId() == instructorId)
                instructorRegistrationRepository.delete(r);
            return;
        }
        throw new GRSException(HttpStatus.UNAUTHORIZED, "instructor not teaching course");
    }    
}

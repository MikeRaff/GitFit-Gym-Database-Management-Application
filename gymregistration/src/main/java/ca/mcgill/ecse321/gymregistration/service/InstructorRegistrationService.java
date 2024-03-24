package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Owner;
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
    @Autowired
    OwnerRepository ownerRepository;

    /**
     * RegisterInstuctorForClass: Register an instructor for a class
     * @param sessionId: Id of the session
     * @param instructorId: Id of the instructor
     * @param gymUser: The user registering the instructor
     * @return The new registration
     * @throws GRSException Invalid email or already registered
     */
    @Transactional
    public InstructorRegistration registerInstructorForClass(int sessionId, int instructorId, GymUser gymUser) {
        if(!(gymUser instanceof Owner) && gymUser.getId() != instructorId) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Instructors can only be assigned by themselves or by the owner.");
        }
        Instructor instructor = instructorRepository.findInstructorById(instructorId);
        Session session = sessionRepository.findSessionById(sessionId);
        if (instructor == null) {
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        }
        if (session == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found");
        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationByInstructor_idAndSession_id(instructorId, sessionId);
        if (instructorRegistration != null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "already registered");

        instructorRegistration = new InstructorRegistration(null, instructor, session);
        instructorRegistrationRepository.save(instructorRegistration);
        return instructorRegistration;
    }

    /**
     * Remove an instructor from a class if there is another class
     * 
     * @param Session
     * @param email
     * @throws GRSException not able to remove instructor
     */
    @Transactional
    public void removeInstructorFromClass(int sessionId, int instructorId, int gymuserId) {
        List<InstructorRegistration> instructorRegistrations = instructorRegistrationRepository
                .findInstructorRegistrationsBySession_id(sessionId);
        if (instructorRegistrations.size() < 2)
            throw new GRSException(HttpStatus.BAD_REQUEST, "not enough instructors registered");
        GymUser gymuser = instructorRepository.findInstructorById(gymuserId);
        if(gymuser ==null)
            gymuser = ownerRepository.findOwnerById(gymuserId);
    
        if( gymuser == null || gymuser instanceof Owner == false || instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(gymuser.getId(), sessionId)==null)
            throw new GRSException(HttpStatus.UNAUTHORIZED, "You don't have permission to remove this instructor");
        for (InstructorRegistration r : instructorRegistrations) {
            if (r.getInstructor().getId() == instructorId)
                instructorRegistrationRepository.delete(r);
            return;
        }
        throw new GRSException(HttpStatus.BAD_REQUEST, "instructor not teaching course");
    }

    /**
     * return an instructor registration
     * 
     * @param instructorId
     * @param sessionId
     * @returnx
     */
    @Transactional
    public InstructorRegistration getInstructorRegistration(int instructorId, int sessionId) {
        Instructor instructor = instructorRepository.findInstructorById(instructorId);
        Session session = sessionRepository.findSessionById(sessionId);

        if (instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");

        if (session == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found");

        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationByInstructor_idAndSession_id(instructor.getId().intValue(), session.getId());

        if (instructorRegistration == null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Instructor not registered for this session");

        return instructorRegistration;
    }


    public InstructorRegistration getInstructorRegistrationById(int id) {
        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationById(id);
        if (instructorRegistration == null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Instructor not registered for this session");
        return instructorRegistration;
    }
}

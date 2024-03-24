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
     * RegisterInstuctorForClass: register an instructor for a class
     * @param sessionId: id of the session
     * @param email: email of the instructor
     * @param gymUser: the user registering the instructor
     * @return the new registration
     * @throws GRSException invalid email or already registered
     */
    @Transactional
    public InstructorRegistration registerInstructorForClass(int sessionId, String email, GymUser gymUser) {
        if(!(gymUser instanceof Owner) && gymUser.getEmail() != email) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Instructors can only be assigned by themselves or by the owner.");
        }
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Session session = sessionRepository.findSessionById(sessionId);
        if (instructor == null) {
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found.");
        }
        if (session == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationByInstructor_idAndSession_id(instructor.getId().intValue(), sessionId);
        if (instructorRegistration != null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Already registered.");

        instructorRegistration = new InstructorRegistration(null, instructor, session);
        instructorRegistrationRepository.save(instructorRegistration);
        return instructorRegistration;
    }

    /**
     * RemoveInstructorFromClass: remove an instructor from a class
     * @param sessionId: id of the session
     * @param email: email of the instructor
     * @param gymUser: the user removing the instructor
     * @throws GRSException not enough instructors registered, Unauthorized user, Instructor not teaching course
     */
    @Transactional
    public void removeInstructorFromClass(int sessionId, String email, int gymuserId) {
        List<InstructorRegistration> instructorRegistrations = instructorRegistrationRepository
                .findInstructorRegistrationsBySession_id(sessionId);
        if (instructorRegistrations.size() < 2)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Not enough instructors registered.");
        GymUser gymuser = instructorRepository.findInstructorById(gymuserId);
        if(gymuser ==null)
            gymuser = ownerRepository.findOwnerById(gymuserId);
    
        if( gymuser == null || gymuser instanceof Owner == false || instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(gymuser.getId(), sessionId)==null)
            throw new GRSException(HttpStatus.UNAUTHORIZED, "You don't have permission to remove this instructor.");
        for (InstructorRegistration r : instructorRegistrations) {
            if (r.getInstructor().getId() == instructorRepository.findInstructorByEmail(email).getId()){
                instructorRegistrationRepository.delete(r);
            }
            return;
        }
        throw new GRSException(HttpStatus.BAD_REQUEST, "Instructor not teaching course.");
    }

    /**
     * GetInstructorRegistration: get the registration of an instructor for a session
     * @param email: email of the instructor
     * @param sessionId: id of the session
     * @return the registration of the instructor for the session
     * @throws GRSException instructor not found, Session not found, Instructor not registered for this session
     */
    @Transactional
    public InstructorRegistration getInstructorRegistration(String email, int sessionId) {
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        Session session = sessionRepository.findSessionById(sessionId);

        if (instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found.");

        if (session == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");

        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationByInstructor_idAndSession_id(instructor.getId().intValue(), session.getId());

        if (instructorRegistration == null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Instructor not registered for this session.");

        return instructorRegistration;
    }

    /**
     * GetInstructorRegistrationById: get the registration of an instructor for a session by registration id
     * @param id: id of the registration
     * @return the registration of the instructor for the session
     * @throws GRSException instructor not registered for this session
     */
    @Transactional
    public InstructorRegistration getInstructorRegistrationById(int id) {
        InstructorRegistration instructorRegistration = instructorRegistrationRepository
                .findInstructorRegistrationById(id);
        if (instructorRegistration == null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Instructor not registered for this session.");
        return instructorRegistration;
    }
}

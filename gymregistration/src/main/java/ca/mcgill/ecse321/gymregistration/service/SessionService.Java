package ca.mcgill.ecse321.gymregistration.service;

import java.sql.Date;
import java.sql.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;

    public SessionService()
    {}
    
    /*
     * Create Session: Creates a new session
     * @param Instructor: The instructor creating the course
     * @param classType: The classtype of the session
     * @param startTime: The start time of the class
     * @param endTime: the end time of the class
     * @param date: the day the class takes place
     * @param description: a textual description of the class 
     * @param name: the name of this specific class
     * @param location: where this class is being taught
     * @return the created session
     * @throws GRSException Invalid creation request
     */
    Session createSession(Date date, Time startTime, Time endTime, String description, String name, String location, ClassType classType)
    {
        if(sessionRepository.findSessionByStartTimeAndDate(startTime, date)!=null)
             throw new GRSException(HttpStatus.BAD_REQUEST, "Time Not Available");
        if(classType == null || startTime == null || endTime == null || date == null || description == null || name == null || location == null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Please enter missing information");
        if(!classType.getIsApproved())
            throw new GRSException(HttpStatus.BAD_REQUEST, "Class must be approved");
        
        Session session = new Session(date, startTime, endTime, description, name, location, classType);
       
        sessionRepository.save(session);

        //logic to call instructor reg
        return session;
    }

    List<Session> getAllSessions()
    {
        return sessionRepository.findAll();
    }
    
}

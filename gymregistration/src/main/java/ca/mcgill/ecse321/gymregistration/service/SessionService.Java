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

public class SessionService {
    @Autowired
    SessionRepository sessionRepository;
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
    Session CreateSession(ClassType classType, Time startTime, Time endTime, Date date, String description, String name, String location)
    {
        if(sessionRepository.findSessionByStartTimeAndDate(startTime, date)!=null)
             throw new GRSException(HttpStatus.BAD_REQUEST, "Time Not Available");
        
        Session session = new Session(date, startTime, endTime, description, name, location, classType);
       
        sessionRepository.save(session);

        //logic to call instructor reg
        return session;
    }

    Session 

    
}

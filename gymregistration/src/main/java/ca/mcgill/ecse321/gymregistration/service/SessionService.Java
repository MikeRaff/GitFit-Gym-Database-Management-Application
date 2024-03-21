package ca.mcgill.ecse321.gymregistration.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    SessionRepository sessionRepository;
    
    /**
     * Create Session: Creates a new session
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
    @Transactional
    public Session createSession(Date date, Time startTime, Time endTime, String description, String name, String location, ClassType classType, int capacity)
    {
        if(sessionRepository.findSessionByStartTimeAndDate(startTime, date)!=null)
             throw new GRSException(HttpStatus.BAD_REQUEST, "Time not available.");
        if(classType == null || startTime == null || endTime == null || date == null || description == null || description.length() == 0 || name == null || name.length() == 0 || location == null || location.length() == 0)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Missing information.");
        if(!classType.getIsApproved())
            throw new GRSException(HttpStatus.BAD_REQUEST, "Class must be approved.");

        Session session = new Session();
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setDescription(description);
        session.setName(name);
        session.setLocation(location);
        session.setClassType(classType);
        session.setCapacity(capacity);
        sessionRepository.save(session);
        return session;
    }

    @Transactional
    public Session updateSession(int oldSessionId, Session newSession){
        if(sessionRepository.findSessionById(oldSessionId) == null){
            throw new GRSException(HttpStatus.CONFLICT, "Session with id " + oldSessionId + " does not exist.");
        }
        if(newSession == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Session cannot be empty.");
        }
        Session toUpdate = sessionRepository.findSessionById(oldSessionId);
        toUpdate.setDate(newSession.getDate());
        toUpdate.setStartTime(newSession.getStartTime());
        toUpdate.setEndTime(newSession.getEndTime());
        toUpdate.setDescription(newSession.getDescription());
        toUpdate.setName(newSession.getName());
        toUpdate.setLocation(newSession.getLocation());
        toUpdate.setClassType(newSession.getClassType());
        toUpdate.setCapacity(newSession.getCapacity());
        toUpdate.setId(newSession.getId());
        sessionRepository.save(toUpdate);
        return toUpdate;
    }

    @Transactional
    public Session getSessionById(int id){
        Session session = sessionRepository.findSessionById(id);
        if (session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        }
        return session;
    }

    @Transactional
    public List<Session> getAllSessions(){
        List<Session> sessions = sessionRepository.findAll();
        if(sessions.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No Sessions found in the system.");
        }
        return sessions;
    }

    @Transactional
    public void deleteSession(int id){
        Session session = sessionRepository.findSessionById(id);
        if(session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        }
        sessionRepository.deleteSessionById(id);
    }
}

package ca.mcgill.ecse321.gymregistration.controller;
import ca.mcgill.ecse321.gymregistration.dto.SessionDto;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
public class SessionRestController {
    @Autowired
    private SessionService sessionService;


    /**
     * GetAllClassTypes: getting all class types
     * @return All Sessions in database
     */
    @GetMapping(value = { "/sessions", "/sessions/"})
    public List<SessionDto> getAllSessions() {
        return sessionService.getAllSessions().stream().map(SessionDto::new).collect(Collectors.toList());
    }
    /**
     * Find session by ID
     * @param id
     * @return the desired session
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/sessions/{id}", "/sessions/{id}/"})
    public ResponseEntity<SessionDto> getSession(@PathVariable("id") int id) throws IllegalArgumentException {
        Session session = sessionService.getSessionById(id);
        return new ResponseEntity<>(new SessionDto(session), HttpStatus.OK);
    }
    /**
     * Creates a new session
     * @param sessionDto
     * @return the created session
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/sessions/create", "/sessions/create/"})
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionDto sessionDto) throws IllegalArgumentException{
        Session session = sessionService.createSession(sessionDto.getDate(), sessionDto.getStartTime(), sessionDto.getEndTime(), sessionDto.getDescription(), sessionDto.getName(), sessionDto.getLocation(), sessionDto.getClassType(), sessionDto.getCapacity());
        return new ResponseEntity<>(new SessionDto(session), HttpStatus.CREATED);
    }
    /**
     * Updated a session
     * @param id
     * @param sessionDto
     * @return the updated session
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/sessions/{id}", "/sessions/{id}/"})
    public ResponseEntity<SessionDto> updateSession(@PathVariable("id") int id, @RequestBody SessionDto sessionDto) throws IllegalArgumentException{
        Session toUpdate = sessionService.getSessionById(id);
        Session session = sessionService.updateSession(id, sessionService.getSessionById(sessionDto.getId()));
        return new ResponseEntity<>(new SessionDto(session), HttpStatus.OK);
    }
    /**
     * Deletes a session
     * @param id
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/sessions/delete/{id}", "/sessions/delete/{id}/"})
    public void deleteSession(@PathVariable("id") int id, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        sessionService.deleteSession(id, gymUser);
    }
}


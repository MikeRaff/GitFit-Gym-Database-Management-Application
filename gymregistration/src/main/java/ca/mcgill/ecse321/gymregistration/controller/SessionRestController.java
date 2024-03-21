package ca.mcgill.ecse321.gymregistration.controller;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return All class types in database
     */
    @GetMapping(value = { "/sessions", "/sessions/"})
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions().stream().map(Session::new).collect(Collectors.toList());
    }
    
   
}


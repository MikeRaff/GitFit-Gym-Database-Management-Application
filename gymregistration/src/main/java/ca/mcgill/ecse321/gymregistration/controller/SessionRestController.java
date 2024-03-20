package ca.mcgill.ecse321.gymregistration.controller;
import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
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

    
   
}


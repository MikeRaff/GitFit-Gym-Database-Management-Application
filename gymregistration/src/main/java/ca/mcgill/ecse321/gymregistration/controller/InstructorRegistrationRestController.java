package ca.mcgill.ecse321.gymregistration.controller;


import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.InstructorRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
public class InstructorRegistrationRestController {
    @Autowired
    private InstructorRegistrationService instructorRegistrationService;

    @PostMapping(value = { "/instructor-registration/create", "/instructor-registration/create/"}) 
    public ResponseEntity<InstructorRegistrationDto> registerInstructorForClass(@RequestBody InstructorRegistrationDto instructorRegistrationDto){
        InstructorRegistration instructorRegistration = instructorRegistrationService.registerInstructorForClass(instructorRegistrationDto.getSession(),instructorRegistrationDto.getInstructor().getEmail());
        return new ResponseEntity<>(new InstructorRegistrationDto(instructorRegistration), HttpStatus.OK);
    }

    @DeleteMapping(value = { "/instructor-registration/delete", "/instructor-registration/delete/"})
    public void removeInstructorFromClass(@PathVariable("id") String email, Session session) throws IllegalArgumentException
    {
        instructorRegistrationService.removeInstructorFromClass(session, email);
    }
    
}

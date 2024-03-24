package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
import ca.mcgill.ecse321.gymregistration.dto.GymUserDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.dto.SessionDto;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.InstructorRegistrationService;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class InstructorRegistrationRestController {
    @Autowired
    private InstructorRegistrationService instructorRegistrationService;
    
    /**
     * Create a new instructor registration
     * @param instructorRegistrationDto
     * @return the response entity of the result
     */
    @PostMapping(value = { "/instructor-registration/create", "/instructor-registration/create/" })
    public ResponseEntity<InstructorRegistrationDto> registerInstructorForClass(
            @RequestBody InstructorRegistrationDto instructorRegistrationDto) {
        try {
            InstructorRegistration instructorRegistration = instructorRegistrationService.registerInstructorForClass(
                    instructorRegistrationDto.getSession().getId(), instructorRegistrationDto.getInstructor().getEmail(), instructorRegistrationDto.getInstructor());
            return new ResponseEntity<>(new InstructorRegistrationDto(instructorRegistration),
                    HttpStatus.CREATED);
        } catch (GRSException e) {
            return new ResponseEntity<>(new InstructorRegistrationDto(), e.getStatus());
        }
    }
    /**
     * Delete an instructor Registration
     * @param id
     * @return A response entity containing the success of the method
     * @throws IllegalArgumentException
     * 
     */
    @DeleteMapping(value = { "/instructor-registration/delete/{id}", "/instructor-registration/delete/{id}/" })
    public void removeInstructorFromClass(@PathVariable("id") int id, @RequestBody String email, @RequestBody GymUser gymUser) throws IllegalArgumentException {
        instructorRegistrationService.removeInstructorFromClass(id, email, gymUser);
    }

    /**
     * UpdateInstructorRegistration: update instructor registration
     * @param id
     * @param instructorRegistrationDto
     * @return
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/instructor-registration/{id}", "/instructor-registration/{id}/"})
    public ResponseEntity<InstructorRegistrationDto> updateInstructorRegistration(@PathVariable("id") int id, @RequestBody InstructorRegistrationDto instructorRegistrationDto) throws IllegalArgumentException{
        InstructorRegistration instructorRegistration = instructorRegistrationService.updateInstructorRegistration(id, instructorRegistrationDto);
        return new ResponseEntity<>(new InstructorRegistrationDto(instructorRegistration), HttpStatus.OK);
    }

    /**
     * Getting all instructor registrations for a session id
     * @param id
     * @return a response entity containing the Dto and the status
     */
    @GetMapping(value = { "/instructor-registration/{id}", "/instructor-registration/{id}" })
    public List<InstructorRegistration> getInstructorRegistration(@PathVariable("id") int id) {
        return instructorRegistrationService.getInstructorRegistrationBySession(id).stream().map(InstructorRegistration::new).collect(Collectors.toList());
    }

}
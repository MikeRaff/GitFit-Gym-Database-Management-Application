package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.service.InstructorRegistrationService;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class InstructorRegistrationRestController {
    @Autowired
    private InstructorRegistrationService instructorRegistrationService;

    @PostMapping(value = { "/instructor-registration/create", "/instructor-registration/create/" })
    public ResponseEntity<InstructorRegistrationDto> registerInstructorForClass(
            @RequestBody InstructorRegistrationDto instructorRegistrationDto) {
        try {
            InstructorRegistration instructorRegistration = instructorRegistrationService.registerInstructorForClass(
                    instructorRegistrationDto.getSession().getId(), instructorRegistrationDto.getInstructor().getId());
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(instructorRegistration),
                    HttpStatus.CREATED);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), e.getStatus());
        }
    }

    @DeleteMapping(value = { "/instructor-registration/delete/{id}", "/instructor-registration/delete/{id}/" })
    public ResponseEntity<InstructorRegistrationDto> removeInstructorFromClass(@PathVariable("id") int id)
            throws IllegalArgumentException {
        try {
            InstructorRegistration instructorRegistration = instructorRegistrationService
                    .getInstructorRegistrationById(id);
            instructorRegistrationService.removeInstructorFromClass(instructorRegistration.getInstructor().getId(),
                    instructorRegistration.getSession().getId());
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), e.getStatus());
        }
    }

    @GetMapping(value = { "/instructor-registration/{id}", "/instructor-registration/{id}" })
    public ResponseEntity<InstructorRegistrationDto> getInstructorRegistration(@PathVariable("id") int id) {
        try {
            InstructorRegistration instructorRegistration = instructorRegistrationService
                    .getInstructorRegistrationById(id);
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(instructorRegistration),
                    HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), e.getStatus());
        }
    }

}
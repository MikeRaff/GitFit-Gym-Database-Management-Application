package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.GymUserDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
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
                    instructorRegistrationDto.getSession().getId(), instructorRegistrationDto.getInstructor().getId());
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(instructorRegistration),
                    HttpStatus.CREATED);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), e.getStatus());
        }
    }
    /**
     * Delete an instructor Registration
     * @param id
     * @return A response entity containing the success of the method
     * @throws IllegalArgumentException
     * 
     */
    @DeleteMapping(value = { "/instructor-registration/delete/{id}/{gid}", "/instructor-registration/delete/{id}/{gid}/" })
    public ResponseEntity<InstructorRegistrationDto> removeInstructorFromClass(@PathVariable("id") int id, @PathVariable("gid") int gid)
            throws IllegalArgumentException {
        try {
            InstructorRegistration instructorRegistration = instructorRegistrationService
                    .getInstructorRegistrationById(id);
            instructorRegistrationService.removeInstructorFromClass(instructorRegistration.getInstructor().getId(),
                    instructorRegistration.getSession().getId(), gid);
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorRegistrationDto>(new InstructorRegistrationDto(), e.getStatus());
        }
    }
    /**
     * Get an instructor of a specific id
     * @param id
     * @return a response entity containing the Dto and the status
     */
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
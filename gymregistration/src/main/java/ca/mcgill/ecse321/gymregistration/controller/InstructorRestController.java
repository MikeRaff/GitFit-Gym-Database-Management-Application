package ca.mcgill.ecse321.gymregistration.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.service.InstructorService;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@CrossOrigin(origins = "*")
@RestController
public class InstructorRestController {
    @Autowired
    private InstructorService instructorService;

    /**
     * Finds and returns an instructor with a given id
     * 
     * @param id
     * @return Response Entity with the desired Instructor DTO
     */
    @GetMapping(value = { "/instructors/{id}", "/instructors/{id}/" })
    public ResponseEntity<InstructorDto> getInstructor(@PathVariable("id") int id) {
        try {
            Instructor instructor = instructorService.getInstructorById(id);
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }

    }

    /**
     * Finds and returns all instructors in the databse
     * 
     * @return
     */
    @GetMapping(value = { "/instructors", "/instructors/" })
    public List<InstructorDto> getAllInstructors() {
        return instructorService.getAllInstructors().stream().map(InstructorDto::new).collect(Collectors.toList());
    }

    /**
     * Creates and returns a new instructor
     * 
     * @param instructorDto
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/instructors/create", "/instructors/create/" })
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody InstructorDto instructorDto)
            throws IllegalArgumentException {
        try {
            Instructor instructor = instructorService.createInstructor(instructorDto.getEmail(),
                    instructorDto.getPassword(), instructorDto.getPerson().getId());
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.CREATED);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }
    }

    /**
     * Updates and returns an instructor
     * 
     * @param id
     * @param email
     * @param password
     * @return the instructor after it
     * @throws IllegalArgumentException
     */
    @PutMapping(value = { "/update-instructors-e/{id}/{email}",
            "/update-instructors-e/{id}/{email}/" })
    public ResponseEntity<InstructorDto> updateInstructorEmail(@PathVariable("id") int id,
            @PathVariable("email") String email)
            throws IllegalArgumentException {
        try {
            Instructor instructor = instructorService.updateInstructorEmail(id, email);
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }
    }

    @PutMapping(value = { "/update-instructors-p/{id}/{password}",
            "/update-instructors-p/{id}/{password}/" })
    public ResponseEntity<InstructorDto> updateInstructorPassword(@PathVariable("id") int id, @PathVariable("password") String password)
            throws IllegalArgumentException {
        try {
            Instructor instructor = instructorService.updateInstructorPassword(id, password);
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }
    }

    @DeleteMapping(value = { "/instructors/delete/{id}", "/instructors/delete/{id}/" })
    public ResponseEntity<InstructorDto> deleteInstructor(@PathVariable("id") int id) throws IllegalArgumentException {
        try {
            instructorService.deleteIntructor(id);
            return new ResponseEntity<InstructorDto>(new InstructorDto(), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }

    }

    @GetMapping(value = { "/instructor/log-in/{email}/{password}", "/instructor/log-in/{email}/{password}/" })
    public ResponseEntity<InstructorDto> logInInstructor(@PathVariable("email") String email,
            @PathVariable("password") String password) throws IllegalArgumentException {
        Instructor instructor;
        try {
            instructor = instructorService.logInInstructor(email, password);
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
        } catch (GRSException e) {
            return new ResponseEntity<InstructorDto>(new InstructorDto(), e.getStatus());
        }
    }
}

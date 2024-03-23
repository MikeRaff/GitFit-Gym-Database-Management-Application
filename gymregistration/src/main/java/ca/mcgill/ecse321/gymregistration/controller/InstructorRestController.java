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


@CrossOrigin(origins="*")
@RestController
public class InstructorRestController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping(value ={"/instructors/{id}", "/instructors/{id}/"})
    public ResponseEntity<InstructorDto> getInstructor(@PathVariable("id") int id)
    {
        Instructor instructor = instructorService.getInstructorById(id);

        return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
    }

    @GetMapping(value = { "/instructors", "/instructors/"})
    public List<InstructorDto> getAllInstructors() {
        return instructorService.getAllInstructors().stream().map(InstructorDto::new).collect(Collectors.toList());
    }

    @PostMapping(value = { "/instructors/create", "/instructors/create/"})
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody InstructorDto instructorDto) throws IllegalArgumentException{
        Instructor instructor = instructorService.createInstructor(instructorDto.getEmail(), instructorDto.getPassword(), instructorDto.getPerson().getId());
        return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.CREATED);
    }

    @PutMapping(value = {"/update-instructors/{id}/{email}/{password}", "/update-instructors/{id}/{email}/{password}/"})
    public ResponseEntity<InstructorDto> updateInstructor(@PathVariable("id") int id, @PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException{
        Instructor instructor = instructorService.updateInstructor(id, email, password);
        return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/instructors/delete/{id}", "/instructors/delete/{id}/"})
    public void deleteInstructor(@PathVariable("id") int id) throws IllegalArgumentException{
        instructorService.deleteIntructor(id);
    }

    @GetMapping(value = {"/instructor/log-in/{email}/{password}","/instructor/log-in/{email}/{password}/"})
    public ResponseEntity<InstructorDto> logInInstructor(@PathVariable("email") String email,@PathVariable("password") String password ) throws IllegalArgumentException{
        Instructor instructor;
        try {
            instructor = instructorService.logInInstructor(email, password);
            return new ResponseEntity<InstructorDto>(new InstructorDto(instructor), HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }
}

package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.service.ClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="*")
@RestController
public class ClassTypeRestController {
    @Autowired
    private ClassTypeService classTypeService;

    /**
     * GetAllClassTypes: getting all class types
     * @return All class types in database
     */
    @GetMapping(value = { "/class-types", "/class-types/"})
    public List<ClassTypeDto> getAllClassTypes() {
        return classTypeService.getAllClassTypes().stream().map(ClassTypeDto::new).collect(Collectors.toList());
    }

    /**
     * GetClassType
     * @param name: Name of class type to get
     * @return ClassType in system
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/class-types/{name}", "/class-types/{name}"})
    public ResponseEntity<ClassTypeDto> getClassType(@PathVariable("name") String name) throws IllegalArgumentException {
        ClassType classType = classTypeService.getClassTypeByName(name);
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.OK);
    }

    /**
     * CreateClassType: creating a class type
     * @param classTypeDto : class type dto to be created
     * @return Class type in system
     * @throws IllegalArgumentException
     */

    @PostMapping(value = { "/class-types/create", "/class-types/create/"})
    public ResponseEntity<ClassTypeDto> createClassType(@RequestBody ClassTypeDto classTypeDto) throws IllegalArgumentException{
        ClassType classType = classTypeService.createClassType(classTypeDto.getName(), classTypeDto.isApproved());
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.CREATED);
    }

    /**
     * UpdateClassType: updating an existing class type
     * @param name: name of class type to update
     * @param classTypeDto : class type dto to be updated
     * @return The updated class type
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/class-types/{name}", "/class-types/{name}/"})
    public ResponseEntity<ClassTypeDto> updateClassType(@PathVariable("name") String name, @RequestBody ClassTypeDto classTypeDto) throws IllegalArgumentException{
        ClassType toUpdate = classTypeService.getClassTypeByName(name);
        ClassType classType = classTypeService.updateClassType(toUpdate.getName(), classTypeDto.getName(), classTypeDto.isApproved());
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.OK);
    }

    /**
     * DeleteClassType: deleting a class type from the system
     * @param name: Name of class type to be deleted
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/class-types/delete/{name}", "/class-types/delete/{name}/"})
    public void deleteClassType(@PathVariable("name") String name) throws IllegalArgumentException{
        classTypeService.deleteClassType(name);
    }
}

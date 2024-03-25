package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
import ca.mcgill.ecse321.gymregistration.dto.GymUserDto;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Owner;
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
    @Autowired 
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    /**
     * GetAllClassTypes: getting all class types
     * @return All class types in database
     */
    @GetMapping(value = { "/class-types", "/class-types/"})
    public List<ClassTypeDto> getAllClassTypes() {
        return classTypeService.getAllClassTypes().stream().map(ClassTypeDto::new).collect(Collectors.toList());
    }

    /**
     * GetClassType: getting a class type by name
     * @param name: Name of class type to get
     * @return ClassType in system
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/class-types/{name}", "/class-types/{name}/"})
    public ResponseEntity<ClassTypeDto> getClassType(@PathVariable("name") String name) throws IllegalArgumentException {
        ClassType classType = classTypeService.getClassTypeByName(name);
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.OK);
    }

    /**
     * CreateClassType: creating a class type
     * @param classTypeDto : class type dto to be created
     * @param Email: email of user creating the class type
     * @return Class type in system
     * @throws IllegalArgumentException
     */

    @PostMapping(value = { "/class-types/create/{email}", "/class-types/create/{email}/"})
    public ResponseEntity<ClassTypeDto> createClassType(@PathVariable("email") String email, @RequestBody ClassTypeDto classTypeDto) throws IllegalArgumentException{
        Owner owner = ownerRepository.findOwnerByEmail(email);
        ClassType classType = classTypeService.createClassType(classTypeDto.getName(), classTypeDto.isApproved(), owner);
        System.out.println("classtype created");
        return new ResponseEntity<ClassTypeDto>(new ClassTypeDto(classType), HttpStatus.CREATED);
    }

    /**
     * ProposeClassType: proposing a class type
     * @param name: name of class type
     * @param gymUser: user proposing the class type
     * @return Proposed class type
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/class-types/propose", "/class-types/propose/"})
    public ResponseEntity<ClassTypeDto> proposeClassType(@RequestBody String name, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        System.out.println("post");
        ClassType classType = classTypeService.proposeClassType(name, gymUser);
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.CREATED);
    }

    /**
     * UpdateClassType: updating an existing class type
     * @param name: name of class type to update
     * @param classTypeDto : class type dto to be updated
     * @param gymUser: user updating the class type
     * @return The updated class type
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/class-types/{name}", "/class-types/{name}/"})
    public ResponseEntity<ClassTypeDto> updateClassType(@PathVariable("name") String name, @RequestBody ClassTypeDto classTypeDto, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        ClassType toUpdate = classTypeService.getClassTypeByName(name);
        ClassType classType = classTypeService.updateClassType(toUpdate.getName(), classTypeDto.getName(), classTypeDto.isApproved(), gymUser);
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.OK);
    }

    /**
     * ApproveProposedClassType: approving the proposed class type
     * @param name: name of class type
     * @param gymUserDto: user approving the class type
     * @return Approved class type
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/class-types/approve/{name}", "/class-types/approve/{name}/"})
    public ResponseEntity<ClassTypeDto> approveProposedClassType(@PathVariable("name") String name, @RequestBody GymUserDto gymUserDto) throws IllegalArgumentException{
        System.out.println("here1");
        GymUser gymUser = ownerRepository.findOwnerByEmail(gymUserDto.getEmail());
        System.out.println("here 2");
        ClassType classType = classTypeService.approveProposedClassType(name, gymUser);
        System.out.println("here 3");
        return new ResponseEntity<>(new ClassTypeDto(classType), HttpStatus.OK);
    }

    /**
     * DeleteClassType: deleting a class type from the system
     * @param name: Name of class type to be deleted
     * @param gymUser: user deleting the class type
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/class-types/delete/{name}", "/class-types/delete/{name}/"})
    public void deleteClassType(@PathVariable("name") String name, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        classTypeService.deleteClassType(name, gymUser);
    }
}

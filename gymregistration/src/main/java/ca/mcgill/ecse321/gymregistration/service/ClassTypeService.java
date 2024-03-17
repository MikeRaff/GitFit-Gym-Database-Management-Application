package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTypeService {

    @Autowired
    ClassTypeRepository classTypeRepository;

    private static final int MAX_CLASS_TYPES = 100;

    /**
     * CreateClassType: service method to create and store a class type in the database
     * @param name: name of class type
     * @param isApproved: approval of class type
     * @return created class type
     * @throws GRSException if attempt to create class type is invalid
     */
    @Transactional
    public ClassType createClassType(String name, boolean isApproved){
        if(name == null || name.trim().isEmpty()){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
        }
        if(!isApproved){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Class Type must be approved.");
        }
        if (classTypeRepository.count() >= MAX_CLASS_TYPES) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Maximum number of class types reached.");
        }
        if(classTypeRepository.findClassTypeByName(name) == null){
            throw new GRSException(HttpStatus.CONFLICT, "Class Type " + name + " already exists.");
        }
        ClassType classType = new ClassType();
        classType.setName(name);
        classType.setApproved(isApproved);
        classTypeRepository.save(classType);
        return classType;
    }

    /**
     * GetClassTypeByName: fetch an existing class type with a name
     * @param name: Name used to fetch class type
     * @return class type that is found
     * @throws GRSException class type not found
     */
    @Transactional
    public ClassType getClassTypeByName(String name){
        ClassType classType = classTypeRepository.findClassTypeByName(name);
        if (classType == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Class Type not found.");
        }
        return classType;
    }

    /**
     * GetAllClassTypes: getting all existing class types
     * @return List of all existing class types
     * @throws GRSException No class types found
     */
    @Transactional
    public List<ClassType> getAllClassTypes(){
        List<ClassType> classTypes = classTypeRepository.findAll();
        if(classTypes.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No Class Types found in the system.");
        }
        return classTypes;
    }

    /**
     * DeleteClassType: delete the class type
     * @param name: class type to be deleted
     * @throws GRSException Class type not found
     */
    @Transactional
    public void deleteClassType(String name){
        ClassType classType = classTypeRepository.findClassTypeByName(name);
        if(classType == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Class Type not found.");
        }
        classTypeRepository.deleteClassTypeByName(name);
    }

    /**
     * ProposeClassType: proposing a new class type
     * @param name: name of class type
     * @return the class type
     * @throws GRSException Invalid request for class type
     */
    @Transactional
    public ClassType proposeClassType(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
        }
        if (classTypeRepository.count() >= MAX_CLASS_TYPES) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Maximum number of class types reached.");
        }
        if (classTypeRepository.findClassTypeByName(name) != null) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Class Type " + name + " already exists.");
        }
        ClassType classType = new ClassType();
        classType.setName(name);
        classType.setApproved(false); // Not approved until owner approves it
        classTypeRepository.save(classType);
        return classType;
    }

    /**
     * ApproveProposedClassType: approving a proposed class type
     * @param name: class type to be approved
     * @throws GRSException Class type not found
     */
    @Transactional
    public void approveProposedClassType(String name){
        ClassType classType = classTypeRepository.findClassTypeByName(name);
        if(classType == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Class Type not found.");
        }
        classType.setApproved(true);
        classTypeRepository.save(classType);
    }

}

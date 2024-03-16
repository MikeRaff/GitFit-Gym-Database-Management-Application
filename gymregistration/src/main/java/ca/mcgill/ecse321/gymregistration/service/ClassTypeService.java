package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClassTypeService {

    @Autowired
    ClassTypeRepository classTypeRepository;

    /**
     * CreateClassType: service method to create and store a class type in the database
     * @param name: name of class type
     * @param isApproved: approval of class type
     * @return created class type
     * @throws Exception of name is null or class type already exists in repository
     */
    @Transactional
    public ClassType createClassType(String name, boolean isApproved) throws Exception {
        ClassType classType = new ClassType();
        classType.setName(name);
        classType.setApproved(isApproved);
        if(name == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Name cannot be empty.");
        }
        else if(classTypeRepository.findClassTypeByName(name) == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Class Type " + name + " already exists.");
        }
        classTypeRepository.save(classType);
        return classType;
    }
}

package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestClassTypeService {
    @Mock
    private ClassTypeRepository classTypeRepository;

    @InjectMocks
    private ClassTypeService classTypeService;


    private static final String NAME = "Pilates";
    private static final boolean IS_APPROVED = true;

    private final ClassType CLASSTYPE = new ClassType();

    @BeforeEach
    public void setMockOutput() {
        lenient().when(classTypeRepository.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            CLASSTYPE.setName(NAME);
            CLASSTYPE.setApproved(IS_APPROVED);

            List<ClassType> classTypeList = new ArrayList<>();
            classTypeList.add(CLASSTYPE);
            return classTypeList;
        });

        lenient().when(classTypeRepository.findClassTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(NAME)) {
                ClassType classType = new ClassType();
                classType.setName(NAME);
                classType.setApproved(IS_APPROVED);
                return classType;
            } else {
                return null;
            }
        });

        lenient().when(classTypeRepository.save(any(ClassType.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    }

    @Test
    public void testCreateClasstype(){
        String classtypeName = "Aerobics";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
        } catch (GRSException e){
            fail(e.getMessage());
        }

        assertNotNull(classType);
        assertEquals(classtypeName, classType.getName());
        assertEquals(classtypeIsApproved, classType.getIsApproved());
    }

    @Test
    public void testCreateClasstypeNameNull() {
        String classtypeName = null;
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            // an error should occur as name is null
            assertEquals(e.getMessage(), "Name cannot be empty.");
        }

        assertNull(classType);
    }

}

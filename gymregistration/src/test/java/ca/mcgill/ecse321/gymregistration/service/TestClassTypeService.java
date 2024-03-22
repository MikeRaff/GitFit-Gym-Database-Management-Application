package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestClassTypeService {
    @Mock
    private ClassTypeRepository classTypeRepository;

    @InjectMocks
    private ClassTypeService classTypeService;


    private static final String NAME = "Pilates";
    private static final boolean IS_APPROVED = true;

    private final ClassType CLASSTYPE = new ClassType();

    private static final int MAX_CLASS_TYPES = 100;

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

    @Test
    public void testCreateClasstypeNotApproved() {
        String classtypeName = "Aerobics";
        boolean classtypeIsApproved = false;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            // an error should occur as class type is not approved
            assertEquals(e.getMessage(), "Class Type must be approved.");
        }

        assertNull(classType);
    }

    @Test
    public void createClassTypeMaxReached() {
        List<ClassType> classTypes = new ArrayList<>();
        for (int i = 0; i < MAX_CLASS_TYPES; i++) {
            ClassType addedClassType = new ClassType();
            addedClassType.setName("ClassType" + i);
            addedClassType.setApproved(true);
            classTypes.add(addedClassType);
        }
        when(classTypeRepository.findAll()).thenReturn(classTypes);

        String classtypeName = "Aerobics";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            // an error should occur as there are 100 class types in the database
            assertEquals(e.getMessage(), "Maximum number of class types reached.");
        }

        verify(classTypeRepository, never()).save(any());

        assertNull(classType);
    }

    @Test
    public void testCreateClasstypeAlreadyExists() {
        String classtypeName = "Pilates";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            // an error should occur as class type already exists
            assertEquals(e.getMessage(), "Class Type " + classtypeName + " already exists.");
        }
        assertNull(classType);
    }

    @Test
    public void testCreateClasstypeEmpty() {
        String classtypeName = "";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.createClassType(classtypeName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            // an error should occur as class type is empty
            assertEquals(e.getMessage(), "Name cannot be empty.");
        }
        assertNull(classType);
    }

    @Test
    public void testUpdateClasstype() {
        String classtypeOldName = "Pilates";
        String classtypeNewName = "Gym";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.updateClassType(classtypeOldName, classtypeNewName, classtypeIsApproved);
        } catch (GRSException e) {
            fail(e.getMessage());
        }
        assertNotNull(classType);
        assertEquals(classtypeNewName, classType.getName());
        assertEquals(classtypeIsApproved, classType.getIsApproved());
    }
    @Test
    public void testUpdateClasstypeNewNameNull() {
        String classtypeOldName = "Pilates";
        String classtypeNewName = null;
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.updateClassType(classtypeOldName, classtypeNewName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            assertEquals(e.getMessage(), "Name cannot be empty.");
        }

        assertNull(classType);
    }
    @Test
    public void testUpdateClasstypeNewNameEmpty() {
        String classtypeOldName = "Pilates";
        String classtypeNewName = "";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.updateClassType(classtypeOldName, classtypeNewName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            assertEquals(e.getMessage(), "Name cannot be empty.");
        }

        assertNull(classType);
    }

    @Test
    public void testUpdateClasstypeNotApproved() {
        String classtypeOldName = "Pilates";
        String classtypeNewName = "Gym";
        boolean classtypeIsApproved = false;

        ClassType classType = null;

        try {
            classType = classTypeService.updateClassType(classtypeOldName, classtypeNewName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            assertEquals(e.getMessage(), "Class Type must be approved.");
        }

        assertNull(classType);
    }

    @Test
    public void testUpdateClasstypeDoesNotExist() {
        String classtypeOldName = "Aerobics";
        String classtypeNewName = "Gym";
        boolean classtypeIsApproved = true;

        ClassType classType = null;

        try {
            classType = classTypeService.updateClassType(classtypeOldName, classtypeNewName, classtypeIsApproved);
            fail();
        } catch (GRSException e) {
            assertEquals(e.getMessage(), "Class Type " + classtypeOldName + " does not exist.");
        }

        assertNull(classType);
    }

    @Test
    public void testGetClassType() {
        ClassType classType = null;
        try {
            classType = classTypeService.getClassTypeByName(NAME);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(classType);
        assertEquals(NAME, classType.getName());
        assertEquals(IS_APPROVED, classType.getIsApproved());
    }

    @Test
    public void testGetClassTypeNameNull() {
        String name = null;
        ClassType classType = null;
        try {
            classType = classTypeService.getClassTypeByName(name);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Class Type not found.");
        }
        assertNull(classType);
    }

    @Test
    public void testGetClassTypeNameEmpty() {
        String name = "";
        ClassType classType = null;
        try {
            classType = classTypeService.getClassTypeByName(name);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Class Type not found.");
        }
        assertNull(classType);
    }

    @Test
    public void testGetAllClassTypesOneClassTypes(){
        List<ClassType> classTypes = new ArrayList<>();
        try {
            classTypes = classTypeService.getAllClassTypes();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(classTypes.stream().map(ClassType::getName).collect(Collectors.toList()).contains("Pilates"));
    }

    @Test
    public void testGetAllClassTypesMultiple(){
        List<ClassType> addedClasses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ClassType addedClassType = new ClassType();
            addedClassType.setName("ClassType " + i);
            addedClassType.setApproved(true);
            addedClasses.add(addedClassType);
        }
        when(classTypeRepository.findAll()).thenReturn(addedClasses);

        List<ClassType> classTypes = new ArrayList<>();
        try {
            classTypes = classTypeService.getAllClassTypes();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(classTypes.stream().map(ClassType::getName).collect(Collectors.toList()).contains("ClassType 0"));
        assertTrue(classTypes.stream().map(ClassType::getName).collect(Collectors.toList()).contains("ClassType 1"));
        assertTrue(classTypes.stream().map(ClassType::getName).collect(Collectors.toList()).contains("ClassType 2"));
    }

    @Test
    public void testGetAllClassTypeNone(){
        List<ClassType> addedClasses = new ArrayList<>();
        when(classTypeRepository.findAll()).thenReturn(addedClasses);

        List<ClassType> classTypes = new ArrayList<>();
        try {
            classTypes = classTypeService.getAllClassTypes();
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "No Class Types found in the system.");
        }
        assertEquals(0, classTypes.size());
    }
}

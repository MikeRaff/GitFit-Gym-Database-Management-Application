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
import org.springframework.http.HttpStatus;

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

    /**
     * Creating class type success
     */
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

    /**
     * creating class type with null name
     */
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
    /**
     * creating class type with empty name
     */
    @Test
    public void testCreateClasstypeNameEmpty() {
        String classtypeName = "";
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

    /**
     * creating class type when not approved
     */
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

    /**
     * creating class time when max reached
     */
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

    /**
     * creating class type when it already exists
     */
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

    /**
     * creating class type with empty name
     */
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

    /**
     * updating class type
     */
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

    /**
     * updating class type with null name
     */
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

    /**
     * updating class type with empty name
     */
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

    /**
     * updating class type not approved
     */
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

    /**
     * updating class type does not exist
     */
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

    /**
     * getting class type
     */
    @Test
    public void testGetClassType() {
        ClassType classType = null;
        try {
            classType = classTypeService.getClassTypeByName(NAME);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(classType);
        assertEquals(NAME, classType.getName());
        assertEquals(IS_APPROVED, classType.getIsApproved());
    }

    /**
     * getting class type name null
     */
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

    /**
     * getting class type name empty
     */
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

    /**
     * getting all class types when one in database
     */
    @Test
    public void testGetAllClassTypesOne(){
        List<ClassType> classTypes = new ArrayList<>();
        try {
            classTypes = classTypeService.getAllClassTypes();
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(classTypes.stream().map(ClassType::getName).collect(Collectors.toList()).contains("Pilates"));
    }

    /**
     * getting all class types when multiple in database
     */
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

    /**
     * getting all class types when none in database
     */
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

    /**
     * deleting class type
     */
    @Test
    public void testDeleteClassType() {
        String classTypeName = "Pilates";
        try {
            classTypeService.deleteClassType(classTypeName);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        verify(classTypeRepository, times(1)).deleteClassTypeByName(classTypeName);
    }

    /**
     * deleting class type when it does not exist
     */
    @Test
    public void testDeleteClassTypeDoesNotExist() {
        String classTypeName = "Gym";
        try {
            classTypeService.deleteClassType(classTypeName);
            fail();
        } catch (GRSException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals("Class Type not found.", e.getMessage());
            verify(classTypeRepository, times(0)).deleteClassTypeByName(classTypeName);
        }
    }

    /**
     * proposing a class type
     */
    @Test
    public void testProposeClassType(){
        String proposedName = "Aerobics";
        ClassType classType = null;
        try {
            classType = classTypeService.proposeClassType(proposedName);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(classType);
        assertEquals("Aerobics", classType.getName());
        assertEquals(false, classType.getIsApproved());
    }

    /**
     * proposing class type with no name
     */
    @Test
    public void testProposeClassTypeNoName(){
        String proposedName = null;
        ClassType classType = null;
        try {
            classType = classTypeService.proposeClassType(proposedName);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name cannot be empty.");
            assertNull(classType);
        }
    }

    /**
     * proposing class type with empty name
     */
    @Test
    public void testProposeClassTypeEmptyName(){
        String proposedName = "";
        ClassType classType = null;
        try {
            classType = classTypeService.proposeClassType(proposedName);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name cannot be empty.");
            assertNull(classType);
        }
    }

    /**
     * proposing class type that already exists
     */
    @Test
    public void testProposeClassTypeExists(){
        String proposedName = NAME;
        ClassType classType = null;
        try {
            classType = classTypeService.proposeClassType(proposedName);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Class Type " + NAME + " already exists.");
            assertNull(classType);
        }
    }

    /**
     * propose class type when at max in database
     */
    @Test
    public void testProposeClassTypeMax(){
        List<ClassType> classTypes = new ArrayList<>();
        for (int i = 0; i < MAX_CLASS_TYPES; i++) {
            ClassType addedClassType = new ClassType();
            addedClassType.setName("ClassType" + i);
            addedClassType.setApproved(true);
            classTypes.add(addedClassType);
        }
        when(classTypeRepository.findAll()).thenReturn(classTypes);

        String proposedName = NAME;
        ClassType classType = null;
        try {
            classType = classTypeService.proposeClassType(proposedName);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Maximum number of class types reached.");
            assertNull(classType);
        }
    }

    /**
     * approve proposed class type
     */
    @Test
    public void testApproveProposedClassType() {
        String classTypeName = NAME;
        ClassType classType = null;
        try {
            classType = classTypeService.approveProposedClassType(classTypeName);
        } catch (Exception e){
            fail();
        }
        assertNotNull(classType);
        assertEquals(classTypeName, classType.getName());
        assertEquals(true, classType.getIsApproved());
    }

    /**
     * approve proposed class type when it does not exist
     */
    @Test
    public void testApproveProposedClassTypeNoExist() {
        String classTypeName = "Gym";
        ClassType classType = null;
        try {
            classType = classTypeService.approveProposedClassType(classTypeName);
            fail();
        } catch (Exception e){
            assertNull(classType);
            assertEquals(e.getMessage(), "Class Type not found.");
        }
    }
}

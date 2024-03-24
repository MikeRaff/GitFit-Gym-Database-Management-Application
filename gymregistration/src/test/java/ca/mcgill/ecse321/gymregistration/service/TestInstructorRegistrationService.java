package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class TestInstructorRegistrationService {
    @Mock
    private InstructorRegistrationRepository instructorRegistrationRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private static InstructorRegistrationService instructorRegistrationService = new InstructorRegistrationService();

    private static final InstructorRegistration INSTRUCTORREGISTRATION = new InstructorRegistration();

    private static final int ID = INSTRUCTORREGISTRATION.getId();

    private static final Instructor INSTRUCTOR = new Instructor();

    private static final int INSTRUCTOR_ID = INSTRUCTOR.getId();

    private static final Session SESSION = new Session();

    private static final int SESSION_ID = SESSION.getId();

    @BeforeEach
    public void setMockOutput() {
        // Stubbing findInstructorById method
        lenient().when(instructorRepository.findInstructorById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if (id == INSTRUCTOR_ID) { // Replace INSTRUCTOR_ID with your expected instructor ID
                Instructor instructor = new Instructor();
                instructor.setId(id);
                instructor.setEmail(null);
                return instructor;
            } else {
                return null;
            }
        });

        // Stubbing findSessionById method
        lenient().when(sessionRepository.findSessionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if (id == SESSION_ID) { // Replace SESSION_ID with your expected session ID
                Session session = new Session();
                session.setId(id);
                return session;
            } else {
                return null;
            }
        });

        // Stubbing findInstructorRegistrationById method
        lenient().when(instructorRegistrationRepository.findInstructorRegistrationById(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    int id = invocation.getArgument(0);
                    if (id == ID) {
                        InstructorRegistration instructorRegistration = new InstructorRegistration();
                        instructorRegistration.setId(ID);
                        return instructorRegistration;
                    } else {
                        return null;
                    }
                });

        lenient().when(instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(anyInt(),
                anyInt())).thenAnswer((InvocationOnMock invocation) -> {
                    int instructorId = invocation.getArgument(0);
                    int sessionId = invocation.getArgument(1);

                    // Customize your behavior based on instructorId and sessionId
                    if (instructorId == INSTRUCTOR_ID && sessionId == SESSION_ID) {
                        InstructorRegistration instructorRegistration = new InstructorRegistration();
                        instructorRegistration.setSession(SESSION);
                        instructorRegistration.setInstructor(INSTRUCTOR);
                        return instructorRegistration;
                    } else {
                        return null;
                    }
                });

        // Stubbing save methods to return the saved object
        lenient().when(instructorRegistrationRepository.save(any(InstructorRegistration.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    return invocation.getArgument(0); // Return the saved object
                });
        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0); // Return the saved object
        });
        lenient().when(sessionRepository.save(any(Session.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0); // Return the saved object
        });

        // Whenever anything is saved, just return the parameter object

    }

    @Test
    public void testRegisterInstructorForClass() {
        Session session = new Session();
        Instructor instructor = new Instructor(null, null, null);
        instructorRepository.save(instructor);
        sessionRepository.save(session);
        lenient()
                .when(instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(anyInt(),
                        anyInt()))
                .thenReturn(null);
        InstructorRegistration instructorRegistration = instructorRegistrationService
                .registerInstructorForClass(session.getId(), instructor.getId());
        assertNotNull(instructorRegistration);
        assertEquals(session.getId(), instructorRegistration.getSession().getId());
        assertEquals(instructor.getId(), instructorRegistration.getInstructor().getId());
    }

    @Test
    public void testRemoveInstructorFromClass() {
        Session session = new Session();
        Instructor instructor = new Instructor(null, null, null);

        instructorRepository.save(instructor);
        sessionRepository.save(session);
        lenient()
                .when(instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(anyInt(),
                        anyInt()))
                .thenReturn(null);
        InstructorRegistration instructorRegistration = instructorRegistrationService
                .registerInstructorForClass(session.getId(), instructor.getId());
        try {
            instructorRegistrationService.removeInstructorFromClass(instructorRegistration.getSession().getId(),
                    instructorRegistration.getInstructor().getId().intValue(),instructorRegistration.getInstructor().getId().intValue());
        } catch (GRSException e) {
            assertEquals(e.getMessage(), "not enough instructors registered");
        }
    }

    @Test
    public void testGetInstructorRegistration() {
        Session session = new Session();
        Instructor instructor = new Instructor();
        instructor = instructorRepository.save(instructor);
        session = sessionRepository.save(session);
        InstructorRegistration instructorRegistration = new InstructorRegistration(null, instructor, session);
        instructorRegistration = instructorRegistrationRepository.save(instructorRegistration);
        InstructorRegistration newInstructorRegistration = instructorRegistrationService
                .getInstructorRegistration(instructor.getId(), session.getId());
        assertNotNull(newInstructorRegistration);

    };

    @Test
    public void testRegisterInstructorForClassInvalid() {
        Session session = new Session();
        Instructor instructor = new Instructor();

        try {
            instructorRegistrationService.registerInstructorForClass(1000, 1000);
        } catch (GRSException e) {
            assertEquals("Instructor not found", e.getMessage());
        }
        instructor = instructorRepository.save(instructor);
        try {
            instructorRegistrationService.registerInstructorForClass(1000, instructor.getId());
        } catch (GRSException e) {
            assertEquals("Session not found", e.getMessage());
        }

        try {
            instructorRegistrationService.registerInstructorForClass(session.getId(), instructor.getId());
        } catch (GRSException e) {
            assertEquals("already registered", e.getMessage());
        }
    }
}

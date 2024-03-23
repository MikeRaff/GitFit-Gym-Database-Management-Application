package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestSessionService {
    @Mock
    private SessionRepository sessionRepository;
    @InjectMocks
    private SessionService sessionService;
    private static final int ID = 123;
    private static final Date DATE = new Date(1700000000L * 1000); //Random date
    private static final Time START_TIME = Time.valueOf("12:00:00");
    private static final Time END_TIME = Time.valueOf("15:00:00");
    private static final String DESCRIPTION = "Example session.";
    private static final String NAME = "Tom";
    private static final String LOCATION = "Montreal";
    private static final ClassType CLASS_TYPE = new ClassType("Pilates", true);
    private static final int CAPACITY = 50;
    private final Session SESSION = new Session();

    @BeforeEach
    public void setMockOutput() {
        lenient().when(sessionRepository.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            SESSION.setId(ID);
            SESSION.setCapacity(CAPACITY);
            SESSION.setName(NAME);
            SESSION.setLocation(LOCATION);
            SESSION.setClassType(CLASS_TYPE);
            SESSION.setEndTime(END_TIME);
            SESSION.setStartTime(START_TIME);
            SESSION.setDescription(DESCRIPTION);
            SESSION.setDate(DATE);

            List<Session> sessionList = new ArrayList<>();
            sessionList.add(SESSION);
            return sessionList;
        });

        lenient().when(sessionRepository.findSessionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID)){
                Session session = new Session();
                session.setId(ID);
                session.setCapacity(CAPACITY);
                session.setName(NAME);
                session.setLocation(LOCATION);
                session.setClassType(CLASS_TYPE);
                session.setEndTime(END_TIME);
                session.setStartTime(START_TIME);
                session.setDescription(DESCRIPTION);
                session.setDate(DATE);
                return session;
            } else{
                return null;
            }
        });
        lenient().when(sessionRepository.save(any(Session.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    }

}

package ca.mcgill.ecse321.gymregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;


@ExtendWith(MockitoExtension.class)
public class TestCustomerRegistrationService {
    
    @Mock
    private CustomerRegistrationRepository customerRegistrationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private CustomerRegistrationService customerRegistrationService;

    private static final Customer CUSTOMER = new Customer(); 
    private static final String CUSTOMER_EMAIL = CUSTOMER.getEmail();

    private static final Session SESSION = new Session();
    private static final int SESSION_ID = SESSION.getId();

    private static final CustomerRegistration CUSTOMER_REGISTRATION = new CustomerRegistration();
    private static final int CUSTOMER_REGISTRATION_ID = CUSTOMER_REGISTRATION.getId();

    
    @BeforeEach
    public void setMockOutput(){

        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) ->{
            if( invocation.getArgument(0).equals(CUSTOMER_EMAIL)){
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_EMAIL);
                return customer;
            }else{
                return null;
            }
        });

        lenient().when(sessionRepository.findSessionById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if(id == SESSION_ID){
                Session session = new Session();
                session.setId(SESSION_ID);
                return session;
            }else{
                return null;
            }
        });

        lenient().when(customerRegistrationRepository.findCustomerRegistrationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if(id == CUSTOMER_REGISTRATION_ID){
                CustomerRegistration customerRegistration = new CustomerRegistration();
                customerRegistration.setId(CUSTOMER_REGISTRATION_ID);
                return customerRegistration;
            }else{
                return null;
            }
        });

        lenient().when(customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(any(Customer.class),  any(Session.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(CUSTOMER) && invocation.getArgument(1).equals(SESSION)){
                CustomerRegistration customerRegistration = new CustomerRegistration();
                customerRegistration.setCustomer(CUSTOMER);
                customerRegistration.setSession(SESSION);
                return customerRegistration;
            }else{
                return null;
            }
        });

        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        lenient().when(sessionRepository.save(any(Session.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        lenient().when(customerRegistrationRepository.save(any(CustomerRegistration.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

    }


    @Test
    public void testRegisterCustomerForSession(){
        Session session = new Session(null, null, null, CUSTOMER_EMAIL, CUSTOMER_EMAIL, CUSTOMER_EMAIL, null);
        Date date = new Date(2024, 4, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        int capacity = 100;
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setCapacity(100);

        Customer customer = new Customer();
        String email = "customer@email.ca";
        int creditCardNumber = 1234;
        customer.setEmail(email);
        customer.setCreditCardNumber(creditCardNumber);

        //sessionRepository.save(session);
        //customerRepository.save(customer);
        sessionRepository.save(session);
        customerRepository.save(customer);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
        }catch (GRSException e){
            fail(e.getMessage());
        }
        assertNotNull(customer);
        assertEquals(customer.getId(), customerRegistration.getCustomer().getId());
        assertEquals(session.getId(), customerRegistration.getSession().getId());
    }

    @Test
    public void testRegisterForNullSession(){
        Session session = new Session();
        Customer customer = new Customer();
    }

}

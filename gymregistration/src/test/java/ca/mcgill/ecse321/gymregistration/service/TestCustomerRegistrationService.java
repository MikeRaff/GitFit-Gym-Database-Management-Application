package ca.mcgill.ecse321.gymregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ReactiveAdapter;

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
    private static final String CUSTOMER_EMAIL = "customer@email.com";
    private static final String CREDIT = "1234 5678 1234 5678";

    private static final Customer CUSTOMER_OTHER = new Customer(); 
    private static final String CUSTOMER_EMAIL_OTHER = "otherCustomer@email.com";
    private static final String CREDIT_OTHER = "8765 4321 8765 4321";


    private static final Session SESSION = new Session();
    private static final int SESSION_ID = 0;
    private static final Date DATE = new Date(2024, 4, 1);
    private static final Time START_TIME= new Time(12,0,0);
    private static final Time END_TIME = new Time(13,0,0);
    private static final int CAPACITY = 100;

    //private static final CustomerRegistration CUSTOMER_REGISTRATION = new CustomerRegistration();
    private static final int CUSTOMER_REGISTRATION_ID = 0;
    private static final Date CURRENT_DATE = new Date(System.currentTimeMillis());

    
    @BeforeEach
    public void setMockOutput(){

        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        lenient().when(sessionRepository.save(any(Session.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        lenient().when(customerRegistrationRepository.save(any(CustomerRegistration.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));


        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) ->{
            if( invocation.getArgument(0).equals(CUSTOMER_EMAIL)){
                Customer customer = new Customer();
                customer.setEmail(CUSTOMER_EMAIL);
                customer.setCreditCardNumber(CREDIT);

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
                session.setDate(DATE);
                session.setStartTime(START_TIME);
                session.setEndTime(END_TIME);
                session.setCapacity(CAPACITY);

                return session;
            }else{
                return null;
            }
        });

        lenient().when(customerRegistrationRepository.findCustomerRegistrationsBySession_Id(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if(id == SESSION_ID){
                CustomerRegistration customerRegistration = new CustomerRegistration();
                customerRegistration.setId(CUSTOMER_REGISTRATION_ID);
                customerRegistration.setSession(SESSION);
                customerRegistration.setCustomer(CUSTOMER);
                customerRegistration.setDate(CURRENT_DATE);

                List<CustomerRegistration> customerRegistrationList= new ArrayList<>();
                customerRegistrationList.add(customerRegistration);
                return customerRegistrationList;
            }else{
                return null;
            }
        });

        lenient().when(customerRegistrationRepository.findCustomerRegistrationById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if(id == CUSTOMER_REGISTRATION_ID){
                CustomerRegistration customerRegistration = new CustomerRegistration();
                customerRegistration.setId(CUSTOMER_REGISTRATION_ID);
                customerRegistration.setSession(SESSION);
                customerRegistration.setCustomer(CUSTOMER);
                customerRegistration.setDate(CURRENT_DATE);

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

        
    }


    @Test
    public void testRegisterCustomerForSession(){
        Session session = new Session(null, null, null, CUSTOMER_EMAIL, CUSTOMER_EMAIL, CUSTOMER_EMAIL, null);
        Date date = new Date(2024, 4, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setCapacity(CAPACITY);

        Customer customer = new Customer();
        customer.setEmail("customer@email.ca");
        customer.setCreditCardNumber(1234);

        sessionRepository.save(session);
        customerRepository.save(customer);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistration = customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
        }catch (GRSException e){
            fail(e.getMessage());
        }
        assertNotNull(customerRegistration);
        assertEquals(customer.getId(), customerRegistration.getCustomer().getId());
        assertEquals(session.getId(), customerRegistration.getSession().getId());
    }

    @Test
    public void testRegisterForNonexistentSession(){
        Session session = null;

        Customer customer = new Customer();
        customer.setEmail("customer@email.com");
        customer.setCreditCardNumber(1234);

        sessionRepository.save(session);
        customerRepository.save(customer);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
            fail();
        }catch (GRSException e){
            assertEquals("Session not found.", e.getMessage());
        }
        assertNull(customerRegistration);
    }

    @Test
    public void testRegisterForPastSession(){
        Session session = new Session();
        Date date = new Date(2023, 4, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setCapacity(CAPACITY);

        Customer customer = new Customer();
        customer.setEmail("customer@email.com");
        customer.setCreditCardNumber(1234);

        sessionRepository.save(session);
        customerRepository.save(customer);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
            fail();
        }catch (GRSException e){
            assertEquals("Cannot register for in-progress or completed session.", e.getMessage());
        }
        assertNull(customerRegistration);
    }

    @Test
    public void testRegisterForSessionNullCreditInformation(){
        Customer customer = CUSTOMER;
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setCreditCardNumber(null);

        Session session = SESSION;
        session.setId(SESSION_ID);
        session.setCapacity(CAPACITY);
        session.setDate(SESSION_DATE);
        session.setStartTime(START_TIME);
        session.setEndTime(END_TIME);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
            fail();
        }catch (GRSException e){
            assertEquals("Credit card must be entered to register for a class.", e.getMessage());
        }
        assertNull(customerRegistration);
    }

    @Test
    public void testRegisterForSessionEmptyCreditInformation(){
        Customer customer = CUSTOMER;
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setCreditCardNumber("");

        Session session = SESSION;
        session.setId(SESSION_ID);
        session.setCapacity(CAPACITY);

        Customer customer = new Customer();
        customer.setEmail("customer@email.com");

        sessionRepository.save(session);
        customerRepository.save(customer);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
            fail();
        }catch (GRSException e){
            assertEquals("Credit card must be entered to register for a class.", e.getMessage());
        }
        assertNull(customerRegistration);
    }
    
    @Test
    public void testRegisterForSessionAtCapacity(){
        Session session = new Session();
        Date date = new Date(2024, 4, 1);
        Time startTime = new Time(12, 0, 0);
        Time endTime = new Time(13, 0, 0);
        session.setDate(date);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setCapacity(CAPACITY);
    
        List<CustomerRegistration> registrations = new ArrayList<>();
        for (int i=0; i<CAPACITY; i++){
            Customer addedCustomer = new Customer();
            addedCustomer.setEmail(i+"@email.com");

            // assign credit card number for each customer
            switch (String.valueOf(i).length()) {
                case 1:
                    addedCustomer.setCreditCardNumber(String.valueOf(i) + "000 5678 1234 5678");
                    break;
            
                case 2:
                    addedCustomer.setCreditCardNumber(String.valueOf(i) + "00 5678 1234 5678");
                    break;

                case 3:
                    addedCustomer.setCreditCardNumber(String.valueOf(i) + "0 5678 1234 5678");
                    break;

                default:
                    break;
            }

            when(customerRepository.findCustomerByEmail(anyString())).thenReturn(addedCustomer);

            CustomerRegistration addedRegistration = new CustomerRegistration();
            addedRegistration = customerRegistrationService.registerCustomerToSession(session.getId(), addedCustomer.getEmail());
            registrations.add(addedRegistration);
        }
        when(customerRegistrationRepository.findCustomerRegistrationsBySession_Id(session.getId())).thenReturn(registrations);

        Customer customer = new Customer();
        customer.setEmail("customer@email.com");
        customer.setCreditCardNumber(1234);

        customerRepository.save(customer);
        sessionRepository.save(session);

        CustomerRegistration customerRegistration = null;
        try{
            customerRegistrationService.registerCustomerToSession(session.getId(), customer.getEmail());
            fail();
        }catch (GRSException e){
            assertEquals("Session is already at capacity.", e.getMessage());
        }
        assertNull(customerRegistration);
    }

    

}

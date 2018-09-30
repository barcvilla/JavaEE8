/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.apress.ejb.entities.Address;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.service.CustomerService;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PC
 */
public class CustomerServiceTest {
    private CustomerService customerService;
    
    EJBContainer contenedor;
    
    @Before
    public void setUp() throws Exception
    {
        contenedor = EJBContainer.createEJBContainer();
        customerService = (CustomerService)
                contenedor.getContext().lookup("java:global/classes/CustomerServiceImpl!com.apress.ejb.service.CustomerService");
    }
    
    @Test
    public void testEJB()
    {
        getAllCustomerTest();
        //addCustomerTest();
    }
    
    public void getAllCustomerTest()
    {
        System.out.println("Iniciando Test Get All Customers");
        assertTrue(customerService != null);
        assertEquals(1, customerService.getCustomerFindAll().size());
        System.out.println("El no de Customers es igual a " + customerService.getCustomerFindAll().size());
        this.showCustomers(customerService.getCustomerFindAll());
        System.out.println("Fin test EJB CustomerService");
    }
    
    public void addCustomerTest()
    {
        System.out.println("Iniciando Test Add Customer");
        assertTrue(customerService != null);
        
        Address address = new Address();
        address.setId(3);
        address.setStreet1("Calle 123");
        address.setStreet2("Calle 234");
        address.setCity("BA");
        address.setZipCode(123);
        
        Customer cus = new Customer();
        cus.setName("Mariana Prado");
        cus.setShippingAddress(address);
        cus.setEmail("m.prado@gmail.com");
        cus.setBillingAddress(address);
        
        customerService.addCustomer(cus);
        
        assertEquals(3, customerService.getCustomerFindAll().size());
        this.showCustomers(customerService.getCustomerFindAll());
    }
    
    public void showCustomers(List<Customer> customers)
    {
        for(Customer customer : customers)
        {
            System.out.println(customer.getName());
        }
    }
}
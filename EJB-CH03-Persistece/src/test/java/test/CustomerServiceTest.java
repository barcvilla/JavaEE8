/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.apress.ejb.entities.Address;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.CustomerOrder;
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
        //addCustomerTest();
        //getAllCustomerTest();
        getCustomerByIdTest(1);
    }
    
    public void getAllCustomerTest()
    {
        System.out.println("Iniciando Test Get All Customers");
        assertTrue(customerService != null);
        assertEquals(2, customerService.getCustomerFindAll().size());
        System.out.println("El no de Customers es igual a " + customerService.getCustomerFindAll().size());
        this.showCustomers(customerService.getCustomerFindAll());
        System.out.println("Fin test EJB CustomerService");
    }
    
    public void addCustomerTest()
    {
        System.out.println("Iniciando Test Add Customer");
        assertTrue(customerService != null);
        
        Address shippinAddress = new Address();
        shippinAddress.setStreet1("La Habana 382");
        shippinAddress.setStreet2("Y Quiroz");
        shippinAddress.setCity("Jose C. Paz");
        shippinAddress.setState("BA");
        shippinAddress.setZipCode(1665);
        
        Address billingAddress = new Address();
        billingAddress.setStreet1("AV. Calle Libertador");
        billingAddress.setStreet2("Y Rivadabia 6000");
        billingAddress.setCity("Don Torcuato");
        billingAddress.setState("BA");
        billingAddress.setZipCode(1689);
        
        Customer cus = new Customer();
        cus.setName("Carlos Palomino Jonas");
        cus.setShippingAddress(shippinAddress);
        cus.setEmail("c.palomino@gmail.com");
        cus.setBillingAddress(billingAddress);
        
        customerService.addCustomer(cus);
    }
    
    public void getCustomerByIdTest(int id)
    {
        Customer customer = customerService.getCustomerFindById(id);
        showCustomerOrderDetail(customer);
    }
    
    private void showCustomerOrderDetail(Customer customer)
    {
        System.out.println("Lista de Ordenes para el cliente: " + customer.getName());
        for(CustomerOrder order : customer.getCustomerOrders())
        {
            System.out.println("Order Id: "+order.getId()+ " date: "+order.getCreationDate()+ " Status: "+order.getStatus()+ " Total: "+order.getTotal());
        }
    }
    
    private void showCustomers(List<Customer> customers)
    {
        for(Customer customer : customers)
        {
            System.out.println(customer.getName());
            if(customer.getCustomerOrders().size() > 0)
            {
                System.out.println("Los pedidos para el cliente:  '"+ customer.getName() +"'\t");
                for(CustomerOrder order : customer.getCustomerOrders())
                {
                    System.out.println("Order Id: "+order.getId()+ " date: "+order.getCreationDate()+ " Status: "+order.getStatus()+ " Total: "+order.getTotal());
                }
            }
            else
            {
                System.out.println("El cliente '" + customer.getName() + "' no tiene pedidos registrados");
            }
            
        }
    }
}
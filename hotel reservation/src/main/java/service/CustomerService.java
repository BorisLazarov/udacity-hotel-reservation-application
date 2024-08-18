package service;

import model.customer.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerService {

    private static final CustomerService reference = new CustomerService();
    private final Set<Customer> customers;

    private CustomerService(){
        this.customers = new HashSet<>();
    }

    public static CustomerService getInstance(){
        return reference;
    }

    public void addCustomer(String email, String firstName, String lastName){
        try {
            if(getCustomer(email) != null){
                throw new IllegalArgumentException("The email " + email + " is already taken.");
            }
            Customer newCustomer = new Customer(firstName,lastName,email);
            reference.customers.add(newCustomer);
        } catch (IllegalAccessException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Customer getCustomer(String customerEmail){
        for (Customer customer : getAllCustomers()){
            if (customer.getEmail().equals(customerEmail)){
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return reference.customers;
    }



}

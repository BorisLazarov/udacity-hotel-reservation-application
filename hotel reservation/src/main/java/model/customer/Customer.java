package model.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    String firstName;
    String lastName;
    final String email;

    public Customer(String firstName, String lastName, String email) throws IllegalAccessException {
        this.firstName = firstName;
        this.lastName = lastName;
        //I've changed this regex from the one in the course,
        // so it requires a dot after the @. I was also considering making it necessary to have .com,
        // but settled for just the dot
        String emailRegex = "^(.+)@(.+)\\.(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalAccessException("Invalid email provided. Accepted format is name@domain.com");
        } else {
            this.email = email;
        }
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + " - Email: " + email;
    }


    /*
    I've only included the email in the equals and hash function because from my understanding the values included
    in those functions should not change during the object's lifetime. Here I am assuming we are ok with name changes,
    but not email changes. An alternative would be to make first and last name immutable as well and include them in the
    functions. The reason for this approach over the alternative is that it is only the requirement for the email to be
    unique, so assumption is that changing first/last name on an account should not be a problem.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer otherCustomer = (Customer) o;
        return email.equals(otherCustomer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

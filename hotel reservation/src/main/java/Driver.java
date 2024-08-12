import model.customer.Customer;

public class Driver {

    public static void main(String[] args){
        try {
            Customer customer = new Customer("first","second","emai");
            System.out.println(customer);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

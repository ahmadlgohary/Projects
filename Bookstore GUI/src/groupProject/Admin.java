/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupProject;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ahmad El-Gohary
 */
public class Admin {
    private static final Saving files = new Saving();
    protected static ArrayList<Books> books = new ArrayList<>();
    private static final ArrayList<Customers> customers = new ArrayList<>();

    public void restockArrays() throws IOException {
        ArrayList<Books> tempBooks = files.readBookFile();
        ArrayList<Customers> tempCustomers = files.readCustomerFile();
        books.addAll(tempBooks);
        customers.addAll(tempCustomers);
    }

    public String getUsername(){
        return "admin";
    }
    public String getPassword(){
        return "admin";
    }

    public void addCustomer(Customers c){
        customers.add(c);
    }

    public void deleteCustomer(Customers c){
        customers.remove(c);
    }
    @SuppressWarnings({"un-checked", "unchecked"})
    public ArrayList<Customers> getCustomers(){
        return (ArrayList<Customers>) customers.clone(); //casting?
    }
}

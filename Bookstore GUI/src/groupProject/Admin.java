/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ahmad
 */
public class Admin {
    private static final Saving files = new Saving();
    protected static ArrayList<Books> books = new ArrayList<>(); //do same here as customer
    private static final ArrayList<Customers> customers = new ArrayList<>();

public void retrieveLists() throws IOException {
        ArrayList<Books> temporaryBooks = files.readFromBooksTextFile();
        ArrayList<Customers> temporaryCustomers = files.readFromCustomerTextFile();
        books.addAll(temporaryBooks);
        customers.addAll(temporaryCustomers);
    }

    public String getUsername(){
        return "admin";
    }
    public String getPassword(){
        return "admin";
    }

    public void addCustomerToTable(Customers created){
        customers.add(created);
    }

    public void deleteCustomerFromTable(Customers selected){
        customers.remove(selected);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Customers> getCustomers(){
        return (ArrayList<Customers>) customers.clone();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ahmad
 */
public class Saving {
    
    //all elements(Books and Customers) will be written into a txt file
    public void bookFileWrite(ArrayList<Books> books) throws IOException{
        FileWriter booksTextFile = new FileWriter("books.txt", true);
        for(Books boo: books){
            String aBook = boo.getBookName() + ", " + boo.getBookPrice() + "\n";
            booksTextFile.write(aBook);
        }
        booksTextFile.close();
    }

    public void customerFileWrite(ArrayList<Customers> customers) throws IOException{
        FileWriter customerTextFile = new FileWriter("customers.txt", true);
        for(Customers cust: customers){
            String aCustomer = cust.getUsername() + ", " + cust.getPassword() + ", " + cust.getPoints() + "\n";
            customerTextFile.write(aCustomer);
        }
        customerTextFile.close();
    }

    //allows overwriting
    public void bookFileReset() throws IOException {
        FileWriter booksTextFile = new FileWriter("books.txt", false);
        booksTextFile.close();
    }

    public void customerFileReset() throws IOException {
        FileWriter customerTextFile = new FileWriter("customers.txt", false);
        customerTextFile.close();
    }

    //reads from text file and transfer it into temporary array to be used
    public ArrayList<Books> readFromBooksTextFile() throws IOException{
        Scanner scanner = new Scanner(new FileReader("books.txt"));
        ArrayList<Books> temporaryBook = new ArrayList<>();

        while(scanner.hasNext()) {
            String[] bookInfo = scanner.nextLine().split(",");
            String title = bookInfo[0];
            double price = Double.parseDouble(bookInfo[1]);
            temporaryBook.add(new Books(title, price));
        }
        return temporaryBook;
    }

    public ArrayList<Customers> readFromCustomerTextFile() throws IOException{
        Scanner scanner = new Scanner(new FileReader("customers.txt"));
        ArrayList<Customers> tempCustomer = new ArrayList<>();

        while(scanner.hasNext()) {
            String[] customerInfo = scanner.nextLine().split(", ");
            String username = customerInfo[0]; //username is the first element in the array
            String password = customerInfo[1]; //password is the second element in the array
            int points = Integer.parseInt(customerInfo[2]); //points is the third element in the array
            tempCustomer.add(new Customers(username, password));
            tempCustomer.get(tempCustomer.size()-1).setPoints(points);
        }
        return tempCustomer;
    }

}

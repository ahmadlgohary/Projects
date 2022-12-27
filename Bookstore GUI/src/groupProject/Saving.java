/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupProject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ahmad El-Gohary
 */
public class Saving {
    
    public void saveBooks2Files(ArrayList<Books> book) throws IOException{
        FileWriter saveBooks = new FileWriter("bookList.txt"); 
            for(Books b: book){
                saveBooks.write(b.getTitle() + ", " + b.getBookPrice() + "\n");
            }
        saveBooks.close();
    }
    
    public void saveCustomers2Files(ArrayList<Customers> Customer) throws IOException{
        FileWriter saveCustomers = new FileWriter("CustomersList.txt"); 
            for(Customers c: Customer){
                saveCustomers.write(c.getUsername() + ", " + c.getPassword()  + ", " + c.getPoints() + "\n");
            }
        saveCustomers.close();
    }
    
    
        public void resetBookFile() throws IOException {
        FileWriter bookFile = new FileWriter("bookList.txt", false);
        bookFile.close();
    }
        
    public void resetCustFile() throws IOException {
        FileWriter customerFile = new FileWriter("CustomersList.txt", false);
        customerFile.close();
    }

    public ArrayList<Books> readBookFile() throws IOException{
        Scanner scan = new Scanner(new FileReader("bookList.txt"));
        ArrayList<Books> tempBookList = new ArrayList<>();

        while(scan.hasNext()) {
            String[] bookInfo = scan.nextLine().split(",");
            String title = bookInfo[0];
            double price = Double.parseDouble(bookInfo[1]);
            tempBookList.add(new Books(title, price));
        }
        return tempBookList;
    }

    public ArrayList<Customers> readCustomerFile() throws IOException{
        Scanner scan = new Scanner(new FileReader("CustomersList.txt"));
        ArrayList<Customers> tempCustList = new ArrayList<>();

        while(scan.hasNext()) {
            String[] customerInfo = scan.nextLine().split(", ");
            String username = customerInfo[0];
            String password = customerInfo[1];
            int points = Integer.parseInt(customerInfo[2]);
            tempCustList.add(new Customers(username, password, points));
            //tempCustomerHolder.get(tempCustomerHolder.size()-1).setPoints(points);
        }
        return tempCustList;
    }    
}

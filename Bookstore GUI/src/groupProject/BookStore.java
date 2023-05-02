package groupProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 *
 * @author Ahmad El-Gohary
 */
public class BookStore extends Application {
    
    Button loginButton = new Button("Login");
    Button booksButton = new Button("Books");
    Button customersButton = new Button("Customers");
    Button logoutButton = new Button("Logout");
    Button backButton = new Button("back");
    Button buyButton = new Button("Buy");
    Button pointsBuyButton = new Button("Redeem points & Buy");
    TextField userTextField = new TextField();
    PasswordField passTextField = new PasswordField();
    HBox hb = new HBox();    
    
    private final Admin owner = new Admin();
    private Customers user;
    private static final Saving files = new Saving();
    TableView<Books> bookList = new TableView<>();
    final TableView.TableViewFocusModel<Books> defaultFocusModel = bookList.getFocusModel();
    ObservableList<Books> books = FXCollections.observableArrayList();

    public ObservableList<Books> addBookss(){
        books.addAll(Admin.books);
        return books;
    }
    TableView<Customers> customersList = new TableView<>();
    ObservableList<Customers> customers = FXCollections.observableArrayList();

    public ObservableList<Customers> addCustomers(){
        customers.addAll(owner.getCustomers());
        return customers;
    }

    
    
    @Override
    public void start(Stage primaryStage) {
      
        primaryStage.setTitle("Bookstore App");
       // primaryStage.getIcons().add(new Image("file:src/bookPic.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loginScreen(false), 605, 550));
        primaryStage.show();
        //System.out.println("Opened bookstore application");

        try{
            owner.restockArrays();
            System.out.println("Arrays restocked from files");
        }
        catch (IOException e){
            System.out.println("File Importing Error");
        }

        loginButton.setOnAction(e -> {
            boolean logged_in = false;

            if(userTextField.getText().equals(owner.getUsername()) && passTextField.getText().equals(owner.getUsername())) {
                primaryStage.setScene(new Scene(ownerStartScreen(), 605, 550));
                logged_in = true;
            }
            for(Customers c: owner.getCustomers()) {
                if (userTextField.getText().equals(c.getUsername()) && passTextField.getText().equals(c.getPassword())) {
                    user = c;
                    primaryStage.setScene(new Scene(customerHomeScreen(0), 605, 550));
                    logged_in = true;
                }
            }
            if(!logged_in) {
                primaryStage.setScene(new Scene(loginScreen(true), 605, 550));
            }
        });

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(loginScreen(false), 605, 550));
            for(Books b: Admin.books){
                b.setSelect(new CheckBox());
            }
            userTextField.clear();
            passTextField.clear();
        });

        booksButton.setOnAction(e -> primaryStage.setScene(new Scene(bookListScreen(), 605, 550)));

        customersButton.setOnAction(e -> primaryStage.setScene(new Scene(customerTableScreen(), 605, 550)));
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerStartScreen(), 605, 550)));

        pointsBuyButton.setOnAction(e -> {
            boolean bookSelected = false;
            for(Books b: Admin.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(!bookSelected){
                primaryStage.setScene(new Scene(customerHomeScreen(1), 605, 550));
            } else if(user.getPoints() == 0){
                primaryStage.setScene(new Scene(customerHomeScreen(2), 605, 550));
            } else if(user.getPoints() != 0){
                primaryStage.setScene(new Scene(checkoutScreen(true), 605, 550));
            }
        });//need this to not open checkout screen unless customer has picked a book and has points on account

        buyButton.setOnAction(e -> {
            boolean bookSelected = false;
            for(Books b: Admin.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(bookSelected){
                primaryStage.setScene(new Scene(checkoutScreen(false), 605, 550));
            } else primaryStage.setScene(new Scene(customerHomeScreen(1), 605, 550));
        }); //need this to not open checkout screen unless a book is selected

        primaryStage.setOnCloseRequest(e -> {
            //System.out.println("Exited the book store");
            try {
                files.resetBookFile();
                files.resetCustFile();
                //System.out.println("Files reset");
                files.saveBooks2Files(Admin.books);
                files.saveCustomers2Files(owner.getCustomers());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            //when program closes, reset file and add all current stock and so when
            // program opens arrays are restocked with current available stock
        });

        //css
        /*{
            buyButton.setStyle("-fx-background-color: #fa940c;");
            pointsBuyButton.setStyle("-fx-background-color: #fa940c;");
            customersButton.setStyle("-fx-background-color: #a07855;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            booksButton.setStyle("-fx-background-color: #a07855;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            logoutButton.setStyle("-fx-background-color: #fa940c;");
            backButton.setStyle("-fx-background-color: #E1C699;" + "-fx-font-size:14;");
            loginButton.setStyle("-fx-background-color: #fa940c;");

            customersList.setStyle("-fx-control-inner-background: #a07855;" +
                    "-fx-selection-bar: #fa940c; -fx-selection-bar-non-focused: #fa940c;" + "-fx-border-color: #a07855;" +
                    "-fx-table-cell-border-color: #a07855;" + "-fx-background-color: #a07855;");

            bookList.setStyle("-fx-control-inner-background: #a07855;" + "-fx-border-color: #a07855;" +
                    "-fx-selection-bar: #fa940c; -fx-selection-bar-non-focused: #fa940c;" +
                    "-fx-table-cell-border-color: #a07855;" + "-fx-background-color: #a07855;" + "-fx-column-header-background: #a07855;");
        }*/
    }
    

    public static void main(String[] args) {
        launch(args);
    }   
}

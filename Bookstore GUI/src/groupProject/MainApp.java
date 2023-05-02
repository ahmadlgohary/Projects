/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

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
 * @author ahmad
 */
public class MainApp extends Application {
    
    private final Admin admin = new Admin(); //create admin object
    private static final Saving saving = new Saving();//create saving object
    private Customers activeCustomer; //create customer currently logged in

    //create buttons, textfields, obserbableslists...
    Button loginButton = new Button("Login");
    Button selectBooksButton = new Button("Books");
    Button selectCustomersButton = new Button("Customers");
    Button logoutButton = new Button("Logout");
    Button backButton = new Button("Back");
    Button buyButton = new Button("Buy");
    Button redeemButton = new Button("Redeem & Buy");
    TextField usernameTextField = new TextField();
    PasswordField passwordTextField = new PasswordField();
    HBox hbox = new HBox();

    //create tableView for books
    TableView<Books> booksList = new TableView<>();
    final TableView.TableViewFocusModel<Books> defaultFocusModel = booksList.getFocusModel();
    //create observableList for books
    ObservableList<Books> books = FXCollections.observableArrayList();

    public ObservableList<Books> addBooksToList(){
        books.addAll(Admin.books);
        return books;
    }
    //create tableView for customers
    TableView<Customers> customersList = new TableView<>();
    //create observableList for customers
    ObservableList<Customers> customers = FXCollections.observableArrayList();

    public ObservableList<Customers> addCustomersToList(){
        customers.addAll(admin.getCustomers());
        return customers;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Bookstore App");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loginScreen(false), 605, 550));
        primaryStage.show();
        //System.out.println("Opened bookstore application");

        try{
            //retrieve all information from txt files
            admin.retrieveLists();
        }
        catch (IOException e){
        }

        loginButton.setOnAction(e -> {
            boolean loggedIn = false;

            if(usernameTextField.getText().equals(admin.getUsername()) && passwordTextField.getText().equals(admin.getUsername())) {
                primaryStage.setScene(new Scene(ownerStartScreen(), 605, 550));
                loggedIn = true;
            }
            for(Customers cust: admin.getCustomers()) {
                if (usernameTextField.getText().equals(cust.getUsername()) && passwordTextField.getText().equals(cust.getPassword())) {
                    activeCustomer = cust;
                    primaryStage.setScene(new Scene(customerStartScreen(0), 605, 550));
                    loggedIn = true;
                }
            }
            if(!loggedIn) {
                primaryStage.setScene(new Scene(loginScreen(true), 605, 550));
            }
        });

        //assign task of Books Button in owner start screen
        selectBooksButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerBooksScreen(), 605, 550)));

        //assign task of Customers Button in owner start screen
        selectCustomersButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerCustomersScreen(), 605, 550)));
        
        //asign task of log out button
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(loginScreen(false), 605, 550));
            for(Books boo: Admin.books){
                boo.setSelect(new CheckBox());
            }
            usernameTextField.clear();
            passwordTextField.clear();
        });
        
        //assign task of back Button
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerStartScreen(), 605, 550)));
        
        //assign task og buy button in Customer Start screen 
        buyButton.setOnAction(e -> {
            boolean bookSelected = false;
            for(Books boo: Admin.books) {
                if (boo.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(bookSelected){
                primaryStage.setScene(new Scene(customerCostScreen(false), 605, 550));
            } else primaryStage.setScene(new Scene(customerStartScreen(1), 605, 550));
        });

        //assign task of redeem button in Customer Start screen        
        redeemButton.setOnAction(e -> { //only will work if customers has an account and has selected a book to buy
            boolean bookSelected = false;
            for(Books boo: Admin.books) {
                if (boo.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(!bookSelected){
                primaryStage.setScene(new Scene(customerStartScreen(1), 605, 550));
            } else if(activeCustomer.getPoints() == 0){
                primaryStage.setScene(new Scene(customerStartScreen(2), 605, 550));
            } else if(activeCustomer.getPoints() != 0){
                primaryStage.setScene(new Scene(customerCostScreen(true), 605, 550));
            }
        });

        //when "x" is clicked, all the following methods will be called to overwrite everything on the screen and to write to the text files
        primaryStage.setOnCloseRequest(e -> {
            try {
                saving.bookFileReset();
                saving.customerFileReset();
                saving.bookFileWrite(Admin.books);
                saving.customerFileWrite(admin.getCustomers());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        //all the buttons and tables declared
        {
            loginButton.setStyle("-fx-background-color: #DFE166;");
            selectCustomersButton.setStyle("-fx-background-color: #DFE166;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            selectBooksButton.setStyle("-fx-background-color: #DFE166;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            logoutButton.setStyle("-fx-background-color: #DFE166;");
            backButton.setStyle("-fx-background-color: #DFE166;" );
            buyButton.setStyle("-fx-background-color: #DFE166;;");
            redeemButton.setStyle("-fx-background-color: #DFE166;;");
            customersList.setStyle("-fx-control-inner-background: #D3D3D3;" + "-fx-border-color: #D3D3D3;" +
                    "-fx-table-cell-border-color: #D3D3D3;" + "-fx-background-color: #D3D3D3;" + "-fx-column-header-background: #D3D3D3;");
            booksList.setStyle("-fx-control-inner-background: #D3D3D3;" + "-fx-border-color: #D3D3D3;" +
                    "-fx-table-cell-border-color: #D3D3D3;" + "-fx-background-color: #D3D3D3;" + "-fx-column-header-background: #D3D3D3;");
        }
    }

    //first screen that the user sees when running the app
    public Group loginScreen(boolean loginError){
        Group loginscreen = new Group();

        HBox topSide = new HBox();
        Label titleOfScreen = new Label("Group 58's Library App");
        titleOfScreen.setStyle(" -fx-alignment: CENTER;");
        titleOfScreen.setFont(new Font("Arial", 38));
        topSide.getChildren().addAll(titleOfScreen);
        topSide.setSpacing(6);
        topSide.setAlignment(Pos.CENTER);

        VBox loginBox = new VBox();
        loginBox.setPadding(new Insets(60,65,45,60));
        loginBox.setStyle("-fx-background-color: #9A9EAB;");
        loginBox.setSpacing(10);
        Text usernameText = new Text("Username:");
        usernameTextField.setStyle("-fx-background-color: 	#D3D3D3;");
        passwordTextField.setStyle("-fx-background-color: 	#D3D3D3;");
        Text passwordText = new Text("Password:");
        loginButton.setMinWidth(174);
        loginBox.getChildren().addAll(usernameText, usernameTextField, passwordText, passwordTextField, loginButton);

        if(loginError){
            Text wrongLogin = new Text("Incorrect username or password.");
            wrongLogin.setFill(Color.RED);
            loginBox.getChildren().add(wrongLogin);
        }

        VBox backg = new VBox();
        backg.getChildren().addAll(topSide, loginBox);
        backg.setStyle("-fx-background-color: #EC96A4;");
        backg.setPadding(new Insets(80, 280, 200, 150));
        backg.setSpacing(80);

        loginscreen.getChildren().addAll(backg);
        return loginscreen;
    }

    public Group customerStartScreen(int type){
        Group bookstore = new Group();
        booksList.getItems().clear();
        booksList.getColumns().clear();
        booksList.setFocusModel(null);

        Font font = new Font(16);
        Text welcomeText = new Text("Welcome, " + activeCustomer.getUsername() + "!");
        welcomeText.setFont(font);
        Text statusText = new Text(" Status: ");
        statusText.setFont(font);
        Text membershipStatus = new Text(activeCustomer.getStatus());
        membershipStatus.setFont(font);

        Text pointsAccumulated = new Text(" Points: " + activeCustomer.getPoints());
        pointsAccumulated.setFont(font);

        //Book title column
        TableColumn<Books, String> colBookName = new TableColumn<>("Book Name");
        colBookName.setMinWidth(170);
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Book price column
        TableColumn<Books, Double> colBookPrice = new TableColumn<>("Book Price");
        colBookPrice.setMinWidth(170);
        colBookPrice.setStyle("-fx-alignment: CENTER;");
        colBookPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        //Checkbox column
        TableColumn<Books, String> colSelect = new TableColumn<>("Select");
        colSelect.setMinWidth(80);
        colSelect.setStyle("-fx-alignment: CENTER;");
        colSelect.setCellValueFactory(new PropertyValueFactory<>("select"));

        booksList.setItems(addBooksToList());
        booksList.getColumns().addAll(colBookName, colBookPrice, colSelect);

        HBox membershiprecap = new HBox();
        membershiprecap.getChildren().addAll(statusText, membershipStatus, pointsAccumulated);
        BorderPane header = new BorderPane();
        header.setLeft(welcomeText);
        header.setRight(membershiprecap);

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setSpacing(45);
        bottom.getChildren().addAll(buyButton, redeemButton, logoutButton);

        VBox vbox = new VBox();
        String errorMessage = "";
        if(type == 1){
            errorMessage = "Warning! Select a book before proceeding.";
        }
        else if(type == 2){
            errorMessage =  "You do not have enough points to redeem.";
        }
        Text warning = new Text(errorMessage);
        warning.setFill(Color.RED);
        vbox.setStyle("-fx-background-color: #EC96A4;");
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40, 200, 30, 100));
        vbox.getChildren().addAll(header, booksList, bottom, warning);

        bookstore.getChildren().addAll(vbox);

        return bookstore;
    }//complete

    public Group customerCostScreen(boolean usedPoints){
                Group customercostscreen = new Group();
        double total, subtotal = 0, discount;
        int pointsEarned, i = 0, numOfBooks = 0;
        String[][] booksPurchased= new String[25][2];

        for(Books boo: Admin.books){
            if(boo.getSelect().isSelected()){
                subtotal += boo.getBookPrice();
                booksPurchased[i][0] = boo.getBookName();
                booksPurchased[i][1] = String.valueOf(boo.getBookPrice());
                i++;
            }
        }

        if(usedPoints){
            if((double)activeCustomer.getPoints()/100 >= subtotal){
                discount = subtotal;
                activeCustomer.setPoints(-(int)subtotal*100);
            }
            else{
                discount = ((double)activeCustomer.getPoints()/100);
                activeCustomer.setPoints(-activeCustomer.getPoints());
            }
        }else discount = 0;

        total = subtotal - discount;
        pointsEarned = (int)total*10;
        activeCustomer.setPoints(pointsEarned);

        HBox headerMessage = new HBox();
        headerMessage.setAlignment(Pos.CENTER);
        headerMessage.setSpacing(15);
        headerMessage.setPadding(new Insets(0,0,25,0));
        Label titleOfPage = new Label("Group 58's Library App");
        titleOfPage.setFont(new Font("Arial", 35));
        headerMessage.getChildren().addAll(titleOfPage);

        VBox purchasesBreakdown = new VBox();
        purchasesBreakdown.setSpacing(7);
        Text receiptTxt = new Text("Receipt");
        receiptTxt.setFont(Font.font(null, FontWeight.BOLD, 12));
        Line divider1 = new Line(0, 150, 400, 150);
        divider1.setStrokeWidth(3);
        purchasesBreakdown.getChildren().addAll(receiptTxt, divider1);

        VBox receiptItems = new VBox();
        receiptItems.setStyle("-fx-background-color: #EC96A4;");
        receiptItems.setSpacing(7);
        for (i = 0; i<25; i++) {
            if(booksPurchased[i][0] != null){
                Text bookTitle = new Text(booksPurchased[i][0]);
                Text bookPrice = new Text(booksPurchased[i][1]);
                BorderPane item = new BorderPane();
                item.setLeft(bookTitle);
                item.setRight(bookPrice);
                Line line = new Line(0, 150, 400, 150);
                receiptItems.getChildren().addAll(item, line);
                numOfBooks++;
            }
        }

        ScrollPane scrollReceipt = new ScrollPane(receiptItems); // lets us scroll through books in receipt if more than 4 books are bought
        scrollReceipt.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollReceipt.setStyle("-fx-background-color:transparent;");
        scrollReceipt.setFitToWidth(true);
        if(numOfBooks<=4){
            scrollReceipt.setFitToHeight(true);
        } else scrollReceipt.setPrefHeight(130);


        Text subtotalValue = new Text("Total before redeeming: $" + (Math.round(subtotal*100.0))/100.0);
        Text pointsRedeemed = new Text("Points Redeemed: $" + (Math.round(discount*100.0))/100.0);
        Text totalValue= new Text("Total after redeeming: $" + (Math.round(total*100.0))/100.0);
        totalValue.setFont(new Font("Arial", 15));
        Line divider2 = new Line(0, 150, 400, 150);
        divider2.setStrokeWidth(3);
        purchasesBreakdown.getChildren().addAll(scrollReceipt, subtotalValue, pointsRedeemed, totalValue, divider2);

        VBox bottomMessage = new VBox();
        bottomMessage.setSpacing(40);
        bottomMessage.setAlignment(Pos.CENTER);
        Text info = new Text("Points Earned: " + pointsEarned + "\nCurrent Status: " + activeCustomer.getStatus() );
        bottomMessage.getChildren().addAll(info, logoutButton);

        VBox screen = new VBox();
        screen.setStyle("-fx-background-color: #EC96A4;");
        screen.setPadding(new Insets(60,105,500,100));
        screen.setAlignment(Pos.CENTER);
        screen.setSpacing(10);
        screen.getChildren().addAll(headerMessage, purchasesBreakdown, bottomMessage);

        customercostscreen.getChildren().addAll(screen);
        admin.books.removeIf(b -> b.getSelect().isSelected());
        return customercostscreen;
    }//complete
    
    public VBox ownerStartScreen() {
        VBox ownerstartscreen = new VBox();
        ownerstartscreen.setStyle("-fx-background-color: #EC96A4;");
        ownerstartscreen.setAlignment(Pos.CENTER);
        ownerstartscreen.setSpacing(100);
        ownerstartscreen.setPadding(new Insets(80,0,30,0));

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(40);
        //Line vLine = new Line(150, 0, 150, 200);
        buttons.getChildren().addAll(selectBooksButton,  selectCustomersButton);
        selectBooksButton.setPrefSize(200,150);
        selectCustomersButton.setPrefSize(200,150);

        ownerstartscreen.getChildren().addAll(buttons, logoutButton);
        return ownerstartscreen;
    }//complete

    public Group ownerBooksScreen() {
        Group ownerbooksscreen = new Group();
        hbox.getChildren().clear();
        booksList.getItems().clear();
        booksList.getColumns().clear();
        booksList.setFocusModel(defaultFocusModel);

        Label label = new Label("Books List");
        label.setFont(new Font("Arial", 30));

        //Book name column
        TableColumn<Books, String> titleColumn = new TableColumn<>("Book Name");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Book price column
        TableColumn<Books, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(150);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        booksList.setItems(addBooksToList());
        booksList.getColumns().addAll(titleColumn, priceColumn);

        final TextField addBookName = new TextField();
        addBookName.setPromptText("Name");
        addBookName.setMaxWidth(titleColumn.getPrefWidth());
        final TextField addBookPrice = new TextField();
        addBookPrice.setMaxWidth(priceColumn.getPrefWidth());
        addBookPrice.setPromptText("Price");
        addBookName.setStyle("-fx-background-color: #F1F1F2;");
        addBookPrice.setStyle("-fx-background-color: #F1F1F2;");

        VBox core = new VBox();
        final Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #DFE166;");
        Label bookAddedError = new Label("Invalid Input");
        bookAddedError.setTextFill(Color.color(1,0,0));

        addButton.setOnAction(e -> {
            try {
                double price = Math.round((Double.parseDouble(addBookPrice.getText()))*100);
                Admin.books.add(new Books(addBookName.getText(), price/100));
                //makes new book and adds it to arraylist
                booksList.getItems().clear(); //refresh page so new books can be accessed
                booksList.setItems(addBooksToList());
                addBookName.clear(); //clears text fields
                addBookPrice.clear();
                core.getChildren().remove(bookAddedError); //removes a previous Invalid Input error if there was one
            }
            catch (Exception exception){
                if(!core.getChildren().contains(bookAddedError)){
                    core.getChildren().add(bookAddedError);
                }
            }
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #DFE166;");
        deleteButton.setOnAction(e -> {
            Books selectedRow = booksList.getSelectionModel().getSelectedItem(); //the selected row will be highlighted
            booksList.getItems().remove(selectedRow); //delete button pressed -> item deleted
            Admin.books.remove(selectedRow); //item deleted from array list
        });


        hbox.getChildren().addAll(addBookName, addBookPrice, addButton, deleteButton, backButton);
        hbox.setSpacing(3);
        hbox.setAlignment(Pos.CENTER);

        //HBox back = new HBox();
        //back.setPadding(new Insets(5));
        //back.getChildren().addAll(backButton);

        core.setAlignment(Pos.CENTER);
        core.setSpacing(5);
        core.setPadding(new Insets(0, 0, 0, 120));
        core.getChildren().addAll(label, booksList, hbox);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #EC96A4;");
        vbox.setPadding(new Insets(0, 200, 60, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(core);


        ownerbooksscreen.getChildren().addAll(vbox);

        return ownerbooksscreen;
    }//complete

    public Group ownerCustomersScreen() {
        Group ownercustomersscreen = new Group();
        hbox.getChildren().clear();
        customersList.getItems().clear();
        customersList.getColumns().clear();

        Label label = new Label("Customers");
        label.setFont(new Font("Arial", 30));

        //Customer username column
        TableColumn<Customers, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(140);
        usernameCol.setStyle("-fx-alignment: CENTER;");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Customer password column
        TableColumn<Customers, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setMinWidth(140);
        passwordCol.setStyle("-fx-alignment: CENTER;");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        //Customer points column
        TableColumn<Customers, Integer> pointsCol = new TableColumn<>("Points");
        pointsCol.setMinWidth(100);
        pointsCol.setStyle("-fx-alignment: CENTER;");
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        customersList.setItems(addCustomersToList());
        customersList.getColumns().addAll(usernameCol, passwordCol, pointsCol);

        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(usernameCol.getPrefWidth());
        final TextField addPassword = new TextField();
        addPassword.setMaxWidth(passwordCol.getPrefWidth());
        addPassword.setPromptText("Password");
        addPassword.setStyle("-fx-background-color: #F1F1F2;");
        addUsername.setStyle("-fx-background-color: #F1F1F2;");

        VBox box = new VBox();
        Text customerAddErr = new Text("Warning! This customer already exists"); //checks if customer exists
        customerAddErr.setFill(Color.color(1,0,0));
        final Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #DFE166;");
        addButton.setOnAction(e -> {
            boolean duplicate = false;

            for(Customers cust: admin.getCustomers()){
                if((cust.getUsername().equals(addUsername.getText()) && cust.getPassword().equals(addPassword.getText())) ||
                        (addUsername.getText().equals(admin.getUsername()) && addPassword.getText().equals(admin.getPassword()))){
                    duplicate = true;
                    if(!box.getChildren().contains(customerAddErr)){
                        box.getChildren().add(customerAddErr);
                    }
                }
            }

            if(!(addUsername.getText().equals("") || addPassword.getText().equals("")) && !duplicate) {
                admin.addCustomerToTable(new Customers(addUsername.getText(), addPassword.getText())); //add cusgtomer to arraylist
                customersList.getItems().clear(); //refresh
                customersList.setItems(addCustomersToList());
                box.getChildren().remove(customerAddErr); //all error messages will be removed
                addPassword.clear(); //all text fields already filled with info will be removed
                addUsername.clear();
            }
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #DFE166;");
        deleteButton.setOnAction(e -> {
            Customers selectedRow = customersList.getSelectionModel().getSelectedItem();
            customersList.getItems().remove(selectedRow); //remove item from tableview
            customers.remove(selectedRow); //remoce item from obesrvable list
            admin.deleteCustomerFromTable(selectedRow); //removes current arraylist
        });

        hbox.getChildren().addAll(addUsername, addPassword, backButton, addButton, deleteButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(3);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.setPadding(new Insets(0,0,0,110));
        box.getChildren().addAll(label, customersList, hbox);

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color:#EC96A4;");
        vbox.setPadding(new Insets(0, 150, 60, 0));
        vbox.getChildren().addAll(box);
        vbox.setAlignment(Pos.CENTER);

        ownercustomersscreen.getChildren().addAll(vbox);
        return ownercustomersscreen;
    }//complete

    public static void main(String[] args) {
        launch(args);
    }
}
    


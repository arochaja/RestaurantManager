package com.project_files;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Customer_Controller {
    @FXML
    private TextField tip_field;

    @FXML
    private TextField cc_field;

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private TextField field3;
    @FXML
    private TextField prompt_box1;
    @FXML
    private TextArea result;
    private ArrayList<String> store_name_phone = new ArrayList<>();
    private ArrayList<String> cart = new ArrayList<>();

    @FXML
    protected void LoginButtonClick() {
        String sql_text;
        sql_text = "INSERT INTO Customer(c_name, phone_num, email) VALUES (" +
                "'" + field1.getText() + "'" + ", " + field3.getText() + ", " +
                "'" + field2.getText() + "');";
        SQL_loader(sql_text);
        prompt_box1.setText("Login Success! What would you like to order?");
        if (!store_name_phone.isEmpty()) {
            ArrayList<String> store_name_phone = new ArrayList<>();
        }
        store_name_phone.add(field1.getText());
        store_name_phone.add(field3.getText());
        result.appendText("Current Cart:");
    }
    static Connection connect;
    public static String SQL_loader(String send) {
        String res_string = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    send);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return res_string;
    }
    void throwCartError() {
        prompt_box1.setText("Cart already contains that item, please try again");
    }
    @FXML
    protected void addSandwich() {
        if (cart.contains("Sandwich")) {
            throwCartError();
        }
        else {
            cart.add("Sandwich");
            result.appendText("\n " + "Sandwich");
        }
    }
    @FXML
    protected void addMuffin() {
        if (cart.contains("Muffin")) {
            throwCartError();
        }
        else {
            cart.add("Muffin");
            result.appendText("\n " + "Muffin");
        }
    }
    @FXML
    protected void addDonut() {
        if (cart.contains("Donut")) {
            throwCartError();
        }
        else {
            cart.add("Donut");
            result.appendText("\n " + "Donut");
        }
    }
    @FXML
    protected void addBagel() {
        if (cart.contains("Bagel")) {
            throwCartError();
        }
        else {
            cart.add("Bagel");
            result.appendText("\n " + "Bagel");
        }
    }
    @FXML
    protected void addToast() {
        if (cart.contains("Toast")) {
            throwCartError();
        }
        else {
            cart.add("Toast");
            result.appendText("\n " + "Toast");
        }
    }
    @FXML
    protected void addIcedCoffee() {
        if (cart.contains("Iced Coffee")) {
            throwCartError();
        }
        else {
            cart.add("Iced Coffee");
            result.appendText("\n " + "Iced Coffee");
        }
    }
    @FXML
    protected void addEspresso() {
        if (cart.contains("Espresso")) {
            throwCartError();
        }
        else {
            cart.add("Espresso");
            result.appendText("\n " + "Espresso");
        }
    }
    @FXML
    protected void addAmericano() {
        if (cart.contains("Americano")) {
            throwCartError();
        }
        else {
            cart.add("Americano");
            result.appendText("\n " + "Americano");
        }
    }
    @FXML
    protected void addMacchiato() {
        if (cart.contains("Macchiato")) {
            throwCartError();
        }
        else {
            cart.add("Macchiato");
            result.appendText("\n " + "Macchiato");
        }
    }
    @FXML
    protected void addLatte() {
        if (cart.contains("Latte")) {
            throwCartError();
        }
        else {
            cart.add("Latte");
            result.appendText("\n " + "Latte");
        }
    }
    @FXML
    protected void getSandwich() {
        SQL_getter("Sandwich");
    }
    @FXML
    protected void getMuffin() {
        SQL_getter("Muffin");
    }
    @FXML
    protected void getDonut() {
        SQL_getter("Donut");
    }
    @FXML
    protected void getBagel() {
        SQL_getter("Bagel");
    }
    @FXML
    protected void getToast() {
        SQL_getter("Toast");
    }
    @FXML
    protected void getIcedCoffee() {
        SQL_getter("Iced Coffee");
    }
    @FXML
    protected void getEspresso() {
        SQL_getter("Espresso");
    }
    @FXML
    protected void getAmericano() {
        SQL_getter("Americano");
    }
    @FXML
    protected void getMacchiato() {
        SQL_getter("Sandwich");
    }
    @FXML
    protected void getLatte() {
        SQL_getter("Latte");
    }
    public void SQL_getter(String get) {
        Connection connect;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
            Statement ingredients = connect.createStatement();
            ResultSet rs = ingredients.executeQuery(
                    "SELECT ingredients FROM Food WHERE food_name = '" + get + "' ;");
            String ingredient = "";
            while (rs.next()) {
                ingredient = rs.getString(1);
            }
            List<String> ingredient_list = new ArrayList<>(Arrays.asList(ingredient.split(", ")));
            result.appendText("\nThe ingredients in a " + get + " are : \n");
            for (String single_ingredient : ingredient_list) {
                result.appendText(single_ingredient + "\n");
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void checkout() {
        double cart_price = calculateCartPrice() + Double.parseDouble(tip_field.getText());
        result.appendText("\n\n\nThank you for your purchase! \n The final total is: \n" + calculateCartPrice() + " + \n" +
                tip_field.getText() + "\n--------------\n" + cart_price);

        // Update SQL tables
        int cust_id = getCustID();
        String cc_num = cc_field.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO Payment(cc_num, id, tip, total) VALUES('" +
                            cc_num + "', " + cust_id + ", " + Double.parseDouble(tip_field.getText()) + ", " + cart_price
                    + ");");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateCartPrice() {
        double final_price = 0;
        for (String s : cart) {
            Connection connect;
            // Get information of price from Food table
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(
                        "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
                Statement price = connect.createStatement();
                ResultSet rs = price.executeQuery(
                        "SELECT price FROM Food WHERE food_name = '" + s + "' ;");
                while (rs.next()) {
                    final_price = final_price + rs.getDouble(1);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Insert Payment Info into Receipt
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(
                        "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
                Statement statement = connect.createStatement();
                statement.executeUpdate(
                        "INSERT INTO Receipt(id, food, payment_type, receipt_date) VALUES(" +
                                getCustID() + ", '" + s + "', " + "'Credit Card', '" + makeDate()
                                + "');");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Remove food from inventory
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(
                        "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
                Statement statement = connect.createStatement();
                statement.executeUpdate(
                        "UPDATE Inventory SET `" + s + "` = `" + s + "` - 1 ;");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return final_price;
    }
    public int getCustID() {
        int cust_id = -1;
            Connection connect;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connect = DriverManager.getConnection(
                        "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/One?user=One&password=One");
                Statement price = connect.createStatement();
                ResultSet rs = price.executeQuery(
                        "SELECT id FROM Customer WHERE c_name = '" + store_name_phone.get(0)+
                                "' " + " and " + "phone_num = " + store_name_phone.get(1) + "; ");
                while (rs.next()) {
                    cust_id = rs.getInt(1);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return cust_id;
    }
    public String makeDate() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter date1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter date2 = DateTimeFormatter.ofPattern("h:m:ss");
        return time.format(date1) + " " + time.format(date2);
    }
}
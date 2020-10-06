package org.example;

import java.sql.*;

public class JDBCFirstApp {
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";

    private static void main(String[] args){


    }

    private static void printDAtaTable(){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            System.out.println("Connection with database 'testdb'");
            System.out.println("Print all from table 'car'");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CAR");
            System.out.printf("%-20s %20s %20s", "Model", "Price", "Color");
            while(resultSet.next()){
                System.out.printf(
                        "%-20s %20s %20s",
                        resultSet.getString("model"),
                        resultSet.getString("price"),
                        resultSet.getString("color")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable(String model, Long price, String color){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            System.out.println("Connection with database 'testdb'");
            Statement statement = connection.createStatement();
            System.out.printf("%-20s %20s %20s", "Model", "Price", "Color");
            statement.execute(String.format(
                    "INSERT INTO CAR VALUES(%s, %s, %s)",
                    model, price, color)
            );
            System.out.printf("row (%s, %s, %s) was added/n", model, price, color);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

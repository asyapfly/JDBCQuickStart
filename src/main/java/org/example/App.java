package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App
{
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //printDAtaTable();
        insertIntoTable("Porc12he", 15L, "Red");
        printDAtaTable();
    }


    private static void printDAtaTable(){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            System.out.println("Connection with database 'testdb'");
            System.out.println("Print all from table 'car'");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CAR");
            System.out.printf("%-20s %20s %20s", "Model", "Price", "Color\n");
            while(resultSet.next()){
                System.out.printf(
                        "%-20s %20s %20s",
                        resultSet.getString("model"),
                        resultSet.getString("price"),
                        resultSet.getString("color")
                );
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable(String model1, Long price1, String color1){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            System.out.println("Connection with database 'testdb'");
            Statement statement = connection.createStatement();
            System.out.printf("%-20s %20s %20s", "Model", "Price", "Color\n");
            statement.execute(String.format(
                    "INSERT INTO CAR VALUES(%s, %s, %s)",
                    model1, price1, color1)
            );
            //statement.execute("INSERT INTO CAR VALUES(model1,price1,color1)");
            System.out.printf("row (%s, %s, %s) was added/n", model1, price1, color1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package org.example;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;

public class App
{
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/testdb";

    public static void main( String[] args )
    {
        checkMyName("Васильев", "Илья", "Николаевич");
    }

    private static void checkMyName(String lastname, String firstname, String middlename){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            //String.format как-то неправильно конвертит строку, так что sql выдает ошибку "Столбец "Васильев" не существует"
            // Хотя в параметрах передаю верно. Поэтмоу решил использовать просто строку в качестве запроса
            String query = "SELECT * FROM public.\"Person\" WHERE lastname = '" + lastname + "'AND firstname = '" + firstname + "'AND middlename = '" + middlename + "'";
            System.out.println("Connection with database 'testdb'");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //System.out.printf("%-20s %20s %20s %s%n", "lastname", "firstname", "middlename", "id");
            if(!resultSet.isBeforeFirst() ){
                System.out.println("Твоих данных нет в таблице. Переходим к вставке значений");
                insertIntoTable(lastname, firstname, middlename);
            }
            else {
                while (resultSet.next()) {
                    System.out.printf(
                            "%-50s %-50s %-50s %d",
                            resultSet.getString("lastname"),
                            resultSet.getString("firstname"),
                            resultSet.getString("middlename"),
                            resultSet.getInt("id")
                    );
                    System.out.println("Ты уже есть в таблице");
                }
           }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable(String lastname, String firstname, String middlename){
        try(Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)){
            String query = "INSERT INTO public.\"Person\" VALUES('" + lastname + "','" + firstname + "','"+ middlename +"')";
            System.out.println("Connection with database 'testdb'");
            Statement statement = connection.createStatement();
            System.out.printf("%-20s %20s %20s", "Last Name", "First Name", "Middle Name\n");
            statement.execute(query);
            System.out.printf("row (%s, %s, %s) was added/n", lastname, firstname, middlename);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

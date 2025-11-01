package HosptalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class doctor {
    private Connection connection;

    public doctor(Connection connection){
        this.connection = connection;
    }

    public void viewDoctor(){
        String retrieve = "Select * from doctor";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(retrieve);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+---------------+---------------------+");
            System.out.println("| Doctor id | Name           | Specialization      |");
            System.out.println("+------------+---------------+---------------------+");

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("Specialization");

                System.out.printf("|%-12s|%-15s|%-21s|\n", id,name,specialization);
                System.out.println("+------------+---------------+---------------------+");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkdoctors(int id){
        String query = "Select * from doctor where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

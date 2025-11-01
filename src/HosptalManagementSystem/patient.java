package HosptalManagementSystem;

import java.sql.*;
import java.util.Scanner;
import java.util.SplittableRandom;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        scanner.nextLine();
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the Gender: ");
        String gender = scanner.nextLine();

        String add_patient = "Insert into PATIENT (name,age,gender) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(add_patient);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int rows = preparedStatement.executeUpdate();
            if (rows>0){
                System.out.print("Patient added successfully !!\n");
            }else {
                System.out.println("Error !!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPatient(){
        String retrieve = "Select * from PATIENT";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(retrieve);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("The total Patients Are: ");
            System.out.println("+------------+---------------+-----+---------+");
            System.out.println("| Patient id | Name          | Age | Gender  |");
            System.out.println("+------------+---------------+-----+---------+");

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.printf("|%-12s|%-15s|%-5s|%-9s|\n", id,name,age,gender);

                System.out.println("+------------+---------------+-----+---------+");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DeletePatient(){
        String deleteQ = "Delete from patient where id = ?";
        System.out.println("Enter the ID of the patient");
        int id = scanner.nextInt();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQ);
            preparedStatement.setInt(1,id);

            int rowsAffect = preparedStatement.executeUpdate();
            if (rowsAffect>0){
                System.out.println("Patient data is deleted.");
            }else {
                System.out.println("Their is some error.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPatient(int id){
        String query = "Select * from patient where id=?";
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

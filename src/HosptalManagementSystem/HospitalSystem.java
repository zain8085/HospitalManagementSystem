package HosptalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalSystem {
    private static final String url = <Enter JDBC URL>;
    private static final String username = <enter username>;
    private static final String pass = <enter password>;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(url,username,pass);
            Scanner scanner = new Scanner(System.in);
            patient patient = new patient(connection,scanner);
            doctor doc = new doctor(connection);
            System.out.println();
            System.out.println("Hospital Management System");

            while (true){
                System.out.println();
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. Delete Patient");
                System.out.println("4. View Doctor");
                System.out.println("5. Book Appointment");
                System.out.println("6. Exit");

                System.out.print("Enter the choice: ");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        patient.DeletePatient();
                        break;
                    case 4:
                        doc.viewDoctor();
                        break;
                    case 5:
                        getAppointment(patient,doc,connection,scanner);
                        System.out.println();
                        break;
                    case 6:
                        try {
                            exit();
                            return;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    default:
                        System.out.println("Enter Valid input ***");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void getAppointment(patient patient, doctor doc, Connection connection, Scanner scanner){
        System.out.println("Enter the patient id: ");
        int patient_id = scanner.nextInt();

        System.out.println("Enter the doc id: ");
        int Doc_id = scanner.nextInt();

        System.out.println("Enter the Date: in YYYY/MM/DD");
        String date = scanner.nextLine();

        if (patient.checkPatient(patient_id) && doc.checkdoctors(Doc_id)){
            if(doctorsAvailable(Doc_id,date, connection)){
                String query = "Insert into appointments(patient_id,doctor_id,appointment_date) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,Doc_id);
                    preparedStatement.setString(3,date);

                    int rows = preparedStatement.executeUpdate();
                    if (rows>0){
                        System.out.println("Appointment Booked !!");
                    }else {
                        System.out.println("Try Again !!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    private static boolean doctorsAvailable(int docId, String date,Connection connection) {
        String q3 = "Select count(*) from appointments WHERE doctor_id = ? AND  appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(q3);
            preparedStatement.setInt(1,docId);
            preparedStatement.setString(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting System !!");
        int i = 1;
        while (i<=5){
            System.out.print(".");
            Thread.sleep(1000);
            i++;
        }
    }
}

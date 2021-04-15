package school.repository;

import school.main.Main;
import school.models.Student;

import java.sql.*;
import java.util.LinkedList;

public class StudentRepository {
    private Connection connection;
    private Statement statement;

    public StudentRepository() {
        try {
            // https://www.youtube.com/watch?v=xM1KNbRkF3A
            // => File => project structure => libraries => add new
            // Load the Driver (net.sf.ucanaccess:ucanaccess:5.0.0)

            // Establish the Connection
            connection = DriverManager.getConnection("jdbc:ucanaccess://" + Main.msAccessDB);
            // Create the Statement
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DoublyLinkedList<Student> getAll() {
        try{
            ResultSet resultSet = statement.executeQuery(Student.getSelectQuery());

            DoublyLinkedList<Student> studentList = new DoublyLinkedList<>();

            while (resultSet.next()) {
                Student student = new Student(

                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getInt(9),
                        resultSet.getDouble(10),
                        resultSet.getInt(11)

                );
                
                System.out.println(student.toString());
                
                studentList.add(student);
            }

            return studentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public Boolean addStudent(Student student) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(student.getInsertQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateStudent(Student student) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(student.getUpdateQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean deleteStudent(Student student) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(student.getDeleteQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

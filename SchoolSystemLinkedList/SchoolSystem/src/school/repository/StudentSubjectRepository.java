package school.repository;

import school.main.Main;
import school.models.StudentSubject;

import java.sql.*;
import java.util.LinkedList;

public class StudentSubjectRepository {
    private Connection connection;
    private Statement statement;

    public StudentSubjectRepository() {
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

    public LinkedList<StudentSubject> getAll() {
        try{
            ResultSet resultSet = statement.executeQuery(StudentSubject.getSelectQuery());

            LinkedList<StudentSubject> studentSubjectList = new LinkedList<>();

            while (resultSet.next()) {
                StudentSubject studentSubject = new StudentSubject(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)
                );
                
                System.out.println(studentSubject.toString());
                
                studentSubjectList.add(studentSubject);
            }

            return studentSubjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public Boolean addStudentSubject(StudentSubject studentSubject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(studentSubject.getInsertQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateStudentSubject(StudentSubject studentSubject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(studentSubject.getUpdateQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean deleteStudentSubject(StudentSubject studentSubject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(studentSubject.getDeleteQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

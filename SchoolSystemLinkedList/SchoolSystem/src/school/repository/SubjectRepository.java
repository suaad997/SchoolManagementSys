package school.repository;

import school.main.Main;
import school.models.Subject;

import java.sql.*;
import java.util.LinkedList;

public class SubjectRepository {
    private Connection connection;
    private Statement statement;

    public SubjectRepository() {
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

    public LinkedList<Subject> getAll() {
        try{
            ResultSet resultSet = statement.executeQuery(Subject.getSelectQuery());

            LinkedList<Subject> subjectList = new LinkedList<>();

            while (resultSet.next()) {

                Subject subject = new Subject(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                
                System.out.println(subject.toString());
                
                subjectList.add(subject);
            }

            return subjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public Boolean addSubject(Subject subject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(subject.getInsertQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateSubject(Subject subject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(subject.getUpdateQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean deleteSubject(Subject subject) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(subject.getDeleteQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

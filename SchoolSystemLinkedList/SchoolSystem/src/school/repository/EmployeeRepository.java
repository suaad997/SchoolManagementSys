package school.repository;

import school.main.Main;
import school.models.Employee;

import java.sql.*;
import java.util.LinkedList;

public class EmployeeRepository {
    private Connection connection;
    private Statement statement;

    public EmployeeRepository() {
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

    public LinkedList<Employee> getAll() {
        try {
            ResultSet resultSet = statement.executeQuery(Employee.getSelectQuery());

            LinkedList<Employee> employeeList = new LinkedList<>();

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getInt(11)
                );

                System.out.println(employee.toString());

                employeeList.add(employee);
            }

            return employeeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public Boolean addEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(employee.getInsertQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean updateEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(employee.getUpdateQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean deleteEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(employee.getDeleteQuery());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

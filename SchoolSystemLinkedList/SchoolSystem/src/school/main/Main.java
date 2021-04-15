package school.main;

import school.models.Employee;
import school.models.Student;
import school.models.Subject;
import school.service.EmployeeQuestions;
import school.service.StudentQuestions;
import school.service.SubjectService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static final String msAccessDB = "E:\\Platforms\\Java\\Projects\\SchoolSystem\\src\\school\\db\\SchoolDB.accdb";

    public static final int MAIN_TYPE_EMPLOYEE = 1;
    public static final int MAIN_TYPE_STUDENT = 2;
    public static final int MAIN_TYPE_SUBJECT = 3;

    static LinkedList<Employee> employeeList = new LinkedList<>();
    static LinkedList<Student> studentList = new LinkedList<>();
    static LinkedList<Subject> subjectList = new LinkedList<>();

    public Main() {
        createMainFrame();
    }

    public static void main(String[] args) {
        new Main();

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("==================== School System");
            System.out.println("Please enter");
            System.out.println(frist_TYPE_EMPLOYEE + ": To manage Employee");
            System.out.println(frist_TYPE_STUDENT + ": To manage Student");
            System.out.println(frist_TYPE_SUBJECT + ": To manage Subject");
            System.out.print("Your choice is: ");
            choice = input.nextInt();
            switch (choice) {
                case MAIN_TYPE_EMPLOYEE: {
                    EmployeeQuestions questions = new EmployeeQuestions(employeeList);
                    employeeList = questions.execute();
                    break;
                }
                case MAIN_TYPE_STUDENT: {
                    StudentQuestions questions = new StudentQuestions(studentList);
                    studentList = questions.execute();
                    break;
                }
                case MAIN_TYPE_SUBJECT: {
                    SubjectService questions = new SubjectService(subjectList);
                    subjectList = questions.execute();
                    break;
                }
                default: {
                    System.out.println("Please enter correct choice");
                    break;
                }
            }
        } while (choice != 0);

    }

    public void createMainFrame() {
        JLabel label = new JLabel("Welcome to \"School System\" ");


        JButton employeeButton = new JButton();
        employeeButton.setName("Employee");
        employeeButton.setText("Employees");
        employeeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeQuestions(employeeList).showEmployeeFrame();

            }
        });

        JButton studentButton = new JButton();
        studentButton.setName("Student");
        studentButton.setText("Students");
        studentButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentQuestions(studentList).showStudentFrame();

            }
        });

        JButton subjectButton = new JButton();
        subjectButton.setName("Subject");
        subjectButton.setText("Subjects");
        subjectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubjectService(subjectList).showSubjectFrame();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(employeeButton);
        buttonsPanel.add(studentButton);
        buttonsPanel.add(subjectButton);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(buttonsPanel);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setVisible(true);
        frame.setTitle("School System");
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);
        frame.add(panel);
    }
}

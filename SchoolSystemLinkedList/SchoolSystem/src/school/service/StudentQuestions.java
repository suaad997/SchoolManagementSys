package school.service;

import school.models.Student;
import school.repository.StudentRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;

public class StudentQuestions {
    public static final int ACTION_TYPE_SHOW_ALL = 1;
    public static final int ACTION_TYPE_SHOW_BY_INDEX = 2;
    public static final int ACTION_TYPE_ADD = 3;
    public static final int ACTION_TYPE_UPDATE = 4;
    public static final int ACTION_TYPE_DELETE = 5;

    private final StudentRepository studentRepository;

    private LinkedList<Student> studentList;

    public StudentQuestions(LinkedList<Student> studentList) {
        studentRepository = new StudentRepository();

        this.studentList = studentList;
        if (this.studentList.size() == 0) {
            // get all student from database
            this.studentList = studentRepository.getAll();
        }
    }

    public LinkedList<Student> execute() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("==================== Student System");
            System.out.println("Please enter");
            System.out.println(ACTION_TYPE_SHOW_ALL + ": To show Student");
            System.out.println(ACTION_TYPE_SHOW_BY_INDEX + ": To show Student by index");
            System.out.println(ACTION_TYPE_ADD + ": To add Student");
            System.out.println(ACTION_TYPE_UPDATE + ": To update Student by index");
            System.out.println(ACTION_TYPE_DELETE + ": To delete Student");
            System.out.print("Your choice is: ");
            choice = input.nextInt();
            switch (choice) {
                case ACTION_TYPE_SHOW_ALL: {
                    showAllStudent(input);
                    break;
                }
                case ACTION_TYPE_SHOW_BY_INDEX: {
                    showStudentByIndex(input);
                    break;
                }
                case ACTION_TYPE_ADD: {
                    addStudent(input);
                    break;
                }
                case ACTION_TYPE_UPDATE: {
                    updateStudent(input);
                    break;
                }
                case ACTION_TYPE_DELETE: {
                    deleteStudent(input);
                    break;
                }
                default: {
                    System.out.println("Please enter correct choice");
                    break;
                }
            }
        } while (choice != 0);

        // close connection to database
        studentRepository.close();

        // return students data to school.main page
        return studentList;
    }

    public void showAllStudent(Scanner input) {
        if (studentList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (Student Student : studentList) {
                System.out.println(Student.toString());
            }
        }
    }

    public void showStudentByIndex(Scanner input) {
        System.out.println("Please enter Student index position: ");
        int index = input.nextInt();
        if (studentList.size() > index) {
            System.out.println("The student data in position " + index + " is: ");
            System.out.println(studentList.get(index));
        } else {
            System.out.println("There is no student in this position.");
        }
    }

    // ==========================================================================

    public void addStudent(Scanner input) {
        Student studentData = new Student();

        System.out.println("Please enter Student id: ");
        studentData.setId(input.nextInt());

        System.out.println("Please enter Student first name: ");
        studentData.setFirst_name(input.nextLine());

        System.out.println("Please enter Student last name: ");
        studentData.setLast_name(input.nextLine());

        System.out.println("Please enter Student phone: ");
        studentData.setPhone(input.nextLine());

        System.out.println("Please enter Student age: ");
        studentData.setAge(input.nextInt());

        System.out.println("Please enter Student address: ");
        studentData.setAddress(input.nextLine());

        System.out.println("Please enter Student birth date: ");
        studentData.setBirth_date(input.nextLine());

        System.out.println("Please enter Student gender: ");
        studentData.setGender(input.nextLine());

        System.out.println("Please enter Student level: ");
        studentData.setLevel(input.nextInt());

        System.out.println("Please enter Student work Fees paid: ");
        studentData.setFees_paid(input.nextDouble());

        System.out.println("Please enter Student work Fees total: ");
        studentData.setFees_total(input.nextDouble());

        // add student to database
        if (studentRepository.addStudent(studentData)) {
            // add student to list
            studentList.add(studentData);
            System.out.println("Student is address successfully.");
        }
    }

    public void updateStudent(Scanner input) {
        System.out.println("Please enter Student index position: ");
        int index = input.nextInt();
        Student studentData = studentList.get(index);

        System.out.println("Please enter Student first name: ");
        studentData.setFirst_name(input.nextLine());

        System.out.println("Please enter Student last name: ");
        studentData.setLast_name(input.nextLine());

        System.out.println("Please enter Student phone: ");
        studentData.setPhone(input.nextLine());

        System.out.println("Please enter Student age: ");
        studentData.setAge(input.nextInt());

        System.out.println("Please enter Student address: ");
        studentData.setAddress(input.nextLine());

        System.out.println("Please enter Student birth date: ");
        studentData.setBirth_date(input.nextLine());

        System.out.println("Please enter Student gender: ");
        studentData.setGender(input.nextLine());

        System.out.println("Please enter Student level: ");
        studentData.setLevel(input.nextInt());

        System.out.println("Please enter Student work Fees paid: ");
        studentData.setFees_paid(input.nextDouble());

        System.out.println("Please enter Student work Fees total: ");
        studentData.setFees_total(input.nextDouble());

        // update student in database
        if (studentRepository.updateStudent(studentData)) {
            // update student in list
            studentList.set(index, studentData);
            System.out.println("Student is updated successfully.");
        }
    }

    public void deleteStudent(Scanner input) {
        System.out.println("Please enter Student index position: ");
        int index = input.nextInt();

        Student studentData = studentList.get(index);

        // delete student from database
        if (studentRepository.deleteStudent(studentData)) {
            // delete student from list
            studentList.remove(index);
            System.out.println("Student is deleted successfully.");
        }
    }

    // ==========================================================================

    public void showStudentFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setTitle("Students");

        // get all student from database
        this.studentList = studentRepository.getAll();

        Object[] columnNames = { "ID", "First name", "Last name", "Phone", "Age", "Address",
                "Birth_date", "Gender", "Level", "Fees paid", "Fees total"};

        Object[][] rowData = new Object[studentList.size()][11];


        for (int i = 0; i < studentList.size(); i++) {
            Object[] data = {studentList.get(i).getId(),
                    studentList.get(i).getFirst_name(),
                    studentList.get(i).getLast_name(),
                    studentList.get(i).getPhone(),
                    studentList.get(i).getAge(),
                    studentList.get(i).getAddress(),
                    studentList.get(i).getBirth_date(),
                    studentList.get(i).getGender(),
                    studentList.get(i).getLevel(),
                    studentList.get(i).getFees_paid(),
                    studentList.get(i).getFees_total(),
            };
            rowData[i] = data;
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel btnPanel = new JPanel();
        btnPanel.add(new JButton(new AbstractAction("Add new") {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentFrame();
                frame.setVisible(false);
            }
        }));

        btnPanel.add(new JButton(new AbstractAction("Delete selected row") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // get index of selected row
                    int index = table.getSelectedRow();
                    if (index >= 0) {
                        // get data of position 0 from selected row
                    Integer id = (Integer) table.getModel().getValueAt(index, 0);
                    System.out.println(index + " delete from students where id=" + id);

                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                    if (dialogResult == 0) {
                        System.out.println("Yes option");

                        Student studentData = studentList.get(index);
                        // delete subject from database
                        if (studentRepository.deleteStudent(studentData)) {
                            // delete subject from list
                            studentList.remove(index);
                            // delete subject from table
                            model.removeRow(index);
                        }

                        JOptionPane.showMessageDialog(null, "Student is deleted successfully.");
                        System.out.println("Student is deleted successfully.");
                    } else {
                        System.out.println("No Option");
                    }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));

        btnPanel.add(new JButton(new AbstractAction("Delete first row") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // get index of selected row
                    int index = 0; //table.getSelectedRow();
                    if (index >= 0) {
                        // get data of first position  from list
                        int id = studentList.getFirst().id;
                        System.out.println(index + " delete from students where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Student student = studentList.getFirst();

                            // delete student from database
                            if (studentRepository.deleteStudent(student)) {
                                // delete student from list
                                studentList.removeFirst();
                                // delete student from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Student is deleted successfully.");
                            System.out.println("Student is deleted successfully.");
                        } else {
                            System.out.println("No Option");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));

        btnPanel.add(new JButton(new AbstractAction("Delete last row") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // get index of selected row
                    int index = studentList.size()-1; //table.getSelectedRow();
                    if (index >= 0) {
                        // get data of last position  from list
                        int id = studentList.getLast().id;
                        System.out.println(index + " delete from students where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Student student = studentList.getLast();

                            // delete student from database
                            if (studentRepository.deleteStudent(student)) {
                                // delete student from list
                                studentList.removeLast();
                                // delete student from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Student is deleted successfully.");
                            System.out.println("Student is deleted successfully.");
                        } else {
                            System.out.println("No Option");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));


        btnPanel.add(new JButton(new AbstractAction("Close") {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                // close connection to database
                studentRepository.close();
            }
        }));

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width/2-frame.getSize().width/2 , die.height/2-frame.getSize().height/2);

    }
    public void addStudentFrame() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("Welcome to \"Student\" ");

        JLabel fNameLbl = new JLabel("First Name: ");
        JTextField txt_first_name = new JTextField();
        txt_first_name.setColumns(15);
        txt_first_name.setVisible(true);
        JLabel lNameLbl = new JLabel("Last Name: ");
        JTextField txt_last_name = new JTextField();
        txt_last_name.setColumns(15);
        txt_last_name.setVisible(true);
        JPanel nPanel = new JPanel();
        nPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        nPanel.add(fNameLbl);
        nPanel.add(txt_first_name);
        nPanel.add(lNameLbl);
        nPanel.add(txt_last_name);

        JLabel phoneLbl = new JLabel("Phone: ");
        JTextField txt_phone = new JTextField();
        txt_phone.setColumns(22);
        txt_phone.setVisible(true);
        JPanel numPanel = new JPanel();
        numPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        numPanel.add(phoneLbl);
        numPanel.add(txt_phone);

        JLabel ageLbl = new JLabel("Age:  ");
        JTextField txt_age = new JTextField();
        txt_age.setColumns(13);
        txt_age.setVisible(true);
        JLabel birtDateLbl = new JLabel("DirtDay: ");
        JTextField txt_birth_date = new JTextField();
        txt_birth_date.setColumns(22);
        txt_birth_date.setVisible(true);
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        datePanel.add(ageLbl);
        datePanel.add(txt_age);
        datePanel.add(birtDateLbl);
        datePanel.add(txt_birth_date);

        JLabel addressLbl = new JLabel("Address: ");
        JTextField txt_address = new JTextField();
        txt_address.setColumns(20);
        txt_address.setVisible(true);
        JLabel genderLbl = new JLabel("Gender: ");
        JTextField txt_gender = new JTextField();
        txt_gender.setColumns(13);
        txt_gender.setVisible(true);
        JPanel addgenPanel = new JPanel();
        addgenPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        addgenPanel.add(addressLbl);
        addgenPanel.add(txt_address);
        addgenPanel.add(genderLbl);
        addgenPanel.add(txt_gender);

        JLabel levelLbl = new JLabel("Level: ");
        JTextField txt_level = new JTextField();
        txt_level.setColumns(15);
        txt_level.setVisible(true);
        JPanel nPanel1 = new JPanel();
        nPanel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        nPanel1.add(levelLbl);
        nPanel1.add(txt_level);

        JLabel fees_paidLbl = new JLabel("Fees Paid: ");
        JTextField txt_fees_paid = new JTextField();
        txt_fees_paid.setColumns(15);
        txt_fees_paid.setVisible(true);
        JPanel nPanel2 = new JPanel();
        nPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        nPanel2.add(fees_paidLbl);
        nPanel2.add(txt_fees_paid);

        JLabel fees_totalLbl = new JLabel("Fees Total: ");
        JTextField txt_fees_total = new JTextField();
        txt_fees_total.setColumns(15);
        txt_fees_total.setVisible(true);
        JPanel nPanel6 = new JPanel();
        nPanel6.setLayout(new FlowLayout(FlowLayout.LEADING));
        nPanel6.add(fees_totalLbl);
        nPanel6.add(txt_fees_total);

        JButton okBtn = new JButton();
        okBtn.setText("Save");
        okBtn.setSize(0, 0);
        okBtn.setLocation(2, 10);
        okBtn.setVisible(true);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create new model
                Student studentData = new Student();

                // add data to model
                studentData.setFirst_name(txt_first_name.getText());
                studentData.setLast_name(txt_last_name.getText());
                studentData.setPhone(txt_phone.getText());

                if (!txt_age.getText().isEmpty())
                    studentData.setAge(Integer.parseInt(txt_age.getText()));

                studentData.setAddress(txt_address.getText());
                studentData.setBirth_date(txt_birth_date.getText());
                studentData.setGender(txt_gender.getText());

                if (!txt_level.getText().isEmpty())
                    studentData.setLevel(Integer.parseInt(txt_level.getText()));

                if (!txt_fees_paid.getText().isEmpty())
                    studentData.setFees_paid(Double.parseDouble(txt_fees_paid.getText()));

                if (!txt_fees_total.getText().isEmpty())
                    studentData.setFees_total(Double.parseDouble(txt_fees_total.getText()));

                System.out.println(studentData.toString());

                // check if data id empty
                if (studentData.getFirst_name().isEmpty() || studentData.getLast_name().isEmpty()
                        || studentData.getPhone().isEmpty() || studentData.getAge() == 0
                        || studentData.getAddress().isEmpty() || studentData.getBirth_date().isEmpty()
                        || studentData.getGender().isEmpty() || studentData.getLevel() == 0
                        || studentData.getFees_paid() == 0 || studentData.getFees_total() == 0) {
                    JOptionPane.showMessageDialog(null, "Please file all fields.");
                    return;
                }

                // add student to database
                if (studentRepository.addStudent(studentData)) {
                    // add student to list
                    studentList.add(studentData);
                    // close add frame
                    frame.setVisible(false);
                    // show start frame
                    showStudentFrame();

                    JOptionPane.showMessageDialog(null, "Student is added successfully.");
                    System.out.println("Student is added successfully.");
                }
            }
        });

        JButton cancelBtn = new JButton();
        cancelBtn.setText("Cancel");
        cancelBtn.setSize(0, 0);
        cancelBtn.setLocation(2, 10);
        cancelBtn.setVisible(true);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close add frame
                frame.setVisible(false);
                // show start frame
                showStudentFrame();
            }
        });
        JPanel BtnsPanel = new JPanel();
        BtnsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        BtnsPanel.add(okBtn);
        BtnsPanel.add(cancelBtn);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(nPanel);
        panel.add(numPanel);
        panel.add(datePanel);
        panel.add(addgenPanel);
        panel.add(nPanel1);
        panel.add(nPanel2);
        panel.add(nPanel6);
        panel.add(BtnsPanel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setVisible(true);
        frame.setTitle("Student");
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);
        frame.add(panel);
    }


}

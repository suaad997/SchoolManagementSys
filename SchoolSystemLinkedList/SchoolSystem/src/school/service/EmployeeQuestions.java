package school.service;

import school.models.Employee;
import school.repository.EmployeeRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;

public class EmployeeQuestions {
    public static final int ACTION_TYPE_SHOW_ALL = 1;
    public static final int ACTION_TYPE_SHOW_BY_INDEX = 2;
    public static final int ACTION_TYPE_ADD = 3;
    public static final int ACTION_TYPE_UPDATE = 4;
    public static final int ACTION_TYPE_DELETE = 5;

    private final EmployeeRepository employeeRepository;

    private LinkedList<Employee> employeeList;

    public EmployeeQuestions(LinkedList<Employee> employeeList) {
        employeeRepository = new EmployeeRepository();

        this.employeeList = employeeList;
        if (this.employeeList.size() == 0) {
            // get all employees from database
            this.employeeList = employeeRepository.getAll();
        }
    }

    public LinkedList<Employee> execute() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("==================== Employee System");
            System.out.println("Please enter");
            System.out.println(ACTION_TYPE_SHOW_ALL + ": To show Employee");
            System.out.println(ACTION_TYPE_SHOW_BY_INDEX + ": To show Employee by index");
            System.out.println(ACTION_TYPE_ADD + ": To add Employee");
            System.out.println(ACTION_TYPE_UPDATE + ": To update Employee by index");
            System.out.println(ACTION_TYPE_DELETE + ": To delete Employee");
            System.out.print("Your choice is: ");
            choice = input.nextInt();
            switch (choice) {
                case ACTION_TYPE_SHOW_ALL: {
                    showAllEmployee(input);
                    break;
                }
                case ACTION_TYPE_SHOW_BY_INDEX: {
                    showEmployeeByIndex(input);
                    break;
                }
                case ACTION_TYPE_ADD: {
                    addEmployee(input);
                    break;
                }
                case ACTION_TYPE_UPDATE: {
                    updateEmployee(input);
                    break;
                }
                case ACTION_TYPE_DELETE: {
                    deleteEmployee(input);
                    break;
                }
                default: {
                    System.out.println("Please enter correct choice");
                    break;
                }
            }
        } while (choice != 0);

        // close connection to database
        employeeRepository.close();

        // return employees data to school.main page
        return employeeList;
    }

    public void showAllEmployee(Scanner input) {
        if (employeeList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            // Both blow for loop has the same result

            // foreach
//        for (Employee employee: employeeList) {
//            System.out.println(employee.toString());
//        }

            // for loop
            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println(employeeList.get(i));
            }
        }
    }

    public void showEmployeeByIndex(Scanner input) {
        System.out.println("Please enter employee index position: ");
        int index = input.nextInt();
        if (employeeList.size() > index) {
            System.out.println("The employee data in position " + index + " is: ");
            System.out.println(employeeList.get(index));
        } else {
            System.out.println("There is no employee in this position.");
        }
    }

    // ==========================================================================

    public void addEmployee(Scanner input) {
        Employee employeeData = new Employee();

        System.out.println("Please enter employee id: ");
        employeeData.setId(input.nextInt());

        System.out.println("Please enter employee first name: ");
        employeeData.setFirst_name(input.nextLine());

        System.out.println("Please enter employee last name: ");
        employeeData.setLast_name(input.nextLine());

        System.out.println("Please enter employee phone: ");
        employeeData.setPhone(input.nextLine());

        System.out.println("Please enter employee age: ");
        employeeData.setAge(input.nextInt());

        System.out.println("Please enter employee address: ");
        employeeData.setAddress(input.nextLine());

        System.out.println("Please enter employee birth date: ");
        employeeData.setBirth_date(input.nextLine());

        System.out.println("Please enter employee gender: ");
        employeeData.setGender(input.nextLine());

        System.out.println("Please enter employee price: ");
        employeeData.setPrice(input.nextDouble());

        System.out.println("Please enter employee salary: ");
        employeeData.setSalary(input.nextDouble());

        System.out.println("Please enter employee work hours: ");
        employeeData.setWorking_hours(input.nextInt());

        // add employee to database
        if (employeeRepository.addEmployee(employeeData)) {
            // add employee to list
            employeeList.add(employeeData);
            System.out.println("Employee is added successfully.");
        }
    }

    public void updateEmployee(Scanner input) {
        System.out.println("Please enter employee index position: ");
        int index = input.nextInt();
        Employee employeeData = employeeList.get(index);

        System.out.println("Please enter employee first name: ");
        employeeData.setFirst_name(input.nextLine());

        System.out.println("Please enter employee last name: ");
        employeeData.setLast_name(input.nextLine());

        System.out.println("Please enter employee phone: ");
        employeeData.setPhone(input.nextLine());

        System.out.println("Please enter employee age: ");
        employeeData.setAge(input.nextInt());

        System.out.println("Please enter employee address: ");
        employeeData.setAddress(input.nextLine());

        System.out.println("Please enter employee birth date: ");
        employeeData.setBirth_date(input.nextLine());

        System.out.println("Please enter employee gender: ");
        employeeData.setGender(input.nextLine());

        System.out.println("Please enter employee price: ");
        employeeData.setPrice(input.nextDouble());

        System.out.println("Please enter employee salary: ");
        employeeData.setSalary(input.nextDouble());

        System.out.println("Please enter employee work hours: ");
        employeeData.setWorking_hours(input.nextInt());

        // update employee in database
        if (employeeRepository.updateEmployee(employeeData)) {
            // update employee in list
            employeeList.set(index, employeeData);
            System.out.println("Employee is updated successfully.");
        }
    }

    public void deleteEmployee(Scanner input) {

        System.out.println("Please enter employee index position: ");
        int index = input.nextInt();

        Employee employeeData = employeeList.get(index);

        // delete employee from database
        if (employeeRepository.deleteEmployee(employeeData)) {
            // delete employee from list
            employeeList.remove(index);
            System.out.println("Employee is deleted successfully.");
        }
    }

    // ==========================================================================

    public void showEmployeeFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setTitle("Employees");

        Object[] columnNames = {"ID", "First name", "Last name", "Phone", "Age", "Address",
                "Birth_date", "Gender", "Price", "Salary", "Working hours"};

        // get all employees from database
        this.employeeList = employeeRepository.getAll();

        Object[][] rowData = new Object[employeeList.size()][11];

        for (int i = 0; i < employeeList.size(); i++) {
            System.out.println(" delete from employees where id=" + employeeList.get(i).getId());

            Object[] data = {employeeList.get(i).getId(),
                    employeeList.get(i).getFirst_name(),
                    employeeList.get(i).getLast_name(),
                    employeeList.get(i).getPhone(),
                    employeeList.get(i).getAge(),
                    employeeList.get(i).getAddress(),
                    employeeList.get(i).getBirth_date(),
                    employeeList.get(i).getGender(),
                    employeeList.get(i).getPrice(),
                    employeeList.get(i).getSalary(),
                    employeeList.get(i).getWorking_hours(),
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
                addEmployeeFrame();
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
                        System.out.println(index + " delete from employees where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Employee employeeData = employeeList.get(index);

                            // delete subject from database
                            if (employeeRepository.deleteEmployee(employeeData)) {
                                // delete subject from list
                                employeeList.remove(index);
                                // delete subject from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Employee is deleted successfully.");
                            System.out.println("Employee is deleted successfully.");
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
                        int id = employeeList.getFirst().id;
                        System.out.println(index + " delete from employees where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Employee employeeData = employeeList.getFirst();

                            // delete employee from database
                            if (employeeRepository.deleteEmployee(employeeData)) {
                                // delete employee from list
                                employeeList.removeFirst();
                                // delete employee from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Employee is deleted successfully.");
                            System.out.println("Employee is deleted successfully.");
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
                    int index = employeeList.size()-1; //table.getSelectedRow();
                    if (index >= 0) {
                        // get data of last position  from list
                        int id = employeeList.getLast().id;
                        System.out.println(index + " delete from employees where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Employee employeeData = employeeList.getLast();

                            // delete employee from database
                            if (employeeRepository.deleteEmployee(employeeData)) {
                                // delete employee from list
                                employeeList.removeLast();
                                // delete employee from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Employee is deleted successfully.");
                            System.out.println("Employee is deleted successfully.");
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
                employeeRepository.close();
            }
        }));

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);
    }

    public void addEmployeeFrame() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("welcome to \"Employee\" ");

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

        JLabel priceLbl = new JLabel("Price: ");
        JTextField txt_price = new JTextField();
        txt_price.setColumns(20);
        txt_price.setVisible(true);
        JLabel salaryLbl = new JLabel("Salary: ");
        JTextField txt_salary = new JTextField();
        txt_salary.setColumns(13);
        txt_salary.setVisible(true);
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        addPanel.add(priceLbl);
        addPanel.add(txt_price);
        addPanel.add(salaryLbl);
        addPanel.add(txt_salary);


        JLabel work_houLbl = new JLabel("Working hours: ");
        JTextField txt_work_hou = new JTextField();
        txt_work_hou.setColumns(20);
        txt_work_hou.setVisible(true);
        JPanel addPanel2 = new JPanel();
        addPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        addPanel2.add(work_houLbl);
        addPanel2.add(txt_work_hou);


        JButton okBtn = new JButton();
        okBtn.setText("Save");
        okBtn.setSize(0, 0);
        okBtn.setLocation(2, 10);
        okBtn.setVisible(true);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create new model
                Employee employeeDate = new Employee();

                // add data to model
                employeeDate.setFirst_name(txt_first_name.getText());
                employeeDate.setLast_name(txt_last_name.getText());
                employeeDate.setPhone(txt_phone.getText());
                if (!txt_age.getText().isEmpty())
                    employeeDate.setAge(Integer.parseInt(txt_age.getText()));
                employeeDate.setAddress(txt_address.getText());
                employeeDate.setBirth_date(txt_birth_date.getText());
                employeeDate.setGender(txt_gender.getText());

                if (!txt_price.getText().isEmpty())
                    employeeDate.setPrice(Integer.parseInt(txt_price.getText()));

                if (!txt_salary.getText().isEmpty())
                    employeeDate.setSalary(Double.parseDouble(txt_salary.getText()));

                if (!txt_work_hou.getText().isEmpty())
                    employeeDate.setWorking_hours(Integer.parseInt(txt_work_hou.getText()));

                System.out.println(employeeDate.toString());

                // check if data id empty
                if (employeeDate.getFirst_name().isEmpty() || employeeDate.getLast_name().isEmpty()
                        || employeeDate.getPhone().isEmpty() || employeeDate.getAge() == 0
                        || employeeDate.getAddress().isEmpty() || employeeDate.getBirth_date().isEmpty()
                        || employeeDate.getGender().isEmpty() || employeeDate.getPrice() == 0
                        || employeeDate.getSalary() == 0 || employeeDate.getWorking_hours() == 0) {
                    JOptionPane.showMessageDialog(null, "Please file all fields.");
                    return;
                }

                // add student to database
                if (employeeRepository.addEmployee(employeeDate)) {
                    // add student to list
                    employeeList.add(employeeDate);
                    // close add frame
                    frame.setVisible(false);
                    // show start frame
                    showEmployeeFrame();

                    JOptionPane.showMessageDialog(null, "Employee is added successfully.");
                    System.out.println("Employee is added successfully.");
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
                showEmployeeFrame();
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
        panel.add(addPanel);
        panel.add(addPanel2);
        panel.add(BtnsPanel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setVisible(true);
        frame.setTitle("Employee");
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);
        frame.add(panel);
    }


}

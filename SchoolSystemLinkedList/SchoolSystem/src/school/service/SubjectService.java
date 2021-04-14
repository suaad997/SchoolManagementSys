package school.service;

import school.models.Student;
import school.models.Subject;
import school.repository.SubjectRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;

public class SubjectService {
    public static final int ACTION_TYPE_SHOW_ALL = 1;
    public static final int ACTION_TYPE_SHOW_BY_INDEX = 2;
    public static final int ACTION_TYPE_ADD = 3;
    public static final int ACTION_TYPE_UPDATE = 4;
    public static final int ACTION_TYPE_DELETE = 5;

    private final SubjectRepository subjectRepository;

    private LinkedList<Subject> subjectList;

    public SubjectService(LinkedList<Subject> subjectList) {
        subjectRepository = new SubjectRepository();

        this.subjectList = subjectList;

        if (this.subjectList.size() == 0) {

            // get all student from database
            this.subjectList = subjectRepository.getAll();
        }
    }

    public LinkedList<Subject> execute() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("==================== Subject System");
            System.out.println("Please enter");
            System.out.println(ACTION_TYPE_SHOW_ALL + ": To show Subject");
            System.out.println(ACTION_TYPE_SHOW_BY_INDEX + ": To show Subject by index");
            System.out.println(ACTION_TYPE_ADD + ": To add Subject");
            System.out.println(ACTION_TYPE_UPDATE + ": To update Subject by index");
            System.out.println(ACTION_TYPE_DELETE + ": To delete Subject");
            System.out.print("Your choice is: ");
            choice = input.nextInt();
            switch (choice) {
                case ACTION_TYPE_SHOW_ALL: {
                    showAllSubject(input);
                    break;
                }
                case ACTION_TYPE_SHOW_BY_INDEX: {
                    showSubjectByIndex(input);
                    break;
                }
                case ACTION_TYPE_ADD: {
                    addSubject(input);
                    break;
                }
                case ACTION_TYPE_UPDATE: {
                    updateSubject(input);
                    break;
                }
                case ACTION_TYPE_DELETE: {
                    deleteSubject(input);
                    break;
                }
                default: {
                    System.out.println("Please enter correct choice");
                    break;
                }
            }
        } while (choice != 0);

        // close connection from database
        subjectRepository.close();

        // return subjects data to school.main page
        return subjectList;
    }

    public void showAllSubject(Scanner input) {
        if (subjectList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (Subject Subject : subjectList) {
                System.out.println(Subject.toString());
            }
        }
    }

    public void showSubjectByIndex(Scanner input) {
        System.out.println("Please enter Subject index position: ");
        int index = input.nextInt();
        if (subjectList.size() > index) {
            System.out.println("The subject data in position " + index + " is: ");
            System.out.println(subjectList.get(index));
        } else {
            System.out.println("There is no subject in this position.");
        }
    }

    // ==========================================================================

    public void addSubject(Scanner input) {
        Subject subjectData = new Subject();

        System.out.println("Please enter Subject id: ");
        subjectData.setId(input.nextInt());

        System.out.println("Please enter Subject name: ");
        String name = input.next();
        subjectData.setName(name);


        // add subject to database
        if (subjectRepository.addSubject(subjectData)) {
            // add subject to list
            subjectList.add(subjectData);
            System.out.println("Subject is added successfully.");
        }
    }

    public void updateSubject(Scanner input) {
        System.out.println("Please enter Subject index position: ");
        int index = input.nextInt();

        Subject subjectData = subjectList.get(index);

        System.out.println("Please enter Subject name: ");
        String name = input.next();
        subjectData.setName(name);

        // update subject in database
        if (subjectRepository.updateSubject(subjectData)) {
            // update subject in list
            subjectList.set(index, subjectData);
            System.out.println("Subject is updated successfully.");
        }
    }

    public void deleteSubject(Scanner input) {
        System.out.println("Please enter Subject index position: ");
        int index = input.nextInt();


        Subject subjectData = subjectList.get(index);

        // delete subject from database
        if (subjectRepository.deleteSubject(subjectData)) {
            // delete subject from list
            subjectList.remove(index);
            System.out.println("Subject is deleted successfully.");
        }
    }

    // ==========================================================================

    public void showSubjectFrame() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setTitle("Subjects");

        // get all student from database
        this.subjectList = subjectRepository.getAll();

        Object[] columnNames = {"ID", "Name"};

        // Object rowData[][] = { { "1", "Name 1" }, { "1", "Name 1" } };
        Object[][] rowData = new Object[subjectList.size()][2];


        for (int i = 0; i < subjectList.size(); i++) {

            Object[] data = {subjectList.get(i).getId(), subjectList.get(i).getName()};

            rowData[i] = data;
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel btnPanel = new JPanel();
        btnPanel.add(
                new JButton(new AbstractAction("Add new") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addSubjectFrame();
                        frame.setVisible(false);
                    }
                })
        );

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

                        System.out.println(index + " delete from subjects where id=" + id);

                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete from subjects where id=" + id, "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Subject subjectData = subjectList.get(index);

                            // delete subject from database
                            if (subjectRepository.deleteSubject(subjectData)) {
                                // delete subject from list
                                subjectList.remove(index);
                                // delete subject from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Subject is deleted successfully.");
                            System.out.println("Subject is deleted successfully.");
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
                        int id = subjectList.getFirst().id;
                        System.out.println(index + " delete from students where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Subject student = subjectList.getFirst();

                            // delete student from database
                            if (subjectRepository.deleteSubject(student)) {
                                // delete student from list
                                subjectList.removeFirst();
                                // delete student from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Subject is deleted successfully.");
                            System.out.println("Subject is deleted successfully.");
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
                    int index = subjectList.size()-1; //table.getSelectedRow();
                    if (index >= 0) {
                        // get data of last position  from list
                        int id = subjectList.getLast().id;
                        System.out.println(index + " delete from students where id=" + id);

                        // create Warning dialog
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(btnPanel, "Delete", "Are you sure?", dialogButton);
                        if (dialogResult == 0) {
                            System.out.println("Yes option");

                            Subject subject = subjectList.getLast();

                            // delete student from database
                            if (subjectRepository.deleteSubject(subject)) {
                                // delete student from list
                                subjectList.removeLast();
                                // delete student from table
                                model.removeRow(index);
                            }

                            JOptionPane.showMessageDialog(null, "Subject is deleted successfully.");
                            System.out.println("Subject is deleted successfully.");
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
                subjectRepository.close();
            }
        }));

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.PAGE_END);

        //frame.pack();
        frame.setVisible(true);
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);

    }

    public void addSubjectFrame() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("Welcome to \"Subject\" ");

        JLabel subjectLbl = new JLabel("Subject Full Name: ");
        JTextField subjectTxt = new JTextField();
        subjectTxt.setColumns(34);
        subjectTxt.setVisible(true);

        JPanel nPanel = new JPanel();
        nPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        nPanel.add(subjectLbl);
        nPanel.add(subjectTxt);


        JButton okBtn = new JButton();
        okBtn.setText("Save");
        okBtn.setSize(0, 0);
        okBtn.setLocation(2, 10);
        okBtn.setVisible(true);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create new model
                Subject subjectData = new Subject();

                // add data to model
                subjectData.setName(subjectTxt.getText());

                System.out.println(subjectData.toString());

                // check if data id empty
                if (subjectData.getName().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please file all fields.");
                    return;
                }

                // add subject to database
                if (subjectRepository.addSubject(subjectData)) {
                    // add subject to list
                    subjectList.add(subjectData);
                    // close add frame
                    frame.setVisible(false);
                    // show start frame
                    showSubjectFrame();

                    JOptionPane.showMessageDialog(null, "Subject is added successfully.");
                    System.out.println("Subject is added successfully.");
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
                showSubjectFrame();
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
        panel.add(BtnsPanel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setVisible(true);
        frame.setTitle("Subject");
        Dimension die = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(die.width / 2 - frame.getSize().width / 2, die.height / 2 - frame.getSize().height / 2);
        frame.add(panel);

    }
}

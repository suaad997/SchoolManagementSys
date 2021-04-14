package school.service;

import school.models.StudentSubject;
import school.repository.StudentSubjectRepository;

import java.util.LinkedList;
import java.util.Scanner;

public class StudentSubjectQuestions {
    public static final int ACTION_TYPE_SHOW_ALL = 1;
    public static final int ACTION_TYPE_SHOW_BY_INDEX = 2;
    public static final int ACTION_TYPE_ADD = 3;
    public static final int ACTION_TYPE_UPDATE = 4;
    public static final int ACTION_TYPE_DELETE = 5;

    private final StudentSubjectRepository studentSubjectRepository;
    private  LinkedList<StudentSubject> studentSubjectList;

    public StudentSubjectQuestions(LinkedList<StudentSubject> studentSubjectList) {
        studentSubjectRepository = new StudentSubjectRepository();

        this.studentSubjectList = studentSubjectList;
        if(this.studentSubjectList.size() == 0){
            // get all student from database
            this.studentSubjectList = studentSubjectRepository.getAll();
        }
    }

    public LinkedList<StudentSubject> execute() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("==================== StudentSubject System");
            System.out.println("Please enter");
            System.out.println(ACTION_TYPE_SHOW_ALL + ": To show StudentSubject");
            System.out.println(ACTION_TYPE_SHOW_BY_INDEX + ": To show StudentSubject by index");
            System.out.println(ACTION_TYPE_ADD + ": To add StudentSubject");
            System.out.println(ACTION_TYPE_UPDATE + ": To update StudentSubject by index");
            System.out.println(ACTION_TYPE_DELETE + ": To delete StudentSubject");
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

        // close connection to database
        studentSubjectRepository.close();

        // return subjects data to school.main page
        return studentSubjectList;
    }

    public void showAllSubject(Scanner input) {
        if (studentSubjectList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (StudentSubject studentSubject : studentSubjectList) {
                System.out.println(studentSubject.toString());
            }
        }
    }

    public void showSubjectByIndex(Scanner input) {
        System.out.println("Please enter StudentSubject index position: ");
        int index = input.nextInt();

        if (studentSubjectList.size() > index) {
            System.out.println("The subject data in position " + index + " is: ");
            System.out.println(studentSubjectList.get(index));
        } else {
            System.out.println("There is no subject in this position.");
        }
    }

    // ==========================================================================

    public void addSubject(Scanner input) {
        StudentSubject subjectData = new StudentSubject();

        System.out.println("Please enter StudentSubject id: ");
        subjectData.setId(input.nextInt());

        System.out.println("Please enter Student id: ");
        subjectData.setStudent_id(input.nextInt());

        System.out.println("Please enter Subject id: ");
        subjectData.setStudent_id(input.nextInt());

        // add subject to database
        if (studentSubjectRepository.addStudentSubject(subjectData)) {
            // add subject to list
            studentSubjectList.add(subjectData);
            System.out.println("StudentSubject is added successfully.");
        }
    }

    StudentSubject subjectData;
    public void updateSubject(Scanner input) {
        System.out.println("Please enter StudentSubject index position: ");
        int index = input.nextInt();
        StudentSubject subjectData = studentSubjectList.get(index);

        System.out.println("Please enter Student id: ");
        subjectData.setStudent_id(input.nextInt());

        System.out.println("Please enter Subject id: ");
        subjectData.setStudent_id(input.nextInt());

        // update subject in database
        if (studentSubjectRepository.updateStudentSubject(subjectData)) {
            // update subject in list
            studentSubjectList.set(index, subjectData);
            System.out.println("StudentSubject is updated successfully.");
        }
    }

    public void deleteSubject(Scanner input) {
        System.out.println("Please enter StudentSubject index position: ");
        int index = input.nextInt();


        StudentSubject subjectData = studentSubjectList.get(index);

        // delete subject from database
        if (studentSubjectRepository.deleteStudentSubject(subjectData)) {
            // delete subject from list
            studentSubjectList.remove(index);
            System.out.println("StudentSubject is deleted successfully.");
        }
    }

    // ==========================================================================

}

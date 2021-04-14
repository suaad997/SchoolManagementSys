package school.models;

public class StudentSubject {
    public int id;
    public int student_id;
    public int subject_id;


    public StudentSubject() {
    }

    public StudentSubject(int id, int student_id, int subject_id) {
        this.id = id;
        this.student_id = student_id;
        this.subject_id = subject_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ",student_id=" + student_id +
                ",subject_id=" + subject_id +
                '}';
    }

    // utils
    public static String getSelectQuery() {
        return "SELECT * FROM student_subjects";
    }

    public String getInsertQuery() {
        return "insert into student_subjects (id,student_id,subject_id) " +
                "values(" + id +
                "," + student_id +
                "," + subject_id + ")";
    }
    public String getUpdateQuery() {
        return "Update student_subjects SET " +

                "id=" + id +
                ",student_id = " + student_id +
                ",subject_id = " + subject_id +

                " WHERE id =" + id;
    }
    public String getDeleteQuery() {
        return "DELETE FROM student_subjects WHERE id =" + id;
    }

}

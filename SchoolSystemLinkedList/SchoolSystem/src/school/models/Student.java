package school.models;

public class Student extends Person {
    public int level;
    public double fees_paid;
    public double fees_total;

    public Student(int id, String first_name, String last_name, String phone, int age, String address
            , String birth_date, String gender, int level, double fees_paid, double fees_total) {
        super(id, first_name, last_name, phone, age, address, birth_date, gender);
        this.level = level;
        this.fees_paid = fees_paid;
        this.fees_total = fees_total;
    }

    public Student() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getFees_paid() {
        return fees_paid;
    }

    public void setFees_paid(double fees_paid) {
        this.fees_paid = fees_paid;
    }

    public double getFees_total() {
        return fees_total;
    }

    public void setFees_total(double fees_total) {
        this.fees_total = fees_total;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", gender='" + gender + '\'' +
                ", level=" + level +
                ", sees_paid=" + fees_paid +
                ", fees_total=" + fees_total +
                '}';
    }

    // utils
    public static String getSelectQuery() {
        return "SELECT * FROM students";
    }
    public String getInsertQuery() {
        return "insert into students (id,first_name,last_name,phone,age,address,birth_date,gender,level,fees_paid,fees_total) " +
                "values(" + id +
                ",'" + first_name + "'" +
                ",'" + last_name + "'" +
                ",'" + phone + "'" +
                "," + age + "" +
                ",'" + address + "'" +
                ",'" + birth_date + "'" +
                ",'" + gender + "'" +
                ",'" + level + "'" +
                "," + fees_paid + "" +
                "," + fees_total + ")";
    }
    public String getUpdateQuery() {
        return "Update students SET  " +

                " id=" + id +
                ",first_name = '" + first_name + "'" +
                ",last_name ='" + last_name + "'" +
                ",phone = '" + phone + "'" +
                ",age = " + age + "" +
                ",address = '" + address + "'" +
                ",birth_date = '" + birth_date + "'" +
                ",gender = '" + gender + "'" +
                ",level = '" + level + "'" +
                ",fees_paid = " + fees_paid + "" +
                ",fees_total = " + fees_total +

                "   WHERE id =" + id;
    }
    public String getDeleteQuery() {
        return "DELETE FROM students WHERE id =" + id;
    }

}

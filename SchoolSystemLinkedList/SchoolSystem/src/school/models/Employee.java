package school.models;

public class Employee extends Person{
    public double price;
    public double salary;
    public int working_hours;

    public Employee(int id, String first_name, String last_name, String phone, int age, String address, String birth_date, String gender, double price, double salary, int working_hours) {
        super(id, first_name, last_name, phone, age, address, birth_date, gender);
        this.price = price;
        this.salary = salary;
        this.working_hours = working_hours;
    }

    public Employee() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(int working_hours) {
        this.working_hours = working_hours;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", gender='" + gender + '\'' +
                ", price=" + price +
                ", salary=" + salary +
                ", working_hours=" + working_hours +
                '}';
    }


    // utils
    public static String getSelectQuery() {
        return "SELECT id,first_name,last_name,phone,age,address,brith_date,gender,price,salary,working_hours FROM employees";
    }
    public String getInsertQuery() {
        return "insert into employees (id,first_name,last_name,phone,age,address,brith_date,gender,price,salary,working_hours) " +
                "values(" + id +
                ",'" + first_name + "'" +
                ",'" + last_name + "'" +
                ",'" + phone + "'" +
                "," + age +
                ",'" + address + "'" +
                ",'" + birth_date + "'" +
                ",'" + gender + "'" +
                "," + price +
                "," + salary +
                "," + working_hours + ")";
    }
    public String getUpdateQuery() {
        return "Update employees SET " +

                "id=" + id +
                ",first_name = '" + first_name + "'" +
                ",last_name ='" + last_name + "'" +
                ",phone = '" + phone + "'" +
                ",age = " + age + "" +
                ",address = '" + address + "'" +
                ",brith_date = '" + birth_date + "'" +
                ",gender = '" + gender + "'" +
                ",price = " + price + "" +
                ",salary = " + salary + "" +
                ",working_hours = " + working_hours +

                "  WHERE id =" + id;
    }
    public String getDeleteQuery() {
        return "DELETE FROM employees WHERE id =" + id;
    }
}

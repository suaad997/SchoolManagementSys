package school.models;

public class Subject {
    public int id;
    public String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Subject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // utils
    public static String getSelectQuery() {
        return "SELECT * FROM subjects";
    }

    public String getInsertQuery() {
        return "insert into subjects (id,name) " +
                "values(" + id + ",'" + name + "')";
    }
    public String getUpdateQuery() {
        return "UPDATE subjects SET " +

                "id=" + id +
                ",name = '" + name + "'" +

                " WHERE id =" + id;
    }
    public String getDeleteQuery() {
        return "DELETE FROM subjects WHERE id =" + id;
    }

}

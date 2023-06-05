
public class Student implements Comparable<Student> {
    private String name;
    private String lastName;
    StudentCondition stan;
    private int birthYear;
    private double points;

    public Student(String name, String lastName, StudentCondition stan, int birthYear, double points) {
        this.name = name;
        this.lastName = lastName;
        this.stan = stan;
        this.birthYear = birthYear;
        this.points = points;
    }

    @Override
    public int compareTo(Student s) {
        return this.lastName.compareTo(s.lastName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPoints() {
        return points;
    }

    public void setStan(StudentCondition stan) {
        this.stan = stan;
    }

    public Student setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public StudentCondition getStan() {
        return stan;
    }

    public Student setPoints(double points) {
        this.points = points;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public String print() {
        return "Student{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", stan=" + stan +
                ", birthYear=" + birthYear +
                ", points=" + points +
                '}';
    }
}


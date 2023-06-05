
import java.util.*;


public class Class {

    private String groupName;
    private List<Student> students = new ArrayList<>();
    private int maxStudents;

    public Class(String name, int maxStudents) {
        this.groupName = name;
        this.maxStudents=maxStudents;
    }
    public String getGroupName() {
        return  this.groupName;
    }
    public List<Student> getStudents() {
        return this.students;
    }
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
    public double getGroupSize(){
        return students.size();
    }
    public int getMaxGroupSize() {
        return this.maxStudents;
    }

    public void addStudent(Student student) {
        for (Student stud : students) {
            if (stud.getName().contains(student.getName())) {
                System.out.println("Student o tym imieniu juz istnieje");
            }
        }

        if (students.size() >= maxStudents) {
            System.err.println("Maksymalna ilosc studentow, nie mozna dodac wiecej.");
            return;
        }
        students.add(student);
    }

    public void addPoints(Student student, double points) {
        student.setPoints(student.getPoints() + points);
    }

    public Optional<Student> getStudent(Student student) {

        Iterator<Student> iterator = this.students.iterator();

        while (iterator.hasNext()) {
            Student listStudent = iterator.next();

            if (0 == listStudent.compareTo(student) /* 0 == student.getPoints()*/) {
                iterator.remove();
                return Optional.of(student);
            }
        }

        return Optional.empty();

    }

    public void changeCondition(Student student, StudentCondition stan) {
        student.setStan(stan);
    }

    public void removePoints(Student student, double points){
        student.setPoints(student.getPoints()-points);
    }

    public Optional<Student> search(String lastname) {
        return this.students.stream()
                .filter(el -> lastname.equals(el.getLastName()))
                .findFirst();
    }

    public String searchPartial(String namePart) {
        String str="";
        for (Student student : students) {
            if (student.getName().contains(namePart) || student.getLastName().contains(namePart)) {
                str+=student.print()+"\n";
            }
        }
        if(str==""){
            return "Brak w systemie";
        }
        return str;
    }

    public int countCondition(StudentCondition condition) {
        int count = 0;
        for (Student student : students) {
            if (student.getStan() == condition) {
                count++;
            }
        }
        return count;
    }
    public void summary() {
        for (Student student : students) {
            System.out.println( student.print());
        }
    }
    public ArrayList<Student> sortByName() {
        Collections.sort(students);
        return (ArrayList<Student>) students;
    }

    public ArrayList<Student> sortByPoints() {
        Collections.sort(students, new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                if (s1.getPoints() > s2.getPoints())
                    return -1;
                if (s1.getPoints() < s2.getPoints())
                    return 1;
                return 0;
            }
        });
        return (ArrayList<Student>) students;
    }
    public String max() {
        Student mostPoints= Collections.max(students, Comparator.comparing(Student::getPoints));
        return mostPoints.print();
    }

    public boolean isEmpty() {
        return this.students.isEmpty();
    }

    public double getPercentageFill() {
        return (double) this.getGroupSize() / (double) this.getMaxGroupSize() * 100.0;
    }

}

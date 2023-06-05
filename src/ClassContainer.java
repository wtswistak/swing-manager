import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {

    private Map<String, Class> classes;
    public ClassContainer() {
        classes = new HashMap<>();
    }

    public void addClass(String className, int maxStudents) {
        Class newClass = new Class(className, maxStudents);
        classes.put(className, newClass);
    }

    public void editClass(String name, int volume) {
        Class classroom = this.getClass(name);
        classroom.setMaxStudents(volume);
    }

    public void removeClass(String className) {
        classes.remove(className);
    }

    public Class getClass(String name) {
        return this.classes.get(name);
    }

    public ArrayList<String> findEmpty() {
        ArrayList<String> emptyClasses = new ArrayList<>();
        for (Class c : classes.values()) {
            if (c.getStudents().size() == 0) {
                emptyClasses.add(c.getGroupName());
            }
        }
        return emptyClasses;
    }
    public void summary() {
        for (Class c : classes.values()) {
            double fillPercentage = (c.getGroupSize() / c.getMaxGroupSize())*100;
            System.out.printf("Nazwa grupy: "+c.getGroupName()+" procentowe zapelnienie:"+fillPercentage+"%%\n");
        }
    }
    public ArrayList<Class> getClasses() {
        return new ArrayList(this.classes.values());
    }

}

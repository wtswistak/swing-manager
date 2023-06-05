import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class ClassTable {
    private final JTable table;
    private final ClassTableModel model;
    private final ClassContainer classContainer;
    private ListSelectionListener listener;


    public ClassTable(ClassContainer classContainer, ListSelectionListener listener) {
        this.listener = listener;
        this.classContainer = classContainer;
        this.model = new ClassTableModel(this.classContainer.getClasses());

        this.table = new JTable(this.model);
        this.table.setBounds(30, 40, 200, 300);
        this.table.getSelectionModel().addListSelectionListener(this.listener);
    }

    public JTable getTable() {
        return this.table;
    }

    public void addClassroom(String name, int volume) {
        this.classContainer.addClass(name, volume);
        this.tableChanged();
    }

    public void editClassroom(int max) {
        int selectedRow = this.table.getSelectedRow();
        if (-1 < selectedRow){
            String classroomName = (String)this.table.getValueAt(selectedRow, ClassTableModel.COLUMN_NAME);
            this.classContainer.editClass(classroomName, max);
            this.tableChanged();
        }
    }

    public void deleteClassroom() {
        int selectedRow = this.table.getSelectedRow();
        if (-1 < selectedRow){
            String classroomName = (String)this.table.getValueAt(selectedRow, ClassTableModel.COLUMN_NAME);
            this.classContainer.removeClass(classroomName);
            this.tableChanged();
        }
    }

    public void tableChanged() {
        this.model.setClassrooms(this.classContainer.getClasses());
        this.model.fireTableDataChanged();
    }

    public List<Student> getClassroomStudents() {
        int selectedRow = this.table.getSelectedRow();
        if (-1 < selectedRow) {
            String classroomName = (String)this.table.getValueAt(selectedRow, ClassTableModel.COLUMN_NAME);
            Class classroom = this.classContainer.getClass(classroomName);
            return classroom.getStudents();
        }
        return null;
    }

    public Class getSelectedClassroom() {
        int selectedRow = this.table.getSelectedRow();
        if (-1 < selectedRow) {
            String classroomName = (String)this.table.getValueAt(selectedRow, ClassTableModel.COLUMN_NAME);
            return this.classContainer.getClass(classroomName);

        }
        return null;
    }

    public List<Student> sortStudentsPoints() {
        int selectedRow = this.table.getSelectedRow();
        if (-1 < selectedRow) {
            String classroomName = (String)this.table.getValueAt(selectedRow, ClassTableModel.COLUMN_NAME);
            Class classroom = this.classContainer.getClass(classroomName);
            return classroom.sortByPoints();
        }

        return null;
    }

}

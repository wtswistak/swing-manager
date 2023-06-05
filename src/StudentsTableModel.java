import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentsTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_LASTNAME = 1;
    public static final int COLUMN_BIRTH_YEAR = 2;
    public static final int COLUMN_POINTS = 3;
    public static final int COLUMN_STATUS = 4;
    private final String[] columns = {"Imie", "Nazwisko", "Rok urodzenia", "Punkty", "Stan"};

    private List<Student> students;

    public StudentsTableModel(List<Student> students) {
        super();
        this.setStudents(students);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getRowCount() {
        if (students != null) {
            return students.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = this.students.get(rowIndex);
        switch (columnIndex) {
            case StudentsTableModel.COLUMN_NAME:
                return student.getName();
            case StudentsTableModel.COLUMN_LASTNAME:
                return student.getLastName();
            case StudentsTableModel.COLUMN_BIRTH_YEAR:
                return student.getBirthYear();
            case StudentsTableModel.COLUMN_POINTS:
                return student.getPoints();
            case StudentsTableModel.COLUMN_STATUS:
                return student.getStan();
        }
        return new String();
    }

    public String getColumnName(int i) {
        return this.columns[i];
    }
}

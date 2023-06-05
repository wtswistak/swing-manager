import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ClassTableModel extends AbstractTableModel {
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_MAX_STUDENT_COUNT = 1;
    public static final int COLUMN_STUDENTS_COUNT = 2;
    public static final int COLUMN_PERCENTAGE_FILLING = 3;
    private final String[] columns = {"Nazwa", "Max", "Liczba studentów", "% studentow"};

    private ArrayList<Class> classes;

    public ClassTableModel(ArrayList<Class> classes1) {
        super();
        this.setClassrooms(classes1);
    }

    public void setClassrooms(ArrayList<Class> classes1) {
        this.classes = classes1;
    }

    @Override
    public int getRowCount() {
        if (classes != null) {
            return this.classes.size();
        } else {
            return 0;
        }

    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowId, int columnId) {
        Class class1 = this.classes.get(rowId);
        switch (columnId) {
            case ClassTableModel.COLUMN_NAME:
                return class1.getGroupName();
            case ClassTableModel.COLUMN_MAX_STUDENT_COUNT:
                return class1.getMaxGroupSize();
            case ClassTableModel.COLUMN_STUDENTS_COUNT:
                return class1.getGroupSize();
            case ClassTableModel.COLUMN_PERCENTAGE_FILLING:
                return class1.getPercentageFill();
        }
        return new String();
    }

    public String getColumnName(int i) {
        return this.columns[i];
    }
}

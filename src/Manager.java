import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Manager implements ActionListener, ListSelectionListener {

    private final GridBagLayout btnGrid;
    private final GridBagConstraints btnGridSettings;

    private final JFrame frame;
    private final JPanel mainPanel;
    private final JPanel bottomPanel;

    private final JButton addClassBtn;
    private final JButton deleteClassBtn;
    private final JButton editClassBtn;
    private final JButton addStudentBtn;
    private final JButton deleteStudentBtn;
    private final JButton editStudentBtn;
    private final JButton sortStudentPointsBtn;

    private final ClassTable classroomsTable;
    private final JScrollPane classroomTablePanel;
    private final StudentsTable studentsTable;
    private final JScrollPane studentTablePanel;


    public Manager() {

        btnGrid = new GridBagLayout();
        btnGridSettings = new GridBagConstraints();

        frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 440);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(btnGrid);

        this.addClassBtn = new JButton();
        this.editClassBtn = new JButton();
        this.deleteClassBtn = new JButton();
        this.addStudentBtn = new JButton();
        this.editStudentBtn = new JButton();
        this.deleteStudentBtn = new JButton();
        this.sortStudentPointsBtn = new JButton();

        this.setBtn(this.addClassBtn, "Dodaj klasę", Actions.ADD_CLASSROOM, 0 ,0)
                .setBtn(this.deleteClassBtn,"Usuń klasę", Actions.DELETE_CLASSROOM, 1,0)
                .setBtn(this.editClassBtn,"Edytuj klasę", Actions.EDIT_CLASSROOM, 2,0)
                .setBtn(this.addStudentBtn,"Dodaj studenta", Actions.ADD_STUDENT, 3,0)
                .setBtn(this.deleteStudentBtn,"Usuń studenta", Actions.DELETE_STUDENT, 4,0)
                .setBtn(this.editStudentBtn,"Edytuj studenta", Actions.EDIT_STUDENT, 5,0)
                .setBtn(this.sortStudentPointsBtn,"Sortuj", Actions.STUDENT_SORT_POINTS, 6,0);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        ClassContainer classContainer = new ClassContainer();
        this.classroomsTable = new ClassTable(classContainer, this);
        this.classroomTablePanel = new JScrollPane(this.classroomsTable.getTable());

        ArrayList<Student> students = new ArrayList<>();
        this.studentsTable = new StudentsTable(students);
        this.studentTablePanel = new JScrollPane(this.studentsTable.getTable());

        this.mainPanel.add(this.classroomTablePanel);
        this.mainPanel.add(this.studentTablePanel);

        frame.getContentPane().add(BorderLayout.SOUTH, this.bottomPanel);
        frame.getContentPane().add(BorderLayout.CENTER, this.mainPanel);
        frame.setVisible(true);
    }

    private Manager setBtn(JButton btn, String text, String action, int posX, int posY) {
        btn.setText(text);
        btn.setActionCommand(action);
        btn.addActionListener(this);
        this.setBtnGridSetting(posX,posY);
        this.bottomPanel.add(btn, this.btnGridSettings);
        return this;
    }

    private void setBtnGridSetting(int x, int y) {
        this.btnGridSettings.fill = GridBagConstraints.HORIZONTAL;
        this.btnGridSettings.gridx = x;
        this.btnGridSettings.gridy = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case Actions.ADD_CLASSROOM -> this.addClassroom();
            case Actions.EDIT_CLASSROOM -> this.editClassroom();
            case Actions.DELETE_CLASSROOM -> this.classroomsTable.deleteClassroom();
            case Actions.ADD_STUDENT -> this.addStudent();
            case Actions.EDIT_STUDENT -> this.editStudent();
            case Actions.DELETE_STUDENT -> this.deleteStudent();
            case Actions.STUDENT_SORT_POINTS -> this.sortStudentsByPoints();
        }


    }

    private void showStudents() {
        List<Student> studentList = this.classroomsTable.getClassroomStudents();
        this.studentsTable.setStudents(studentList);
    }

    private void sortStudentsByPoints() {
        List<Student> students = this.classroomsTable.sortStudentsPoints();
        this.studentsTable.setStudents(students);
    }

    private void deleteStudent() {
        String studentLastname = this.studentsTable.getSelectedStudent();
        if (studentLastname == null) {
            JOptionPane.showMessageDialog(null, "Zaznacz ucznia.");
            return;
        }

        Class classroom = this.classroomsTable.getSelectedClassroom();
        if (classroom == null) {
            JOptionPane.showMessageDialog(null, "Zaznacz klasę.");
            return;
        }

        Optional<Student> optionalStudent = classroom.search(studentLastname);
        if (optionalStudent.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Brak uczniow w klasie.");
            return;
        }

        Student student = optionalStudent.get();
        classroom.getStudent(student);
        this.classroomsTable.tableChanged();
        this.studentsTable.fireTableDataChanged();

    }

    private void editStudent() {
        double points;
        Class classroom = this.classroomsTable.getSelectedClassroom();
        if (classroom == null) {
            JOptionPane.showMessageDialog(null, "Zaznacz klasę.");
            return;
        }

        String studentLastname = this.studentsTable.getSelectedStudent();
        Optional<Student> optionalStudent = classroom.search(studentLastname);
        if (optionalStudent.isEmpty()){
            JOptionPane.showMessageDialog(null, "Zaznacz ucznia.");
            return;
        }
        Student student = optionalStudent.get();

        String sName = (String) JOptionPane.showInputDialog(this.frame, "Imię:", null, JOptionPane.INFORMATION_MESSAGE, null, null, student.getName());
        String sLastname = (String) JOptionPane.showInputDialog(this.frame, "Nazwisko:",null, JOptionPane.INFORMATION_MESSAGE, null, null, student.getLastName());
        int birthYear;
        try {
            birthYear = Integer.parseInt((String) JOptionPane.showInputDialog(this.frame, "Rok urodzenia:",null, JOptionPane.INFORMATION_MESSAGE, null, null, student.getBirthYear()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Podaj rok");
            return;
        }
        try {
            points = Double.parseDouble((String) JOptionPane.showInputDialog(this.frame, "Punkty:",null, JOptionPane.INFORMATION_MESSAGE, null, null, student.getPoints()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Podaj punkty ");
            return;
        }

        StudentCondition[] choices = StudentCondition.values();
        StudentCondition sStatus = StudentCondition.valueOf(
                JOptionPane.showInputDialog(
                        this.frame,
                        "Wybierz stan ucznia",
                        null,
                        JOptionPane.QUESTION_MESSAGE,
                        null, choices, student.getStan()
                ).toString()
        );
        student.setName(sName);
        student.setBirthYear(birthYear);
        student.setPoints(points);
        student.setStan(sStatus);
        this.classroomsTable.tableChanged();
        this.studentsTable.fireTableDataChanged();
    }

    private void editClassroom() {
        try {
            int volume = Integer.parseInt(JOptionPane.showInputDialog(this.frame, "Pojemność:"));
            this.classroomsTable.editClassroom(volume);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Niepoprawna pojemność");
        }
    }

    private void addClassroom() {
        String name = JOptionPane.showInputDialog(this.frame, "Nazwa");
        try {
            int volume = Integer.parseInt(JOptionPane.showInputDialog(this.frame, "Pojemność:"));
            this.classroomsTable.addClassroom(name, volume);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Niepoprawna pojemność");
        }
    }

    private void addStudent() {
        Class classroom = this.classroomsTable.getSelectedClassroom();
        if (classroom == null) {
            JOptionPane.showMessageDialog(null, "Zaznacz klasę.");
            return;
        }
        String sName = JOptionPane.showInputDialog(this.frame, "Imię:");
        String sLastname = JOptionPane.showInputDialog(this.frame, "Nazwisko:");
        int birthYear;
        double points;
        try {
            birthYear = Integer.parseInt(JOptionPane.showInputDialog(this.frame, "Rok urodzenia:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Niepoprawny rok urodzenia");
            return;
        }
        try {
            points = Double.parseDouble(JOptionPane.showInputDialog(this.frame, "Punkty:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Niepoprawna ilość punktów");
            return;
        }
        StudentCondition[] choices = StudentCondition.values();
        StudentCondition sCondition = StudentCondition.valueOf(
                JOptionPane.showInputDialog(
                        this.frame,
                        "Wybierz stan",
                        null,
                        JOptionPane.QUESTION_MESSAGE,
                        null, choices, choices[1]
                ).toString()
        );
        Student student = new Student(sName, sLastname,sCondition, birthYear, points);
        try {
            classroom.addStudent(student);
        } catch (ClassException ex) {
            JOptionPane.showMessageDialog(null, "Klasa jest już pełna");
            return;
        }
        this.classroomsTable.tableChanged();
        this.studentsTable.setStudents(classroom.getStudents());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.showStudents();
    }
}

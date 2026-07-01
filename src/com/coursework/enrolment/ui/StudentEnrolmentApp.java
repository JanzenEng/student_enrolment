package com.coursework.enrolment.ui;

import com.coursework.enrolment.model.Student;
import com.coursework.enrolment.service.StudentEnrolmentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

/**
 * Java Swing UI for the Student Enrolment Program.
 *
 * The UI only displays data. The main storage is the custom DoublyLinkedList
 * accessed through StudentEnrolmentService.
 */
public class StudentEnrolmentApp extends JFrame {
    private final StudentEnrolmentService service;

    private JTextField searchField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField courseField;

    private DefaultTableModel tableModel;
    private JTable studentTable;

    private JPanel detailPanel;
    private JLabel detailIdLabel;
    private JLabel detailNameLabel;
    private JLabel detailEmailLabel;
    private JLabel detailCourseLabel;

    public StudentEnrolmentApp() {
        service = new StudentEnrolmentService();
        service.seedSampleData();

        setTitle("Student Enrolment Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);

        buildUI();
        refreshTable(service.getAllStudents());
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Student Enrolment Program");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createSearchPanel());
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createAddStudentPanel());
        mainPanel.add(Box.createVerticalStrut(20));

        detailPanel = createDetailPanel();
        detailPanel.setVisible(false);
        mainPanel.add(detailPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createTablePanel());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane);
    }

    private JPanel createSearchPanel() {
        JPanel panel = createSectionPanel("Search & Tools");

        searchField = new JTextField(20);
        searchField.setToolTipText("Search by name or course");

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(event -> handleSearch());

        JButton clearButton = new JButton("Clear Search / View All");
        clearButton.addActionListener(event -> handleViewAll());

        JButton reverseButton = new JButton("View Reverse Copy");
        reverseButton.addActionListener(event -> handleReverseCopy());

        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(event -> handleGenerateReport());

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(searchField);
        row.add(searchButton);
        row.add(clearButton);
        row.add(reverseButton);
        row.add(reportButton);

        panel.add(row);
        return panel;
    }

    private JPanel createAddStudentPanel() {
        JPanel panel = createSectionPanel("Add New Student");

        nameField = new JTextField(18);
        emailField = new JTextField(18);
        courseField = new JTextField(18);

        JButton enrolButton = new JButton("Enrol Student");
        enrolButton.addActionListener(event -> handleAddStudent());

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel("Name:"));
        row.add(nameField);
        row.add(new JLabel("Email:"));
        row.add(emailField);
        row.add(new JLabel("Course:"));
        row.add(courseField);
        row.add(enrolButton);

        panel.add(row);
        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = createSectionPanel("Selected Student Details");

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(0, 102, 204)),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(new Color(248, 248, 248));

        detailIdLabel = new JLabel();
        detailNameLabel = new JLabel();
        detailEmailLabel = new JLabel();
        detailCourseLabel = new JLabel();

        card.add(detailIdLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(detailNameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(detailEmailLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(detailCourseLabel);
        card.add(Box.createVerticalStrut(12));

        JButton closeButton = new JButton("Close Details");
        closeButton.addActionListener(event -> detailPanel.setVisible(false));
        card.add(closeButton);

        panel.add(card);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = createSectionPanel("Enrolled Students");

        String[] columns = {"ID", "Name", "Course", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(35);
        studentTable.getColumn("Actions").setCellRenderer(new ActionCellRenderer());
        studentTable.getColumn("Actions").setCellEditor(new ActionCellEditor());

        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setPreferredSize(new Dimension(1100, 250));
        panel.add(tableScrollPane);

        return panel;
    }

    private JPanel createSectionPanel(String title) {
        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        outerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        outerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        outerPanel.add(titleLabel);
        outerPanel.add(Box.createVerticalStrut(15));

        return outerPanel;
    }

    private void handleAddStudent() {
        try {
            service.addStudent(nameField.getText(), emailField.getText(), courseField.getText());
            clearInputFields();
            refreshTable(service.getAllStudents());
            showInfo("Student enrolled successfully.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void handleSearch() {
        Student[] results = service.searchStudents(searchField.getText());
        refreshTable(results);

        if (results.length == 0) {
            showInfo("No matching student record found.");
        }
    }

    private void handleViewAll() {
        searchField.setText("");
        detailPanel.setVisible(false);
        refreshTable(service.getAllStudents());
    }

    private void handleReverseCopy() {
        Student[] reversedStudents = service.getReverseCopy();
        refreshTable(reversedStudents);
        showInfo("Displaying reverse copy of the list. The original DLL order is not changed.");
    }

    private void handleGenerateReport() {
        JTextArea reportArea = new JTextArea(service.generateReport());
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(420, 260));

        JOptionPane.showMessageDialog(this, scrollPane, "Enrolment Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleViewStudent(int studentId) {
        Student student = service.findStudentById(studentId);

        if (student == null) {
            showError("Student record not found.");
            return;
        }

        detailIdLabel.setText("ID: " + student.getId());
        detailNameLabel.setText("Name: " + student.getName());
        detailEmailLabel.setText("Email: " + student.getEmail());
        detailCourseLabel.setText("Course: " + student.getCourse());
        detailPanel.setVisible(true);
    }

    private void handleDeleteStudent(int studentId) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = service.deleteStudentById(studentId);
        if (deleted) {
            detailPanel.setVisible(false);
            refreshTable(service.getAllStudents());
            showInfo("Student record deleted successfully.");
        } else {
            showError("Student record not found.");
        }
    }

    private void refreshTable(Student[] students) {
        tableModel.setRowCount(0);

        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getCourse(),
                    "Actions"
            });
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        emailField.setText("");
        courseField.setText("");
    }

    private int getStudentIdFromRow(int row) {
        int modelRow = studentTable.convertRowIndexToModel(row);
        Object value = tableModel.getValueAt(modelRow, 0);
        return Integer.parseInt(value.toString());
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Renderer for the Actions column. It displays View and Delete buttons.
     */
    private class ActionCellRenderer extends JPanel implements TableCellRenderer {
        private final JButton viewButton = new JButton("View");
        private final JButton deleteButton = new JButton("Delete");

        public ActionCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
            add(viewButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {
            return this;
        }
    }

    /**
     * Editor for the Actions column. It handles button clicks for each table row.
     */
    private class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        private final JButton viewButton = new JButton("View");
        private final JButton deleteButton = new JButton("Delete");
        private int editingRow = -1;

        public ActionCellEditor() {
            viewButton.addActionListener(event -> {
                int studentId = getStudentIdFromRow(editingRow);
                fireEditingStopped();
                handleViewStudent(studentId);
            });

            deleteButton.addActionListener(event -> {
                int studentId = getStudentIdFromRow(editingRow);
                fireEditingStopped();
                handleDeleteStudent(studentId);
            });

            panel.add(viewButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column
        ) {
            editingRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }

        @Override
        public boolean isCellEditable(EventObject event) {
            return true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentEnrolmentApp app = new StudentEnrolmentApp();
            app.setVisible(true);
        });
    }
}

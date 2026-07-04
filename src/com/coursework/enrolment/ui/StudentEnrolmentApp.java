package com.coursework.enrolment.ui;

import com.coursework.enrolment.model.Student;
import com.coursework.enrolment.service.StudentEnrolmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Main Swing UI for the Student Enrolment Program.
 */
public class StudentEnrolmentApp extends JFrame {
    private final StudentEnrolmentService service;

    private JTextField searchField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField courseField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JPanel detailsPanel;
    private JLabel detailsContentLabel;

    public StudentEnrolmentApp() {
        this.service = new StudentEnrolmentService();

        setTitle("Student Enrolment Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);

        buildUI();
        refreshTable(service.getAllStudents());
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Student Enrolment Program");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createSearchPanel());
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createAddStudentPanel());
        mainPanel.add(Box.createVerticalStrut(15));

        detailsPanel = createDetailsPanel();
        detailsPanel.setVisible(false);
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(createTablePanel());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        setContentPane(scrollPane);
    }

    private JPanel createSearchPanel() {
        JPanel panel = createSectionPanel("Search & Tools");

        searchField = new JTextField(18);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear Search / View All");
        JButton reportButton = new JButton("Generate Report");
        JButton reverseButton = new JButton("View Reverse Copy");

        searchButton.addActionListener(e -> handleSearch());
        clearButton.addActionListener(e -> handleViewAll());
        reportButton.addActionListener(e -> handleGenerateReport());
        reverseButton.addActionListener(e -> handleViewReverseCopy());

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(searchField);
        row.add(searchButton);
        row.add(clearButton);
        row.add(reportButton);
        row.add(reverseButton);

        panel.add(row);
        return panel;
    }

    private JPanel createAddStudentPanel() {
        JPanel panel = createSectionPanel("Add New Student");

        nameField = new JTextField(16);
        emailField = new JTextField(18);
        courseField = new JTextField(18);
        JButton enrolButton = new JButton("Enrol Student");

        enrolButton.addActionListener(e -> handleAddStudent());

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

    private JPanel createDetailsPanel() {
        JPanel panel = createSectionPanel("Selected Student Details");

        detailsContentLabel = new JLabel("No student selected.");
        detailsContentLabel.setVerticalAlignment(SwingConstants.TOP);

        JButton closeButton = new JButton("Close Details");
        closeButton.addActionListener(e -> detailsPanel.setVisible(false));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(detailsContentLabel, BorderLayout.CENTER);
        contentPanel.add(closeButton, BorderLayout.SOUTH);

        panel.add(contentPanel);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = createSectionPanel("Enrolled Students");

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Course"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(26);

        JButton viewButton = new JButton("View Selected");
        JButton deleteButton = new JButton("Delete Selected");

        viewButton.addActionListener(e -> handleViewSelectedStudent());
        deleteButton.addActionListener(e -> handleDeleteSelectedStudent());

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonRow.add(viewButton);
        buttonRow.add(deleteButton);

        panel.add(new JScrollPane(studentTable));
        panel.add(buttonRow);
        return panel;
    }

    private JPanel createSectionPanel(String title) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(titleLabel);
        wrapper.add(Box.createVerticalStrut(10));

        return wrapper;
    }

    private void handleAddStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields (Name, Email, Course).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isAdded = service.addStudent(name, email, course);

        if (isAdded) {
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable(service.getAllStudents());
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student. Student name might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSearch() {
      
       // TODO: Task 3 member will complete the search function logic.
        Student result = service.searchStudent(searchField.getText());

        if (result == null) {
            showTodoMessage("Search function");
            return;
        }

        refreshTable(new Student[]{result});
    }

    private void handleViewAll() {
         // TODO: Task 3 member will complete getAllStudents in the DLL.
        searchField.setText("");
        refreshTable(service.getAllStudents());
    }

    private void handleViewReverseCopy() {
       // // TODO: Task 3 member will complete reverse copy logic.
        Student[] reverseStudents = service.getReverseCopy();
        refreshTable(reverseStudents);

        if (reverseStudents.length == 0) {
            showTodoMessage("View Reverse Copy function");
        }
    }

    private void handleGenerateReport() {
        // TODO: Task 4 member will complete report generation logic.
        JOptionPane.showMessageDialog(this, service.generateReport(), "Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleViewSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a student first.");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        String email = tableModel.getValueAt(selectedRow, 2).toString();
        String course = tableModel.getValueAt(selectedRow, 3).toString();

        detailsContentLabel.setText("<html>"
                + "<b>ID:</b> " + id + "<br><br>"
                + "<b>Name:</b> " + name + "<br><br>"
                + "<b>Email:</b> " + email + "<br><br>"
                + "<b>Course:</b> " + course
                + "</html>");

        detailsPanel.setVisible(true);
    }

    private void handleDeleteSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.");
            return;
        }

        String name = tableModel.getValueAt(selectedRow, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete student: " + name + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }

        boolean isDeleted = service.deleteStudent(name);

        if (isDeleted) {
            JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable(service.getAllStudents());
            detailsPanel.setVisible(false); 
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete student. Record might not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(Student[] students) {
        tableModel.setRowCount(0);

        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getCourse()
            });
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        emailField.setText("");
        courseField.setText("");
    }

    private void showTodoMessage(String functionName) {
        JOptionPane.showMessageDialog(
                this,
                functionName + " is prepared as a placeholder. The assigned member should implement it.",
                "Function Not Implemented Yet",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentEnrolmentApp app = new StudentEnrolmentApp();
            app.setVisible(true);
        });
    }
}
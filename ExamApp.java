import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExamApp extends JFrame implements ActionListener {

    String studentName = "";
    String selectedExam = "";
    int currentQuestion = 0;
    int score = 0;

    int[] userAnswers = {-1, -1, -1, -1, -1};

    JPanel loginPanel;
    JTextField txtName;
    JComboBox<String> cbExamList;
    JButton btnStart;
    JLabel lblWarning;

    JPanel examPanel;
    JLabel lblStudentInfo, lblQuestion, lblResult, lblCorrectAnsInfo;
    JRadioButton op1, op2, op3, op4;
    ButtonGroup bg;
    JButton btnNext, btnPrev, btnSubmit;

    String[] javaQuestions = {
            "Q1: What is the full form of JDK?",
            "Q2: Which component runs Java code?",
            "Q3: Which keyword is used to inherit a class in Java?",
            "Q4: What is the default value of a boolean variable?",
            "Q5: Which of these is NOT a Java access modifier?"
    };
    String[][] javaOptions = {
            {"Java Development Kit", "Java Design Kit", "Java Desktop Key", "None"},
            {"JDK", "JVM", "JRE", "AWT"},
            {"implements", "extends", "inherits", "super"},
            {"true", "false", "0", "null"},
            {"public", "private", "protected", "friend"}
    };
    int[] javaAnswers = {0, 1, 1, 1, 3};

    String[] gkQuestions = {
            "Q1: Which planet is known as the Red Planet?",
            "Q2: What is the capital of India?",
            "Q3: Which is the largest ocean on Earth?",
            "Q4: Who is known as the Father of the Indian Constitution?",
            "Q5: How many continents are there on Earth?"
    };
    String[][] gkOptions = {
            {"Earth", "Jupiter", "Mars", "Saturn"},
            {"Mumbai", "Delhi", "Kolkata", "Chennai"},
            {"Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"},
            {"Mahatma Gandhi", "Dr. B.R. Ambedkar", "Jawaharlal Nehru", "Subhash Chandra Bose"},
            {"5", "6", "7", "8"}
    };
    int[] gkAnswers = {2, 1, 2, 1, 2};

    String[] currentQuestions;
    String[][] currentOptions;
    int[] currentAnswers;

    public ExamApp() {
        setTitle("Simple Online Exam with Answer Review");
        setSize(520, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        loginPanel = new JPanel(new GridLayout(7, 1, 5, 5));

        loginPanel.add(new JLabel("Enter Student Name:"));
        txtName = new JTextField(15);
        loginPanel.add(txtName);

        loginPanel.add(new JLabel("Select Exam Topic:"));
        String[] subjects = {"Java Programming", "General Knowledge"};
        cbExamList = new JComboBox<>(subjects);
        loginPanel.add(cbExamList);

        lblWarning = new JLabel("");
        lblWarning.setForeground(Color.RED);
        loginPanel.add(lblWarning);

        btnStart = new JButton("Start Exam");
        btnStart.addActionListener(this);
        loginPanel.add(btnStart);

        add(loginPanel);

        examPanel = new JPanel(new GridLayout(11, 1, 5, 5));

        lblStudentInfo = new JLabel();
        lblQuestion = new JLabel();

        op1 = new JRadioButton();
        op2 = new JRadioButton();
        op3 = new JRadioButton();
        op4 = new JRadioButton();

        bg = new ButtonGroup();
        bg.add(op1); bg.add(op2); bg.add(op3); bg.add(op4);

        btnPrev = new JButton("Previous Question");
        btnNext = new JButton("Next Question");
        btnSubmit = new JButton("Submit Exam");

        btnPrev.setEnabled(false);
        btnSubmit.setEnabled(false);

        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnSubmit.addActionListener(this);

        lblCorrectAnsInfo = new JLabel("");
        lblCorrectAnsInfo.setForeground(new Color(39, 174, 96));

        lblResult = new JLabel("");

        examPanel.add(lblStudentInfo);
        examPanel.add(lblQuestion);
        examPanel.add(op1); examPanel.add(op2); examPanel.add(op3); examPanel.add(op4);
        examPanel.add(lblCorrectAnsInfo);
        examPanel.add(btnPrev);
        examPanel.add(btnNext);
        examPanel.add(btnSubmit);
        examPanel.add(lblResult);

        examPanel.setVisible(false);
        add(examPanel);
    }

    void saveSelection() {
        if (op1.isSelected()) userAnswers[currentQuestion] = 0;
        else if (op2.isSelected()) userAnswers[currentQuestion] = 1;
        else if (op3.isSelected()) userAnswers[currentQuestion] = 2;
        else if (op4.isSelected()) userAnswers[currentQuestion] = 3;
    }

    void loadQuestionUI() {
        lblQuestion.setText(currentQuestions[currentQuestion]);
        op1.setText(currentOptions[currentQuestion][0]);
        op2.setText(currentOptions[currentQuestion][1]);
        op3.setText(currentOptions[currentQuestion][2]);
        op4.setText(currentOptions[currentQuestion][3]);

        op1.setForeground(Color.BLACK);
        op2.setForeground(Color.BLACK);
        op3.setForeground(Color.BLACK);
        op4.setForeground(Color.BLACK);

        bg.clearSelection();

        int previousSelection = userAnswers[currentQuestion];
        if (previousSelection == 0) op1.setSelected(true);
        else if (previousSelection == 1) op2.setSelected(true);
        else if (previousSelection == 2) op3.setSelected(true);
        else if (previousSelection == 3) op4.setSelected(true);

        btnPrev.setEnabled(currentQuestion > 0);
        btnNext.setEnabled(currentQuestion < currentQuestions.length - 1);
        btnSubmit.setEnabled(currentQuestion == currentQuestions.length - 1);
    }

    void loadReviewUI() {
        loadQuestionUI();

        int selected = userAnswers[currentQuestion];
        int correct = currentAnswers[currentQuestion];

        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op4.setEnabled(false);

        JRadioButton[] options = {op1, op2, op3, op4};

        if (selected != -1) {
            options[selected].setSelected(true);
        }

        if (selected == correct) {
            options[selected].setForeground(new Color(39, 174, 96));
            lblCorrectAnsInfo.setText("Correct Choice!");
            lblCorrectAnsInfo.setForeground(new Color(39, 174, 96));
        } else {
            if (selected != -1) {
                options[selected].setForeground(Color.RED);
            }
            options[correct].setForeground(new Color(39, 174, 96));
            lblCorrectAnsInfo.setText("Correct Answer: " + currentOptions[currentQuestion][correct]);
            lblCorrectAnsInfo.setForeground(Color.RED);
        }

        btnPrev.setEnabled(currentQuestion > 0);
        btnNext.setEnabled(currentQuestion < currentQuestions.length - 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnStart) {
            studentName = txtName.getText().trim();

            if (studentName.isEmpty()) {
                lblWarning.setText("Enter your name");
                return;
            }

            selectedExam = (String) cbExamList.getSelectedItem();

            if (selectedExam.equals("Java Programming")) {
                currentQuestions = javaQuestions;
                currentOptions = javaOptions;
                currentAnswers = javaAnswers;
            } else {
                currentQuestions = gkQuestions;
                currentOptions = gkOptions;
                currentAnswers = gkAnswers;
            }

            loginPanel.setVisible(false);
            examPanel.setVisible(true);

            lblStudentInfo.setText("Student: " + studentName + " | Exam: " + selectedExam);
            loadQuestionUI();
        }

        else if (e.getSource() == btnPrev) {
            saveSelection();
            currentQuestion--;

            if (btnNext.getText().equals("Next Review")) {
                loadReviewUI();
            } else {
                loadQuestionUI();
            }
        }

        else if (e.getSource() == btnNext && btnNext.getText().equals("Next Question")) {
            saveSelection();
            currentQuestion++;
            loadQuestionUI();
        }

        else if (e.getSource() == btnSubmit) {
            saveSelection();

            score = 0;
            for (int i = 0; i < currentQuestions.length; i++) {
                if (userAnswers[i] == currentAnswers[i]) {
                    score++;
                }
            }

            btnSubmit.setVisible(false);
            btnPrev.setText("Previous Review");
            btnNext.setText("Next Review");

            lblResult.setText("Final Score: " + score + " / " + currentQuestions.length);

            currentQuestion = 0;
            loadReviewUI();
        }

        else if (e.getSource() == btnNext && btnNext.getText().equals("Next Review")) {
            currentQuestion++;
            loadReviewUI();
        }
    }

    public static void main(String[] args) {
        ExamApp app = new ExamApp();
        app.setVisible(true);
    }
}
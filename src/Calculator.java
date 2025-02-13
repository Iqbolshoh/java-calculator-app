import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1 = 0, num2 = 0, result = 0;
    private boolean isNewOperation = false;

    public Calculator() {
        setTitle("Calculator");
        setSize(300, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            if (isNewOperation || display.getText().equals("0")) {
                display.setText(command);
                isNewOperation = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            num1 = num2 = result = 0;
            operator = "";
            isNewOperation = false;
        } else if (command.equals("=")) {
            if (!operator.isEmpty() && !display.getText().isEmpty()) {
                num2 = Double.parseDouble(display.getText().split(" ")[display.getText().split(" ").length - 1]);
                result = evaluate(num1, num2, operator);
                display.setText(String.valueOf(result));
                operator = "";
                num1 = result;
                isNewOperation = true;
            }
        } else {
            if (!display.getText().isEmpty()) {
                if (!operator.isEmpty()) {
                    num2 = Double.parseDouble(display.getText().split(" ")[display.getText().split(" ").length - 1]);
                    result = evaluate(num1, num2, operator);
                    display.setText(result + " " + command + " ");
                    num1 = result;
                } else {
                    num1 = Double.parseDouble(display.getText());
                    display.setText(display.getText() + " " + command + " ");
                }
                operator = command;
                isNewOperation = false;
            }
        }
    }

    private double evaluate(double num1, double num2, String operator) {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> (num2 != 0) ? num1 / num2 : Double.NaN;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
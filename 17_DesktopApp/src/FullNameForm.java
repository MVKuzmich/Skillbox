import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class FullNameForm {
    private JPanel fullName;
    private JTextField surname;
    private JTextField name;
    private JTextField secondName;
    private JButton button;
    private JLabel labelSurname;
    private JLabel labelName;
    private JLabel labelSecondName;

    public FullNameForm() {
        button.addActionListener(new ActionListener() {
            JTextField oneField = new JTextField();
            JLabel oneLabel = new JLabel();

            @Override
            public void actionPerformed(ActionEvent e) {

                switch (button.getText()) {
                    case "Collapse":
                        boolean isEmptySurname = surname.getText().isBlank();
                        boolean isEmptyName = name.getText().isBlank();
                        boolean hasSecondName = secondName.getText().isBlank();

                        if (!isEmptySurname && !isEmptyName) {

                            oneField.setColumns(20);
                            oneField.setText(surname.getText().concat(" ").concat(name.getText()));

                            fullName.add(oneField);

                            oneLabel.setText("ФИО");

                            fullName.add(oneLabel);

                            fullName.remove(surname);
                            fullName.remove(name);
                            fullName.remove(secondName);
                            fullName.remove(labelSurname);
                            fullName.remove(labelName);
                            fullName.remove(labelSecondName);

                            button.setText("Expand");
                        } else {
                            JOptionPane.showMessageDialog
                                    (fullName, "Введите правильно ФИО", "Внимание!", JOptionPane.PLAIN_MESSAGE);

                        }
                        break;
                    case "Expand":

                        String[] words = oneField.getText().split(" ");
                        if (words.length == 2) {
                            fullName.remove(oneField);
                            fullName.remove(oneLabel);
                            fullName.add(labelSurname);
                            fullName.add(surname);
                            fullName.add(labelName);
                            fullName.add(name);

                            surname.setText(words[0]);
                            name.setText(words[1]);

                            button.setText("Collapse");

                        } else {
                            JOptionPane.showMessageDialog
                                    (fullName, "Введите правильно ФИО", "Внимание!", JOptionPane.PLAIN_MESSAGE);

                        }
                        break;
                    default:
                        System.out.println("Something is wrong");
                }
            }

        });
    }


    public JPanel getFullNamePanel() {
        return fullName;
    }
}

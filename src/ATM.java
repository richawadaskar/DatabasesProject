import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM {

    private JPanel ATMView;
    private JButton OKButton;
    private JTextField usernameField;
    private JPasswordField pinField;

    public ATM() {
        OKButton.addActionListener(new OKBtnClicked());
    }

    private class OKBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentials(usernameField.getText(), pinField.getPassword().toString());
        }

    }

    public void checkCredentials(String username, String pin) {
        //Check if username exists. If exists, check if PIN matches
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ATM");
        frame.setContentPane(new ATM().ATMView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

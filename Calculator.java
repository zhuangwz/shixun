import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;

public class Calculator {
    JFrame frame = new JFrame("calculator");
    JTextField pre = new JTextField();
    JTextField symbol = new JTextField();
    JTextField back = new JTextField();
    JTextField equal = new JTextField();
    JTextField result = new JTextField();
    JButton plus = new JButton("+");
    JButton sub = new JButton("-");
    JButton mul = new JButton("*");
    JButton divi = new JButton("/");
    JButton ok = new JButton("OK");

    public Calculator(){
        symbol.setEditable(false);
        equal.setText("=");
        equal.setEditable(false);
        result.setEditable(false);
        pre.setSize(60,60);

        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(2,5));
        pan.add(pre);
        pan.add(symbol);
        pan.add(back);
        pan.add(equal);
        pan.add(result);
        pan.add(plus);
        pan.add(sub);
        pan.add(mul);
        pan.add(divi);
        pan.add(ok);
        pan.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        frame.setLocation(1000,500);
        frame.setSize(1000,500);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(pan,BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        class Listener implements ActionListener{
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e){
                String s = ((JButton)e.getSource()).getText();
                if(s!="OK")
                    symbol.setText(s);
                else{
                    if (pre.getText() == "" || back.getText() == "")
                        return;
                    double a=Double.parseDouble(pre.getText());
                    double b=Double.parseDouble(back.getText());
                    s = symbol.getText();
                    if(s.equals("+")){
                        result.setText(String.valueOf(a + b));
                    }
                    else if(s.equals("-"))
                        result.setText(String.valueOf(a - b));
                    else if(s.equals("*"))
                        result.setText(String.valueOf(a * b));
                    else if(s.equals("/"))
                        result.setText(String.valueOf(a / b));
                }
            }
        }

        Listener listen = new Listener();
        plus.addActionListener(listen);
        sub.addActionListener(listen);
        mul.addActionListener(listen);
        divi.addActionListener(listen);
        ok.addActionListener(listen);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String... args){
        new Calculator();
    }
}

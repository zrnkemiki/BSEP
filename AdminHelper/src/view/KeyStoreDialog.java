package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyStoreDialog extends JDialog{

	public KeyStoreDialog(JFrame parent, String title) {
		super(parent, title);
        setLocation(400, 400);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel textFieldPane = new JPanel(new FlowLayout());
    
        JLabel label = new JLabel("Enter cert file: ");
        textFieldPane.add(label);
        
        JTextField jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(200, 50));
        textFieldPane.add(jtf);
        
        getContentPane().add(textFieldPane);
        
        JPanel tfp2 = new JPanel(new FlowLayout());
        
        JLabel label2 = new JLabel("Enter private key file: ");
        tfp2.add(label2);
        
        JTextField jtf2 = new JTextField();
        jtf2.setPreferredSize(new Dimension(200, 50));
        tfp2.add(jtf2);
        
        getContentPane().add(tfp2);
        
        JPanel tfp3 = new JPanel(new FlowLayout());
        
        JLabel label3 = new JLabel("Enter key store password: ");
        tfp3.add(label3);
        
        JTextField jtf3 = new JTextField();
        jtf3.setPreferredSize(new Dimension(200, 50));
        tfp3.add(jtf3);
        
        getContentPane().add(tfp3);
        
        JPanel tfp4 = new JPanel(new FlowLayout());
        
        JLabel label4 = new JLabel("Enter private key password: ");
        tfp4.add(label4);
        
        JTextField jtf4 = new JTextField();
        jtf4.setPreferredSize(new Dimension(200, 50));
        tfp4.add(jtf4);
        
        getContentPane().add(tfp4);
        
        JPanel tfp5 = new JPanel(new FlowLayout());
        
        JLabel label5 = new JLabel("Enter alias: ");
        tfp5.add(label5);
        
        JTextField jtf5 = new JTextField();
        jtf5.setPreferredSize(new Dimension(200, 50));
        tfp5.add(jtf5);
        
        getContentPane().add(tfp5);
        
        JPanel tfp6 = new JPanel(new FlowLayout());
        
        JLabel label6 = new JLabel("Enter key store file: ");
        tfp6.add(label6);
        
        JTextField jtf6 = new JTextField();
        jtf6.setPreferredSize(new Dimension(200, 50));
        tfp6.add(jtf6);
        
        getContentPane().add(tfp6);
        
        
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("Generate");
        buttonPane.add(button);
        // set action listener on the button
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyFrame.getInstance().generateKeyStore(
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\certs\\" + jtf.getText(),
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\csrs\\" + jtf2.getText(),
						jtf3.getText(),
						jtf4.getText(),
						jtf5.getText(),
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\keystores\\" + jtf6.getText()
						);
				
			}
		});
        getContentPane().add(buttonPane);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
	}
	
}

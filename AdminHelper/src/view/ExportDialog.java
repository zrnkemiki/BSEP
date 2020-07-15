package view;

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

public class ExportDialog extends JDialog{

	public ExportDialog(JFrame parent, String title) {
		super(parent, title);
        setLocation(400, 400);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel textFieldPane = new JPanel(new FlowLayout());
    
        JLabel label = new JLabel("Enter path: ");
        textFieldPane.add(label);
        
        JTextField jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(200, 50));
        textFieldPane.add(jtf);
        
        getContentPane().add(textFieldPane);
        
        JPanel tfp2 = new JPanel(new FlowLayout());
        
        JLabel label2 = new JLabel("Enter cert file path: ");
        tfp2.add(label2);
        
        JTextField jtf2 = new JTextField();
        jtf2.setPreferredSize(new Dimension(200, 50));
        tfp2.add(jtf2);
        
        getContentPane().add(tfp2);
        
        
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("Generate");
        buttonPane.add(button);
        // set action listener on the button
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyFrame.getInstance().exportCert(
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\certs\\" + jtf.getText(),
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\certs\\" + jtf2.getText());
				
			}
		});
        getContentPane().add(buttonPane);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
	}
	
	
}

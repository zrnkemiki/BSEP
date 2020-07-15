package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CSRDialog extends JDialog {
	
	public CSRDialog(JFrame parent, String title) {
		super(parent, title);
        setLocation(400, 400);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        
        
        JPanel textFieldPane = new JPanel(new FlowLayout());
        
        JLabel label = new JLabel("Enter path: ");
        textFieldPane.add(label);
        
        JTextField jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(200, 50));
        textFieldPane.add(jtf);
        
        getContentPane().add(textFieldPane, BorderLayout.NORTH);
        
        
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("Generate");
        buttonPane.add(button);
        // set action listener on the button
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyFrame.getInstance().generateCsrSiemRequest(
						"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\AdminHelper\\recources\\csrs\\" + jtf.getText());
				
			}
		});
        getContentPane().add(buttonPane, BorderLayout.PAGE_END);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
	}
	

}

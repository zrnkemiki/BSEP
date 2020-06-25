package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * klasa nasleđuje JMenuBar i predstavlja kontejner
 * za komponente JMenu. Instanca ove klase će se 
 * smeštati u glavni prozor aplikacije
 * 
 */
public class MyMenuBar extends JMenuBar{
	
	public MyMenuBar (){
		
	    JMenu file=new JMenu("File");
	    JMenu generate =new JMenu("Generate");
	    JMenuItem export =new JMenuItem("Export cert for agent in .pem");
	    export.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          ExportDialog dialog = new ExportDialog(MyFrame.getInstance(), "Export cert");
	          dialog.setVisible(true);
	        }
	      });
		
	    JMenuItem csrItem=new JMenuItem("CSR SIEM agent request");
	    csrItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          CSRDialog dialog = new CSRDialog(MyFrame.getInstance(), "CSR request");
	          dialog.setVisible(true);
	        }
	      });
	    
	    JMenuItem csr2Item=new JMenuItem("CSR SIEM center request");
	    csr2Item.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          CSRAgentDialog dialog = new CSRAgentDialog(MyFrame.getInstance(), "CSR request");
	          dialog.setVisible(true);
	        }
	      });
	    
		JMenuItem keyStoreItem=new JMenuItem("KeyStore");
		keyStoreItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          KeyStoreDialog dialog = new KeyStoreDialog(MyFrame.getInstance(), "Key store ");
	          dialog.setVisible(true);
	        }
	      });
		
		JMenuItem trustStoreItem=new JMenuItem("TrustStore");
		trustStoreItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          TrustDialog dialog = new TrustDialog(MyFrame.getInstance(), "Trust store ");
	          dialog.setVisible(true);
	        }
	      });

		generate.add(csrItem);
		generate.addSeparator();
		generate.add(csr2Item);
		generate.addSeparator();
		generate.add(keyStoreItem);
		generate.addSeparator();
		generate.add(trustStoreItem);
		
		file.add(generate);
		file.addSeparator();
		file.add(export);

		add(file);
	}

}

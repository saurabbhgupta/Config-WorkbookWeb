package ConfigWorkbookWeb;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class SalesforceLoginForm extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	// Declaring labels,buttons,font and popupmenu
	final JTextField username, endpoint;
	final JTextField password;
	final JLabel usernameLabel, endpointLabel;
	final JLabel passwordLabel;
	JLabel mandatoryLabel;
	JButton login, reset;
	Font font;
	JPopupMenu progressPop;

	Properties prop = ConfigurationProperties.getPropValues();

	public SalesforceLoginForm() {
		super("LOGIN page");

		Container content = getContentPane();

		content.setLayout(null);

		setSize(new Dimension(1060, 668));
		setLocationRelativeTo(null);

		font = new Font("Dialog", Font.PLAIN, 20);
		// Adding labels on screen
		usernameLabel = new JLabel("User Name:  ", Label.RIGHT);
		passwordLabel = new JLabel("Password:  ", Label.RIGHT);
		endpointLabel = new JLabel("END POINT URL:  ", Label.RIGHT);
		usernameLabel.setBounds(150, 125, 150, 100);
		passwordLabel.setBounds(150, 166, 150, 100);
		endpointLabel.setBounds(150, 215, 200, 100);
		// setting the  font
		usernameLabel.setFont(font);
		passwordLabel.setFont(font);
		endpointLabel.setFont(font);
		// Adding buttons,textfields and passwordfield on screen
		login = new JButton("Login");
		reset = new JButton("Reset");
		username = new JTextField(30);
		password = new JPasswordField(30);
		endpoint = new JTextField(30);
		username.setBounds(350, 160, 350, 30);
		password.setBounds(350, 205, 350, 30);
		endpoint.setBounds(350, 250, 350, 30);
		username.setText(prop.getProperty(ConfigBookMain.username));
		password.setText(prop.getProperty(ConfigBookMain.password));
		endpoint.setText(prop.getProperty(ConfigBookMain.endpoint));
		add(usernameLabel);
		add(passwordLabel);
		add(username);
		add(password);
		add(endpointLabel);
		add(endpoint);
		login.addActionListener(this);
		add(login);
		reset.addActionListener(this);
		add(reset);
		login.setBounds(270, 350, 100, 30);
		reset.setBounds(400, 350, 100, 30);

	}

	// Adding functionality to buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String res = e.getActionCommand();
		if (username.getText().equals("") || password.getText().equals("")
				|| endpoint.getText().equals("")) {
			mandatoryLabel = new JLabel(
					"Please enter Username,Password and Endpoint Url",
					Label.RIGHT);
			add(mandatoryLabel);
			mandatoryLabel.setBounds(400, 280, 280, 80);
			return;
		}
		if (res.equals("Login")) {

			

			// Create and set up the content pane.
			JComponent newContentPane = new ProgressBar(username.getText(),
					password.getText(), endpoint.getText());

			setSize(new Dimension(300, 300));
			setLocationRelativeTo(null);

			newContentPane.setOpaque(true); // content panes must be opaque
			this.setContentPane(newContentPane);

			// Display the window.
			this.pack();
			this.setVisible(true);

		} else if (res.equals("Reset")) {
			username.setText("");
			password.setText("");
			endpoint.setText("");
		}
	}

}

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatLogin {

	private JFrame frmChatLogin;
	private JTextField userNameText;
	private JTextField IPText;
	private JTextField portText;
	private String user;
	private String IPAddress;
	private int port;
	
	public ChatLogin() {
		initialize();
	}

	
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		frmChatLogin = new JFrame();
		frmChatLogin.setTitle("Chat - Login");
		frmChatLogin.setBounds(100, 100, 380, 281);
		frmChatLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatLogin.getContentPane().setLayout(null);
		frmChatLogin.setLocationRelativeTo(null);
		frmChatLogin.setVisible(true);
		
		JLabel userName = new JLabel("User name:");
		userName.setBounds(71, 58, 68, 24);
		frmChatLogin.getContentPane().add(userName);
		
		JLabel pw = new JLabel("IP address:");
		pw.setBounds(71, 93, 85, 24);
		frmChatLogin.getContentPane().add(pw);
		
		userNameText = new JTextField();
		userNameText.setBounds(147, 61, 118, 22);
		frmChatLogin.getContentPane().add(userNameText);
		userNameText.setColumns(10);
		
		IPText = new JTextField();
		IPText.setBounds(147, 94, 118, 22);
		frmChatLogin.getContentPane().add(IPText);
		IPText.setColumns(10);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = userNameText.getText();
				IPAddress = IPText.getText();
				port = Integer.parseInt(portText.getText());
				login(user,IPAddress,port);
				
			}			
		});
		loginBtn.setBounds(129, 180, 118, 40);
		frmChatLogin.getContentPane().add(loginBtn);
		
		portText = new JTextField();
		portText.setBounds(147, 127, 118, 24);
		frmChatLogin.getContentPane().add(portText);
		portText.setColumns(10);
		
		JLabel port = new JLabel("Port:");
		port.setBounds(92, 128, 46, 23);
		frmChatLogin.getContentPane().add(port);
		
	
		
	}
	
	private void login(String user,String IP,int port)
	{
		frmChatLogin.dispose();
		new ChatMain(user,IP,port);
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatLogin window = new ChatLogin();
					window.frmChatLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

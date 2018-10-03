import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JList;

public class ChatMain implements Runnable{

	private JFrame frame;
	private String user;
	private String IP;
	public int port;
	private JTextField textArea;
	public JTextArea msgWindow;
	private Thread run, showMessage;
	private boolean running = false;
	private Chat c;
	@SuppressWarnings("rawtypes")
	private JList list;

	
	public ChatMain(String user,String IP,int port) {
		this.user = user;
		this.IP = IP;
		this.port = port;
		c = new Chat(IP,port);
		boolean confirm = c.OpenConnection(IP);
		initialize();
		if(confirm)
		{
			c.console("Successfully connected to IP address: " + IP,msgWindow);
		}
		String str = "/c/" + user + "/e/";
		c.send(str.getBytes());
		running = true;
		run = new Thread(this,"Running");
		run.start();
	}

	

	@SuppressWarnings("rawtypes")
	private void initialize() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 428);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1, 111, 132, 30, 89, 50, 111, 0};
		gridBagLayout.rowHeights = new int[]{1, 15, 158, 35, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "/d/" + user + "/e/";
				c.send(str.getBytes());
				System.exit(0);
			}
		});
		
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textArea.getText(); 
				send(message,true);
			}
		});
		
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setText("User: " + user);
		
		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridheight = 2;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 0;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.setText("IP address: " + IP);
		
		JLabel lblNewLabel_2 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 0;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		lblNewLabel_2.setText("Port: " + String.valueOf(port));
		
		textArea = new JTextField();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String message =textArea.getText();
					send(message,true);
					
				}
			}
		});
		
		JLabel friends = new JLabel("Other users:");
		friends.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_friends = new GridBagConstraints();
		gbc_friends.fill = GridBagConstraints.BOTH;
		gbc_friends.insets = new Insets(0, 0, 5, 0);
		gbc_friends.gridx = 6;
		gbc_friends.gridy = 1;
		frame.getContentPane().add(friends, gbc_friends);
		
		msgWindow = new JTextArea();
		msgWindow.setEditable(false);
		JScrollPane scroll = new JScrollPane(msgWindow,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_msgWindow = new GridBagConstraints();
		gbc_msgWindow.gridwidth = 4;
		gbc_msgWindow.insets = new Insets(0, 0, 5, 5);
		gbc_msgWindow.fill = GridBagConstraints.BOTH;
		gbc_msgWindow.gridx = 1;
		gbc_msgWindow.gridy = 2;
		frame.getContentPane().add(scroll, gbc_msgWindow);
		
		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 6;
		gbc_list.gridy = 2;
		frame.getContentPane().add(list, gbc_list);
		
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridwidth = 4;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 3;
		frame.getContentPane().add(textArea, gbc_textArea);
		textArea.setColumns(10);
		textArea.requestFocusInWindow();
		GridBagConstraints gbc_send = new GridBagConstraints();
		gbc_send.anchor = GridBagConstraints.WEST;
		gbc_send.fill = GridBagConstraints.VERTICAL;
		gbc_send.insets = new Insets(0, 0, 5, 0);
		gbc_send.gridwidth = 2;
		gbc_send.gridx = 5;
		gbc_send.gridy = 3;
		frame.getContentPane().add(send, gbc_send);
		
		JButton logOff = new JButton("Logout");
		logOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				String str = "/l/" + user + "/e/";
				c.send(str.getBytes());
				new ChatLogin();
			}
		});
		GridBagConstraints gbc_logOff = new GridBagConstraints();
		gbc_logOff.anchor = GridBagConstraints.NORTH;
		gbc_logOff.insets = new Insets(0, 0, 0, 5);
		gbc_logOff.gridx = 2;
		gbc_logOff.gridy = 4;
		frame.getContentPane().add(logOff, gbc_logOff);
		GridBagConstraints gbc_exit = new GridBagConstraints();
		gbc_exit.anchor = GridBagConstraints.NORTH;
		gbc_exit.fill = GridBagConstraints.HORIZONTAL;
		gbc_exit.insets = new Insets(0, 0, 0, 5);
		gbc_exit.gridx = 4;
		gbc_exit.gridy = 4;
		frame.getContentPane().add(exit, gbc_exit);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				String dis = "/d/" + user + "/e/";
				send(dis,false);
				
				c.closeConnection();
			}
	});
		
	}
	
	
	private void send(String message,boolean confirm)
	{	if(message.equals("")) return;
	if(confirm) {
		message = user + ":" + message;
		 message = "/m/" + message + "/e/";}
		c.send(message.getBytes());
		textArea.setText("");
	}
	
	public void showMessage()
	{
	 showMessage = new Thread("message")
		{
			@SuppressWarnings("unchecked")
			public void run()
			{
				while(running) 
				{
					String text = c.receive();
					if(text.startsWith("/m/"))
					{
						c.console(text.split("/m/|/e/")[1],msgWindow);
					}
					else if(text.startsWith("/d/"))
					{
						
						c.console(text.split("/d/|/e")[1] + " disconnected from the chat ",msgWindow);
					}
					else if(text.startsWith("/l/"))
					{
						
						c.console(text.split("/l/|/e")[1] + " logged out from the chat",msgWindow);
					}
					else if(text.startsWith("/u/"))
					{
						String[] users = text.split("/u/|/n/|/e/");
						
						list.setListData(Arrays.copyOfRange(users, 1, users.length -1));
 					}
					
					
				}
			}
		};
		showMessage.start();
	}
	 public void run()
	 {
		 showMessage();
	 }
}

import javax.swing.JTextArea;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Chat{
	private DatagramSocket s;
	private InetAddress ip;
	private Thread send;

	private String adr;
	private int port;

	
	
	public Chat(String adr,int port)
	{
		this.adr = adr;
		this.port=port;
	}
	
	public boolean OpenConnection(String Address)
	{
		try {
			s = new DatagramSocket(port);
			ip = InetAddress.getByName(Address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public void send(final byte[] data)
	{
		send = new Thread("Send") {
		public void run()
		{
			try {
				DatagramPacket packet = new DatagramPacket(data,data.length,InetAddress.getByName(adr),port);
				s.send(packet);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			 catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
		send.start();
	} 
	
	public String receive()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		
		try {
			s.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}
	
	
	
	public void console(String message,JTextArea msgWindow)
	{
		try {
		msgWindow.append(message + "\n\r");}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeConnection()
	{
		new Thread() {
			public void run() {
		synchronized(s)
		{
			s.close();
		}}
		}.start();
	}

}




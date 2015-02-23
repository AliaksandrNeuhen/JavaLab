package com.epam.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public void start() throws IOException{
		String addressString = "127.0.0.1";
		Integer port = 1710;
		InetAddress ipAddress = null;
		try {
			ipAddress = InetAddress.getByName(addressString);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Socket socket = null;
			socket = new Socket(ipAddress, port);
			Thread listener = new Thread(new SocketInputListener(socket));
			Thread sender = new Thread(new SocketMessageSender(socket));
			
//			listener.setDaemon(true);
//			sender.setDaemon(true);
			
			listener.start();
			sender.start();
	}
	
	public static void main(String [] args) {
		Client client = new Client();
		try {
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

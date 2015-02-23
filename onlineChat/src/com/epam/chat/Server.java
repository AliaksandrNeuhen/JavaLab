package com.epam.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public void start() throws IOException{
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(1710);
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress()+ ":" +
			 socket.getPort() + " connected.");
			Thread listener = new Thread(new SocketInputListener(socket));
			Thread sender = new Thread(new SocketMessageSender(socket));
//			
//			listener.setDaemon(true);
//			sender.setDaemon(true);
			
			listener.start();
			sender.start();
		} finally {
			serverSocket.close();
		}
	}
	
	public static void main(String [] args) {
//		System.out.println("Enter your nickname: ");
//		Scanner scanner = new Scanner(System.in);
//		String nickname = scanner.nextLine();
//		if (nickname.length() > 16 || nickname.length() < 4) {
//			System.out.println("Incorrect nickname length. It should be < 16 and > 4");
//		}
		Server server = new Server();
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

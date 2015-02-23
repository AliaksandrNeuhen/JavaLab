package com.epam.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketMessageSender implements Runnable{
	private Socket socket;
	
	public SocketMessageSender(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
		Scanner scanner = new Scanner(System.in);
		final DataOutputStream sout = new DataOutputStream(out);
		while (true) {
			String outcomingString = scanner.nextLine();
			try {
				sout.writeUTF(outcomingString);
				sout.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
}

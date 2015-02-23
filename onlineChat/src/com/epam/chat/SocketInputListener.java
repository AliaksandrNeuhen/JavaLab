package com.epam.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

public class SocketInputListener implements Runnable {
	private Socket socket;

	public SocketInputListener(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InputStream in = null;
		try {
			in = socket.getInputStream();
			final DataInputStream sin = new DataInputStream(in);
			while (true) {
				String incomingMessage = "";
				try {
					incomingMessage = sin.readUTF();
				} catch (){ //Catch exception when closing socket
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(incomingMessage);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
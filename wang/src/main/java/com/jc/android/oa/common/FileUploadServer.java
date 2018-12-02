package com.jc.android.oa.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUploadServer {

	ServerSocket serverSocket;

	public FileUploadServer() {
		try {
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startSave() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Socket socket = null;
				OutputStream m = null;
				try {
					socket = serverSocket.accept();
					InputStream in = socket.getInputStream();
					byte[] b = new byte[in.available()];
					in.read(b);
					FileOutputStream out = new FileOutputStream("d:\\fff.ss");
					out.write(b);
					m = socket.getOutputStream();
					m.write(1);
					in.close();
					out.close();
				} catch (Exception e) {
					try {
						m.write(2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}

			}
		}).start();
	}

}

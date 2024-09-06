package thread;
import java.io.*;
import java.net.*;

import info.*;
import db.*;

public class ThreadManager {
	DBManager dbM = null;
	ClientThread[] tList = null;
	ServerSocket server = null;
	int index = 0;
	Thread acceptThread = null;
	public ThreadManager(int n, DBManager dbM, int port) throws BindException{
		this.dbM = dbM;
		tList = new ClientThread[n];
		try {
			server = new ServerSocket(port);
		} catch(BindException e) {
			throw new BindException();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("서버 소켓 생성 중 에러");
		}
		acceptThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ClientThread add = null;
				while(true) {
					try {
						System.out.println("클라이언트 받는 중...");
						add = new ClientThread(server, server.accept(), dbM);
						tList[index] = add;
						tList[index].start();
						index++;
						System.out.println("연결 성공!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.err.println("accept 쓰레드 생성 중 에러");
						return;
					}
				}
			}
		});
		acceptThread.start();
	}
}

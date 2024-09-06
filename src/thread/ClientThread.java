package thread;

import java.io.*;
import java.net.*;

import db.DBManager;
import info.Info;

public class ClientThread extends Thread{
	DBManager dbM = null;
	DatagramSocket udpSocket = null;
	DatagramPacket udpPacket = null;
	int port = 0;
	Socket client = null;
	BufferedReader in = null;
	PrintWriter out = null;
	InetAddress addr = null;
	boolean login = false;
	boolean unname = false;
	int uid = 0;
	ClientThread(ServerSocket server, Socket socket, DBManager dbM){
		this.dbM = dbM;
		try {
			udpSocket = new DatagramSocket();
			addr = socket.getInetAddress();
			port = socket.getPort();
			client = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("클라이언트 쓰레드 생성 중 에러");
		}			
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(1);
				String receive = null;
				String send = null;
				byte[] buf = new byte[256];
				try {
					receive = in.readLine();
					System.out.println("RECEIVE : " + receive);
					send = process(receive);
					out.println(send);
					System.out.println("SENT : " + send);
	
					udpPacket = new DatagramPacket(buf, buf.length, addr, port);
					udpSocket.send(udpPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					disconnected();
					System.out.println("클라이언트 통신 종료");
					try {
						client.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.err.println("클라이언트 소켓 닫는 중 에러");
					}
					return;
				}
			}
		}catch(InterruptedException e) {
		
		}
	}
	
	public int[] parseTcpPacket(String tcpPacket) {
		int[] ret = new int[tcpPacket.split(" ").length];
		for(int i = 0; i < tcpPacket.split(" ").length; i++) {
			ret[i] = Integer.parseInt(tcpPacket.split(" ")[i]);
		}
		return ret;
	}
	
	public void disconnected() {
		login = false;
		int rid = dbM.getRoomInfo(uid);
		if(rid != 0) {
			if(dbM.getRoomMemberCount(rid) == 1) {
				dbM.deleteRoom(rid);
			}
		}
		dbM.logout(uid);
		dbM.clearUserInfoAboutRoom(uid);
		if(unname == true) {
			dbM.deleteUser(uid);
		}
		unname = false;
		uid = 0;
	}
	
	public String process(String receivePacket) throws IOException{
		int[] parse = parseTcpPacket(receivePacket);
		String sendPacket = null;
		switch(parse[0]) {
		case Info.SIGNUP:
			sendPacket = processSignUp(parse);
			break;
		case Info.LOGIN : 
			sendPacket = processLogin(parse);
			break;
		case Info.UNNAME_LOGIN :
			sendPacket = processUnnameLogin(parse);
			break;
		case Info.REQUEST_CREATE_ROOM : 
			sendPacket = processCreateRoom(parse);
			break;
		case Info.SEND_MESSAGE :
			sendPacket = processCreateMessage(parse);
			break;
		case Info.EXIT_ROOM :
			sendPacket = processExitRoom(parse);
			break;
		case Info.LOGOUT:
			sendPacket = processLogout(parse);
			break;
		case Info.ENTER_ROOM:
			sendPacket = processEnter(parse);
			break;
		case Info.ADD_FRIEND:
			sendPacket = processAddFriend(parse);
			break;
		case Info.DELETE_FRIEND:
			sendPacket = processDeleteFriend(parse);
			break;
		case Info.EXIT :
			throw new IOException();
		}
		return sendPacket;
	}
	
	public String processEnter(int[] parse) {
		String sendPacket = null;
		//sendPacket = "" + Info.SERVER_ENTER_ROOM_SUCCESS;
		if(dbM.enterRoom(uid, parse[2])) {
			sendPacket = "" + Info.SERVER_ENTER_ROOM_SUCCESS;
		}else {
			sendPacket = "" + Info.SERVER_ENTER_ROOM_FAILED;
		}
		return sendPacket;
	}
	
	public String processLogin(int[] parse) {
		String sendPacket = null;
		sendPacket = "" + Info.SERVER_LOGIN_RECEIVE;
		login = true;
		unname = false;
		uid = parse[1];
		dbM.logon(uid);
		dbM.clearUserInfoAboutRoom(uid);
		return sendPacket;
	}
	
	public String processUnnameLogin(int[] parse) {
		String sendPacket = null;
		sendPacket = "" + Info.SERVER_LOGIN_RECEIVE;
		login = true;
		unname = true;
		uid = parse[1];
		return sendPacket;
	}
	
	public String processCreateRoom(int[] parse) {
		String sendPacket = null;
		if(dbM.makeRoom(uid, parse[2])) {
			sendPacket = "" + Info.SERVER_CREATE_ROOM_SUCCESS;
		}
		else{
			sendPacket = "" + Info.SERVER_CREATE_ROOM_FAILED;
		}
		return sendPacket;
	}
	
	public String processCreateMessage(int[] parse) {
		String sendPacket = null;
		if(dbM.makeMessage(uid, parse[2])) {
			sendPacket = "" + Info.SERVER_CREATE_MESSAGE_SUCCESS;
		}
		else{
			sendPacket = "" + Info.SERVER_CREATE_MESSAGE_FAILED;
		}
		return sendPacket;
	}
	
	public String processLogout(int[] parse) {
		String sendPacket = null;
		sendPacket = "" + Info.SERVER_LOGOUT_RECEIVE;
		login = false;
		dbM.logout(uid);
		if(unname == true) {
			dbM.deleteUser(uid);
		}
		unname = false;
		dbM.clearUserInfoAboutRoom(uid);
		uid = 0;
		return sendPacket;
	}
	
	// 아직 할거 있다?
	public String processExitRoom(int[] parse) {
		String sendPacket = null;
		sendPacket = "" + Info.SERVER_EXIT_ROOM_SUCESS;
		dbM.clearUserInfoAboutRoom(uid);
		if(dbM.getRoomMemberCount(parse[2]) == 0) {
			dbM.deleteRoom(parse[2]);
		}
		// 이제 방에 새로 남아있는 인원에게 방장 양도하고 사람 없으면 방 폭파
		return sendPacket;
	}
	
	public String processSignUp(int[] parse) {
		String sendPacket = null;
		sendPacket = "" + Info.SERVER_REGISTER;
		return sendPacket;
	}
	
	public String processAddFriend(int[] parse) {
		String sendPacket = null;
		if(dbM.addFriend(parse[1], parse[2])) {
			sendPacket = "" + Info.SERVER_ADD_FRIEND_SUCCESS;
		}
		else {
			sendPacket = "" + Info.SERVER_ADD_FRIEND_FAILED;
		}
		return sendPacket;
	}
	public String processDeleteFriend(int[] parse) {
		String sendPacket = null;
		if(dbM.deleteFriend(parse[1], parse[2])) {
			sendPacket = "" + Info.SERVER_DELETE_FRIEND_SUCCESS;
		}
		else {
			sendPacket = "" + Info.SERVER_DELETE_FRIEND_FAILED;
		}
		return sendPacket;
	}
}

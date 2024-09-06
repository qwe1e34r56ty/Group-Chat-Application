package db;

import java.sql.*;
import info.*;

import info.*;

public class DBManager {
	Connection conn = null;
	public DBManager(){
		try {
			Class.forName(Info.driverName);
			System.out.println("드라이버 연결 성공!");
			conn = DriverManager.getConnection(Info.url, Info.id, Info.pwd);
			System.out.println("DB 연결 성공!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("드라이버를 찾을 수 없습니다");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("DB 오류");
		}
	}
	public void clear() {
		clearMessage();
		clearRoom();
	}
	public void clearMessage() {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from message_db.message_table;";
			stmt.executeQuery(query);
			query = "delete from message_request_db.message_request_table;";
			stmt.executeQuery(query);
			System.out.println("메시지 db 초기화 성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("메시지 db 초기화 실패");
		}
	}
	public void clearRoom() {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from room_db.room_table;";
			stmt.executeQuery(query);
			query = "delete from request_db.request_table;";
			stmt.executeQuery(query);
			System.out.println("방 db 초기화 성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			e.printStackTrace();
			System.err.println("방 db 초기화 실패");
		}
	}
	public boolean makeRoom(int uid, int rid) {
		try {
			Statement stmt = conn.createStatement();
			String[] query = makeRoomQuery(uid, rid);
			for(int i = 0; i < query.length; i++) {
				stmt.executeQuery(query[i]);
			}
			System.out.println("방 만들기 성공");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("방 만들기 실패");
		}
		return false;
	}
	
	public String[] makeRoomQuery(int uid, int rid) {
		String[] ret = new String[3];
		ret[0] = "insert\n" +
			"into room_db.room_table (rid, roomName, maxUser, opt)\n" +
			"select id, roomName, maxUser, opt\n" +
			"from request_db.request_table\n" +
			"where id = " + rid + ";\n";
		ret[1] = "update user.user_info\n" +
			"set roomid = (select id from room_db.room_table where rid = " + rid + "),\n" +
			"ownerRoomid = (select id from room_db.room_table where rid = " + rid + ")\n" +
			"where id = " + uid + ";\n";
		ret[2] = "delete from request_db.request_table where id = " + rid + ";\n";
		return ret;
	}
	
	public boolean enterRoom(int uid, int rid) {
		try {
			Statement stmt = conn.createStatement();
			if(this.getMaxRoomMember(rid) <= this.getRoomMemberCount(rid)){
				return false;
			}
			if(this.getRoomOpt(rid) != 1) {
				return false;
			}
			String query =  "update user.user_info\n" +
					"set roomid = (select id from room_db.room_table where id = " + rid + ")\n" +
					"where id = " + uid + ";\n";
			stmt.executeQuery(query);
			System.out.println("방 입장 성공");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("방 입장 실패");
		}
		return false;
	}
	
	public boolean makeMessage(int uid, int mid) {
		try {
			Statement stmt = conn.createStatement();
			String[] query = makeMessageQuery(uid, mid);
			for(int i = 0; i < query.length; i++) {
				stmt.executeQuery(query[i]);
			}
			System.out.println("메세지 만들기 성공");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("메세지 만들기 실패");
		}
		return false;
	}
	public String[] makeMessageQuery(int uid, int mid) {
		String[] ret = new String[2];
		ret[0] = "insert\n" +
			"into message_db.message_table (mid, msg, uid, rid)\n" +
			"select id, msg, uid, rid\n" +
			"from message_request_db.message_request_table\n" +
			"where id = " + mid + ";\n";
		ret[1] = "delete from message_request_db.message_request_table where id = " + mid + ";\n";
		return ret;
	}
	
	public void clearUserInfoAboutRoom(int uid) {
		if(uid == 0) {
			return;
		}
		try {
			Statement stmt = conn.createStatement();
			String query = "update user.user_info\n" +
					"set roomid = null, ownerRoomid = null\n" +
					"where id = " + uid + ";\n";
			stmt.executeQuery(query);
			System.out.println("유저의 방 관련 정보 초기화 성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("유저의 방 관련 정보 초기화 실패\n");
		}
	}
	public void deleteUser(int uid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from user.user_info where id = " + uid + ";\n";
			stmt.executeQuery(query);
			System.out.println("유저 정보 삭제 완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("유저 정보 삭제 실패");
		}
	}
	public void deleteRoom(int rid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "delete from room_db.room_table where id = " + rid + ";\n";
			stmt.executeQuery(query);
			System.out.println("방 정보 삭제 완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
			System.err.println("방 정보 삭제 실패");
		}
	}
	public int getRoomMemberCount(int rid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "select count(id) as c from user.user_info where roomid = " + rid + ";\n";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getInt("c");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return 0;
	}
	
	public int getMaxRoomMember(int rid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "select maxUser from room_db.room_table where id = " + rid + ";\n";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getInt("maxUser");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return 0;
	}
	
	public int getRoomOpt(int rid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "select opt from room_db.room_table where id = " + rid + ";\n";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getInt("opt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return 0;
	}
	
	public boolean addFriend(int uid, int fid) {
		try {
			if(uid == fid) {
				return false;
			}
			Statement stmt = conn.createStatement();
			String query = "insert into friend_db.friend_table\n" +
					"(id, fid)values(" + uid + ", " + fid + ");";
			stmt.executeQuery(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return false;
	}
	public boolean deleteFriend(int uid, int fid) {
		try {
			if(uid == fid) {
				return false;
			}
			Statement stmt = conn.createStatement();
			String query = "select * from friend_db.friend_table\n" +
					"where id = " + uid + " and fid = " + fid + ";";
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.next()) {
				return false;
			}
			query = "delete from friend_db.friend_table\n" +
					"where id = " + uid + " and fid = " + fid + ";";
			stmt.executeQuery(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return false;
	}
	public void logon(int uid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update user.user_info\n" +
					"set logon = " + true + "\n" +
					"where id = " + uid + ";";
			stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
	}
	public void logout(int uid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "update user.user_info\n" +
					"set logon = " + false + "\n" +
					"where id = " + uid + ";";
			stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
	}
	public int getRoomInfo(int uid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "select roomid from user.user_info where id = " + uid + ";\n";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getInt("roomid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block\
			//e.printStackTrace();
		}
		return 0;
	}
}

package info;

public class Info {
	public static final String url = "jdbc:mariadb://localhost:3308/";
	public static final String driverName = "org.mariadb.jdbc.Driver";
	public static final String id = "root";
	public static final String pwd = "1234";
	public static final int myPort = 5582;
	
	
	public static final int SIGNUP = 100;	// 회원가입
	public static final int LOGIN = 120;	// 로그인
	public static final int UNNAME_LOGIN = 130;	// 익명 로그인
	public static final int CREATE_ROOM = 200;	// 방만들기
	public static final int REQUEST_CREATE_ROOM = 210;	// 방만들기 요청
	public static final int ENTER_ROOM = 300;	// 방입장
	public static final int ENTER_ROOM_OK = 301;	// 방입장 성공
	public static final int ENTER_ROOM_NO = 302;	// 방입장 실패
	public static final int EXIT_ROOM = 310;	// 방나가기
	public static final int SEND_MESSAGE = 400;	// 메세지 송신
	public static final int RECIEVE_MESSAGE = 410;	// 메세지 수신
	public static final int LOGOUT = 500; // 로그아웃
	public static final int EXIT = 510; // 종료
	public static final int FRIEND = 600;	// 친구
	public static final int ADD_FRIEND = 601;	// 친구 추가
	public static final int DELETE_FRIEND = 602;	// 친구 삭제
	public static final int INVATE_FRIEND = 610;	// 친구 초대
	public static final int ENTRY_FRIEND = 620;	// 친구 방에 들어가기
	
	public static final int SERVER_REGISTER = 1100;
	public static final int SERVER_LOGIN_RECEIVE = 1121;	// 서버에서 로그인 받았다고 응답
	public static final int SERVER_LOGOUT_RECEIVE = 1122;	// 서버에서 로그아웃 확인했다고 응답
	public static final int SERVER_EXIT = 1200;	// 서버에서 종료 확인했다고 응답
	public static final int SERVER_CREATE_ROOM_SUCCESS = 2000;	// 서버에서 방 만들었다고 응답
	public static final int SERVER_CREATE_ROOM_FAILED = 2001;	// 방 못만들었다고 응답
	public static final int SERVER_EXIT_ROOM_SUCESS = 2310;	// 방 내보냈다고 응답
	public static final int SERVER_CREATE_MESSAGE_SUCCESS = 2400;	// 서버에서 메세지 만들었다고 응답
	public static final int SERVER_CREATE_MESSAGE_FAILED = 2401;	// 메세지 못만들었다고 응답
	public static final int SERVER_ENTER_ROOM_SUCCESS = 3001;		// 서버에서 방 입장시켰다고 응답
	public static final int SERVER_ENTER_ROOM_FAILED = 3002;		// 서버에서 방 입장시키기 실패했다고 응답
	public static final int SERVER_ADD_FRIEND_SUCCESS = 6001;		// 서버에서 친구 추가 성공했다고 응답
	public static final int SERVER_ADD_FRIEND_FAILED = 6011;		// 서버에서 친구 추가 실패했다고 응답
	public static final int SERVER_DELETE_FRIEND_SUCCESS = 6002;		// 서버에서 친구 삭제 성공했다고 응답
	public static final int SERVER_DELETE_FRIEND_FAILED = 6012;		// 서버에서 친구 삭제 실패했다고 응답
	
	private Info() {
		
	}
}

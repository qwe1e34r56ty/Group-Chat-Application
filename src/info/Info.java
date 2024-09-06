package info;

public class Info {
	public static final String url = "jdbc:mariadb://localhost:3308/";
	public static final String driverName = "org.mariadb.jdbc.Driver";
	public static final String id = "root";
	public static final String pwd = "1234";
	public static final int myPort = 5582;
	
	
	public static final int SIGNUP = 100;	// ȸ������
	public static final int LOGIN = 120;	// �α���
	public static final int UNNAME_LOGIN = 130;	// �͸� �α���
	public static final int CREATE_ROOM = 200;	// �游���
	public static final int REQUEST_CREATE_ROOM = 210;	// �游��� ��û
	public static final int ENTER_ROOM = 300;	// ������
	public static final int ENTER_ROOM_OK = 301;	// ������ ����
	public static final int ENTER_ROOM_NO = 302;	// ������ ����
	public static final int EXIT_ROOM = 310;	// �泪����
	public static final int SEND_MESSAGE = 400;	// �޼��� �۽�
	public static final int RECIEVE_MESSAGE = 410;	// �޼��� ����
	public static final int LOGOUT = 500; // �α׾ƿ�
	public static final int EXIT = 510; // ����
	public static final int FRIEND = 600;	// ģ��
	public static final int ADD_FRIEND = 601;	// ģ�� �߰�
	public static final int DELETE_FRIEND = 602;	// ģ�� ����
	public static final int INVATE_FRIEND = 610;	// ģ�� �ʴ�
	public static final int ENTRY_FRIEND = 620;	// ģ�� �濡 ����
	
	public static final int SERVER_REGISTER = 1100;
	public static final int SERVER_LOGIN_RECEIVE = 1121;	// �������� �α��� �޾Ҵٰ� ����
	public static final int SERVER_LOGOUT_RECEIVE = 1122;	// �������� �α׾ƿ� Ȯ���ߴٰ� ����
	public static final int SERVER_EXIT = 1200;	// �������� ���� Ȯ���ߴٰ� ����
	public static final int SERVER_CREATE_ROOM_SUCCESS = 2000;	// �������� �� ������ٰ� ����
	public static final int SERVER_CREATE_ROOM_FAILED = 2001;	// �� ��������ٰ� ����
	public static final int SERVER_EXIT_ROOM_SUCESS = 2310;	// �� �����´ٰ� ����
	public static final int SERVER_CREATE_MESSAGE_SUCCESS = 2400;	// �������� �޼��� ������ٰ� ����
	public static final int SERVER_CREATE_MESSAGE_FAILED = 2401;	// �޼��� ��������ٰ� ����
	public static final int SERVER_ENTER_ROOM_SUCCESS = 3001;		// �������� �� ������״ٰ� ����
	public static final int SERVER_ENTER_ROOM_FAILED = 3002;		// �������� �� �����Ű�� �����ߴٰ� ����
	public static final int SERVER_ADD_FRIEND_SUCCESS = 6001;		// �������� ģ�� �߰� �����ߴٰ� ����
	public static final int SERVER_ADD_FRIEND_FAILED = 6011;		// �������� ģ�� �߰� �����ߴٰ� ����
	public static final int SERVER_DELETE_FRIEND_SUCCESS = 6002;		// �������� ģ�� ���� �����ߴٰ� ����
	public static final int SERVER_DELETE_FRIEND_FAILED = 6012;		// �������� ģ�� ���� �����ߴٰ� ����
	
	private Info() {
		
	}
}

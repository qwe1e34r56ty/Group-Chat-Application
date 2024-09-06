package main;

import thread.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.BindException;

import db.*;

public class MainCtrl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//db.makeRoom(1, 1);
		//System.out.println(db.makeRoomQuery(1, 1));
		ServerUI serverUI = new ServerUI();
		//ThreadManager tMgr = new ThreadManager(50, db);
	}
}

class ServerUI extends JFrame{
	JFrame f;
	JLabel portL;
	JLabel errL;
	JLabel successL;
	JTextField portT;
	JButton connectB;
	JPanel portP;
	ThreadManager tMgr = null;
	DBManager db = null;
	public ServerUI() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("����");
		
		f = this;
		
		db = new DBManager();
		db.clear();
		
		portL = new JLabel("           ��Ʈ ��ȣ           ");
		portT = new JTextField(10);
		portP = new JPanel();
		portP.setLayout(new BorderLayout());
		portP.add(portL, BorderLayout.WEST);
		portP.add(portT, BorderLayout.CENTER);
		add(portP, BorderLayout.NORTH);
		
		errL = new JLabel();
		add(errL, BorderLayout.CENTER);
		
		connectB = new JButton("����");
		add(connectB, BorderLayout.SOUTH);
		pack();
		connectB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int port;
				try {
					port = Integer.parseInt(portT.getText());
					tMgr = new ThreadManager(50, db, port);
				} catch (BindException e1) {
					// TODO Auto-generated catch block
					errL.setText("�ٸ� ��Ʈ ��ȣ�� ������ �ּ���!");
					pack();
					return;
					//e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					errL.setText("�ùٸ� ��Ʈ�� �Է��� �ּ���!");
					pack();
					return;
					//e1.printStackTrace();
				}
				f.getContentPane().removeAll();
				successL = new JLabel("���� ���� ��...                               ��Ʈ : " + port);
				add(successL);
				pack();
			}
			
		});
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
		setVisible(true);
	}
}
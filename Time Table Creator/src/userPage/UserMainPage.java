package userPage;

import subUserPages.*;
import tools.Validation;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.border.MatteBorder;

import homePage.Login;

public class UserMainPage extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;

	private static String[] username= {},name= {};
	public static JFrame frame,loginFrame;
	private static JPanel homePage,menubar,panel_3,timeTable,panel,currentActive,contentPane;
	private static JPanel addCollegeP,manageCollegeP,addCourseP,manageCourseP,addStaffP, manageStaffP, createTimeTableP, exportTimeTableP;
	private static String user;
	private static JLabel User,uname,gender,dob,menu,oldPassL,pass1L,pass2L;
	private static JTextField fname,email,phone;
	private static JPasswordField oldPass,pass1,pass2;
	private static JButton chgPass,update,delete,change,handover;
	public UserMainPage(JFrame obj,String username) 
	{
		super("Time Table Creator");
		frame=this;
		UserMainPage.user=username;
		con=Login.getCon();
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_HORIZ);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		loginFrame=obj;
		screen.setSize(screen.width, screen.height-40);
		setMinimumSize(screen);
		setIconImage(new ImageIcon("image_resources/icon.png").getImage());
		getContentPane().setPreferredSize(screen);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setFont(new Font("Tahoma", Font.PLAIN, 19));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel title = new JPanel();
		title.setBackground(new Color(0, 191, 255));
		title.setBounds(0, 0, 1538, 67);
		contentPane.add(title);
		title.setLayout(null);
		
		JLabel close = new JLabel("X");
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.setFont(new Font("Dialog", Font.BOLD, 14));
		close.setOpaque(true);
		close.setBackground(new Color(255, 255, 255));
		close.setForeground(new Color(255, 0, 0));
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setToolTipText("Close\r\n");
		close.setBounds(1515, 0, 23, 19);
		title.add(close);
		
		JLabel minimize = new JLabel("-");
		minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimize.setPreferredSize(new Dimension(8, 16));
		minimize.setToolTipText("Minimize");
		minimize.setOpaque(true);
		minimize.setHorizontalAlignment(SwingConstants.CENTER);
		minimize.setForeground(new Color(0, 0, 0));
		minimize.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 14));
		minimize.setBackground(Color.WHITE);
		minimize.setBounds(1490, 0, 23, 19);
		minimize.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setState(Frame.ICONIFIED);
			}
		});
		title.add(minimize);
		
		JLabel lblNewLabel = new JLabel("Time Table Creator");
		lblNewLabel.setBounds(110, 22, 1278, 33);
		title.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton logout = new JButton("LOGOUT");
		logout.setHorizontalTextPosition(SwingConstants.CENTER);
		logout.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		logout.setBounds(1423, 35, 105, 28);
		title.add(logout);
		logout.setFocusPainted(false);
		logout.setFocusTraversalKeysEnabled(false);
		logout.setBackground(new Color(0, 0, 255));
		logout.setForeground(new Color(255, 255, 255));
		logout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		logout.addActionListener(this);		
		
		menu = new JLabel("MENU");
		menu.setOpaque(true);
		menu.setBackground(new Color(255, 255, 255));
		menu.setFont(new Font("Georgia Pro Cond Semibold", Font.BOLD, 16));
		menu.setForeground(new Color(0, 0, 0));
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageMenuBar();
			}
		});
		menu.setBounds(110, 30, 65, 33);
		title.add(menu);
		
		menubar = new JPanel();
		menubar.setBackground(new Color(0, 0, 0));
		menubar.setBounds(0, 68, 300, 796);
		contentPane.add(menubar);
		menubar.setLayout(null);
		
		JPanel homePanel = new JPanel();
		homePanel.setBackground(new Color(0, 0, 0));
		homePanel.setBounds(17, 10, 276, 35);
		menubar.add(homePanel);
		homePanel.setLayout(null);
		
		JLabel home = new JLabel("   HOME");
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(currentActive!=homePage)
					currentActive.setVisible(false);
				currentActive=homePage;
				homePage.setVisible(true);
				changeMenuSize(currentActive);
			}
		});
		home.setToolTipText("HOME");
		home.setIcon(new ImageIcon("image_resources/home.png"));
		home.setFont(new Font("Dialog", Font.BOLD, 16));
		home.setForeground(new Color(255, 255, 255));
		home.setBounds(0, 0, 264, 35);
		homePanel.add(home);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 255, 255));
		separator.setBounds(0, 55, 293, 2);
		menubar.add(separator);
		
		JPanel college = new JPanel();
		college.setLayout(null);
		college.setBackground(Color.BLACK);
		college.setBounds(17, 69, 276, 106);
		menubar.add(college);
		
		JLabel l1 = new JLabel("----- College -----             ");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setForeground(new Color(255, 255, 255));
		l1.setBounds(0, 0, 276, 21);
		college.add(l1);
		
		JLabel addCollege = new JLabel("   Add new college");
		addCollege.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addCollegeP.setVisible(true);
				if(currentActive != addCollegeP)
					currentActive.setVisible(false);
				currentActive=addCollegeP;
				changeMenuSize(currentActive);
			}
		});
		addCollege.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addCollege.setToolTipText("   Add new college");
		addCollege.setForeground(Color.WHITE);
		addCollege.setIcon(new ImageIcon("image_resources/university.png"));
		addCollege.setFont(new Font("Dialog", Font.BOLD, 16));
		addCollege.setBounds(0, 33, 264, 35);
		college.add(addCollege);
		

		JLabel manageCollege = new JLabel("   Manage Colleges");
		manageCollege.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageCollegeP.setVisible(true);
				if(currentActive != manageCollegeP)
					currentActive.setVisible(false);
				currentActive=manageCollegeP;
				changeMenuSize(currentActive);
			}
		});
		manageCollege.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		manageCollege.setToolTipText("   Manage Colleges");
		manageCollege.setForeground(Color.WHITE);
		home.setIcon(new ImageIcon("image_resources/home.png"));
		manageCollege.setIcon(new ImageIcon("image_resources/manage.png"));	
		manageCollege.setFont(new Font("Dialog", Font.BOLD, 16));
		manageCollege.setBounds(0, 71, 264, 35);
		college.add(manageCollege);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(0, 187, 293, 2);
		menubar.add(separator_1);
		
		JPanel staff = new JPanel();
		staff.setLayout(null);
		staff.setBackground(Color.BLACK);
		staff.setBounds(17, 210, 276, 106);
		menubar.add(staff);
		
		JLabel l2 = new JLabel("----- Teaching Staff -----       ");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setForeground(Color.WHITE);
		l2.setBounds(0, 0, 276, 21);
		staff.add(l2);
		
		JLabel addStaff = new JLabel("   Add new staff details");
		addStaff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addStaffP.setVisible(true);
				if(currentActive != addStaffP)
					currentActive.setVisible(false);
				currentActive=addStaffP;
				changeMenuSize(currentActive);
			}
		});
		addStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addStaff.setToolTipText("   Add new staff details");
		addStaff.setForeground(Color.WHITE);
		addStaff.setIcon(new ImageIcon("image_resources/teacher.png"));	
		addStaff.setFont(new Font("Dialog", Font.BOLD, 16));
		addStaff.setBounds(0, 33, 264, 35);
		staff.add(addStaff);
		
		JLabel manageStaff = new JLabel("   Manage Staff");
		manageStaff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageStaffP.setVisible(true);
				if(currentActive != manageStaffP)
					currentActive.setVisible(false);
				currentActive=manageStaffP;
				changeMenuSize(currentActive);
			}
		});
		manageStaff.setToolTipText("   Manage Staff");
		manageStaff.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		manageStaff.setForeground(Color.WHITE);
		manageStaff.setIcon(new ImageIcon("image_resources/staff-manage.png"));	
		manageStaff.setFont(new Font("Dialog", Font.BOLD, 16));
		manageStaff.setBounds(0, 71, 264, 35);
		staff.add(manageStaff);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(0, 328, 293, 2);
		menubar.add(separator_2);
		
		JPanel courses = new JPanel();
		courses.setLayout(null);
		courses.setBackground(Color.BLACK);
		courses.setBounds(17, 342, 276, 106);
		menubar.add(courses);
		
		JLabel l3 = new JLabel("----- Cources -----               ");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setForeground(Color.WHITE);
		l3.setBounds(0, 0, 276, 21);
		courses.add(l3);
		
		JLabel addCourse = new JLabel("   Add new course");
		addCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addCourseP.setVisible(true);
				if(currentActive != addCourseP)
					currentActive.setVisible(false);
				currentActive=addCourseP;
				changeMenuSize(currentActive);
			}
		});
		addCourse.setToolTipText("   Add new course");
		addCourse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addCourse.setForeground(Color.WHITE);
		addCourse.setIcon(new ImageIcon("image_resources/course.png"));	
		addCourse.setFont(new Font("Dialog", Font.BOLD, 16));
		addCourse.setBounds(0, 33, 264, 35);
		courses.add(addCourse);
		
		JLabel manageCourse = new JLabel("   Manage Cources");
		manageCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageCourseP.setVisible(true);
				if(currentActive != manageCourseP)
					currentActive.setVisible(false);
				currentActive=manageCourseP;
				changeMenuSize(currentActive);
			}
		});
		manageCourse.setToolTipText("   Manage Cources");
		manageCourse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		manageCourse.setForeground(Color.WHITE);
		manageCourse.setIcon(new ImageIcon("image_resources/course-manage.png"));	
		manageCourse.setFont(new Font("Dialog", Font.BOLD, 16));
		manageCourse.setBounds(0, 71, 264, 35);
		courses.add(manageCourse);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(0, 460, 293, 2);
		menubar.add(separator_3);
		
		timeTable = new JPanel();
		timeTable.setLayout(null);
		timeTable.setBackground(Color.BLACK);
		timeTable.setBounds(17, 474, 276, 106);
		menubar.add(timeTable);
		
		JLabel l4 = new JLabel("----- Time Table -----        ");
		l4.setHorizontalAlignment(SwingConstants.CENTER);
		l4.setForeground(Color.WHITE);
		l4.setBounds(0, 0, 276, 21);
		timeTable.add(l4);
		
		JLabel create = new JLabel("   Create Time-Table");
		create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createTimeTableP.setVisible(true);
				if(currentActive != createTimeTableP)
					currentActive.setVisible(false);
				currentActive=createTimeTableP;
				changeMenuSize(currentActive);
			}
		});
		create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		create.setToolTipText("   Create Time-Table");
		create.setForeground(Color.WHITE);
		create.setIcon(new ImageIcon("image_resources/time-table.png"));	
		create.setFont(new Font("Dialog", Font.BOLD, 16));
		create.setBounds(0, 33, 264, 35);
		timeTable.add(create);
		
		JLabel export = new JLabel("   Export Time-Table");
		export.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exportTimeTableP.setVisible(true);
				if(currentActive != exportTimeTableP)
					currentActive.setVisible(false);
				currentActive=exportTimeTableP;
				changeMenuSize(currentActive);
			}
		});
		export.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		export.setToolTipText("   Export Time-Table");
		export.setForeground(Color.WHITE);
		export.setIcon(new ImageIcon("image_resources/export.png"));	
		export.setFont(new Font("Dialog", Font.BOLD, 16));
		export.setBounds(0, 71, 264, 35);
		timeTable.add(export);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.WHITE);
		separator_4.setBounds(0, 592, 293, 2);
		menubar.add(separator_4);
		
		homePage = new JPanel();
		homePage.setBackground(new Color(255, 250, 205));
		homePage.setBounds(300, 68, 1238, 797);
		contentPane.add(homePage);
		homePage.setLayout(null);	
		currentActive=homePage;
		
		JLabel welcome = new JLabel("Welcome");
		welcome.setHorizontalAlignment(SwingConstants.LEFT);
		welcome.setFont(new Font("Calibri", Font.ITALIC, 32));
		welcome.setBounds(49, 83, 142, 53);
		homePage.add(welcome);
		
		User = new JLabel("");
		User.setForeground(Color.RED);
		User.setVerticalAlignment(SwingConstants.TOP);
		User.setBackground(Color.CYAN);
		User.setFont(new Font("Rockwell", Font.PLAIN, 36));
		User.setBounds(49, 127, 772, 53);
		homePage.add(User);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setVisible(false);
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(860, 307, 318, 295);
		homePage.add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		oldPassL = new JLabel("Old Password");
		oldPassL.setBounds(87, 10, 142, 19);
		panel.add(oldPassL);
		oldPassL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		oldPass = new JPasswordField();
		oldPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					changePassword();
			}
		});
		oldPass.setHorizontalAlignment(SwingConstants.CENTER);
		oldPass.setBounds(49, 39, 219, 32);
		panel.add(oldPass);
		oldPass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		oldPass.setColumns(10);
		
		pass1L = new JLabel("Create a new password   ");
		pass1L.setHorizontalAlignment(SwingConstants.CENTER);
		pass1L.setBounds(42, 104, 230, 14);
		panel.add(pass1L);
		pass1L.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		pass1 = new JPasswordField();
		pass1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					changePassword();
			}
		});
		pass1.setHorizontalAlignment(SwingConstants.CENTER);
		pass1.setBounds(51, 128, 219, 32);
		panel.add(pass1);
		pass1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pass1.setColumns(10);
		
		pass2L = new JLabel("Re-enter new password ");
		pass2L.setBounds(47, 179, 225, 14);
		panel.add(pass2L);
		pass2L.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		pass2 = new JPasswordField();
		pass2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					changePassword();
			}
		});
		pass2.setHorizontalAlignment(SwingConstants.CENTER);
		pass2.setBounds(50, 203, 219, 32);
		panel.add(pass2);
		pass2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pass2.setColumns(10);
		
		change = new JButton("CHANGE");
		change.setBounds(169, 253, 123, 32);
		panel.add(change);
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePassword();
			}
		});
		change.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		
		JButton cancel = new JButton("CANCEL");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c:panel.getComponents())
					if(c instanceof  JTextField)
						((JTextField) c).setText("");
				panel.setVisible(false);
			}
		});
		cancel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		cancel.setBounds(36, 253, 123, 32);
		panel.add(cancel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBounds(49, 185, 527, 90);
		homePage.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel usernameL = new JLabel("USERNAME : ");
		usernameL.setBounds(10, 21, 127, 19);
		panel_1.add(usernameL);
		usernameL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		uname = new JLabel("");
		uname.setBounds(20, 50, 507, 19);
		panel_1.add(uname);
		uname.setFont(new Font("Bookman Old Style", Font.PLAIN, 20));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBounds(49, 307, 527, 392);
		homePage.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel fnameL = new JLabel("Full Name : ");
		fnameL.setBounds(10, 10, 123, 19);
		panel_2.add(fnameL);
		fnameL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		fname = new JTextField();
		fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(fname.getText().length()>=50) e.consume();
			}
		});
		fname.setBounds(10, 39, 385, 32);
		panel_2.add(fname);
		fname.setEditable(false);
		fname.setDisabledTextColor(new Color(0, 0, 0));
		fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					email.requestFocus();
			}
		});
		fname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fname.setColumns(10);
		
		JLabel dobL = new JLabel("Date of Birth :");
		dobL.setBounds(10, 95, 150, 19);
		panel_2.add(dobL);
		dobL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		dob = new JLabel("");
		dob.setBounds(153, 95, 192, 19);
		panel_2.add(dob);
		dob.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		gender = new JLabel("");
		gender.setBounds(101, 124, 192, 19);
		panel_2.add(gender);
		gender.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		JLabel genderL = new JLabel("Gender :");
		genderL.setBounds(10, 125, 127, 19);
		panel_2.add(genderL);
		genderL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		JLabel emailL = new JLabel("E-mail : ");
		emailL.setBounds(10, 170, 101, 19);
		panel_2.add(emailL);
		emailL.setName("email");
		emailL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		email = new JTextField();
		email.setBounds(10, 199, 264, 32);
		panel_2.add(email);
		email.setEditable(false);
		email.setDisabledTextColor(new Color(0, 0, 0));
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(fname.getText().length()>=30) e.consume();
			}
		});
		email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		email.setColumns(10);
		
		JLabel phoneL = new JLabel("Phone number : ");
		phoneL.setBounds(10, 240, 179, 19);
		panel_2.add(phoneL);
		phoneL.setName("");
		phoneL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
		
		phone = new JTextField();
		phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(fname.getText().length()>=10) e.consume();
			}
		});
		phone.setBounds(10, 269, 264, 32);
		panel_2.add(phone);
		phone.setEditable(false);
		phone.setFocusCycleRoot(true);
		phone.setDisabledTextColor(new Color(0, 0, 0));
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		phone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		phone.setColumns(10);
		
		update = new JButton("EDIT DETAILS");
		update.setFocusPainted(false);
		update.setForeground(Color.WHITE);
		update.setBounds(114, 350, 123, 32);
		update.setBackground(Color.BLUE);
		update.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		panel_2.add(update);
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(update.getActionCommand().equals("EDIT DETAILS"))
				{
					update.setText("UPDATE");
					delete.setText("CANCEL");
					fname.setEditable(true);
					email.setEditable(true);
					phone.setEditable(true);
				}
				else
					updateDetails();
			}
		});
		
		delete = new JButton("DELETE ACCOUNT");
		delete.setFocusPainted(false);
		delete.setForeground(Color.WHITE);
		delete.setBounds(247, 350, 158, 32);
		delete.setBackground(Color.RED);
		delete.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		panel_2.add(delete);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Deleter account
				if(delete.getActionCommand().equals("CANCEL"))
				{
					update.setText("EDIT DETAILS");
					delete.setText("DELETE ACCOUNT");
					fname.setEditable(false);
					email.setEditable(false);
					phone.setEditable(false);
				}
				else
				{
					try {
						stmt=con.createStatement();
						rs=stmt.executeQuery("SELECT COUNT(*) FROM UserAccounts;");
						rs.next();
						int count=rs.getInt(1);
						rs=stmt.executeQuery("SELECT username FROM UserAccounts WHERE username='"+user+"' AND admin=1;");
						if(rs.next() && count!=1) {
							JOptionPane.showMessageDialog(null, "You are not allowed to delete your account. Please handover your admin previlage to other users and try again.");
							return;
						}
					}
					catch(SQLException ee)
					{
						ee.printStackTrace();
					}
					int i=JOptionPane.showOptionDialog(null, "Are your sure you want to delete your account?"
							+ "? \nYou no longer can access to the system. ","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
					if(i!=JOptionPane.YES_OPTION)
						return;
					else
						deleteAccount();
				}
			}
		});
		delete.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		
		chgPass = new JButton("CHANGE PASSWORD >");
		chgPass.setForeground(new Color(255, 255, 255));
		chgPass.setBounds(631, 308, 202, 32);
		chgPass.setBackground(new Color(50, 205, 50));
		chgPass.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		chgPass.setFocusPainted(false);
		chgPass.setFocusTraversalKeysEnabled(false);
		homePage.add(chgPass);
		chgPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!panel.isVisible())
				{
					panel.setVisible(true);
				}
			}
		});
		chgPass.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
 
		getUserDetails();
		createAdminServices();
		manageAdminServices();
		
		addCollegeP=new AddCollege();
		contentPane.add(addCollegeP);
		addCollegeP.setBounds(50, 68, 1563, 797);
		addCollegeP.setVisible(false);
		
		manageCollegeP=new ManageCollege(user);
		contentPane.add(manageCollegeP);
		manageCollegeP.setBounds(50, 68, 1563, 797);
		manageCollegeP.setVisible(false);
		
		addStaffP=new AddStaff();
		contentPane.add(addStaffP);
		addStaffP.setBounds(50, 68, 1563, 797);
		addStaffP.setVisible(false);
		
		manageStaffP=new ManageStaff();
		contentPane.add(manageStaffP);
		manageStaffP.setBounds(50, 68, 1563, 797);
		manageStaffP.setVisible(false);
		
		addCourseP=new AddCourse();
		contentPane.add(addCourseP);
		addCourseP.setBounds(50, 68, 1563, 797);
		addCourseP.setVisible(false);
		
		manageCourseP=new ManageCourse();
		contentPane.add(manageCourseP);
		manageCourseP.setBounds(50, 68, 1563, 797);
		manageCourseP.setVisible(false);
		
		createTimeTableP=new CreateTimeTable(frame);
		contentPane.add(createTimeTableP);
		createTimeTableP.setBounds(50, 68, 1563, 797);
		createTimeTableP.setVisible(false);
		
		exportTimeTableP=new ExportTimeTable();
		contentPane.add(exportTimeTableP);
		exportTimeTableP.setBounds(50, 68, 1563, 797);
		exportTimeTableP.setVisible(false);
	}
	
	private void createAdminServices() {
			panel_3 = new JPanel();
			panel_3.setBackground(new Color(255, 255, 255));
			panel_3.setBounds(874, 192, 304, 105);
			homePage.add(panel_3);
			panel_3.setVisible(false);
			panel_3.setLayout(null);
			
			JComboBox<String> userList = new JComboBox<String>();
			userList.setBounds(10, 10, 284, 28);
			panel_3.add(userList);
			userList.setBackground(Color.WHITE);
			
			try {
				stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs=stmt.executeQuery("SELECT username,name FROM UserAccounts WHERE username<>'"+user+"';");
				int count=0;
				while(rs.next()) count++;
				rs.beforeFirst();
				username=new String[count+1];
				name=new String[count+1];
				count=1;
				name[0]="--- Select new admin --- ";
				while(rs.next())
				{
					username[count]=rs.getString(1);
					name[count]=rs.getString(2);
					count++;
				}
				userList.setModel(new DefaultComboBoxModel<String>(name));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			JButton cancel1 = new JButton("CANCEL");
			cancel1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_3.setVisible(false);
					userList.setSelectedIndex(0);
				}
			});
			cancel1.setBounds(24, 47, 123, 32);
			panel_3.add(cancel1);
			cancel1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			JButton change1 = new JButton("CHANGE");
			change1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(userList.getSelectedIndex()<=0)
					{
						JOptionPane.showMessageDialog(null, "Please select the user.");
						return;
					}
					int i=JOptionPane.showOptionDialog(null, "Are your sure you want to handover your adminship to "+name[userList.getSelectedIndex()]
							+ "? \nAdmin previlage will be handed over to selected user and you no longer be admin. ","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
					if(i!=JOptionPane.YES_OPTION)
						return;
					try {
						stmt.executeUpdate("UPDATE UserAccounts SET admin=0 WHERE username='"+user+"';");
						stmt.executeUpdate("UPDATE UserAccounts SET admin=1 WHERE username='"+username[userList.getSelectedIndex()]+"';");
					}catch(SQLException ee) 
					{
						ee.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Admin previlage transferred succesfully.");
					manageAdminServices();
				}
			});
			change1.setBounds(157, 47, 123, 32);
			panel_3.add(change1);
			change1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			handover = new JButton("HANDOVER MY ADMINSHIP >");
			handover.setForeground(new Color(255, 255, 255));
			handover.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_3.setVisible(true);
				}
			});
			handover.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			handover.setBounds(631, 191, 233, 32);
			handover.setBackground(new Color(218, 112, 214));
			handover.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			handover.setFocusPainted(false);
			handover.setFocusTraversalKeysEnabled(false);
			homePage.add(handover);
		}
	
	private void manageAdminServices() {
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT username FROM UserAccounts WHERE username='"+user+"' AND admin="+1+";");
			if(!rs.next())  {
				handover.setVisible(false);
				panel_3.setVisible(false);
			}
			else {
				handover.setVisible(true);
				panel_3.setVisible(false);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		 loginFrame.setVisible(true);
		 dispose();
	}
	
	private void manageMenuBar()
	{
		if(menubar.getWidth()==300)
		{
			menubar.setSize(65, menubar.getHeight());
			menu.setBounds(0,30,65,33);	
			if(homePage.isVisible())
			{
				homePage.setSize(homePage.getWidth()+235, homePage.getHeight());
				homePage.setBounds(homePage.getBounds().x-235 , homePage.getBounds().y, homePage.getWidth(), homePage.getHeight());
			}
		}
		else if(menubar.getWidth()==65)
		{
			menubar.setSize(300, menubar.getHeight());
			menu.setBounds(110,30,65,33);		
			if(homePage.isVisible())
			{
				homePage.setSize(homePage.getWidth()-235, homePage.getHeight());
				homePage.setBounds(homePage.getBounds().x+235 , homePage.getBounds().y, homePage.getWidth(), homePage.getHeight());
			}
		}	
	}
	
	private void changeMenuSize(JPanel jp)
	{
		if(jp.getClass().equals(homePage.getClass()) && menubar.getWidth()==65)
		{
			menubar.setSize(300, menubar.getHeight());
			menu.setBounds(110,30,65,33);
			if(homePage.getX()!=300)
				homePage.setBounds(homePage.getBounds().x+235 , homePage.getBounds().y, homePage.getWidth(), homePage.getHeight());
		}
		else if(!jp.getClass().equals(homePage.getClass()))
		{
			menubar.setSize(65, menubar.getHeight());
			menu.setBounds(0,30,65,33);
		}
	}
	
	private void getUserDetails()
	{
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT name,dob,gender,email,phone FROM UserAccounts WHERE username='"+user+"';");
			rs.next();
			User.setText(rs.getString(1));
			fname.setText(rs.getString(1));
			uname.setText(user);
			dob.setText(setDate(rs.getString(2)));
			gender.setText(rs.getString(3));
			email.setText(rs.getString(4));
			phone.setText(rs.getString(5));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private String setDate(String str)
	{
		String[] s=str.split("-");
		return(s[2]+"-"+s[1]+"-"+s[0]);
	}
	
	private void updateDetails()
	{
		if(!Validation.validateUpdateDetails(fname,  email, phone))
		{
			return;
		}
		try {
			stmt=con.createStatement();
			int i=stmt.executeUpdate("UPDATE UserAccounts SET name='"+fname.getText()+"', email='"+email.getText()+"',phone='"+phone.getText()+"' WHERE username='"+user+"';");
			if(i>0) {
				JOptionPane.showMessageDialog(null, "Account details updated.");
				getUserDetails();
			}	
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		update.setText("EDIT DETAILS");
		delete.setText("DELETE ACCOUNT");
		fname.setEditable(false);
		email.setEditable(false);
		phone.setEditable(false);
	}
	
	private void deleteAccount()
	{
		try {
			stmt=con.createStatement();
			int i=stmt.executeUpdate("DELETE FROM UserAccounts WHERE username='"+user+"';");
			if(i>0)
				JOptionPane.showMessageDialog(null, "Account deleted successfully.");
				loginFrame.setVisible(true);
				dispose();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void changePassword()
	{
		if(!passwordValidate())
			return;
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT password FROM UserAccounts WHERE username='"+user+"';");
			rs.next();
			String old=rs.getString(1);
			if(!String.valueOf(oldPass.getPassword()).equals(old))
			{
				JOptionPane.showMessageDialog(this, "Old password is incorrect.!", "Incorrect old password", JOptionPane.INFORMATION_MESSAGE);
				oldPass.requestFocus();
				return;
			}
			
			int i=stmt.executeUpdate("UPDATE UserAccounts SET password='"+String.valueOf(pass1.getPassword())+"' WHERE username='"+user+"';");
			if(i>0)
			{
				JOptionPane.showMessageDialog(this, "Password changed successfully.");
				panel.setVisible(false);
			}
			else
				JOptionPane.showMessageDialog(this, "Failed to change password!");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private boolean passwordValidate() 
	{
		if(String.valueOf(oldPass.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter your current password", "Password Required", JOptionPane.INFORMATION_MESSAGE);
			pass1.requestFocus();
			return false;
		}
		if(String.valueOf(pass1.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter password (8-15 characters)", "Password Required", JOptionPane.INFORMATION_MESSAGE);
			pass1.requestFocus();
			return false;
		}
		if(String.valueOf(pass1.getPassword()).length()<8 || String.valueOf(pass1.getPassword()).length()>15) {
			JOptionPane.showMessageDialog(this, "Password should be between 8-15 characters", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
			pass1.requestFocus();
			return false;
		}
		if(!String.valueOf(pass1.getPassword()).equals(String.valueOf(pass2.getPassword()))) {
			JOptionPane.showMessageDialog(this, "Passwords do not match!", "Password Mismatch", JOptionPane.INFORMATION_MESSAGE);
			pass2.requestFocus();
			return false;
		}
		return true;
	}
}

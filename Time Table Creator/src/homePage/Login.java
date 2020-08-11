package homePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import userPage.UserMainPage;
import registerPage.Register;

public class Login {
	private static Connection con=null;
	private static Statement stmt=null;
	private static ResultSet rs=null;
	
	private static JFrame frame;
	private static JLabel error,status;
	private static JTextField user;
	private static JPasswordField pass;
	private static JButton login;
	
	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = "jdbc:sqlserver://LAPTOP-BAB1964C";
//	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DB_URL="jdbc:mysql://localhost:3306";
			
	static final String USER = "DBMS_Project";
	static final String PASS = "project";
	
	public Login() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("Time Table Creator");
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(new Color(250, 255, 210));
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		frame.setIconImage(new ImageIcon("image_resources/icon.png").getImage());
		
		JPanel title_container = new JPanel();
		title_container.setBounds(0, 0, 500, 80);
		frame.getContentPane().add(title_container);
		title_container.setBackground(Color.BLUE);
		title_container.setLayout(null);
		
		JLabel title = new JLabel("Time Table Creator");
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font("Rockwell Nova", Font.BOLD, 33));
		title.setBounds(78, 20, 383, 44);
		title_container.add(title);
		
		JLabel logo = new JLabel("");
		logo.setBounds(10, 10, 58, 60);
		title_container.add(logo);
		logo.setIcon(new ImageIcon(new ImageIcon("image_resources/icon.png").getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_DEFAULT)));
		
		JLabel close = new JLabel("X");
		close.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		close.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		close.setOpaque(true);
		close.setForeground(Color.WHITE);
		close.setBackground(Color.RED);
		close.setHorizontalTextPosition(SwingConstants.CENTER);
		close.setBounds(467, 0, 33, 21);
		title_container.add(close);
		
		status = new JLabel("");
		status.setFont(new Font("Verdana", Font.PLAIN, 11));
		status.setForeground(Color.WHITE);
		status.setBounds(78, 59, 412, 21);
		title_container.add(status);
		
		JLabel username = new JLabel("USERNAME");
		username.setBounds(305, 172, 110, 26);
		frame.getContentPane().add(username);
		username.setFont(new Font("Georgia", Font.PLAIN, 15));
		username.setHorizontalTextPosition(SwingConstants.CENTER);
		username.setHorizontalAlignment(SwingConstants.CENTER);
		
		user = new JTextField("");
		user.setBounds(249, 208, 223, 35);
		frame.getContentPane().add(user);
		user.setSelectionColor(new Color(0, 120, 215));
		user.setToolTipText("Username");
		user.setHorizontalAlignment(SwingConstants.CENTER);
		user.setFont(new Font("Tahoma", Font.PLAIN, 16));
		user.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		user.setBackground(new Color(245, 255, 250));
		user.setColumns(10);
		user.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					pass.requestFocus();
			}
		});
		
		JLabel password = new JLabel("PASSWORD");
		password.setBounds(305, 269, 110, 20);
		frame.getContentPane().add(password);
		password.setHorizontalTextPosition(SwingConstants.CENTER);
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setFont(new Font("Georgia", Font.PLAIN, 15));
		
		pass = new JPasswordField("");
		pass.setBounds(249, 299, 223, 35);
		frame.getContentPane().add(pass);
		pass.setToolTipText("Password");
		pass.setHorizontalAlignment(SwingConstants.CENTER);
		pass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pass.setDisabledTextColor(new Color(109, 109, 109));
		pass.setBackground(new Color(245, 255, 250));
		pass.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					login();
			}
		});
		
		login = new JButton("LOGIN");
		login.setBorder(null);
		login.setDefaultCapable(false);
		login.setFocusTraversalPolicyProvider(true);
		login.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					login();
			}
		});
		login.setBounds(324, 367, 91, 35);
		frame.getContentPane().add(login);
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login.setFont(new Font("Century Gothic", Font.BOLD, 15));
		login.setForeground(new Color(255, 255, 255));
		login.setBackground(Color.BLUE);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				login();
			}
		});
		
		JLabel registerAdmin = new JLabel(" Register as admin? Click here!");
		registerAdmin.setBounds(266, 424, 206, 19);
		frame.getContentPane().add(registerAdmin);
		registerAdmin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
						Register register = new Register(frame,con);
						register.setVisible(true);
						frame.setVisible(false);
			}
		});
		registerAdmin.setToolTipText("Click here to register");
		registerAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerAdmin.setForeground(Color.BLUE);
		registerAdmin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		registerAdmin.setHorizontalTextPosition(SwingConstants.CENTER);
		registerAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		
		error = new JLabel("");
		error.setBounds(249, 128, 241, 23);
		frame.getContentPane().add(error);
		error.setVisible(false);
		error.setForeground(new Color(255, 51, 0));
		error.setBackground(new Color(255, 255, 255));
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		
		JLabel background = new JLabel("");
		background.setFocusTraversalKeysEnabled(false);
		background.setBackground(Color.BLUE);
		background.setBounds(0, 0, 500, 500);
		frame.getContentPane().add(background);
		background.setIcon(new ImageIcon(new ImageIcon("image_resources/back.png").getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH)));
		
		JLabel login_icon = new JLabel("");
		login_icon.setBounds(201, 151, 50, 50);
		frame.getContentPane().add(login_icon);
		login_icon.setIcon(new ImageIcon("image_resources/login.png"));
	}
	
	private void login()
	{
		if(user.getText().equals("")) {
			JOptionPane.showMessageDialog(frame, "Username is required !","Invalid credentials" , JOptionPane.WARNING_MESSAGE);
			user.requestFocus();
			return;
		}
		else if(String.valueOf(pass.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(frame, "Password cannot be blank !","Invalid credentials" , JOptionPane.WARNING_MESSAGE);
			pass.requestFocus();
			return;
		}
		String user,pass;
		user=Login.user.getText();
		pass=String.valueOf(Login.pass.getPassword());
		error.setVisible(false);
		try {
			stmt=con.createStatement();
			String qry="SELECT username,password FROM UserAccounts WHERE username=\'"+user+"\' AND password=\'"+pass+"\';" ;
			rs=stmt.executeQuery(qry);
			if(rs.next()) {
				Login.user.setText("");
				Login.pass.setText("");
			   UserMainPage userPage = new UserMainPage(frame,user);
			   userPage.setVisible(true);
			   frame.setVisible(false);
			}
			else
			{
				error.setText("Invalid username or password !!");
				error.setVisible(true);
				Login.pass.setText("");
				Login.user.requestFocus();
			}
		} 
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(frame, e.getMessage(), e.getMessage()	, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable ( ) {
			public void run() {
				try {
					new Login();
					Login.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Encountered exception"	, JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}			});
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch(ClassNotFoundException e) {
			showMessage("Unable to load driver!",0,status);
			e.printStackTrace();
		}
		
		System.out.println("Connecting to database...");
		status=new JLabel("");
		wait(500);
		showMessage("Waiting...",500,status);
		showMessage("Connecting to database...",0,status);
			
		try {
			con=DriverManager.getConnection(DB_URL,USER,PASS);
		} catch(SQLException e) {
			showMessage("Unable to connect to the database!",0,status);
			e.printStackTrace();
		}

		try 
		{
			stmt=con.createStatement();
			System.out.println("Connection established.");
			showMessage("Connection established.",0,status);
			
			if(!checkDBExists(con,"TIME_TABLE_DATA")) {
				System.out.println("Creating database...");
				stmt.executeUpdate("CREATE DATABASE TIME_TABLE_DATA");
				System.out.println("Database created successfully.");
			}
			stmt.executeUpdate("USE TIME_TABLE_DATA");
			
			rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"UserAccounts",null);
			if(!rs.next()) //If table not available create it 
			{
				System.out.println("Creating table UserAccounts...");
				showMessage("Creating table UserAccounts...",0,status);
				stmt.executeUpdate("CREATE TABLE UserAccounts "
						+ "(user_id int NOT NULL IDENTITY(1,1) PRIMARY KEY, "
						+ "username varchar(30) UNIQUE NOT NULL, "
						+ "name varchar(50), dob date, gender varchar(7), "
						+ "email varchar(30), phone varchar(15),  password varchar(16) NOT NULL, admin int);");
				System.out.println("Table created successfully.");
				showMessage("Table created successfully.",2000,status);
			}
			
			createTables();
			wait(4000);
			status.setText("");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Encountered exception"	, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Encountered exception"	, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private static void createTables() throws SQLException
	{
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"College",null);		//Create table College if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Colleges...");
			String table = "CREATE TABLE College (" + 
					"	code varchar(10)  PRIMARY KEY," + 
					"	fname varchar(100) NOT NULL," + 
					"	sname varchar(50)," + 
					"	address varchar(120)," + 
					"	city varchar(20)," + 
					"	pincode varchar(10)," + 
					"	email varchar(30)," + 
					"	phone varchar(10)" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		else
		{
			rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"college_view",null);		//Create table College if notexists
			if(!rs.next())
			{
				System.out.println("Creating view of College...");
				String table = "CREATE VIEW college_view AS (" + 
						"SELECT code,fname FROM College);";
				stmt.executeUpdate(table);
				System.out.println("View created successfully.");
			}
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Department",null);		//Create table Department if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Department...");
			String table = "CREATE TABLE Department (" + 
					"	dept_id varchar(10)," + 
					"	dept_name varchar(50)," + 
					"	hod varchar(10) DEFAULT NULL," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(dept_id,code)," + 
					"	FOREIGN KEY(code) REFERENCES College(code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Semester",null);		//Create table Semester if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Semester...");
			String table = "CREATE TABLE Semester (" + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(sem,dept_id,code)," + 
					"	FOREIGN KEY(dept_id,code) REFERENCES Department(dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Course",null);		//Create table Course if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Course...");
			String table = "CREATE TABLE Course (" + 
					"	course_id varchar(10)," + 
					"	course_name varchar(50)," + 
					"   short_name varchar(7)," +
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(course_id,sem,dept_id,code)," + 
					"	FOREIGN KEY(sem,dept_id,code) REFERENCES Semester(sem,dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE\r\n" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Staff",null);		//Create table Course if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Staff...");
			String table = "CREATE TABLE Staff (" + 
					"	staff_id varchar(10)," + 
					"	staff_name varchar(30)," + 
					"	dob date," + 
					"	age int," + 
					"	phone varchar(10)," + 
					"	email varchar(30)," + 
					"	joined_date date," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY (staff_id,dept_id,code)," + 
					"	FOREIGN KEY(dept_id,code) REFERENCES Department(dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
			
			System.out.println("Creating trigger...");
			String trigger = "CREATE TRIGGER set_age" + 
					" ON Staff" + 
					" FOR INSERT,UPDATE" + 
					" AS" + 
					" BEGIN" + 
					"  DECLARE @ID varchar(10)" + 
					"  SELECT @ID=staff_id FROM INSERTED" + 
					"  UPDATE Staff SET dob=getDate() WHERE dob=''" +
					"  UPDATE Staff SET age=DateDiff(year, dob, getDate()) WHERE staff_id=@id" + 
					" END;";
			try {
			stmt.executeUpdate(trigger);
			}
			catch(SQLException e) {
				System.out.println("Trigger already exists.");
			}
			System.out.println("Trigger created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"ClassRoom",null);		//Create table Classroom if notexists
		if(!rs.next())
		{
			System.out.println("Creating table ClassRoom...");
			String table = "CREATE TABLE ClassRoom (" + 
					"	room_number varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(room_number,code)," + 
					"	FOREIGN KEY(code) REFERENCES College(code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfull.");
		}
	
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Section",null);		//Create table Section if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Section...");
			String table = "CREATE TABLE Section (" + 
					"	section varchar(5)," + 
					"	no_of_students int," + 
					"	room_number varchar(10)," + 
					"	dept_id varchar(10)," + 
					"	sem int," + 
					"	code varchar(10)," +
					"   hours int," +
					"   final int,"+
					"   advisor varchar(10)," + 
					"	PRIMARY KEY(section,dept_id,code,sem)," + 
					"	FOREIGN KEY(sem,dept_id,code) REFERENCES Semester(sem,dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Schedule",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			System.out.println("Creating table Schedule...");
			String table = "CREATE TABLE Schedule (" + 
					"	course_id varchar(10)," + 
					"	staff_id varchar(10)," + 
					"	week_day int," + 
					"	hour_no int," + 
					"	class_type varchar(30)," + 
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"   start_time time," +
					"   end_time time," +
					"	PRIMARY KEY(course_id,hour_no,week_day,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(section,dept_id,code,sem) REFERENCES Section(section,dept_id,code,sem) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
			System.out.println("Table created successfully.");
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Time",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE Time (" + 
					"	hour int," + 
					"	start_time time," +
					"   end_time time," +
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(hour,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(section,dept_id,code,sem) REFERENCES Section(section,dept_id,code,sem) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Theory",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE Theory (" + 
					"	course_id varchar(10)," + 
					"	staff_id varchar(10)," + 
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(course_id,staff_id,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(section,dept_id,code,sem) REFERENCES Section(section,dept_id,code,sem) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"LabGroup",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE LabGroup (" +
					"   lab_group varchar(30)," +
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(lab_group,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(section,dept_id,code,sem) REFERENCES Section(section,dept_id,code,sem) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Lab",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE Lab (" +
					"   lab_group varchar(30)," +
					"	course_id varchar(10)," + 
					"	staff_id varchar(10)," + 
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(lab_group,course_id,staff_id,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(lab_group,section,sem,dept_id,code) REFERENCES LabGroup(lab_group,section,sem,dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"ElectiveGroup",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE ElectiveGroup (" +
					"   elective_group varchar(30)," +
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(elective_group,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(section,dept_id,code,sem) REFERENCES Section(section,dept_id,code,sem) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
		
		rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Elective",null);		//Create table Schedule if notexists
		if(!rs.next())
		{
			String table = "CREATE TABLE Elective (" + 
					"   elective_group varchar(30)," +
					"	course_id varchar(10)," + 
					"	staff_id varchar(10)," + 
					"	section varchar(5)," + 
					"	sem int," + 
					"	dept_id varchar(10)," + 
					"	code varchar(10)," + 
					"	PRIMARY KEY(elective_group,course_id,staff_id,section,sem,dept_id,code)," + 
					"	FOREIGN KEY(elective_group,section,sem,dept_id,code) REFERENCES ElectiveGroup(elective_group,section,sem,dept_id,code) ON DELETE CASCADE ON UPDATE CASCADE" + 
					");";
			stmt.executeUpdate(table);
		}
	}
		
	public static boolean checkDBExists(Connection con, String DB) throws SQLException
	{
		rs=con.getMetaData().getCatalogs();
		while(rs.next())
			if(DB.equals(rs.getString(1)))
				return true;
		return false;
	}
	
	private static void showMessage(String msg, int time, JLabel label)
	{
		status.setText(msg);
		if(time!=0) {
		wait(time);
		status.setText("");}
	}
	private static void wait(int time)
	{
		try {
			Thread.sleep(time);
		} catch(InterruptedException e) {}
	}
	
	public static Connection getCon() {
		return con;
	}
}
package registerPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.*;
import java.sql.*;
import tools.Validation;

public class Register extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static Connection con=null;
	private static Statement stmt=null;
	private static ResultSet rs=null;
	
	private static JPanel details,confirm;
	private static JFrame loginFrame;
	private static JTextField fname,mname,lname,email,phone,username,aUser;
	private static JPasswordField pass1,pass2,aPass;
	private static JDateChooser date;
	private static JRadioButton male,female,other;
	private static JLabel unameStatus,admin,lblFieldMarkedWith,lblAdminAuthuntication;
	private static StringBuilder result;
	private static ButtonGroup bg;
	private static JButton cancel,next,reset,exit,back,finish;
	private static int authorize=0;
	public Register(JFrame parent,Connection conn) {
		loginFrame=parent;
		Register.con=conn;
		new JFrame("Time Table Creator");
		setResizable(false);
		getContentPane().setBackground(new Color(255, 240, 245));
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setIconImage(new ImageIcon("image_resources/icon.png").getImage());
		
		JPanel title_container = new JPanel();
		title_container.setBounds(0, 0, 500, 50);
		getContentPane().add(title_container);
		title_container.setLayout(null);
		title_container.setBackground(Color.BLUE);
		
		JLabel logo = new JLabel("");
		logo.setBounds(0, 0, 50, 50);
		logo.setIcon(new ImageIcon(new ImageIcon("image_resources/icon.png").getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_DEFAULT)));
		title_container.add(logo);
		
		JLabel title = new JLabel("Time Table Creator");
		title.setBounds(60, 5, 352, 44);
		title_container.add(title);
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font("Rockwell Nova", Font.BOLD, 25));
		
		JLabel close = new JLabel(" X ");
		close.setBounds(444, 0, 46, 13);
		title_container.add(close);
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		close.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		close.setOpaque(true);
		close.setForeground(Color.WHITE);
		close.setBackground(Color.RED);
		close.setHorizontalTextPosition(SwingConstants.CENTER);
		close.setBounds(467, 0, 33, 21);
		close.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
		bg=new ButtonGroup();
		
		confirm = new JPanel();
		confirm.setOpaque(false);
		confirm.setVisible(false);
		getContentPane().add(confirm);
		confirm.setBounds(0, 49, 500, 451);
		confirm.setLayout(null);
		JLabel createUsername = new JLabel("Create a new username  :  ");
		createUsername.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		createUsername.setBounds(48, 53, 178, 14);
		confirm.add(createUsername);
		
		username = new JTextField();
		username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(validatedUsername()) {
						validateDetails();
						username.setBackground(Color.white);
						unameStatus.setVisible(false);
					}
					else
					{
						username.setBackground(new Color(255,255,200));
						username.requestFocus();
					}
				}
			}
			public void keyTyped(KeyEvent e) {
				if(username.getText().length()>=30) e.consume();
			}
		});
		username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		username.setColumns(10);
		username.setBounds(48, 77, 178, 21);
		confirm.add(username);
		
		JLabel pass = new JLabel("Create a new password  :  ");
		pass.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		pass.setBounds(47, 130, 179, 14);
		confirm.add(pass);
		
		pass1 = new JPasswordField();
		pass1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
				}
			}
		});
		pass1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pass1.setColumns(10);
		pass1.setBounds(47, 148, 178, 21);
		confirm.add(pass1);
		
		JLabel cpass = new JLabel("Confirm password :");
		cpass.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		cpass.setBounds(47, 179, 134, 14);
		confirm.add(cpass);
		
		pass2 = new JPasswordField();
		pass2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
				}
			}
		});
		pass2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pass2.setColumns(10);
		pass2.setBounds(47, 197, 178, 21);
		confirm.add(pass2);
		
		JLabel adminUser = new JLabel("Enter the username of Admin:");
		adminUser.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		adminUser.setBounds(48, 273, 213, 14);
		confirm.add(adminUser);
		
		aUser = new JTextField();
		aUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
				}
			}
		});
		aUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		aUser.setColumns(10);
		aUser.setBounds(48, 290, 178, 21);
		confirm.add(aUser);
		
		JLabel adminPass = new JLabel("Admin Password :");
		adminPass.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		adminPass.setBounds(48, 321, 144, 14);
		confirm.add(adminPass);
		
		aPass = new JPasswordField();
		aPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					finish.requestFocus();
					finalValidate();
				}
			}
		});
		aPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		aPass.setColumns(10);
		aPass.setBounds(48, 339, 178, 21);
		confirm.add(aPass);
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirm.setVisible(false);
				for(Component c : confirm.getComponents())
					if(c instanceof JTextField)
						((JTextField) c).setText("");
				details.setVisible(true);
			}
		});
		back.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		back.setBounds(131, 385, 75, 25);
		confirm.add(back);
		
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitFrame();
			}
		});
		exit.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		exit.setBounds(216, 385, 66, 25);
		confirm.add(exit);
		
		finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalValidate();
			}
		});
		finish.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		finish.setBounds(292, 385, 80, 25);
		confirm.add(finish);
		
		unameStatus = new JLabel("");
		unameStatus.setFont(new Font("Source Sans Pro Semibold", Font.PLAIN, 12));
		confirm.add(unameStatus);
		unameStatus.setVisible(false);
		unameStatus.setBounds(48, 108, 178, 13);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(91, 226, 319, 2);
		confirm.add(separator);
		
		lblAdminAuthuntication = new JLabel("Admin Authuntication");
		lblAdminAuthuntication.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminAuthuntication.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblAdminAuthuntication.setBounds(150, 228, 192, 25);
		confirm.add(lblAdminAuthuntication);
		
		admin = new JLabel("");
		admin.setFont(new Font("Tahoma", Font.PLAIN, 9));
		admin.setHorizontalAlignment(SwingConstants.CENTER);
		admin.setBounds(20, 250, 459, 13);
		confirm.add(admin);
		
		details = new JPanel();
		details.setOpaque(false);
		details.setBounds(0, 49, 500, 451);
		getContentPane().add(details);
		details.setLayout(null);
		
		JLabel fn = new JLabel("First Name : *");
		fn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		fn.setBounds(26, 50, 101, 19);
		details.add(fn);
		
		JLabel mn = new JLabel("Middle Name :");
		mn.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		mn.setBounds(26, 79, 101, 19);
		details.add(mn);
		
		JLabel ln = new JLabel("Last Name :");
		ln.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		ln.setBounds(26, 112, 101, 19);
		details.add(ln);
		
		JLabel birth = new JLabel("Date of Birth : *");
		birth.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		birth.setBounds(26, 154, 121, 19);
		details.add(birth);
		
		JLabel gen = new JLabel("Gender : *");
		gen.setName("gen");
		gen.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		gen.setBounds(26, 198, 101, 19);
		details.add(gen);
		
		JLabel em = new JLabel("E-mail : *");
		em.setName("email");
		em.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		em.setBounds(26, 253, 101, 19);
		details.add(em);
		
		JLabel ph = new JLabel("Phone number : *");
		ph.setName("");
		ph.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		ph.setBounds(26, 309, 121, 19);
		details.add(ph);
		
		fname = new JTextField();
		fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					validateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(fname.getText().length()>=50) e.consume();
			}
		});
		fname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fname.setBounds(126, 50, 150, 20);
		details.add(fname);
		fname.setColumns(10);
		
		mname = new JTextField();
		mname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					validateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(mname.getText().length()>=(50-fname.getText().length())) e.consume();
			}
		});
		mname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mname.setColumns(10);
		mname.setBounds(126, 79, 150, 20);
		details.add(mname);
		
		lname = new JTextField();
		lname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					validateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(mname.getText().length()>=(50-fname.getText().length()-mname.getText().length())) e.consume();
			}
		});
		lname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lname.setColumns(10);
		lname.setBounds(126, 108, 150, 20);
		details.add(lname);
		
		date = new JDateChooser();
		date.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					validateDetails();
			}
		});
		date.setBounds(144,154,114,20);
		details.add(date);
		
		male = new JRadioButton("Male");
		male.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				male.setSelected(true);
			}
		});
		male.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
					male.setSelected(true);
				}
				if(e.getKeyCode()==KeyEvent.VK_TAB)
					female.requestFocus();
			}
		});
		male.setFont(new Font("Tahoma", Font.PLAIN, 12));
		male.setOpaque(false);
		male.setBounds(26, 218, 53, 21);
		details.add(male);
		bg.add(male);	
		
		female = new JRadioButton("Female");
		female.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				female.setSelected(true);
			}
		});
		female.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
					female.setSelected(true);
				}
				if(e.getKeyCode()==KeyEvent.VK_TAB)
					other.requestFocus();
			}
		});
		female.setFont(new Font("Tahoma", Font.PLAIN, 12));
		female.setActionCommand("Female");
		female.setOpaque(false);
		female.setBounds(94, 218, 67, 21);
		details.add(female);
		bg.add(female);
		
		other = new JRadioButton("Other");
		other.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				other.setSelected(true);
			}
		});
		other.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					validateDetails();
					other.setSelected(true);
				}
				if(e.getKeyCode()==KeyEvent.VK_TAB)
					email.requestFocus();
			}
		});
		other.setOpaque(false);
		other.setFont(new Font("Tahoma", Font.PLAIN, 12));
		other.setBounds(179, 218, 67, 21);
		details.add(other);
		bg.add(other);
		
		email = new JTextField();
		email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					validateDetails();
			}
			public void keyTyped(KeyEvent e) {
				if(email.getText().length()>=30) e.consume();
			}
		});
		email.setFont(new Font("Tahoma", Font.PLAIN, 12));
		email.setBounds(26, 274, 204, 20);
		details.add(email);
		email.setColumns(10);
		
		phone = new JTextField();
		phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					next.requestFocus();
					validateDetails();
				}
			}
			public void keyTyped(KeyEvent e) {
				if(phone.getText().length()>=10) e.consume();
			}
		});
		phone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		phone.setBounds(26, 329, 121, 20);
		details.add(phone);
		phone.setColumns(10);
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateDetails();
			}
		});
		next.setBounds(212, 380, 85, 25);
		details.add(next);
		next.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame.setVisible(true);
				dispose();
			}
		});
		cancel.setBounds(307, 380, 85, 25);
		details.add(cancel);
		cancel.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		
		reset = new JButton("Reset");
		reset.setFont(new Font("MS UI Gothic", Font.BOLD, 15));
		reset.setBounds(117, 380, 85, 25);
		details.add(reset);
		
		lblFieldMarkedWith = new JLabel("Fields marked with * are mandatory.");
		lblFieldMarkedWith.setForeground(Color.RED);
		lblFieldMarkedWith.setBounds(26, 359, 282, 13);
		details.add(lblFieldMarkedWith);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				for(Component c : details.getComponents()) {
					if(c instanceof JTextField)
						((JTextField)c).setText("");
					else if(c instanceof JDateChooser)
						((JDateChooser)c).setDate(null);
				}
				bg.clearSelection();
			}
		});
		
		JLabel title1 = new JLabel("New User Registration :");
		title1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		title1.setBounds(26, 72, 185, 19);
		getContentPane().add(title1);
		
		JLabel register_icon = new JLabel("");
		register_icon.setBounds(300, 50, 200, 200);
		getContentPane().add(register_icon);
		register_icon.setIcon(new ImageIcon(new ImageIcon("image_resources/register.png").getImage().getScaledInstance(register_icon.getWidth(), register_icon.getHeight(), Image.SCALE_SMOOTH)));
	}
	
	private void validateDetails()
	{
		if(!Validation.successfulValidateDetails(fname, mname, lname, date, getDate(), getGender(), email, phone))
		{
			return;
		}
		result=new StringBuilder();
		result.append("'").append(getFullName()).append("',");
		result.append("'").append(getDate()).append("',");
		result.append("'").append(getGender()).append("',");
		result.append("'").append(email.getText()).append("',");
		result.append("'").append(phone.getText()).append("',");
		details.setVisible(false);
		confirm.setVisible(true);
		
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT name,phone,email FROM UserAccounts WHERE admin=1;");
			rs.next();
			admin.setText("Contact : "+rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
		
	private void finalValidate()
	{
		if(!Validation.usernameDomainSatisfied(username.getText()))
		{
			username.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Username can contailn alphabets, digits and _@.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return;
		}
		username.setBackground(Color.white);
		
		String result1="",result2="";
		if(validatedUsername()) {
			unameStatus.setVisible(false);
			result1="INSERT INTO UserAccounts VALUES ('"+username.getText()+"',";
			pass1.requestFocus();
		} 
		else
			return;
			
		if(passwordValidate())
			result2=String.valueOf(pass1.getPassword());
		else
			return;
		
		if(authorizationSuccessful())
		{
			result.insert(0, result1);
			result.append("'").append(result2).append("',");
			try {
				stmt=con.createStatement();
				rs=stmt.executeQuery("SELECT COUNT(*) FROM UserAccounts;");
				rs.next();
				if(rs.getInt(1)==0)
					result.append("1");
				else
					result.append("0");
				result.append(");");
				int i=stmt.executeUpdate(result.toString());
				if(i>0)
				{
					JOptionPane.showMessageDialog(this, "You have registed successfully.\nPlease login now!", "Successful Registration", JOptionPane.INFORMATION_MESSAGE);
					loginFrame.setVisible(true);
					dispose();
				}
			}
			catch(SQLException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage()	, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	private String getFullName()
	{
		String name="";
		if(!fname.getText().equals(""))
				name+=fname.getText();
		if(!mname.getText().equals(""))
			name+=" "+mname.getText();
		if(!lname.getText().equals(""))
			name+=" "+lname.getText();
		return name;
	}
	
	private String getGender()
	{
		if(male.isSelected())
			return "Male";
		else if(female.isSelected())
			return "Female";
		else if(other.isSelected())
			return "Other";
		else return "";
	}
	
	private String getDate()
	{
		try {
			StringBuilder date1=new StringBuilder(date.getDate().toString());
			String date2="";
			date2+=date1.substring(8, 10)+"-";
			date2+=date1.substring(4, 7).toUpperCase()+"-";
			date2+=date1.substring(24);
			return date2;
		}catch(Exception e) {return "";}
	}
	
	private void exitFrame()
	{
		System.exit(1);
	}
	
	private boolean validatedUsername() {
		int count=-1;
		if(username.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Username is required.", "Username Required", JOptionPane.INFORMATION_MESSAGE);
			username.requestFocus();
			return false;
		}
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT COUNT(username) FROM UserAccounts WHERE username='"+username.getText()+"';");
			rs.next();
			count=rs.getInt(1);
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		if(count>0)
		{
			unameStatus.setText("Username already exists!");
			unameStatus.setVisible(true);
			return false;
		}
		return true;
	}
	
	private boolean passwordValidate() 
	{
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

	private boolean authorizationSuccessful()
	{
		if(aUser.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please ask admin to enter the correct username.", "Invalid Username", JOptionPane.INFORMATION_MESSAGE);
			aUser.requestFocus();
			return false;
		}
		if(String.valueOf(aPass.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter correct admin password", "Wrong password", JOptionPane.INFORMATION_MESSAGE);
			finish.requestFocus();
			return false;
		}
		int totalUsers=-1;
		int admin=-1;
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT COUNT(*) FROM UserAccounts;");
			rs.next();
			totalUsers=rs.getInt(1);
			rs=stmt.executeQuery("SELECT COUNT(username) FROM UserAccounts WHERE username='"+aUser.getText()+"' AND password='"+String.valueOf(aPass.getPassword())+"';");
			rs.next();
			admin=rs.getInt(1);
		}
		catch(SQLException e) { e.printStackTrace(); }
		if(totalUsers==0 || (totalUsers>0 && admin>0))
			return true;
		else {
			authorize++;
			JOptionPane.showMessageDialog(this, "Wrong authorization credentials.");
			if(authorize>3)
			{
				authorize=0;
				JOptionPane.showMessageDialog(this, "Invalid admin credentials entered more than 3 times. Please try again!","",JOptionPane.ERROR_MESSAGE);
				loginFrame.setVisible(true);
				dispose();
			}
		}
		return false;
	}
}
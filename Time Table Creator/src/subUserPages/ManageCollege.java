package subUserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import homePage.Login;
import tools.Validation;
import dialogs.*;
import userPage.UserMainPage;

import java.sql.*;
import javax.swing.border.MatteBorder;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class ManageCollege extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	@SuppressWarnings("unused")
	private static String user;
	private static JComboBox<String> comboBox;
	private static String[] code,name;
	private static JPanel panel,panelEdit,panelDept;
	private JTextField codeT,fnameT,snameT,addressT,cityT,pinT,phoneT,emailT,dept_id,dept_name;
	private JButton edit,add,delete,update1;
	
	public ManageCollege(String user) {
		ManageCollege.user=user;
		addPropertyChangeListener("visible",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if((boolean)evt.getNewValue()) {
					updateComboBox();
					comboBox.setSelectedIndex(0);
				}
			}
		});
		con=Login.getCon();
		
		setBackground(new Color(255, 250, 240));
		setSize(1563,797);
		setLayout(null);
		
		JLabel title = new JLabel("Manage College");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 40, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(603, 81, 191, 2);
		add(separator);
		
		JLabel lblSelectYourCollege = new JLabel("Select your college :");
		lblSelectYourCollege.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectYourCollege.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectYourCollege.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectYourCollege.setBounds(245, 93, 148, 26);
		add(lblSelectYourCollege);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()>0) {
					panel.setVisible(true);
					panel.requestFocus();
					panelEdit.setVisible(false);
					panelDept.setVisible(false);
				}
				else
					panel.setVisible(false);
			}
		});
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(403, 93, 758, 28);
		add(comboBox);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(200, 136, 1044, 2);
		add(separator_1);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(141, 164, 1179, 584);
		add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		edit = new JButton("Edit College Details");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete.setEnabled(false);
				panelEdit.setVisible(true);
				panelDept.setVisible(false);
				getCollegeDetails();
			}
		});
		edit.setHorizontalTextPosition(SwingConstants.CENTER);
		edit.setForeground(Color.WHITE);
		edit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		edit.setFocusTraversalKeysEnabled(false);
		edit.setFocusPainted(false);
		edit.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		edit.setBackground(new Color(51, 0, 255));
		edit.setBounds(86, 10, 207, 40);
		panel.add(edit);
		
		add = new JButton("Add / Manage Departments");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete.setEnabled(false);
				panelEdit.setVisible(false);
				panelDept.setVisible(true);
				panelDept.setVisible(true);
			}
		});
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add.setFocusTraversalKeysEnabled(false);
		add.setFocusPainted(false);
		add.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		add.setBackground(new Color(50, 205, 50));
		add.setBounds(303, 10, 297, 40);
		panel.add(add);
		
		delete = new JButton("Delete College");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCollege();
			}
		});
		delete.setHorizontalTextPosition(SwingConstants.CENTER);
		delete.setForeground(Color.WHITE);
		delete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		delete.setFocusTraversalKeysEnabled(false);
		delete.setFocusPainted(false);
		delete.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		delete.setBackground(Color.RED);
		delete.setBounds(796, 10, 252, 40);
		panel.add(delete);
		
		panelDept = new JPanel();
		panelDept.setOpaque(false);
		panelDept.setBackground(Color.WHITE);
		panelDept.setLayout(null);
		panelDept.setBounds(25, 78, 1063, 496);
		panel.add(panelDept);
		panelDept.setVisible(false);
		
		JLabel lblAddNewDepartment = new JLabel("Add New Department");
		lblAddNewDepartment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewDepartment.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		lblAddNewDepartment.setBounds(297, 10, 467, 43);
		panelDept.add(lblAddNewDepartment);
		
		JLabel lblCollegeId = new JLabel("Department ID : *");
		lblCollegeId.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblCollegeId.setBounds(246, 67, 150, 19);
		panelDept.add(lblCollegeId);
		
		dept_id = new JTextField();
		dept_id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(dept_id.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					dept_name.requestFocus();
			}
		});
		dept_id.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dept_id.setColumns(10);
		dept_id.setBounds(246, 92, 150, 25);
		panelDept.add(dept_id);
		
		JLabel lblNameOfThe = new JLabel("Name of the Department / Branch : *");
		lblNameOfThe.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblNameOfThe.setBounds(246, 138, 311, 19);
		panelDept.add(lblNameOfThe);
		
		dept_name = new JTextField();
		dept_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(dept_name.getText().length()>=50) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addDepartment();
			}
		});
		dept_name.setHorizontalAlignment(SwingConstants.CENTER);
		dept_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		dept_name.setColumns(10);
		dept_name.setBounds(246, 167, 580, 25);
		panelDept.add(dept_name);
		
		update1 = new JButton("Add");
		update1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDepartment();
			}
		});
		update1.setHorizontalTextPosition(SwingConstants.CENTER);
		update1.setForeground(Color.WHITE);
		update1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		update1.setFocusTraversalKeysEnabled(false);
		update1.setFocusPainted(false);
		update1.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		update1.setBackground(new Color(51, 0, 255));
		update1.setBounds(532, 236, 126, 32);
		panelDept.add(update1);
		
		JButton cancel1 = new JButton("Cancel");
		cancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelDept.setVisible(false);
				delete.setEnabled(true);
			}
		});
		cancel1.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel1.setForeground(Color.WHITE);
		cancel1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancel1.setFocusTraversalKeysEnabled(false);
		cancel1.setFocusPainted(false);
		cancel1.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		cancel1.setBackground(Color.ORANGE);
		cancel1.setBounds(396, 236, 126, 32);
		panelDept.add(cancel1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(150, 290, 741, 2);
		panelDept.add(separator_2);
		
		JLabel lblManageSectionsAnd = new JLabel("Manage Departments, Sections and Semesters");
		lblManageSectionsAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblManageSectionsAnd.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		lblManageSectionsAnd.setBounds(297, 307, 467, 43);
		panelDept.add(lblManageSectionsAnd);
		
		JButton btnManageSemester = new JButton("Manage Department & Semester");
		btnManageSemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageSemester(UserMainPage.frame,code[comboBox.getSelectedIndex()],user);
			}
		});
		btnManageSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		btnManageSemester.setForeground(Color.WHITE);
		btnManageSemester.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnManageSemester.setFocusTraversalKeysEnabled(false);
		btnManageSemester.setFocusPainted(false);
		btnManageSemester.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnManageSemester.setBackground(new Color(128, 0, 0));
		btnManageSemester.setBounds(307, 360, 296, 40);
		panelDept.add(btnManageSemester);
		
		JButton btnManageSection = new JButton("Manage Section");
		btnManageSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageSection(UserMainPage.frame,code[comboBox.getSelectedIndex()]);
			}
		});
		btnManageSection.setHorizontalTextPosition(SwingConstants.CENTER);
		btnManageSection.setForeground(Color.WHITE);
		btnManageSection.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnManageSection.setFocusTraversalKeysEnabled(false);
		btnManageSection.setFocusPainted(false);
		btnManageSection.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnManageSection.setBackground(new Color(128, 0, 0));
		btnManageSection.setBounds(613, 360, 176, 40);
		panelDept.add(btnManageSection);
		
		JLabel lblWarningCollege = new JLabel("Warning : Department ID or name can't be edited in future.");
		lblWarningCollege.setForeground(Color.RED);
		lblWarningCollege.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWarningCollege.setBounds(246, 202, 437, 24);
		panelDept.add(lblWarningCollege);
		
		panelEdit = new JPanel();
		panelEdit.setLayout(null);
		panelEdit.setOpaque(false);
		panelEdit.setBounds(25, 78, 1063, 496);
		panel.add(panelEdit);
		panelEdit.setVisible(false);
		
		JLabel lblCollegeCode = new JLabel("College code : ");
		lblCollegeCode.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblCollegeCode.setBounds(0, 3, 121, 19);
		panelEdit.add(lblCollegeCode);
		
		codeT = new JTextField();
		codeT.setSelectionColor(new Color(0, 0, 0));
		codeT.setForeground(new Color(0, 0, 0));
		codeT.setEnabled(false);
		codeT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		codeT.setColumns(10);
		codeT.setBounds(117, 0, 150, 25);
		panelEdit.add(codeT);
		
		JLabel lblFullNameOf = new JLabel("Full name of College : ");
		lblFullNameOf.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblFullNameOf.setBounds(0, 58, 168, 19);
		panelEdit.add(lblFullNameOf);
		
		fnameT = new JTextField();
		fnameT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(fnameT.getText().length()>=100) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		fnameT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fnameT.setColumns(10);
		fnameT.setBounds(178, 55, 878, 25);
		panelEdit.add(fnameT);
		
		JLabel lblShortNameOf = new JLabel("Short name of College : ");
		lblShortNameOf.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblShortNameOf.setBounds(0, 90, 177, 19);
		panelEdit.add(lblShortNameOf);
		
		snameT = new JTextField();
		snameT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(snameT.getText().length()>=50) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		snameT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		snameT.setColumns(10);
		snameT.setBounds(188, 87, 865, 25);
		panelEdit.add(snameT);
		
		JLabel label_3 = new JLabel("Address :");
		label_3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_3.setBounds(0, 145, 73, 19);
		panelEdit.add(label_3);
		
		addressT = new JTextField();
		addressT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(addressT.getText().length()>=120) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		addressT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressT.setColumns(10);
		addressT.setBounds(0, 163, 977, 25);
		panelEdit.add(addressT);
		
		JLabel label_4 = new JLabel("Main City :");
		label_4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_4.setBounds(0, 254, 73, 19);
		panelEdit.add(label_4);
		
		cityT = new JTextField();
		cityT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(cityT.getText().length()>=20) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		cityT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityT.setColumns(10);
		cityT.setBounds(79, 251, 161, 25);
		panelEdit.add(cityT);
		
		JLabel label_5 = new JLabel("Pincode :");
		label_5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_5.setBounds(0, 215, 65, 19);
		panelEdit.add(label_5);
		
		pinT = new JTextField();
		pinT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(pinT.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		pinT.setHorizontalAlignment(SwingConstants.CENTER);
		pinT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pinT.setColumns(10);
		pinT.setBounds(75, 212, 96, 25);
		panelEdit.add(pinT);
		
		JLabel label_6 = new JLabel("Contact number :");
		label_6.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_6.setBounds(0, 316, 126, 19);
		panelEdit.add(label_6);
		
		phoneT = new JTextField();
		phoneT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(phoneT.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					updateCollege();
			}
		});
		phoneT.setHorizontalAlignment(SwingConstants.CENTER);
		phoneT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneT.setColumns(10);
		phoneT.setBounds(127, 313, 96, 25);
		panelEdit.add(phoneT);
		
		JLabel label_7 = new JLabel("Email Address :");
		label_7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_7.setBounds(0, 348, 110, 19);
		panelEdit.add(label_7);
		
		emailT = new JTextField();
		emailT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(emailT.getText().length()>=30) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					updateCollege();
				}
			}
		});
		emailT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailT.setColumns(10);
		emailT.setBounds(109, 345, 233, 25);
		panelEdit.add(emailT);
		
		JButton update = new JButton("Udpate");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCollege();
				delete.setEnabled(true);
			}
		});
		update.setHorizontalTextPosition(SwingConstants.CENTER);
		update.setForeground(Color.WHITE);
		update.setFont(new Font("Tahoma", Font.PLAIN, 18));
		update.setFocusTraversalKeysEnabled(false);
		update.setFocusPainted(false);
		update.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		update.setBackground(new Color(51, 0, 255));
		update.setBounds(537, 454, 126, 32);
		panelEdit.add(update);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEdit.setVisible(false);
				delete.setEnabled(true);
			}
		});
		cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel.setForeground(Color.WHITE);
		cancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancel.setFocusTraversalKeysEnabled(false);
		cancel.setFocusPainted(false);
		cancel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		cancel.setBackground(Color.ORANGE);
		cancel.setBounds(401, 454, 126, 32);
		panelEdit.add(cancel);
		
		JLabel lblIfAnyField = new JLabel("If any field is left blank, then value of that field will be ignored during updation.");
		lblIfAnyField.setForeground(Color.BLACK);
		lblIfAnyField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIfAnyField.setBounds(0, 414, 605, 19);
		panelEdit.add(lblIfAnyField);
		
		JButton addClass = new JButton("Add Classrooms");
		addClass.setBounds(610, 10, 176, 40);
		panel.add(addClass);
		addClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageClassroom(UserMainPage.frame,code[comboBox.getSelectedIndex()]);
			}
		});
		addClass.setHorizontalTextPosition(SwingConstants.CENTER);
		addClass.setForeground(Color.WHITE);
		addClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addClass.setFocusTraversalKeysEnabled(false);
		addClass.setFocusPainted(false);
		addClass.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		addClass.setBackground(new Color(128, 0, 0));
		
		updateComboBox();
	}
	
	public void setVisible(boolean b) {
		boolean visible=isVisible();
		super.setVisible(b);
		firePropertyChange("visible",visible,b);
	}
	
	public static void updateComboBox()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query="SELECT * FROM college_view ORDER BY fname;";
			rs=stmt.executeQuery(query);
			while(rs.next()) 
				count++;
			code=new String[count+1];
			name=new String[count+1];
			rs.beforeFirst();
			int i=1;
			name[0]="--- Select your college --- ";
			while(rs.next()) {
				code[i]=rs.getString(1);
				name[i]=rs.getString(2)+" - "+rs.getString(1);
				i++;
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(name));
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public void deleteCollege()
	{
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete college "+name[index]
					+ "? \nDeleting will remove all the college details like departments, courses and staff details from the database!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			if((stmt.executeUpdate("DELETE FROM College WHERE code='"+code[index]+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "College '"+name[index]+"' deleted successfully.");
				updateComboBox();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the class entry.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getCollegeDetails()
	{
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			String query="SELECT * FROM College WHERE code='"+code[index]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			panelEdit.setVisible(true);
			codeT.setText(rs.getString(1));
			fnameT.setText(rs.getString(2));
			snameT.setText(rs.getString(3));
			addressT.setText(rs.getString(4));
			cityT.setText(rs.getString(5));
			pinT.setText(rs.getString(6));
			emailT.setText(rs.getString(7));
			phoneT.setText(rs.getString(8));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public boolean validateDomain()
	{
		if(!fnameT.getText().equals("") && !Validation.nameDomainSatisfied(fnameT.getText()))
		{
			fnameT.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		fnameT.setBackground(Color.white);
		if(!snameT.getText().equals("") && !Validation.nameDomainSatisfied(snameT.getText()))
		{
			snameT.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		snameT.setBackground(Color.white);
		if(!cityT.getText().equals("") && !Validation.nameDomainSatisfied(cityT.getText()))
		{
			cityT.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "City name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		cityT.setBackground(Color.white);
		
		if(phoneT.getText().length()>0 && (phoneT.getText().length()==6 && !Validation.phoneDomainSatisfied(phoneT.getText(),0)) || (phoneT.getText().length()==10 && !Validation.phoneDomainSatisfied(phoneT.getText(),1)))
		{
			phoneT.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Contact number should have 6 or 10 digits\n", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		phoneT.setBackground(Color.white);
		
		if(!emailT.getText().equals("") && !Validation.emailDomainSatisfied(emailT.getText()))
		{
			emailT.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Invalid email\nExample : example@domain.com", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		emailT.setBackground(Color.white);
		return true;
	}
	
	public void updateCollege()
	{
		codeT.setBackground(Color.white);
		fnameT.setBackground(Color.white);
		snameT.setBackground(Color.white);
		
		
		if(!validateDomain())
			return;
		
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			String query="SELECT * FROM College WHERE code='"+code[index]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			String update="UPDATE College SET code='"+rs.getString(1)+"'";
			
			update+=", fname='";
			if(!fnameT.getText().equals("")) update+=fnameT.getText()+"'"; else update+=rs.getString(2)+"'";
			update+=", sname='";
			if(!snameT.getText().equals("")) update+=snameT.getText()+"'"; else update+=rs.getString(3)+"'";
			update+=", address='";
			if(!addressT.getText().equals("")) update+=addressT.getText()+"'"; else update+=rs.getString(4)+"'";
			update+=", city='";
			if(!cityT.getText().equals("")) update+=cityT.getText()+"'"; else update+=rs.getString(5)+"'";
			update+=", pincode='";
			if(!pinT.getText().equals("")) update+=pinT.getText()+"'"; else update+=rs.getString(6)+"'";
			update+=", email='";
			if(!emailT.getText().equals("")) update+=emailT.getText()+"'"; else update+=rs.getString(7)+"'";
			update+=", phone='";
			if(!phoneT.getText().equals("")) update+=phoneT.getText()+"'"; else update+=rs.getString(8)+"'";
			update+=" WHERE code='"+rs.getString(1)+"';";
	
			stmt.executeUpdate(update);
			JOptionPane.showMessageDialog(null,"College details updated successfully.");
			panelEdit.setVisible(false);
			delete.setEnabled(true);
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to insert college detail to database.");
			e.printStackTrace();
		}
	}
	
	public void addDepartment() {
		dept_id.setBackground(Color.white);
		dept_name.setBackground(Color.white);
		boolean flag=false;
		if(dept_id.getText().equals("")) {
			dept_id.setBackground(new Color(255,255,200));
			dept_id.requestFocus();
			flag=true;
		} else if(dept_name.getText().equals("")) {
			dept_name.setBackground(new Color(255,255,200));
			dept_name.requestFocus();
			flag=true;
		}
		if(flag)
		{
			JOptionPane.showMessageDialog(this, "Please fill all the fields.", "Time Table Creator",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!Validation.uniqueIdDomainSatisfied(dept_id.getText()))
		{
			dept_id.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Department ID can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return;
		}
		dept_id.setBackground(Color.white);
		
		if(!Validation.nameDomainSatisfied(dept_name.getText()))
		{
			dept_name.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return;
		}
		dept_name.setBackground(Color.white);
		
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT dept_id FROM Department WHERE dept_id='"+dept_id.getText()+"' AND code='"+code[comboBox.getSelectedIndex()]+"';");
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "Department with this Department ID already exists. Please use other department ID.");
				return;
			}
			String insert="INSERT INTO Department VALUES('"+dept_id.getText()+"','"+dept_name.getText()+"','','"+code[comboBox.getSelectedIndex()]+"')";
			int i=stmt.executeUpdate(insert);
			if(i>0) {
				JOptionPane.showMessageDialog(null, "Department added succesfully.");
				dept_id.setText("");
				dept_name.setText("");
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to insert college detail to database.");
			e.printStackTrace();
		}
		
	}
}

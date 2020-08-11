package subUserPages;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import homePage.Login;
import tools.Validation;

import java.sql.*;
import java.util.Date;


public class ManageStaff extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static String[] code,name,dept_id,dept_name,staff_id,staff_name;
	
	private static JComboBox<String> comboBox,comboBox2,comboBox3;
	private static JDateChooser dob;
	private JTextField staffid,staffname,email,phone;
	private static JPanel panel,panelEdit;
	private JButton edit,delete;

	
	public ManageStaff() {
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
		
		JLabel title = new JLabel("Manage Staff Details");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 40, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(580, 81, 233, 2);
		add(separator);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateComboBox2();
				panel.requestFocus();
			}
		});
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(403, 93, 758, 28);
		add(comboBox);
		
		JLabel label = new JLabel("Select your college :");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Georgia", Font.PLAIN, 16));
		label.setBounds(245, 93, 148, 26);
		add(label);
		
		JLabel lblSelectYourDepartment = new JLabel("Select department :");
		lblSelectYourDepartment.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectYourDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectYourDepartment.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectYourDepartment.setBounds(245, 130, 148, 26);
		add(lblSelectYourDepartment);
		
		comboBox2 = new JComboBox<String>();
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.requestFocus();
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0) {
					panel.setVisible(true);
					updateComboBox3();
				}
				else
					panel.setVisible(false);
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(403, 130, 758, 28);
		add(comboBox2);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(141, 164, 1179, 584);
		add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		edit = new JButton("Edit Staff Details");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox3.getSelectedIndex()<=0)
				{
					JOptionPane.showMessageDialog(null, "Please select staff from list.");
					return;
				}
				delete.setEnabled(false);
				getStaffDetails();
			}
		});
		edit.setHorizontalTextPosition(SwingConstants.CENTER);
		edit.setForeground(Color.WHITE);
		edit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		edit.setFocusTraversalKeysEnabled(false);
		edit.setFocusPainted(false);
		edit.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		edit.setBackground(new Color(51, 0, 255));
		edit.setBounds(290, 68, 252, 40);
		panel.add(edit);
		
		delete = new JButton("Delete Staff");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteStaff();
			}
		});
		delete.setHorizontalTextPosition(SwingConstants.CENTER);
		delete.setForeground(Color.WHITE);
		delete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		delete.setFocusTraversalKeysEnabled(false);
		delete.setFocusPainted(false);
		delete.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		delete.setBackground(Color.RED);
		delete.setBounds(552, 68, 252, 40);
		panel.add(delete);
		
		JLabel lblSelectStaff = new JLabel("Select staff :");
		lblSelectStaff.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectStaff.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectStaff.setBounds(164, 10, 105, 26);
		panel.add(lblSelectStaff);
		
		comboBox3 = new JComboBox<String>();
		comboBox3.setBackground(Color.WHITE);
		comboBox3.setBounds(267, 11, 620, 28);
		panel.add(comboBox3);
		
		panelEdit = new JPanel();
		panelEdit.setLayout(null);
		panelEdit.setOpaque(false);
		panelEdit.setBounds(23, 145, 1063, 296);
		panel.add(panelEdit);
		panelEdit.setVisible(false);
		
		
		
		JButton update = new JButton("Udpate");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStaff();
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
		update.setBounds(536, 254, 126, 32);
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
		cancel.setBounds(400, 254, 126, 32);
		panelEdit.add(cancel);
		
		JLabel staffidlabel = new JLabel("Staff ID : ");
		staffidlabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		staffidlabel.setBounds(133, 6, 87, 19);
		panelEdit.add(staffidlabel);
		
		staffid = new JTextField();
		staffid.setEnabled(false);
		staffid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		staffid.setColumns(10);
		staffid.setBounds(212, 3, 150, 25);
		panelEdit.add(staffid);
		
		JLabel lblFullName = new JLabel("Full Name : ");
		lblFullName.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblFullName.setBounds(133, 44, 104, 19);
		panelEdit.add(lblFullName);
		
		staffname= new JTextField();
		staffname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		staffname.setColumns(10);
		staffname.setBounds(235, 41, 698, 25);
		panelEdit.add(staffname);
		
		JLabel label_3 = new JLabel("Date of Birth : ");
		label_3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_3.setBounds(133, 86, 121, 21);
		panelEdit.add(label_3);
		
		dob = new JDateChooser();
		dob.setBounds(261, 84, 114, 25);
		panelEdit.add(dob);
		
		JLabel label_4 = new JLabel("Contact number :");
		label_4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_4.setBounds(133, 135, 126, 19);
		panelEdit.add(label_4);
		
		phone = new JTextField();
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		phone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phone.setColumns(10);
		phone.setBounds(260, 132, 96, 25);
		panelEdit.add(phone);
		
		JLabel label_5 = new JLabel("Email Address :");
		label_5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_5.setBounds(379, 135, 110, 19);
		panelEdit.add(label_5);
		
		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		email.setColumns(10);
		email.setBounds(488, 132, 233, 25);
		panelEdit.add(email);
		
		JLabel label_1 = new JLabel("If any field is left blank, then value of that field will be ignored during updation.");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(133, 180, 605, 19);
		panelEdit.add(label_1);
		
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
	
	public static void updateComboBox2()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(comboBox.getSelectedIndex()<=0)
			{
				String str1[]= {"--- Select your College --- "};
				comboBox2.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT dept_id,dept_name FROM Department WHERE code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY dept_id;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				dept_id=new String[count+1];
				dept_name=new String[count+1];
				rs.beforeFirst();
				int i=1;
				dept_name[0]="--- Select your semester --- ";
				while(rs.next()) {
					dept_id[i]=rs.getString(1);
					dept_name[i]=rs.getString(2)+" - "+rs.getString(1);
					i++;
				}
				comboBox2.setModel(new DefaultComboBoxModel<String>(dept_name));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public static void updateComboBox3()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(comboBox.getSelectedIndex()<=0)
			{
				String str1[]= {"--- Select staff name --- "};
				comboBox2.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT staff_id,staff_name FROM Staff WHERE dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY staff_name;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				staff_id=new String[count+1];
				staff_name=new String[count+1];
				rs.beforeFirst();
				int i=1;
				staff_name[0]="--- Select staff name --- ";
				while(rs.next()) {
					staff_id[i]=rs.getString(1);
					staff_name[i]=rs.getString(2)+" - "+rs.getString(1);
					i++;
				}
				comboBox3.setModel(new DefaultComboBoxModel<String>(staff_name));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public void deleteStaff()
	{
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete staff "+staff_name[index]
					+ "? \nDeleting will remove details of staff from selected department and college!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			stmt.executeUpdate("DELETE FROM Theory WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND code='"+code[index]+"';");
			stmt.executeUpdate("DELETE FROM Lab WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND code='"+code[index]+"';");
			stmt.executeUpdate("DELETE FROM Elective WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND code='"+code[index]+"';");
			stmt.executeUpdate("DELETE FROM Schedule WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND code='"+code[index]+"';");
			if((stmt.executeUpdate("DELETE FROM Staff WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[index]+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Details of staff '"+staff_name[comboBox3.getSelectedIndex()]+"' deleted successfully.");
				updateComboBox3();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the staff details.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getStaffDetails()
	{
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			String query="SELECT * FROM Staff WHERE staff_id='"+staff_id[comboBox3.getSelectedIndex()]+"' AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[index]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			panelEdit.setVisible(true);
			staffid.setText(rs.getString(1));
			staffname.setText(rs.getString(2));
			//dob.setText(rs.getString(3));
			phone.setText(rs.getString(5));
			email.setText(rs.getString(6));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public boolean validateDomain()
	{
		if(!staffid.getText().equals("") && !Validation.uniqueIdDomainSatisfied(staffid.getText()))
		{
			staffid.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Staff ID can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		staffid.setBackground(Color.white);
		
		if(!staffname.getText().equals("") && !Validation.nameDomainSatisfied(staffname.getText()))
		{
			staffname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		staffname.setBackground(Color.white);
		
		if(phone.getText().length()>0 && !Validation.phoneDomainSatisfied(phone.getText(),1))
		{
			phone.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Contact number should have 10 digits\n", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		phone.setBackground(Color.white);
		
		if(!email.getText().equals("") && !Validation.emailDomainSatisfied(email.getText()))
		{
			email.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Invalid email\nExample : example@domain.com", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		email.setBackground(Color.white);
		return true;
	}
	
	public void updateStaff()
	{
		if(!validateDomain())
			return;
		try {
			stmt=con.createStatement();
			int index=comboBox.getSelectedIndex();
			String query="SELECT * FROM Staff WHERE staff_id='"+staffid.getText()+"' AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[index]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			String update="UPDATE Staff SET staff_id='"+rs.getString(1)+"'";
			
			update+=", staff_name='";
			if(!staffname.getText().equals("")) update+=staffname.getText()+"'"; else update+=rs.getString(2)+"'";
			update+=", dob='";
			if(!getDate(0).equals("")) update+=getDate(0)+"'"; else update+=rs.getString(3)+"'";
			update+=", phone='";
			if(!phone.getText().equals("")) update+=phone.getText()+"'"; else update+=rs.getString(5)+"'";
			update+=", email='";
			if(!email.getText().equals("")) update+=email.getText()+"'"; else update+=rs.getString(6)+"'";
			update+=" WHERE staff_id='"+rs.getString(1)+"';";
			stmt.executeUpdate(update);
			JOptionPane.showMessageDialog(null,"Staff details updated successfully.");
			panelEdit.setVisible(false);
			delete.setEnabled(true);
			updateComboBox3();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to udpate course detail to database.");
			e.printStackTrace();
		}
	}
	
	public String getDate(int i)
	{
		try {
			StringBuilder date1=new StringBuilder("");
			if(i==0)
				date1=new StringBuilder(dob.getDate().toString());
			else if(i==1) {
				Date today=new Date();
				date1=new StringBuilder(today.toString());
			}
			String date2="";
			date2+=date1.substring(8, 10)+"-";
			date2+=date1.substring(4, 7).toUpperCase()+"-";
			date2+=date1.substring(24);
			return date2;
		}catch(Exception e) {return "";}
	}
}

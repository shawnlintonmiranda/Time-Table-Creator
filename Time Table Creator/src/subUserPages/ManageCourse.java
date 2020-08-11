package subUserPages;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import homePage.Login;
import tools.Validation;

import java.sql.*;


public class ManageCourse extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	
	private static String[] code,name,dept_id,dept_name,course_id,course_name;
	private static int[] sem;
	private static JComboBox<String> comboBox,comboBox2,comboBox3,comboBox4;
	
	
	private static JPanel panel,panelEdit;
	private JButton edit,delete;
	private JTextField courseid,coursename;
	private JTextField sname;

	public ManageCourse() {
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
		
		JLabel title = new JLabel("Manage Courses");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 40, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(574, 81, 233, 2);
		add(separator);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.requestFocus();
				updateComboBox2();
				comboBox2.setSelectedIndex(0);
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
				updateComboBox3();
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(403, 130, 758, 28);
		add(comboBox2);
		
		
		comboBox3 = new JComboBox<String>();
		comboBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.requestFocus();
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0 && comboBox3.getSelectedIndex()>0) {
					panel.setVisible(true);
					updateComboBox4();
				}
				else
					panel.setVisible(false);
			}
		});
		comboBox3.setBackground(Color.WHITE);
		comboBox3.setBounds(403, 167, 278, 28);
		add(comboBox3);
		
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(141, 239, 1179, 509);
		add(panel);
		panel.setLayout(null);
		panel.setVisible(false);
		
		edit = new JButton("Edit Course Details");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox4.getSelectedIndex()<=0)
				{
					JOptionPane.showMessageDialog(null, "Please select course from list.");
					return;
				}
				delete.setEnabled(false);
				panelEdit.setVisible(true);
				getCourseDetails();
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
		
		delete = new JButton("Delete Course");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCourse();
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
		
		JLabel lblSelectStaff = new JLabel("Select course :");
		lblSelectStaff.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectStaff.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectStaff.setBounds(164, 10, 105, 26);
		panel.add(lblSelectStaff);
		
		comboBox4 = new JComboBox<String>();
		comboBox4.setBackground(Color.WHITE);
		comboBox4.setBounds(267, 11, 620, 28);
		panel.add(comboBox4);
		
		panelEdit = new JPanel();
		panelEdit.setLayout(null);
		panelEdit.setOpaque(false);
		panelEdit.setBounds(23, 145, 1063, 235);
		panel.add(panelEdit);
		panelEdit.setVisible(false);
		
		
		
		JButton update = new JButton("Udpate");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCourse();
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
		update.setBounds(539, 193, 126, 32);
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
		cancel.setBounds(403, 193, 126, 32);
		panelEdit.add(cancel);
		
		
		JLabel label_1 = new JLabel("If any field is left blank, then value of that field will be ignored during updation.");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(133, 156, 605, 19);
		panelEdit.add(label_1);
		
		JLabel label_3 = new JLabel("Course ID : *");
		label_3.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_3.setBounds(133, 33, 87, 19);
		panelEdit.add(label_3);
		
		courseid = new JTextField();
		courseid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		courseid.setColumns(10);
		courseid.setBounds(228, 30, 150, 25);
		panelEdit.add(courseid);
		courseid.setEnabled(false);
		
		JLabel label_4 = new JLabel("Course Title: *");
		label_4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_4.setBounds(133, 71, 104, 19);
		panelEdit.add(label_4);
		
		coursename = new JTextField();
		coursename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		coursename.setColumns(10);
		coursename.setBounds(235, 68, 698, 25);
		panelEdit.add(coursename);
		
		JLabel label_5 = new JLabel("Short Name : *");
		label_5.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_5.setBounds(133, 112, 104, 19);
		panelEdit.add(label_5);
		
		sname = new JTextField();
		sname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sname.setColumns(10);
		sname.setBounds(245, 107, 150, 25);
		panelEdit.add(sname);
		
		JLabel label_2 = new JLabel("Select semester :");
		label_2.setHorizontalTextPosition(SwingConstants.CENTER);
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_2.setBounds(245, 167, 148, 26);
		add(label_2);
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
				String str1[]= {"--- Select your department --- "};
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
				dept_name[0]="--- Select your department --- ";
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
			if(comboBox2.getSelectedIndex()<=0)
			{
				String str1[]= {"--- Select your semester --- "};
				comboBox3.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT sem FROM Semester WHERE dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY sem;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				sem=new int[count+1];
				String str[]=new String[count+1];
				rs.beforeFirst();
				int i=1;
				str[0]="--- Select your semester --- ";
				while(rs.next()) {
					sem[i]=rs.getInt(1);
					str[i]=rs.getString(1);
					i++;
				}
				comboBox3.setModel(new DefaultComboBoxModel<String>(str));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public static void updateComboBox4()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(comboBox3.getSelectedIndex()<=0)
			{
				String str1[]= {"--- Select your course --- "};
				comboBox4.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT course_id,course_name FROM Course WHERE sem="+sem[comboBox3.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY sem;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				course_id=new String[count+1];
				course_name=new String[count+1];
				rs.beforeFirst();
				int i=1;
				course_name[0]="--- Select your course --- ";
				while(rs.next()) {
					course_id[i]=rs.getString(1);
					course_name[i]=rs.getString(2)+" - "+rs.getString(1);
					i++;
				}
				comboBox4.setModel(new DefaultComboBoxModel<String>(course_name));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public void deleteCourse()
	{
		try {
			stmt=con.createStatement();
			int index1=comboBox.getSelectedIndex();
			int index2=comboBox2.getSelectedIndex();
			int index3=comboBox3.getSelectedIndex();
			int index4=comboBox4.getSelectedIndex();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete course "+course_name[index4]
					+ "? \nDeleting will remove details of course from selected department and college!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			stmt.executeUpdate("DELETE FROM Theory WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';");
			stmt.executeUpdate("DELETE FROM Lab WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';");
			stmt.executeUpdate("DELETE FROM Elective WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';");
			stmt.executeUpdate("DELETE FROM Schedule WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';");
			
			if((stmt.executeUpdate("DELETE FROM Course WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Course '"+course_name[index4]+"' deleted successfully.");
				updateComboBox4();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the course details.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getCourseDetails()
	{
		try {
			stmt=con.createStatement();
			int index1=comboBox.getSelectedIndex();
			int index2=comboBox2.getSelectedIndex();
			int index3=comboBox3.getSelectedIndex();
			int index4=comboBox4.getSelectedIndex();
			String query="SELECT course_id,course_name,short_name FROM Course WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			panelEdit.setVisible(true);
			courseid.setText(rs.getString(1));
			coursename.setText(rs.getString(2));
			sname.setText(rs.getString(3));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching course details");
			e.printStackTrace();
		}
	}
	
	public boolean validateDomain()
	{
		if(!courseid.getText().equals("") && !Validation.uniqueIdDomainSatisfied(courseid.getText()))
		{
			courseid.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Course ID can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		courseid.setBackground(Color.white);
		
		if(!coursename.getText().equals("") && !Validation.nameDomainSatisfied(coursename.getText()))
		{
			coursename.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		coursename.setBackground(Color.white);
		return true;
	}
	
	public void updateCourse()
	{
		if(!validateDomain())
			return;
		try {
			stmt=con.createStatement();
			int index1=comboBox.getSelectedIndex();
			int index2=comboBox2.getSelectedIndex();
			int index3=comboBox3.getSelectedIndex();
			int index4=comboBox4.getSelectedIndex();
			String query="SELECT course_id,course_name,short_name FROM Course WHERE course_id='"+course_id[index4]+"' AND sem="+sem[index3]+" AND dept_id='"+dept_id[index2]+"' AND code='"+code[index1]+"';";
			rs=stmt.executeQuery(query);
			rs.next();
			String update="UPDATE Course SET course_id='"+rs.getString(1)+"'";
			
			update+=", course_name='";
			if(!coursename.getText().equals("")) update+=coursename.getText()+"'"; else update+=rs.getString(2)+"'";
			
			update+=", short_name='";
			if(!sname.getText().equals("")) update+=sname.getText()+"'"; else update+=rs.getString(3)+"'";
			
			update+=" WHERE course_id='"+rs.getString(1)+"';";
			stmt.executeUpdate(update);
			JOptionPane.showMessageDialog(null,"Course details updated successfully.");
			panelEdit.setVisible(false);
			delete.setEnabled(true);
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to update course detail to database.");
			e.printStackTrace();
		}
	}
}

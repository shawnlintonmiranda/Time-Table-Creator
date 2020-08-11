package dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import homePage.Login;
import java.sql.*;


public class ManageSemester extends JDialog {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static Statement stmt=null;
	private static ResultSet rs=null;

	private JDialog dialog;
	
	private static String user,code;
	
	private static String[] dept_id,dept_name;
	private static int[] sem;
	
	private static JComboBox<String> comboBox,comboBox2;
	private static JSpinner spinner;
	private static JPanel page1,page2;
	private JLabel lblSelectSemester;
	private JButton deleteSem;
	private JButton Cancel;
	private JButton hod;

	public ManageSemester(JFrame frame,String code,String user) {
		super(frame,"Manage Semester",Dialog.ModalityType.DOCUMENT_MODAL);
		dialog=this;
		ManageSemester.code=code;
		ManageSemester.user=user;
		con=Login.getCon();
		getContentPane().setBackground(new Color(255, 248, 220));
		setSize(500,310);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		page1 = new JPanel();
		page1.setOpaque(false);
		page1.setBounds(10, 72, 466, 191);
		getContentPane().add(page1);
		page1.setVisible(false);
		page1.setLayout(null);
		
		JLabel lblTotalSemester = new JLabel("Total Semesters :");
		lblTotalSemester.setBounds(134, 74, 133, 26);
		page1.add(lblTotalSemester);
		lblTotalSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		spinner = new JSpinner();
		spinner.setBounds(280, 74, 45, 28);
		page1.add(spinner);
		spinner.setModel(new SpinnerNumberModel(8, 1, 8, 1));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton next = new JButton("Create");
		next.setBounds(172, 112, 126, 32);
		page1.add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()>0)
					createSemesters();
				else
					JOptionPane.showMessageDialog(null, "Please select a department.");
			}
		});
		next.setHorizontalTextPosition(SwingConstants.CENTER);
		next.setForeground(Color.WHITE);
		next.setFont(new Font("Tahoma", Font.PLAIN, 18));
		next.setFocusTraversalKeysEnabled(false);
		next.setFocusPainted(false);
		next.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		next.setBackground(new Color(51, 0, 255));
		
		JButton btnEditSemesters = new JButton("Edit Semesters");
		btnEditSemesters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.setVisible(false);
				page2.setVisible(true);
				if(comboBox.getSelectedIndex()>0)
					updateComboBox2();
			}
		});
		btnEditSemesters.setBounds(165, 149, 140, 32);
		page1.add(btnEditSemesters);
		btnEditSemesters.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEditSemesters.setForeground(Color.WHITE);
		btnEditSemesters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnEditSemesters.setFocusTraversalKeysEnabled(false);
		btnEditSemesters.setFocusPainted(false);
		btnEditSemesters.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnEditSemesters.setBackground(new Color(255, 99, 71));
		
		JButton btnDeleteDepartment = new JButton("Delete Department");
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select department to be deleted.");
				else
					deleteDept();
			}
		});
		btnDeleteDepartment.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDeleteDepartment.setForeground(Color.WHITE);
		btnDeleteDepartment.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDeleteDepartment.setFocusTraversalKeysEnabled(false);
		btnDeleteDepartment.setFocusPainted(false);
		btnDeleteDepartment.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnDeleteDepartment.setBackground(Color.RED);
		btnDeleteDepartment.setBounds(237, 10, 191, 32);
		page1.add(btnDeleteDepartment);
		
		hod = new JButton("Choose HOD");
		hod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stmt=con.createStatement();
					rs=stmt.executeQuery("SELECT user_id FROM UserAccounts WHERE username='"+ManageSemester.user+"' AND admin=1;");
					if(rs.next())
						new AddHOD(dialog,dept_id[comboBox.getSelectedIndex()],dept_name[comboBox.getSelectedIndex()],code);
					else
					{
						JOptionPane.showMessageDialog(null, "Only admin is permitter to add/change HOD of deparment.","Permission denied",JOptionPane.WARNING_MESSAGE);
						hod.setEnabled(false);
					}
				}
				catch(SQLException ee)
				{
					ee.printStackTrace();
				}
			}
		});
		hod.setHorizontalTextPosition(SwingConstants.CENTER);
		hod.setForeground(Color.WHITE);
		hod.setFont(new Font("Tahoma", Font.PLAIN, 18));
		hod.setFocusTraversalKeysEnabled(false);
		hod.setFocusPainted(false);
		hod.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		hod.setBackground(new Color(0, 0, 205));
		hod.setBounds(36, 10, 191, 32);
		page1.add(hod);
		
		page2 = new JPanel();
		page2.setOpaque(false);
		page2.setBounds(10, 90, 466, 134);
		getContentPane().add(page2);
		page2.setLayout(null);
		page2.setVisible(false);
		
		lblSelectSemester = new JLabel("Select semester :");
		lblSelectSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectSemester.setBounds(44, 11, 148, 26);
		page2.add(lblSelectSemester);
		
		comboBox2 = new JComboBox<String>();
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.requestFocus();
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(172, 10, 227, 28);
		page2.add(comboBox2);
		
		deleteSem = new JButton("Delete Semester");
		deleteSem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select a department.");
				else if(comboBox2.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select a semester.");
				else
				{
					deleteSemester();
				}
			}
		});
		deleteSem.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteSem.setForeground(Color.WHITE);
		deleteSem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteSem.setFocusTraversalKeysEnabled(false);
		deleteSem.setFocusPainted(false);
		deleteSem.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		deleteSem.setBackground(new Color(51, 0, 255));
		deleteSem.setBounds(159, 55, 148, 32);
		page2.add(deleteSem);
		
		Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.setVisible(false);
				if(comboBox.getSelectedIndex()>0)
					page1.setVisible(true);
			}
		});
		Cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		Cancel.setForeground(Color.WHITE);
		Cancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Cancel.setFocusTraversalKeysEnabled(false);
		Cancel.setFocusPainted(false);
		Cancel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		Cancel.setBackground(new Color(255, 99, 71));
		Cancel.setBounds(163, 92, 140, 32);
		page2.add(Cancel);
		
		JLabel lblSelectDepartment = new JLabel("Select department :");
		lblSelectDepartment.setBounds(10, 10, 148, 26);
		getContentPane().add(lblSelectDepartment);
		lblSelectDepartment.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectDepartment.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 34, 466, 28);
		getContentPane().add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox2();
				if(comboBox.getSelectedIndex()>0 && !page2.isVisible()) 
					page1.setVisible(true);
				else
					page1.setVisible(false);
			}
		});
		comboBox.setBackground(Color.WHITE);
		updateComboBox();
		setVisible(true);
		
		
	}
	
	public static void updateComboBox()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query="SELECT dept_id,dept_name FROM Department WHERE code='"+code+"' ORDER BY dept_name;";
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
			comboBox.setModel(new DefaultComboBoxModel<String>(dept_name));
			
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
				String str1[]= {"--- Select your semester --- "};
				comboBox2.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT sem FROM Semester WHERE dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"' ORDER BY sem;";
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
				comboBox2.setModel(new DefaultComboBoxModel<String>(str));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public void createSemesters()
	{
		try {
			stmt=con.createStatement();
			int count=(int)spinner.getValue();
			for(int i=1; i<=count; i++)
			{
				rs=stmt.executeQuery("SELECT sem FROM Semester WHERE sem="+i+" AND dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"';");
				if(!rs.next())
					stmt.executeUpdate("INSERT INTO Semester VALUES("+i+",'"+dept_id[comboBox.getSelectedIndex()]+"','"+code+"');");	
			}
			JOptionPane.showMessageDialog(null, "Semesters added succesfully.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void deleteSemester()
	{
		try {
			stmt=con.createStatement();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete semester "+sem[comboBox2.getSelectedIndex()]
					+ "? \nDeleting will remove all the sections and time-tables associated with the semester!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			if((stmt.executeUpdate("DELETE FROM Semester WHERE sem="+sem[comboBox2.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Semester deleted successfully.");
				updateComboBox2();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the semester entry.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteDept()
	{
		try {
			stmt=con.createStatement();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete department "+dept_name[comboBox.getSelectedIndex()]
					+ "? \nDeleting will remove all the semesters, staff, sections and time-tables associated with the department!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			if((stmt.executeUpdate("DELETE FROM Department WHERE dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Department deleted successfully.");
				updateComboBox();
				page1.setVisible(false);
				spinner.setValue(8);
				
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the department entry.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}

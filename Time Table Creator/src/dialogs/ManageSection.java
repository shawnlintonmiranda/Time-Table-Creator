package dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import homePage.Login;

import java.sql.*;

public class ManageSection extends JDialog {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static Statement stmt=null;
	private static ResultSet rs=null;
	
	private static String code;
	
	private static String[] dept_id,dept_name,section;
	private static int[] sem;
	
	private static JComboBox<String> comboBox,comboBox2,comboBox3;
	private static JSpinner spinner;
	private static JPanel page1,page2;
	private JLabel lblSelectSemester;
	private JButton deleteSem;
	private JButton Cancel;
	private JLabel label;

	public ManageSection(JFrame frame,String code) {
		super(frame,"Manage Section",Dialog.ModalityType.DOCUMENT_MODAL);
		ManageSection.code=code;
		con=Login.getCon();
		getContentPane().setBackground(new Color(255, 248, 220));
		setSize(500,342);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		page1 = new JPanel();
		page1.setOpaque(false);
		page1.setBounds(10, 132, 466, 132);
		getContentPane().add(page1);
		page1.setVisible(false);
		setResizable(false);
		page1.setLayout(null);
		
		JLabel lblTotalSemester = new JLabel("Total Sections  :");
		lblTotalSemester.setBounds(96, 10, 133, 26);
		page1.add(lblTotalSemester);
		lblTotalSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		spinner = new JSpinner();
		spinner.setBounds(242, 10, 45, 28);
		page1.add(spinner);
		spinner.setModel(new SpinnerNumberModel(1, 1, 26, 1));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton next = new JButton("Create");
		next.setBounds(168, 60, 126, 32);
		page1.add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()>0)
					createSections();
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
		
		JButton btnEditSemesters = new JButton("Edit Sections");
		btnEditSemesters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.setVisible(false);
				page2.setVisible(true);
				if(comboBox2.getSelectedIndex()>0 && comboBox.getSelectedIndex()>0)
					updateComboBox3();
			}
		});
		btnEditSemesters.setBounds(161, 97, 140, 32);
		page1.add(btnEditSemesters);
		btnEditSemesters.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEditSemesters.setForeground(Color.WHITE);
		btnEditSemesters.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnEditSemesters.setFocusTraversalKeysEnabled(false);
		btnEditSemesters.setFocusPainted(false);
		btnEditSemesters.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnEditSemesters.setBackground(new Color(255, 99, 71));
		
		page2 = new JPanel();
		page2.setOpaque(false);
		page2.setBounds(10, 132, 466, 134);
		getContentPane().add(page2);
		page2.setLayout(null);
		page2.setVisible(false);
		
		lblSelectSemester = new JLabel("Select section :");
		lblSelectSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectSemester.setBounds(88, 11, 148, 26);
		page2.add(lblSelectSemester);
		
		comboBox3 = new JComboBox<String>();
		String str[]= {"--- Select your section --- "};
		comboBox3.setModel(new DefaultComboBoxModel<String>(str));
		comboBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.requestFocus();
			}
		});
		comboBox3.setBackground(Color.WHITE);
		comboBox3.setBounds(216, 10, 164, 28);
		page2.add(comboBox3);
		
		deleteSem = new JButton("Delete Section");
		deleteSem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select a department.");
				else if(comboBox2.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select a semester.");
				else if(comboBox3.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select a section.");
				else
				{
					deleteSection();
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
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0)
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
		lblSelectDepartment.setBounds(10, 21, 148, 26);
		getContentPane().add(lblSelectDepartment);
		lblSelectDepartment.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectDepartment.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 52, 466, 28);
		getContentPane().add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox2();
			}
		});
		comboBox.setBackground(Color.WHITE);
		
		
		comboBox2 = new JComboBox<String>();
		String str1[]= {"--- Select your semester --- "};
		comboBox2.setModel(new DefaultComboBoxModel<String>(str1));
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox3();
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0 && !page2.isVisible())
					page1.setVisible(true);
				else
					page1.setVisible(false);
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(138, 94, 227, 28);
		getContentPane().add(comboBox2);
		
		label = new JLabel("Select semester :");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Georgia", Font.PLAIN, 16));
		label.setBounds(10, 95, 148, 26);
		getContentPane().add(label);
		
		updateComboBox();
		updateComboBox2();
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
	
	public static void updateComboBox3()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(comboBox.getSelectedIndex()<=0 || comboBox2.getSelectedIndex()<=0)
			{
				String str1[]= {"--- Select your section --- "};
				comboBox2.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT section FROM Section WHERE sem="+sem[comboBox2.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"' ORDER BY section;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				section=new String[count+1];
				rs.beforeFirst();
				int i=1;
				section[0]="--- Select your section --- ";
				while(rs.next()) {
					section[i]=rs.getString(1);
					i++;
				}
				comboBox3.setModel(new DefaultComboBoxModel<String>(section));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	public void createSections()
	{
		try {
			stmt=con.createStatement();
			int count=(int)spinner.getValue();
			for(int i=1; i<=count; i++)
			{
				char ch=(char)(i+64);
				rs=stmt.executeQuery("SELECT section FROM Section WHERE section='"+ch+"' AND sem="+sem[comboBox2.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"';");
				if(!rs.next()) {
					stmt.executeUpdate("INSERT INTO Section VALUES('"+ch+"',0,'','"+dept_id[comboBox.getSelectedIndex()]+"',"+sem[comboBox2.getSelectedIndex()]+",'"+code+"',0,0,'');");	
				}}
			JOptionPane.showMessageDialog(null, "Sections added succesfully.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void deleteSection()
	{
		try {
			stmt=con.createStatement();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete semester "+section[comboBox3.getSelectedIndex()]
					+ "? \nDeleting will remove all the sections and time-tables associated with the semester!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			if((stmt.executeUpdate("DELETE FROM Section WHERE section='"+section[comboBox3.getSelectedIndex()]+"' AND sem="+sem[comboBox2.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox.getSelectedIndex()]+"' AND code='"+code+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Semester deleted successfully.");
				updateComboBox3();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the section entry.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}

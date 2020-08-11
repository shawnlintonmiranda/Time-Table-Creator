package subUserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import homePage.Login;
import tools.Validation;
import java.sql.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class AddCourse extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static String[] code,name,dept_id,dept_name;
	private static int[] sem;
	private static JComboBox<String> comboBox,comboBox2,comboBox3;
	private JTextField courseid,coursename;
	private static JPanel panel;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JLabel noData,ec;
	private JTextField short_name;
	
	public AddCourse() {
		addPropertyChangeListener("visible",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if((boolean)evt.getNewValue()) {
					updateComboBox();
					comboBox.setSelectedIndex(0);
					if(comboBox2.getSelectedIndex()>0)
						updateTable();
				}
				
			}
		});
		con=Login.getCon();
		
		setBackground(new Color(255, 250, 240));
		setSize(1563,797);
		setLayout(null);
		
		JLabel title = new JLabel("Add New Course");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 40, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(577, 81, 233, 2);
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
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setOpaque(false);
		panel.setBounds(170, 216, 1063, 260);
		add(panel);
		
		JLabel lblStaffId = new JLabel("Course ID : *");
		lblStaffId.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblStaffId.setBounds(154, 31, 87, 19);
		panel.add(lblStaffId);
		
		courseid = new JTextField();
		courseid.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(courseid.getText().length()>=10) e.consume();
			}
		});
		courseid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		courseid.setColumns(10);
		courseid.setBounds(249, 28, 150, 25);
		panel.add(courseid);
		
		JLabel slabel = new JLabel("Course Title: *");
		slabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		slabel.setBounds(154, 69, 104, 19);
		panel.add(slabel);
		
		coursename = new JTextField();
		coursename.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(coursename.getText().length()>=50) e.consume();
			}
		});
		coursename.setFont(new Font("Tahoma", Font.PLAIN, 15));
		coursename.setColumns(10);
		coursename.setBounds(256, 66, 698, 25);
		panel.add(coursename);
		
		JLabel label_9 = new JLabel("Fields marked with * are mandatory.");
		label_9.setForeground(Color.RED);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_9.setBounds(154, 149, 272, 19);
		panel.add(label_9);
		
		JButton add = new JButton("Add Course");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()<=0) {
					JOptionPane.showMessageDialog(null, "Please select college.");
					return;
				}
				if(comboBox2.getSelectedIndex()<=0) {
					JOptionPane.showMessageDialog(null, "Please select department.");
					return;
				}
				if(comboBox3.getSelectedIndex()<=0) {
					JOptionPane.showMessageDialog(null, "Please select semester.");
					return;
				}
				addCourse();
				updateTable();
			}
		});
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add.setFocusTraversalKeysEnabled(false);
		add.setFocusPainted(false);
		add.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		add.setBackground(new Color(51, 0, 255));
		add.setBounds(523, 218, 126, 32);
		panel.add(add);
		
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component c : panel.getComponents())
				{
					if(c instanceof JTextField)
						((JTextField)c).setText("");
				}
			}
		});
		reset.setHorizontalTextPosition(SwingConstants.CENTER);
		reset.setForeground(Color.WHITE);
		reset.setFont(new Font("Tahoma", Font.PLAIN, 18));
		reset.setFocusTraversalKeysEnabled(false);
		reset.setFocusPainted(false);
		reset.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		reset.setBackground(Color.DARK_GRAY);
		reset.setBounds(387, 218, 126, 32);
		panel.add(reset);
		
		JLabel lblPleaseNote = new JLabel("Please note : Course ID must be unique within particular semester.");
		lblPleaseNote.setForeground(Color.BLACK);
		lblPleaseNote.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseNote.setBounds(154, 178, 495, 19);
		panel.add(lblPleaseNote);
		
		JLabel label4 = new JLabel("Short Name : *");
		label4.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label4.setBounds(154, 106, 104, 19);
		panel.add(label4);
		
		short_name = new JTextField();
		short_name.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(short_name.getText().length()>=7) e.consume();
			}
		});
		short_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		short_name.setColumns(10);
		short_name.setBounds(266, 101, 150, 25);
		panel.add(short_name);
		
		updateComboBox();
		
		ec = new JLabel("Existing Courses :");
		ec.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		ec.setBounds(245, 509, 185, 19);
		ec.setVisible(false);
		add(ec);
		
		model=new DefaultTableModel();
		model.addColumn("Course ID");
		model.addColumn("Course Title");
		model.addColumn("Short Name");
		DefaultTableCellRenderer render=new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		
		noData = new JLabel("");
		noData.setVisible(false);
		noData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		noData.setBounds(245, 538, 424, 35);
		add(noData);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setOpaque(false);
		scrollPane.setBounds(287, 576, 805, 170);
		add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		//table.setUpdateSelectionOnSort(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setGridColor(Color.BLACK);
		table.setEnabled(false);
		table.setSelectionBackground(Color.BLACK);
		table.setIntercellSpacing(new Dimension(10, 10));
		table.setRowMargin(2);
		table.setRowHeight(25);
		table.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		table.setForeground(Color.BLACK);
		table.setFillsViewportHeight(true);
		table.setBackground(Color.WHITE);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(245, 486, 916, 2);
		add(separator_1);
		
		JLabel lblSelectSemester = new JLabel("Select semester :");
		lblSelectSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectSemester.setBounds(245, 166, 148, 26);
		add(lblSelectSemester);
		
		comboBox3 = new JComboBox<String>();
		comboBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.requestFocus();
				updateTable();
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0 && comboBox3.getSelectedIndex()>0)
				{
					ec.setVisible(true);
					noData.setVisible(true);
					scrollPane.setVisible(true);
				}
				else
				{
					ec.setVisible(false);
					noData.setVisible(false);
					scrollPane.setVisible(false);
				}
			}
		});
		comboBox3.setBackground(Color.WHITE);
		comboBox3.setBounds(403, 166, 278, 28);
		add(comboBox3);
		table.getColumnModel().getColumn(0).setMaxWidth(120);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(render);
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
	
	public void addCourse() {
		courseid.setBackground(Color.white);
		coursename.setBackground(Color.white);
		boolean flag=false;
		if(courseid.getText().equals("")) {
			courseid.setBackground(new Color(255,255,200));
			courseid.requestFocus();
			flag=true;
		} else if(coursename.getText().equals("")) {
			coursename.setBackground(new Color(255,255,200));
			coursename.requestFocus();
			flag=true;
		} else if(short_name.getText().equals("")) {
			short_name.setBackground(new Color(255,255,200));
			short_name.requestFocus();
			flag=true;
		}
		if(flag)
		{
			JOptionPane.showMessageDialog(this, "Please fill the mandatory fields.");
			return;
		}
		
		if(!validateDomain())
			return;
		
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT course_id FROM Course WHERE course_id='"+courseid.getText()+"' AND sem="+sem[comboBox3.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"';");
			
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "Course ID already exists! Please use different one.", "Time Table Creator", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String insert="INSERT INTO Course VALUES('"+courseid.getText()+"','"+coursename.getText()+"',"+sem[comboBox3.getSelectedIndex()]+",'"+dept_id[comboBox2.getSelectedIndex()]+"','"+code[comboBox.getSelectedIndex()]+"','"+short_name.getText()+"');";
			if(stmt.executeUpdate(insert)>0) {
				JOptionPane.showMessageDialog(null, "Course added successfully.");
				for(Component comp:panel.getComponents())
				{
					if(comp instanceof JTextField)
						((JTextField) comp).setText("");
				}
			}
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to insert college detail to database.");
			e.printStackTrace();
		}
		
	}
	public boolean validateDomain()
	{
		if(!Validation.uniqueIdDomainSatisfied(courseid.getText()))
		{
			courseid.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Course ID can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		courseid.setBackground(Color.white);
		
		return true;
	}
	
	public void updateTable()
	{
		int count=0;
		try {
			model.setRowCount(0);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT course_id,course_name,short_name FROM Course WHERE sem="+sem[comboBox3.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY course_name DESC,course_id DESC;");
			while(rs.next()) count++;
			if(count==0)
			{
				scrollPane.setVisible(false);
				noData.setText("No courses found for selected semester.");
			}
			else
			{
				noData.setText("Total Courses : "+count);
				scrollPane.setVisible(true);
				rs.first();
				do {
					model.insertRow(0, new String[] {rs.getString(1), rs.getString(2),rs.getString(3)});
				}while (rs.next());
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "Unable to fetch data from database!","Error",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
}

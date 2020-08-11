package subUserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import homePage.Login;
import tools.Validation;

import java.sql.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;


public class AddStaff extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static String[] code,name,dept_id,dept_name;
	
	private static JComboBox<String> comboBox,comboBox2;
	private static JDateChooser dob;
	private JTextField staffid;
	private static JPanel panel;
	private JTextField staffname;
	private JTextField phone;
	private JTextField email;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JLabel noData,es;
	public AddStaff() {
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
		
		JLabel title = new JLabel("Add New Staff Details");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 40, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(583, 80, 233, 2);
		add(separator);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.requestFocus();
				updateComboBox2();
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
				updateTable();
				if(comboBox.getSelectedIndex()>0 && comboBox2.getSelectedIndex()>0)
				{
					es.setVisible(true);
					noData.setVisible(true);
					scrollPane.setVisible(true);
				}
				else
				{
					es.setVisible(false);
					noData.setVisible(false);
					scrollPane.setVisible(false);
				}
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(403, 130, 758, 28);
		add(comboBox2);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setOpaque(false);
		panel.setBounds(186, 182, 1063, 294);
		add(panel);
		
		JLabel lblStaffId = new JLabel("Staff ID : *");
		lblStaffId.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblStaffId.setBounds(0, 3, 87, 19);
		panel.add(lblStaffId);
		
		staffid = new JTextField();
		staffid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		staffid.setColumns(10);
		staffid.setBounds(79, 0, 150, 25);
		panel.add(staffid);
		
		JLabel slabel = new JLabel("Full Name : *");
		slabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		slabel.setBounds(0, 41, 104, 19);
		panel.add(slabel);
		
		staffname = new JTextField();
		staffname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		staffname.setColumns(10);
		staffname.setBounds(102, 38, 698, 25);
		panel.add(staffname);
		
		JLabel label_7 = new JLabel("Contact number :");
		label_7.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_7.setBounds(0, 132, 126, 19);
		panel.add(label_7);
		
		phone = new JTextField();
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		phone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phone.setColumns(10);
		phone.setBounds(127, 129, 96, 25);
		panel.add(phone);
		
		JLabel label_8 = new JLabel("Email Address :");
		label_8.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		label_8.setBounds(246, 132, 110, 19);
		panel.add(label_8);
		
		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		email.setColumns(10);
		email.setBounds(355, 129, 233, 25);
		panel.add(email);
		
		JLabel label_9 = new JLabel("Fields marked with * are mandatory.");
		label_9.setForeground(Color.RED);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_9.setBounds(0, 183, 272, 19);
		panel.add(label_9);
		
		JButton add = new JButton("Add Staff");
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
				addStaff();
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
		add.setBounds(505, 251, 126, 32);
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
		reset.setBounds(369, 251, 126, 32);
		panel.add(reset);
		
		dob = new JDateChooser();
		dob.setBounds(128, 81, 114, 25);
		panel.add(dob);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth : ");
		lblDateOfBirth.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblDateOfBirth.setBounds(0, 83, 121, 21);
		panel.add(lblDateOfBirth);
		
		JLabel lblPleaseNote = new JLabel("Please note : Staff ID must be unique within particular department of the college.");
		lblPleaseNote.setForeground(Color.BLACK);
		lblPleaseNote.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseNote.setBounds(0, 212, 495, 19);
		panel.add(lblPleaseNote);
		
		updateComboBox();
		
		es = new JLabel("Existing Staff :");
		es.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		es.setBounds(245, 509, 185, 19);
		es.setVisible(false);
		add(es);
		
		model=new DefaultTableModel();
		model.addColumn("Staff ID");
		model.addColumn("Staff Name");
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
	
	public void addStaff() {
		staffid.setBackground(Color.white);
		staffname.setBackground(Color.white);
		boolean flag=false;
		if(staffid.getText().equals("")) {
			staffid.setBackground(new Color(255,255,200));
			staffid.requestFocus();
			flag=true;
		} else if(staffname.getText().equals("")) {
			staffname.setBackground(new Color(255,255,200));
			staffname.requestFocus();
			flag=true;
		}
		if(flag)
		{
			JOptionPane.showMessageDialog(this, "Please fill the mandatory fields.", "Fields required",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!validateDomain())
			return;
		
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT staff_id FROM Staff WHERE staff_id='"+staffid.getText()+"' AND code='"+code[comboBox.getSelectedIndex()]+"';");
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "Staff ID already exists! Please use different one.", "Time Table Creator", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String insert="INSERT INTO Staff VALUES('"+staffid.getText()+"','"+staffname.getText()+"','"+getDate(0)+"',0,'"+phone.getText()+"','"+email.getText()+"','"+getDate(1)+"','"+dept_id[comboBox2.getSelectedIndex()]+"','"+code[comboBox.getSelectedIndex()]+"');";
			if(stmt.executeUpdate(insert)>0) {
				JOptionPane.showMessageDialog(null, "Staff details added successfully.");
				for(Component comp:panel.getComponents())
				{
					if(comp instanceof JTextField)
						((JTextField) comp).setText("");
				}
				dob.cleanup();
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
		if(!Validation.uniqueIdDomainSatisfied(staffid.getText()))
		{
			staffid.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Staff ID can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		staffid.setBackground(Color.white);
		
		if(!Validation.nameDomainSatisfied(staffname.getText()))
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
	
	public void updateTable()
	{
		int count=0;
		try {
			model.setRowCount(0);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT staff_id,staff_name FROM Staff WHERE dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY staff_name DESC,staff_id DESC;");
			while(rs.next()) count++;
			if(count==0)
			{
				scrollPane.setVisible(false);
				noData.setText("Staff details are not found for selected college and department.");
			}
			else
			{
				noData.setText("Total Staff : "+count);
				scrollPane.setVisible(true);
				rs.first();
				do {
					model.insertRow(0, new String[] {rs.getString(1), rs.getString(2)});
				}while (rs.next());
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "Unable to fetch data from database!","Error",JOptionPane.WARNING_MESSAGE);
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

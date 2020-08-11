package subUserPages;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import homePage.Login;
import panels.Cell;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.swing.border.BevelBorder;


public class ExportTimeTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static JPanel page1,page2;
	private static JComboBox<String> comboBox,comboBox2,comboBox3,comboBox4;
	
	private static String[] code,name,dept_id,dept_name,sec,course_id,course_name,staff_id,staff_name;
	private static int[] sem;
	
	private static String college,college_name,department,department_name,section;
	private static int semester;
	public static boolean saved=true;
	private static JButton proceed;
	private static JTable table;
	private static DefaultTableModel model;
	private static JScrollPane scrollPane;
	private static JLabel secname,dname,sname,cname;
	private JLabel label_3,notAvail;
	private JButton btnBack;
	private JLabel advisor;
	private JLabel classroom;
	
	public ExportTimeTable() {
		addPropertyChangeListener("visible",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if((boolean)evt.getNewValue()) {
					updateComboBox();
					comboBox.setSelectedIndex(0);
				}
			}
		});
		con=Login.getCon();
		setBackground(new Color(255, 235, 205));
		setSize(1563,797);
		setLayout(null);
		
		JLabel title = new JLabel("Export Time Table");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 10, 1376, 43);
		add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(575, 51, 233, 2);
		add(separator);
		
		page1 = new JPanel();
		page1.setVisible(true);
		
		notAvail = new JLabel("This section does not have any created time table. Please check whether it is finally submitted.");
		notAvail.setBounds(0, 200, 1540, 30);
		add(notAvail);
		notAvail.setVisible(false);
		notAvail.setFont(new Font("Sylfaen", Font.PLAIN, 17));
		notAvail.setHorizontalAlignment(SwingConstants.CENTER);
		
		page2 = new JPanel();
		page2.setBackground(new Color(255, 255, 255));
		page2.setBounds(40, 63, 1420, 609);
		add(page2);
		page2.setVisible(false);
		page2.setLayout(null);
		
		cname = new JLabel("");
		cname.setHorizontalAlignment(SwingConstants.CENTER);
		cname.setForeground(new Color(255, 0, 51));
		cname.setFont(new Font("Algerian", Font.PLAIN, 24));
		cname.setBounds(22, 10, 1438, 36);
		page2.add(cname);
		
		dname = new JLabel("");
		dname.setOpaque(true);
		dname.setHorizontalAlignment(SwingConstants.CENTER);
		dname.setForeground(new Color(0, 0, 0));
		dname.setFont(new Font("Segoe Print", Font.BOLD, 20));
		dname.setBorder(null);
		dname.setBackground(new Color(255, 255, 255));
		dname.setBounds(403, 47, 676, 30);
		page2.add(dname);
		
		sname = new JLabel("");
		sname.setOpaque(true);
		sname.setHorizontalAlignment(SwingConstants.CENTER);
		sname.setForeground(Color.BLACK);
		sname.setFont(new Font("Segoe Print", Font.BOLD, 16));
		sname.setBorder(null);
		sname.setBackground(Color.WHITE);
		sname.setBounds(403, 76, 676, 30);
		page2.add(sname);
		
		secname = new JLabel("");
		secname.setOpaque(true);
		secname.setHorizontalAlignment(SwingConstants.CENTER);
		secname.setForeground(Color.BLACK);
		secname.setFont(new Font("Segoe Print", Font.BOLD, 16));
		secname.setBorder(null);
		secname.setBackground(Color.WHITE);
		secname.setBounds(645, 116, 194, 30);
		page2.add(secname);
		
		model=new DefaultTableModel();
		scrollPane = new JScrollPane();
		scrollPane.setVisible(true);
		scrollPane.setBounds(22, 156, 1388, 402);
		page2.add(scrollPane);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		//table.setUpdateSelectionOnSort(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setGridColor(Color.BLACK);
		table.setEnabled(false);
		table.setSelectionBackground(Color.BLACK);
		table.setIntercellSpacing(new Dimension(30, 10));
		table.setRowMargin(2);
		table.setRowHeight(50);
		table.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		table.setForeground(Color.BLACK);
		table.setFillsViewportHeight(true);
		table.setBackground(new Color(255, 255, 255));
		
		label_3 = new JLabel("");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(431, 10, 424, 35);
		page2.add(label_3);
		
		advisor = new JLabel("");
		advisor.setOpaque(true);
		advisor.setHorizontalAlignment(SwingConstants.LEFT);
		advisor.setForeground(Color.BLACK);
		advisor.setFont(new Font("Times New Roman", Font.BOLD, 16));
		advisor.setBorder(null);
		advisor.setBackground(Color.WHITE);
		advisor.setBounds(22, 116, 406, 30);
		page2.add(advisor);
		
		classroom = new JLabel("");
		classroom.setOpaque(true);
		classroom.setHorizontalAlignment(SwingConstants.RIGHT);
		classroom.setForeground(Color.BLACK);
		classroom.setFont(new Font("Times New Roman", Font.BOLD, 16));
		classroom.setBorder(null);
		classroom.setBackground(Color.WHITE);
		classroom.setBounds(1199, 116, 194, 30);
		page2.add(classroom);
		
		page1.setOpaque(false);
		page1.setBounds(296, 125, 861, 291);
		add(page1);
		page1.setLayout(null);
		JLabel label = new JLabel("Select your college :");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Georgia", Font.PLAIN, 16));
		label.setBounds(10, 24, 148, 26);
		page1.add(label);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox2();
				comboBox2.setSelectedIndex(0);
			}
		});
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(168, 24, 683, 28);
		page1.add(comboBox);
		
		JLabel label_1 = new JLabel("Select department :");
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_1.setBounds(10, 72, 148, 26);
		page1.add(label_1);
		
		comboBox2 = new JComboBox<String>();
		comboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox3();
				comboBox3.setSelectedIndex(0);
			}
		});
		comboBox2.setBackground(Color.WHITE);
		comboBox2.setBounds(168, 72, 683, 28);
		page1.add(comboBox2);
		
		JLabel label_2 = new JLabel("Select semester :");
		label_2.setHorizontalTextPosition(SwingConstants.CENTER);
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_2.setBounds(10, 119, 148, 26);
		page1.add(label_2);
		
		comboBox3 = new JComboBox<String>();
		comboBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				updateComboBox4();
				comboBox4.setSelectedIndex(0);
			}
		});
		comboBox3.setBackground(Color.WHITE);
		comboBox3.setBounds(168, 119, 278, 28);
		page1.add(comboBox3);
		
		JLabel lblSelectSection = new JLabel("Select section :");
		lblSelectSection.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectSection.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectSection.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectSection.setBounds(10, 168, 148, 26);
		page1.add(lblSelectSection);
		
		comboBox4 = new JComboBox<String>();
		comboBox4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page1.requestFocus();
				
				if(comboBox4.getSelectedIndex()>0)
					proceed.setVisible(true);
				else
					proceed.setVisible(false);
			}
			
		});
		comboBox4.setBackground(Color.WHITE);
		comboBox4.setBounds(168, 168, 278, 28);
		page1.add(comboBox4);
		
		proceed = new JButton("View Time Table");
		proceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.setVisible(true);
				page1.setVisible(false);
				btnBack.setVisible(true);
				college=code[comboBox.getSelectedIndex()];
				college_name=name[comboBox.getSelectedIndex()];
				department=dept_id[comboBox2.getSelectedIndex()];
				department_name=dept_name[comboBox2.getSelectedIndex()];
				semester=sem[comboBox3.getSelectedIndex()];
				section=sec[comboBox4.getSelectedIndex()];
				scrollPane.setVisible(true);
				getData();
			}
		});
		proceed.setHorizontalTextPosition(SwingConstants.CENTER);
		proceed.setForeground(Color.WHITE);
		proceed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		proceed.setFocusTraversalKeysEnabled(false);
		proceed.setFocusPainted(false);
		proceed.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		proceed.setBackground(new Color(51, 0, 255));
		proceed.setBounds(345, 235, 168, 32);
		proceed.setVisible(false);
		page1.add(proceed);
		
		btnBack = new JButton("BACK");
		btnBack.setVisible(false);
		btnBack.setBounds(673, 683, 126, 32);
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.setVisible(false);
				notAvail.setVisible(false);
				page1.setVisible(true);
				model.setRowCount(0);
				model.setColumnCount(0);
				btnBack.setVisible(false);
			}
		});
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnBack.setBackground(new Color(50, 205, 50));
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
			name[0]="--- Select your Department --- ";
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
				String str1[]= {"--- Select your section --- "};
				comboBox4.setModel(new DefaultComboBoxModel<String>(str1));
			}
			else
			{
				String query="SELECT section FROM Section WHERE sem="+sem[comboBox3.getSelectedIndex()]+" AND dept_id='"+dept_id[comboBox2.getSelectedIndex()]+"' AND code='"+code[comboBox.getSelectedIndex()]+"' ORDER BY section;";
				rs=stmt.executeQuery(query);
				while(rs.next()) 
					count++;
				sec=new String[count+1];
				rs.beforeFirst();
				int i=1;
				sec[0]="--- Select your section --- ";
				while(rs.next()) {
					sec[i]=rs.getString(1);
					i++;
				}
				comboBox4.setModel(new DefaultComboBoxModel<String>(sec));
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}

	private void getData()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT staff_id,staff_name FROM Staff_view;");
			while(rs.next()) 
				count++;
			staff_id=new String[count];
			staff_name=new String[count];
			rs.beforeFirst();
			int i=0;
			while(rs.next()) {
				staff_id[i]=rs.getString(1);
				staff_name[i]=rs.getString(1)+" : "+rs.getString(2);
				i++;
			}
			
			count=0;
			rs=stmt.executeQuery("SELECT course_id,course_name,short_name FROM Course WHERE sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()) 
				count++;
			course_id=new String[count];
			course_name=new String[count];
			rs.beforeFirst();
			i=0;
			while(rs.next()) {
				course_id[i]=rs.getString(1);
				course_name[i]=rs.getString(3)+" : "+rs.getString(2);
				i++;
			}
			
			rs=stmt.executeQuery("SELECT final,advisor,room_number,hours FROM Section WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"' AND final=1;");
			if(!rs.next())
			{
				notAvail.setVisible(true);
				btnBack.setVisible(true);
				page2.setVisible(false);
				return;
			}
			model.addColumn("Day / Hour");
			String adv=rs.getString(2);
			String room=rs.getString(3);
			notAvail.setVisible(false);
			cname.setText(college_name);
			dname.setText(department_name);
			sname.setText("SEMESTER - "+semester);
			secname.setText("Section - "+section);
			classroom.setText("Class Room : "+room);
			advisor.setText("Academic Advisor : "+staff_name[find(staff_id,adv)]);
			int hours=Integer.parseInt(rs.getString(4));
			
			rs=stmt.executeQuery("SELECT start_time,end_time FROM Time WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"' ORDER BY hour;");
			rs.first();
			for(i=1;i<=hours; i++)
				model.addColumn(i);
			DefaultTableCellRenderer render=new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			for(i=0; i<hours+1; i++)
				table.getColumnModel().getColumn(i).setCellRenderer(render);
			for(i=0; i<7; i++)
				model.insertRow(0,new String[] {});
			for(i=1; i<hours+1; i++) {
				model.setValueAt(getTime(rs.getString(1))+"-"+getTime(rs.getString(2)), 0, i);
				rs.next();
			}
			
			for(i=1; i<7; i++)
				model.setValueAt(weekDay(i), i, 0);
			rs=stmt.executeQuery("SELECT * FROM Schedule WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()){
				String ci=rs.getString(1);
				ci=course_name[find(course_id,ci)];
				String[] str=ci.split(" : ");
				ci=str[0];
				String si=rs.getString(2);
				int x=rs.getInt(3), y=rs.getInt(4);
				String type=rs.getString(5);
				
				if(type.equals("Theory"))
				{
					model.setValueAt((Object)(ci+" ("+si+")"), x, y);
				}
				else if((type.substring(0,2)).equals("L-"))
				{	
					str=type.split("-");
					type=str[1];
					model.setValueAt((Object)type, x, y);
				}
				else if((type.substring(0,2)).equals("E-"))
				{
					str=type.split("-");
					type=str[1];
					model.setValueAt((Object)type, x, y);
				}
			}
			for(i=0;i<7;i++)
				for(int j=0; j<hours+1; j++)
					if(table.getModel().getValueAt(i, j)==null)
						table.setValueAt("*", i, j);
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching data.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private String weekDay(int i)
	{
		switch(i)
		{
		case 1:return("Monday"); 
		case 2:return("Tuesday"); 
		case 3:return("Wednesday"); 
		case 4:return("Thursday"); 
		case 5: return("Friday"); 
		case 6:return("Saturday"); 
		}
		return "Sunday";
	}
	
	private String getTime(String time)
	{
		try {
			SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
			java.util.Date date1=f.parse(time.substring(0,8));
			f=new SimpleDateFormat("h:mm a");
			String formattedDate=f.format(date1).toString();
			return formattedDate;
		} catch(Exception e) { e.printStackTrace(); return "";}	
	}
	
	private static int find(String[] arr, String target)
	{
		for(int i=0; i<arr.length; i++)
		{
			if(arr[i].toString().equals(target))
				return i;
		}
		return -1;
	}
}

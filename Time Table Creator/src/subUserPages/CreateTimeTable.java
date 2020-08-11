package subUserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import homePage.Login;
import panels.Cell;
import panels.TimePanel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuListener;

import dialogs.ConfigureClasses;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class CreateTimeTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static JFrame frame;
	
	private static JLabel title;
	private static JSeparator separator;
	private static JPanel page1,schedule,page2,time,day,hour;
	private static Cell cells[][];
	private static TimePanel duration[];
	private static JComboBox<String> comboBox,comboBox2,comboBox3,comboBox4,comboBox5,advisor;
	
	private static String[] code,name,dept_id,dept_name,sec,course_id,course_name,staff_id,staff_name,room_id,courseL_id,courseE_id,groupL,groupE,advisor_id,advisor_name;
	private static int[] sem;
	
	private static String college,department,section,classroom;
	private static int semester,hours;
	public static boolean saved=true;
	private static JButton proceed;
	private static JPanel proceedPanel;
	private static JSpinner sectionCount,hourCount;
	private static JButton btnBack;
	
	public CreateTimeTable(JFrame frame) {
		this.frame=frame;
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
		
		page2 = new JPanel();
		page2.setBounds(20, 10, 1483, 747);
		add(page2);
		page2.setVisible(false);
		page2.setLayout(null);
		
		schedule = new JPanel();
		schedule.setOpaque(false);
		schedule.setBounds(101, 96, 1361, 606);
		page2.add(schedule);
		
		JLabel lblDayHour = new JLabel("Day / Hour");
		lblDayHour.setForeground(new Color(255, 255, 255));
		lblDayHour.setOpaque(true);
		lblDayHour.setBackground(new Color(255, 0, 0));
		lblDayHour.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		lblDayHour.setHorizontalAlignment(SwingConstants.CENTER);
		lblDayHour.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblDayHour.setBounds(0, 0, 100, 98);
		page2.add(lblDayHour);
		
		hour = new JPanel();
		hour.setBackground(new Color(255, 215, 0));
		hour.setBounds(101, 0, 1361, 32);
		page2.add(hour);
		
		day = new JPanel();
		day.setBackground(new Color(255, 215, 0));
		day.setBounds(0, 96, 100, 606);
		page2.add(day);
		
		JButton clear = new JButton("CLEAR");
		clear.setFocusPainted(false);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=1; i<7; i++)
					for(int j=1; j<hours+1; j++) {
						cells[i][j].bg.clearSelection();
						cells[i][j].course.setSelectedIndex(0);
						cells[i][j].staff.setSelectedIndex(0);
					}
			}
		});
		clear.setHorizontalTextPosition(SwingConstants.CENTER);
		clear.setForeground(Color.WHITE);
		clear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		clear.setFocusTraversalKeysEnabled(false);
		clear.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		clear.setBackground(Color.RED);
		clear.setBounds(543, 705, 126, 32);
		page2.add(clear);
		
		btnBack = new JButton("BACK");
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
				if(!saved) {
					int i=JOptionPane.showOptionDialog(null, "Changes are not saved to the database! Do you want to save changes?","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","Exit Anyway"}, "Yes");
					if(i==JOptionPane.YES_OPTION) {
						saveTimeTable();
						return;
					}
					else if(i==JOptionPane.CLOSED_OPTION)
					{
						return;
					}
				}
				page2.setVisible(false);
				page1.setVisible(true);
				clearPageContents();
			}
		});
		btnBack.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnBack.setBackground(Color.ORANGE);
		btnBack.setBounds(407, 705, 126, 32);
		page2.add(btnBack);
		
		time = new JPanel();
		time.setBackground(new Color(255, 215, 0));
		time.setBounds(101, 32, 1361, 66);
		page2.add(time);
		
		JButton save = new JButton("SAVE");
		save.setFocusPainted(false);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTimeTable();
			}
		});
		save.setHorizontalTextPosition(SwingConstants.CENTER);
		save.setForeground(Color.WHITE);
		save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		save.setFocusTraversalKeysEnabled(false);
		save.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		save.setBackground(new Color(50, 205, 50));
		save.setBounds(679, 705, 126, 32);
		page2.add(save);
		
		JButton submit = new JButton("Final Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					if(classroom.equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please assign a room number for section before final submit.");
						return;
					}
					if(advisor.getSelectedIndex()<=0)
					{
						JOptionPane.showMessageDialog(null,"Please choose a academic advisor for the section.");
						return;
					}
					saveTimeTable();
					JOptionPane.showMessageDialog(null, "You section time table is finally submitted. You can view this under Export Time table page.");
			}
		});
		submit.setFocusPainted(false);
		submit.setHorizontalTextPosition(SwingConstants.CENTER);
		submit.setForeground(Color.WHITE);
		submit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		submit.setFocusTraversalKeysEnabled(false);
		submit.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		submit.setBackground(Color.BLUE);
		submit.setBounds(815, 705, 136, 32);
		page2.add(submit);
		
		JLabel lblClickHereTo = new JLabel("Click here to configure courses  (Theory / Lab / Elective )");
		lblClickHereTo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new ConfigureClasses(frame,course_id,course_name,staff_id,staff_name,college,department,semester,section);
			}
		});
		lblClickHereTo.setToolTipText("Click here to register");
		lblClickHereTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblClickHereTo.setHorizontalAlignment(SwingConstants.LEFT);
		lblClickHereTo.setForeground(Color.BLUE);
		lblClickHereTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClickHereTo.setBounds(10, 712, 387, 19);
		page2.add(lblClickHereTo);
		
		JLabel lblAcademicAdvisor = new JLabel("Academic Advisor :");
		lblAcademicAdvisor.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAcademicAdvisor.setHorizontalAlignment(SwingConstants.LEFT);
		lblAcademicAdvisor.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblAcademicAdvisor.setBounds(1008, 712, 158, 26);
		page2.add(lblAcademicAdvisor);
		
		advisor = new JComboBox<String>();
		advisor.setOpaque(false);
		advisor.setBackground(Color.WHITE);
		advisor.setBounds(1162, 715, 300, 21);
		page2.add(advisor);
		
		title = new JLabel("Create Time Table");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setBounds(10, 10, 1376, 43);
		add(title);
		
		separator = new JSeparator();
		separator.setBounds(569, 48, 233, 2);
		add(separator);
		
		page1 = new JPanel();
		page1.setVisible(true);
		page1.setOpaque(false);
		page1.setBounds(296, 125, 861, 399);
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
				
				if(comboBox4.getSelectedIndex()>0) {
					proceedPanel.setVisible(true);
					college=code[comboBox.getSelectedIndex()];
					department=dept_id[comboBox2.getSelectedIndex()];
					semester=sem[comboBox3.getSelectedIndex()];
					section=sec[comboBox4.getSelectedIndex()];
					getClassRooms();
					updateSpinner();
				}
				else
					proceedPanel.setVisible(false);
			}
			
		});
		comboBox4.setBackground(Color.WHITE);
		comboBox4.setBounds(168, 168, 278, 28);
		page1.add(comboBox4);
		
		proceedPanel = new JPanel();
		proceedPanel.setOpaque(false);
		proceedPanel.setBounds(186, 216, 436, 173);
		page1.add(proceedPanel);
		proceedPanel.setLayout(null);
		
		proceed = new JButton("Proceed >");
		proceed.setBounds(177, 131, 126, 32);
		proceedPanel.add(proceed);
		proceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(hourCount.getValue().toString()) != hours)
				{
					try {
						stmt=con.createStatement();
						stmt.executeUpdate("DELETE FROM Schedule WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
					}
					catch(SQLException ee)
					{
						JOptionPane.showMessageDialog(null, "Please try again!","Error",JOptionPane.ERROR_MESSAGE);
						ee.printStackTrace();
						return;
					}
				}
				hours=Integer.parseInt(hourCount.getValue().toString());
				page2.setVisible(true);
				page1.setVisible(false);
				title.setVisible(false);
				separator.setVisible(false);
				createStaffView();
				getData();
				getAdvisor();
				pageDesigner();
				new ConfigureClasses(frame,course_id,course_name,staff_id,staff_name,college,department,semester,section);
			}
		});
		proceed.setHorizontalTextPosition(SwingConstants.CENTER);
		proceed.setForeground(Color.WHITE);
		proceed.setFont(new Font("Tahoma", Font.PLAIN, 18));
		proceed.setFocusTraversalKeysEnabled(false);
		proceed.setFocusPainted(false);
		proceed.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		proceed.setBackground(new Color(51, 0, 255));
		
		JLabel lblClassRoomNumber = new JLabel("Class Room Number :");
		lblClassRoomNumber.setBounds(0, 0, 178, 26);
		proceedPanel.add(lblClassRoomNumber);
		lblClassRoomNumber.setHorizontalTextPosition(SwingConstants.CENTER);
		lblClassRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
		lblClassRoomNumber.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		comboBox5 = new JComboBox<String>();
		comboBox5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sectionCount.requestFocus();
				if(!classroom.equals("") && !comboBox5.getSelectedItem().equals(classroom))
				{
					int i=JOptionPane.showOptionDialog(null, "This section already assigned to class room. "+classroom
							+ "? \nDo you want to assign new class room?","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
					if(i!=JOptionPane.YES_OPTION)
						comboBox5.setSelectedIndex(find(room_id,classroom));
				}
			}
		});
		comboBox5.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if(comboBox5.getItemCount()<=1)
					JOptionPane.showMessageDialog(null, "Sufficient class rooms are not available in your college.\nAll existing class rooms have been occupied.");
			}
		});
		comboBox5.setBounds(201, 0, 235, 28);
		proceedPanel.add(comboBox5);
		comboBox5.setBackground(Color.WHITE);
		
		JLabel lblTotalStudentCount = new JLabel("Total student count of section :");
		lblTotalStudentCount.setBounds(68, 36, 235, 26);
		proceedPanel.add(lblTotalStudentCount);
		lblTotalStudentCount.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalStudentCount.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalStudentCount.setFont(new Font("Georgia", Font.PLAIN, 16));
		
		sectionCount = new JSpinner();
		sectionCount.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		sectionCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sectionCount.setBounds(307, 38, 60, 24);
		proceedPanel.add(sectionCount);
		
		JLabel lblNumberOfClasses = new JLabel("Number of classes per day :");
		lblNumberOfClasses.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNumberOfClasses.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumberOfClasses.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNumberOfClasses.setBounds(66, 72, 205, 26);
		proceedPanel.add(lblNumberOfClasses);
		
		hourCount = new JSpinner();
		hourCount.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(hours!=0 && Integer.parseInt(hourCount.getValue().toString()) != hours)
					JOptionPane.showMessageDialog(null, "This section already has a time table with "+hours+" hours/day.\n"
							+ "If you change the number of hours, existing time table will be deleted.","Time Table Creator",JOptionPane.WARNING_MESSAGE);
			}
		});
		hourCount.setModel(new SpinnerNumberModel(7, 1, 10, 1));
		hourCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hourCount.setBounds(281, 73, 60, 24);
		proceedPanel.add(hourCount);
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

	private void getClassRooms()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT room_number FROM Section WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			classroom="";
			if(rs.next())	
				classroom=rs.getString(1);
			String query="SELECT room_number FROM ClassRoom WHERE code='"+code[comboBox.getSelectedIndex()]+"'"
					+ " AND room_number NOT IN ("
					+ "SELECT room_number FROM Section WHERE room_number<>'' AND room_number<>'"+classroom+"'"
					+ ") ORDER BY room_number;";
			rs=stmt.executeQuery(query);
			while(rs.next()) 
				count++;
			room_id=new String[count+1];
			rs.beforeFirst();
			int i=1;
			room_id[0]="--- Select your room number --- ";
			while(rs.next()) {
				room_id[i]=rs.getString(1);
				i++;
			}
			comboBox5.setModel(new DefaultComboBoxModel<String>(room_id));
			if(!classroom.equals(""))
				comboBox5.setSelectedIndex(find(room_id,classroom));
			else 
				comboBox5.setSelectedIndex(0);

		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class details");
			e.printStackTrace();
		}
	}
	
	private void updateSpinner()
	{
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT hours,no_of_students FROM Section WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			rs.next();
			hours=rs.getInt(1);
			if(hours>0)
				hourCount.setValue(hours);
			sectionCount.setValue(rs.getInt(2));
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	private static void pageDesigner()
	{
		int i,j;
		int hours=Integer.parseInt(hourCount.getValue().toString());
		time.setLayout(new GridLayout(1, hours, 0, 0));
		day.setLayout(new GridLayout(6, 1, 0, 0));
		hour.setLayout(new GridLayout(1, hours, 0, 0));
		schedule.setLayout(new GridLayout(6, hours, 5, 5));
		cells=new Cell[7][hours+1]; 
		duration=new TimePanel[hours+1];
		for(i=0; i<7; i++)
			for(j=0; j<hours+1; j++)
			{
				if(i>0 && j==0) {
					JLabel day=new JLabel(weekDay(i));
					day.setHorizontalAlignment(SwingConstants.CENTER);
					day.setVerticalAlignment(SwingConstants.CENTER);
					day.setFont(new Font("Segoe Print", Font.BOLD, 15));
					CreateTimeTable.day.add(day);
				}
				else if(i==0 && j>0) {
					JLabel hour=new JLabel(String.valueOf(j));
					hour.setHorizontalAlignment(SwingConstants.CENTER);
					hour.setVerticalAlignment(SwingConstants.CENTER);
					hour.setFont(new Font("Segoe Print", Font.BOLD, 18));
					CreateTimeTable.hour.add(hour);
					
					duration[j]=new TimePanel(hours);
					duration[j].time1.setName(String.valueOf(j));
					duration[j].time2.setName(String.valueOf(j));
					time.add(duration[j]);
					duration[j].time1.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							saved=false;
							int index=Integer.parseInt(((JSpinner)e.getSource()).getName());
							if(timeDifference(duration[index].time1,duration[index].time2)<0)
								duration[index].time2.setValue(duration[index].time1.getValue());
						}
					});
					duration[j].time2.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							saved=false;
							int index=Integer.parseInt(((JSpinner)e.getSource()).getName());
							if(index<hours && timeDifference(duration[index].time2, duration[index+1].time1)<0)
								duration[index+1].time1.setValue(duration[index].time2.getValue());
						}
					});
				}
				else if(i>0 && j>0) {
					cells[i][j]=new Cell(hours);
					cells[i][j].hour=j;
					cells[i][j].day=i;
					cells[i][j].course.setEnabled(false);
					cells[i][j].staff.setEnabled(false);
					schedule.add(cells[i][j]);
					String[] str= {"Select Course --- "};
					cells[i][j].course.setModel(new DefaultComboBoxModel<String>(str));
					str[0]= "Select Staff --- ";
					cells[i][j].staff.setModel(new DefaultComboBoxModel<String>(str));
					
					cells[i][j].t.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							saved=false;
							Cell obj=(Cell)((JRadioButton)e.getSource()).getParent();
							obj.course.setEnabled(true);
							obj.staff.setEnabled(true);
							obj.staff.setVisible(true);
							if(((JRadioButton)e.getSource()).isSelected()) {
								updateTheory(obj);
								obj.course.setSelectedIndex(obj.t1);
							}
						}
					});
					cells[i][j].course.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							saved=false;
							Cell obj=(Cell)((Component)e.getSource()).getParent();
							if(obj.t.isSelected())
								updateTheoryStaff(obj,obj.courseT_id[obj.course.getSelectedIndex()]);
							if(obj.course.getSelectedIndex()>0)
							{
								obj.course.setBackground(new Color(255,255,150));
								obj.course.transferFocus();
							}
							else 
								obj.course.setBackground(Color.white);
							
							if(obj.t.isSelected()) 
								obj.t1=obj.course.getSelectedIndex();
							else if(obj.l.isSelected())
							{
								if(detectCollision(obj))
								{
									obj.course.setSelectedIndex(obj.l1);
									if(obj.l1==0)
										obj.course.setBackground(Color.white);
									return;	
								}
								obj.l1=obj.course.getSelectedIndex();
							}
							else if(obj.e.isSelected())
							{
								if(detectCollision(obj))
								{
									obj.course.setSelectedIndex(obj.l1);
									if(obj.e1==0)
										obj.course.setBackground(Color.white);
									return;
								}
								obj.e1=obj.course.getSelectedIndex();
							}
						}
					});
					cells[i][j].l.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							saved=false;
							Cell obj=(Cell)((JRadioButton)e.getSource()).getParent();
							obj.course.setEnabled(true);
							obj.staff.setEnabled(false);
							obj.staff.setVisible(false);
							if(((JRadioButton)e.getSource()).isSelected()) {
								updateLab(obj);
								obj.course.setSelectedIndex(obj.l1);
							}
						}
					});
					cells[i][j].e.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							saved=false;
							Cell obj=(Cell)((JRadioButton)e.getSource()).getParent();
							obj.course.setEnabled(true);
							obj.staff.setEnabled(false);
							obj.staff.setVisible(false);
							if(((JRadioButton)e.getSource()).isSelected())
							{
								updateElective(obj);
								obj.course.setSelectedIndex(obj.e1);
							}
						}
					});
					
					cells[i][j].staff.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							saved=false;
							Cell obj=(Cell)((Component)e.getSource()).getParent();
							if(obj.staff.getSelectedIndex()>0) {
								if(obj.t.isSelected())
								{
									if(detectCollision(obj))
									{
										obj.course.setSelectedIndex(obj.t2);
										if(obj.e1==0)
											obj.course.setBackground(Color.white);
										return;
									}
									obj.t2=obj.course.getSelectedIndex();
								}
								obj.staff.setBackground(new Color(255,255,150));
								obj.staff.transferFocus();
							}
							else 
								obj.staff.setBackground(Color.white);
							if(obj.t.isSelected())
								obj.t2=obj.staff.getSelectedIndex();
						}
					});
					
					if(i==6)
					{
						try {
							String time="08:00 AM";
							SimpleDateFormat f=new SimpleDateFormat("hh:mm a");
							duration[j].time1.setValue(f.parse(time));
							duration[j].time2.setValue(f.parse(time));
							saved=true;
						}
						catch(Exception ee) {}
					}
				}
			}
	}
	
	private static void updateTheory(Cell obj)
	{
		try {
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT DISTINCT(course_id) FROM Theory WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			int count=0,i=1;
			while(rs.next()){
				count++;
			}
			obj.courseT_id=new String[count+1];
			obj.courseT_name=new String[count+1];
			rs.beforeFirst();
			obj.courseT_name[0]="Select Course ---";
			while(rs.next()) {
				obj.courseT_id[i]=rs.getString(1);
				String str[]=course_name[find(course_id,obj.courseT_id[i])].split(" - ");
				obj.courseT_name[i]=str[1];
				i++;
			}
			obj.course.setModel(new DefaultComboBoxModel<String>(obj.courseT_name));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching data.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private static void updateTheoryStaff(Cell obj,String id)
	{
		try {
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT staff_id FROM Theory WHERE course_id='"+id+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			int count=0,i=1;
			while(rs.next()){
				count++;
			}
			obj.staffT_id=new String[count+1];
			obj.staffT_name=new String[count+1];
			rs.beforeFirst();
			obj.staffT_name[0]="Select Staff ---";
			while(rs.next()) {
				obj.staffT_id[i]=rs.getString(1);
				obj.staffT_name[i]=staff_name[find(staff_id,obj.staffT_id[i])];
				i++;
			}
			obj.staff.setModel(new DefaultComboBoxModel<String>(obj.staffT_name));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching data.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	private static void updateLab(Cell obj)
	{
		obj.course.setModel(new DefaultComboBoxModel<String>(groupL));
	}
	
	private static void updateElective(Cell obj)
	{
		obj.course.setModel(new DefaultComboBoxModel<String>(groupE));
	}
	
	private static void clearPageContents()
	{
		day.removeAll();
		time.removeAll();
		hour.removeAll();
		schedule.removeAll();
	}
	
	
	private static String weekDay(int i)
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
	
	private static void createStaffView()
	{
		try {
			stmt=con.createStatement();
			rs=con.getMetaData().getTables("TIME_TABLE_DATA",null,"Staff_view",null);		
			if(rs.next())
			{
				stmt.executeUpdate("DROP VIEW Staff_view;");
			}
			stmt.executeUpdate("CREATE VIEW Staff_view AS ("
					+ "SELECT staff_id,staff_name FROM Staff WHERE dept_id='"+department+"' AND code='"+college+"');");
		}
		catch(SQLException ee)
		{
			ee.printStackTrace();
		}
	}
	
	public static void getInitialData()
	{
		try {
			ResultSet r;
			SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
			stmt=con.createStatement();
			r=stmt.executeQuery("SELECT * FROM Schedule WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(r.next()){
				String ci=r.getString(1);
				String si=r.getString(2);
				int x=r.getInt(3), y=r.getInt(4);
				String type=(r.getString(5)).substring(0,2);
				try {
					java.util.Date date;
					date=f.parse(getTime(r.getString(10)));
					duration[y].time1.setValue(date);
					date=f.parse(getTime(r.getString(11)));
					duration[y].time2.setValue(date);
				}
				catch(Exception e) {}
			
				if(type.equals("Th"))
				{
					cells[x][y].t.setSelected(true);
					cells[x][y].t1=find(cells[x][y].courseT_id,ci);
					cells[x][y].course.setSelectedIndex(cells[x][y].t1);
					cells[x][y].t2=find(cells[x][y].staffT_id,si);
					cells[x][y].staff.setSelectedIndex(cells[x][y].t2);
				}
				else if((type.substring(0, 2)).equals("L-"))
				{
					cells[x][y].l.setSelected(true);
					cells[x][y].l1=find(groupL,r.getString(5));
					cells[x][y].course.setSelectedIndex(cells[x][y].l1);
				}
				else if((type.substring(0, 2)).equals("E-"))
				{
					cells[x][y].e.setSelected(true);
					cells[x][y].e1=find(groupE,r.getString(5));
					cells[x][y].course.setSelectedIndex(cells[x][y].e1);
				}
			}
			r=stmt.executeQuery("SELECT advisor FROM Section WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			r.next();
			if(!r.getString(1).equals(""))
			{
				advisor.setSelectedIndex(find(advisor_id,r.getString(1)));
			}

		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching data.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private static void getData()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT course_id,course_name FROM Course WHERE sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()) 
				count++;
			course_id=new String[count+1];
			course_name=new String[count+1];
			rs.beforeFirst();
			int i=1;
			course_name[0]="--- Select Course --- ";
			while(rs.next()) {
				course_id[i]=rs.getString(1);
				course_name[i]=rs.getString(1)+" - "+rs.getString(2);
				i++;
			}
			
			count=0;
			rs=stmt.executeQuery("SELECT staff_id,staff_name FROM Staff_view;");
			while(rs.next()) 
				count++;
			staff_id=new String[count+1];
			staff_name=new String[count+1];
			rs.beforeFirst();
			i=1;
			staff_name[0]="--- Select Staff ---";
			while(rs.next()) {
				staff_id[i]=rs.getString(1);
				staff_name[i]=rs.getString(1)+" - "+rs.getString(2);
				i++;
			}
			
			count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT DISTINCT(lab_group) FROM LabGroup WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			i=1;
			while(rs.next()){
				count++;
			}
			groupL=new String[count+1];
			rs.beforeFirst();
			groupL[0]="Select Lab Group ---";
			while(rs.next()) {
				groupL[i]=rs.getString(1);
				i++;
			}
			
			count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT DISTINCT(elective_group) FROM ElectiveGroup WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			i=1;
			while(rs.next()){
				count++;
			}
			groupE=new String[count+1];
			rs.beforeFirst();
			groupE[0]="Select Elective group ---";
			while(rs.next()) {
				groupE[i]=rs.getString(1);
				i++;
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching course details");
			e.printStackTrace();
		}
	}
	
	public static void getAdvisor()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT DISTINCT(staff_id) FROM Theory WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()) 
				count++;
			advisor_id=new String[count+1];
			advisor_name=new String[count+1];
			rs.beforeFirst();
			int i=1;
			advisor_name[0]="--- Select Academic advisor --- ";
			while(rs.next()) {
				advisor_id[i]=rs.getString(1);
				advisor_name[i]=staff_name[find(staff_id,rs.getString(1))];
				i++;
			}
			advisor.setModel(new DefaultComboBoxModel<String>(advisor_name));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching course details");
			e.printStackTrace();
		}
	}
	
	private static boolean detectCollision(Cell obj)
	{
		try {
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(obj.t.isSelected())
			{
				String staff=obj.staffT_id[obj.staff.getSelectedIndex()];
				String query="SELECT class_type,section,sem FROM Schedule WHERE section<>'"+section+"' AND staff_id='"+staff+"' AND hour_no="+obj.hour+" AND week_day="+obj.day+" AND code='"+college+"';";
				//String type="";
				//if((rs.getString(1)).equals("Theory")) type="Theory class";
				//else(substr(rs.))
				rs=stmt.executeQuery(query);
				if(rs.next())
				{
					JOptionPane.showMessageDialog(null, "Faculty "+staff_name[find(staff_id,staff)] +" has other class during this hour.");
					return true;
				}
			}
			else if(obj.l.isSelected())
			{
				String[] str;
				int count=0;
				String query="SELECT staff_id FROM Lab WHERE lab_group='"+obj.course.getSelectedItem()+"' AND code='"+college+"';";
				rs=stmt.executeQuery(query);
				while(rs.next())
					count++;
				str=new String[count];
				for(String staff:str)
				{
					query="SELECT class_type,section,sem FROM Schedule WHERE staff_id='"+staff+"' AND hour_no="+obj.hour+" AND week_day="+obj.day+" AND code='"+college+"';";
					rs=stmt.executeQuery(query);
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "Faculty "+staff_name[find(staff_id,staff)] +" has other class during this hour.");
						return true;
					}
				}
			}
			else if(obj.e.isSelected())
			{
				String[] str;
				int count=0;
				String query="SELECT staff_id FROM Elective WHERE elective_group='"+obj.course.getSelectedItem()+"' AND code='"+college+"';";
				rs=stmt.executeQuery(query);
				while(rs.next())
					count++;
				str=new String[count];
				for(String staff:str)
				{
					query="SELECT class_type,section,sem FROM Schedule WHERE staff_id='"+staff+"' AND hour_no="+obj.hour+" AND week_day="+obj.day+" AND code='"+college+"';";
					rs=stmt.executeQuery(query);
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "Faculty "+staff_name[find(staff_id,staff)] +" has other class during this hour.");
						return true;
					}
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
			return false;
	}
	
	private static int find(String[] arr, String target)
	{
		for(int i=1; i<arr.length; i++)
		{
			if(arr[i].toString().equals(target))
				return i;
		}
		return -1;
	}
	
	
	private static void saveTimeTable()
	{
		long val=isValidTime();
		Connection conn=null;
		ResultSet res=null;
		Statement stm=null;
		try {
			conn=DriverManager.getConnection("jdbc:sqlserver://LAPTOP-BAB1964C","DBMS_Project","project");
			stm=conn.createStatement();
		}
		catch(SQLException e) {
			System.out.println("Unable to connect to database.");
			e.printStackTrace();
		}
		if(val!=0)
		{
			JOptionPane.showMessageDialog(null, "You have entered invalid duration near class hours - "+val
					+"\nInstructions : \n > Starting time of each class should be less than or equal to its ending time.\n"
					+ " > Starting time of succeeding classes should be greater than or equal to ending time of preceeding classes.","Invalid Class Timings",JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			stmt=con.createStatement();
			con.setAutoCommit(false);
			String qry="UPDATE Section SET no_of_students="+sectionCount.getValue()+", hours="+hours;
			if(comboBox5.getSelectedIndex()>0)
				qry+=", room_number='"+room_id[comboBox5.getSelectedIndex()]+"'";
			qry+=",final=0 WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';";
			stmt.executeUpdate(qry);
			stmt.executeUpdate("UPDATE Section SET advisor='"+advisor_id[advisor.getSelectedIndex()]+"', final=1 WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			stmt.executeUpdate("DELETE FROM Time WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			stmt.executeUpdate("DELETE FROM Schedule WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			for(int j=1;j<hours+1;j++)
			{
				stmt.executeUpdate("INSERT INTO Time VALUES("+j+",'"+getTime(duration[j].time1)+"','"+getTime(duration[j].time2)+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
				for(int i=1;i<7;i++)
				{
					if(cells[i][j].t.isSelected())
					{
						if(cells[i][j].staff.getSelectedIndex()>0 && cells[i][j].course.getSelectedIndex()>0)
						{
							String course_id=cells[i][j].courseT_id[cells[i][j].course.getSelectedIndex()];
							String staff_id=cells[i][j].staffT_id[cells[i][j].staff.getSelectedIndex()];
							String query="INSERT INTO Schedule VALUES('"+course_id+"','"+staff_id+"',"+i+","+j+",'Theory','"+section+"',"+semester+",'"+department+"','"+college+"','"+getTime(duration[j].time1)+"','"+getTime(duration[j].time2)+"');";
							stmt.executeUpdate(query);
						}
					}
					else if(cells[i][j].l.isSelected())
					{
						if(cells[i][j].course.getSelectedIndex()>0)
						{
							stm.executeUpdate("USE TIME_TABLE_DATA;");
							String add="SELECT lab_group,course_id,staff_id FROM Lab WHERE lab_group='"+cells[i][j].course.getSelectedItem()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';";
							res=stm.executeQuery(add);
							while(res.next()) {
								String query="INSERT INTO Schedule VALUES('"+res.getString(2)+"','"+res.getString(3)+"',"+i+","+j+",'"+res.getString(1)+"','"+section+"',"+semester+",'"+department+"','"+college+"','"+getTime(duration[j].time1)+"','"+getTime(duration[j].time2)+"');";
								stmt.executeUpdate(query);
							}
						}
					}
					else if(cells[i][j].e.isSelected())
					{
						if(cells[i][j].course.getSelectedIndex()>0)
						{
							stm.executeUpdate("USE TIME_TABLE_DATA;");
							String add="SELECT elective_group,course_id,staff_id FROM Elective WHERE elective_group='"+cells[i][j].course.getSelectedItem()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';";
							res=stm.executeQuery(add);
							while(res.next()) {
								String query="INSERT INTO Schedule VALUES('"+res.getString(2)+"','"+res.getString(3)+"',"+i+","+j+",'"+res.getString(1)+"','"+section+"',"+semester+",'"+department+"','"+college+"','"+getTime(duration[j].time1)+"','"+getTime(duration[j].time2)+"');";
								stmt.executeUpdate(query);
							}
						}
					}
					else
					{
						stmt.executeUpdate("DELETE FROM Schedule WHERE week_day="+i+" AND hour_no="+j+" AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
					}
				}
				if(advisor.getSelectedIndex()>0)
					stmt.executeUpdate("UPDATE Section SET advisor='"+advisor_id[advisor.getSelectedIndex()]+"' WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"'");
			}
			con.commit();
			JOptionPane.showMessageDialog(null, "Saved successfully.");
			page2.setVisible(false);
			page1.setVisible(true);
			clearPageContents();
		}
		catch(SQLException e)
		{
			try {
				con.rollback();
			} catch(SQLException ee) {}
			JOptionPane.showMessageDialog(null, "Error occured while saving data.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		finally {
			try {
				con.setAutoCommit(true);
			}
			catch(SQLException e) {}
		}
	}
	
	private static int isValidTime()
	{
		if(timeDifference(duration[1].time1,duration[1].time2)<0)
			return 1;
		for(int i=2; i<hours+1; i++)
		{
			if(timeDifference(duration[i].time1,duration[i].time2)<0 || timeDifference(duration[i-1].time2, duration[i].time1)<0)
				return i;
		}
		return 0;
	}
	
	public static long timeDifference(JSpinner time1,JSpinner time2)
	{
		try {
			SimpleDateFormat f=new SimpleDateFormat("HH:mm:ss");
			f.setTimeZone(TimeZone.getTimeZone("UTC"));
			java.util.Date date1=f.parse(getTime(time1));
			java.util.Date date2=f.parse(getTime(time2));
			long diff=(date2.getTime()-date1.getTime())/(1000*60);
			return diff;
		}
		catch(Exception e) {e.printStackTrace();}
		return -1;
	}
	
	private static String getTime(JSpinner obj)
	{
		StringBuffer time=new StringBuffer(obj.getValue().toString());
		return time.substring(11, 19);
	}
	
	private static String getTime(String time)
	{
		StringBuffer t=new StringBuffer(time);
		return t.substring(0,7);
	}

	public static String[] getStaffId()
	{
		return staff_id;
	}
	public static String[] getStaffName()
	{
		return staff_name;
	}
}

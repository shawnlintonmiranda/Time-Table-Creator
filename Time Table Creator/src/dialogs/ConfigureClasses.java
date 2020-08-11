package dialogs;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import homePage.Login;
import subUserPages.CreateTimeTable;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConfigureClasses extends JDialog {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private static String[] course_id,course_name,staff_id,staff_name,groupL_id,groupE_id;
	private static String college,department,section;
	private static int semester;
	
	private static JComboBox<String> courseT,groupL,groupE,courseL,courseE;
	private static DefaultListModel<String> model;
	private static JList<String> staffT,staffL,staffE;
	private static JPanel Theory,Lab,Elective;
	private static DefaultTableModel tableModel;
	private static JTable table;
	private static JScrollPane scrollPane_1;
	private static JTabbedPane tabbedPane;
	private static JTextField labGroup,eleGroup;
	
	public ConfigureClasses(JFrame frame,String[] ci,String[] cn, String[] si, String[] sn, String c,String d,int sem,String s)
	{
		super(frame,"Manage Section",Dialog.ModalityType.DOCUMENT_MODAL);
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			if(CreateTimeTable.saved==true) {
				CreateTimeTable.getAdvisor();
				CreateTimeTable.getInitialData();
			}
			}
			
		});
		getContentPane().setBackground(new Color(255, 248, 220));
		getContentPane().setLayout(null);
		setSize(600,768);
		setResizable(false);
		setLocationRelativeTo(null);
		course_id=ci;
		course_name=cn;
		staff_id=si;
		staff_name=sn;
		college=c;
		department=d;
		semester=sem;
		section=s;
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==0 && tableModel!=null)
					updateTable();
				else if(tabbedPane.getSelectedIndex()==1)
				{
					getGroupL();
					updateTable();
				}
				else if(tabbedPane.getSelectedIndex()==2)
				{
					getGroupE();
					updateTable();
				}
			}
		});
		tabbedPane.setBounds(10, 10, 561, 343);
		getContentPane().add(tabbedPane);
		
		Theory = new JPanel();
		Theory.setBackground(Color.WHITE);
		tabbedPane.addTab("Theory", null, Theory, null);
		
		Lab = new JPanel();
		Lab.setBackground(Color.WHITE);
		tabbedPane.addTab("Lab", null, Lab, null);
		Lab.setLayout(null);
		
		JLabel lblCreateLabGroup = new JLabel("Create Lab Group and Add Lab Subjects");
		lblCreateLabGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCreateLabGroup.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLabGroup.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblCreateLabGroup.setBounds(20, 10, 521, 26);
		Lab.add(lblCreateLabGroup);
		
		labGroup = new JTextField();
		labGroup.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(labGroup.getText().length()>=30) e.consume();
			}
		});
		labGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labGroup.setColumns(10);
		labGroup.setBounds(30, 46, 342, 25);
		Lab.add(labGroup);
		
		JButton addLabGroup = new JButton("Add Lab Group");
		addLabGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLabGroup();
			}
		});
		addLabGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		addLabGroup.setForeground(Color.WHITE);
		addLabGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addLabGroup.setFocusTraversalKeysEnabled(false);
		addLabGroup.setFocusPainted(false);
		addLabGroup.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		addLabGroup.setBackground(new Color(51, 0, 255));
		addLabGroup.setBounds(382, 42, 164, 32);
		Lab.add(addLabGroup);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(45, 81, 476, 7);
		Lab.add(separator_1);
		
		JLabel lblSelectLabGroup = new JLabel("Select Lab Group :");
		lblSelectLabGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectLabGroup.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectLabGroup.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectLabGroup.setBounds(10, 91, 147, 26);
		Lab.add(lblSelectLabGroup);
		
		groupL = new JComboBox<String>();
		groupL.setOpaque(false);
		groupL.setBackground(Color.WHITE);
		groupL.setBounds(157, 94, 268, 21);
		Lab.add(groupL);
		
		JButton deleteLabGroup = new JButton("Delete");
		deleteLabGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteLabGroup();
			}
		});
		deleteLabGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteLabGroup.setForeground(Color.WHITE);
		deleteLabGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteLabGroup.setFocusTraversalKeysEnabled(false);
		deleteLabGroup.setFocusPainted(false);
		deleteLabGroup.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		deleteLabGroup.setBackground(Color.RED);
		deleteLabGroup.setBounds(435, 88, 102, 32);
		Lab.add(deleteLabGroup);
		
		JLabel label_1 = new JLabel("Select staff :");
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_1.setBounds(10, 163, 111, 26);
		Lab.add(label_1);
		model=new DefaultListModel<String>();
		for(int i=1; i<staff_name.length; i++)
			model.addElement(staff_name[i]);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(110, 163, 382, 119);
		Lab.add(scrollPane_2);
		
		staffL = new JList<String>();
		scrollPane_2.setViewportView(staffL);
		staffL.setModel(model);
		
		JButton lab = new JButton("SUBMIT");
		lab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(groupL.getSelectedIndex()>0)
				{
					if(courseL.getSelectedIndex()>0)
					{
						addLab();
						updateTable();
					}
					else
						JOptionPane.showMessageDialog(null, "Please select Course.");
				}
				else
					JOptionPane.showMessageDialog(null, "Please select Lab Group.");
			}
		});
		lab.setHorizontalTextPosition(SwingConstants.CENTER);
		lab.setForeground(Color.WHITE);
		lab.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lab.setFocusTraversalKeysEnabled(false);
		lab.setFocusPainted(false);
		lab.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		lab.setBackground(new Color(51, 0, 255));
		lab.setBounds(225, 284, 126, 32);
		Lab.add(lab);
		
		JLabel label_2 = new JLabel("Select course :");
		label_2.setHorizontalTextPosition(SwingConstants.CENTER);
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_2.setBounds(10, 127, 120, 26);
		Lab.add(label_2);
		
		courseL = new JComboBox<String>();
		courseL.setOpaque(false);
		courseL.setBackground(Color.WHITE);
		courseL.setBounds(125, 130, 300, 21);
		courseL.setModel(new DefaultComboBoxModel<String>(course_name));
		Lab.add(courseL);
		
		Elective = new JPanel();
		Elective.setBackground(Color.WHITE);
		tabbedPane.addTab("Elective", null, Elective, null);
		Elective.setLayout(null);
		
		JLabel lblCreateElectiveGroup = new JLabel("Create Elective Group and Add Elective Subjects");
		lblCreateElectiveGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCreateElectiveGroup.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateElectiveGroup.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblCreateElectiveGroup.setBounds(10, 10, 521, 26);
		Elective.add(lblCreateElectiveGroup);
		
		eleGroup = new JTextField();
		eleGroup.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(eleGroup.getText().length()>=30) e.consume();
			}
		});
		eleGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		eleGroup.setColumns(10);
		eleGroup.setBounds(20, 46, 342, 25);
		Elective.add(eleGroup);
		
		JButton addEleGroup = new JButton("Add Elective Group");
		addEleGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addElectiveGroup();
			}
		});
		addEleGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		addEleGroup.setForeground(Color.WHITE);
		addEleGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addEleGroup.setFocusTraversalKeysEnabled(false);
		addEleGroup.setFocusPainted(false);
		addEleGroup.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		addEleGroup.setBackground(new Color(51, 0, 255));
		addEleGroup.setBounds(372, 42, 174, 32);
		Elective.add(addEleGroup);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(45, 81, 476, 7);
		Elective.add(separator_2);
		
		JLabel lblSelectElectiveGroup = new JLabel("Select Elective Group :");
		lblSelectElectiveGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectElectiveGroup.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectElectiveGroup.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectElectiveGroup.setBounds(10, 91, 170, 26);
		Elective.add(lblSelectElectiveGroup);
		
		groupE = new JComboBox<String>();
		groupE.setOpaque(false);
		groupE.setBackground(Color.WHITE);
		groupE.setBounds(180, 94, 245, 21);
		Elective.add(groupE);
		
		JButton deleteEleGroup = new JButton("Delete");
		deleteEleGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEleGroup();
			}
		});
		deleteEleGroup.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteEleGroup.setForeground(Color.WHITE);
		deleteEleGroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteEleGroup.setFocusTraversalKeysEnabled(false);
		deleteEleGroup.setFocusPainted(false);
		deleteEleGroup.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		deleteEleGroup.setBackground(Color.RED);
		deleteEleGroup.setBounds(435, 88, 102, 32);
		Elective.add(deleteEleGroup);
		
		JLabel label_3 = new JLabel("Select course :");
		label_3.setHorizontalTextPosition(SwingConstants.CENTER);
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_3.setBounds(10, 127, 120, 26);
		Elective.add(label_3);
		
		courseE = new JComboBox<String>();
		courseE.setOpaque(false);
		courseE.setBackground(Color.WHITE);
		courseE.setBounds(125, 130, 300, 21);
		courseE.setModel(new DefaultComboBoxModel<String>(course_name));
		Elective.add(courseE);
		
		JLabel label_4 = new JLabel("Select staff :");
		label_4.setHorizontalTextPosition(SwingConstants.CENTER);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setFont(new Font("Georgia", Font.PLAIN, 16));
		label_4.setBounds(10, 163, 111, 26);
		Elective.add(label_4);
		model=new DefaultListModel<String>();
		for(int i=1; i<staff_name.length; i++)
			model.addElement(staff_name[i]);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(110, 163, 382, 119);
		Elective.add(scrollPane_3);
		
		staffE = new JList<String>();
		scrollPane_3.setViewportView(staffE);
		staffE.setModel(model);
		
		JButton elective = new JButton("SUBMIT");
		elective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(groupE.getSelectedIndex()>0)
				{
					if(courseE.getSelectedIndex()>0)
					{
						addElective();
						updateTable();
					}
					else
						JOptionPane.showMessageDialog(null, "Please select Course.");
				}
				else
					JOptionPane.showMessageDialog(null, "Please select Elective Group.");
			}
		});
		elective.setHorizontalTextPosition(SwingConstants.CENTER);
		elective.setForeground(Color.WHITE);
		elective.setFont(new Font("Tahoma", Font.PLAIN, 18));
		elective.setFocusTraversalKeysEnabled(false);
		elective.setFocusPainted(false);
		elective.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		elective.setBackground(new Color(51, 0, 255));
		elective.setBounds(225, 284, 126, 32);
		Elective.add(elective);
		con=Login.getCon();
		Theory.setLayout(null);
		
		JLabel lblSelectCourse = new JLabel("Select course :");
		lblSelectCourse.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectCourse.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectCourse.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectCourse.setBounds(19, 57, 120, 26);
		Theory.add(lblSelectCourse);
		
		JLabel lblSelectCourseAnd = new JLabel("Select Course and Faculty");
		lblSelectCourseAnd.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectCourseAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectCourseAnd.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblSelectCourseAnd.setBounds(10, 10, 521, 26);
		Theory.add(lblSelectCourseAnd);
		
		courseT = new JComboBox<String>();
		courseT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				staffT.setSelectedIndex(-1);
			}
		});
		courseT.setOpaque(false);
		courseT.setBackground(new Color(255, 255, 255));
		courseT.setBounds(134, 60, 300, 21);
		courseT.setModel(new DefaultComboBoxModel<String>(course_name));
		Theory.add(courseT);
		
		JLabel lblSelectStaff = new JLabel("Select staff :");
		lblSelectStaff.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectStaff.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectStaff.setBounds(19, 93, 111, 26);
		Theory.add(lblSelectStaff);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 94, 384, 121);
		Theory.add(scrollPane);
		
		staffT = new JList<String>();
		model=new DefaultListModel<String>();
		for(int i=1; i<staff_name.length; i++)
			model.addElement(staff_name[i]);
		staffT.setModel(model);
		scrollPane.setViewportView(staffT);
		
		JButton theory = new JButton("SUBMIT");
		theory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(courseT.getSelectedIndex()>0)
				{
					addTheory();
					updateTable();
				}
				else
					JOptionPane.showMessageDialog(null, "Please select course and staff.");
			}
		});
		theory.setHorizontalTextPosition(SwingConstants.CENTER);
		theory.setForeground(Color.WHITE);
		theory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		theory.setFocusTraversalKeysEnabled(false);
		theory.setFocusPainted(false);
		theory.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		theory.setBackground(new Color(51, 0, 255));
		theory.setBounds(208, 225, 126, 32);
		Theory.add(theory);
		
		JButton deleteTheory = new JButton("Delete");
		deleteTheory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTheory();
			}
		});
		deleteTheory.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteTheory.setForeground(Color.WHITE);
		deleteTheory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		deleteTheory.setFocusTraversalKeysEnabled(false);
		deleteTheory.setFocusPainted(false);
		deleteTheory.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		deleteTheory.setBackground(Color.RED);
		deleteTheory.setBounds(444, 54, 102, 32);
		Theory.add(deleteTheory);
		
		JLabel lblInstructionsTheory = new JLabel("<html>\r\nInstructions : <br>\r\n  > Theory : Please select your Theory course and select one or more faculty for the course. <br>\r\n  > Lab : Make groups of concurrent laboratories. Select lab subject as well as faculties.<br>\r\n  > Elective : Make group of concurrent elective. Choose subject as well as faculty.<br><br>\r\nTo select multiple items please keep Cntrl key pressed.");
		lblInstructionsTheory.setHorizontalTextPosition(SwingConstants.CENTER);
		lblInstructionsTheory.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructionsTheory.setFont(new Font("Georgia", Font.PLAIN, 13));
		lblInstructionsTheory.setBounds(10, 620, 561, 110);
		getContentPane().add(lblInstructionsTheory);
		
		tableModel=new DefaultTableModel();
		DefaultTableCellRenderer render=new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 396, 561, 227);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setVisible(true);
		table = new JTable(tableModel);
		scrollPane_1.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setGridColor(Color.BLACK);
		table.setEnabled(false);
		table.setSelectionBackground(Color.BLACK);
		table.setRowMargin(2);
		table.setRowHeight(20);
		table.setForeground(Color.BLACK);
		table.setFillsViewportHeight(true);
		table.setBackground(new Color(255, 255, 255));
		updateTable();
		
		JLabel lblExistingData = new JLabel("Existing Data :");
		lblExistingData.setHorizontalTextPosition(SwingConstants.CENTER);
		lblExistingData.setHorizontalAlignment(SwingConstants.LEFT);
		lblExistingData.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblExistingData.setBounds(10, 360, 111, 26);
		getContentPane().add(lblExistingData);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 363, 561, 2);
		getContentPane().add(separator);
		setVisible(true);
	}
	
	private void addLabGroup()
	{
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT lab_group FROM LabGroup WHERE lab_group='L-"+labGroup.getText()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null, "Lab group - "+labGroup.getText()+" already exists. Please use different name.");
				return;
			}
			stmt.executeUpdate("INSERT INTO LabGroup VALUES('L-"+labGroup.getText()+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
			JOptionPane.showMessageDialog(null, "Lab group added successfully.");
			getGroupL();
			updateTable();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while adding Lab group.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void getGroupL()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT lab_group FROM LabGroup WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()) 
				count++;
			rs.beforeFirst();
			groupL_id=new String[count+1];
			int i=1;
			groupL_id[0]="--- Select Lab Group --- ";
			while(rs.next()) {
				groupL_id[i]=rs.getString(1);
				i++;
			}
			groupL.setModel(new DefaultComboBoxModel<String>(groupL_id));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Error occured while fetching Lab Groups.");
		}
	}
	
	private void deleteLabGroup()
	{
		if(groupL.getSelectedIndex()>0)
		{
			int i=JOptionPane.showOptionDialog(null, "Are your sure to delete the selected Laboratory group and its course and staff members from the database.","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			try {
				stmt=con.createStatement();
				i=stmt.executeUpdate("DELETE FROM Schedule WHERE class_type='"+groupL.getSelectedIndex()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				i=stmt.executeUpdate("DELETE FROM LabGroup WHERE lab_group='"+groupL.getSelectedItem()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				if(i>0) {
					JOptionPane.showMessageDialog(null, "Deleted successfully.");
					updateTable();
				}
					
			}
			catch(SQLException ee)
			{
				ee.printStackTrace();
			}
			getGroupL();
			updateTable();
		}
		else
			JOptionPane.showMessageDialog(null, "Please select Lab Group.");
	}
	
	private void addElectiveGroup()
	{
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT elective_group FROM ElectiveGroup WHERE elective_group='E-"+eleGroup.getText()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null, "Elective group - "+eleGroup.getText()+" already exists. Please use different name.");
				return;
			}
			stmt.executeUpdate("INSERT INTO ElectiveGroup VALUES('E-"+eleGroup.getText()+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
			JOptionPane.showMessageDialog(null, "Elective group added successfully.");
			getGroupE();
			updateTable();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while adding Elective group.","Time Table Creator",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void getGroupE()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT elective_group FROM ElectiveGroup WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
			while(rs.next()) 
				count++;
			rs.beforeFirst();
			groupE_id=new String[count+1];
			int i=1;
			groupE_id[0]="--- Select Elective Group --- ";
			while(rs.next()) {
				groupE_id[i]=rs.getString(1);
				i++;
			}
			groupE.setModel(new DefaultComboBoxModel<String>(groupE_id));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Error occured while fetching Elective Groups.");
		}
	}
	
	private void deleteEleGroup()
	{
		if(groupE.getSelectedIndex()>0)
		{
			int i=JOptionPane.showOptionDialog(null, "Are your sure to delete the selected Elective group and its course and staff members from the database.","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			try {
				stmt=con.createStatement();
				i=stmt.executeUpdate("DELETE FROM Schedule WHERE class_type='"+groupE.getSelectedIndex()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				i=stmt.executeUpdate("DELETE FROM ElectiveGroup WHERE elective_group='"+groupE.getSelectedItem()+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				if(i>0) {
					JOptionPane.showMessageDialog(null, "Deleted successfully.");
					updateTable();
				}	
			}
			catch(SQLException ee)
			{
				ee.printStackTrace();
			}
			getGroupE();
			updateTable();
		}
		else
			JOptionPane.showMessageDialog(null, "Please select Elective Group.");
	}
	
	private void addTheory()
	{
		try {
			stmt=con.createStatement();
			con.setAutoCommit(false);
			String cid=course_id[find(course_id,course_id[courseT.getSelectedIndex()])];
			String[] values=getListValues(staffT);
			if(values.length==0)
				JOptionPane.showMessageDialog(null, "Please select one or more staff from list.");
			for(String i:values)
			{
				String str[]=i.split(" - ");
				rs=stmt.executeQuery("SELECT COUNT(*) FROM Theory WHERE course_id='"+cid+"' AND staff_id='"+str[0]+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				rs.next();
				if(rs.getInt(1) == 0)
					stmt.executeUpdate("INSERT INTO Theory VALUES('"+cid+"','"+str[0]+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
			}
			con.commit();
			JOptionPane.showMessageDialog(null, "Saved successfully.");
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
	
	private void deleteTheory()
	{
		if(courseT.getSelectedIndex()>0)
		{
			int i=JOptionPane.showOptionDialog(null, "Are your sure to delete the selected course and its staff members from database.","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			try {
				stmt=con.createStatement();
				String cid=course_id[find(course_id,course_id[courseT.getSelectedIndex()])];
				i=stmt.executeUpdate("DELETE FROM Schedule WHERE course_id='"+cid+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				i=stmt.executeUpdate("DELETE FROM Theory WHERE course_id='"+cid+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				if(i>0) {
					JOptionPane.showMessageDialog(null, "Deleted successfully.");
					updateTable();
				}
					
			}
			catch(SQLException ee)
			{
				ee.printStackTrace();
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Please select course.");
	}

	private void addLab()
	{
		try {
			stmt=con.createStatement();
			con.setAutoCommit(false);
			String cid=course_id[find(course_id,course_id[courseL.getSelectedIndex()])];
			String[] values=getListValues(staffL);
			if(values.length==0)
				JOptionPane.showMessageDialog(null, "Please select on or more staff from list.");
			for(String i:values)
			{
				String str[]=i.split(" - ");
				rs=stmt.executeQuery("SELECT COUNT(*) FROM Lab WHERE lab_group='"+groupL.getSelectedItem()+"' AND course_id='"+cid+"' AND staff_id='"+str[0]+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				rs.next();
				if(rs.getInt(1) == 0)
					stmt.executeUpdate("INSERT INTO Lab VALUES('"+groupL.getSelectedItem()+"','"+cid+"','"+str[0]+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
			}
			con.commit();
			JOptionPane.showMessageDialog(null, "Saved successfully.");
			labGroup.setText("");
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
	
	private void addElective()
	{
		try {
			stmt=con.createStatement();
			con.setAutoCommit(false);
			String cid=course_id[find(course_id,course_id[courseE.getSelectedIndex()])];
			String[] values=getListValues(staffE);
			if(values.length==0)
				JOptionPane.showMessageDialog(null, "Please select on or more staff from list.");
			for(String i:values)
			{
				String str[]=i.split(" - ");
				rs=stmt.executeQuery("SELECT COUNT(*) FROM Elective WHERE elective_group='"+groupE.getSelectedItem()+"' AND course_id='"+cid+"' AND staff_id='"+str[0]+"' AND section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"';");
				rs.next();
				if(rs.getInt(1) == 0)
					stmt.executeUpdate("INSERT INTO Elective VALUES('"+groupE.getSelectedItem()+"','"+cid+"','"+str[0]+"','"+section+"',"+semester+",'"+department+"','"+college+"');");
			}
			con.commit();
			JOptionPane.showMessageDialog(null, "Saved successfully.");
			eleGroup.setText("");
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
	
	public void updateTable()
	{
		try {
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(tabbedPane.getSelectedIndex()==0)
			{
				rs=stmt.executeQuery("SELECT course_id,staff_id FROM Theory WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"' ORDER BY course_id;");
				if(!rs.next()) return;
				rs.beforeFirst();
				tableModel.addColumn("Course");
				tableModel.addColumn("Staff Name");
				rs.first();
				do {
					tableModel.insertRow(0, new String[] {course_name[find(course_id,rs.getString(1))], staff_name[find(staff_id,rs.getString(2))]});
				}while (rs.next());
			}
			else if(tabbedPane.getSelectedIndex()==1)
			{
				rs=stmt.executeQuery("SELECT lab_group,course_id,staff_id FROM Lab WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"' ORDER BY course_id;");
				if(!rs.next()) return;
				rs.beforeFirst();
				tableModel.addColumn("Lab Group");
				tableModel.addColumn("Lab Course");
				tableModel.addColumn("Staff Name");
				rs.first();
				do {
					tableModel.insertRow(0, new String[] {rs.getString(1),course_name[find(course_id,rs.getString(2))], staff_name[find(staff_id,rs.getString(3))]});
				}while (rs.next());
			}
			else if(tabbedPane.getSelectedIndex()==2)
			{
				rs=stmt.executeQuery("SELECT elective_group,course_id,staff_id FROM Elective WHERE section='"+section+"' AND sem="+semester+" AND dept_id='"+department+"' AND code='"+college+"' ORDER BY course_id;");
				if(!rs.next()) return;
				rs.beforeFirst();
				tableModel.addColumn("Elective Group");
				tableModel.addColumn("Elective Course");
				tableModel.addColumn("Staff Name");
				rs.first();
				do {
					tableModel.insertRow(0, new String[] {rs.getString(1),course_name[find(course_id,rs.getString(2))], staff_name[find(staff_id,rs.getString(3))]});
				}while (rs.next());
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "Unable to fetch data from database!","Error",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private int find(String[] arr, String target)
	{
		for(int i=1; i<arr.length; i++)
			if(arr[i].toString().equals(target))
				return i;
		return -1;
	}
	
	private String[] getListValues(JList<String> list)
	{
		ListModel<String> list_model=list.getModel();
		int[] index=list.getSelectedIndices();
		int size=index.length;
		String[] values=new String[size];
		for(int i=0; i<size; i++)
			values[i]=list_model.getElementAt(index[i]);
		return values;
	}
}

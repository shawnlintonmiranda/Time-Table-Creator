package dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import homePage.Login;
import tools.Validation;

import java.sql.*;


public class ManageClassroom extends JDialog {
	private static final long serialVersionUID = 1L;
	private static Connection con=null;
	private static Statement stmt=null;
	private static ResultSet rs=null;
	
	private static String code;
	
	private static String[] room_number;
	
	private static JComboBox<String> comboBox ;
	private static JPanel page1,page2;
	private JLabel lblSelectSemester,lblEnterRoomNo,title;
	private static JTextField rno;

	public ManageClassroom(JFrame frame,String code) {
		super(frame,"Manage Classroom",Dialog.ModalityType.DOCUMENT_MODAL);
		ManageClassroom.code=code;
		con=Login.getCon();
		getContentPane().setBackground(new Color(255, 248, 220));
		setSize(500,310);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		title = new JLabel("Add Classroom");
		title.setBounds(140, 10, 198, 26);
		getContentPane().add(title);
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		page2 = new JPanel();
		page2.setOpaque(false);
		page2.setBounds(10, 46, 466, 203);
		page2.setLayout(null);
		page2.setVisible(false);
		
		page1 = new JPanel();
		page1.setOpaque(false);
		page1.setBounds(10, 46, 466, 203);
		getContentPane().add(page1);
		page1.setLayout(null);
		
		lblEnterRoomNo = new JLabel("Enter Class Room No :");
		lblEnterRoomNo.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		lblEnterRoomNo.setBounds(57, 25, 165, 19);
		page1.add(lblEnterRoomNo);
		
		rno = new JTextField();
		rno.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rno.setColumns(10);
		rno.setBounds(232, 22, 150, 25);
		page1.add(rno);
		
		JLabel lblRoomNumberShould = new JLabel("Note : Room numbers should be unique within a college.");
		lblRoomNumberShould.setForeground(Color.BLACK);
		lblRoomNumberShould.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRoomNumberShould.setBounds(19, 54, 437, 24);
		page1.add(lblRoomNumberShould);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClassRoom();
			}
		});
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add.setFocusTraversalKeysEnabled(false);
		add.setFocusPainted(false);
		add.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		add.setBackground(new Color(51, 0, 255));
		add.setBounds(163, 88, 126, 32);
		page1.add(add);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(43, 130, 379, 2);
		page1.add(separator);
		
		JButton btnManageClassRooms = new JButton("Manage Class Rooms");
		btnManageClassRooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title.setText("Delete Classroom");
				page1.setVisible(false);
				page2.setVisible(true);
				updateComboBox();
			}
		});
		btnManageClassRooms.setHorizontalTextPosition(SwingConstants.CENTER);
		btnManageClassRooms.setForeground(Color.WHITE);
		btnManageClassRooms.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnManageClassRooms.setFocusTraversalKeysEnabled(false);
		btnManageClassRooms.setFocusPainted(false);
		btnManageClassRooms.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnManageClassRooms.setBackground(Color.ORANGE);
		btnManageClassRooms.setBounds(138, 142, 190, 32);
		page1.add(btnManageClassRooms);
		
		lblSelectSemester = new JLabel("Select classroom :");
		lblSelectSemester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSelectSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectSemester.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblSelectSemester.setBounds(44, 11, 148, 26);
		page2.add(lblSelectSemester);
		getContentPane().add(page2);
		
		comboBox = new JComboBox<String>();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(44, 47, 393, 28);
		page2.add(comboBox);
		
		JButton btnDeleteClassRoom = new JButton("Delete Class Room");
		btnDeleteClassRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page2.requestFocus();
				if(comboBox.getSelectedIndex()<=0)
					JOptionPane.showMessageDialog(null, "Please select the class room to be deleted.");
				else
					deleteClassRoom();
			}
		});
		btnDeleteClassRoom.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDeleteClassRoom.setForeground(Color.WHITE);
		btnDeleteClassRoom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDeleteClassRoom.setFocusTraversalKeysEnabled(false);
		btnDeleteClassRoom.setFocusPainted(false);
		btnDeleteClassRoom.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		btnDeleteClassRoom.setBackground(Color.RED);
		btnDeleteClassRoom.setBounds(129, 96, 207, 40);
		page2.add(btnDeleteClassRoom);
		setVisible(true);
	}
	
	public static void updateComboBox()
	{
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String query="SELECT room_number FROM ClassRoom WHERE code='"+code+"' ORDER BY room_number;";
			rs=stmt.executeQuery(query);
			while(rs.next()) 
				count++;
			room_number=new String[count+1];
			rs.beforeFirst();
			int i=1;
			room_number[0]="--- Select your classroom --- ";
			while(rs.next()) {
				room_number[i]=rs.getString(1);
				i++;
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(room_number));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching class room details");
			e.printStackTrace();
		}
	}

	public void addClassRoom() {
		rno.setBackground(Color.white);
		if(rno.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please fill the mandatory fields.", "Fields required",JOptionPane.ERROR_MESSAGE);
			rno.setBackground(new Color(255,255,200));
			rno.requestFocus();
			return;
		}
		if(!Validation.uniqueIdDomainSatisfied(rno.getText()))
		{
			rno.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Room Number can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return;
		}
		rno.setBackground(Color.white);
		try {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT room_number FROM ClassRoom WHERE room_number='"+rno.getText()+"';");
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "Classroom number already exists! Please use different one.", "Time Table Creator", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String insert="INSERT INTO ClassRoom VALUES('"+rno.getText()+"','"+code+"');";
			if(stmt.executeUpdate(insert)!=0)
				JOptionPane.showMessageDialog(null,"Class room added successfully.");
			rno.setText("");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to insert room number detail to database.");
			e.printStackTrace();
		}
		
	}
	
	public void deleteClassRoom()
	{
		try {
			stmt=con.createStatement();
			int i=JOptionPane.showOptionDialog(null, "Do you really want to delete class room "+room_number[comboBox.getSelectedIndex()]
					+ "? \nDeleting will remove room number from database!","Time Table Creator",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes","No"}, "No");
			if(i!=JOptionPane.YES_OPTION)
				return;
			
			if((stmt.executeUpdate("DELETE FROM ClassRoom WHERE room_number="+room_number[comboBox.getSelectedIndex()]+" AND code='"+code+"';"))!=0)
			{
				JOptionPane.showMessageDialog(null, "Class room deleted successfully.");
				updateComboBox();
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting the Class room entry.");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}

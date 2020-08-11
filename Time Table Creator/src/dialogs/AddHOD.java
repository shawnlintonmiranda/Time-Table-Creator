package dialogs;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.sql.*;

import homePage.Login;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddHOD extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	private String[] staff_id,staff_name;
	private String dept_id,dept_name,code;
	private JComboBox<String> comboBox;
	private JLabel label,label_1;
	public AddHOD(JDialog dialog,String dept_id, String dept_name,String code) {
		super(dialog,"Add / Modify HOD",Dialog.ModalityType.DOCUMENT_MODAL);
		getContentPane().setBackground(new Color(255, 248, 220));
		con=Login.getCon();
		this.dept_id=dept_id;
		this.dept_name=dept_name;
		this.code=code;
		
		setBounds(100, 100, 450, 300);
		setSize(500,195);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		comboBox = new JComboBox<String>();
		comboBox.setOpaque(false);
		comboBox.setBounds(131, 75, 345, 25);
		getContentPane().add(comboBox);
		
		label = new JLabel("");
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Georgia", Font.BOLD, 16));
		label.setBounds(18, 10, 458, 26);
		getContentPane().add(label);
		
		JLabel lblChooseHod = new JLabel("Choose HOD :");
		lblChooseHod.setHorizontalTextPosition(SwingConstants.CENTER);
		lblChooseHod.setHorizontalAlignment(SwingConstants.LEFT);
		lblChooseHod.setFont(new Font("Georgia", Font.PLAIN, 14));
		lblChooseHod.setBounds(18, 74, 114, 26);
		getContentPane().add(lblChooseHod);
		
		JButton cancel = new JButton("CANCEL");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setHorizontalTextPosition(SwingConstants.CENTER);
		cancel.setForeground(Color.WHITE);
		cancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancel.setFocusTraversalKeysEnabled(false);
		cancel.setFocusPainted(false);
		cancel.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		cancel.setBackground(new Color(255, 140, 0));
		cancel.setBounds(113, 116, 126, 32);
		getContentPane().add(cancel);
		
		JButton add = new JButton("ADD");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHOD();
			}
		});
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add.setFocusTraversalKeysEnabled(false);
		add.setFocusPainted(false);
		add.setBorder(new MatteBorder(2, 2, 1, 1, (Color) new Color(255, 255, 255)));
		add.setBackground(new Color(51, 0, 255));
		add.setBounds(249, 116, 126, 32);
		getContentPane().add(add);
		
		label_1 = new JLabel("<dynamic>");
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Georgia", Font.PLAIN, 11));
		label_1.setBounds(18, 39, 458, 26);
		getContentPane().add(label_1);
		staffList();
		setVisible(true);
	}

	private void staffList()
	{
		
		try {
			int count=0;
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT staff_name FROM Staff WHERE staff_id IN ("
					+ "SELECT hod FROM Department WHERE dept_id='"+dept_id+"' AND code='"+code+"');");
			label.setText(dept_name);
			if(rs.next())
				label_1.setText("( Current HOD : "+rs.getString(1)+" )");
			else 
				label_1.setText("Currently this deparment doesn't have HOD");
			rs=stmt.executeQuery("SELECT staff_id,staff_name FROM Staff WHERE dept_id='"+dept_id+"' AND code='"+code+"';");
			while(rs.next()) 
				count++;
			staff_id=new String[count+1];
			staff_name=new String[count+1];
			rs.beforeFirst();
			int i=1;
			staff_name[0]="--- Select staff --- ";
			while(rs.next()) {
				staff_id[i]=rs.getString(1);
				staff_name[i]=rs.getString(2);
				i++;
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(staff_name));
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error occured while fetching staff details");
			e.printStackTrace();
		}
	}
	
	private void addHOD()
	{
		try {
			stmt=con.createStatement();
			int i=stmt.executeUpdate("UPDATE Department SET hod='"+staff_id[comboBox.getSelectedIndex()]+"' WHERE dept_id='"+dept_id+"' AND code='"+code+"';");
			if(i>0)
			{
				JOptionPane.showMessageDialog(null, "HOD added succesfully.");
				dispose();
			}
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Failed to add HOD.");
			e.printStackTrace();
		}
	}
}

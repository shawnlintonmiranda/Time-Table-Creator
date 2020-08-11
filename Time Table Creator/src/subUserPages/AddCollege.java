package subUserPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import homePage.Login;
import tools.Validation;
import java.sql.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.awt.*;
import javax.swing.border.MatteBorder;
public class AddCollege extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JTextField code,fname,sname,address,city,pin,phone,email;
	private static JTable table;
	private static JLabel noData;
	private static DefaultTableModel model;
	private static JPanel panel;
	private static JScrollPane scrollPane;
	
	private static Connection con=null;
	private static ResultSet rs=null;
	private static Statement stmt=null;
	
	public AddCollege() {
		addPropertyChangeListener("visible",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if((boolean)evt.getNewValue())
					updateTable();
			}
		});
		con=Login.getCon();
		
		setBackground(new Color(255, 250, 240));
		setSize(1563,797);
		setLayout(null);

		JLabel title = new JLabel("Add New College");
		title.setFont(new Font("Serif", Font.PLAIN, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(10, 54, 1376, 43);
		add(title);
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(206, 124, 1063, 315);
		add(panel);
		panel.setLayout(null);
		
		JLabel codeL = new JLabel("College code : *");
		codeL.setBounds(0, 3, 121, 19);
		panel.add(codeL);
		codeL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		code = new JTextField();
		code.setBounds(117, 0, 150, 25);
		panel.add(code);
		code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(code.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		code.setFont(new Font("Tahoma", Font.PLAIN, 15));
		code.setColumns(10);
		
		JLabel fnameL = new JLabel("Full name of College : *");
		fnameL.setBounds(0, 41, 168, 19);
		panel.add(fnameL);
		fnameL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		fname = new JTextField();
		fname.setBounds(178, 38, 878, 25);
		panel.add(fname);
		fname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(fname.getText().length()>=100) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		fname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fname.setColumns(10);
		
		JLabel snameL = new JLabel("Short name of College : *");
		snameL.setBounds(0, 73, 177, 19);
		panel.add(snameL);
		snameL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		sname = new JTextField();
		sname.setBounds(188, 70, 865, 25);
		panel.add(sname);
		sname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(sname.getText().length()>=50) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		sname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sname.setColumns(10);
		
		JLabel addressL = new JLabel("Address :");
		addressL.setBounds(0, 117, 73, 19);
		panel.add(addressL);
		addressL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		address = new JTextField();
		address.setBounds(79, 114, 977, 25);
		panel.add(address);
		address.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(address.getText().length()>=120) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		address.setFont(new Font("Tahoma", Font.PLAIN, 15));
		address.setColumns(10);
		
		JLabel cityL = new JLabel("Main City :");
		cityL.setBounds(0, 157, 73, 19);
		panel.add(cityL);
		cityL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		city = new JTextField();
		city.setBounds(79, 154, 161, 25);
		panel.add(city);
		city.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(city.getText().length()>=20) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		city.setFont(new Font("Tahoma", Font.PLAIN, 15));
		city.setColumns(10);
		
		JLabel pinL = new JLabel("Pincode :");
		pinL.setBounds(287, 157, 65, 19);
		panel.add(pinL);
		pinL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		pin = new JTextField();
		pin.setBounds(362, 154, 96, 25);
		panel.add(pin);
		pin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(pin.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		pin.setHorizontalAlignment(SwingConstants.CENTER);
		pin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pin.setColumns(10);
		
		JLabel phoneL = new JLabel("Contact number :");
		phoneL.setBounds(0, 191, 126, 19);
		panel.add(phoneL);
		phoneL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		phone = new JTextField();
		phone.setBounds(127, 188, 96, 25);
		panel.add(phone);
		phone.setHorizontalAlignment(SwingConstants.CENTER);
		phone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(phone.getText().length()>=10) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					addCollege();
			}
		});
		phone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phone.setColumns(10);
		
		JLabel emailL = new JLabel("Email Address :");
		emailL.setBounds(246, 191, 110, 19);
		panel.add(emailL);
		emailL.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 12));
		
		email = new JTextField();
		email.setBounds(355, 188, 233, 25);
		panel.add(email);
		email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(email.getText().length()>=30) e.consume();
			}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					addCollege();
				}
			}
		});
		email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		email.setColumns(10);
		
		JLabel label = new JLabel("Fields marked with * are mandatory.");
		label.setForeground(Color.RED);
		label.setBounds(0, 229, 240, 13);
		panel.add(label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JButton add = new JButton("Add College");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCollege();
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
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(192, 192, 192));
		separator.setBounds(139, 449, 1177, 2);
		add(separator);
		
		JLabel lblYourColleges = new JLabel("Your Colleges :");
		lblYourColleges.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		lblYourColleges.setBounds(202, 487, 185, 19);
		add(lblYourColleges);
		
		model=new DefaultTableModel();
		model.addColumn("College Code");
		model.addColumn("College Name");
		DefaultTableCellRenderer render=new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		
		noData = new JLabel("");
		noData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		noData.setBounds(210, 516, 424, 35);
		add(noData);
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBounds(247, 561, 805, 170);
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
		separator_1.setBounds(603, 95, 191, 2);
		add(separator_1);
		table.getColumnModel().getColumn(0).setMaxWidth(120);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(render);
		updateTable();
	}
	
	public void setVisible(boolean b) {
		boolean visible=isVisible();
		super.setVisible(b);
		firePropertyChange("visible",visible,b);
	}
	
	private void addCollege() {
		code.setBackground(Color.white);
		fname.setBackground(Color.white);
		sname.setBackground(Color.white);
		boolean flag=false;
		if(code.getText().equals("")) {
			code.setBackground(new Color(255,255,200));
			code.requestFocus();
			flag=true;
		} else if(fname.getText().equals("")) {
			fname.setBackground(new Color(255,255,200));
			fname.requestFocus();
			flag=true;
		} else if(sname.getText().equals(""))
		{
			fname.setBackground(new Color(255,255,200));
			sname.requestFocus();
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
			rs=stmt.executeQuery("SELECT code FROM College WHERE code='"+code.getText()+"';");
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "College ID already exists! Please use different one.", "Time Table Creator", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String insert="INSERT INTO College VALUES('"+code.getText()+"','"+fname.getText()+"','"+sname.getText()+"','"+address.getText()+"','"+city.getText()+"','"+pin.getText()+"','"+email.getText()+"','"+phone.getText()+"');";
			if(stmt.executeUpdate(insert)>0) {
				JOptionPane.showMessageDialog(null, "College added successfully.");
				for(Component comp:panel.getComponents())
				{
					if(comp instanceof JTextField)
						((JTextField) comp).setText("");
				}
			}
			updateTable();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Failed to insert college detail to database.");
			e.printStackTrace();
		}
		
	}
	private boolean validateDomain()
	{
		if(!Validation.uniqueIdDomainSatisfied(code.getText()))
		{
			code.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "College code can contain only alphabets,digits and Hyphen.", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		code.setBackground(Color.white);
		
		if(!Validation.nameDomainSatisfied(fname.getText()))
		{
			fname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		fname.setBackground(Color.white);
		if(!Validation.nameDomainSatisfied(sname.getText()))
		{
			sname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		sname.setBackground(Color.white);
		if(!Validation.nameDomainSatisfied(city.getText()))
		{
			city.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "City name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		city.setBackground(Color.white);
		
		if((phone.getText().length()>0 && phone.getText().length()<6) ||(phone.getText().length()==6 && !Validation.phoneDomainSatisfied(phone.getText(),0)) || (phone.getText().length()==10 && !Validation.phoneDomainSatisfied(phone.getText(),1)))
		{
			phone.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(this, "Contact number should have 6 or 10 digits\n", "Invalid field value", JOptionPane.WARNING_MESSAGE);
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
	
	private void updateTable()
	{
		int count=0;
		try {
			model.setRowCount(0);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("SELECT code,fname FROM College ORDER BY fname DESC;");
			while(rs.next()) count++;
			if(count==0)
			{
				scrollPane.setVisible(false);
				noData.setText("College details are not found in the database.");
			}
			else
			{
				noData.setText("Total Colleges : "+count);
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
}

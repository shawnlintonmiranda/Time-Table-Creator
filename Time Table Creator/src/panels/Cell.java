package panels;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cell extends JPanel {
	private static final long serialVersionUID = 1L;
	public JComboBox<String> course,staff;
	public ButtonGroup bg;
	public JRadioButton l,t,e;
	public int t1=0,t2=0,l1=0,e1=0,hour,day;
	public String[] courseT_id,courseT_name,staffT_id,staffT_name;
	public Cell(int hours) {
		setBackground(Color.WHITE);
		setLayout(null);
		setSize(new Dimension(1260/hours, 87));
		
		UIManager.put("ComboBox.background", new ColorUIResource(Color.white));
		UIManager.put("ComboBox.foreground", new ColorUIResource(Color.black));
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.white));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.black));
		
		course = new JComboBox<String>();
		course.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				course.setSize(course.getWidth()-190, course.getHeight());
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				course.setSize(course.getWidth()+190, course.getHeight());
			}
		});
		course.setBackground(new Color(255, 255, 255));
		course.setOpaque(false);
		course.setBounds(10, 20, 854/hours, 21);
		add(course);
		UIManager.put("comboBox.background", new ColorUIResource(Color.black));
		UIManager.put("comboBox.foreground", new ColorUIResource(Color.white));
		UIManager.put("comboBox.selectionBackground", new ColorUIResource(Color.black));
		UIManager.put("comboBox.selectionForeground", new ColorUIResource(Color.white));
		
		UIManager.put("comboBox2.background", new ColorUIResource(Color.black));
		UIManager.put("comboBox2.foreground", new ColorUIResource(Color.white));
		UIManager.put("comboBox2.selectionBackground", new ColorUIResource(Color.black));
		UIManager.put("comboBox2.selectionForeground", new ColorUIResource(Color.white));
		
		staff = new JComboBox<String>();
		staff.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				staff.setSize(staff.getWidth()-190, staff.getHeight());
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				staff.setSize(staff.getWidth()+190, staff.getHeight());
			}
		});
		staff.setOpaque(false);
		staff.setBounds(10, 50, 854/hours, 21);
		add(staff);
		
		bg=new ButtonGroup();
		l = new JRadioButton("L");
		l.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		l.setOpaque(false);
		l.setBounds((854/hours)+10, 40, 40, 18);
		bg.add(l);
		add(l);
		
		t = new JRadioButton("T");
		t.setOpaque(false);
		t.setBounds((854/hours)+10, 15, 40, 18);
		bg.add(t);
		add(t);
		
		e = new JRadioButton("E");
		e.setOpaque(false);
		e.setBounds((854/hours)+10, 65, 40, 18);
		bg.add(e);
		add(e);
	}
}

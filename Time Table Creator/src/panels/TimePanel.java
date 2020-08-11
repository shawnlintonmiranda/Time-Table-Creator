package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class TimePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JSpinner time1,time2;
	public JSpinner.DateEditor format1,format2;

	public TimePanel(int hours) {
		setOpaque(false);
		setBackground(Color.WHITE);
		setSize(new Dimension(110, 66));
		
		JLabel label1=new JLabel("FROM");
		add(label1);
		
		SpinnerDateModel model1=new SpinnerDateModel();
		model1.setCalendarField(Calendar.MINUTE);
		time1 = new JSpinner(model1);
		format1=new JSpinner.DateEditor(time1,"h:mm a");
		DateFormatter f1=(DateFormatter)format1.getTextField().getFormatter();
		f1.setAllowsInvalid(false);
		f1.setOverwriteMode(true);
		time1.setEditor(format1);
		time1.setBounds(181, 100, 141, 20);
		add(time1);
		
		JLabel label2=new JLabel(" - TO");
		add(label2);
		
		SpinnerDateModel model2=new SpinnerDateModel();
		model2.setCalendarField(Calendar.MINUTE);
		time2 = new JSpinner(model2);
		format2=new JSpinner.DateEditor(time2,"h:mm a");
		DateFormatter f2=(DateFormatter)format2.getTextField().getFormatter();
		f2.setAllowsInvalid(false);
		f2.setOverwriteMode(true);
		time2.setEditor(format2);
		time2.setBounds(181, 100, 141, 20);
		add(time2);
	}

}

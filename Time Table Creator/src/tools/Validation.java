package tools;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class Validation {
	public static boolean nameDomainSatisfied(String text)
	{
		Matcher m;
		Pattern r;
		r=Pattern.compile("^[a-zA-Z ]*$");
		m=r.matcher(text);
		if(m.find())
			return true;
		return false;
	}
	
	public static boolean emailDomainSatisfied(String text)
	{
		Matcher m;
		Pattern r;
		r=Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
		m=r.matcher(text);
		if(m.find())
			return true;
		return false;
	}
	
	public static boolean phoneDomainSatisfied(String text,int i)
	{
		Matcher m;
		Pattern r;
		if(i==0)
		{		
			r=Pattern.compile("^[0-9]{6}$");
		}
		else
		{
			r=Pattern.compile("^[0-9]{10}$");
		}
		m=r.matcher(text);
		if(m.find())
			return true;
		return false;
	}
	public static boolean usernameDomainSatisfied(String text)
	{
		Matcher m;
		Pattern r;
		r=Pattern.compile("^[a-zA-Z0-9_@.]+$");
		m=r.matcher(text);
		if(m.find())
			return true;
		return false;
	}
	
	public static boolean uniqueIdDomainSatisfied(String text)
	{
		Matcher m;
		Pattern r;
		r=Pattern.compile("^[A-Za-z0-9-]+$");
		m=r.matcher(text);
		if(m.find())
			return true;
		return false;
	}
	
	public static boolean successfulValidateDetails(JTextField fname,JTextField mname,JTextField lname,JDateChooser date,String dateValue,String gender,JTextField email,JTextField phone)
	{
		if(fname.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "First name is required ..", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			fname.requestFocus();
			fname.setBackground(new Color(255,255,180));
			return false;
		}
		fname.setBackground(Color.white);
				
		if(dateValue.equals("")) {
			JOptionPane.showMessageDialog(null, "Date of birth is required", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			date.requestFocus();
			date.setBackground(new Color(255,255,180));
			return false;
		}
		date.setBackground(Color.white);
		
		if(gender.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please select gender.", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
			
		if(email.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter your email..", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			email.requestFocus();
			email.setBackground(new Color(255,255,180));
			return false;
		}
		email.setBackground(Color.white);
		
		if(phone.getText().length()<10) {
			JOptionPane.showMessageDialog(null, "Phone number must contain 10 digits.", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			phone.requestFocus();
			phone.setBackground(new Color(255,255,180));
			return false;
		}
		phone.setBackground(Color.white);
		
		if(!nameDomainSatisfied(fname.getText()))
		{
			fname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		fname.setBackground(Color.white);
		
		if(!nameDomainSatisfied(mname.getText()))
		{
			mname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		mname.setBackground(Color.white);
		
		if(!nameDomainSatisfied(lname.getText()))
		{
			lname.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		lname.setBackground(Color.white);

		if(!(date.getForeground()!=Color.red))	{
			date.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Invalid date!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		date.setBackground(Color.white);
		
		if(!emailDomainSatisfied(email.getText()))
		{
			email.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Invalid email\nExample : example@domain.com", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		email.setBackground(Color.white);
		
		if(!phoneDomainSatisfied(phone.getText(),1))
		{
			phone.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Contact number should contain only 0-9 digits", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		phone.setBackground(Color.white);
		
		return true;
	}

	public static boolean validateUpdateDetails(JTextField name, JTextField email,JTextField phone)
	{
		if(name.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "New name cannot be blank", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			name.requestFocus();
			name.setBackground(new Color(255,255,180));
			return false;
		}
		name.setBackground(Color.white);
				
		if(email.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter your email..", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			email.requestFocus();
			email.setBackground(new Color(255,255,180));
			return false;
		}
		email.setBackground(Color.white);
		
		if(phone.getText().length()<10) {
			JOptionPane.showMessageDialog(null, "Phone number must contain 10 digits.", "Requirements", JOptionPane.INFORMATION_MESSAGE);
			phone.requestFocus();
			phone.setBackground(new Color(255,255,180));
			return false;
		}
		phone.setBackground(Color.white);
		
		if(!nameDomainSatisfied(name.getText()))
		{
			name.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Name can contain alphabets and whitespace only!", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		name.setBackground(Color.white);

		if(!emailDomainSatisfied(email.getText()))
		{
			email.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Invalid email\nExample : example@domain.com", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		email.setBackground(Color.white);
		
		if(!phoneDomainSatisfied(phone.getText(),1))
		{
			phone.setBackground(new Color(255,255,180));
			JOptionPane.showMessageDialog(null, "Contact number should contain only 0-9 digits", "Invalid field value", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		phone.setBackground(Color.white);
		return true;
	}
}


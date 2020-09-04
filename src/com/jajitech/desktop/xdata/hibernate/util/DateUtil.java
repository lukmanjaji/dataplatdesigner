/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.hibernate.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Javalove
 */
public class DateUtil {
    
    Date dt;
    
    
    
    public String getDayOfWeek(Date date)
    {
        GregorianCalendar calendar= new GregorianCalendar();
	calendar.setTime(date);
        String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday","Friday", "Saturday" };
        return strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
    
    
    
    public String getMonthName(int day, int month, int year)
    {
        Calendar ca1 = Calendar.getInstance();
        ca1.set(year, month-1, day);
        java.util.Date d = new java.util.Date(ca1.getTimeInMillis());
        return new SimpleDateFormat("MMMM").format(d);
    }
    
    
    public java.util.Date parseDate( String dateString ) {
	
	String s = dateString;
        System.out.println("date string is "+s);
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            date = simpleDateFormat.parse(s);
            System.out.println("date : "+simpleDateFormat.format(date));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        return date;
}
    
    
    
    public String dateToString(Date dt)
    {
        this.dt = dt;
        GregorianCalendar calendar= new GregorianCalendar();
	calendar.setTime(dt);
        int x=calendar.get(10);
	String strdate = (new StringBuilder()).append(calendar.get(5)).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(1)).toString();
        return strdate;
    }
    
    
    public String returnNumericMonth(String mn)
	{
		String mnt="";
		if(mn.startsWith("Jan"))
		{
			mnt="01";
		}
		
		if(mn.startsWith("Feb"))
		{
			mnt="02";
		}
		
		if(mn.startsWith("Mar"))
		{
			mnt="03";
		}
		
		if(mn.startsWith("Apr"))
		{
			mnt="04";
		}
		
		if(mn.startsWith("May"))
		{
			mnt="05";
		}
		
		if(mn.startsWith("Jun"))
		{
			mnt="06";
		}
		
		if(mn.startsWith("Jul"))
		{
			mnt="07";
		}
		
		if(mn.startsWith("Aug"))
		{
			mnt="08";
		}
		
		if(mn.startsWith("Sep"))
		{
			mnt="09";
		}
		
		if(mn.startsWith("Oct"))
		{
			mnt="10";
		}
		
		if(mn.startsWith("Nov"))
		{
			mnt="11";
		}
		
		if(mn.startsWith("Dec"))
		{
			mnt="12";
		}
		
		return mnt;
	}
    
}

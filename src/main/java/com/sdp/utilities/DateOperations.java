/**
 * 
 */
package com.sdp.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author ruchira.more
 *
 */
public class DateOperations {

	public static void main(String[] args) throws ParseException {

		// System.out.println("dates in table"+new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss.ms").parse("2018-06-11 06:33:01.46"));
				// System.out.println(new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss.ms").parse("2018-05-28 00:00:00.000"));
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-12"));
//		Date currentdate=new Date();
//		
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(currentdate.toString()));
//		
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		System.out.println(date);
		 String nextDate="";
		 LocalDate ldate = LocalDate.now();
	       // ldate = ldate.plusDays(1);
	        nextDate = ldate.toString();
	        System.out.println("next day:"+nextDate);
		
	}

}

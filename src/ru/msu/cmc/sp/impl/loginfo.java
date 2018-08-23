package ru.msu.cmc.sp.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;



public class loginfo implements Comparable<loginfo> {
	private LocalDate date;
	private String usname;
	private String sometext;
	
	public loginfo(String date_s, String usname, String sometext) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		this.date = LocalDate.parse(date_s, formatter);
		this.usname=usname;
		this.sometext=sometext;
	}
	public String toString() 
	{
		return "Дата: " + date + "\n" + "Имя пользователя: " + usname + "\n" + "Некоторый текст: " + sometext;
	}

	public String getName() {
        return usname;
    }
     
    public void setName(String name) {
        this.usname = name;
    }
    public LocalDate getDate() {
    	return date;
    }
	
    @Override
    public int compareTo(loginfo o) {
      return getDate().compareTo(o.getDate());
    }
    
}

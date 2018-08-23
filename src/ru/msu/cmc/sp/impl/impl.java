package ru.msu.cmc.sp.impl;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class impl {

public static String username_f;
//your directory with log files
public static String directoryoflogs="D:\\impl\\logs\\";
//your directory with output file
public static String directoryforoutput="D:\\impl\\output\\";
public static String resultoffilter="";
public static String resultofgroup="";

	public static void main(String[] args) {

		
		Scanner in = new Scanner(System.in);
		System.out.println("Введите имя для фильтра: ");
		username_f= in.nextLine();
		
		final File folder = new File(directoryoflogs);
		listFilesForFolder(folder);
		
		System.out.println("Результат фильтра");
		System.out.println("=================");
		
		if (resultoffilter!="")
		System.out.println(resultoffilter);
		else System.out.println("Имя не найдено\n");
		
		System.out.println("Результат группировки");
		System.out.println("=================");
		System.out.println(
				"Дата\t\tКоличество записей\r\n"+
				resultofgroup);
		
		File file = new File(directoryforoutput+"output.txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			  writer.write(
					  "Дата\t\tКоличество записей\r\n"+
					  resultofgroup);
		      writer.flush();
		      writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
	}

	public static void listFilesForFolder(final File folder)  {

	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            String fan = (fileEntry.getName());
	            System.out.println(fan);
	            List<loginfo> invItem = getDataFromFile(directoryoflogs + fan);         
	            
	            // display 
	            for (loginfo each : invItem) {
	                System.out.println("====================");
	                System.out.println(each);
	                System.out.println();
	            }
	            
	            
	            //filter
	            loginfo result1 = invItem.stream()                        
	                .filter(x -> username_f.equals(x.getName()))       
	                .findAny()                                     
	                .orElse(null); 
	           if (result1!=null) {
	        	  resultoffilter=resultoffilter+result1+"\n\n";
	           }
	           
	           Stream<loginfo> result2 = invItem.stream();
	           
	           //grouping 
	           Map<LocalDate, Long> countbydate = result2
	        		   .collect(Collectors.groupingBy(loginfo::getDate, Collectors.counting()));
	           for(Map.Entry<LocalDate, Long> item : countbydate.entrySet()){
	        	    resultofgroup=resultofgroup+(item.getKey() + "\t" + item.getValue())+"\r\n";
	        	}
	         
	            
	        }
	    }
	}


	
	
	private static List<loginfo> getDataFromFile(String fileName) {
        List<loginfo> invItems = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String fileRead = br.readLine();

            // loop until all lines are read
            while (fileRead != null) {

                // use string.split to load a string array 
                String[] tokenize = fileRead.split(",");

                loginfo items = buildloginfo(tokenize);

                // add to array list
                invItems.add(items);
                
                // read next line before looping
                // if end of file reached
                fileRead = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return invItems;
    }

    private static loginfo buildloginfo(String[] dataLine){
    	
        String tempdate = dataLine[0];
        String tempun = dataLine[1];
        String tempsm = dataLine[2];
    	
        // creat temporary instance of loginfo object
        // and load with three data values
        loginfo li = new loginfo(tempdate, tempun, tempsm);
    	
        return li;

    }


}



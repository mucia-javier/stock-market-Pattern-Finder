/*
Programmer's: Javier Mucia
DATE:         May 22, 2015

Description:  Program that reads a csv file; computes 20/50 day SMA
              and outputs to a new csv file

http://rosettacode.org/wiki/CSV_data_manipulation#Java
*/ 
import java.io.File;
import java.io.FilenameFilter;

public class SMA {

/*IMPORTANT!! *Enter YOUR folder PATH of the CSV Project before running!!*
  May be different for all users*/
private static final String FOLDER_PATH ="C:\\Users\\Edward\\workspace\\CSV";

public static void main(String[] args) {
    try {
        CSV csv = new CSV(); 	 //Create a new object of type "csv"
        CSV csvTOTAL = new CSV(); // Holds the grand total for 25 markets
        	
        	/*	COMPUTE THE DATA OF DIFFERENT MARKETS, BASED ON THE HISTORICAL PRICES GIVEN BY YAHOO FINANCE
         	- Each market is computed individually and the results for each marked are saved onto a new 
         	  file (titled **Results.csv).
         	- The collective results after computing all the given markets are saved onto a new file
         	  titled "FINALRESULTS.csv". This file contains the names of all the markets processed,  
         	  the Total Percent Gaain/Loss of each market and the average percent Gain/Loss of all the 
         	  given markets combined. 
         	 
         	Each market is processed in three steps:  
      		Step (1): Open **.CSV file, where data of a market is stored in ascending order 
      				according to dates. Each Market file contains historical prices for 15 years (or less if 
      				data is not available for 15 years) from Jan 01, 2000 to Dec 31, 2014.
      				
        	Step (2): Find patterns on the data in each file and save the results onto a new file 
        			whose name is given as the argument to the function 
        	
        	Step (3): Copy the Total Percent Gain/Loss of each market to the file containing the collective 
        			  results of all the markets combined. 
        			*/

        	int fileCount = 0; //keep count of # of stocks/files
        	File dir = new File(FOLDER_PATH); // Creates a new File instance by converting the given pathname 
    	    								  // string into an abstract pathname.abstract pathname is just the string
    	    								  // form of the file/location held in the File object.
        	FilenameFilter filter = new FileFilter();	
        	File[] files = dir.listFiles(filter); // Creates and returns an array of abstract pathnames representing 
        										  // the files and directories in the directory denoted by "dir", 
        										  // that satisfies the specified filter; filters out all non-csv files.
        	
        	for(File f : files){ //enhanced for-loop; loops through each file in the array
    		String filename = f.getName();
    		String stock = filename.split(".csv")[0]; // truncate .csv extension from filename
    		String results = stock + "_Results";	  // concatenate "_Results" to stock name
    		
    		csv.open(stock); 				   // (1)
   		    csv.findPatterns(results); 		   // (2)
            csvTOTAL.getTotalPercentGain(csv); // (3)          
            csv.clear();					   // Clear object to use it again for next market	
            
            fileCount++;
    		}
        csvTOTAL.CalculateFinalResults();
		csvTOTAL.save(new File("FINALRESULTS.csv"));  //save all the resulting data onto a new file
        System.out.println("Computations completed.");
        System.out.println("Stocks: " + fileCount);

    } catch (Exception e) {
    System.out.println("WRONG");
    e.printStackTrace();
      }
}
}

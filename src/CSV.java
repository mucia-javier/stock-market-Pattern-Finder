/*
Programmer's: Javier Mucia
DATE:         May 2, 2015
Description:  Program that reads a csv file and analyzes market trend (bear,bull) 
			  then outputs results onto a new csv file

http://rosettacode.org/wiki/CSV_data_manipulation#Java
*/                                                                           
import java.io.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Spring;
 
public class CSV {
 
    private HashMap<Point, String> _map = new HashMap<Point, String>();
    private int _cols;
    private int _rows;
    private double totalPercentGain;
    private String nameOfFile;
    
    public void open(String fileName) throws FileNotFoundException, IOException {
    	File file = new File(fileName+".csv");
        open(file, ',', fileName);
    }
 
    //method to read file
    public void open(File file, char delimiter, String fileName) 
            throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(Character.toString(delimiter));
 
        clear();
    	this.nameOfFile = fileName;
        while(scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(Character.toString(delimiter));
 
            int col = 0;
            for ( String value: values ) {
                _map.put(new Point(col, _rows), value);
                _cols = Math.max(_cols, ++col);
            }
            _rows++;
        }
        _rows++; 
        scanner.close();
    }
     
    public void save(File file) throws IOException {
        save(file, ',');
    }
 
    public void save(File file, char delimiter) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
 
        for (int row = 0; row < _rows; row++) {
            for (int col = 0; col < _cols; col++) {
                Point key = new Point(col, row);
                if (_map.containsKey(key)) {
                    bw.write(_map.get(key));
                }
 
                if ((col + 1) < _cols) {
                    bw.write(delimiter);
                }
            }
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
    
/////////    Returns the value at the given Cell as a String/Double
    public String get(int col, int row) {
        String val = "";
        Point key = new Point(col, row);
        if (_map.containsKey(key)) {
            val = _map.get(key);
        }
        return val;
    }
    
/////////    Returns the value at the given Cell as Double
    public Double getN(int col, int row) {
        String val = "";
        Point key = new Point(col, row);
        if (_map.containsKey(key)) {
            val = _map.get(key);
        }
    	Double value;
        if(val.isEmpty() )
        	value = 0.0; 
        else
        	value = Double.parseDouble(val);

        return value;
    }

/////////    Takes the Column and Row indices of a Cell, and a String 
////////     value to be written on that cell
    public void put(int col, int row, String value) {
        _map.put(new Point(col, row), value);
        _cols = Math.max(_cols, col+1);
        _rows = Math.max(_rows, row+1);
    }
    
/////////    Takes the Column and Row indices of a Cell, and a Double 
////////     value to be written on that cell
    public void put(int col, int row, Double val) {
    	String value = Double.toString(val);
        _map.put(new Point(col, row), value);
        _cols = Math.max(_cols, col+1);
        _rows = Math.max(_rows, row+1);
    }
/////////    Takes the Column and Row indices of a Cell, and an Integer 
////////     value to be written on that cell
    public void put(int col, int row, int val) {
    	String value = Integer.toString(val);
        _map.put(new Point(col, row), value);
        _cols = Math.max(_cols, col+1);
        _rows = Math.max(_rows, row+1);
    }
   
////////     Clear the current CSV file
    public void clear() {
        _map.clear();
        _cols = 0;
        _rows = 0;
        totalPercentGain = 0; 
        nameOfFile = "";
    }
 
////////	Accessor Funcions to get #ofRows, #ofColumns, nameOfFile, totalPercentGain
    public int rows() {
        return _rows;
    }
    public int cols() {
        return _cols;
    }
    public String nameOfFile(){
    	return nameOfFile;
    }
    public double totalPercentGain(){
    	return totalPercentGain;
    }
    
    public void setTotalPercentGain(double newVal){ CSV.this.totalPercentGain = newVal; }
    
    
///////		Copy the name and the total percent gain/loss of a market on the given CSV object onto a new file    
    public void getTotalPercentGain(CSV aCSV){
    	if(this.rows()==0){
    		this.put(0, 0, "STOCK NAME");
    		this.put(1, 0,  "TOTAL PERCENT GAIN/LOSS"); }
    	CSV.this.put(0, CSV.this.rows(), aCSV.nameOfFile());  		
    	CSV.this.put(1, CSV.this.rows()-1, aCSV.totalPercentGain());
    	}
  
    
///////		Computes the average of all the markets combined (Used only for the "Final Results" file)    
    public void CalculateFinalResults(){
    	double sum = 0.0;
    	int row = 1;
    	for (; row <= this.rows(); ++row )		// Starting from row 0, to the final row on the file
			{
    		sum += this.getN(1, row);                  			  	//Cumulative sum from start to interval
			}
    	double average = sum/(this.rows()-1);
    	this.put(0, this.rows()+1, "COMBINED AVERAGE: ");
    	this.put(1, this.rows()-1, average);
    	}
    
///////		Fills an Empty CSV Object with the desired values of each of the 
///////		Pattern Objects on a Queue-Of-Patterns List  
   
	public void GetListData(QueueOfPatterns List){
    	Pattern Current = List.DeleteFirst();
    	
/////////   Writes the Header Of each of the columns in the Output CSV file       
		this.put(0, 0, "Trade Opened");
		this.put(1, 0, "Open");			// In the Cell at Column=1, Row=0, put the value "Open"
		this.put(2, 0, "Stop Loss");
		this.put(3, 0, "Take Profit");
		this.put(4, 0, "Trade Closed");
		
		this.put(5, 0, "Percent Gain");
		
/////////	Fills All the following rows with values from a Pattern in the QueueOfPatterns 
    	int theRow = 1;
    	while( Current != null){  
    		this.put(0, theRow, Current.enterTradeDate);
    		this.put(1, theRow, Current.enter);
    		this.put(2, theRow, Current.stopLoss);
    		this.put(3, theRow, Current.takeProfit);
    		if ( Current.exitTradeDate != null){
    			this.put(4, theRow, Current.exitTradeDate);
    		}
    		else
    		{ this.put(4, theRow, "n/a");}
    		
    		this.put(5, theRow, Current.avgPercentGain);
    		++theRow;
    		Current = List.DeleteFirst();
    		}
 }
    
///////		Calculates simple moving average and outputs the results to the specified column
    public void SMA(int interval, int outputColumn) {
    	int start = 1; 
    	String avg;
    	double num1;
    	for (int col = 4; col < 5 && interval < this.rows(); ) //Start at column E and row "interval"
        {													   //until last row in file
        	double sum = 0.0;
        	double average = 0.0;
       		for (int row = start; row <= interval; row++ ) 	//begin at start, end at interval
        		{
       													  	// get numerical value 
        		num1 = this.getN(col, row);;   			 	// from (column 4, row)
        		sum += num1;                  			  	//Cumulative sum from start to interval
        		}
        	average = sum / ((interval + 1) - start);		 //average from start to interval
        	avg = Double.toString(average);			  		//convert double to a String
        	this.put(outputColumn, interval, average);	  	//insert average into specified column, interval
        	start++;	  	
        	interval++; 			//increment start and interval to calculate next block of numbers
        							//ex. if starting from E2-E21 next block of numbers would be E3-E22
        }
    }

////////		Function that does all the computations to calculate, PercentGain, PercentLoss,
///////			Wins, Losses, etc, after a Pattern has been found. 
	
	public void enterTrade(int row, int EndingRow, Pattern PatternK){
		
		Day TradingDay = new Day(this);
		double closeOFNextDay, highOfDay, lowOfDay;
		
		row++;
		
		TradingDay.GetRowValues(row);
		PatternK.enterTradeDate = TradingDay.getDate();
		PatternK.enter = TradingDay.getOpen();
		PatternK.stopLoss = PatternK.L1.Low;
	
		PatternK.takeProfit = 2*(PatternK.enter - PatternK.stopLoss) + PatternK.enter;
		PatternK.takeProfit = Math.round(PatternK.takeProfit*100)/100.0d;
		
		for (int i = row; i < EndingRow; ) {
			TradingDay.GetRowValues(i);

			highOfDay = TradingDay.getHigh();
			lowOfDay = TradingDay.getLow();
			
			if (highOfDay >= PatternK.takeProfit){

				PatternK.exitTradeDate = TradingDay.getDate();
				PatternK.winners++;
				PatternK.avgPercentGain = ((PatternK.takeProfit - PatternK.enter)/PatternK.enter)*100;
				PatternK.avgPercentGain = Math.round(PatternK.avgPercentGain*100)/100.0d;
				PatternK.sumPercentGain += PatternK.avgPercentGain;
				i++;
				break;
			}
			else if (lowOfDay <= PatternK.stopLoss){
				
				PatternK.exitTradeDate = TradingDay.getDate();
				PatternK.losses++;
				PatternK.avgPercentGain = ((PatternK.stopLoss - PatternK.enter)/PatternK.enter)*100;
				PatternK.avgPercentGain = Math.round(PatternK.avgPercentGain*100)/100.0d;
				PatternK.sumPercentGain += PatternK.avgPercentGain;
				i++;
				break; 
			}
			i++;
		}
	}

////////		Function That Finds all the Patterns in the data of the given file
////////		Each Pattern Found is temporarily Saved in a Linked List.
////////		A Function Does the necessary calculations on the List of Patterns 
///////			and the results are saved onto a new File named "PatternResult.csv"
	public void findPatterns(String resultFileName){
		int StartingRow = 2;				// Ignore the file headers (in row 1)
		int EndingRow   = CSV.this.rows()-1;	// Last row on the file
		
		Pattern PatternK = new Pattern(this);  						  // An instance of a pattern H1, L1, H2, L2
		QueueOfPatterns theListOfPatterns = new QueueOfPatterns(this); // A List (in the form of a Queue) of all the patterns found
		int CurrentRow = StartingRow;
		int numOfPatterns = 0;

		while( CurrentRow <= EndingRow ){
			if( PatternK.H1.Flag == " " && PatternK.L1.Flag == " " && 
				PatternK.H2.Flag == " " && PatternK.L2.Flag == " "){   		// Find H1		
				CurrentRow = PatternK.FindH1(CurrentRow);
				CurrentRow++;
				}
			else if( PatternK.H1.Flag == "H1" && PatternK.L1.Flag == " " && 
				     PatternK.H2.Flag == " "  && PatternK.L2.Flag == " "){  // Find L1
				CurrentRow = PatternK.FindL1(CurrentRow);
				CurrentRow++;
				}
			else if (PatternK.H1.Flag == "H1" && PatternK.L1.Flag == "L1" && 
				     PatternK.H2.Flag == " "  && PatternK.L2.Flag == " "){    // Find H2
				CurrentRow = PatternK.FindH2(CurrentRow);
				CurrentRow++;
				}
			else if(PatternK.H1.Flag == "H1" && PatternK.L1.Flag == "L1" && 
				    PatternK.H2.Flag == "H2" && PatternK.L2.Flag == " "){     // Find L2
				CurrentRow = PatternK.FindL2(CurrentRow); 	
				CurrentRow++;
				}
			else if (  PatternK.H1.Flag == "H1" && PatternK.L1.Flag == "L1" && 
			           PatternK.H2.Flag == "H2" && PatternK.L2.Flag == "L2"){  // An instance of the pattern has been 														  		// found. Do calculations and save results
				
				if( CSV.this.get(0, CurrentRow+1) != ""){ // Check that there is data for the  2nd Day after L2
					this.enterTrade(CurrentRow, EndingRow, PatternK);
					theListOfPatterns.InsertPattern(PatternK);
					numOfPatterns++;
					}
				PatternK.reset();
				CurrentRow++;

				
				}			
			}

		theListOfPatterns.DisplayPatterns();				// Display All the Patterns in the QueueOfPatterns
	    try {
			CSV ResultFile = new CSV(); 					// Create a new object of type "csv"
			numOfPatterns+=3;
			ResultFile.GetListData( theListOfPatterns );	// Fill this new CSV object with Data from the QueueOfPatterns
			ResultFile.put(0, numOfPatterns, "Winners:");
			ResultFile.put(1, numOfPatterns, PatternK.winners);
			ResultFile.put(0, numOfPatterns+1, "Losers");
			ResultFile.put(1, numOfPatterns+1, PatternK.losses);
			ResultFile.put(0, numOfPatterns+2, "Avg Pct Gain");
			ResultFile.put(1, numOfPatterns+2, (PatternK.sumPercentGain)/(PatternK.winners+PatternK.losses));
			ResultFile.save(new File(resultFileName+".csv"));  //save all the resulting data onto a new file
			System.out.println();
			if( PatternK.winners+PatternK.losses == 0 ) 	// Avoid division by 0, in the extraordinary case of no winners/losers
				this.setTotalPercentGain(0.0);
			else
				this.setTotalPercentGain((PatternK.sumPercentGain)/(PatternK.winners+PatternK.losses));
	       
	    } catch (Exception e) {
	    System.out.println("DOUBLEWRONG");
	    e.printStackTrace();
	      } 
		}

	public static void main(String[] args){
    	/*
        try {
        	double num1, num2;
        	String str1, str2;
            CSV csv = new CSV();
 
            csv.open(new File("resultTSLA.csv")); //open file
            
            int maxRows = csv.rows(); //total number of rows
            int maxCols = csv.cols(); //total number of columns
            
            //TEST System.out.print(maxCols);
            //TEST System.out.print(maxRows);
            
            for (int col = 7; col < maxCols-1; col++)     //start at column H and row 51
            	for (int row = 50; row < maxRows; row++ ) //end at last row
            	{
            		str1 = csv.get(col, row);        //get string from (column H,row)
            		str2 = csv.get(col+1, row);		 //get string from (column I,row)
            		num1 = Double.parseDouble(str1); //change string1 to a double
            		num2 = Double.parseDouble(str2); //change string2 to a double
            		
            		if (num1 < num2)			
            		{							 	   //if column H < column I
            			csv.put(maxCols, row, "bear"); //insert "bear" into column J
            		}							 
            		else if (num1 > num2)
            		{							 	   //if column H > column I
            			csv.put(maxCols, row, "bull"); //insert "bull" into column J
            		}
            		else
            		{
            			csv.put(maxCols, row, "neutral");//if equal insert "neutral"
            		}							   		 //into column J
            	}

            csv.save(new File("TSLA_bearbull.csv")); //save file
        } catch (Exception e) {
        }*/

		}
	
	}

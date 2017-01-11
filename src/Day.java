
/////////////////////////////////////  DAY CLASS ////////////////////////////////////////
//An instance of this class is an object that holds the values of
//one day/Row in the given CSV file
    public class Day{
    	/**
		 * 
		 */
		private final CSV csv;
		String Date, Flag;   //Flag can be "Hi", "Lo" or " ", Hi=RelativeHigh, Lo=RelativeLow
    	double Open, High, Low, Close, SMA20, SMA50;
    	int index;

    	Day(CSV csv){
    		this.csv = csv;
			Date=Flag=" ";  				  //Initially each day is neither a high or a low
    		Open = High = Low = Close = SMA20 = SMA50=0.0;
    		index=-1; //non-existing index in a real file
    	}
    Day(CSV csv, int aRow){					   	// Constructor that takes the index of a Row and 	
    	this.csv = csv;
		Date = this.csv.get(0,  aRow);  // initializes all the values of "this" day 
    	Open = this.csv.getN(1, aRow);	// with the values in the given row
    	High = this.csv.getN(2, aRow);
    	Low  = this.csv.getN(3, aRow);
    	Close= this.csv.getN(4, aRow);
    	SMA20= this.csv.getN(7, aRow);
    	SMA50= this.csv.getN(8, aRow);
    	index=aRow;
    	Flag = " ";
    }
    
    ///		Display all the values of a Day object
    public void Display(){
    	System.out.println(index+", "+Date+", "+Open+", "+High+", "+Low+", "+Close+", "+SMA20+", "+SMA50+", "+Flag);	
    }
    
    ///		Display the values of "this" day as a relative High / Low
    public void DisplayHL(){ 
    	if(this.Flag == "H1" || this.Flag == "H2" )
    		System.out.println(index+", "+Date+", "+High+", "+Flag);	
    	else if(this.Flag == "L1" || this.Flag == "L2")
    		System.out.println(index+", "+Date+", "+Low +", "+Flag);
    	else
    		System.out.println(index+", "+Date+", "+High+", "+Low +", "+Flag);  //// !!!!!!!!!!!!!!!!!!!   D E L E T E  T H I S  L A T E R 
    }
   
    ///		Clear all the values on a Day object
    public void ClearVals(){
    	Date=Flag=" ";
    	Open = High = Low = Close = SMA20 = SMA50=0.0;
    	index=-1; //Nonexistent index in a real file
    }
    
    //		Fill/Overwrite all the values of a Day object with the values in the indicated row
    public void GetRowValues(int aRow){  // Set all the values of "this" day equal
    	Date = this.csv.get(0, aRow);    // to the values in the given row
    	Open = this.csv.getN(1, aRow);
    	High = this.csv.getN(2, aRow);
    	Low  = this.csv.getN(3, aRow);
    	Close= this.csv.getN(4, aRow);
    	SMA20= this.csv.getN(7, aRow);
    	SMA50= this.csv.getN(8, aRow);
    	index=aRow;
    }
    
    //		Fill/Overwrite all the values of a Day with all the Values on the given Day object
    public void SetEqualTo(Day aDay){  // Set all the values of "this" day equal
    	this.Date = aDay.Date;    	   // to the values in the given Day object
    	this.Open = aDay.Open;
    	this.High = aDay.High;
    	this.Low  = aDay.Low;
    	this.Close= aDay.Close;
    	this.SMA20= aDay.SMA20;
    	this.SMA50= aDay.SMA50;
    	this.Flag = aDay.Flag;
    	this.index =aDay.index;
    	}
    
    //		Accessor Functions to get the values of a Day
	public double getHigh() { return High; }
	public double getOpen() { return Open; }
	public double getClose(){ return Close; }
	public String getDate() { return Date; }
	public double getLow()  { return Low;  }
	
    }
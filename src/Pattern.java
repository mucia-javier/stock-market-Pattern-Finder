
///////////////////////////////    PATTERN CLASS //////////////////////////////
// This class holds an instance of the pattern H1, L1, H2, L2. Using four points: H1, L1, H2, L2. 
// where: H1 = Relative High, L1 = Relative Low,
//        H2 = Relative High (Higher than H1), L2 = Relative Low (Higher than L1)
	public class Pattern{
		
		/**
		 * 
		 */
		private final CSV csv;
		int winners, losses;
		double avgPercentGain, takeProfit, stopLoss, enter, exit, sumPercentGain;
		String enterTradeDate, exitTradeDate;
		public Day H1, L1, H2, L2;      // Values of:High1, Low1, High2, Low2 
		Pattern Next;    			// Link Form ONe pattern to another to make a LinkedList (in the form of Queue)
		
		//		Default Constructor: Initialize a new pattern
		Pattern(CSV csv){ 
			this.csv = csv;
			winners = losses = 0;
			avgPercentGain = takeProfit = stopLoss = enter = exit = sumPercentGain = 0.0;
			
			H1 = new Day(this.csv);
			L1 = new Day(this.csv);
			H2 = new Day(this.csv);
			L2 = new Day(this.csv);
			Next = null;
			}
		
		//		Set all the values of "this" Pattern equal to the given pattern "P"		
		public void CopyPattern(Pattern P){
			this.H1.SetEqualTo(P.H1);
			this.L1.SetEqualTo(P.L1);
			this.H2.SetEqualTo(P.H2);
			this.L2.SetEqualTo(P.L2);
			this.winners = P.winners;
			this.losses =  P.losses;
			this.avgPercentGain = P.avgPercentGain;
			this.takeProfit = P.takeProfit;
			this.stopLoss = P.stopLoss;
			this.enter = P.enter;
			this.exit = P.exit;
			this.enterTradeDate = P.enterTradeDate;
			this.exitTradeDate = P.exitTradeDate;
			this.sumPercentGain = P.sumPercentGain;
			this.Next = P.Next;
			}
		
		//		Clear all the Values on the "this" Pattern Object
		void reset(){
			H1.ClearVals();
			L1.ClearVals();
			H2.ClearVals();
			L2.ClearVals();
			
			avgPercentGain = takeProfit = stopLoss = enter = exit = 0.0;
			enterTradeDate = exitTradeDate = null;
			Next = null;
			}
	
		//		Display all the values of "this" Pattern object
		void Display(){
			H1.DisplayHL();
			L1.DisplayHL();
			H2.DisplayHL();
			L2.DisplayHL();
			System.out.println("Entering trade at: " + enter + " on " + enterTradeDate);
			System.out.println("Stop Loss is: " + stopLoss);
			System.out.println("Take Profit at: " + takeProfit);
			System.out.println("Exit trade at " + exit + " on " + exitTradeDate);
			System.out.println("Percent Gain = " + avgPercentGain + "%");	
			System.out.println("Sum PERCENT GAIN = " + sumPercentGain);
			System.out.println(winners+", "+losses+", "+avgPercentGain+", "+"\n");

			}	
		
		//		Finds "H1" for "this" pattern starting at the given row
		public int FindH1(int CurrentRow){
			Day CurrentDay = new Day(this.csv, CurrentRow);
			
			while( this.H1.Flag != "H1" && CurrentRow<this.csv.rows() ){                         // This loop does not stop until H1 is found
				Day PreviousDay = new Day(this.csv, CurrentRow-1); // The first relative high found is made our H1
				Day NextDay    = new Day(this.csv, CurrentRow+1);  //
				
				if( PreviousDay.High<CurrentDay.High && CurrentDay.High>NextDay.High ){  // Check if Current day is 
					this.H1.SetEqualTo(CurrentDay);										 // a relative high (Higher than
					this.H1.Flag = "H1";												 // the previous and next day high)  
					this.H1.index = CurrentRow;
					return CurrentRow;
					}
				else {										// If Current day is not a relative High, then move to the next day
					CurrentRow++;							// 
					CurrentDay.GetRowValues(CurrentRow);
					}
				}
			return CurrentRow;
			}
		
		//		Finds "L1" for "this" pattern starting at the given row
		public int FindL1(int CurrentRow){
			Day CurrentDay = new Day(this.csv, CurrentRow);
			
			while( this.L1.Flag != "L1" && CurrentRow<this.csv.rows() ){  							// This loop doesn't stop until L1 is found
				Day PreviousDay = new Day(this.csv, CurrentRow-1);
				Day NextDay    = new Day(this.csv, CurrentRow+1);
				
				if( PreviousDay.Low>CurrentDay.Low && CurrentDay.Low<NextDay.Low ){ 	// CHeck if current day is a relative Low
					this.L1.SetEqualTo(CurrentDay); 									// (Lower than previous and next Lows)
					this.L1.Flag = "L1";	
					this.L1.index = CurrentRow;
					break;
					}
				else if( (PreviousDay.High<CurrentDay.High && CurrentDay.High>NextDay.High) && (CurrentDay.High>this.H1.High) ) {
					this.H1.SetEqualTo(CurrentDay);								// If current day is not a relative low, and if it is
					this.H1.Flag = "H1";										// a relative high, higher than H1, make this our H1
					this.H1.index=CurrentRow;
					CurrentRow++;
					CurrentDay.GetRowValues(CurrentRow);
					}
				else {
					CurrentRow++;												// If current day is neither, then move to the next
					CurrentDay.GetRowValues(CurrentRow);						// day
					}
				}
			return CurrentRow;
			}
		
		//		Finds "H2" for "this" pattern starting at the given row
		public int FindH2(int CurrentRow){
			Day CurrentDay = new Day(this.csv, CurrentRow);
			
			while( this.H2.Flag != "H2"  && CurrentRow<this.csv.rows() ){												// Loop does not stop until H2 is found
				Day PreviousDay = new Day(this.csv, CurrentRow-1);
				Day NextDay    = new Day(this.csv, CurrentRow+1);
				
				if(PreviousDay.High<CurrentDay.High && CurrentDay.High>NextDay.High ){ // Check if current day is a relative high
						if( CurrentDay.High > this.H1.High){							// If it is a relative high, then Check 
							this.H2.SetEqualTo(CurrentDay);								// if it is Higher than H1, If it is
							this.H2.Flag = "H2"; 										// then make this relative high our H2
							this.H2.index=CurrentRow;
							return CurrentRow;
							}
						else {															// If the relative High found is not higher than 
							this.H1.SetEqualTo(CurrentDay);								// H1, then make this relative high our H1 and 
							this.H1.Flag = "H1";										// we reset the pattern
							this.H1.index=CurrentRow;
							this.L1.ClearVals();
							this.H2.ClearVals();
							return CurrentRow;
							}
					 }
				else if((PreviousDay.Low>CurrentDay.Low && CurrentDay.Low<NextDay.Low) && ( CurrentDay.Low<this.L1.Low) ) {
					this.L1.SetEqualTo(CurrentDay);									// If current day is not a relative high, check if it is
					this.L1.Flag = "L1";											// a relative low, lower than our L1. If this new relative
					this.L1.index=CurrentRow;										// is lower than L1, then make it our L1
					CurrentRow++;
					CurrentDay.GetRowValues(CurrentRow);
					}
				else {																// If current day is neither a relative low/high, then just
					CurrentRow++;													// move to the next day 
					CurrentDay.GetRowValues(CurrentRow);
					}
				}
			return CurrentRow;
			}
			
		//		Finds "L2" for "this" pattern starting at the given row
		public int FindL2(int CurrentRow){
			Day CurrentDay = new Day(this.csv, CurrentRow);

			while( this.L2.Flag != "L2" && CurrentRow<this.csv.rows() ){ 							// This is loop does not stop until L2 is found
				Day PreviousDay = new Day(this.csv, CurrentRow-1);
				Day NextDay    = new Day(this.csv, CurrentRow+1);
				
				if( PreviousDay.Low>CurrentDay.Low && CurrentDay.Low<NextDay.Low ){ // First check if current day is a Relative low
					if( CurrentDay.Low > this.L1.Low) {								// If it is, then make sure that it is Higher than
						this.L2.SetEqualTo(CurrentDay);								// L1, to make it our L2
						this.L2.Flag = "L2";
						this.L2.index=CurrentRow;
						return CurrentRow;
						}
					else{ 								 							// If this relative low is lower than L1, 
						this.L1.SetEqualTo(CurrentDay);								// Make this relative Low our L1 and reset the pattern 
						this.L1.Flag = "L1";										// up to H1, L1, to start searching for  H2 again
						this.L1.index = CurrentRow;
						
						this.H1.SetEqualTo(this.H2);
						this.H1.Flag = "H1";
						
						this.H2.ClearVals();
						this.L2.ClearVals();
						return CurrentRow;
						}
					}
				else if( (PreviousDay.High<CurrentDay.High && CurrentDay.High>NextDay.High) && (CurrentDay.High>this.H2.High) ){
					this.H2.SetEqualTo(CurrentDay);						// If this day is not a relative Low, check if it is a relative
					this.H2.Flag = "H2";								// High. If it is a relative High higher than H2, then make this 
					this.H2.index=CurrentRow;							// our H2.
					CurrentRow++;
					CurrentDay.GetRowValues(CurrentRow);
					}
				else{													// If current day is neither a relative High/Low, then just move to 
					CurrentRow++;										// the next day
					CurrentDay.GetRowValues(CurrentRow);
					}
				}
		return CurrentRow;
		}
	}
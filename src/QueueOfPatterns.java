
///////////////////////////////    QUEUE-OF-PATTERNS CLASS //////////////////////////////
// Linked LIst in the form of a Queue that temporarily Saves all instances 
// of the the Pattern found in the Data. This list is later on Written onto a File
	public class QueueOfPatterns{
		
		/**
		 * 
		 */
		private final CSV csv;
		Pattern First;  		// Reference to the first element in the linked list
		Pattern Last;			// Reference to the last element in the linked list
		
		// 		Default Constructor for an emty list
		QueueOfPatterns(CSV csv){ 
			this.csv = csv;
			First = null;
			Last  = null; 
			}		
		
		//		Inserts a new Pattern-Object at the end of the list/Queue
		public void InsertPattern( Pattern aPattern ){
			Pattern newPattern = new Pattern(this.csv);
			newPattern.CopyPattern(aPattern);
			if( this.isEmpty() ) {
				First = newPattern;
				Last  = newPattern; 
				}
			else {
				Last.Next = newPattern;
				Last = newPattern; 
				}
			}
		
		//		Deletes and Returns the first Pattern-Object on the list/Queue		
		public Pattern DeleteFirst(){
			Pattern CopyOfPattern = new Pattern(this.csv);
			if( this.isEmpty() ){
				return null;
				}
			else {
				CopyOfPattern.CopyPattern(First);
				this.First = First.Next;
				}
			return CopyOfPattern;
			}
		//		Displays the all the elements of "this" Pattern-Object
		public void DisplayPatterns(){
			Pattern Current = First;
			while( Current != null ){
				Current.Display();
				Current = Current.Next;
				}
			}
		
		//		Returns true if the current list is Empty. Returns False otherwise
		public boolean isEmpty() {
			return (First==null); }
	
	}
# stock-market-Pattern-Finder
** Main class is "SMA.java"



** THIS PROGRAM FINDS THE PATTERN HIGH-LOW-HIGH-LOW ON HISTORICAL PRICES OF STOCKS, GIVEN BY YAHOO FINANCE
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

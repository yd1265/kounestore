package com.bookstore.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {
	
	public final static String US="US";
	
	public final static Map<String, String> mapOfUSStates(){
		   HashMap<String, String> usStates=new HashMap<String, String>();
		   
		   usStates.put("AL", "Alabama");
		   usStates.put("AK", "Alaska");
		   usStates.put("AZ", "Arizona");
		   usStates.put("AR", "Arkansas");
		   

		   usStates.put("Ca", "California");
		   usStates.put("CT", "Connecticut");

		   usStates.put("DL", "Delaware");
		   usStates.put("DC", "Discrit of Columbia");
		   usStates.put("FL", "Florida");
		   usStates.put("GA", "Georgia");

		   usStates.put("HI", "Hawali");
		   usStates.put("ID", "Idaho");
		   usStates.put("IL", "Illinois");
		   usStates.put("IN", "Indiana");

		   usStates.put("IA", "Iowa");
		   usStates.put("KS", "Kansas");
		   usStates.put("KY", "Kentucky");
		   usStates.put("LA", "Louisiana");
		   
		   
		   usStates.put("ME", "Maine");
		   usStates.put("MD", "Maryland");
		   usStates.put("Ma", "Massachussets");
		   usStates.put("MI", "Michigan");

		   usStates.put("MN", "Minnesota");
		   usStates.put("MS", "Mississippi");
		   
		   usStates.put("MO", "Missouri");
		   usStates.put("MT", "Montana");

		   usStates.put("NE", "Nebraska");
		   usStates.put("NV", "Nevada");
		   usStates.put("NH", "New Hamphsire");
		   usStates.put("NJ", "New Jersey");
		   
		   usStates.put("NM", "New Mexico");
		   usStates.put("NY", "New York");
		   usStates.put("NC", "North Carolina");
		   usStates.put("NK", "North Dakota");
		   usStates.put("OH", "Ohio");
		   usStates.put("OK", "Oklahoma");
		   usStates.put("OR", "Oregan");
		   usStates.put("PA", "Pennsylvania");
		   usStates.put("RI", " Rhode Island");
		   usStates.put("SC", "South Carolina");
		   usStates.put("SD", "South Dakota");
		   usStates.put("TN", "Tennesse");
		   usStates.put("TX", "Texas");
		   usStates.put("UT", "Utah");
		   usStates.put("VT", "Vermont");
		   usStates.put("VA", "Virgina");
		   usStates.put("WA", "Washington");
		   usStates.put("WV", "West Virgina");
		   usStates.put("WI", "Wisconsin");
		   usStates.put("WY", "Wyoming");
		

		    return usStates;
	}
	
   public final static List<String> listOfStatesCode=new ArrayList<>(mapOfUSStates().keySet());
   
   public final static List<String> listOfStatesNames=new ArrayList<>(mapOfUSStates().values());


}

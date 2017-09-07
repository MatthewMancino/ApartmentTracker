import java.util.*;

public class Apartment{
	String Address, City, HashString;
	int AptNum, ZIPCode, Rent, SQFT, Index;
	
	public Apartment(String a,String b, int c, int d, int e, int f)
	{
		Address = a;
		City = b;
		AptNum = c;
		ZIPCode = d;
		Rent = e;
		SQFT = f;
		
		StringBuilder B = new StringBuilder();
		B.append(Address);
		B.append(ZIPCode);
		B.append(AptNum);
	
		
		HashString = B.toString();
		
	}  
	/*  Prints out the entire Apartment as a String
	 * 
	 */
	public String toString()
	{
		String y = "    \n";
		StringBuilder Stringy = new StringBuilder();
		Stringy.append("Address: "+ Address);
		Stringy.append("  Apt#: "+ AptNum);
		Stringy.append("  City: "+ City+y);
		Stringy.append("  ZIP: "+ ZIPCode +y);
		Stringy.append("	Monthly Rent: "+ Rent);
		Stringy.append("	Square Feet: "+SQFT+y);

		return Stringy.toString();
	}
	public int getPrice()
	{
		return Rent;
	}
	public String city() { return City;}
}

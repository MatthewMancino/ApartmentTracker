import java.util.*;
import java.io.*;
public class AptTracker {
	
	public static void main(String [] args) throws FileNotFoundException, InputMismatchException
	{	
		
		HeapPQ [] PQs = new HeapPQ[20]; //Make an array of priority Queues 
		SeparateChainingHashST<String, Integer> PQin = new SeparateChainingHashST<String, Integer>();
		
		PQs[0] = new HeapPQ(true);  //the PQ for all apartments by rent
		PQs[1] = new HeapPQ(false);   //the PQ for all apartments by sqft
		int CityIndex = 2;
		
		Scanner Scanny = new Scanner(System.in);

		System.out.println("Welcome to the Apartment Tracker!\n");
		while (true)
		{
			System.out.println("Menu Options (Enter the number of the menu option)\n1)Add apt      2)Update apt rent   3) Remove Apartment\n4)Lowest price 5) Highest sqft apt 6) lowest price by city 7) highest sqft by city");
			switch (Scanny.nextInt())
			{
				case 1:  //Creates a new Apartment object and adds it to the data structure.
					Scanny.nextLine();
					String street, city;
					int num,zip,ppm,sqft;
					
					System.out.println("What is the street address of this apartment?");
					street = Scanny.nextLine();
					System.out.println("What is the number of this apartment?");
					num = Scanny.nextInt();
					System.out.println("What is the city which this apartment is located in?");
					Scanny.nextLine();
					city = Scanny.nextLine();
					System.out.println("What is the ZIP code of this apartment");
					zip = Scanny.nextInt();
					System.out.println("What is the rent for each month?");
					ppm = Scanny.nextInt();
					System.out.println("What is the square-footage of the apartment");
					sqft = Scanny.nextInt();
					
					
					int a;
					if (PQin.contains(city))
					{
						a = (int) PQin.get(city);
						System.out.println("city found");
						
					}
					else
					{
						PQs[CityIndex] = new HeapPQ(true);
						PQs[CityIndex+1] = new HeapPQ(false);
						PQin.put(city,CityIndex);
						a = CityIndex;
						CityIndex++;
						CityIndex++;
					}
		
					
					Apartment here = new Apartment(street,city,num,zip,ppm,sqft);
					
					System.out.println(here.toString());
					
					PQs[0].add(new Apartment(street,city,num,zip,ppm,sqft));
					PQs[1].add(new Apartment(street,city,num,zip,ppm,sqft));
					PQs[a].add(new Apartment(street,city,num,zip,ppm,sqft));
					PQs[a+1].add(new Apartment(street,city,num,zip,ppm,sqft));
					break;
					
					
				case 2: //Update the apartment
					Scanny.nextLine();
					String HS = buildHashString(Scanny);
					System.out.println("Would you like to update the rent of this apartment?(type yes)");
					
					if(Scanny.next().equals("yes"))
					{
						System.out.println("What is the new rent value of this apartment?");
						int rent = Scanny.nextInt();
						
						int index = (int) PQin.get(PQs[0].city(HS));
						PQs[0].update(HS,rent);
						PQs[1].update(HS,rent);
						PQs[index].update(HS, rent);
						PQs[index+1].update(HS,rent);
						System.out.println(PQs[0].apt(HS));
					}
					break;
					
					
				case 3:  //Remove an apartment
					Scanny.nextLine();
					String HS2 = buildHashString(Scanny);
					System.out.println("Would you like to remove this apartment?");
					
					if (Scanny.next().equals("yes"))
					{
						if (PQs[0].city(HS2) == null)
								{ System.out.println("invalid String");}
						int index = (int) PQin.get(PQs[0].city(HS2));
						System.out.println(PQs[0].apt(HS2));
						PQs[0].remove(HS2);
						PQs[1].remove(HS2);
						PQs[index].remove(HS2);
						PQs[index+1].remove(HS2);
						if(PQs[index].isEmpty())
						{
							PQs[index] = null;
							PQs[index] = null;
							PQin.delete(PQs[0].city(HS2));
						}
					}
					break;
				
				case 4:  //Gets Cheapest apartment
					if(PQs[0].isEmpty())
					{
						System.out.println("none added yet");
						break;
					}
					System.out.println(PQs[0].getLowestPrice());
					break;
					
				case 5:  //Gets largest apartment
					System.out.println(PQs[1].getHighSQFT());
					break;
					
				case 6:  //Gets cheapest apartment in X (Pittsburgh)
					Scanny.nextLine();
					System.out.println("What city would you like to see?");
					String CTY = Scanny.nextLine();
					if(PQin.contains(CTY))
					{
						int indexr = (int) PQin.get(CTY);
						System.out.println(PQs[indexr].getLowestPrice());	
					}
					else
					{
						System.out.println("City Not Found");
					}
					
					break;
					
				case 7: //Gets biggest apartment in X (Pittsburgh)
					Scanny.nextLine();
					System.out.println("What city would you like to see?");
					String CITY = Scanny.nextLine();
					if(PQin.contains(CITY))
					{
						int indexy = (int) PQin.get(CITY);
						System.out.println(PQs[indexy+1].getHighSQFT());
					}
					else
					{
						System.out.println("City Not Found");
					}
					
					break;
					
				default:
					System.out.println("Error: Try Again");
					break;
			}
			
		}
	
		
	}
	public static String buildHashString(Scanner scan)
	{
		StringBuilder B = new StringBuilder();
		
		System.out.println("What is the street address of this apartment?");
		String Address = scan.nextLine();
		System.out.println("What is the Apartment Number?");
		int AptNum = scan.nextInt();
		System.out.println("What is the ZIP Code?");
		int ZIP = scan.nextInt();
		
		B.append(Address);
		B.append(ZIP);
		B.append(AptNum);
		
		return B.toString();
		
	}

}

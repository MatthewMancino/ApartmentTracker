//Author - Matthew Mancino - This class is the implementation of the Priority Queue via a binary heap
import java.util.*;

public class HeapPQ {
	
	Apartment [] Apartments; 								//Apartment array holding the data
	SeparateChainingHashST<String, Integer> HT = new SeparateChainingHashST<String, Integer>(); //Indirection Hash Table!
	boolean rent;  											//Used to determine whether this PQ is organizing for rent or SQFT
	int EndOfArray = 0;										
	
	public HeapPQ(boolean LowestRent) //Initializes PQ
	{
		Apartments = new Apartment[100];
		rent = LowestRent;
	}
	
	/* Adds an apartment to the end of the heap then swims up to the correct location in the array. Also adds the HashString to the HashTable
	 * 
	 */
	public void add(Apartment entry)
	{
		if (rent)
		{
			addByPrice(entry);
		}
		else 
		{
			addBySqFt(entry);
		}
	}
	
	private void addByPrice(Apartment entry)
	{
		boolean property = false;
		Apartments[EndOfArray] = entry; //put entry at the end
		int curr = EndOfArray;
		while (property == false)
		{
			int parent = (curr-1)/2;
			if(Apartments[curr].Rent < Apartments[parent].Rent)
			{
				HT.put(Apartments[parent].HashString, new Integer(curr));
				Apartment Holder = Apartments[curr];
				Apartments[curr] = Apartments[parent];
				Apartments[parent] = Holder;
				
				curr = parent;
				
			}
			else 
			{
				property = true;
			}
		}
		HT.put(entry.HashString,new Integer(curr));
		EndOfArray++;
		System.out.println(curr);
	}
	
	private void addBySqFt(Apartment entry)
	{
		boolean property = false;
		Apartments[EndOfArray] = entry; //put entry at the end
		int curr = EndOfArray;
		while (property == false)
		{
			int parent = (curr-1)/2;
			if(Apartments[curr].SQFT > Apartments[parent].SQFT)
			{
				HT.put(Apartments[parent].HashString, new Integer(curr));
				Apartment Holder = Apartments[curr];
				Apartments[curr] = Apartments[parent];
				Apartments[parent] = Holder;
				
				curr = parent;
			}
			else 
			{
				property = true;
			}
		}
		HT.put(entry.HashString, new Integer(curr));
		System.out.println(curr);
		EndOfArray++;
	}
	
	
	/*@params HashString - Address,AptNum,ZIPCode appended together
	 * int newrent - the value to update the given rent to
	 * Updates the rent value of a given apartment via indirection and then application of swim and sink functions
	 * 
	 */
	
	public void update(String HashString, int newrent)
	{
		Integer IIndex = HT.get(HashString);
		if (IIndex == null)
		{
			System.out.println("Apartment not found");
			return;
		}
		Apartments[IIndex].Rent = newrent;
		int curr = IIndex;
		boolean down = true;
		if (rent)
		{
			boolean property = false;
			while (property == false)
			{
				int parent = (curr-1)/2;
				int leftChild = (2*curr) + 1;
				int rightChild = (2*curr) + 2;
				if(Apartments[curr].Rent < Apartments[parent].Rent)
				{
					HT.put(Apartments[parent].HashString, new Integer(curr));
					Apartment Holder = Apartments[curr];
					Apartments[curr] = Apartments[parent];
					Apartments[parent] = Holder;
					curr = parent;
					down = false;
				}
				else if (down)
				{
					if(Apartments[leftChild] == null)
					{ 
						if(Apartments[rightChild] == null)
						{ property = true;}
						
						else if(Apartments[curr].Rent > Apartments[rightChild].Rent)
						{
							HT.put(Apartments[rightChild].HashString, new Integer(curr));
							Apartment Holder = Apartments[curr];
							Apartments[curr] = Apartments[parent];
							Apartments[parent] = Holder;
							curr = parent;
						}
						else
						{
							property = true;
						}
					}
					else
					{
						if(Apartments[rightChild] == null)
						{
							if (Apartments[curr].Rent > Apartments[leftChild].Rent)
							{
								HT.put(Apartments[leftChild].Address, new Integer(curr));
								Apartment Holder = Apartments[curr];
								Apartments[curr] = Apartments[leftChild];
								Apartments[leftChild] = Holder;
								curr = leftChild;
							}
							else
							{
								property = true;
							}
						}
						else
						{
							if(Apartments[leftChild].Rent < Apartments[rightChild].Rent)
							{
								if (Apartments[curr].Rent > Apartments[leftChild].Rent)
								{
									HT.put(Apartments[leftChild].HashString, new Integer(curr));
									Apartment Holder = Apartments[curr];
									Apartments[curr] = Apartments[leftChild];
									Apartments[leftChild] = Holder;
									curr = leftChild;
								}
								else
								{
									property = true;
								}
							}
							else
							{
								if (Apartments[curr].Rent > Apartments[rightChild].Rent)
								{
									HT.put(Apartments[rightChild].Address, new Integer(curr));
									Apartment Holder = Apartments[curr];
									Apartments[curr] = Apartments[rightChild];
									Apartments[rightChild] = Holder;
									curr = rightChild;
								}
								else
								{
									property = true;
								}
							}
						}
					}
				}
				else if (!down)
				{
					property = true;
				}
				
			}
		
		}
		HT.put(Apartments[curr].HashString, curr);
		System.out.println(curr);
		
		
	}
	
	public void remove(String HashString)
	{
		if (rent)
		{
			removeByRent(HashString);
		}
		else 
		{
			removeBySQFT(HashString);
		}
	}
	
	/*removes the apartment from the PQ, then rearranges PQ by Lowest Rent Price
	 * @params HashString (see above)
	 * 
	 */
	
	private void removeByRent(String HashString) //Removes an apartment from
	{
		Integer IIndex = HT.get(HashString);
		if (IIndex == null)
		{
			System.out.println("Apartment not found");
			return;
		}
		
		Apartments[IIndex] = Apartments[EndOfArray - 1];
		Apartments[EndOfArray - 1] = null;
		int curr = IIndex;
		boolean property = false;
		while (property == false)
		{
			int leftChild = (2*curr+1); 
			int rightChild = (2*curr+2);   //Check left child
			if (Apartments[leftChild] == null) // if it's null, check righ child
			{
				if(Apartments[rightChild] == null)  ///check right child -> if null
				{
					property = true;
				}
				else
				{
					if (Apartments[curr].Rent > Apartments[rightChild].Rent)
					{
						HT.put(Apartments[rightChild].HashString, new Integer(curr));
						Apartment Holder = Apartments[curr];
						Apartments[curr] = Apartments[rightChild];
						Apartments[rightChild] = Holder;
						curr = rightChild;
					}
					else
					{
						property = true;
					}
				}
			}
			else
			{
				if(Apartments[rightChild] == null)
				{
					if (Apartments[curr].Rent > Apartments[leftChild].Rent)
					{
						HT.put(Apartments[leftChild].HashString, new Integer(curr));
						Apartment Holder = Apartments[curr];
						Apartments[curr] = Apartments[leftChild];
						Apartments[leftChild] = Holder;
						curr = leftChild;
					}
					else
					{
						property = true;
					}
				}
				else
				{
					if (Apartments[leftChild].Rent < Apartments[rightChild].Rent)
					{
						if (Apartments[curr].Rent > Apartments[leftChild].Rent)
						{
							HT.put(Apartments[leftChild].HashString, new Integer(curr));
							Apartment Holder = Apartments[curr];
							Apartments[curr] = Apartments[leftChild];
							Apartments[leftChild] = Holder;
							curr = leftChild;
						}
						else
						{
							property = true;
						}
					}
					else if (Apartments[leftChild].Rent > Apartments[rightChild].Rent)
					{
						if (Apartments[curr].Rent > Apartments[rightChild].Rent)
						{
							HT.put(Apartments[rightChild].HashString, new Integer(curr));
							Apartment Holder = Apartments[curr];
							Apartments[curr] = Apartments[rightChild];
							Apartments[rightChild] = Holder;
							curr = rightChild;
						}
						else
						{
							property = true;
						}
					}
				}
			}
		}
		if(Apartments[curr] != null)
		{
			HT.put(Apartments[curr].HashString, curr);
			System.out.println(curr);
		}
		EndOfArray--;
	}
		
	/* Similar to RemoveByRent - removes apartment then rearranges based on 
	 * 
	 */
	public void removeBySQFT(String HashString)
	{
		Integer IIndex = HT.get(HashString);
		if (IIndex == null)
		{
			System.out.println("Apartment not found");
			return;
		}
		
		Apartments[IIndex] = Apartments[EndOfArray-1];
		Apartments[EndOfArray-1] = null;
		int curr = IIndex;
		boolean property = false;
		while (property == false)
		{
			int leftChild = (2*curr+1); 
			int rightChild = (2*curr+2);   //Check left child
			if (Apartments[leftChild] == null) // if it's null, check righ child
			{
				if(Apartments[rightChild] == null )  ///check right child -> if null
				{
					property = true;
				}
				else
				{
					if (Apartments[curr].SQFT < Apartments[rightChild].SQFT)
					{
						HT.put(Apartments[rightChild].HashString, new Integer(curr));
						Apartment Holder = Apartments[curr];
						Apartments[curr] = Apartments[rightChild];
						Apartments[rightChild] = Holder;
						curr = rightChild;
					}
					else
					{
						property = true;
					}
				}
			}
			else
			{
				if(Apartments[rightChild] == null)
				{
					if (Apartments[curr].SQFT < Apartments[leftChild].SQFT)
					{
						HT.put(Apartments[leftChild].HashString, new Integer(curr));
						Apartment Holder = Apartments[curr];
						Apartments[curr] = Apartments[leftChild];
						Apartments[leftChild] = Holder;
						curr = leftChild;
					}
					else
					{
						property = true;
					}
				}
				else
				{
					if (Apartments[leftChild].SQFT > Apartments[rightChild].SQFT)
					{
						
						if (Apartments[curr].SQFT < Apartments[leftChild].SQFT)
						{
							HT.put(Apartments[leftChild].HashString, new Integer(curr));
							Apartment Holder = Apartments[curr];
							Apartments[curr] = Apartments[leftChild];
							Apartments[leftChild] = Holder;
							curr = leftChild;
						}
						else 
						{
							property = true;
						}
					}
					else
					{
						if (Apartments[curr].SQFT < Apartments[rightChild].SQFT)
						{
							HT.put(Apartments[rightChild].HashString, new Integer(curr));
							Apartment Holder = Apartments[curr];
							Apartments[curr] = Apartments[rightChild];
							Apartments[rightChild] = Holder;
							curr = rightChild;
						}
						else 
						{
							property = true;
						}
					}
				}
			}
		}
		if(Apartments[curr] != null)
		{
			HT.put(Apartments[curr].HashString, curr);
			System.out.println(curr);
		}
		EndOfArray--;
		
	}
	
	/* getLowestPrice() Looks up the lowest priced apartment in O(1) time
	 * same with getHighSQFT()
	 */
    public String getLowestPrice() 
	{
		return Apartments[0].toString();
	}	
	public String getHighSQFT()
	{	
		return Apartments[0].toString();
	}

	/*Helper Functions
	 * public String apt(String HashString) - returns an apartment given by @param HashString
	 * public String city(String HashString) - return the city of a given apartment by @param hashString
	 * public boolean isEmpty() - checks if the PQ is empty
	 */
	
	public String apt(String HashString)
	{
		return Apartments[(int) HT.get(HashString)].toString();
	}
	public String city(String HashString)
	{
		if (HT.contains(HashString))
		{
			return Apartments[(int) HT.get(HashString)].city();
		}
		else
		{
			return null;
		}	
	}
	
	public boolean isEmpty()
	{
		if (Apartments[0] == null){return true;}
		return false;
	}
	
}


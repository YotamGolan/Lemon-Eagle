import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hasher {
	public Hashtable<String, Integer> spearHash = new Hashtable<String, Integer>();
	Set<String> keys;
	public Hasher() throws FileNotFoundException {
		shakereader();
	}


	public void shakereader() throws FileNotFoundException{
		// Intakes the shakespeare.txt file
		// Creates an arraylist of arrays to hold input
		Scanner in = new Scanner(new File("shakespeare.txt"));
		// converts input lines into int arrays using tokenization
		// adds each int[] to the arraylist 
		//	int i = 0;
		while (in.hasNextLine()) {
			String lines = in.nextLine().trim();
			lines = lines.toLowerCase();
			String[] tokens = lines.split("[\\s?,.!:;\\[\\]]+");
			//System.out.println(i);
			//i++;
			for(String words : tokens) {
				if(spearHash.containsKey(words))
				{
					int n = spearHash.get(words);
					spearHash.put(words, n+1);
				}
				else
					spearHash.put(words, 1);
			}
		}
		in.close();
		keys = spearHash.keySet();
		// converts the arraylist of int[] to a int[][] because otherwise my code doesnt work and making the entire function use a arraylist would 
		// be better code probably but i am lazy
	}

	public String findIt(int length, int position)
	{
		ArrayList<String> PreSort = new ArrayList<String>();
		//int k = 0;
		//System.out.println(keys.toString());
		//if(true) return "-";
		for(String key : keys)
		{
			//System.out.println(k);
			//k++;
			if(key.length() == length)
				PreSort.add(key);
		}

		for(int i = 0; i < PreSort.size() ; i++)
		{
			for(int j = i + 1; j < PreSort.size() ; j++)
			{
				int x = spearHash.get(PreSort.get(i));
				int y = spearHash.get(PreSort.get(j));
				if (x < y) 
				{
					String temp = "";
					temp = PreSort.get(j);
					PreSort.set(j, PreSort.get(i));
					PreSort.set(i,temp);
				}
				else if(x == y) {
					if(PreSort.get(i).compareTo(PreSort.get(j)) > 0)
					{
						String temp = "";
						temp = PreSort.get(j);
						PreSort.set(j, PreSort.get(i));
						PreSort.set(i,temp);
					}
				}
			}


		}
		//if(true) return PreSort.toString();
		if(PreSort.size()>position)
			return PreSort.get(position);
		return "-";
	}


}


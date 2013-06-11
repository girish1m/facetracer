import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;


public class DataFilter
{
	public static HashSet<Integer> hset=new HashSet<Integer>();
	
	private static void filterData(String facestats,String output) throws IOException
	{
		FileInputStream fis = new FileInputStream(facestats);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);

		FileWriter fw = new FileWriter(output);
		
		while (dis.available() != 0) 
		{
			String line = dis.readLine().trim();
			if(line.startsWith("#")) continue;
			String tokens[] = line.split("\t");
			if(hset.contains(Integer.parseInt(tokens[0])))
				fw.write(line+"\n");
		}
		fw.close();

	}



	private static void readLabels(String facelabels) throws IOException
	{
		FileInputStream fis = new FileInputStream(facelabels);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);

		while (dis.available() != 0) 
		{
			String line = dis.readLine().trim();
			if(line.startsWith("#")) continue;
			String tokens[] = line.split("\t");
			if(tokens[2].equals("black"))
				hset.add(Integer.parseInt(tokens[0]));
		}
		dis.close();
	}


	

	public static void main(String[] args) throws IOException
	{
		String facelabels="/Users/girish/Desktop/GSOC/facetracer/facelabels.txt";
		readLabels(facelabels);

		String facestats="/Users/girish/Desktop/GSOC/facetracer/facestats.txt";
		String filtered="/Users/girish/Desktop/GSOC/facetracer/facestats_black.txt";
		filterData(facestats,filtered);
		
		System.out.println("Number of filtered faces were: " +hset.size());
		
		
	}




}

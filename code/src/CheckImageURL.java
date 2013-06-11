import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CheckImageURL 
{
	
	public static HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	
	public static boolean urlExists(String url) 
	{
		boolean exists=false;
		try 
		{
			URL u = new URL (url);
			HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
			huc.setRequestMethod ("HEAD"); 
			huc.connect () ; 
			int code = huc.getResponseCode() ;
			
			if(hm.containsKey(code))
				hm.put(code,hm.get(code)+1);
			else if(!hm.containsKey(code))
				hm.put(code,1);
			
			if( code/100==2 || code/100==3) 
			{
				exists=true;
				System.out.println("Existent: "+url+" "+code);
			}
			else
			{
				exists=false;
				System.out.println("Non-Existent: "+url+" "+code);
			}
		}
		catch ( Exception e)
		{
			System.out.println("Wrong URL: "+url);
			exists = false;
		}
		return exists;
	}
	
	public static void main(String[] args) throws IOException 
	{		
		String outputFile = "/Users/girish/Desktop/GSOC/faceindex_filtered.txt";
		String inputFile = "/Users/girish/Desktop/GSOC/facetracer/faceindex.txt";

		FileWriter fw = new FileWriter(outputFile);		
		
		FileInputStream fis = new FileInputStream(inputFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);

		int valid=0,total=0;
		while (dis.available() != 0) {
			String line = dis.readLine().trim();
			String tokens[] = line.split("\t");
			
			if(tokens[0].startsWith("#")) continue;
			if(tokens.length!=3)
			{
				System.out.println("ERROR in input!");
				continue;
			}
			total++;
			
			System.out.println(total);
			if(urlExists(tokens[1])) 
			{
				fw.write(line+"\n");
				valid++;
			}
		}
		
		System.out.println("Number of total URLs in file: "+total);
		System.out.println("Number of valid URLs in file: "+valid);
		for(int key:hm.keySet())
			System.out.println("HTTP code: "+key+" Count:"+hm.get(key));		
		fw.close();

	}

}

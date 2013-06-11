import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class ParseFT
{
	public static ArrayList<Float> outerCDList = new ArrayList<Float>();
	public static ArrayList<Float> innerCDList = new ArrayList<Float>();
	public static ArrayList<Float> pfissure_rList = new ArrayList<Float>();
	public static ArrayList<Float> pfissure_lList = new ArrayList<Float>();

	private static void readFile(String facestats) throws IOException
	{
		FileInputStream fis = new FileInputStream(facestats);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);

		while (dis.available() != 0) 
		{
			String line = dis.readLine().trim();
			if(line.startsWith("#")) continue;
			String tokens[] = line.split("\t");
			int width = Integer.parseInt(tokens[1]);
			int left_eye_left_x = Integer.parseInt(tokens[8]);
			int left_eye_left_y = Integer.parseInt(tokens[9]);
			int left_eye_right_x = Integer.parseInt(tokens[10]);
			int left_eye_right_y = Integer.parseInt(tokens[11]);
			int right_eye_left_x = Integer.parseInt(tokens[12]);
			int right_eye_left_y = Integer.parseInt(tokens[13]);
			int right_eye_right_x = Integer.parseInt(tokens[14]);
			int right_eye_right_y = Integer.parseInt(tokens[15]);
					
			float outerCD = Dist(left_eye_left_x,left_eye_left_y,right_eye_right_x,right_eye_right_y);
			float innerCD = Dist(left_eye_right_x,left_eye_right_y,right_eye_left_x,right_eye_left_y);
			float pfissure_l = Dist(left_eye_left_x,left_eye_left_y,left_eye_right_x,left_eye_right_y); 		
			float pfissure_r = Dist(right_eye_left_x,right_eye_left_y,right_eye_right_x,right_eye_right_y); 		
			
			//width=1;
			
			outerCDList.add(outerCD/width);
			innerCDList.add(innerCD/width);
			pfissure_lList.add(pfissure_l/width);
			pfissure_rList.add(pfissure_r/width);
			
			//System.out.println(outerCD/width+"\t"+innerCD/width+"\t"+pfissure_l/width+"\t"+pfissure_r/width);
		}

	}



	private static float Dist(int p1x, int p1y,int p2x, int p2y)
	{
		return (float) Math.sqrt((p1x-p2x)*(p1x-p2x) + (p1y-p2y)*(p1y-p2y));
	}

	private static void getMeanSD(ArrayList<Float> vals)
	{
		float mean=0;
		float sdev=0;
		
		for (float val: vals)
		{
			mean=mean+val;
		}
		mean=mean/vals.size();
		
		float tmp=0;
		for (float val: vals)
		{
			tmp=tmp+(val-mean)*(val-mean);
		}
		sdev=(float) Math.sqrt(tmp/vals.size());
		
		System.out.println("Mean is: "+mean);
		System.out.println("SDev is: "+sdev);
		
	}



	public static void main(String[] args) throws IOException
	{
		String facestats="/Users/girish/Desktop/GSOC/facetracer/facestats_black.txt";
		readFile(facestats);
		System.out.println("Stats for outerCD");
		getMeanSD(outerCDList);
		System.out.println("Stats for innerCD");
		getMeanSD(innerCDList);
		System.out.println("Stats for pfissure right");
		getMeanSD(pfissure_rList);
		System.out.println("Stats for pfissure left");
		getMeanSD(pfissure_lList);
	}





}

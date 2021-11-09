import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
	
	private static final Random random = new Random();
	
	public static double calculateEuclideanDistance(double[] v1, double[] v2) {
	        return Math.sqrt(Math.pow(v1[0]-v2[0],2)+Math.pow(v1[1]-v2[1],2));
	}
	
	public static List<double[]> randomCentroids(List<double[]> records) {
		double maxX = Double.MIN_VALUE;
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;	    
		List<double[]> centroids = new ArrayList<double[]>();
		for (double[] record : records) 
	    {	
	    	maxX = record[0] > maxX ? record[0] : maxX;
	    	minX = record[0] < minX ? record[0] : minX;
	    	maxY = record[1] > maxY ? record[1] : maxY;
	    	minY = record[1] < minY ? record[1] : minY;
	    }
	    for (int i = 0; i < 5; i++) {
	        double[] coordinates = new double[2];
	        coordinates[0] = random.nextDouble() * (maxX - minX) + minX;
	        coordinates[1] = random.nextDouble() * (maxY - minY) + minY;
			centroids.add(coordinates);
	    }
	    return centroids;
	}
	
	public static double[] nearestCentroid(double[] record, List<double[]> centroids) {
	    double minimumDistance = Double.MAX_VALUE;
	    double[] nearest = null;

	    for (double[] centroid : centroids) {
	        double currentDistance = calculateEuclideanDistance(record, centroid);
	        if (currentDistance < minimumDistance) {
	            minimumDistance = currentDistance;
	            nearest = centroid;
	        }
	    }
	    return nearest;
	}
	
	
	public static Map<double[], List<double[]>> createClusters(List<double[]> records, List<double[]> centroids){
		Map<double[], List<double[]>> clusters = new HashMap<double[], List<double[]>>();
		for(double[] centroid : centroids)
		{
			clusters.put(centroid,new ArrayList<double[]>());
		}
		for(double[] record : records)
		{
			double[] nearest= nearestCentroid(record, centroids);
			List<double[]> list = clusters.get(nearest);
			list.add(record);
			clusters.put(nearest, list);
		}
		return clusters;
	}

	public static double[] average(List<double[]> records) {
		double[] avg = new double[2];
		for(double[] record:  records)
		{
			avg[0] += record[0];
			avg[1] += record[1];
		}
		avg[0] = avg[0]/records.size();
		avg[1] = avg[1]/records.size();
		return avg;
	}
	
	public static List<double[]> relocateCentroids( Map<double[], List<double[]>> clusters){
		List<double[]> centroids = new ArrayList<double[]>();		
		for (Map.Entry mapentry : clusters.entrySet()) {
			centroids.add(average((List<double[]>) mapentry.getValue()));
	    }
		return centroids;
	}
	
	
	public static List<double[]> readFile(String fileLink){
		List<double[]> records = new ArrayList<double[]>();
		File file = new File(fileLink);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			try {
				while ((st = br.readLine()) != null)
				{
					String[] parts = st.split(" ");
					double[]  record = {Double.parseDouble(parts[0]), Double.parseDouble(parts[1])};
					records.add(record);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return records;
	}
}


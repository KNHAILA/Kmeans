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
	
	private static List<double[]> randomCentroids(List<int[]> records, int k) {
		double maxX = 0;
		double minX = 0;
		double minY = 0;
		double maxY = 0;	    
		List<double[]> centroids = new ArrayList<double[]>();
		for (int[] record : records) 
	    {	
	    	maxX = record[0] > maxX ? record[0] : maxX;
	    	minX = record[0] < minX ? record[0] : minX;
	    	maxX = record[1] > maxX ? record[0] : maxX;
	    	minX = record[1] < minX ? record[0] : minX;
	    }

	    for (int i = 0; i < k; i++) {
	        double[] coordinates = new double[2];
	        coordinates[0] = random.nextDouble() * (maxX - minX) + minX;
	        coordinates[1] = random.nextDouble() * (maxY - minY) + minY;
			centroids.add(coordinates);
	    }
	    return centroids;
	}
	
	private static double[] nearestCentroid(double[] record, List<double[]> centroids) {
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
	
	
	private static Map<double[], List<double[]>> createClusters(List<double[]> records, List<double[]> centroids){
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

	private static double[] average(List<double[]> records) {
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
}

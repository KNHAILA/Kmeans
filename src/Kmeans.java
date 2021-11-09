import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kmeans {

	
	public static Map<double[], List<double[]>> fit(List<double[]> records, int maxIterations) {
		List<double[]> centroids = Utils.randomCentroids(records);
		Map<double[], List<double[]>> clusters = new HashMap<>();
		Map<double[], List<double[]>> lastState = new HashMap<>();
		
		for (int i = 0; i < maxIterations; i++) {
		//	System.out.print("i"+i);
			boolean isLastIteration = i == maxIterations - 1;
			for (double[] record : records) {
				double[] centroid = Utils.nearestCentroid(record, centroids);
				List<double[]> list = clusters.get(centroid);
				if(list == null)
					list = new ArrayList<double[]>();
				list.add(record);
				clusters.put(centroid, list);
		}
		boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
		lastState = clusters;
		if (shouldTerminate) {
			break; 
		}
		centroids = Utils.relocateCentroids(clusters);
		clusters = new HashMap<>();
		}
		return lastState;
	}
	
	
	public static double score(Map<double[], List<double[]>> clusters)
	{
		double score = 0.0;
		for (Map.Entry mapentry : clusters.entrySet()) {
			List<double[]> records = (List<double[]>)mapentry.getValue();
			for(double[] record : records) {
				score += Utils.calculateEuclideanDistance((double[])mapentry.getKey(), record);
			}
	    }
		return score;
	}
	
	
	public static double getScore(String fileName) {
		return score(fit(Utils.readFile(fileName),100));
	}
	
	public Kmeans() {
	}

}

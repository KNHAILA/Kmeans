import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kmeans {

	
	public static Map<double[], List<double[]>> fit(List<double[]> records, int maxIterations) {
		List<double[]> centroids = Utils.randomCentroids(records);
		Map<double[], List<double[]>> clusters = new HashMap<>();
		Map<double[], List<double[]>> lastState = new HashMap<>();
		
		for (int i = 0; i < maxIterations; i++) {
			boolean isLastIteration = i == maxIterations - 1;
			for (double[] record : records) {
				double[] centroid = Utils.nearestCentroid(record, centroids);
				List<double[]> list = clusters.get(centroid);
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
	
	
	public double score()
	{
		return 0;
	}
	public Kmeans() {
		// TODO Auto-generated constructor stub
	}

}

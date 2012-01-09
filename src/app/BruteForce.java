package app;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {

	private int maximumWeight;
	private int numberOfObjects;
	private int[] itemsGain;
	private int[] itemsWeights;

	private String result = "";

	public BruteForce(int maximumWeight, int numberOfObjects, int[] itemsGain,
			int[] itemsWeights) {
		this.maximumWeight = maximumWeight;
		this.numberOfObjects = numberOfObjects;
		this.itemsGain = itemsGain;
		this.itemsWeights = itemsWeights;
	}
	
	public void fillSack() {
		this.result = "\n\nBrute Force \n";
		int[] gains = this.adjustItemsArray(this.itemsGain);
		int[] weights = this.adjustItemsArray(this.itemsWeights);

		List<Integer> arrayIndexesList = new ArrayList<Integer>();
		for (int i = 1; i < gains.length; i++) {
			arrayIndexesList.add(i);
		}

		List<List<Integer>> indexesList = PowerSet.solve(arrayIndexesList);
		List<Integer> reachedGains = new ArrayList<Integer>();
		List<Integer> reachedWeights = new ArrayList<Integer>();
		List<Boolean> take = new ArrayList<Boolean>();

		for (List<Integer> indexes : indexesList) {
			int totalGain = 0;
			int totalWeight = 0;
			for (Integer index : indexes) {
				totalGain += gains[index];
				totalWeight += weights[index];
			}
			reachedGains.add(totalGain);
			reachedWeights.add(totalWeight);
			take.add(totalWeight <= this.maximumWeight);
		}

		this.result += "Objects Ids \tTotal Gain \tTotal Weight \tTake \n";
		for (int i = 0; i < indexesList.size(); i++) {
			this.result += indexesList.get(i) + "\t"
					+ reachedGains.get(i) + "\t" + reachedWeights.get(i) + "\t"
					+ take.get(i) + "\n";
		}

		int indexMax = -1;
		int gainMax = 0;
		for (int i = 0; i < reachedGains.size(); i++) {
			int reachedGain = reachedGains.get(i);
			if (reachedGain > gainMax
					&& reachedWeights.get(i) <= this.maximumWeight) {
				gainMax = reachedGains.get(i);
				indexMax = i;
			}
		}

		this.result += "\n\n BestSolution\n";
		this.result += indexesList.get(indexMax) + "\t"
				+ reachedGains.get(indexMax) + "\t"
				+ reachedWeights.get(indexMax) + "\t" + take.get(indexMax)
				+ "\n";
		this.result += "Complexity: "
				+ (int) Math.pow(2, this.numberOfObjects) + "\n";
	}
	
	private int[] adjustItemsArray(int[] items) {
		int[] result = new int[items.length + 1];
		result[0] = 0;
		for (int i = 1; i < result.length; i++) {
			result[i] = items[i - 1];
		}
		return result;
	}

	public String getResult() {
		return result;
	}
}

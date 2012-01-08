package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DPKP {

	private int maximumWeight;
	private int numberOfObjects;
	private int[] itemsGain;
	private int[] itemsWeights;
	
	private String result = "";
	private String resultBruteForce = "";

	public DPKP(int maximumWeight, int numberOfObjects, int[] objectsGain,
			int[] objectsWeights) {
		this.maximumWeight = maximumWeight;
		this.numberOfObjects = numberOfObjects;
		this.itemsGain = objectsGain;
		this.itemsWeights = objectsWeights;
	}
	
	public void fillSack() {
		this.result = "DP\n";
		int[] gains = this.adjustItemsArray(this.itemsGain);
		int[] weights = this.adjustItemsArray(this.itemsWeights);
		
		this.result += "Knapsack Capacity: " + this.maximumWeight + "\n";
		this.result += "Number of Items: " + this.numberOfObjects + "\n";
		
		int[][] opt = new int[this.numberOfObjects+1][this.maximumWeight+1];
		boolean[][] sol = new boolean[this.numberOfObjects+1][this.maximumWeight+1];
		
		for (int n = 1; n <= this.numberOfObjects; n++) {
			for (int w = 1; w <= this.maximumWeight; w++) {
				// don't take item n
				int option1 = opt[n-1][w];
				
				// take item n
				int option2 = Integer.MIN_VALUE;
				if (weights[n] <= w) {
					option2 = gains[n] + opt[n-1][w-weights[n]];
				}
				
				//select better of two options
				opt[n][w] = Math.max(option1, option2);
				sol[n][w] = (option2 > option1);
			}
		}
		
		// determine which items to take
		boolean[] take = new boolean[this.numberOfObjects+1];
		for (int n = this.numberOfObjects, w = this.maximumWeight; n > 0; n--) {
			if (sol[n][w]) {
				take[n] = true;
				w = w - weights[n];
			} else {
				take[n] = false;
			}
		}
		
		// output results
//		System.out.println("item \tprofit \tweight \ttake");
//		for (int n = 1; n <= this.numberOfObjects; n++) {
//			System.out.println(n + "\t" + gains[n] + "\t" + weights[n] + "\t" + take[n]);
//		}
		
		//set result variable
		this.result += "item \tprofit \tweight \ttake\n";
		for (int n = 1; n <= this.numberOfObjects; n++) {
			this.result += n + "\t" + gains[n] + "\t" + weights[n] + "\t" + take[n] + "\n";
		}
		
		this.result += "\n";
		int totalGain = 0;
		for (int n = 1; n <= this.numberOfObjects; n++) {
			if (take[n]) {
				totalGain += gains[n];
			}
		}
		this.result += "Total Gain: " + totalGain + "\n";
		this.result += "Complexity: " + this.numberOfObjects * this.getMaxWeightFromInput() + "\n";
	}
	
	public void fillSackBruteForce() {
		this.resultBruteForce = "\n\nBrute Force \n";
		int[] gains = this.adjustItemsArray(this.itemsGain);
		int[] weights = this.adjustItemsArray(this.itemsWeights);
		
		Set<Integer> arrayIndexesSet = new HashSet<Integer>();
		for (int i = 1; i < gains.length; i++) {
			arrayIndexesSet.add(i);
		}
		
		List<List<Integer>> indexesList = this.powerSet(arrayIndexesSet);
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
		
		this.resultBruteForce += "Objects Ids \tTotal Gain \tTotal Weight \tTake \n";
		for (int i = 0; i < indexesList.size(); i++) {
			this.resultBruteForce += indexesList.get(i) + "\t" + reachedGains.get(i) + "\t" + reachedWeights.get(i) + "\t" + take.get(i) + "\n";
		}
		
		int indexMax = -1;
		int gainMax = 0;
		for (int i = 0; i < reachedGains.size(); i++) {
			int reachedGain = reachedGains.get(i);
			if (reachedGain > gainMax && reachedWeights.get(i) <= this.maximumWeight) {
				gainMax = reachedGains.get(i);
				indexMax = i;
			}
		}
		
		this.resultBruteForce += "\n\n BestSolution\n";
		this.resultBruteForce += indexesList.get(indexMax) + "\t" + reachedGains.get(indexMax) + "\t" + reachedWeights.get(indexMax) + "\t" + take.get(indexMax) + "\n";
		this.resultBruteForce += "Complexity: " + (int)Math.pow(2, this.numberOfObjects) + "\n";
	}
	
	private List<List<Integer>> powerSet(Set<Integer> originalSet) {
		List<List<Integer>> sets = new ArrayList<List<Integer>>();
		if (originalSet.isEmpty()) {
			sets.add(new ArrayList<Integer>());
			return sets;
		}
		List<Integer> list = new ArrayList<Integer>(originalSet);
		Integer head = list.get(0);
		Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
		for (List<Integer> set : powerSet(rest)) {
			List<Integer> newSet = new ArrayList<Integer>();
			newSet.add(head);
			newSet.addAll(set);
			sets.add(newSet);
			sets.add(set);
		}
		return sets;
	}
	
	private int getMaxWeightFromInput() {
		if (this.itemsWeights.length > 0) {
			int maxWeight = this.itemsWeights[0];
			for (int weight : this.itemsWeights) {
				if (weight > maxWeight) {
					maxWeight = weight;
				}
			}
			return maxWeight;
		}
		return 0;
	}
	
	private int[] adjustItemsArray(int[] items) {
		int[] result = new int[items.length+1];
		result[0] = 0;
		for (int i = 1; i < result.length; i++) {
			result[i] = items[i-1];
		}
		return result;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public String getResultBruteForce() {
		return this.resultBruteForce;
	}
}

package app;


public class DPKP {

	private int maximumWeight;
	private int numberOfObjects;
	private int[] itemsGain;
	private int[] itemsWeights;

	private String result = "";

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

		int[][] opt = new int[this.numberOfObjects + 1][this.maximumWeight + 1];
		boolean[][] sol = new boolean[this.numberOfObjects + 1][this.maximumWeight + 1];

		for (int n = 1; n <= this.numberOfObjects; n++) {
			for (int w = 1; w <= this.maximumWeight; w++) {
				// don't take item n
				int option1 = opt[n - 1][w];

				// take item n
				int option2 = Integer.MIN_VALUE;
				if (weights[n] <= w) {
					option2 = gains[n] + opt[n - 1][w - weights[n]];
				}

				// select better of two options
				opt[n][w] = Math.max(option1, option2);
				sol[n][w] = (option2 > option1);
			}
		}
		System.out.println("Opt:");
		this.logArrayInt(opt);
		System.out.println("Sol");
		this.logArrayBoolean(sol);

		// determine which items to take
		boolean[] take = new boolean[this.numberOfObjects + 1];
		for (int n = this.numberOfObjects, w = this.maximumWeight; n > 0; n--) {
			if (sol[n][w]) {
				take[n] = true;
				w = w - weights[n];
			} else {
				take[n] = false;
			}
		}

		// output results
		// System.out.println("item \tprofit \tweight \ttake");
		// for (int n = 1; n <= this.numberOfObjects; n++) {
		// System.out.println(n + "\t" + gains[n] + "\t" + weights[n] + "\t" +
		// take[n]);
		// }

		// set result variable
		this.result += "item \tprofit \tweight \ttake\n";
		for (int n = 1; n <= this.numberOfObjects; n++) {
			this.result += n + "\t" + gains[n] + "\t" + weights[n] + "\t"
					+ take[n] + "\n";
		}

		this.result += "\n";
		int totalGain = 0;
		for (int n = 1; n <= this.numberOfObjects; n++) {
			if (take[n]) {
				totalGain += gains[n];
			}
		}
		this.result += "Total Gain: " + totalGain + "\n";
		this.result += "Complexity: " + this.numberOfObjects
				* this.getMaxWeightFromInput() + "\n";
	}

	private void logArrayInt(int[][] arr) {
		String result = "";
		for (int[] row : arr) {
			for (int value : row) {
				result += value + " ";
			}
			result += "\n";
		}
		System.out.println(result);
	}
	
	private void logArrayBoolean(boolean[][] arr) {
		String result = "";
		for (boolean[] row : arr) {
			for (boolean value : row) {
				result += value ? 1 : 0;
				result += " ";
			}
			result += "\n";
		}
		System.out.println(result);
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
		int[] result = new int[items.length + 1];
		result[0] = 0;
		for (int i = 1; i < result.length; i++) {
			result[i] = items[i - 1];
		}
		return result;
	}

	public String getResult() {
		return this.result;
	}
}

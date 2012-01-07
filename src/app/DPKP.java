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
		int[] gains = this.adjustItemsArray(this.itemsGain);
		int[] weights = this.adjustItemsArray(this.itemsWeights);
		
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
		System.out.println("item \tprofit \tweight \ttake");
		for (int n = 1; n <= this.numberOfObjects; n++) {
			System.out.println(n + "\t" + gains[n] + "\t" + weights[n] + "\t" + take[n]);
		}
		
		//set result variable
		this.result = "item \tprofit \tweight \ttake\n";
		for (int n = 1; n <= this.numberOfObjects; n++) {
			this.result += n + "\t" + gains[n] + "\t" + weights[n] + "\t" + take[n] + "\n";
		}
		
		this.result += "\n\n";
		int totalGain = 0;
		for (int n = 1; n <= this.numberOfObjects; n++) {
			if (take[n]) {
				totalGain += gains[n];
			}
		}
		this.result += "Total Gain: " + totalGain;
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
}

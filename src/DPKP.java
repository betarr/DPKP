
public class DPKP {
	private static final int MAX_WEIGHT = 100;
	
	private int maximumWeight;
	private int numberOfObjects;
	private int[] objectsGain;
	private int[] objectsWeights;

	public DPKP(int maximumWeight, int numberOfObjects, int[] objectsGain,
			int[] objectsWeights) {
		this.maximumWeight = maximumWeight;
		this.numberOfObjects = numberOfObjects;
		this.objectsGain = objectsGain;
		this.objectsWeights = objectsWeights;
	}
	
	public void fillSack() {
		int[] a = new int[DPKP.MAX_WEIGHT];
		int[] lastAdded = new int[DPKP.MAX_WEIGHT];
		
		for (int i = 0; i <= this.maximumWeight; i++) {
			a[i] = 0;
			lastAdded[i] = -1;
		}
		
		//a[0] = 0;
		for (int i = 1; i <= this.maximumWeight; i++) {
			for (int j = 0; j < this.numberOfObjects; j++) {
				if ((this.objectsGain[j] <= i) && (a[i] < a[i - this.objectsGain[j]] + this.objectsWeights[j])) {
					a[i] = a[i - this.objectsGain[j]] + this.objectsGain[j];
					lastAdded[i] = j;
				}
			}
		}
		
		for (int i = 0; i <= this.maximumWeight; i++) {
			if (lastAdded[i] != -1) {
				System.out.format("Weight %d; Benefit: %d; To reach this weight I added object %d (%d$ %dKg) to weight %d.\n",
						i,
						a[i],
						lastAdded[i]+1,
						this.objectsWeights[lastAdded[i]],
						this.objectsGain[lastAdded[i]],
						i-this.objectsGain[lastAdded[i]]);
			} else {
				System.out.format("Weight %d; Benefit: 0; Can't reach this exact weight.\n",
						i);
			}
		}
		
		System.out.println("----------------");
		
		int aux = this.maximumWeight;
		while ((aux > 0) && (lastAdded[aux] != -1)) {
			System.out.format("Added object %d (%d$ %dKg). Space left: %d\n",
					lastAdded[aux]+1,
					this.objectsGain[lastAdded[aux]],
					this.objectsWeights[lastAdded[aux]],
					aux - this.objectsGain[lastAdded[aux]]);
			aux -= this.objectsGain[lastAdded[aux]];
		}
		
		System.out.format("Total value added: %d$\n", a[this.maximumWeight]);
	}
	
	
}

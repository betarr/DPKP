package app;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PowerSet {
	
	
	private static class Aggregator {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		public List<List<Integer>> getResult() {
			return result;
		}
		
		public void addSublist(List<Integer> sublist) {
			result.add(sublist);
		}
		
	}
	
	public static List<List<Integer>> solve(List<Integer> inputSet) {
		boolean[] setIsIncluded = new boolean[inputSet.size()];
		Arrays.fill(setIsIncluded, false);

		Aggregator aggregator = new Aggregator();
		tryAllCombinations(0, setIsIncluded, inputSet, aggregator);
		
		return aggregator.getResult();
	}

	private static void tryAllCombinations(int depth, boolean[] setIsIncluded, List<Integer> inputSet, Aggregator aggregator) {
		if (depth == inputSet.size()) {
			List<Integer> sublist = new ArrayList<Integer>();
			for (int i = 0; i < depth; i++) {
				if (setIsIncluded[i]) {
					sublist.add(inputSet.get(i));
				}
			}
			aggregator.addSublist(sublist);
		} else {
			setIsIncluded[depth] = true;
			tryAllCombinations(depth + 1, setIsIncluded, inputSet, aggregator);
			setIsIncluded[depth] = false;
			tryAllCombinations(depth + 1, setIsIncluded, inputSet, aggregator);
		}
	}
}

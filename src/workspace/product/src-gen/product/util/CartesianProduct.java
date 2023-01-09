package product.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartesianProduct {
	// https://codereview.stackexchange.com/a/67922
	public static <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
	    List<List<T>> combinations = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList())));
	    for (List<T> list : lists) {
	        List<List<T>> extraColumnCombinations = new ArrayList<>();
	        for (List<T> combination : combinations)
	            for (T element : list) {
	                List<T> newCombination = new ArrayList<>(combination);
	                newCombination.add(element);
	                extraColumnCombinations.add(newCombination);
	            }
	        combinations = extraColumnCombinations;
	    }
	    return combinations;
	}
	
	public static <T> List<List<T>> selfCartesianProduct(List<T> list, int n) {
		List<List<T>> replicatedNTimes = new ArrayList<>();
		for (int i = 0; i < n; ++i)
			replicatedNTimes.add(list);
		return cartesianProduct(replicatedNTimes);
	}
}

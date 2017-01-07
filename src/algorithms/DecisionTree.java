package algorithms;

import java.util.ArrayList;
import java.util.Random;

public class DecisionTree {
	
	private static Random randomGen = new Random();
	
	private ArrayList<DecisionTree> subTrees;
	private int value;
	private DecisionTree parent;

	public DecisionTree(int initialValue){
		subTrees = new ArrayList<DecisionTree>();
		value = initialValue;
	}

	/**
	 * @return the subTrees
	 */
	public ArrayList<DecisionTree> getSubTrees() {
		return subTrees;
	}
	

	/**
	 * Returns a random child. Does not check if the array is empty. Exception will be thrown if no child
	 * @return
	 */
	public int randomChildIndex(){
		int sum = 0;
		for (DecisionTree decisionTree : subTrees) {
			sum += decisionTree.getValue();
		}
		
		
		
		int indexNum = randomGen.nextInt(sum);
		
		
		sum = 0;
		
		int i = 0;
		while(sum < indexNum){
			sum += subTrees.get(i).getValue();
			i++;
		}
		return i;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the parent
	 */
	public DecisionTree getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(DecisionTree parent) {
		this.parent = parent;
	}
	
	

}

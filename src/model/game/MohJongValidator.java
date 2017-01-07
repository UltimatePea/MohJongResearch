package model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import model.MohJong;
import model.MohJongContainer;

public class MohJongValidator {
	
	public static boolean validateMohJongs(MohJongContainer container){
		return validateMohJongs(container.getMohJongs());
	}
	
	public static boolean validateMohJongs(ArrayList<MohJong> mohJongs){
		return isMohJongsValid(mohJongs);
		
	}
	
	private static boolean isMohJongsValid(ArrayList<MohJong> mohJongs){
		if (mohJongs.size() == 2) {
			MohJong m0 = mohJongs.get(0);
			MohJong m1 = mohJongs.get(1);
			return m0.getUniqueIdentifier().equals(m1.getUniqueIdentifier());
		}
		//create a copy first
		@SuppressWarnings("unchecked")
		ArrayList<MohJong> mohJongsCopy = (ArrayList<MohJong>) mohJongs.clone();
		//NO reference to mohJongs beyond this point
		//算法参考 https://www.zhihu.com/question/47908832
		
		//先洗牌
		sort(mohJongsCopy);
		
		//判断是否7小对
		boolean b = true;
		for (int i = 0; i < mohJongsCopy.size()/2; i++) {
			//if two consecutive copies are not the same, break
			if(! mohJongsCopy.get(2 * i) .equalsToMohJong(mohJongsCopy.get(2 * i + 1))){
				b = false;
			}
		}
		if(b){ return true; }
		
		//找队长（对子）
		for(int i = 0; i < mohJongsCopy.size()-1; i++){
			
			if(mohJongsCopy.get(i).equalsToMohJong(mohJongsCopy.get(i + 1))){
				//remove the copy
				MohJong obj1 = mohJongsCopy.remove(i);
				MohJong obj2 = mohJongsCopy.remove(i);
				
				if(twelveValidation(mohJongsCopy)){
					return true;
				}
				
				mohJongsCopy.add(i, obj2);
				mohJongsCopy.add(i, obj1);
			}
		}

		
		//returns false if validation fails
		
		return false;
	}
	
	private static boolean twelveValidation(ArrayList<MohJong> mohJongs){
		//grouping
		ArrayList<ArrayList<MohJong>> groups = new ArrayList<ArrayList<MohJong>>();
		
		for (Iterator<MohJong> iterator = mohJongs.iterator(); iterator.hasNext();) {
			MohJong mohJong = (MohJong) iterator.next();
			
			//if the groups are empty or current item does not belong to the last group
			if(groups.isEmpty() || !groups.get(groups.size() - 1).get(0).getCategory().equals(mohJong.getCategory())){

				//create a new group and put the current object in
				ArrayList<MohJong> newGroup = new ArrayList<MohJong>();
				newGroup.add(mohJong);
				groups.add(newGroup);
			} else {
				//else add the current object to the last group
				groups.get(groups.size() - 1).add(mohJong);
			}
			
		}
		
		//if size per group is not a multiple of three return zero
		for (Iterator<ArrayList<MohJong>> iterator = groups.iterator(); iterator.hasNext();) {
			ArrayList<MohJong> arrayList = (ArrayList<MohJong>) iterator.next();
			if(arrayList.size()%3 != 0){
				return false;
			}
		}
		
		for (Iterator<ArrayList<MohJong>> iterator = groups.iterator(); iterator.hasNext();) {
			ArrayList<MohJong> arrayList = (ArrayList<MohJong>) iterator.next();
			
			//if judgement fails, return false immediately
			if(!recursiveJudgementOn3Multiples(new ArrayList<MohJong>(), arrayList)){
				return false;
			}
		}
		
		//passed all tests, we should be fine
		return true;
		
	}
	
	/**
	 * Judgement on 3 Multiples, recursive, only checking 1~9
	 * @param sofar elements that have been added for evaluation, entry method should pass empty array
	 * @param pending elements pending processing
	 * @return true if the 3-Multiples is a valid win combination
	 */
	@SuppressWarnings("unchecked")
	private static boolean recursiveJudgementOn3Multiples(ArrayList<MohJong> sofar, ArrayList<MohJong> pending){
		//if sofar size is equal to three
		if(sofar.size() == 3){
			
			//if so check if the three forms a shun zi or ke zi
			if(isShunZi(sofar) || isKeZi(sofar)){
				
				//if so clear so far
				sofar.clear();
				
				//if pending is empty, the current side is clear, this is a match
				if(pending.isEmpty()){
					return true;
				} else {
					//else continue search
					return recursiveJudgementOn3Multiples(sofar, pending);
				}
			} else {
				//if the three does not form a shunzi or kezi
				return false;
			}
		} else {
			//if so far size is less than three, iterate over pending to add element to sofar
			for (int i = 0; i < pending.size(); i++) {
				MohJong m = pending.get(i);
				pending.remove(i);
				sofar.add(m);
				//clone is very important here
				if(recursiveJudgementOn3Multiples((ArrayList<MohJong>) sofar.clone(), pending)){
					return true;
				} else {
					sofar.remove(sofar.size() -1 );
					pending.add(i, m);
				}
			}
			return false;
		}
	}
	
	/**
	 * 判断3张牌是否为顺子, 如果卡牌的category为s，该方法一定返回否
	 * @param arr
	 * @return
	 */
	private static boolean isShunZi(ArrayList<MohJong> arr){
		return !arr.get(0).getCategory().equals("s") &&
				arr.get(2).getRank() - arr.get(1).getRank() == 1 &&
				arr.get(1).getRank() - arr.get(0).getRank() == 1;
	}
	
	/**
	 * 判断3张牌是否为刻子（3张一样）
	 * @param arr
	 * @return
	 */
	private static boolean isKeZi(ArrayList<MohJong> arr){
		return arr.get(2).getRank() == arr.get(1).getRank() &&
				arr.get(1).getRank() == arr.get(0).getRank();
	}

	private static void sort(ArrayList<MohJong> arr){
		Collections.sort(arr, new Comparator<MohJong>(){
			@Override
			public int compare(MohJong m1, MohJong m2){
				return m1.getUniqueIdentifier().compareTo(m2.getUniqueIdentifier());
			}
		});
	}
		
	
	

}

package model;

/**
 * Main class for a single moh-jong tile.
 * Unique id: [category][rank]
 * [category]
 * w for wang, b for bing or tong, t for tiao or suozi, s for others
 * [rank]
 * 1 ~ 9 for w, b, t
 * for s: 1 ~ 5 represents East South West North Middle respectively
 * s6 facai
 * s7 baiban
 * s8 Spring Summer Autumn Winter
 * s9 plum blossom, orchid, banboo, chrysanthemum (Mei, Lan, Zhu, Ju)
 * 
 * @author chenzb
 *
 */
public class MohJong {
	
	/**
	 * The unique id, e.g. w3, b4, t5, s6
	 */
	private String uniqueIdentifier;
	/**
	 * the rank, 1 ~ 9
	 */
	private int rank;
	/**
	 * the category, w, b, t, s
	 */
	private String category;

	/**
	 * gets the unique identifier, e.g. s2, w5, 
	 * @return a string representing the unique identifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * sets the unique identifier, also updates the rank and category
	 * @param uniqueIdentifier
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
		setRankAndCategoryForUniqueIdentifier(uniqueIdentifier);
	}

	/**
	 * gets the rank, 1 ~ 9
	 * @return
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * sets the rank 1 ~ 9, also updates unique identifier
	 * @param rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
		setUniqueIdentifierForRankAndCategory(rank, category);
	}

	/**
	 * gets the category, w(an), b(ing), t(iao), s(pecial)
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * sets the category, w, b, t, s
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
		setUniqueIdentifierForRankAndCategory(rank, category);
	}
	
	public boolean equalsToMohJong(MohJong aMohJong){
		return getUniqueIdentifier() .equals(aMohJong.getUniqueIdentifier());
	}
	
	/**
	 * creates a MohJong tile object with specified parameters
	 * @param uniqueIdentifier
	 * @param rank
	 * @param category
	 */
	public MohJong(String uniqueIdentifier, int rank, String category) {
		super();
		this.uniqueIdentifier = uniqueIdentifier;
		this.rank = rank;
		this.category = category;
	}

	/**
	 * creates using unique identifier only
	 * @param uniqueIdentifier
	 */
	public MohJong(String uniqueIdentifier) {
		super();
		this.uniqueIdentifier = uniqueIdentifier;
		setRankAndCategoryForUniqueIdentifier(uniqueIdentifier);
	}

	/**
	 * creates using rank and category
	 * @param rank
	 * @param category
	 */
	public MohJong(int rank, String category) {
		super();
		this.rank = rank;
		this.category = category;
		setUniqueIdentifierForRankAndCategory(rank, category);
	}
	
	/**
	 * Utility Method for setting the rank and category according to given unique identifier
	 * @param uniqueIdentifier
	 */
	private void setRankAndCategoryForUniqueIdentifier(String uniqueIdentifier){
		this.rank = Integer.parseInt(uniqueIdentifier.substring(1));
		this.category = uniqueIdentifier.substring(0, 1);
	}
	
	/**
	 * Utility Method for setting the unique id for given rank and category
	 * @param rank
	 * @param category
	 */
	private void setUniqueIdentifierForRankAndCategory(int rank, String category) {
		this.uniqueIdentifier = category + rank;
	}
	
	public String toString(){
		
		return getUniqueIdentifier();
	}

}

package model.readable;

import model.MohJong;


/**
 * the class can be used to create a user readable text for specific mojong tiles
 * @author chenzb
 *
 */
public class MohJongReader {
	
	private MohJong readingMohJong;
	private MohJongReaderLanguageEnum language;

	/**
	 * gets the current language
	 * @return
	 */
	public MohJongReaderLanguageEnum getLanguage() {
		return language;
	}

	/**
	 * sets the language
	 * @param language
	 */
	public void setLanguage(MohJongReaderLanguageEnum language) {
		this.language = language;
	}
	
	/**
	 * constructs specifying MohJong Tile and language.
	 * Use <code>getReadablePresentation()</code> to get the result
	 * @param readingMohJong
	 * @param language
	 */
	public MohJongReader(MohJong readingMohJong,
			MohJongReaderLanguageEnum language) {
		super();
		this.readingMohJong = readingMohJong;
		this.language = language;
	}

	/**
	 * gets the readable string for specified language
	 * @return 
	 */
	public String getReadablePresentation() {
		String category = readingMohJong.getCategory();
		int rank = readingMohJong.getRank();
		String rankName = "", categoryName = "";
		//rank name 
		if (category.equals("s")) {
			rankName = getSpecialStringForRank(rank);
		} else {
			rankName = getRegularStringForRank(rank);
		}
		if (category.equals("w")) {
			categoryName = language == MohJongReaderLanguageEnum.Chinese?"万":"Wan";
		} else if (category.equals("b")) {
			categoryName = language == MohJongReaderLanguageEnum.Chinese?"饼":"Bing";
		} else if (category.equals("t")) {
			categoryName = language == MohJongReaderLanguageEnum.Chinese?"条":"Tiao";
		} else if (category.equals("s")) {
			
		}
		return rankName + categoryName;
	}
	
	/**
	 * gets the rank text for specials, e.g. wind directions
	 * @param rank
	 * @return
	 */
	private String getSpecialStringForRank(int rank){
		String returnString;
		switch (language) {
		case Chinese:
			String[] namesChi = new String[]{"东风", "南风", "西风", "北风", "红中", "发财", "白板", "四季", "花朵"};
			returnString = namesChi[rank-1];
			break;

		case English:
		default:
			String[] namesEng = new String[]{"East", "South", "West", "North", "Middle", "$$", "Whiteboard", "Seasons", "Flowers"};
			returnString = namesEng[rank-1];
			break;
		}
		return returnString;
	}
	/**
	 * gets the rank string 1 ~ 9
	 * @param rank
	 * @return
	 */
	private String getRegularStringForRank(int rank){
		String returnString;
		switch (language) {
		case Chinese:
			String[] names = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九"};
			returnString = names[rank-1];
			break;
		case English:
		default:
			returnString = String.valueOf(rank);
			break;
		}
		return returnString;
	}
	
	

}

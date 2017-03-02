package MultiSwap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ReplaceIndex {

	// public static final String[] ordinal = new
	// String[]{"第一","第二","第三","第四","第五","第六","第七","第八","第九","第十","第十一","第十二","第十三","第十四","第十五","第十六","第十七","第十八","第十九","第二十"};
	// public static final String[] num = new
	// String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	// public static final String[] word = new
	// String[]{"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};
	// public static final String[] capiword = new
	// String[]{"First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Ninth","Tenth","Eleventh","Twevlfth","Thirteenth","Fourteenth","Fifteenth","Sixteenth","Seventeenth","Eighteenth","Nineteenth","Twentieth"};
	// public static final String[] roman = new
	// String[]{"I","II","III","IV","Ⅴ","
	// VI","VII","VIII","IX","X","XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX"};
	// public static final String[] shortword = new String
	// []{"a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","t"};
	public static final List<String> ordinal = new ArrayList<>();
	public static final List<String> num = new ArrayList<>();
	public static final List<String> word = new ArrayList<>();
	public static final List<String> capiword = new ArrayList<>();
	public static final List<String> roman = new ArrayList<>();
	public static final List<String> shortword = new ArrayList<>();
	static String catetransref = null; // reftrans category
	static String cate = null; // word category
	static String in = null; // word index need to be replaced
	static String res = null;// word need to be replaced

	static {
		ordinal.add("第一");
		ordinal.add("第二");
		ordinal.add("第三");
		ordinal.add("第四");
		ordinal.add("第五");
		ordinal.add("第六");
		ordinal.add("第七");
		ordinal.add("第八");
		ordinal.add("第九");
		ordinal.add("第十");
		ordinal.add("第十一");
		ordinal.add("第十二");
		ordinal.add("第十三");
		ordinal.add("第十四");
		ordinal.add("第十五");
		ordinal.add("第十六");
		ordinal.add("第十七");
		ordinal.add("第十八");
		ordinal.add("第十九");
		ordinal.add("第二十");

		num.add("1");
		num.add("2");
		num.add("3");
		num.add("4");
		num.add("5");
		num.add("6");
		num.add("7");
		num.add("8");
		num.add("9");
		num.add("10");
		num.add("11");
		num.add("12");
		num.add("13");
		num.add("14");
		num.add("15");
		num.add("16");
		num.add("17");
		num.add("18");
		num.add("19");
		num.add("20");

		word.add("一");
		word.add("二");
		word.add("三");
		word.add("四");
		word.add("五");
		word.add("六");
		word.add("七");
		word.add("八");
		word.add("九");
		word.add("十");
		word.add("十一");
		word.add("十二");
		word.add("十三");
		word.add("十四");
		word.add("十五");
		word.add("十六");
		word.add("十七");
		word.add("十八");
		word.add("十九");
		word.add("二十");

		capiword.add("First");
		capiword.add("Second");
		capiword.add("Third");
		capiword.add("Fourth");
		capiword.add("Sixth");
		capiword.add("Seventh");
		capiword.add("Eighth");
		capiword.add("Ninth");
		capiword.add("Tenth");
		capiword.add("Eleventh");
		capiword.add("Twevlfth");
		capiword.add("Thirteenth");
		capiword.add("Fourteenth");
		capiword.add("Fifteenth");
		capiword.add("Sixteenth");
		capiword.add("Seventeenth");
		capiword.add("Eighteenth");
		capiword.add("Nineteenth");
		capiword.add("Twentieth");

		roman.add("I");
		roman.add("II");
		roman.add("III");
		roman.add("IV");
		roman.add("V");
		roman.add("VI");
		roman.add("VII");
		roman.add("VIII");
		roman.add("IX");
		roman.add("X");
		roman.add("XI");
		roman.add("XII");
		roman.add("XIII");
		roman.add("XIV");
		roman.add("XV");
		roman.add("XVI");
		roman.add("XVII");
		roman.add("XVIII");
		roman.add("XIX");
		roman.add("XX");

		shortword.add("a");
		shortword.add("b");
		shortword.add("c");
		shortword.add("d");
		shortword.add("e");
		shortword.add("f");
		shortword.add("g");
		shortword.add("h");
		shortword.add("i");
		shortword.add("j");
		shortword.add("k");
		shortword.add("l");
		shortword.add("m");
		shortword.add("n");
		shortword.add("o");
		shortword.add("p");
		shortword.add("r");
		shortword.add("s");
		shortword.add("t");

	}

	public static void main(String[] args) {
		String ref = "( 1.2 “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000 万元";
		String hyp = "( a “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000 万元";
		String transref = "( 1.2 )	LI Dan invested RMB 10,000,000 to Beijing Zhongjin Xinsheng Investment Center (limited partnership) ";
		// String transhyp = "I) LI Dan invested RMB 10,000,000 to Beijing
		// Zhongjin Xinsheng Investment Center (limited partnership) ";
		// replace
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		String transhyp = replace(ref, hyp, dm.hypsub.get(0), transref,"zh-ch");
		System.out.println(dm.hypsub.get(0));
		System.out.println(dm.refsub.toString());
		System.out.println(transref);
		System.out.println(transhyp);
	}

	public static String replace(String ref, String hyp, String refsub, String transref,String target) {
		target = StringUtils.left(target, 2);
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		String hypdiff = dm.hypsub.get(0);
		String refdiff = dm.refsub.get(0);
		Pattern p = Pattern.compile("\\d+\\.\\d+\\.\\d+|\\d+\\.\\d+");
		String transhyp = null;
		if (p.matcher(hypdiff).matches() || p.matcher(refdiff).matches()) {
			String[] dl = refdiff.split("\\.");
			if (dl[0].length() < 4) {
				cate = "generic";
				transhyp = transref.replaceFirst(refdiff, hypdiff);
			}
		}
		if (cate==null && target.equals("en")) {
			getCategory(transref);
		}
		if (cate==null && target.equals("zh")) {
			getZhCategory(transref);
		}
		// hypothesis index
		Integer index = getHypIndex(refsub);
		//System.out.println("category:	"+refsub);
		//System.out.println("info>>> "+index+" "+cate+" "+res);
		/*if (cate==null && res==null){
			res = NumberSwitcher.numberSwitch(refsub, transref,source,target);
		}*/
		if (index != -1) {
			if (cate.equals("num"))
				transhyp = transref.replaceFirst(res, num.get(index));
			else if (cate.equals("word"))
				transhyp = transref.replaceFirst(res, word.get(index));
			else if (cate.equals("capiword"))
				transhyp = transref.replaceFirst(res, capiword.get(index));
			else if (cate.equals("roman"))
				transhyp = transref.replaceFirst(res, roman.get(index));
			else if (cate.equals("shortword"))
				transhyp = transref.replaceFirst(res, shortword.get(index));
			else if (cate.equals("ordinal"))
				transhyp = transref.replaceFirst(res, ordinal.get(index));
			else
				System.out.println("the first token is irreplacable");
		}
		//System.out.println("transhyp>>>>>"+cate+refdiff+hypdiff+transhyp);
		return transhyp;
	}

	private static void getZhCategory(String transref) {
		// get the category of chinese token
		String token = UtilLang.pureTokenizeZh(transref).split("\\s+")[0];
		String token1 = UtilLang.pureTokenizeZh(transref).split("\\s+")[1];
		// the first token is index
		checkCategory(token);
		// the second token is index
		if (cate == null) {
			checkCategory(token1);
		}
		
	}

	public static Integer getHypIndex(String refsub) {
		// registering sub word to global variables
		int indexHyp = -1;

		if (num.contains(refsub))
			indexHyp = num.indexOf(refsub);
		else if (word.contains(refsub))
			indexHyp = word.indexOf(refsub);
		else if (capiword.contains(refsub))
			indexHyp = capiword.indexOf(refsub);
		else if (roman.contains(refsub))
			indexHyp = roman.indexOf(refsub);
		else if (shortword.contains(refsub))
			indexHyp = shortword.indexOf(refsub);
		else if (ordinal.contains(refsub))
			indexHyp = ordinal.indexOf(refsub);

		else{}
			//System.out.println("The value is beyond designed");
		return indexHyp;
	}

	private static String checkVagueContain(String token, List<String> list) {
		String res = null;
		for (String s : list) {
			Pattern p = Pattern.compile(s);
			java.util.regex.Matcher m = p.matcher(token);
			if (m.find()) {
				res = s;
			}
		}
		return res;
	}

	public static void getCategory(String transref) {
		String token = transref.trim().split("\\s+")[0];
		String token1 = transref.trim().split("\\s+")[1];
		// the first token is index
		checkCategory(token);
		// the second token is index
		if (cate == null) {
			checkCategory(token1);
		}
	}

	public static ArrayList<String> checkCategory(String token) {
		ArrayList<String> category = new ArrayList<String>();
		//System.out.println("bingo1");

		if (checkVagueContain(token, num) != null) {
			cate = "num";
			res = checkVagueContain(token, num);
		} else if (checkVagueContain(token, word) != null) {
			cate = "word";
			res = checkVagueContain(token, word);
		} else if (checkVagueContain(token, capiword) != null) {
			cate = "capiword";
			res = checkVagueContain(token, capiword);
		} else if (checkVagueContain(token, roman) != null) {
			cate = "roman";
			res = checkVagueContain(token, roman);
		} else if (checkVagueContain(token, shortword) != null) {
			cate = "shortword";
			res = checkVagueContain(token, shortword);
		} else if (checkVagueContain(token, ordinal) != null) {
			cate = "ordinal";
			res = checkVagueContain(token, ordinal);
		}
		category.add(cate);
		category.add(res);
		return category;
	}

}

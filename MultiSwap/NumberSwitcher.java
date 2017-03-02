package MultiSwap;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
/*
 * transfer forms of num+string to newnum+samestring
 */

import edu.stanford.nlp.process.PTBTokenizer;


public class NumberSwitcher {

	public static void main(String[] args) {
		String ref = "1 “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 100 。";
		String hyp = "1 “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000万 。";
		String transref = "I) LI Dan invested RMB 100 to Beijing Zhongjin Xinsheng Investment Center (limited partnership) ";
		String transhyp = "I) LI Dan invested RMB 10,000,000 to Beijing Zhongjin Xinsheng Investment Center (limited partnership) ";
		print("reference: " + transref);
		print("hypothesis: " + transhyp);
		Differ.getDiffer(transref, transhyp);
		System.out.println("Reference Translation: " + hyp);
		System.out.println("Hypothesis Translation: " + numberSwitch(Differ.refsub, Differ.hypsub, ref, "en","zh-cn"));
	}

	/* UNIVERSAL LANGUAGE
	 * if the different token is the combination of word and number, split the
	 * combination, and rewrite the combination to to pure number this part
	 * change 1,000 to 5,000 or 1000 to 5000 and reproduce the switched
	 * translation
	 */
	public static String numberSwitch(String tr1, String tr2, String trans, String lang) {
		String num = null;
		String transhyp = null;

		lang = StringUtils.left(lang, 2);

		if (lang.equals("zh")) {
			Pattern p = Pattern.compile("\\d+|\\d*\\.\\d+|\\d+\\.*\\d*\\D+|.*千*.*百*.*十*.*点*.*(亿|万)*.*千*.*百*.*十*.*点*.*");
			if (p.matcher(tr1) != null && p.matcher(tr2) != null) {
				ArrayList<String> en1 = MultiEnglishNum.formatChinese(tr1); // list of all possible translation of reference
				ArrayList<String> en2 = MultiEnglishNum.formatChinese(tr2); //list of all possible translation of hypothesis
				num = en1.toString() + en2.toString();
				//System.out.println(num);
				transhyp = cn2en(en1, en2, trans);
			}
		} else {
			Pattern p = Pattern.compile("(\\d+,)*\\d+\\.*\\d*");
			if (p.matcher(tr1) != null && p.matcher(tr2) != null) {
				//System.out.println("tr1: "+tr1+"tr2: "+tr2);
				ArrayList<String> cn1 = MultiChineseNum.formatEnglish(tr1);
				ArrayList<String> cn2 = MultiChineseNum.formatEnglish(tr2);
				num = cn1.toString() + cn2.toString();
				//System.out.println(num);
				transhyp = en2cn(cn1, cn2, trans);
			}
		}
		return transhyp;
	}
	//CH-EN 
	public static String numberSwitch(String tr1, String tr2, String trans, String source, String target) {
		String num = null;
		String transhyp = null;

		source = StringUtils.left(source, 2);
		target = StringUtils.left(target, 2);

		if (source.equals("zh") && target.equals("en")) {
			//in case there are unnormalized number
			tr1 = tr1.replaceAll(",", "");
			tr2 = tr2.replaceAll(",", "");
			Pattern p = Pattern.compile("\\d+|\\d*\\.\\d+|\\d+\\.*\\d*\\D+|.*千*.*百*.*十*.*点*.*(亿|万)*.*千*.*百*.*十*.*点*.*");
			if (p.matcher(tr1) != null && p.matcher(tr2) != null) {
				ArrayList<String> en1 = MultiEnglishNum.formatChinese(tr1); // list of all possible translation of reference
				en1.add(Num2En.parse(en1.get(0)));
				ArrayList<String> en2 = MultiEnglishNum.formatChinese(tr2); //list of all possible translation of hypothesis
				en2.add(Num2En.parse(en2.get(0)));
				num = en1.toString() + en2.toString();
				System.out.println(num);
				transhyp = cn2en(en1, en2, trans);
			} else {
				System.out.println("check zh-en translation!");
			}
		} else if (source.equals("en") && target.equals("zh")) {
			Pattern p = Pattern.compile("(\\d+,)*\\d+\\.*\\d*");
			if (p.matcher(tr1) != null && p.matcher(tr2) != null) {
				ArrayList<String> cn1 = MultiChineseNum.formatEnglish(tr1);
				ArrayList<String> cn2 = MultiChineseNum.formatEnglish(tr2);
				num = cn1.toString() + cn2.toString();
				//System.out.println("rep numbers: "+num);
				transhyp = en2cn(cn1, cn2, trans);
			}
		}
		return transhyp;
	}


	public static String en2cn(ArrayList<String> l1, ArrayList<String> l2, String tran) {
		String[] tranl = UtilLang.pureTokenizeZh(tran).split("\\s+");
		String tran2 = "";
		if(tran.length()>3){
		tran2 = tran.substring(0, 2);
		tran = tran.substring(2);
		}

		String transhyp = null;

		// l1 第一个为阿拉伯数字，第二个为英文格式数字
		for (int l = 0; l < l1.size(); l++) {
			for (int i = 2; i < tranl.length; i++) {
				if (tranl[i].equals(l1.get(l))) {
					//print("rep>>>"+tranl[i]);

					// pure number
					if (l == 0) {
						transhyp = tran.replace(l1.get(l), l2.get(0));
					} // pure character
					else if (l == 1) {
						transhyp = tran.replace(l1.get(l), l2.get(1));
					} // number + character
					else if (l == 2) {
						if (l2.size() < 3) {
							transhyp = tran.replace(l1.get(l), l2.get(0));
						} else {
							transhyp = tran.replace(l1.get(l), l2.get(2));
						}
					}
					else if (l == 3) {
						if (l2.size() <= 3) {
							transhyp = tran.replace(l1.get(l), l2.get(0));
						} else {
							transhyp = tran.replace(l1.get(l), l2.get(-1));
						}
					}
				}
			}
		}
		return tran2+transhyp;
	}
	
	/*public static String getPluralVerb(){
		
	}
	public static String getSingularVerb(){
		
	}*/
	public static String cn2en(ArrayList<String> l1, ArrayList<String> l2, String tran) {
		String[] tranl = UtilLang.tokenizeUniversal(tran).split(" ");
		StringBuilder sb = new StringBuilder();
		String tran2 = "";
		Integer flag = 0;//Whether has one
		if(tran.length()>3){
			tran2 = tran.substring(0, 2);
			tran = tran.substring(2);
			}
		String transhyp = null;
		String transnew = null;
		//
		if(l1.contains("1")){
			flag = 1;
			for (int l = 0; l < l1.size(); l++) {
				for (int i = 2; i < tranl.length; i++) {
					if (tranl[i].equals(l1.get(l))) {
						tran = tran.replace(l1.get(l), l2.get(l));	
						transnew = tran.replace(tranl[i+1], English.plural(tranl[i+1]));
					}
				}
			}
		}
		if(l2.contains("1")){
			flag = 2;
			for (int l = 0; l < l1.size(); l++) {
				for (int i = 2; i < tranl.length; i++) {
					if (tranl[i].equals(l1.get(l))) {
						transnew = tran.replace(l1.get(l), l2.get(l));	
						//transnew = tran.replace(tranl[i+1], Inflector.singularize(tranl[i+1]));
					}
				}
			}
		}
		// l1 第一个为阿拉伯数字，第二个为英文格式数字
		if(flag==0){
			for (int l = 0; l < l1.size(); l++) {
				for (int i = 2; i < tranl.length; i++) {
				System.out.println(tranl[i]);
					if (tranl[i].equals(l1.get(l))) {
						transnew = tran.replace(l1.get(l), l2.get(l));	
					}
				}
			}
		}
		transhyp = PTBTokenizer.ptb2Text(tran2+transnew);
		return transhyp;
	}

	private static void print(Object arg0) {
		System.out.println(arg0);
	}

	/*
	 * public static String formatStrAndNum(String t1,String t2,String trans) {
	 * 
	 * if (trans.contains(formOriginal1)){ System.out.println("bingo");
	 * transhyp=trans.replace(formOriginal1,formOriginal2);} else if
	 * (trans.contains(formNum1)){ System.out.println("formatted!");
	 * transhyp=trans.replace(formNum1, formNum2); } return transhyp; }
	 */

	/*
	 * Split into single Chinese and number Pattern p =
	 * Pattern.compile("\\d+|\\D"); java.util.regex.Matcher m = p.matcher(hyp);
	 * while ( m.find() ) { System.out.println( m.group() ); }
	 */
}

package MultiSwap;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Check {
	public static Boolean replace = false;
	public static Boolean insertion = false;
	public static Boolean deletion = false;

	// filter out word replacement
	public static Integer isGeneric(List<String> refl,List<String> reftransl) {
		Integer gi1check = 0;//0 empty 1 true 2 false
		Integer gi2check = 0;//0 empty 1 true 2 false
		Integer generic = 0;//0 empty 1 true 2 false
		if (genericIndex(refl.get(0))) {
			if (reftransl.subList(0, 2).contains(refl.get(0))) {
				gi1check = 1;
			}else if (!reftransl.subList(0, 2).contains(refl.get(0))) {
				gi1check = 2;
			}
		}
		if (genericIndex(refl.get(1))) {
			if (reftransl.subList(0, 2).contains(refl.get(1))) {
				gi2check = 1;
			}else if (!reftransl.subList(0, 2).contains(refl.get(0))) {
				gi2check = 2;
			}
		}
		//print(gi1check+" "+gi2check);
		if (gi1check != 0 || gi2check != 0){
			if (gi1check == 1 || gi2check == 1) {
				generic = 1;
			}
			if (gi1check == 2 || gi2check == 2) {
				generic = 2;
			}
		}
		return generic;
	}
	public static Boolean numValue(String token,String reftrans,String reflang,String translang){
		Boolean check = true;
		if (isNumber(token)) {
			// chinese to english
			if (reflang.equals("zh") && translang.equals("en")) {
				//List<String> entransl = Arrays.asList(UtilLang.tokenize(reftrans).split(" "));
				Pattern pc = Pattern.compile("\\d+|\\d*\\.\\d+|\\d+\\.*\\d*\\D+|.*千*.*百*.*十*.*点*.*(亿|万)*.*千*.*百*.*十*.*点*.*");
				//print(refl.get(i).length());
				if (pc.matcher(token) != null && token.length()>0) {
					//print("token: "+refl.get(i));
					ArrayList<String> en1 = MultiEnglishNum.formatChinese(token);
					en1.add(Num2En.parse(en1.get(0)));
					//print("here en1	"+en1);
					ArrayList<Boolean> checkl = new ArrayList<Boolean>();
					for (String e : en1) {
						//mark contain
						if (reftrans.contains(" "+e+" ")) {
							checkl.add(true);
						} else {
							//print(e+"	"+entransl.toString());
							checkl.add(false);
						}
					}
					if (!checkl.contains(true) && en1.size()>0) {
						check = false;
						//print("zh-en num break!!!"+checkl.toString());

					}
				} 
				//System.out.println("zh-en number check : "+check);
			}
			// english to chinese
			else if (reflang.equals("en") && translang.equals("zh")) {
				List<String> transl = Arrays.asList(UtilLang.tokenizeZh(reftrans).split(" "));
				Pattern pe = Pattern.compile("(\\d+,)*\\d+\\.*\\d*");
				//print("000>>>"+refl.get(i));
				if (pe.matcher(token) != null || !token.equals("\\.")) {
					//print("111>>>"+refl.get(i));
					ArrayList<String> cn1 = MultiChineseNum.formatEnglish(token);
					ArrayList<Boolean> checkl = new ArrayList<Boolean>();
					//print("222>>>"+cn1.toString());
					for (String c : cn1) {
						if (transl.contains(c)) {
							checkl.add(true);
						} else {
							checkl.add(false);
						}
					}
					if (!checkl.contains(true) && cn1.size()>0) {
						check = false;
						//print("en-zh num break!!!");
					}
				}
				//System.out.println("en-zh number check: "+check);
			}	
		}

		return check;
		
	}
	public static Boolean translationValue(String ref, String reftrans, String reflang, String translang) {
		Boolean check = true;
		ArrayList<Boolean> lang = new ArrayList<Boolean>();
		ArrayList<Boolean> categ = new ArrayList<Boolean>();
		ArrayList<Boolean> num = new ArrayList<Boolean>();
		Integer generic = 0;
		Boolean word = null;
		Integer in0 = null; //ref index at position 0
		Integer in1 = null; //ref index at position 1
		Integer intran0 = -1; //reftrans index at position 0
		Integer intran1 = -1; //reftrans index at position 1
		List<String> refl = Arrays.asList(UtilLang.tokenizeLang(ref, reflang).split(" "));
		List<String> reftransl = Arrays.asList(UtilLang.tranTokenizeLang(reftrans, translang).split(" "));
		//print(UtilLang.tokenizeLang(ref, reflang));
		//print(UtilLang.tranTokenizeLang(reftrans, translang));
		reflang = StringUtils.left(reflang, 2);
		translang = StringUtils.left(translang, 2);

		//check lang category
		if (reflang.equals("zh") && translang.equals("en")){
			lang.add(true);
		}
		if (reflang.equals("en") && translang.equals("zh")){
			lang.add(true);
		}
		if (!lang.contains(true)){
			check = false;
			return check;
		}

		//check generic index
		generic = isGeneric(refl,reftransl);
		//print(generic);
		if (generic == 2) {
			return check = false;
		}
		//check categorical index
		
		if (generic == 0) {
			if (isIndex(refl.get(0))) {
				in0 = ReplaceIndex.getHypIndex(refl.get(0));
				intran0 = ReplaceIndex.getHypIndex(reftransl.get(0));
				intran1 = ReplaceIndex.getHypIndex(reftransl.get(1));
				//print("token0>>> "+refl.get(0)+" in0: "+in0+" intran0: "+intran0+" intran1: "+intran1);
				if (in0.equals(intran0) || in0.equals(intran1)){
					categ.add(false);
				}else
					categ.add(true);
			} else if (isNumber(refl.get(0))){
				if (numValue(refl.get(0),reftrans,reflang,translang)==true)
					num.add(true);
				else
					num.add(false);
			}
			else
				word = true;
			if (isIndex(refl.get(1))) {
				in1 = ReplaceIndex.getHypIndex(refl.get(1));
				intran0 = ReplaceIndex.getHypIndex(reftransl.get(0));
				intran1 = ReplaceIndex.getHypIndex(reftransl.get(1));
				//print("token1>>> "+refl.get(1)+" in1: "+in1+" intran0: "+intran0+" intran1: "+intran1);
				if (in1.equals(intran0) || in1.equals(intran1)){
					categ.add(false);
				}else
					categ.add(true);
			}else if (isNumber(refl.get(1))){
				if (numValue(refl.get(0),reftrans,reflang,translang)==true)
					num.add(true);
				else
					num.add(false);
			}
			else
				word = true;
			
		//print("word	"+word+categ.toString()+" "+num.toString());
		//if the position of index is number
		
		if (word!=null && categ.contains(true)){
			check = false;
			return check;
		}
		if (word!=null && num.contains(false)){
			check = false;
			return check;
		}
		//print ("before num check:"+check);
		for (int i = 2; i < refl.size(); i++) {
			// check index value
			//print("routine check: "+check);
			if(numValue(refl.get(i),reftrans,reflang,translang)==false){
				check = false;
				}
			}//print("total check: "+check);
		}
		return check;
	}

	public static Boolean genericIndex(String w) {
		Pattern p = Pattern.compile("\\d+\\.\\d+\\.\\d+|\\d+\\.\\d+");
		Boolean res = false;
		if (p.matcher(w).matches()) {
			String[] dl = w.split("\\.");
			if (dl[0].length() < 4) {
				res = true;
			}
		}
		return res;

	}
	public static Boolean hasOne(String ref, String hyp) {
		Boolean r = false;
		
		List<String> refl = Arrays.asList(ref.split(" "));
		List<String> hypl = Arrays.asList(hyp.split(" "));
		
		if (refl.size()>2&& hypl.size()>2){
			List<String> subref = refl.subList(2,refl.size()-1);
			List<String> subhyp = hypl.subList(2,hypl.size()-1);
			String refrest = String.join(" ",subref);
			String hyprest = String.join(" ",subhyp);
			
			DifferMulti dm = new DifferMulti();
			dm.getDiffer(refrest, hyprest);
			Integer num = dm.wer;
			
			if (num > 0 && num < 3){
				for (int i = 0;i<dm.refsub.size();i++){					
					if (dm.refsub.get(i).equals("1")){//&& !dm.hypsub.get(i).equals(dm.refsub.get(i))
						r = true;
					}
					if (dm.hypsub.get(i).equals("1")){
						r = true;
					}					
				}
			}
		}
		return r;
	}
	
	public static Boolean partialCheck(String ref, String hyp) {
		Boolean r = false;
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		//print("ref: "+ref+" "+String.join(" ", dm.refsub)+" ref");
		//print("hyp: "+hyp+" "+String.join(" ", dm.hypsub)+" hyp");
		Integer num = dm.wer;
		//print("wer num: "+num);

		if (num > 0 && num < 3) {
			if (dm.refsub.size() != 0 && dm.hypsub.size() != 0) {
				ArrayList<String> hypsub = dm.hypsub;
				ArrayList<String> refsub = dm.refsub;
				ArrayList<String> branch = new ArrayList<String>();
				//only switch plura in case single change to plura noun remains the same
				for (int i = 0; i < dm.refsub.size(); i++) {
					branch.add(check(refsub.get(i), ref));
				}
				for (int i = 0; i < dm.hypsub.size(); i++) {
					branch.add(check(hypsub.get(i), hyp));
				}
				if (!branch.contains("WORD")) {
					r = true;
				}
			}
		}else if(num==0){
			r = true;
		}
		/*
		 * else if (num<3){ if (dm.refsub.size()==0 || dm.hypsub.size()==0){ r =
		 * false; return r; }else if (dm.refsub.size()>0 && dm.hypsub.size()>0){
		 * ArrayList<String> hypsub = dm.hypsub; ArrayList<String> refsub =
		 * dm.refsub; for(int i=0;i<dm.refsub.size();i++) { String branch =
		 * check(refsub.get(i),ref); //print(branch); if
		 * (branch.equals("WORD")){ r = false; break; } } for(int
		 * i=0;i<dm.hypsub.size();i++) { String branch =
		 * check(hypsub.get(i),hyp); if (branch.equals("WORD")){
		 * //print("bingo word vice hyp"); //print(dm.hypsub.get(i)); r = false;
		 * break; } } } else if (num>=3) { r = false; } }
		 * 
		 */ return r;
	}

	public static Boolean isIndex(String w1) {
		Boolean res = false;
		if (ReplaceIndex.num.contains(w1)) {
			res = true;
		} else if (ReplaceIndex.capiword.contains(w1)) {
			res = true;
		} else if (ReplaceIndex.roman.contains(w1.toUpperCase())) {
			res = true;
		} else if (ReplaceIndex.shortword.contains(w1)) {
			res = true;
		} else if (ReplaceIndex.word.contains(w1)) {
			res = true;
		} else if (ReplaceIndex.ordinal.contains(w1)) {
			res = true;
		}
		return res;
	}

	// given different word and string check whether it should be switched as
	// index or normal words or number
	public static String check(String w, String s) {
		String res = null;
		List<String> sa = Arrays.asList(s.split(" "));
		//System.out.println("go check: "+w);

		if (sa.indexOf(w) < 2) {

			if (isIndex(w)) {
				res = "INDEX";
			} else if (genericIndex(w)) {
				res = "INDEX";
			} else if (isNumber(w)) {
				res = "NUMBER";
			} else {
				res = "WORD";
			}
		} else {
			if (isNumber(w)) {
				res = "NUMBER";
			} else {
				res = "WORD";
			}
		}
		//System.out.println("check branch:"+res);
		return res;
	}

	public static Boolean isNumber(String s) {
		boolean flag = false;
		double num = 0;
		double word = 0;

		Pattern p = Pattern.compile("\\d+\\.*\\d+|(\\d+,\\d+)*\\.*\\d+");
		if (p.matcher(s).matches()) {
			flag = true;
		} else {
			String[] sl = s.split("");
			for (String w : sl) {
				if (MultiEnglishNum.NUM.contains(w)) {
					num++;
				} else
					word++;
			}
			if (word >= 0 && num > 0)// !
				flag = true;
		}
		return flag;
	}

	public static Boolean numCheck(String ref, String hyp,String lang) {
		Boolean res = false;
		ref = UtilLang.tokenizeLang(ref, lang); // tokenized reference
		hyp = UtilLang.tokenizeLang(hyp, lang); // tokenized reference

		if (ref.split("\\s+").length == hyp.split("\\s+").length) {//replace index and numbers
			if ((partialCheck(ref, hyp) == true)) {
				res = true;
			}
		}/* else if(ref.split("\\s+").length - hyp.split("\\s+").length <= 3){
			if (Insertion.isIndex(ref, hyp)){
				res = 2;
			}
		}*/
		else {
			res = false;
		}

		if (hasOne(ref,hyp).equals(true)) {
			res = false;
		}
		return res;
	}

	@SuppressWarnings("unused")
	private static void print(Object arg0) {
		System.out.println(arg0);
	}

}

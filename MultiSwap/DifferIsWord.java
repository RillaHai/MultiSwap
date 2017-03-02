package MultiSwap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * 专有词汇转写
 * 词典有翻译概率
 * 词典格式如下(delimiter=\t)
 * 中金鑫盛	Zhongjin-Xinsheng	1.0000000
   百盛	baisheng	1.0000000
   百盛	Zhongjin-Xinsheng	0.5000000
 */
public class DifferIsWord {
	public static String refsub; //substituted reference token
	public static String hypsub; //substituted hypothesis token
	public static String reftran; //translation of the refsub
	public static String hyptran; //translation of the hypsub
	public static ArrayList<String> zh = new ArrayList<String>();
	public static ArrayList<Float> refprob = new ArrayList<Float>();
	public static ArrayList<Float> prob = new ArrayList<Float>();
	public static ArrayList<Float> hypprob = new ArrayList<Float>();
	public static ArrayList<String> en = new ArrayList<String>();
	public static String cnenDic = "/home/work/cnen/vehicle/model/dic";
	public static String encnDic = "/home/work/cnen/vehicle/model/dic";
	
	public static String replace(String ref,String hyp,String transref,String flag){
		
		getDiffer(hyp,ref);
		if (flag.equals("cn-en"))
			loadDic(cnenDic);
		else if(flag.equals("en-cn"))	
			loadDic(encnDic);
		String word = detectWord(transref,refprob);
		String trans = getHypTranslation(en,hypprob);
		String transhyp = transref.replace(word,trans);
		return transhyp;
	}
	
	public static void main(String[]args) {
		String ref = "1 “ 李丹 ” 投资 北京 中金鑫盛 投资 中心 （ 有限 合伙 ） 1000万元";
		String hyp = "1 “ 李丹 ” 投资 北京 百盛 投资 中心 （ 有限 合伙 ） 1000万元";
		String transref = "I)	LI Dan invested RMB 10,000,000 to Beijing Zhongjin-Xinsheng Investment Center (limited partnership) ";
		String translation = replace(ref,hyp,transref,"cn-en");
		System.out.println("referece: "+ref);
		System.out.println("hyp: "+hyp);
		System.out.println(transref);
		System.out.println(translation);
	}
	
	//return substituted words
	private static String getSub(String[] a ) {
		String subtoken = null;
		for (int i = 0;i<a.length;i++) {
			String[] al = a[i].split(">");
			if (al.length==2) {
				String reftoken = al[0];                   
				String refmark = al[1];
				if (refmark.equals("SUB")){
					subtoken = reftoken;
				}
			}
		}
		return subtoken;
	}
	
	//If there is one substitution change the value of refsub and hypsub
	public static void getDiffer(String ref,String hyp) {
		WordSequenceAligner aligner = new WordSequenceAligner();
		WordSequenceAligner.Alignment a = aligner.align(hyp.split(" "), ref.split(" "));
		if (a.numSubstitutions == 1) {
			refsub = getSub(a.reference);
			hypsub = getSub(a.hypothesis);
			}
		else {
			System.out.println("There are more than one substitution!");
		}
	}
	
	//load dic into three list, one is source language one is target language and one is the translation probability
	public static void loadDic(String dic) {
		 try {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(new File(dic));
			while (in.hasNext()) {
				String[] sl = in.nextLine().trim().split("	");
				zh.add(sl[0]);
				en.add(sl[1]);
				float p = Float.parseFloat(sl[2]);
				prob.add(p);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//get index of all the possible translation of reference sentence
	private static ArrayList<Integer> indexOfAll(Object obj, ArrayList<String> list){
	    ArrayList<Integer> indexList = new ArrayList<Integer>();
	    for (int i = 0; i < list.size(); i++)
	        if(obj.equals(list.get(i)))
	            indexList.add(i);
	    return indexList;
	    }
	
	//ob1 is reftran/hyptran list is en/zh
	//add hypothesis translation probability
	public static ArrayList<String> searchDic(Object ob1,ArrayList<String> list,ArrayList<Float> pro) {
		ArrayList<Integer> inl = indexOfAll(ob1,zh);
		ArrayList<String> res = new ArrayList<String>();
		for (Integer i:inl) {
			String v = list.get(i);
			Float p = prob.get(i);
			pro.add(p);
			res.add(v);
			}
		return res;
		}
	//loop the reference and detect the replaced words or segments
	public static String detectWord(String reftrans,ArrayList<Float> pro){
		String word = null;
		ArrayList<String> translation = searchDic(refsub,en,pro);
		//System.out.println(translation);
		String[] tl = reftrans.trim().split("\\s+");
		for (String t:tl) {
			if (translation.contains(t))
				word = t;
		}
		if (word == null)
			System.out.println("Translation of the changed word is not in the dictionary! ");
		return word;
	}
	
	public static String getHypTranslation(ArrayList<String> en,ArrayList<Float> pro) {
		String trans = null;
		Float p =  (float) 0;
		//System.out.println(hypsub);
		ArrayList<String> hyptranslation = searchDic(hypsub,en,pro);
		for (int h= 0;h<hyptranslation.size();h++) {
			if (hypprob.get(h)>p)
				p = hypprob.get(h);
		}
		if (p== (float) 0)
			System.err.println("The ArrayList of Probability is not updated!");
		int index = hypprob.indexOf(p);
		trans = hyptranslation.get(index);
		return trans;
	}
	
	}


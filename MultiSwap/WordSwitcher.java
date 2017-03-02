package MultiSwap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * 词汇替换程序
 * 适用于不带概率的词典，delimiter\t
 */
public class WordSwitcher {
	public static String cnenDic = "/home/work/cnen/vehicle/model/dic";
	public static String encnDic = "/home/work/cnen/vehicle/model/dic";
	public static ArrayList<String> sw = new ArrayList<String>(); //save dic source word
	public static ArrayList<String> tw = new ArrayList<String>(); //save dic target word

	public WordSwitcher() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String ref = "1 “ 李丹 ” 投资 北京 中金鑫盛 投资 中心 （ 有限 合伙 ） 1000万元";
		String hyp = "1 “ 李丹 ” 投资 北京 鑫盛 投资 中心 （ 有限 合伙 ） 1000万元";
		String transref = "I)	LI Dan invested RMB 10,000,000 to Beijing Zhongjin-Xinsheng Investment Center (limited partnership) ";
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref,hyp);
		ArrayList<String> refsub = dm.refsub;
		ArrayList<String> hypsub = dm.hypsub;
		String r = refsub.get(0);
		String h = hypsub.get(0);
		System.out.println(r);
		System.out.println(h);
		
		String translation = replace(r,h,transref,"cn-en");
		System.out.println("referece: "+ref);
		System.out.println("hyp: "+hyp);
		System.out.println(transref);
		System.out.println(translation);
	}
	
	public static String replace(String r,String h,String transref,String flag){
		if (flag.equals("cn-en"))
			loadDic(cnenDic);
		else if(flag.equals("en-cn"))	
			loadDic(encnDic);
		String trans = detectWord(transref,r,sw,tw);
		String trant = searchDic(h,sw,tw);
		String transhyp = transref.replace(trans,trant);
		return transhyp;
	}
	
	//load dic into three list, one is source language one is target language and one is the translation probability
		public static void loadDic(String dic) {
			 try {
				Scanner in = new Scanner(new File(dic));
				while (in.hasNext()) {
					String[] sl = in.nextLine().trim().split("	");
					sw.add(sl[0]);
					tw.add(sl[1]);
				}
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//get index of all the possible translation of reference sentence
		private static Integer indexOfAll(String r, ArrayList<String> sw){
		    Integer index = -1;
		    for (int i = 0; i < sw.size(); i++)
		        if(r.equals(sw.get(i)))
		            index=i;
		    return index;
		    }
		
		public static String searchDic(String r,ArrayList<String> sw,ArrayList<String> tw) {
			Integer ind = indexOfAll(r,sw);
			return tw.get(ind);
			}
		//loop the reference and detect the replaced words or segments
		public static String detectWord(String reftrans,String r,ArrayList<String> sw,ArrayList<String> tw){
			String word = null;
			String translation = searchDic(r,sw,tw);
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

}

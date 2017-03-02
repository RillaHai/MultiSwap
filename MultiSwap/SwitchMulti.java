package MultiSwap;

import java.util.ArrayList;

public class SwitchMulti {
	public static Integer num;

	public static void main(String[] args) {
		
		String transref = "There are three roads beside river.";
		String hyp = "河边有一条路";
		String ref = "河边有三条路";
			    

		System.out.println("REF:" + ref);
		System.out.println("HYP:" + hyp);
		print("transref: "+transref);

		// check numerical value between ref and transref
		System.out.println("SOURCE CHECK:	" + Check.numCheck(ref, hyp,"zh"));
		System.out.println("VALUE CHECK:	" + Check.translationValue(ref, transref, "zh-ch", "en"));
		

		// apply numCheck
		if (Check.numCheck(ref, hyp,"en") && Check.translationValue(ref, transref, "zh-ch", "en")) {
			String tranres = numSwitch(ref, hyp, transref, "zh-ch","en");
			print("transhyp: " + tranres);
		} /*else if (Check.numCheck(ref, hyp,"zh-ch")){
			String tranres = Insertion.insert(transref);
			print("transhyp: " + tranres);
		}
		else {
			print("Can not be auto translated!");
		}*/

	}

	public static String numSwitch(String ref, String hyp, String transref, String flag,String target) {
		String res = null;
		// limited switch
		ref = UtilLang.tokenizeLang(ref, flag); // tokenized reference
		hyp = UtilLang.tokenizeLang(hyp, flag); // tokenized reference
		//print(ref);
		//print(hyp);

		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		Integer wer = dm.wer;
		if (wer==0){
			res = transref;
			return res;
		}
		ArrayList<String> refsub = dm.refsub;
		ArrayList<String> hypsub = dm.hypsub;
		for (int i = 0; i < dm.refsub.size(); i++) {
			String branch = Check.check(refsub.get(i), ref);
			//print(branch);
			//print(refsub.get(i)+hypsub.get(i)+transref);
			if (branch.equals("INDEX")) {
				if (res == null) {
					res = ReplaceIndex.replace(ref, hyp, hypsub.get(i), transref,target);
				} else
					res = ReplaceIndex.replace(ref, hyp, refsub.get(i), res,target);

			} else if (branch.equals("NUMBER")) {
				if (res == null)
					res = NumberSwitcher.numberSwitch(refsub.get(i), hypsub.get(i), transref, flag,target);
				else
					res = NumberSwitcher.numberSwitch(refsub.get(i), hypsub.get(i), res, flag,target);
			}
		}
		return res;
	}

//	public static String run(String ref, String hyp, String transref, String lang) {
//		lang = StringUtils.left(lang,2);
//
//		// preproceesing
//		ref = UtilLang.tokenizeLang(ref, lang); // tokenized reference
//		hyp = UtilLang.tokenizeLang(hyp, lang); // tokenized reference
//		print("reference: " + ref);
//		print("hypothesis: " + hyp);
//
//		// compare
//		String res = null;
//		DifferMulti dm = new DifferMulti();
//		dm.getDiffer(ref, hyp);
//		print(dm.wer);
//		// limited switch
//
//		ArrayList<String> refsub = dm.refsub;
//		ArrayList<String> hypsub = dm.hypsub;
//		for (int i = 0; i < dm.refsub.size(); i++) {
//			String branch = Check.check(refsub.get(i), ref);
//			print("difference is: " + branch);
//			print(hypsub.get(i));
//			if (branch.equals("INDEX")) {
//
//				if (res == null) {
//					res = ReplaceIndex.replace(ref, hyp, hypsub.get(i), transref);
//				} else
//					res = ReplaceIndex.replace(ref, hyp, refsub.get(i), res);
//
//			} else if (branch.equals("NUMBER")) {
//				if (res == null)
//					res = NumberSwitcher.numberSwitch(refsub.get(i), hypsub.get(i), transref, lang);
//				else
//					res = NumberSwitcher.numberSwitch(refsub.get(i), hypsub.get(i), res, lang);
//			} else if (branch.equals("WORD")) {
//				if (res == null)
//					res = WordSwitcher.replace(refsub.get(i), hypsub.get(i), transref, lang);
//				else
//					res = WordSwitcher.replace(refsub.get(i), hypsub.get(i), res, lang);
//			}
//		}
//
//		return res;
//	}

	private static void print(Object arg0) {
		System.out.println(arg0);
	}

}

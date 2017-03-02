package MultiSwap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insertion {
	public static String INS;

	public Insertion() {
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ref = "“ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 100 。";
		String hyp = "1.2.3 “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000万 。";
		String transref = "LI Dan invested RMB 100 to Beijing Zhongjin Xinsheng Investment Center (limited partnership).";
		String transhyp = "1) LI Dan invested RMB 10,000,000 to Beijing Zhongjin Xinsheng Investment Center (limited partnership).";
		//System.out.println("reference: " + transref);
		//System.out.println("hypothesis: " + transhyp);
		System.out.println("res:	"+isIndex(ref,hyp));
		System.out.println("restrans:	"+insert(transref));
		System.out.println("res:	"+isIndex(hyp,ref));
		System.out.println("restrans:	"+delete(transhyp));
	}

	public static Boolean consistent(ArrayList<String> insl, List<String> rl){
		Boolean res = false;
		if (!insl.isEmpty()){
			String ins = String.join("", insl);
			if (insl.size() == 3){
				if (ins.equals(String.join("", rl.subList(0, 3))) && ReplaceIndex.num.contains(insl.get(1))){
					INS = ins;
					res = true;
				}
			}else if (insl.size() == 2){
				if (ins.equals(String.join("", rl.subList(0, 2)))){
					if (ReplaceIndex.num.contains(insl.get(1)) || ReplaceIndex.num.contains(insl.get(0))){
						INS = ins;
						res = true;
					}
				}
			}else if (insl.size() == 1){
				if (ins.equals(rl.get(0))){
					if (Check.genericIndex(insl.get(0)) || ReplaceIndex.num.contains(insl.get(0))){
						INS = ins;
					    res = true;
					}
				}
			}
		}
		return res;
	}
	//ref doesn't have index but hyp has
	public static Boolean isIndex(String ref, String hyp) {
		Boolean res = false;
		List<String> hl = Arrays.asList(hyp.split(" "));
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		//System.out.println(dm.hypins);
		if (consistent(dm.hypins,hl)){
			res = true;
		} 
		return res;
	}
	
	public static String insert(String transref){
		return INS+" "+transref;
	}
	public static String delete(String transref){
		return transref.replaceFirst(INS+" ", "");
	}
	
	
	

}

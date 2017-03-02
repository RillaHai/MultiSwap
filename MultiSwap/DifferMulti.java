package MultiSwap;

import java.util.ArrayList;

public class DifferMulti {
	public ArrayList<String> refsub = new ArrayList<String>(); //substituted reference token
	public ArrayList<String> hypsub = new ArrayList<String>(); //substituted hypothesis token
	public ArrayList<String> refins = new ArrayList<String>(); //substituted reference token
	public ArrayList<String> hypins = new ArrayList<String>(); //substituted hypothesis token
	
	public Integer wer;


	public static void main(String[] args) {
		String ref = "“ 李丹 ” 投资 北京 中金鑫盛 投资 中心 （ 有限 合伙 ） 1000万元";
		String hyp = "a “ 李丹 ” 投资 北京 投资 中心 （ 有限 合伙 ） 100万元";
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref,hyp);
		print("refsub: "+dm.refsub);
		print("hypsub: "+dm.hypsub);
		print("hypins: "+dm.hypins);
		print("refins: "+dm.refins);

	}
	public static void getIns(String[] a,ArrayList<String> ins ){
		for (int i = 0;i<a.length;i++) {
			String[] al = a[i].split(">");
			if (al.length==2) {
				String reftoken = al[0];                   
				String refmark = al[1];
				if (refmark.equals("INS")){
					ins.add(reftoken);
				}
			}
		}
	}
	public static void getSub(String[] a,ArrayList<String> sub ){

		for (int i = 0;i<a.length;i++) {
			String[] al = a[i].split(">");
			if (al.length==2) {
				String reftoken = al[0];                   
				String refmark = al[1];
				if (refmark.equals("SUB")){
					sub.add(reftoken);
				}
			}
		}
	}
	
	public static void getDel(String[] a,ArrayList<String> sub ){

		for (int i = 0;i<a.length;i++) {
			String[] al = a[i].split(">");
			if (al.length==2) {
				String reftoken = al[0];                   
				String refmark = al[1];
				if (refmark.equals("DEL")){
					sub.add(reftoken);
				}
			}
		}
	}

			
	//If there is one substitution change the value of refsub and hypsub
	public void getDiffer(String ref,String hyp) {
		WordSequenceAligner aligner = new WordSequenceAligner();
		WordSequenceAligner.Alignment a = aligner.align(hyp.split(" "), ref.split(" "));
		
		wer = a.getWER();
		//System.out.println("weird reference"+String.join(" ",a.reference)+""+String.join(" ",a.hypothesis));
		getSub(a.reference,hypsub);
		getSub(a.hypothesis,refsub);
		getIns(a.hypothesis,refins);
		getDel(a.reference,hypins);
		//print(String.join(" ", a.reference));
		//print(String.join(" ", a.hypothesis));
	}
	
	private static void print(Object arg0) {
		System.out.println(arg0);
	}


}

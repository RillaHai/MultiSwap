                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         package MultiSwap;

public class Differ {
	public static String refsub; //substituted reference token
	public static String hypsub; //substituted hypothesis token

	//return substituted words
	private static String getSub(String[] a ){
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
			hypsub = getSub(a.reference);
			refsub = getSub(a.hypothesis);
			}
		else {
			System.out.println("There are more than one substitution!");
		}
	}
}

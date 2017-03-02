package MultiSwap;
import java.util.Arrays;
import java.util.regex.Pattern;

public class testIndex {
	
	static String cate; //word category
	static String res; //word need to be replaced

	public static final String[] ordinal = new String[]{"第一","第二","第三","第四","第五","第六","第七","第八","第九","第十","第十一","第十二","第十三","第十四","第十五","第十六","第十七","第十八","第十九","第二十"};
	public static final String[] num = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	public static final String[] word = new String[]{"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"};
	public static final String[] capiword = new String[]{"First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Ninth","Tenth","Eleventh","Twevlfth","Thirteenth","Fourteenth","Fifteenth","Sixteenth","Seventeenth","Eighteenth","Nineteenth","Twentieth"};
	public static final String[] roman = new String[]{"I","II","III","IV","Ⅴ"," VI","VII","VIII","IX","X","XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX"};
	public static final String[] shortword = new String []{"a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","t"};
		
	public static void main(String[] args) {
		String ref = "( 1.1 ) “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000 万元";
		String hyp = "( 2 ) “ 李丹 ” 投资 北京中 金鑫盛 投资 中心 （ 有限 合伙 ） 1000 万元";
		String transref = "( 1.1 )	LI Dan invested RMB 10,000,000 to Beijing Zhongjin Xinsheng Investment Center (limited partnership) ";
//		String transhyp = "I)	LI Dan invested RMB 10,000,000 to Beijing Zhongjin Xinsheng Investment Center (limited partnership) ";
		//replace
		DifferMulti dm = new DifferMulti();
		dm.getDiffer(ref, hyp);
		String transhyp = replace(ref,hyp,dm.hypsub.get(0),transref);
		System.out.println(dm.hypsub.toString());
		System.out.println(dm.refsub.toString());
		System.out.println(transref);
		System.out.println(transhyp);
	}

	private static Integer getHypIndex(String ref,String hyp,String refsub) {
		 //registering sub word to global variables
		int indexHyp = -1;
		
		if (Arrays.asList(num).contains(refsub)) 
		    indexHyp = Arrays.asList(num).indexOf(refsub);
		else if (Arrays.asList(word).contains(refsub))
			indexHyp = Arrays.asList(word).indexOf(refsub);
		else if (Arrays.asList(capiword).contains(refsub))
			indexHyp = Arrays.asList(capiword).indexOf(refsub);
		else if (Arrays.asList(roman).contains(refsub))
			indexHyp = Arrays.asList(roman).indexOf(refsub);
		else if (Arrays.asList(shortword).contains(refsub))
			indexHyp = Arrays.asList(shortword).indexOf(refsub);
		else if (Arrays.asList(ordinal).contains(refsub))
			indexHyp = Arrays.asList(ordinal).indexOf(refsub);
		
		else
			System.out.print("The value is beyond designed");
		return indexHyp;
	}
	
	private static String checkVagueContain(String token,String[] list) {
		String res = null;
		for (String s:list) {
			Pattern p = Pattern.compile(s);
			java.util.regex.Matcher m = p.matcher(token);
		    if (m.find()){
		    	res = s;
		       // System.out.println("match cate:"+res);
		        }
		}
		return res;
	}
	
	private static void getCategory (String transref) {
		String token = transref.trim().split("\\s+")[0];
		if (checkVagueContain(token,num)!= null) {
			cate = "num";
			res = checkVagueContain(token,num);
		} else if (checkVagueContain(token,word)!= null){
			cate = "word";
			res = checkVagueContain(token,word);
		} else if (checkVagueContain(token,capiword)!=null){
			cate = "capiword";
			res = checkVagueContain(token,capiword);
		} else if (checkVagueContain(token,roman)!=null){
			cate = "roman";
			res = checkVagueContain(token,roman);
		} else if (checkVagueContain(token,shortword)!=null){
			cate = "shortword";
			res = checkVagueContain(token,shortword);
		} 
		else if (checkVagueContain(token,ordinal)!=null){
			cate = "ordinal";
			res = checkVagueContain(token,ordinal);
		}else
			System.out.println("no category found: "+token);
	}
	public static String replace (String ref,String hyp,String refsub,String transref) {
		Integer index = getHypIndex(ref,hyp,refsub);
		getCategory(transref);
		String transhyp = null;
		//System.out.println(index+" "+cate+" "+word[index]);
		if (index != -1) {
			if (cate.equals("num"))
				transhyp = transref.replaceFirst(res,num[index]);
			else if (cate.equals("word"))
				transhyp = transref.replaceFirst(res,word[index]);
			else if (cate.equals("capiword"))
				transhyp = transref.replaceFirst(res,capiword[index]);
			else if (cate.equals("roman"))
				transhyp = transref.replaceFirst(res,roman[index]);
			else if (cate.equals("shortword"))
				transhyp = transref.replaceFirst(res,shortword[index]);
			else if (cate.equals("ordinal"))
				transhyp = transref.replaceFirst(res,ordinal[index]);
			else
				System.out.println("either the first token is not replacable or the value is beyong designed");
		}
		return transhyp;
	}
	
}

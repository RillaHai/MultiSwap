package MultiSwap;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang.StringUtils;

import com.ue.maxdata.tool.UEAnalyzer;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;

public class UtilLang {
	//final static String UNIT = "元|个|米|克|度|毫米|厘米|毫升|分|寸|尺|毫克|丈|亩|丝|斤|勺|斗|英镑|加元|日元|韩元|本|天|\\.|支";
	final static List<String> en = new ArrayList<String>(En2Num.hm.keySet());
	
	public static void main(String[] args) {
		String test = "2.2.3 U.S.A. U.K. There was two kinds of ways.";	
		System.out.println(tokenize(test));}
		/*try {
            Scanner in = new Scanner(new File("/home/bear/unit.test"));
            while (in.hasNextLine()) {
                String str = in.nextLine();
                System.out.println(tokenizeZh(str));
                }
			}catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		}*/
	//change English number words to numbers
	public static String enWord2Num(String sen){
		sen = sen.toLowerCase();
		String r = "((one |two |three |four |five |six |seven |eight |nine )?"
				+ "(hundred |hundred and )?"
				+ "(ninety |eighty |seventy |sixty |fifty |forty |thirty |twenty )?"
				+ "(one |two |three |four |five |six |seven |eight |nine |ten "
				+ "|eleven |twelve |thirteen |fourteen |fifteen |sixteen |seventeen |eighteen |nineteen )?"
				+ "(trillion |billion |million |thousand )?)+"
				+ "(one |two |three |four |five |six |seven |eight |nine )?"
				+ "(hundred |hundred and |and )?"
				+ "(ninety |eighty |seventy |sixty |fifty |forty |thirty |twenty )?" 
				+ "(one |two |three |four |five |six |seven |eight |nine |ten "
				+ "|eleven |twelve |thirteen |fourteen |fifteen |sixteen |seventeen |eighteen |nineteen )?"
				+ "(point )?"
				+ "(one |two |three |four |five |six |seven |eight |nine )*";
		
		Pattern p = Pattern.compile(r);
		System.out.println(sen.replaceAll(r, En2Num.parse(r)));
		Matcher matcher = p.matcher(sen);
		if (matcher.find() && matcher.group().length()>0) {
			String numWord = matcher.group();
			System.out.println("numWord: "+numWord);
			String num = En2Num.parse(numWord);
			return sen.replace(numWord, num+" ");
		}
		return sen;
	}

	//tokenize language
	public static String tokenizeLang(String sen, String lang) {
		lang = StringUtils.left(lang, 2);

		if (lang.equalsIgnoreCase("zh")) {
			sen = UtilLang.tokenizeZh(sen);
		} else if (lang.equalsIgnoreCase("en")) {
			sen = UtilLang.tokenize(sen);
		} else {
			sen = UtilLang.tokenizeUniversal(sen);// save for appending new language
		}
		return sen;
	}
	
	public static String tranTokenizeLang(String sen, String lang) {
		lang = StringUtils.left(lang, 2);

		if (lang.equalsIgnoreCase("zh")) {
			sen = UtilLang.pureTokenizeZh(sen);
		} else {
			sen = UtilLang.tokenizeUniversal(sen);// save for appending new language
		}
		return sen;
	}

	
	//roughly for all languages
	public static String tokenizeUniversal(String sen) {
		TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "ptb3Escaping=false");
		Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(sen));
		List<CoreLabel> rawWords = tok.tokenize();
		StringBuilder sb = new StringBuilder();
		for (CoreLabel r :rawWords){
			sb.append(r.toString());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	//tokenize English
	public static String tokenize(String text) {
		TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
				"ptb3Escaping=false");
		Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(text));
		List<CoreLabel> rawWords = tok.tokenize();
		StringBuilder sb = new StringBuilder();
		for (CoreLabel r : rawWords) {
			sb.append(r.toString());
			sb.append(" ");
		}
		String tdSen = sb.toString();
		List<String> tl = Arrays.asList(tdSen.split(" "));
		ArrayList<String> pl = new ArrayList<String>();
		en.add("point");
		en.add("and");
		for (int i=0; i<tl.size(); i++){
			if (en.contains(tl.get(i))) {
				if (pl.size()==0){
					pl.add(tl.get(i));
				}else{
					if(tl.get(i-1).equals((pl.get(pl.size()-1)))){//if continuous
						pl.add(tl.get(i));
					}
				}	
			}else{
				if(pl.size()>0){
					if (pl.size()==1 && !pl.get(0).equals("and") && !pl.get(0).equals("point")
							&& !pl.get(0).equals("And") && !pl.get(0).equals("Point")	){
						String s = String.join(" ", pl);
						pl.clear();
						tdSen = tdSen.replace(s, En2Num.parse(s));
						}
					else if (pl.size()==2 && !String.join(" ", pl).equals("and point") && !String.join(" ", pl).equals("And point") 
							&& !String.join(" ", pl).equals("point and") && !String.join(" ", pl).equals("Point and")){
						String s = String.join(" ", pl);
						pl.clear();
						tdSen = tdSen.replace(s, En2Num.parse(s));
					}
					else if (pl.size()>2){
						System.out.println(pl.size());
						String s = String.join(" ", pl);
						pl.clear();
						tdSen = tdSen.replace(s, En2Num.parse(s));
					}
				}
			}
		}
		//lemmatise
		UEAnalyzer ua = UEAnalyzer.getInstance();
		List<String> tdl = ua.getList(tdSen, "en");
		
		return String.join(" ", tdl);
	}

	//if string contains Chinese number words return true
	public static Boolean containsZhNumWord(String sen) {
		Boolean res = false;
		Pattern p = Pattern.compile(".*千*.*百*.*十*.*点*.*(亿|万)*.*千*.*百*.*十*.*点*.*");
		Matcher matcher = p.matcher(sen);
		if (matcher.find()) {
			res = true;
		}
		return res;
	}
	
	public static Boolean isZhNumWord(String num) {
		Boolean res = false;
		Pattern pa = Pattern.compile("(一|二|三|四|五|六|七|八|九|十|百|千|万|亿|点)+|"
				+ "\\d+\\.\\d+\\.\\d+|\\d+\\.?\\d*(亿|万)?");
		Matcher matcher = pa.matcher(num);					
		if (matcher.find()) {
			res = true;
		/*String[] numl = num.split("");
		for (String n : numl) {
			if (!MultiEnglishNum.NUM.contains(n)) {
				res = false;
			}
*/	    }return res;
	}
	
	public static String zhWord2Num(String word){
		if (isZhNumWord(word)) {
			ArrayList<String> en1 = MultiEnglishNum.formatChinese(word); // list of all possible translation of reference
			if (en1.size() > 0){
			word = word.replace(word, en1.get(0));
			//System.out.println("en1"+en1.size()+en1.toString());
			}
		}
		return word;
	}
	
	// Tokenize Chinese with number rewrite.
	public static String tokenizeZh(String text) {
		List<Term> parse = ToAnalysis.parse(text);

		// System.out.println(parse);
		String wordLine = "";

		for (Term par : parse) {
			String wordWithTag = par.toString();

			if (!wordWithTag.equals("/")) {
				String word = wordWithTag.split("/")[0];
				Pattern pa = Pattern.compile("(一|二|三|四|五|六|七|八|九|十|百|千|万|亿|点)+|"
						+ "\\d+\\.\\d+\\.\\d+|\\d+\\.?\\d*(亿|万)?");
				Matcher matcher = pa.matcher(word);					
				if (matcher.find()) {
					String unit = matcher.group(0);
					//System.out.println(unit);
					word = word.replace(unit," "+unit+ " ");
					//System.out.println(word);
				}
				wordLine = wordLine + (word);
				wordLine = wordLine + " ";
			} else {
				wordLine = wordLine + "/";
				wordLine = wordLine + " ";
			}
		}
		
		String[] wl = wordLine.replaceAll("\\s+", " ").trim().split(" ");
		ArrayList<String> nwl = new ArrayList<String>();
		for (String w : wl) {
			w =zhWord2Num(w);
			nwl.add(w);
			//System.out.println("formatted: "+w);
		}
		//System.out.println("formatted whole: "+String.join(" ", nwl));
		wordLine = String.join(" ", nwl);
		return wordLine;

	}
	
	public static String pureTokenizeZh(String text) {
		List<Term> parse = ToAnalysis.parse(text);

		// System.out.println(parse);
		String wordLine = "";

		for (Term par : parse) {
			String wordWithTag = par.toString();

			if (!wordWithTag.equals("/")) {
				String word = wordWithTag.split("/")[0];
				Pattern pa = Pattern.compile("(一|二|三|四|五|六|七|八|九|十|百|千|万|亿|点)+|"
						+ "\\d+\\.\\d+\\.\\d+|\\d+\\.?\\d*(亿|万)?");
				Matcher matcher = pa.matcher(word);					
				if (matcher.find()) {
					String unit = matcher.group(0);
					//System.out.println(unit);
					word = word.replace(unit," "+unit+ " ");
					//System.out.println(word);
				}
				wordLine = wordLine + (word);
				wordLine = wordLine + " ";
			} else {
				wordLine = wordLine + "/";
				wordLine = wordLine + " ";
			}
		}		
		wordLine = wordLine.replaceAll("\\s+", " ").trim();
		return wordLine;
	}
}

package MultiSwap;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

public class GetDependency {
	public final static String taggerPath = "/home/bear/Downloads/tagger/stanford-postagger-full-2015-12-09/models/english-left3words-distsim.tagger";
	public final static String modelPath = DependencyParser.DEFAULT_MODEL;

	public GetDependency() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sen = "didn't 34 people vote for the election.";
		getVerb(sen,4);
	}


	public static void getVerb(String sen,Integer id){
		Integer govid = null;
		Integer auxid = null;
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(sen));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);
			Collection<TypedDependency> td = gs.typedDependenciesCollapsed();

			Object[] list = td.toArray();
			TypedDependency typedDependency;
			for (Object object : list) {
				typedDependency = (TypedDependency) object;
				System.out.println(object);
				System.out.println("Depdency Name : "+typedDependency.dep().toString()+ " :: "+typedDependency.reln());
				if (typedDependency.reln().getShortName().equals("nsubj") && typedDependency.dep().index()==id) {
					govid = typedDependency.gov().index();
					}
			}
			System.out.println("govid: "+govid);
			for (Object object : list) {
				typedDependency = (TypedDependency) object;
				//System.out.println(object);
				//System.out.println("Depdency Name : "+typedDependency.dep().toString()+ " :: "+typedDependency.reln());
				if (govid!=null && typedDependency.reln().getShortName().equals("aux") && typedDependency.gov().index()==govid) {
					System.out.println(typedDependency.dep().index());
					}
			}
		}
	}
}
package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.io.*;

import utils.Alphabet;
import utils.Dictionary;

public class DependencyInstance implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public int length;

	// FORM: the forms - usually words, like "thought"
	public String[] forms;

	// LEMMA: the lemmas, or stems, e.g. "think"
	public String[] lemmas;
	
	// COARSE-POS: the coarse part-of-speech tags, e.g."V"
	public String[] cpostags;

	// FINE-POS: the fine-grained part-of-speech tags, e.g."VBD"
	public String[] postags;
	
	// MOST-COARSE-POS: the coarsest part-of-speech tags (about 11 in total)
	//public String[] mcpostags;
	
	// FEATURES: some features associated with the elements separated by "|", e.g. "PAST|3P"
	public String[][] feats;

	// HEAD: the IDs of the heads for each element
	public int[] heads;

	// DEPREL: the dependency relations, e.g. "SUBJ"
	public String[] deprels;
	
	public int[] formids;
	public int[] lemmaids;
	public int[] postagids;
	public int[] cpostagids;
	public int[] deprelids;
	public int[][] featids;
	public int[] wordVecIds;

	public int[] depids;

    public DependencyInstance() {}
    
    public DependencyInstance(int length) { this.length = length; }
    
    public DependencyInstance(String[] forms) {
    	length = forms.length;
    	this.forms = forms;
    	this.feats = new String[length][];
    	this.deprels = new String[length];
    }
    
    public DependencyInstance(String[] forms, String[] postags, int[] heads) {
    	this.length = forms.length;
    	this.forms = forms;    	
    	this.heads = heads;
	    this.postags = postags;
    }
    
    public DependencyInstance(String[] forms, String[] postags, int[] heads, String[] deprels) {
    	this(forms, postags, heads);
    	this.deprels = deprels;    	
    }

    public DependencyInstance(String[] forms, String[] lemmas, String[] cpostags, String[] postags,
            String[][] feats, int[] heads, String[] deprels) {
    	this(forms, postags, heads, deprels);
    	this.lemmas = lemmas;    	
    	this.feats = feats;
    	this.cpostags = cpostags;
    }
    
    public DependencyInstance(DependencyInstance a) {
    	//this(a.forms, a.lemmas, a.cpostags, a.postags, a.feats, a.heads, a.deprels);
    	//mcpostags = a.mcpostags;
    	length = a.length;
    	heads = a.heads;
    	formids = a.formids;
    	lemmaids = a.lemmaids;
    	postagids = a.postagids;
    	cpostagids = a.cpostagids;
    	deprelids = a.deprelids;
    	depids = a.depids;
    	featids = a.featids;
    	wordVecIds = a.wordVecIds;
    }
    
    //public void setDepIds(int[] depids) {
    //	this.depids = depids;
    //}
    
    public void setInstIds(Dictionary tagDict, Dictionary wordDict, 
    		Alphabet typeAlphabet, Dictionary wordVecDict) {
    	    	
    	formids = new int[length];    	
		deprelids = new int[length];
		depids = new int[length];
		postagids = new int[length];
		cpostagids = new int[length];
		
    	for (int i = 0; i < length; ++i) {
    		formids[i] = wordDict.lookupIndex("form="+forms[i]);
			postagids[i] = tagDict.lookupIndex("pos="+postags[i]);
			cpostagids[i] = tagDict.lookupIndex("cpos="+cpostags[i]);
			deprelids[i] = tagDict.lookupIndex("label="+deprels[i]);
			depids[i] = typeAlphabet.lookupIndex(deprelids[i]);
    	}
    	    	
    	if (lemmas != null) {
    		lemmaids = new int[length];
    		for (int i = 0; i < length; ++i)
    			lemmaids[i] = wordDict.lookupIndex("lemma="+lemmas[i]);
    	}

		featids = new int[length][];
		for (int i = 0; i < length; ++i) if (feats[i] != null) {
			featids[i] = new int[feats[i].length];
			for (int j = 0; j < feats[i].length; ++j)
				featids[i][j] = tagDict.lookupIndex("feat="+feats[i][j]);
		}
		
		if (wordVecDict != null) {
			wordVecIds = new int[length];
			for (int i = 0; i < length; ++i) {
				int wvid = wordVecDict.lookupIndex(forms[i]);
				if (wvid <= 0) wvid = wordVecDict.lookupIndex(forms[i].toLowerCase());
				if (wvid > 0) wordVecIds[i] = wvid; else wordVecIds[i] = -1; 
			}
		}
    }
    
}

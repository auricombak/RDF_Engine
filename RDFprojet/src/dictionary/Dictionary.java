package dictionary;

import java.util.HashMap;
import java.util.TreeMap;

public class Dictionary{
	TreeMap < String, Integer > dico;
	
	public Dictionary() {
		this.dico = new TreeMap<>();
	}
	
	public Dictionary(TreeMap<String, Integer> dico) {
		this.dico = new TreeMap<>();
		this.dico = dico;
	}

	public TreeMap<String, Integer> getDico() {
		return dico;
	}

	public void setDico(TreeMap<String, Integer> dico) {
		this.dico = dico;
	}


	
	
	
	
}
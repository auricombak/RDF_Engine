package dictionary;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryFactory {
		
	//From String, create object Query
	//Return Query for each Query in the file
	public Query createSimpleQuery(String q) {		
		
		Query query = new Query();
		
		Pattern pattern = Pattern.compile("(<[^>]+>)");
		Matcher matcher = pattern.matcher(q);
		
		int i=0;
		String object = "a";
		String predicat = "a";
		while (matcher.find())	
		{

		    
			if(i == 0) {
			    //System.out.println("Predicat : " + matcher.group(1).replaceAll("<*>*", ""));
			    predicat = matcher.group(1).replaceAll("<*>*", "");
			    i++;
			}
			else if(i == 1) {
			    //System.out.println("Objet : " + matcher.group(1).replaceAll("<*>*", ""));
			    object = matcher.group(1).replaceAll("<*>*", "");
			    
				i = 0;
				Condition c = new Condition(predicat, object);
				query.addCondition(c);
			    i = 0;
			}


		}
		
		return query;
	}

	//Read into the file and call [createSimpleQuery()] for each Query found
	//Return ArrayList Query for each Query in the file
	public ArrayList<Query> loadFromFile(String path) throws IOException {
		
		ArrayList<Query> listQuery = new ArrayList<>();
		
		File file = new File(path); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String st; 
		String sQuery = "";
		while ((st = br.readLine()) != null) {
			sQuery += st;
			if(st.contains("}")) {

				//System.out.println("#" + sQuery); 
				listQuery.add(createSimpleQuery(sQuery));
				sQuery = "";
				
			}

		} 
		return listQuery;
	}
	
	//Read into the folder and call [loadFromFile()] for each file
	//Return ArrayList containing ArrayList<Query> for each file
	public ArrayList<ArrayList<Query>> loadFromFolder(String path) throws IOException {
			ArrayList<ArrayList<Query>> listFileQuery = new ArrayList<>();
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
			  if (listOfFiles[i].isFile()) {
			    listFileQuery.add(loadFromFile(path+"/"+listOfFiles[i].getName()));
			  }
			}
			return listFileQuery;
	}
}


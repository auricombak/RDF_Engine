package dictionary;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		

		
		// TODO Auto-generated method stub
		RDFRawParser parser = new RDFRawParser();
		parser.parse("../RDFprojet/500K.rdfxml");
		
        Dictionary mDictionary = new Dictionary(parser.getList());
        Index mIndex = new Index(mDictionary, parser.getList());	
        Model mModel = new Model(mIndex, mDictionary);
        QueryFactory factory = new QueryFactory();
        
       				
		/*
		//Return ArrayList containing ArrayList<Query> for each file
		//factory.loadFromFolder("../RDFprojet/queries");
       
		//Return ArrayList containing each query
		for(Query q2 : factory.loadFromFile("../RDFprojet/queries/Q_1_eligibleregion.queryset") ) {
			QueryExecutioner qe2 = new QueryExecutioner(q2, mModel);
	        System.out.println(qe2.execute());
		}
		
*/
		
		ArrayList<Query> aq = factory.loadFromFile("../RDFprojet/queries/Q_4_location_nationality_gender_type.queryset");
		/*int i=0;
		for(Query q : aq) {
			i++;
			System.out.println("Query "+i + " : ");
			System.out.println(q.toString());
		} */
		
		long startTime = System.currentTimeMillis();
		//mDictionary.writeDico();
		//mDictionary.writeBase();
		mIndex.writeIo();
		int x = 0;
		for(ArrayList<Query> a : factory.loadFromFolder("../RDFprojet/queries")) {
			x++;
			int i=0;
			for(Query q : a) {
				QueryExecutioner qe = new QueryExecutioner(q, mModel);
	        	qe.execute();
			}
		}
		
	    long stopTime = System.currentTimeMillis();
	    
	    long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime + " Milliseconds");
		
		
        /*
        FileWriter writer = new FileWriter("outputO.txt"); 
        for(String str: parser.list.getObjects()) {
          writer.write(str + "\n");
        }
        writer.close();
        
        FileWriter writer2 = new FileWriter("outputS.txt"); 
        for(String str: parser.list.getSubjects()) {
        	writer2.write(str + "\n");
        }
        writer2.close();*/
        

	
	}	

}

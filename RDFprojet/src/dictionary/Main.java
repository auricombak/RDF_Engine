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
        QueryFactory factory = new QueryFactory(mModel);
        
       				
        //ArrayList<Query> aq = factory.loadFromFile("../RDFprojet/queries/Q_4_location_nationality_gender_type.queryset");
		
		long startTime = System.currentTimeMillis();

		mIndex.writeIo();
		int x = 0;
		factory.loadFromFolder("../RDFprojet/queries");
		
	    long stopTime = System.currentTimeMillis();
	    
	    long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime + " Milliseconds");
			
	}	

}

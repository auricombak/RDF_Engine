package dictionary;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        boolean verbose = false, exportResults = false, exportStats = false, workloadTime = false;
        if(args.length != 2) {
            System.out.println("At least 3 arguments ");
        }
        String queries = args[0];
        String data = args[1];
        String output = args[2];


        if(args[3].equals("-export_results")) exportResults = true;
        if(args[4].equals("-export_stats")) exportStats = true;
        if(args[5].equals("-workload_time")) workloadTime = true;
	
		// TODO Auto-generated method stub
		RDFRawParser parser = new RDFRawParser();
		parser.parse(data);
		
        Dictionary mDictionary = new Dictionary(parser.getList());
        Index mIndex = new Index(mDictionary, parser.getList());	
        Model mModel = new Model(mIndex, mDictionary);
        String aggFileName = String.valueOf(output + "resultats.txt");
        FileWriter fstream = new FileWriter(aggFileName);
        BufferedWriter out = new BufferedWriter(fstream);
        
        String aggFileName3 = String.valueOf(output + "export_stats.txt");
        FileWriter fstream3 = new FileWriter(aggFileName3);
        BufferedWriter out3 = new BufferedWriter(fstream3);
        
        QueryFactory factory = new QueryFactory();
        
       				
        int x = 0;
        long startTime = System.currentTimeMillis();
        for(ArrayList<Query> aq : factory.loadFromFolder(queries)) {
            x++;
            int i=0;
            for(Query q : aq) {
                QueryExecutioner qe = new QueryExecutioner(q, mModel);
                for(String s: qe.execute()){
                    if (exportResults)
                    out.write("Requete n° : "+ x + s + "\n");
                    if (exportStats) {
                        out3.write(qe.getEvalOrdre().toString() + "\n");
                    }
                }

            }
        }

		
	    long stopTime = System.currentTimeMillis();
	    
	    long elapsedTime = stopTime - startTime;


        if(workloadTime) {
            String aggFileName2 = String.valueOf(output + "exec_time.txt");
            FileWriter fstream2 = new FileWriter(aggFileName2);
            BufferedWriter out2 = new BufferedWriter(fstream2);
            System.out.println(elapsedTime + " Milliseconds");
            out2.write(elapsedTime + " Milliseconds");

}
			
	}	

}

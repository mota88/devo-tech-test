package devo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

/** 
* <h1>tf-idf calculator</h1> 
* This  program computes the tf-idf of a given set of words
* for all the .txt documents in a given directory. It shows
* the names and tf-idf index of the n documents with higher
* tf-idf, where n is given. If documents are added to the
* directory, the program starts refreshing the results
* every p seconds including the new documents, where p
* is given. In this class we run the directorywatcher
* and read all documents. The class Tfidf computes the
* index for all the documents and class Delay is used to
* control the refreshing option by delaying the updates.
* <p>
* <b>Note:</b> The program can be improved in many ways,
* especially in terms of controlling exceptions and parsing
* correctly the documents. When one term is not found in any
* document, results will contain NaN; this is easy to solve,
* as explained in the comments of Tfidf.idf
* 
* The time complexity of the program is O(n^2).
* The space complexity of the program is O(n^2).
* 
* @author  Eduardo Mota
*/
public class DirectoryWatcher {
	
	/**
     * For the given directory, first filter files that have extension ".txt".
     * Next, get the list of files and read all of them using FileUtils.
     * Clean the text obtained from some special characters and then split
     * each word.
     * 
     * @param path			A string containing the directory to read
     * @return documents	A List<List<String>> containing all the words
     * 						obtained in all the documents and their titles
     */
	public static List<List<String>> readFiles(String path) throws IOException {
    	// filter by extension to exclude other types of files
		FilenameFilter filter = new FilenameFilter() {
    	    public boolean accept(File dir, String name) {
    	        return name.endsWith(".txt");
    	    }
    	};
    	// obtain the list of files
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles(filter);
    	// create objects to store information
    	List<List<String>> documents = new ArrayList<List<String>>();
    	List<String> names = new ArrayList<String>();
    	// loop to read each document and split by words
    	for (int i = 0; i < listOfFiles.length; i++) {
    		List<String> doc = new ArrayList<String>();
    	    File file = listOfFiles[i];
    	    // store names of files
    	    names.add(file.getName());
    	    // read document using Apache Commons IO
    	    doc = FileUtils.readLines(file, StandardCharsets.UTF_8);
    	    List<String> docClean = new ArrayList<String>();
    	    // remove certain special characters from the text
    	    for (String s : doc) {
    	        docClean.add(s.replaceAll("[.,;?!]", ""));
    	    }
    	    String[] words = null;
    	    // split by words, assuming they are separated by space
    	    for(String word : docClean) {
    	        words = word.split(" ");
    	    }
    	    // put together all words as a list and add it to the list of lists 
    	    doc = Arrays.asList(words);
    	    documents.add(doc);
    	}
    	// add names after all documents
    	documents.add(names);
	    return documents;
    }
	
	/**
     * This is the method that controls all the actions of the program. It receives
     * all the arguments needed, creates the necessary objects and calls each of
     * the methods to perform the different computations and tasks. It also contains
     * the WatchService process, which audits the changes in the given directory
     * in order to refresh the results. 
     * 
     * @param args	It contains the path of the directory (args[0]), the number of
     * 				documents to be shown with their tfidf index (args[1]), the
     * 				refreshing period of the results (args[2]) and the terms to be
     * 				used for the computation (args[3] onwards).  
     */
	public static synchronized void main(String[] args) throws IOException, InterruptedException {
		if (args.length >= 4) {
			// get the arguments
			String dir = args[0];
        	int n = Integer.parseInt(args[1]);
        	int p = Integer.parseInt(args[2]);
        	String words[] = new String[args.length - 3];
        	for (int i = 3; i < args.length; i++) {
        		words[i-3] = args[i];
        	}
        	// read files for the given directory    		
    		List<List<String>> documents = DirectoryWatcher.readFiles(dir);
    		
    		Queue<Tfidf> docsQueue = new PriorityQueue<>();
    		// calls method that computes the tfidf index
    		docsQueue = Tfidf.analyzeDocuments(documents, words);
    		// calls method that prints the top results
    		docsQueue = Tfidf.printResults(n, docsQueue);
    		// watch service to control the creation of entries in the directory
    		WatchService ws = FileSystems.getDefault().newWatchService();
    		Path path = Paths.get(dir);
    		// only register creation of new files
    		path.register(ws,StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            while ((key = ws.take()) != null) {
            	// new file's event is handled here
                for (WatchEvent<?> event : key.pollEvents()) {
                	// read and analyze documents again to include the new
                    documents = DirectoryWatcher.readFiles(dir);
                	docsQueue = Tfidf.analyzeDocuments(documents, words);
                	// call Delay method to delay next step p seconds
                	Delay d = new Delay();
            	    d.delay(p, TimeUnit.SECONDS);
            	    // refresh the results
                	docsQueue = Tfidf.printResults(n, docsQueue);
                }
                key.reset();
            }
        }
        else {
        	System.out.println("At least four arguments expected: D, N, P and TT.");
        }
    }
}
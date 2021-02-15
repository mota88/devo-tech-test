package devo;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Tfidf implements Comparable<Tfidf> {
    
	private double tfidf;
    private String docName;

    public Tfidf() {
        tfidf = 0.0;
        docName = "NoName";
    }
    
    public Tfidf(double tfidf, String docName) {
        this.tfidf = tfidf;
        this.docName = docName;
    }
    
    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }
    
    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public int compareTo(Tfidf o) {
        return o.tfidf > this.tfidf ? 1 : -1;
    }
    
    @Override
    public String toString() {
        return this.docName + " " + this.tfidf;
    }
	
	/**
	 * Computes the tf for a given document and a given word, following
	 * the formulation given in the wikipedia entry of Tf–idf
	 * 
     * @param doc 					List of strings containing the text of a document
     * @param term					String with the word to be measured
     * @return count / doc.size()	Double result of dividing the number of times that
     * 								term appears in doc by the total number of words in doc
     */
    public static double tf(List<String> doc, String term) {
        double count = 0;
        // traverse document word by word
        for (String word : doc) {
        	// if word found, increment count
            if (term.equalsIgnoreCase(word))
                count++;
        }
        return count / doc.size();
    }

    /**
	 * Computes the idf for a given set of documents and a given word,
	 * following the formulation given in the wikipedia entry of Tf–idf.
	 * As explained in the wiki, the formula might be modified to avoid 
	 * a division by zero, which may happen if the word is not found
	 * 
     * @param docs 		List of lists of strings containing the text of all documents
     * @param term		String with the word to be measured
     * @return result	Double containing the result of the log10 of dividing the total
     * 					number of docs by the number of times that term appears in docs
     */
    public static double idf(List<List<String>> docs, String term) {
        double count = 0;
        double result;
        // traverse documents document by document
        for (List<String> doc : docs) {
        	// traverse document word by word
            for (String word : doc) {
            	// if word found, increment count and pass to the next document
                if (term.equalsIgnoreCase(word)) {
                    count++;
                    break;
                }
            }
        }
        /*
         * Compute formula of the idf. It could be used instead
         * Math.log10(docs.size() / 1 + count), avoiding division by zero when
         * the word is not found
         */
        result = Math.log10(docs.size() / count);
        return result;
    }

    /**
     * Computes the td-idf of a word in a document inside a sets of documents.
     * This is just the product of the td and the idf.
     * 
     * @param doc  								List of strings containing the text 
     * 											of one document
     * @param docs 								List of lists of strings containing 
     * 											the text of all documents
     * @param term 								String with the term to be computed
     * @return tf(doc, term) * idf(docs, term)	Double with the TF-IDF of term
     */
    public static double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }
    
    /**
     * Extends the computation of the tf-idf for several terms, adding the 
     * tf/idf for each term. Use PriorityQueue of type Tfidf to store each
     * entry as docName + tf-idf. The queue processes the elements that
     * enter based on priority, i.e, the priority queue is ordered according
     * to the comparator provided at construction time
     * 
     * @param docs		List of lists of strings containing the text of all documents
     * @param words 		Array of strings containing all the terms to be computed
     * @return docsQueue	PriorityQueue containing the names of documents along with
     * 						their tf-idf index, keeping the order
     */
    public static Queue<Tfidf> analyzeDocuments(List<List<String>> docs, String[] words) {
    	Queue<Tfidf> docsQueue = new PriorityQueue<>();
    	// create some objects to reorganize information
    	List<List<String>> documents = new ArrayList<List<String>>();
    	List<String> names = new ArrayList<String>();
    	int size = docs.size() - 1;
    	// get names of documents, stored at the end of docs
    	names = docs.get(size);
    	/* traverse docs copying its info in documents without
    	 * the last element which contains the names
    	 */
    	int i = 0;
    	for (List<String> doc : docs) {
    		// when i == size the loop is in the last step, do not copy
    		if (i != size) {
    			documents.add(doc);
    			i++;
    		}
    	}
        double calcDoc;
        double calcTotal;
        i = 0;
        // traverse documents document by document
        for (List<String> doc : documents) {
	        calcDoc = (double) 0;
	        calcTotal = (double) 0;
	        Tfidf tfidf = new Tfidf();
	        // traverse words term by term
	        for(String word : words) {
	        	// for the current doc, compute tf-idf for each word
	        	calcDoc = tfIdf(doc, documents, word);
	        	// total tf-idf for all terms of the current document
	        	calcTotal = calcTotal + calcDoc;
	        }
	        // set name and tf-idf
	        tfidf.setDocName(names.get(i));
	        tfidf.setTfidf(calcTotal);
	        // add element tfidf to PriorityQueue - priority established by comparing the tfidf
	        docsQueue.add(tfidf);
	        i++;
        }
        return docsQueue;
    }
    
    /**
     * Prints the given number of documents with higher tf-idf. Due to the way
     * PriorityQueues are accessed in order to print their elements, we need to
     * copy each element in a different PriorityQueue before polling it. The
     * PriorityQueue is returned by the method.  
     * 
     * @param n					Integer containing the number of documents to be shown
     * @param docsQueue 		PriorityQueue containing all documents names along with their tf-idf
     * @return keepDocsQueue	PriorityQueue containing the same information as docsQueue before
     * 							the method is executed
     */
    public static Queue<Tfidf> printResults(int n, Queue<Tfidf> docsQueue) {
    	Queue<Tfidf> keepDocsQueue = new PriorityQueue<>();
    	System.out.println("Top " + String.valueOf(n) + " documents:");
        for(int j = 1; j <= n; j++) {
        	// peeking "shows" the first element without deleting, no access to the second
        	keepDocsQueue.add(docsQueue.peek());
        	// polling "shows" the first element and deletes it, giving access to the next one in the queue
        	System.out.println(docsQueue.poll());
        }
        return keepDocsQueue;
    }
}
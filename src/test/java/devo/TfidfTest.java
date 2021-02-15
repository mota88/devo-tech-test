package devo;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.Assert;
import org.junit.Test;


public class TfidfTest {
	
    private List<String> doc1 = Arrays.asList("Esto", "es", "una", "una", "prueba");
    private List<String> doc2 = Arrays.asList("Esto", "es", "otro", "otro", "ejemplo", "ejemplo", "ejemplo");
    private List<String> names = Arrays.asList("test1.txt", "test2.txt");
    private List<List<String>> docs = Arrays.asList(doc1, doc2, names);
    private List<List<String>> documents = Arrays.asList(doc1, doc2);
    private String term1 = "Esto";
    private String term2 = "ejemplo";
    private String[] terms = new String[] {term1, term2};

    // ensure correct tf is returned
    @Test
    public void givenTerm1_testTf() {
    	double expectedTfDoc1 = (double) 1 / 5;
    	double expectedTfDoc2 = (double) 1 / 7;
    	Assert.assertTrue(Tfidf.tf(doc1, term1) == expectedTfDoc1);
    	Assert.assertTrue(Tfidf.tf(doc2, term1) == expectedTfDoc2);
    }
    
    @Test
    public void givenTerm2_testTf() {
    	double expectedTfDoc1 = (double) 0 / 5;
    	double expectedTfDoc2 = (double) 3 / 7;
    	Assert.assertTrue(Tfidf.tf(doc1, term2) == expectedTfDoc1);
    	Assert.assertTrue(Tfidf.tf(doc2, term2) == expectedTfDoc2);
    }
    
    // ensure correct idf is returned
    @Test
    public void givenTerm1_testIdf() {
    	double expectedIdfDocs = (double) Math.log10(2 / 2);
    	Assert.assertTrue(Tfidf.idf(documents, term1) == expectedIdfDocs);
    }
    
    @Test
    public void givenTerm2_testIdf() {
    	double expectedIdfDocs = (double) Math.log10(2 / 1);
    	Assert.assertTrue(Tfidf.idf(documents, term2) == expectedIdfDocs);
    }
    
    // ensure correct tfidf is returned
    @Test
    public void givenTerm1_testTfidf() {
    	double expectedTfidfDoc1 = (double) 1 / 5 * Math.log10(2 / 2);
    	double expectedTfidfDoc2 = (double) 1 / 7 * Math.log10(2 / 2);
    	Assert.assertTrue(Tfidf.tfIdf(doc1, documents, term1) == expectedTfidfDoc1);
    	Assert.assertTrue(Tfidf.tfIdf(doc2, documents, term1) == expectedTfidfDoc2);
    }
    
    @Test
    public void givenTerm2_testTfidf() {
    	double expectedTfidfDoc1 = (double) 0 / 5 * Math.log10(2 / 1);
    	double expectedTfidfDoc2 = (double) 3 / 7 * Math.log10(2 / 1);
    	Assert.assertTrue(Tfidf.tfIdf(doc1, documents, term2) == expectedTfidfDoc1);
    	Assert.assertTrue(Tfidf.tfIdf(doc2, documents, term2) == expectedTfidfDoc2);
    }
    
    // ensure correct tfidf for several terms is returned
    @Test
    public void givenDocuments_testAnalyzeDocuments() {
    	double expectedTfidfDoc1Term1 = (double) 1 / 5 * Math.log10(2 / 2);
    	double expectedTfidfDoc2Term1 = (double) 1 / 7 * Math.log10(2 / 2);
    	double expectedTfidfDoc1Term2 = (double) 0 / 5 * Math.log10(2 / 1);
    	double expectedTfidfDoc2Term2 = (double) 3 / 7 * Math.log10(2 / 1);
    	Queue<Tfidf> docsQueue = new PriorityQueue<>();
    	/*docsQueue = Tfidf.analyzeDocuments(docs, terms);
    	Assert.assertEquals(docsQueue.poll().toString(), "test2.txt" 
    	+ String.valueOf(expectedTfidfDoc2Term1 + expectedTfidfDoc2Term2));
    	Assert.assertEquals(docsQueue.poll().toString(), "test1.txt" 
    	+ String.valueOf(expectedTfidfDoc1Term1 + expectedTfidfDoc1Term2));*/
    }
}
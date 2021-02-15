package devo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class DirectoryWatcherTest {
	
	private List<String> doc1 = Arrays.asList("Esto", "es", "una", "una", "prueba");
    private List<String> doc2 = Arrays.asList("Esto", "es", "otro", "otro", "ejemplo", "ejemplo", "ejemplo");
    private List<String> names = Arrays.asList("test1.txt", "test2.txt");
    private List<List<String>> documents = Arrays.asList(doc1, doc2, names);
	
	// ensure correct words and titles are returned when reading files
    @Test
    public void givenDir_testReadFiles() throws IOException {
    	Assert.assertEquals(DirectoryWatcher.readFiles(System.getProperty("user.dir") 
    			+ "/src/test/java/devo"), documents);
    }

}
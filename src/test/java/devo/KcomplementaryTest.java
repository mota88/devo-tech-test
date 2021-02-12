package devo;

import org.junit.Assert;
import org.junit.Test;

public class KcomplementaryTest {	
	
	private int[] arr1 = {7, -2, 4, -4, 2, 2, -2, 10, -12};
	private int[] arr2 = {0, -2, 4, -6, 8, -10};
	private int[] arr3 = {1, 2};
	private int[] arr4 = {0, -1, -2, -3, -4, -5};
	private int k1 = 6;
	private int k2 = 8;
	private int k3 = 0;
	private int k4 = 100;
    
    // ensure handling of arrays that contain k-complementary pairs
    @Test
    public void givenArray1_testCheckPairs() {
        StringBuffer sb1 = new StringBuffer("(-4,10), (2,4)");
    	StringBuffer sb2 = Kcomplementary.checkPairs(arr1, k1);
        Assert.assertTrue(sb1.toString().equals(sb2.toString()));
    }
    
    @Test
    public void givenArray2_testCheckPairs() {
        StringBuffer sb1 = new StringBuffer("(0,8), (4,4)");
    	StringBuffer sb2 = Kcomplementary.checkPairs(arr2, k2);
        Assert.assertTrue(sb1.toString().equals(sb2.toString()));
    }
    
    // ensure handling of arrays that do not contain k-complementary pairs
    @Test
    public void givenArray3_testCheckPairs() {
        StringBuffer sb1 = new StringBuffer("");
    	StringBuffer sb2 = Kcomplementary.checkPairs(arr3, k3);
        Assert.assertTrue(sb1.toString().equals(sb2.toString()));
    }
    
    @Test
    public void givenArray4_testCheckPairs() {
        StringBuffer sb1 = new StringBuffer("");
    	StringBuffer sb2 = Kcomplementary.checkPairs(arr4, k4);
        Assert.assertTrue(sb1.toString().equals(sb2.toString()));
    }
}
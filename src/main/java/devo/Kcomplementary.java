package devo;

import java.util.Arrays;

/** 
* <h1>K-complementary pairs</h1> 
* This  program looks for k-complementary pairs in a given 
* array of integers and stores them in a string buffer.
* Given an array arr, the pair (i,j) is k-complementary
* if k = arr[i] + arr[j].
* <p>
* <b>Note:</b> The program counts pairs without repetition,
* i.e., the pair (i,j) is stored just once, although the pair
* (j,i) is also k-complementary.
* 
* The average time complexity of the program is O(n log(n)),
* although the worst case is O(n^2).
* The space complexity of the program is O(n)
* 
* @author  Eduardo Mota
*/
public class Kcomplementary {
	
	/**
     * By sorting the given array of integers, we can traverse
     * it from head and tail inwards. At each step, we check if
     * the sum of the pair (i,j) is equal to k, storing the pair
     * (i,j) if true. Then, we move 'inwards' the values of the
     * indices of the loop according to the  current result of
     * the sum arr[i] + arr[j]: if it's equal to k both move inwards,
     * if it's less than k only the smallest index moves inwards and
     * if it's greater than k only the largest index moves inwards.
     * This searching process is faster than simply traversing the
     * array from start to end.  
     * 
     * @param arr	The array of integers
     * @param k     The int containing the value for the sum
     * @return sb   A StringBuffer containing all the k-complementary
     * 				pairs of arr for the given k
     */
    public static StringBuffer checkPairs(int arr[], int k) {
    	StringBuffer sb = new StringBuffer();
    	// implementing a more efficient sorting method could improve time complexity
    	Arrays.sort(arr);
    	// indices to traverse the array
    	int i = 0;
        int j = arr.length - 1;
        while (i <= j) {
            if (arr[i] + arr[j] == k) {
            	// store the k-complementary pair in the format "(i,j), "
                sb.append("(" + arr[i] + "," + arr[j] + ")" + ", ");
                // since the array is ordered, we can jump to the next two numbers inwards
                i++;
                j--;
            }
            else if ((arr[i] + arr[j]) < k) {
            	/* sum less than k, do not decrease right index, sum would be even smaller next time
            	 * only increase left index -> sum will be at least the same in the next step
            	 */
                i++;
            }
            else {
            	/* sum larger than k, do not increase left index, sum would be even larger next time
            	 * only decrease right index -> sum will be at most the same in the next step
            	 */
                j--;
            }
        }
        // if there is at least one pair stored, we delete the last two characters: ", "
        if (sb.length() > 0) {
        	sb.setLength(sb.length() - 2);
        }
        return sb;
    }
    public static void main(String[] args) {
        if (args.length >= 2) {
        	int k = Integer.parseInt(args[0]);
        	int arr[] = new int[args.length - 1];
        	for (int i = 1; i < args.length; i++) {
        		arr[i-1] = Integer.parseInt(args[i]);
        	}
        	StringBuffer sb = checkPairs(arr, k);
        	if (sb.length() > 0) {
        		System.out.println(sb + " are " + String.valueOf(k) + "-complementary pairs.");
            }
    		else {
    			System.out.println("There are no " + String.valueOf(k) 
    			+ "-complementary pairs in the given input");
    		}
        }
        else {
        	System.out.println("At least two arguments expected.");
        }
    }
}
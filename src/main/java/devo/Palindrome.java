package devo;

/** 
* <h1>Palindrome</h1> 
* This  program computes recursively computes 
* whether a given string of characters forms a palindrome
* or not.
* <p>
* <b>Note:</b> The program deals with strings that contain
* the special characters , ; : and white spaces,
* but it does not deal with tildes; this can be improved
* easily using the method stripAccents from the package
* org.apache.commons.lang3.StringUtils
* 
* The time complexity of the program is O(n)
* The space complexity of the program is O(n)
* 
* @author  Eduardo Mota
*/
public class Palindrome {
    
    /**
     * Removes special characters from the given string 
     * and calls the recursive method recursivePalindrome
     * 
     * @param str  The string to be checked
     * @return     The result of the boolean method recursivePalindrome()
     */
    public boolean isPalindrome(String str) {
        // removes , ; : and white spaces and convert to lower case
        String cleanStr = str.replaceAll("[,;:\\s+]", "").toLowerCase();
        // invokes recursive method passing initial and final positions
        return recursivePalindrome(cleanStr, 0, cleanStr.length() - 1);
    }
    
    /**
     * Computes recursively if a given string is a palindrome by
     * comparing inwards each pair of characters in the string 
     * 
     * @param str    The string to be checked
     * @param left   The int containing the position of the string
     *               to be checked from the left
     * @param right  The int containing the position of the string
     *               to be checked from the right
     * @return       <code>true</code> if the given string is
     *               a palindrome; 
     *               <code>false</code> otherwise.
     */
    private boolean recursivePalindrome(String str, int left, int right) {
        // when the string has length 0, it is a palindrome
        if (left == right)
            return true;
        // when two corresponding characters are not equal, it is not a palindrome
        if ((str.charAt(left)) != (str.charAt(right)))
            return false;
        /* if there are more characters left to check we do it by
         * calling again the method, passing the next two positions
         * one step to the center of the string
         */
        if (left < right + 1) {
            return recursivePalindrome(str, left + 1, right - 1);
        }
        // default return, never occurs
        return false;
    }
}
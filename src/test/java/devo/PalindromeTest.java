package devo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PalindromeTest {

private Palindrome palindrome;
	
	@Before                                        
    public void setUp() throws Exception {
		palindrome = new Palindrome();
    }

    private String[] words = {
            "Ana",
            "arenera",
            "aviva",
            "Kayak",
            "Menem",
            "radar",
            "reconocer"
    };

    private String[] sentences = {
            "Arriba la birra",
            "Anula la Luna",
            "Eva, ya hay ave",
            "La tele letal",
            "Ojo; ese oso, ese; ojo"
    };

    // ensure handling of words that are palindromes
    @Test
    public void givenWord_testIsPalindrome() {
        for (String word : words)
            Assert.assertTrue(palindrome.isPalindrome(word));
    }

    // ensure handling of sentences that are palindromes
    @Test
    public void givenSentence_testIsPalindrome() {
        for (String sentence : sentences)
            Assert.assertTrue(palindrome.isPalindrome(sentence));
    }

}
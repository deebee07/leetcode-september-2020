/*
Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies of the substring together. You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.

 

Example 1:

Input: "abab"
Output: True
Explanation: It's the substring "ab" twice.
Example 2:

Input: "aba"
Output: False
Example 3:

Input: "abcabcabcabc"
Output: True
Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
https://leetcode.com/problems/repeated-substring-pattern/solution/
 */

// Solution 1: Time complexity: \mathcal{O}(N^2) because we use greedy regex pattern. Once we have a +, the pattern is greedy.
import java.util.regex.Pattern;

class Solution {
    public boolean repeatedSubstringPattern(String s) {
        Pattern pat = Pattern.compile("^(.+)\\1+$");
        return pat.matcher(s).matches();
    }
}


// Solution 2: Concatenation
/*

Time complexity: \mathcal{O}(N^2) because of the way in and contains are implemented.

Space complexity: \mathcal{O}(N)O(N), the space is implicitly used to keep s + s string.


*/
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).substring(1, 2 * s.length() - 1).contains(s);
    }
}

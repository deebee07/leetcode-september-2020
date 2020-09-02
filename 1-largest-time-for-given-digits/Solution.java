// Largest Time for Given Digits

// Given an array of 4 digits, return the largest 24 hour time that can be made.

// The smallest 24 hour time is 00:00, and the largest is 23:59.  Starting from 00:00, a time is larger if more time has elapsed since midnight.

// Return the answer as a string of length 5.  If no valid time can be made, return an empty string.

 

// Example 1:

// Input: [1,2,3,4]
// Output: "23:41"
// Example 2:

// Input: [5,5,5,5]
// Output: ""
 

// Note:

// A.length == 4
// 0 <= A[i] <= 9


class Solution {


    public String largestTimeFromDigits(int[] A) {
        for(int h = 23; h >= 0; h--) {
            for(int m = 59; m >= 0; m--) {
                
                boolean flag = true;
                int[] count = new int[10];
                
                count[h < 10 ? 0 : h / 10]++;
                count[h < 10 ? h : h % 10]++;
                count[m < 10 ? 0 : m / 10]++;
                count[m < 10 ? m : m % 10]++;                

                
                for(int e : A) {
                    if(--count[e] < 0) {
                        flag = false;
                        break;
                    }
                }
                
                if(flag) return String.format("%02d:%02d", h, m);
            }
        }
        
        return "";
    }
    
    

}
/*
https://leetcode.com/problems/contains-duplicate-iii/solution/
Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.

Example 1:

Input: nums = [1,2,3,1], k = 3, t = 0
Output: true
Example 2:

Input: nums = [1,0,1,1], k = 1, t = 2
Output: true
Example 3:

Input: nums = [1,5,9,1,5,9], k = 2, t = 3
Output: false


*/
/*
Approach #2 (Binary Search Tree) [Accepted]
Intuition

If elements in the window are maintained in sorted order, we can apply binary search twice to check if Condition 2 is satisfied.

By utilizing self-balancing Binary Search Tree, one can keep the window ordered at all times with logarithmic time insert and delete.

Algorithm

The real bottleneck of Approach #1 is due to all elements in the sliding window are being scanned to check if Condition 2 is satisfied. Could we do better?

If elements in the window are in sorted order, we can apply Binary Search twice to search for the two boundaries x+tx+t and x-tx−t for each element xx.

Unfortunately, the window is unsorted. A common mistake here is attempting to maintain a sorted array. Although searching in a sorted array costs only logarithmic time, keeping the order of the elements after insert and delete operation is not as efficient. Imagine you have a sorted array with kk elements and you are adding a new item xx. Even if you can find the correct position in O(\log k)O(logk) time, you still need O(k)O(k) time to insert xx into the sorted array. The reason is that you need to shift all elements after the insert position one step backward. The same reasoning applies to removal as well. After removing an item from position ii, you need to shift all elements after ii one step forward. Thus, we gain nothing in speed compared to the naive linear search approach above.

To gain an actual speedup, we need a dynamic data structure that supports faster insert, search and delete. Self-balancing Binary Search Tree (BST) is the right data structure. The term Self-balancing means the tree automatically keeps its height small after arbitrary insert and delete operations. Why does self-balancing matter? That is because most operations on a BST take time directly proportional to the height of the tree. Take a look at the following non-balanced BST which is skewed to the left:

            6
           /
          5
         /
        4
       /
      3
     /
    2
   /
  1
Figure 1. A non-balanced BST that is skewed to the left.

Searching in the above BST degrades to linear time, which is like searching in a linked list. Now compare to the BST below which is balanced:

          4
        /   \
       2     6
      / \   /
     1   3  5
Figure 2. A balanced BST.

Assume that nn is the total number of nodes in the tree, a balanced binary tree maintains its height in the order of h = \log nh=logn. Thus it supports O(h) = O(\log n)O(h)=O(logn) time for each of insert, search and delete operations.

Here is the entire algorithm in pseudocode:

Initialize an empty BST set
Loop through the array, for each element xx
Find the smallest element ss in set that is greater than or equal to xx, return true if s - x \leq ts−x≤t
Find the greatest element gg in set that is smaller than or equal to xx, return true if x - g \leq tx−g≤t
Put xx in set
If the size of the set is larger than kk, remove the oldest item.
Return false
One may consider the smallest element ss that is greater or equal to xx as the successor of xx in the BST, as in: "What is the next greater value of xx?". Similarly, we consider the greatest element gg that is smaller or equal to xx as the predecessor of xx in the BST, as in: "What is the previous smaller value of xx?". These two values ss and gg are the two closest neighbors from xx. Thus by checking the distance from them to xx, we can conclude if Condition 2 is satisfied.


Complexity Analysis

Time complexity : O(n \log (\min(n,k)))O(nlog(min(n,k))). We iterate through the array of size nn. For each iteration, it costs O(\log \min(k, n))O(logmin(k,n)) time (search, insert or delete) in the BST, since the size of the BST is upper bounded by both kk and nn.

Space complexity : O(\min(n,k))O(min(n,k)). Space is dominated by the size of the BST, which is upper bounded by both kk and nn.

Note

When the array's elements and tt's value are large, they can cause overflow in arithmetic operation. Consider using a larger size data type instead, such as long.

C++'s std::set, std::set::upper_bound and std::set::lower_bound are equivalent to Java's TreeSet, TreeSet::ceiling and TreeSet::floor, respectively. Python does not provide a Self-balancing BST through its library.




*/

class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; ++i) {
            // Find the successor of current element
            Integer s = set.ceiling(nums[i]);
            if (s != null && s <= nums[i] + t) return true;

            // Find the predecessor of current element
            Integer g = set.floor(nums[i]);
            if (g != null && nums[i] <= g + t) return true;

            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }
}

// Solution 2
// class Solution {
//     public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
//         for (int i = 0; i < nums.length; i++) {
//             for (int j = Math.max(i - k, 0); j < i; j++) {
//                 if (Math.abs(nums[i] - nums[j]) <= t) return true;
//             }
//         }
//         return false;
//     }
// }
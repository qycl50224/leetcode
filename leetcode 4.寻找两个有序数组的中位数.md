## leetcode 4.寻找两个有序数组的中位数

题干

给定两个大小为 m 和 n 的**有序**数组 `nums1` 和 `nums2`。

请你找出这两个有序数组的**中位数**，并且要求算法的时间复杂度为 O(log(m + n))。

你可以假设 `nums1` 和 `nums2` 不会同时为空。

**示例 1:**

> nums1 = [1, 3]
> nums2 = [2]
>
> 则中位数是 2.0

**示例 2:**

> nums1 = [1, 2]
> nums2 = [3, 4]
>
> 则中位数是 (2 + 3)/2 = 2.5

### 一开始的想法

这道题没做出来，因为我对复杂度掌握还不好，无法从一个复杂度想到某种思想或算法，比如很多人看到这里的log复杂度就想到了二分，我虽然想到了二分，但是对如何实现一点头绪都没有

下面的解法是对官方解答的自我理解下的阐述，主要看注释吧

官方的思路是

先把中位数的定义搞清楚，也就是**两个大小相等的子集**，其中一个始终小于另外一个

从这里，不难想到我们要把这两个数组分成两半

但是又不能直接分成两半，除非我们进行组合排序，但这样复杂度就超了（我看答案前只思考到这里）

答案比我做的更多，他于是想道把给定的两个初始数组分成两半，分别用**i**和**j**去跟踪分界线的位置

并且，这两个数组的**左半边长度之和**等于两个数组总长度的一半，也就是`halfLen`

但是我们知道，总长度可以是奇数和偶数，分开讨论得话太麻烦了，于是他

令`halfLen = (m + n + 1) / 2`，这样的作用是

利用了计算机语言除法的特性，会自动忽略小数部分，如果两数组长度之和为奇数，则求出来的`halfLen`是左半边长度，左边比右边长1个单位（这个细节在代码中也会有所体现，就是直接返回`maxLeft`那里）

如果两数组长度之和是偶数，则没有任何影响。

来直观的看下，看懂这张图，考虑边界条件就很简单了

         left_part          |        right_part
    A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
    B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]

- 左边的最大值大于右边最小值，则需要继续循环
- 如果i或者j 等于0， 则左边的最大值就直接等于不为0那一方的最后一个 （j - 1）
- 如果i == m 或者 j == n， 右边的最小值就直接等于另一方的首个（i）

下面再看下实现复杂度的地方，也就是二分查找

代码很简单

用iMax and iMin 来确定 i 所属的范围，然后很轻易地得到中间值

我们只需要保持跟踪两个边界值，以及

在考虑边界条件的时候也要考虑到这两个边界值



```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        // 交换使长度较小的数组在前
        if (m > n) {
            int[] temp = nums1; nums1 = nums2; nums2 = temp;
            int tmp = m; m = n; n = tmp; 
        }
        // halfLen代表什么？
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        // 为什么是<=, 这其实是一个边界条件，因为如果nums1是空集，则根本无法进入这个while
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            // 为什么要使 i < iMax， 因为iMax一开始是m
            if (i < iMax && nums1[i] < nums2[j-1]) {
                iMin = i + 1;
            } else if (i > iMin && nums1[i-1] > nums2[j]) {
                iMax = i - 1;
            } else {
                // 进入到这里就代表不用再查找了
                int maxLeft = 0, minRight = 0;
                // 考虑边界条件0
                if (i == 0) {maxLeft = nums2[j-1];}
                else if (j == 0) {maxLeft = nums1[i-1];}
                else {maxLeft = Math.max(nums1[i-1], nums2[j-1]);}
                // 要把这个返回放在这里，因为如果往下走，又会考虑其他条件
                // 有可能会发生数组溢出
                if ((m + n) % 2 == 1) {return maxLeft;} 

                // 考虑边界条件m和n
                if (i == m) {minRight = nums2[j];}
                else if (j == n) {minRight = nums1[i];}
                else {minRight = Math.min(nums2[j], nums1[i]);}

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 21.0;
    }
}
```


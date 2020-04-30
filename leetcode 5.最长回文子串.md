### leetcode 5.最长回文子串

题干:

给定一个字符串 s，找到 s 中**最长**的**回文子串**。你可以假设 s 的最大长度为 1000。

示例 1：

>输入: "babad"
>输出: "bab"

注意: "aba" 也是一个有效答案。
示例 2：

>输入: "cbbd"
>输出: "bb"



#### 最开始的思路：

既然是要找最长的回文子串，那我就从最长的开始向短的找，找到一个复合的那就是他了，也就是令一个PalinLen 一开始等于给定字符串的长度，然后让他依次减小，然后一个窗口在给定字符串里面判断窗口中的子串是否回文

这样有一个缺点，如果给定的字符串很长，但是存在的最长回文子串很短，则效率很低

![image-20200430142154152](C:\Users\chen\AppData\Roaming\Typora\typora-user-images\image-20200430142154152.png)

结果如我所料

代码如下

```java
class Solution {
    public String longestPalindrome(String s) {
        int PalinLen = s.length();
        while (PalinLen > 0) {
            for(int i = 0; i <= s.length() - PalinLen; i++) {
                String subs = s.substring(i, i + PalinLen);
                // System.out.println(subs.length());
                // System.out.println(subs);
                if (isPalindrome(subs)) {
                    return subs;
                }
            }
            PalinLen --;
        }
        return "";
    }

    // 判断回文如果用栈实现则不用考虑边界条件
    public boolean isPalindrome(String s) {
        // 注意区分halfIndex and halfLen
        
        if (s.length() > 1) {
            int halfLen = (s.length() + 1) / 2;
            // System.out.println("halfLen: " + halfLen );
            for (int i = 0; i <= halfLen - 1; i++) {
                // System.out.println("i: " + s.charAt(i) + "s.length()-1-i: " + s.charAt(s.length()-1-i) );
                if (s.charAt(i) != s.charAt(s.length()-1-i)) {
                    return false;
                }
            }    
        }        
        return true;
    }
}
```

注意到上面的判断是否是回文串的方法

并没有用堆栈来实现，
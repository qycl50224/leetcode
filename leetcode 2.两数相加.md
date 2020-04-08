# leetcode 2.两数相加

 题目：

给出两个 **非空** 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 **逆序** 的方式存储的，并且它们的每个节点只能存储 **一位** 数字。

如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：

> 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
> 输出：7 -> 0 -> 8
> 原因：342 + 465 = 807





也就是说链表头是对应着数字的个位

我们需要考虑数字的位数

还要考虑进位（如何进位，进多少



对于两个链表同一位数上的两个数，相加大于等于10就进1，我们只需要计算他俩之和**对10取余**，





优化：

- 不同位数的两个数字相加，把相同位数相加之后，直接连到多的位数上面

难点：

- 要处理对链表进行实例化但是必须有一个值的问题，因为我一开始没有处理所以的结果老是比答案多了一个0在最高位上，由于实例化必须带上值，为了解决这个问题，我创建了一个`end`指针，如果实例化为1，则`end`指向`next`，如果实例化为0，则指向当前。
- 



最开始的思路：

从个位开始，满10进1，较短的数遍历完之后，把结果与较长的数的后部分相连（这种思路是错的，不能直接相连，因为会有连着进位的情况发生）

下面的代码并不能跑，只是便于我回想自己的思路

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(0);
        ListNode first = new ListNode(0);
        ListNode end = l3;
        first.next = l3;
        while(l1 != null && l2 != null) {
			// 这里v也有问题，没有考虑l3的值
            int v = l1.val + l2.val;
            if(v >= 10) {
                // 下面这里是一个问题，和上面那个结合起来看
                l3.val += v % 10;
                l3.next = new ListNode(1);
                end = l3.next;
            } else {
                l3.val += v;
                l3.next = new ListNode(0);
                end = l3;
                // if(l1.next != null && l2.next != null) {
                //     l3.next = new ListNode(0);
                // }
            }
            l1 = l1.next;
            l2 = l2.next;
            l3 = l3.next;
        }
        // System.out.println(l1);
        // System.out.println(l2);
        // System.out.println(l3);
        // while(first.next != null){
        //     System.out.println(first.val);
        //     first = first.next;
        // }
        
        //下面这里也有问题
        if(l1 == null && l2 != null) {
            l3.val += l2.val;
            l3.next = l2.next;
        } else if (l1 != null && l2 == null) {
            l3.val += l1.val;
            l3.next = l1.next;
        } else {
            l3 = end;
            l3.next = null;
            // System.out.println("end:" + end.val);
        }
        return first.next;
    }
}
```



后来的思路：

把两个链表转化为数字相加再转化为链表，我真是天才

结果。。。忘记考虑溢出了= =

于是回归原来的方法

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //get the sum in l1 and l2
        int i = 1;
        int j = 1;
        long sumOfL1 = 0;
        long sumOfL2 = 0;
        long sum = 0;
        int num;
        ListNode head = new ListNode(0);
        ListNode first = new ListNode(0);
        ListNode end = first;
        head.next = first;
        while(l1 != null) {
            sumOfL1 += l1.val * i;
            i *= 10;
            l1 = l1.next;
        }
        while(l2 != null) {
            sumOfL2 += l2.val * j;
            j *= 10;
            l2 = l2.next;
        }        
        //compute sum
        sum = sumOfL1 + sumOfL2;
        // get every number of sum 
        if(sum == 0) {
            return first;
        }
        while(sum != 0) {
            num = (int)sum % 10;
            sum /= 10;
            // if(first == null) {
            //     first = new ListNode(0);
            // }
            first.val = num;
            end = first;
            first.next = new ListNode(0);
            first = first.next;
        }

        if(end.next.val == 0){
            end.next = null;
        }
        return head.next;
    }
}
```





最后通过的代码：



```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(0);
        ListNode head = new ListNode(0);
        ListNode end = l3;
        head.next = l3;
        // 先把l1,l2都有的位数相加，共同最高位有可能向更高一位进1
        //留到后面的循环去解决
        while(l1 != null && l2 != null) {

            int v = l1.val + l2.val + l3.val;
            System.out.println("l3.val:" + l3.val);
            System.out.println("v:" + v);
            if(v >= 10) {

                l3.val = v % 10;
                l3.next = new ListNode(1);
                end = l3.next;
                System.out.println("end:" + end.val);
            } else {
                l3.val = v;
                l3.next = new ListNode(0);
                end = l3;
            }
            l1 = l1.next;
            l2 = l2.next;
            l3 = l3.next;
        }
        //下面这个循环是考虑到连续进位的情况，也就是+9999这种
        if(l1 == null && l2 != null) {
            while(l2 != null) {
                int v = l2.val + l3.val;
                if(v >= 10) {
                    l3.val = v % 10;
                    l3.next = new ListNode(1);
                    end = l3.next;
                    l3 = l3.next;
                    l2 = l2.next;    
                } else {
                    l3.val = v;
                    l3.next = l2.next;        
                    break;
                }
            }
        } else if (l1 != null && l2 == null) {
            while(l1 != null) {
                int v = l1.val + l3.val;
                if(v >= 10) {
                    l3.val = v % 10;
                    l3.next = new ListNode(1);
                    end = l3.next;
                    l3 = l3.next;
                    l1 = l1.next;
                } else {
                    l3.val = v;
                    System.out.println("l3:" + l3.val);
                    l3.next = l1.next;
                    break;
                }
            }
        } else {
            // 如果l1,l2都没有更高位了，由于我们实例化了一个0的值
            //所以要往前退一位，回到end
            l3 = end;
            l3.next = null;
        }
        return head.next;
    }
}
```

![image-20200408230728769](C:\Users\chen\AppData\Roaming\Typora\typora-user-images\image-20200408230728769.png)
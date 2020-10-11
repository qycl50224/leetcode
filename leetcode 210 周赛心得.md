# leetcode 210 周赛心得

我只过了前两题

第一题错了一次，对题目理解错误

第二题错了一次，因为没考虑输入为空

把我通过的代码贴出来和第一名的代码对比

```java
class Solution {
    public int maxDepth(String s) {
        s = helper(s);
        if (s.length() == 0) return 0;
        Stack<Character> stack = new Stack<>();
        stack.add(s.charAt(0));
        int max = 0;
        for (int i = 1;i < s.length(); i++) {
            max = Math.max(max, stack.size());
            if (s.charAt(i) == ')') {
                stack.pop();
            } else {
                stack.add('(');
            }
        }
        return max;
    }
    private String helper(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            String cur = s.substring(i,i+1);
            if (cur.equals("(") || cur.equals(")")) {
                ans+=cur;
                // System.out.println("ans:"+ans);
            }
        }
        return ans;
    }
}
```



```c++
class Solution {
public:
    int maxDepth(string s) {
        int mx = 0, cur = 0;
        for(char c :s){
            if(c == '(') cur++;
            else if(c == ')') cur--;
            mx = max(mx, cur);
        }
        return mx;
    }
};
```

1. 我先把输入字符转换成只剩括号的字符串
2. 用stack把左括号往里扔，碰到右括号就出队（题目说了输入都是符合规则的，不会出现第一个是右括号的情况）
3. stack的size代表着括号的嵌套深度，所以把他和最大值一直比较取最大值就行

下面再看下大神的操作

1. 没有栈！！！！直接用`cur`表示我的栈的size，出现右括号就-1，相当于我的出栈，出现左括号就+1，相当于入栈，我呆了
2. 没有去除字符！！！！仅仅用if来考虑  需要考虑的情况，也就是左右括号
3. 我人已经傻了



下面是第二题代码对照

```java
public int maximalNetworkRank(int n, int[][] roads) {
        if (roads.length == 0) {
            return 0;
        }
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int[] size = new int[100];
        for (int[] ar: roads) {
            int c1 = ar[0];
            int c2 = ar[1];
            if (!map.containsKey(c1)) {
                map.put(c1, new HashSet<>());
            }
            map.get(c1).add(c2);
            size[c1] = map.get(c1).size();
            if (!map.containsKey(c2)) {
                map.put(c2, new HashSet<>());
            }
            map.get(c2).add(c1);
            size[c2] = map.get(c2).size();
        }

        int maxSize = findMaxSize(size);
        List<Integer> maxcities = new LinkedList<>();

        for (int i = 0; i < size.length; i++) {
            if (size[i] == maxSize) {
                maxcities.add(i);
                size[i] = 0;
            }
        }
        int secSize = findMaxSize(size);
        List<Integer> seccities = new LinkedList<>();
        for (int i = 0; i < size.length; i++) {
            if (size[i] == secSize) {
                seccities.add(i);
                size[i] = 0;
            }
        }

        if (maxcities.size() > 1) {
            for (int i = 0; i < maxcities.size(); i++) {
                for (int j = i+1; j < maxcities.size(); j++) {
                    if (!map.get(maxcities.get(i)).contains(maxcities.get(j))) {
                        return map.get(maxcities.get(i)).size()*2;
                    }
                }
            }
            return map.get(maxcities.get(0)).size()*2-1;
        } else {
            for (int j = 0; j < seccities.size(); j++) {
                if (!map.get(maxcities.get(0)).contains(seccities.get(j))) {
                    return map.get(maxcities.get(0)).size()+map.get(seccities.get(j)).size();
                }
            }
            return map.get(maxcities.get(0)).size()+map.get(seccities.get(0)).size()-1;
        }


    }

    private int findMaxSize(int[] a) {
        int max = 0;
        for (int i: a) {
            max = Math.max(i,max);
        }
        return max;
    }
```

time：36ms 100%

space：40ＭＢ

我照着第一名的c++翻译的java

```java
class Solution {
    public int maximalNetworkRank(int n, int[][] roads) {
        boolean[][] a = new boolean[n][n];
        for (int[] tmp: roads) {
            a[tmp[0]][tmp[1]] = true;
            a[tmp[1]][tmp[0]] = true;
        }
        int mx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                int cnt = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    if (a[i][k]) cnt++;
                    if (a[j][k]) cnt++;
                }
                if (a[i][j]) cnt++;
                mx = Math.max(mx, cnt); 
            }
        }
        return mx;
    }
}
```

time：161ms  100%

space：39MB 100%



光代码行数对比就让我佩服的五体投地

看懂他的代码后总结如下

1. 这是个暴力法
2. 先确定i和j也就是两个不同的城市
3. 然后用k来遍历所有除了j和k的城市
4. 每出现相连情况就cnt++
5. 比较max和cnt取大

思路给我感觉就是很纯粹，一眼就把题目看透那种

学习了
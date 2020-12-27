# 吃苹果的最大数目 Maximum Number of Eaten Apples

level:meidum

### 思路：贪心（可用优先队列也可map

- c++ 的map是基于红黑树实现的，根据key的大小排序，我们令key为过期对应的day，用访问首个元素获取到的就是下一个会过期的苹果，map在这里等效于优先队列，甚至更快
- 先吃最容易过期的苹果

代码源自[吴自华](https://leetcode-cn.com/circle/discuss/KyAmtm/)，贴过来是为了学习他的写法

#### 收获

1. 函数内方法的写法 `auto add = [&](int i) {};`别忘了最后的分号
2. 获取map首个对象的key和val，key直接用 `begin()`，val可以通过用重载的 `[key]`来访问

```cpp
class Solution {
public:
    int eatenApples(vector<int>& apples, vector<int>& days) {
        int ans = 0;
        map<int, int> mp;
        auto add = [&](int i) {
            mp[i+days[i]] += apples[i];
        };
        auto eat = [&](int i){
            while (!mp.empty() && mp.begin()->first <= i) {
                mp.erase(mp.begin());
            }
            if (!mp.empty()) {
                ans++;
                int n = mp.begin()->first;
                mp[n]--;
                if (mp[n] == 0) mp.erase(mp.begin());
            }
        };
        for (int i = 0; i < days.size(); i++) {
            add(i);
            eat(i);
        }

        for (int i = days.size(); !mp.empty(); i++) {
            eat(i);
        }
        return ans;
    }
};
```


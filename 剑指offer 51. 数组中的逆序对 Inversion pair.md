# 剑指offer 51. 数组中的逆序对 Inversion pair

level：hard

### 思路一：归并排序（递归版

- 用全局变量记录ans，在递归中更新ans
- vector t用来存储临时的有序数组

> v 5 6 7 8 9      v代表该index的value
>
> p 0 1 2 3 4      p为二分后的左半边数组的index ，q为右半边的index
>
> ​						我们计算逆序对的表达式为 ans += mid - p
>
> ​						比如这里我们可以看到如果v[p] > v[q] 则 v[p], v[p+1],v[p+2],... 都与v[q]构成逆序对
>
> ​						所以ans += mid - p
>
> v 1 2 3 4 5
>
> q 5 6 7 8 9 
>
>    ↑
>
> mid





#### 复杂度：

- time: O(nlogn)

```cpp
class Solution {
public:
    vector<int> t; // 用来存储已知的临时的有序数
    int ans;
    int reversePairs(vector<int>& nums) {
		// 初始化vector t
        for (int i = 0; i < nums.size(); i++) {
            t.push_back(0);
        }
        ans = 0;
        mergeSort(nums, 0, nums.size());
        return ans;
    }
    
    // 参数为左闭右开 [l, r)
    void mergeSort(vector<int>& v, int l, int r) {
        if (r - l <= 1) return;
        int mid = l + ((r - l) >> 1);
        mergeSort(v, l, mid);
        mergeSort(v, mid, r);
        int p = l, q = mid, s = l;
        while (s < r) {
            if (p >= mid || (q < r && v[p] > v[q])) {
            t[s++] = v[q++];  // 先把有序的序列求出来放到t中，然后在后面的for循环更新原始数组
            ans += mid - p;  // 计算逆序对
            } else
            t[s++] = v[p++];
        }
        for (int i = l; i < r; ++i) v[i] = t[i];
    }
};
```

 

time:288ms 76%

space:46.2MB 30%
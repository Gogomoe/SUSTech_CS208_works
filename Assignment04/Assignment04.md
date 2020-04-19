# Assignment 4

11811712 江川

### 2

##### (a)

True

by kruskal algorithm, we choose the mininum edge each time and use it if it dong make circle.

because the edge cost is distinct and positive, for every $x < y$, it must have $x^2 < y^2$, so sorting by $ c^2 $ is as same as sort by $c$. so kruskal will pick same edges as T.

##### (b)

False

```plain
1->2 5
1->3 4
3->2 2
```

it will chose 3 <- 1 -> 2 by cost $c$. but choose 1 -> 3 -> 2 by $c^2$ 

### 8

sort all edges by it cost, because the cost are distinct , so the sorted sequence is unique.

then pick edges from min cost to max cost if the edge dont make a circle. the pick edges S is unique.

if a edge e not in S be picked, it must not a Mininum-Spanning-Tree, we can use the edge in S to replace.

so the MST is unique.

### 22

counterexample

```text
1-2 0
1-3 1
2-3 1
```

every edges is in T, but edge 1-3 + 2-3 cannot be a MST.
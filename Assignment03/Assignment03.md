# Assignment 2

11811712 江川

### 1

6 topological orderings

abcdef

abdcef

abdecf

adebcf

adbecf

adbcef

### 3

```plain
S <- the set of nodes with no income edge
repeat untial S is empty
	v <- poll a node from S
	delete v from G, record v
	if the neighbor u of v has no income edge, put u into S
if |recording| = |V|
	G is a DAG, print recording
otherwise
	G has circle, print nodes not in recording
```
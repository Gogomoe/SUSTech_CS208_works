# Assignment 1

11811712 江川

### 1

False，For this example

| man  |  1st  | 2nd|
|-----|-----|-----|
| X    | A    | B    |
| Y    | B    | A    |


| woman |  1st  | 2nd|
|-----|-----|-----|
| A    | Y    | X    |
| B    | X    | Y    |

X-A, Y-B are tow pairs. But A prefer Y and B prefer X.

### 2

True.

If (m,w) is not a pair, the match is not stable, m prefer w and w prefer m. So it must be true.

### 3

##### (b)

Network A : (1, 3)

Network B : (2, 4)

if S = (1, 3) , T = (2, 4) , there exists S' = (3, 1)

if S = (3, 1) ,  T = (2, 4) , there exists T' = (4, 2)

### 8

##### (b)

This is the true prefer list.

| man  |  1st  | 2nd| 3rd |
|-----|-----|-----|-----|
| X    |<font color="red"> B  </font> | A  | C |
| Y    |<font color="blue"> A </font> | B   | C |
| Z    | A   | B   |<font color="orange"> C </font>|

| woman |  1st  | 2nd| 3rd|
|-----|-----|-----|-----|
| A    | X   |<font color="blue"> Y    </font>| Z   |
| B    | Y   |<font color="red"> X   </font>| Z   |
| C    | X   | Y   |<font color="orange">  Z   </font>|

if A lie to change Z and Y, A  get better man X.

| man  |  1st  | 2nd| 3rd |
|-----|-----|-----|-----|
| X    | B   |<font color="red"> A  </font>| C |
| Y    | A  |<font color="blue"> B  </font> | C |
| Z    |A   | B   | <font color="orange">C </font>|

| woman |  1st  | 2nd| 3rd|
|-----|-----|-----|-----|
| A    |<font color="red"> X   </font>| **Z** | **Y** |
| B    |<font color="blue"> Y   </font>| X   | Z   |
| C    | X   | Y   |<font color="orange">  Z   </font>|


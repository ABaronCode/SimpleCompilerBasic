GOSUB FizzBuzz
GOSUB CollatzConjeecture
GOSUB ClassAverage

END

FizzBuzz:
FOR z = 0 to 100
	IF z/3 THEN PRINT "Fizz"
		ELSE IF z/5 THEN PRINT "Buzz"
		ELSE IF z/3 AND z/5 THEN PRINT "Fizz Buzz"
		ELSE PRINT A
NEXT A

RETURN

CollatzConjeecture:
INPUT "Collatz Conjecture, Please give number greater than 1: ", numIN
numberOfSteps = 0

while numIN = 1 endWhile
	IF numIN / 2 THEN numIN = numIN / 2
	ELSE numIN * 3
	numberOfSteps = numberOfSteps + 1
	endWhile:
	
RETURN

ClassAverage:

DATA "Bob", 60, 60, 70, 90, 100
READ nameOne$, a, b, c, d, e
avgBob = (a + b + c + d + e)/5
PRINT "Bob's average: ", avgBob

DATA "Sarah", 40, 70, 80, 100, 100, 100
READ nameTwo$, f, g, h, i, j, k
PavgSarah = (f, g, h, i, j, k)/6
PRINT "Sarah's average: ", avgBob


DATA "Frank", 10, 50, 35
READ nameThree, l, m, n

DATA "Jasmine" 25, 55, 79, 100, 21, 19
READ nameFour$, o, p, q, r, s, t
avgJasmine = (o + p + q + r + s + t)/6
PRINT "Jasmine's average: ", avgJasmine

DATA "Karen" 20, 39
READ  nameFive$, u, v
avgKaren = (u + v)/2
PRINT "Karen's average: ", avgKaren 

RETURN
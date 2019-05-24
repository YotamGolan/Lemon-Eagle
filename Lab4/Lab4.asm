##########################################################################
# Created by:  Golan, Yotam
#              yogolan
#              14 May 2019
#
# Assignment:  Lab 4:  Roman Numeral Conversion
#              CMPE 012, Computer Systems and Assembly Language
#              UC Santa Cruz, Spring 2019
# 
# Description: This program intakes ints from 0 - 10 and prints out the factorial. It rejects
# any other int outside the range
# 
# Notes:       This program is intended to be run from the MARS IDE.
# This program converts roman numerals to binary
# Loads the program argument input by user
# Then reads user input, it determines if all the provided characters are valid numerals
# Then it determines if the basic order is followed, with greatest to least and evaluates special circumstancs
# Then it prints out the input
# Then it sums up the total value by running through the numerals and determining if they are to be added or subbed
# Then it converts that into binary with a division loop and stores it in the array
# Then it prints out the binary equivalent
# It is then closed with syscall 10
##########################################################################
# $t1 - $t8 are used as temporary local values throughout the programme
# $t1 - $t2 are used to hold the immediate roman numerals that are being examined
# $t3 - $t4 are used for holding the decimal summation and the added value of the two respectibly
# $t5 - $t6 hold the converted numeral to decimal values
# $t7 - $t8 hold the array location and array value respectably
# $s0 holds the total decimal summation
# $s1 - $s2 hold the qoutient and remained of the array division respectably
# $s3 is used for any short term storage needs
.data
	StartString: .asciiz "\nYou entered the Roman numerals:\n"
	FailString: .asciiz "\nError: Invalid program argument.\n"
	TestString: .asciiz "\n It Works"
	list: .word 0:100
	String: .asciiz "\n"
	FinString: .asciiz "\nThe binary representation is:"
	SString: .asciiz "0b"
	Ostring: .asciiz "1" 
	Zstring: .asciiz "0" 


.text 
#Prints out start prompt, folowed by reading user input  
  	li $v0, 4 			#Print out String
  	la $a0, StartString		#Loads the startstring
 	syscall				#Prints out the startstring
 	
 	lw $a0, ($a1)			#Loads the program argument
 	move $t0, $a0
 	syscall				#Prints out the program argument
 	lb $t1, ($t0)
NumeralCheckLoop:			#Determines if numerals are correct ascii values
	j CorrectLetterLoop
	CorrectLetters:
	
	lb $t1, ($t0)			#Loads new numeral into register
	addi $t0, $t0, 1
	bne $t1, 0, NumeralCheckLoop	#Checks if hit null char
	
	move $t0, $a0 			#Prepares registers for ProperOrderLoop
 	lb $t1, ($t0)			#Loads user input into register
 	lb $t2, 1($t0)			#Loads user input into register
	b ProperOrderLoop		#Sends to correct ordering loop
CorrectLetterLoop:
	beq $t1, 73, CorrectLetters	#Checks if equal to I
	beq $t1, 86, CorrectLetters	#Checks if equal to V
	beq $t1, 88, CorrectLetters	#Checks if equal to X
	beq $t1, 76, CorrectLetters	#Checks if equal to L
	beq $t1, 67, CorrectLetters	#Checks if equal to C
	beq $t1, 68, CorrectLetters	#Checks if equal to C
	b Failed

ProperOrderLoop:
 	jal NumeralConversionT1		#Converts $t1 &$t2 to integer
 	bnez $s3 TwoBackCheck
	BackCheckSucc:
 	bgt  $t6, $t5, OrderFailed	#If the order is faulty, checks if its a special exception
 	OrderCorrect:
 	addi $t0, $t0, 1		#Increments by 1
 	lb $t1, ($t0)			#Loads user input into register
 	lb $t2, 1($t0)			#Loads user input into register	
	
	bne $t2, 0, ProperOrderLoop
	
	move $t0, $a0
 	j SummationLoop			#Its in proper order, now time to sum
 	
TwoBackCheck:				#Checks if the the 3-sequence combo is valid (IE IIV)
	bgt $t6, $s3,Failed
	j BackCheckSucc
	 	
SummationLoop:
	lb $t1, ($t0)			#Loads user input into register
	beq $t1, 0 SummationEnd		#Ensures there isnt a out of range error
 	lb $t2, 1($t0)			#Loads user input into register	
 	jal NumeralConversionT1		#Converts $t1 &$t2 to integer
	addi $t0, $t0, 1
	
	bgt $t6, $t5, Subtract		#Converted t1 and t2. Depending on order adds or subtracts
	j Addition
	
	SummationContinue:
	
	bne $t2, 0, SummationLoop	#If not finished, continues summation
	
	SummationEnd:			#Summation finished.
	move $s0, $t4			#Prints a new line to match spec
	li $v0, 4
	la $a0, String
	syscall
	j BinaryLoop
 	
Addition:
	add $t4, $t4, $t5		#Adds if addition
	j SummationContinue
Subtract:	
 	sub $t4, $t4, $t5		#Subtracts if subtractions
 	j SummationContinue
 	
NumeralConversionT1:
	move $s3, $t5
	beq $t1, 73, IConversion	#Checks if equal to I and converts
	beq $t1, 86, VConversion	#Checks if equal to V and converts
	beq $t1, 88, XConversion	#Checks if equal to X and converts
	beq $t1, 76, LConversion	#Checks if equal to L and converts
	beq $t1, 67, CConversion	#Checks if equal to C and converts
	beq $t1, 68, DConversion	#Checks if equal to D and converts
	b Failed
	
IConversion:				#Converts I
	li $t5, 1
	j NumeralConversionT2
VConversion:				#Converts V
	li $t5, 5
	j NumeralConversionT2
XConversion:				#Converts X
	li $t5, 10
	j NumeralConversionT2
LConversion:				#Converts L
	li $t5, 50
	j NumeralConversionT2
CConversion:				#Converts C
	li $t5, 100
	j NumeralConversionT2
DConversion:				#Converts D
	li $t5, 500
	jr $ra	
		
NumeralConversionT2:
	beq $t2, 73, IConversion2	#Checks if equal to I and converts
	beq $t2, 86, VConversion2	#Checks if equal to V and converts
	beq $t2, 88, XConversion2	#Checks if equal to X and converts
	beq $t2, 76, LConversion2	#Checks if equal to L and converts
	beq $t2, 67, CConversion2	#Checks if equal to C and converts
	beq $t2, 68, DConversion2	#Checks if equal to D and converts
	
IConversion2:				#Converts I
	li $t6, 1
	jr $ra
VConversion2:				#Converts V
	li $t6, 5
	jr $ra
XConversion2:				#Converts X
	li $t6, 10
	jr $ra
LConversion2:				#Converts L
	li $t6, 50
	jr $ra
CConversion2:				#Converts C
	li $t6, 100
	jr $ra	
DConversion2:				#Converts D
	li $t6, 500
	jr $ra	
	
OrderFailed:				#If order is incorrect, determines if it is actually correct (I.E. IV)
	add $t3, $t5, $t6	
	beq $t3, 6, OrderCorrect	#Checks for IV
	beq $t3, 11, OrderCorrect	#Checks for IX
	beq $t3, 60, OrderCorrect	#Checks for XL
	beq $t3, 110, OrderCorrect	#Checks for XC
	beq $t3, 600, OrderCorrect

	j Failed

Failed: 	
	li $v0, 4			#new line
	la $a0, String
	syscall
	la $a0, FailString		#You suck
	syscall				#new line
	la $a0, String
	syscall	
 	li $v0, 10			#Closes the program
 	syscall




BinaryLoop:
	move $t7, $s0
	move $s0, $zero			#Divides the number by 2, and stores the qoutient in an array
	BinaryLooper:
		div $t7, $t7, 2
		mfhi $s1		#Stores the qoutient
		mflo $s2		#Stores the remainder
		sb $s1, list($s0)	#Stores the qoutient in the array
		addi $s0, $s0, 1		
	bge $s2, 1, BinaryLooper	#When finished go to print
	sub $s0, $s0, 1


	li $v0, 4			#new line
	la $a0, FinString
	syscall
	la $a0, String
	syscall
	la $a0, SString
	syscall
PrintLoop:				#goes through array back to front	
	li $v0, 4
	lb $t8, list($s0)		#loads the int element, and prints out the string equivalent
	beq $t8, 1, OnePrint		#Prints One
	beq $t8, 0, ZeroPrint		#Prints Zero
	OnePrint:			#Prints One
		la $a0, Ostring
		syscall
		j fin
	ZeroPrint:			#Prints Zero
		la $a0, Zstring
		syscall
	fin:
	sub $s0, $s0, 1			#Iterates through
	bge $s0, 0, PrintLoop		#Prints
	
	
	li $v0, 4			#new line
	la $a0, String
	syscall
	li $v0, 10			#Good Night
	syscall
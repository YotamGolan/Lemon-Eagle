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
# Then reads user input, if the input is valid roman numerals, then it converts it appropriatly
# Then it prints out the input
# Then it prints out the binary equivalent
# It is then closed with syscall 10
##########################################################################
# Registery Uses
# t0 holds program arguments
# t1 holds immediatly used numeral
# t2 holds next numeral in series
.data
	StartString: .asciiz "\nYou entered the Roman Numerals:\n"
	FailString: .asciiz "\nError: Invalid program argument.\n"
	TestString: .asciiz "\n It Works"
	FinString: .asciiz "Fin"
	String: .asciiz "\n"
	SString: .asciiz "BAD\n"


.text 
#Prints out start prompt, folowed by reading user input  
  	li $v0, 4 		#Print out String
  	la $a0, StartString	#Loads the startstring
 	syscall			#Prints out the startstring
 	
 	lw $a0, ($a1)		#Loads the program argument
 	move $t0, $a0
 	syscall			#Prints out the program argument
 	lb $t1, ($t0)
NumeralCheckLoop:
	j CorrectLetterLoop
	CorrectLetters:
	
	lb $t1, ($t0)			#Loads new numeral into register
	addi $t0, $t0, 1
	bne $t1, 0, NumeralCheckLoop	#Checks if hit null char
	
	move $t0, $a0 	#Prepares registers for ProperOrderLoop
 	lb $t1, ($t0)	#Loads user input into register
 	lb $t2, 1($t0)	#Loads user input into register
	b ProperOrderLoop			#Sends to correct ordering loop
CorrectLetterLoop:
	beq $t1, 73, CorrectLetters	#Checks if equal to I
	beq $t1, 86, CorrectLetters	#Checks if equal to V
	beq $t1, 88, CorrectLetters	#Checks if equal to X
	beq $t1, 76, CorrectLetters	#Checks if equal to L
	beq $t1, 67, CorrectLetters	#Checks if equal to C
	b Failed

#PreNumeralCheckLoop:
#	move $t0, $a0
#	j ProperOrderLoop
ProperOrderLoop:
 	j NumeralConversionT1
 	Converted:
 	bgt  $t2, $t1, OrderFailed
 	OrderCorrect:
 	lb $t1, ($t0)	#Loads user input into register
 	lb $t2, 1($t0)	#Loads user input into register	
	

	addi $t0, $t0, 1
	bne $t2, 0, ProperOrderLoop
	
 	j SummationLoop
 	
SummationLoop:
	li $v0, 4 		#Print out String
  	la $a0, FinString	#Prints Finished
 	syscall	
 	li $v0, 10 		#Print out String
 	syscall	

NumeralConversionT1:
	beq $t1, 73, IConversion	#Checks if equal to I and converts
	beq $t1, 86, VConversion	#Checks if equal to V and converts
	beq $t1, 88, XConversion	#Checks if equal to X and converts
	beq $t1, 76, LConversion	#Checks if equal to L and converts
	beq $t1, 67, CConversion	#Checks if equal to C and converts
	b Failed
	
IConversion:	#Converts I
	li $t1, 1
	j NumeralConversionT2
VConversion:	#Converts V
	li $t1, 5
	j NumeralConversionT2
XConversion:	#Converts X
	li $t1, 10
	j NumeralConversionT2
LConversion:	#Converts L
	li $t1, 50
	j NumeralConversionT2
CConversion:	#Converts C
	li $t1, 100
	j NumeralConversionT2
	
NumeralConversionT2:
	beq $t2, 73, IConversion2	#Checks if equal to I and converts
	beq $t2, 86, VConversion2	#Checks if equal to V and converts
	beq $t2, 88, XConversion2	#Checks if equal to X and converts
	beq $t2, 76, LConversion2	#Checks if equal to L and converts
	beq $t2, 67, CConversion2	#Checks if equal to C and converts
	
IConversion2:	#Converts I
	li $t2, 1
	j Converted
VConversion2:	#Converts V
	li $t2, 5
	j Converted
XConversion2:	#Converts X
	li $t2, 10
	j Converted
LConversion2:	#Converts L
	li $t2, 50
	j Converted
CConversion2:	#Converts C
	li $t2, 100
	j Converted	
	
OrderFailed:
	add $t3, $t1, $t2	
	beq $t3, 6, OrderCorrect
	beq $t3, 11, OrderCorrect
	beq $t3, 60, OrderCorrect
	beq $t3, 110, OrderCorrect	

	j Failed

Failed: 	
	la $a0, FailString
	syscall		
 	li $v0, 10		#Closes the program
 	syscall


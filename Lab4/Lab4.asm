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
.data
	StartString: .asciiz "\nYou entered the Roman Numerals:\n"
	label2: .ascii "CE12"
	label3: .byte 0x21 0x21 0x00 0x41
.text 
#Prints out start prompt, folowed by reading user input  
  	li $v0, 4 		#Print out String
  	la $a0, StartString	#Loads the startstring
 	syscall			#Prints out the startstring
 	
 	lw $a0, ($a1)		#Loads the program argument
 	syscall			#Prints out the program argument
 	
 	li $v0, 10		#Closes the program
 	syscall
 	
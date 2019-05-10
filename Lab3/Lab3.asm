##########################################################################
# Created by:  Golan, Yotam
#              yogolan
#              9 May 2019
#
# Assignment:  Lab 3: MIPS!
#              CMPE 012, Computer Systems and Assembly Language
#              UC Santa Cruz, Spring 2019
# 
# Description: This program intakes ints from 0 - 10 and prints out the factorial. It rejects
# any other int outside the range
# 
# Notes:       This program is intended to be run from the MARS IDE.
# This program caluclates the factorial of numbers from 0 - 10
# Outputs 
##########################################################################
# REGISTER USAGE
# $t0: user input
# $t1: sum of factorial

.data
StartString: .asciiz "\nEnter an integer between 0 and 10: "
NewString: .asciiz "\n"
StartOverString: .asciiz "Invalid entry!"
.text 
  Start:
#Prints out start prompt, folowed by reading user input  
  	li $v0, 4
  	la $a0, StartString
 	syscall
 	
#Reads user input
  	li $v0, 5
  	syscall
  	
#Stores user input in t0 register, loads start of factorial (1) into t1 register  	
  	move $t0, $v0
  	li $t1 1
  	
#Prints a new line  	
  	li $v0, 4
  	la $a0, NewString
	syscall
	
#If input is outside the range, starts over
	bgt  $t0, 10, StartOver
  	blt  $t0, 0, StartOver
  	
#Prints out the integer, followed by the "! = "
  	li $v0, 1
 	la $a0, ($t0)
  	syscall
  	
#Prints out "! = " using only char calls as designated by assignment
 	li $v0, 11
 	la $a0, 0x21
 	syscall
 	li $v0, 11
 	la $a0, 0x20
 	syscall
 	li $v0, 11
 	la $a0, 0x3D
 	syscall
 	li $v0, 11
 	la $a0, 0x20
 	syscall
 	
#Does the actual factorial work by multiplying t1 by t0 until t0 = 0  
 	 Loop: 
		mulo $t1 $t1 $t0
 		addi $t0 $t0 -1
 		bgtz $t0 Loop
 		
#Prints the final factorial   
 	li $v0, 1
  	move $a0 $t1
 	syscall
 	
#Prints a new line	
	li $v0, 4
  	la $a0, NewString
	syscall
	
#Closes the program when it is finished
 	blez $t1 exitProgram
  		exitProgram:
  		li $v0, 10
  		syscall
  		
#If input is invalid, then this restart is called, printing invalid and looping to the start again 	
  StartOver:
  	li $v0, 4
  	la $a0, StartOverString
	syscall
	li $v0, 4
  	la $a0, NewString
	syscall
 	b Start
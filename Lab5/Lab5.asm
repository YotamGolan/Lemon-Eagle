#-------------------------------------------------------------------------
# Created by:  Golan, Yotam
#              yogolan
#              -- Month 2019
#
# Assignment:  Lab 5: A Gambling Game
#              CMPE 012, Computer Systems and Assembly Language
#              UC Santa Cruz, Spring 2019
# 
# Description: a terrible arcade game with little replay value
#		after displaying initial welcome the entire game consists of looping through next turn page
#		where 1 of 3 options is picked, either playing the game, cheating, or quitting
#		playing involves wagering some portion of your points and then guess which index of the array is the largest
#		correct guesses recieve that points, incorrect lose it
#-------------------------------------------------------------------------

#jal end_game                       # this is to prevent the rest of
                                   # the code executing unexpectedly

#--------------------------------------------------------------------
# play_game
#
# This is the highest level subroutine.
#
# arguments:  $a0 - starting score
#             $a1 - address of array index 0 
#
# return:     n/a
#--------------------------------------------------------------------
# registers
# $t4, t5 are used to swap a0 and a1 as the instructions have them constantly flip meanings
# $k0 is used for long term storage of current points
.text
play_game: nop
    
    move $k0 $a0	#stores current points in k0
    move $t4 $a0	#flips a0 and a1 around
    move $a0 $a1
    
    
    jal get_array_size
    
    move $t5 $v0	#stores array size
    move $a0 $t4	#unflips a1 and a0
    
    jal   welcome
    
    in_game:
    
    jal   prompt_options

    move $a3 $v0	#what option was picked
    move $a0 $k0	#updates score
    
    jal   take_turn 
    j in_game
    
    jal   end_game
    
    


#--------------------------------------------------------------------
# welcome (given)
#
# Prints welcome message indicating valid indices.
# Do not modify this subroutine.
#
# arguments:  $a0 - array size in words
#
# return:     n/a
#--------------------------------------------------------------------
#
# REGISTER USE
# $t0: array size
# $a0: syscalls
# $v0: syscalls
#--------------------------------------------------------------------

.data
welcome_msg: .ascii "------------------------------"
             .ascii "\nWELCOME"
             .ascii "\n------------------------------"
             .ascii "\n\nIn this game, you will guess the index of the maximum value in an array."
             .asciiz "\nValid indices for this array are 0 - "

end_of_msg:  .asciiz ".\n\n"
             
.text
welcome: nop

    add   $t0  $zero  $a0         # save address of array

    addiu $v0  $zero  4           # print welcome message
    la    $a0  welcome_msg
    syscall
    
    addiu $v0  $zero  1           # print max array index
    move   $a0  $t5
    syscall

    addiu $v0  $zero  4           # print period
    la    $a0  end_of_msg
    syscall
    
    jr $ra
    
    
#--------------------------------------------------------------------
# prompt_options (given)
#
# Prints user options to screen.
# Do not modify this subroutine. No error handling is required.
# 
# return:     $v0 - user selection
#--------------------------------------------------------------------
#
# REGISTER USE
# $v0, $a0: syscalls
# $t0:      temporarily save user input
#--------------------------------------------------------------------

.data
turn_options: .ascii  "------------------------------" 
              .ascii  "\nWhat would you like to do? Select a number 1 - 3"
              .ascii  "\n"
              .ascii  "\n1 - Make a bet"
              .ascii  "\n2 - Cheat! Show me the array"
              .asciiz "\n3 - Quit before I lose everything\n\n"

.text
prompt_options: nop

    addiu $v0  $zero  4           # print prompts
    la    $a0  turn_options       
    syscall

    addiu $v0  $zero  5           # get user input
    syscall
    
    add   $t0  $zero  $v0         # temporarily saves user input to $t0
    
    addiu $v0  $zero  11
    addiu $a0  $zero  0xA         # print blank line
    syscall

    add   $v0  $zero  $t0         # return player selection
    jr    $ra


#--------------------------------------------------------------------
# take_turn	
#
# All actions taken in one turn are executed from take_turn.
#
# This subroutine calls one of following sub-routines based on the
# player's selection:
#
# 1. make_bet
# 2. print_array
# 3. end_game
#
# After the appropriate option is executed, this subroutine will also
# check for conditions that will lead to winning or losing the game
# with the nested subroutine win_or_lose.
# 
# arguments:  $a0 - current score
#             $a1 - address of array index 0 
#             $a2 - size of array (this argument is optional)
#             $a3 - user selection from prompt_options
#
# return:     $v0 - updated score
#--------------------------------------------------------------------
#
# REGISTER USE
# 
#--------------------------------------------------------------------

.text
take_turn: nop

    subi   $sp   $sp  4          # push return addres to stack
    sw     $ra  ($sp)
    
    # some code
    
  	beq $a3, 1, Betjump	#decides what option was picked 1-3
	beq $a3, 2, Cheatjump
	beq $a3, 3, Quitjump
    	j end_game
	Betjump:		#Betting
	jal make_bet
	move $k0 $v0		#stores new score from betting
	j Fin
	Cheatjump:
	move $t4 $a0		#flips a1 and a0 because they get crossed in instructions given
	move $a0 $a1
	jal print_array
	move $a0 $t4		#unflips a0 and a1
	j Fin
	Quitjump:		
	jal end_game		#quits
	j Fin
	Fin:
	
	# move $a0 $k0

	move $t5 $a1		#flips a1 and a0 as needed for win/lose to work as instructed
	move $a1 $a0
	move $a0 $t5
	jal win_or_lose
	move $a1 $a0		#deflips a1 and a0 to meet requirements
	move $a0 $t4
	move $v0 $k0		#move points over to required position
    # some code
  
    #jal    end_game

    lw    $ra  ($sp)            # pop return address from stack
    addi  $sp   $sp   4
    
    #--------------------------------

    #--------------------------------
        
    jr $ra


#--------------------------------------------------------------------
# make_bet
#
# Called from take_turn.
#
# Performs the following tasks:
#
# 1. Player is prompted for their bet along with their index guess.
# 2. Max value in array and index of max value is determined.
#    (find_max subroutine is called)
# 3. Player guess is compared to correct index.
# 4. Score is modified
# 5. If player guesses correctly, max value in array is either:
#    --> no extra credit: replaced by -1
#    --> extra credit:    removed from array
#  
# arguments:  $a0 - current score of user
#             $a1 - address of first element in array
#
# return:     $v0 - updated score
#--------------------------------------------------------------------
#
# REGISTER USE
# $t7, t8 temporarily save user input
# $a0, v0 syscalls 
# $t9 stores size of array
# $t5 stores location of largest index
#--------------------------------------------------------------------


.data
new_line:      .asciiz "\n"
bet_header:   .ascii  "------------------------------"
              .asciiz "\nMAKE A BET\n\n"
            
score_header: .ascii  "------------------------------"
              .asciiz "\nCURRENT SCORE\n\n"
score_header2: .asciiz " pts \n"
              
point_total1: .asciiz "You currently have "      
point_total2: .ascii " points.\n"  
	      .asciiz "How many points would you like to bet? "     
	      
over_bet:     .asciiz "\nSorry, your bet exceeds your current worth.\n\n"
valid_indices:.asciiz "Valid indices for this array are 0 - "
index_guess:   .asciiz ". \nWhich index do you believe contains the maximum value? "
wrong_guess:   .asciiz "Your guess is incorrect! The maximum value is not in index "
point_loss:    .asciiz ". \n\nYou lost "
point_loss2:    .asciiz " points."

point_gain:	.asciiz "\n\nYou earned "
point_gain2:	.ascii " points!\n\n"
point_gain3:	.asciiz "This value has been removed from the array."

right_guess:   .asciiz "Score! Index "
right_guess2:  .asciiz " has the maximum value in the array."

            
# add more strings

.text
make_bet: nop       
    
    subi   $sp   $sp  4
    sw     $ra  ($sp)


    addi $t7 $a0 0
    move $t8 $a1			#Stores values in temp storage
    
    move $a0 $t8			#flips a0 and a1 t0 suit getarraysizes needs
    jal get_array_size
    move $t9 $v0			#unflips
    jal find_max
    move $t5 $v0			
    move $v1 $zero
    
    addiu  $v0  $zero  4           # print header
    la     $a0  bet_header
    syscall
    
    BetLessThan:
    
    la $a0 point_total1			#Prints out the introtext
    syscall
    
    li $v0 1				#Prints out point total
    move $a0 $t7
    syscall
    
    li $v0 4				#Prints out rest of intro text
    la $a0 point_total2
    syscall
    
    li $v0 5				#stores user input for point gambled
    syscall
    move $t6 $v0
    
    ble $t6, $t7, CorrectBet
    
    li $v0 4				#BetExceeds
    la $a0 over_bet			#Points gambled more then points possessed
    syscall
    j BetLessThan			#reprompts
    
    CorrectBet:				#valid point sum given
    li $v0 4
    la $a0 new_line
    syscall
    la $a0 valid_indices
    syscall
    
    li $v0 1				#Prints array size
    move $a0 $t9
    syscall
    
    li $v0 4				#where do you think it is?
    la $a0 index_guess
    syscall
    
    li $v0 5				#prompts for previous question
    syscall

    move $t9 $v0			#saves it
    
    li $v0 4
    la $a0 new_line
    syscall
    
    bne $t9 $t5 incorrect_guess		#guessed wrong
    b correct_guess			#guess right
    
    
    incorrect_guess:			
   
    li $v0 4				#tells you it was wrong
    la $a0 wrong_guess
    syscall
    
    li $v0 1				#Prints guess size
    move $a0 $t9
    syscall
    
    li $v0 4
    la $a0 point_loss
    syscall
    
    li $v0 1				#Prints guess size
    move $a0 $t6
    syscall
    
    li $v0 4
    la $a0 point_loss2
    syscall
    
    sub $t0 $t7 $t6			#subtracts point total
    
    j end_guess_loop
    
    correct_guess:
    li $v0 4				#guess right, and prints it
    la $a0 right_guess
    syscall
    
    li $v0 1				#Prints guess size
    move $a0 $t9
    syscall
    
    li $v0 4
    la $a0 right_guess2
    syscall
    
    move $a1 $t9			#reasserts a1 and a0 from storage
    move $a0 $t8
    jal mod_array			#modifies array
    
    add $t0 $t7 $t6			#adds points to current sum
    
    li $v0 4
    la $a0 point_gain
    syscall
    
    li $v0 1				#Prints guess size
    move $a0 $t6
    syscall
    
    li $v0 4
    la $a0 point_gain2
    syscall
    
    j end_guess_loop

 
    end_guess_loop:
    
    li $v0 4				#new line!
    la $a0 new_line
    syscall
    syscall
    
    la $a0 score_header			#prints out new point total in currect format
    syscall
    li $v0 1
    move $a0 $t0
    syscall
    li $v0 4
    la $a0 score_header2
    syscall
    la $a0 new_line
    syscall
    
    move $v0 $t0			#reasserts registeries
    move $a0 $t7
    move $a1 $t8
    
    lw     $ra  ($sp)
    addi   $sp   $sp  4

    #--------------------------------

    #--------------------------------

    jr     $ra


#--------------------------------------------------------------------
# find_max
#
# Finds max element in array, returns index of the max value.
# Called from make_bet.
# 
# arguments:  $a0 - address of first element in array
#
# returns:    $v0 - index of the maximum element in the array
#             $v1 - value of the maximum element in the array
#--------------------------------------------------------------------
#
# REGISTER USE
# t0 t2 v1 temp storage
#--------------------------------------------------------------------

.text
find_max: nop

    # some code
    move $t0, $a0		#stores a0
    move $v0, $zero		#resets v0 t2 v1
    li $t2 -1			
    li $v1 -1
    FindMaxLoop:		#loops through array finding largest value
	lw $t1, ($t0)		#loads the currently selected value
	beqz $t1 SizeOut	#if loaded a 0 you are finished
	addiu $t2, $t2 1	#current index
	bgt $t1, $v1, Bigger	#new biggest value
	j Smaller		#not bigger
	Bigger:
	move $v1, $t1		#saves its location and value
	move $v0 $t2
	Smaller:
	addiu $t0, $t0, 4 	#increments by a word and continues
	j FindMaxLoop


    jr     $ra

#--------------------------------------------------------------------
# win_or_lose
#
# After turn is taken, checks to see if win or lose conditions
# have been met
# 
# arguments:  $a0 - address of the first element in array
#             $a1 - updated score
#
# return:     n/a
#--------------------------------------------------------------------
#
# REGISTER USE
# t7 t8 t9 temporary storage
#--------------------------------------------------------------------

.data
win_msg:  .ascii   "------------------------------"
          .asciiz  "\nYOU'VE WON! HOORAY! :D\n\n"

lose_msg: .ascii   "------------------------------"
          .asciiz  "\nYOU'VE LOST! D:\n\n"

.text
win_or_lose: nop

    move $t9, $ra	#stores stack point
    jal find_max	#finds the max, if -1 then you are finished
    
    move $t7 $a0	#saves these
    move $t8 $a1
 
    beq $v1, -1, Win	#if biggest is -1, then you ahve won
    beqz $k0, Lose	#if point total = 0 you have lost
    jr $t9		#else continue
    
    li $v0 10
    syscall
    
    Win:		#prints winner message then end game
    addiu  $v0  $zero  4
    la     $a0  win_msg
    syscall
    
  j end_game
    			#prints loser message then ends game
    Lose:
    addiu  $v0  $zero  4
    la     $a0  lose_msg
    syscall
    
    li $v0 10
    syscall
    
    move $a0 $t7	#reasserts a0 a1
    move $a1 $t8
    j end_game

    jr     $t9


#--------------------------------------------------------------------
# print_array
#
# Print the array to the screen. Called from take_turn
# 
# arguments:  $a0 - address of the first element in array
#--------------------------------------------------------------------
#
# REGISTER USE
# $a0: syscalls
# $v0: syscalls
# $t0 t1 t2 t3 temporary storage
#--------------------------------------------------------------------

.data
cheat_header: .ascii  "------------------------------"
              .asciiz "\nCHEATER!\n\n"
array_spacer: .asciiz ": " 


.text
print_array: nop
	move $t0, $zero			#zeroes all the things
	move $t1, $zero
	move $t2, $zero
	move $t3 $a0

	move $t0, $a0			#Saves array start Location	
    
	addiu  $v0  $zero  4 		# print header
	la     $a0  cheat_header
	syscall
    
    	
    
	ArrayPrintLoop:		#loops through array 
	lw $t1, ($t0)
	beqz $t1 SizeOut	#stops when hit a 0
	
	li $v0, 1		#prints current index
	move $a0, $t2
	syscall
	
	li $v0, 4		#prints the spacer
	la $a0  array_spacer
	syscall
	
	li $v0, 1		#prints word
	move $a0, $t1
	syscall
	
	li $v0, 4		#new line
	la $a0  new_line
	syscall
	
	addi $t2, $t2, 1	#increments values
	addi $t0, $t0, 4
	j ArrayPrintLoop
	
	
    	move $a0 $t3
	jr     $ra



#--------------------------------------------------------------------
# end_game (given)
#
# Exits the game. Invoked by user selection or if the player wins or
# loses.
#
# arguments:  $a0 - current score
#
# returns:    n/a
#--------------------------------------------------------------------
#
# REGISTER USE
# $a0: syscalls
# $v0: syscalls
#--------------------------------------------------------------------

.data
game_over_header: .ascii  "------------------------------"
                  .ascii  " GAME OVER"
                  .asciiz " ------------------------------"

.text
end_game: nop

    add   $s0  $zero  $a0              # save final score

    addiu $v0  $zero  4                # print game over header
    la    $a0  game_over_header
    syscall
    
    addiu $v0  $zero  11               # print new line
    addiu $a0  $zero  0xA
    syscall
    
    addiu $v0  $zero  10               # exit program cleanly
    syscall


#--------------------------------------------------------------------
# OPTIONAL SUBROUTINES
#--------------------------------------------------------------------
# You are permitted to delete these comments.

#--------------------------------------------------------------------
get_array_size: nop
move $t0, $a0
addiu $v0, $zero, -1
move $t1, $zero
SizeLoop:		#loops until hits 0
lw $t1, ($t0)		#loads the word
beqz $t1 SizeOut	#stops if its 0
addi $t0, $t0, 4	#increments and continues
addi $v0, $v0, 1
b SizeLoop
SizeOut:
jr $ra
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# prompt_bet (optional)
#
# Prompts user for bet amount and index guess. Called from make_bet.
# 
# arguments:  $a0 - current score
#             $a1 - address of array index 0
#             $a2 - array size in words
#
# returns:    $v0 - user bet
#             $v1 - user index guess
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# compare (optional)
#
# Compares user guess with index of largest element in array. Called
# from make_bet.
#
# arguments:  $a0 - player index guess
#             $a1 - index of the maximum element in the array
#
# return:     $v0 - 1 = correct guess, 0 = incorrect guess
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# mod_score (optional)
#
# Modifies score based on outcome of comparison between user guess
# correct answer. Returns score += bet for correct guess. Returns
# score -= bet for incorrect guess. Called from make_bet.
# 
# arguments:  $a0 - current score
#             $a1 - player’s bet
#             $a2 - boolean value from comparison
#
# return:     $v0 - updated score
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# mod_array (optional)
#
# Replaces largest element in array with -1 if player guessed correctly.
# Called from make_bet.
#
# If extra credit implemented, the largest element in the array is
# removed and array shrinks by 1 element. Index of largest element
# is replaced by another element in the array.
# 
# arguments:  $a0 - address of array index 0
#             $a1 - index of the maximum element in the array
# 
# return:     n/a
mod_array: nop
move $t0 $a0
li $t1 0
li $t2 -1	
modloop:	
beq $t1 $a1 modloopout	#found desired index, stop
addiu $t0 $t0 4		#increments until found correct index
addiu $t1 $t1 1
j modloop
modloopout:

sw $t2 ($t0) 		#replaces current index with -1
jr $ra
#--------------------------------------------------------------------

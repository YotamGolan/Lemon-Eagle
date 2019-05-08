.text 
  li $v0, 5
  syscall

  move $t0, $v0
  li $t1 1
  
  LOOP: 
  mulo $t1 $t1 $t0
  addi $t0 $t0 -1
  
  li $v0, 1
  move $a0 $t1
  syscall

  blez $t1 exitProgram
  b LOOP
  	exitProgram:
  	li $v0, 10
  	syscall
  	
  

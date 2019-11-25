//-----------------------------------------------------------------------------
// List.c
// Implementation file for List ADT
//-----------------------------------------------------------------------------

#include<stdio.h>
#include<stdlib.h>
#include "List.h"

// structs --------------------------------------------------------------------

// private NodeObj type
typedef struct NodeObj{
   int data;
   struct NodeObj* next;
   struct NodeObj* previous;
} NodeObj;

// private Node type
typedef NodeObj* Node;

// private ListObj type
typedef struct ListObj{
   Node front;
   Node back;
   Node cursor;
   int length;
   int listIndex;
} ListObj;


// Constructors-Destructors ---------------------------------------------------

// newNode()
// Returns reference to new Node object. Initializes next and data fields.
// Private.
Node newNode(int data){
   Node N = malloc(sizeof(NodeObj));
   N->data = data;
   N->next = N->previous = NULL;
   return(N);
}


// newList()
// Returns reference to new empty List object.
List newList(void){
   List L;
   L = malloc(sizeof(ListObj));
   L->front = L->back = NULL;
   L->length = L->listIndex = 0;
   L->cursor = NULL;
   return(L);
}


// freeList()
// Frees all heap memory associated with List *pQ, and sets *pQ to NULL.S
void freeList(List* pL){
   if(pL!=NULL && *pL!=NULL) { 
      while( (*pL)->length!=0 ) { 
         deleteFront(*pL); 
      }
      free(*pL);
      *pL = NULL;
   }
}


// Access functions -----------------------------------------------------------

// getFront()
// Returns the value at the front of L.
// Pre: !L->length==0
int front(List L){
   if( L==NULL ){
      printf("List Error: calling front() on NULL List reference\n");
      exit(1);
   } 
   if( L->length==0 ){
      printf("List Error: calling front() on an empty List\n");
      exit(1);
   }
   return(L->front->data);
}

// length()
// Returns the length of L.
int length(List L){
   if( L==NULL ){
      printf("List Error: calling length() on NULL List reference\n");
      exit(1);
   }
   return(L->length);
}

int index(List L){
   if( L==NULL ){
      printf("List Error: calling index() on NULL List reference\n");
      exit(1);
   }
   if( L->length==0 ){
      printf("List Error: calling index() on an empty List\n");
      exit(1);
   }
   if( L->cursor==NULL ){
      return(-1);
	  }	
	return(L->listIndex);
}

int back(List L){
   if( L==NULL ){
      printf("List Error: calling back() on NULL List reference\n");
      exit(1);
   }
   if( L->length==0 ){
      printf("List Error: calling back() on an empty List\n");
      exit(1);
   }
   return(L->back->data);
}

int get(List L){
   if( L==NULL ){
      printf("List Error: calling get() on NULL List reference\n");
      exit(1);
   }
   if( L->length==0 ){
      printf("List Error: calling get() on an empty List\n");
      exit(1);
   }
   if(L->cursor == NULL || L->listIndex == -1)
   {
	  return -1;
   }
   return(L->cursor->data);
}

int equals(List A, List B){
	if( A==NULL || B==NULL ){
		printf("List Error: calling equals() on NULL List reference\n");
		exit(1);	
	  }
	  

	if(A->length==0 && B->length==0)
		return(1);
	if(A->length!= B->length)
		return(0);
	

	Node nodeA = A->front;
	Node nodeB = B->front;
	
	
	
	while(nodeA->next != NULL && nodeB->next != NULL)
	{
		if(nodeA->data != nodeB->data)
			return(0);
		nodeA = nodeA->next;
		nodeB = nodeB->next;

	}
		if(nodeA->next != nodeB->next)
			return(0);

   return(1);
}
// Manipulation procedures ----------------------------------------------------

void clear(List L)
{
	if( L==NULL ){
      printf("List Error: calling clear() on NULL List reference\n");
      exit(1);
   }
	while(L->front != NULL){
		deleteFront(L);
	}
}

void moveFront(List L)
{
	if( L==NULL ){
		printf("List Error: calling moveFront() on NULL List reference\n");
		exit(1);
   }
	if( L->length==0 ){
		printf("List Error: calling moveFront() on an empty List\n");
		exit(1);
   }
   L->cursor = L->front;
   L->listIndex = 0;
}

void moveBack(List L)
{
	if( L==NULL ){
		printf("List Error: calling moveFront() on NULL List reference\n");
		exit(1);
   }
	if( L->length==0 ){
		printf("List Error: calling moveFront() on an empty List\n");
		exit(1);
   }
   L->cursor = L->back;
   L->listIndex = L->length-1;
}

void movePrev(List L)
{
	if( L==NULL ){
		printf("List Error: calling moveFront() on NULL List reference\n");
		exit(1);
   }
	if( L->length==0 ){
		printf("List Error: calling moveFront() on an empty List\n");
		exit(1);
   }
   
	if(L->listIndex == 0){
	   L->cursor = NULL;
	   L->listIndex = -1;
	}
   
	if(L->cursor != NULL && L->listIndex != 0){
		L->cursor = L->cursor->previous;
		L->listIndex = L->listIndex-1;
	}
}

void moveNext(List L)
{
	if( L==NULL ){
		printf("List Error: calling moveFront() on NULL List reference\n");
		exit(1);
   }
	if( L->length==0 ){
		printf("List Error: calling moveFront() on an empty List\n");
		exit(1);
   }
	if(L->cursor->next == NULL){
	   L->cursor = NULL;
	   L->listIndex = -1;

	}
	if(L->cursor != NULL && L->listIndex != L->length-1){
		L->cursor = L->cursor->next;
		L->listIndex = L->listIndex+1;
	}
}

// append()
// Places new data element at the end of Q
void append(List L, int data)
{
   Node N = newNode(data);

   if( L==NULL ){
		printf("List Error: calling append() on NULL List reference\n");
		exit(1);
   }
   if( L->length==0 ) { 
		L->front = L->back = N; 
   }else{ 
		N->previous = L->back;
		L->back->next = N; 
		L->back = N; 
   }
	L->length++;

}

void prepend(List L, int data)
{
   Node N = newNode(data);

   if( L==NULL ){
		printf("List Error: calling prepend() on NULL List reference\n");
		exit(1);
   }
   if( L->length==0 ) { 
		L->front = L->back = N; 
   }else{ 
		N->next = L->front;
		L->front->previous = N; 
		L->front = N; 
   }
	L->length++;
	L->listIndex++;
}

void insertBefore(List L, int data)
{
   Node N = newNode(data);

   if( L==NULL ){
      printf("List Error: calling insertBefore() on NULL List reference\n");
      exit(1);
   }
   if(L->cursor == NULL){
	  printf("List Error: calling insertBefore() on NULL cursor reference\n");
      exit(1);
   }
   
	if(L->cursor->previous == NULL){
		prepend(L, data);
	}
	else{
	N->previous = L->cursor->previous;
	N->next = L->cursor;
	L->cursor->previous->next = N;
	L->cursor->previous = N; 
	
	L->length++;
	L->listIndex++;
	}
}

void insertAfter(List L, int data)
{
   Node N = newNode(data);

   if( L==NULL ){
      printf("List Error: calling insertBefore() on NULL List reference\n");
      exit(1);
   }
   if(L->cursor == NULL){
	  printf("List Error: calling insertBefore() on NULL cursor reference\n");
      exit(1);
   }
	   
	N->previous = L->cursor;
	N->next = L->cursor->next;
	L->cursor->next->previous = N;
	L->cursor->next = N; 
	
	L->length++;
}

void delete(List L)
{
	if( L==NULL ){
      printf("List Error: calling delete() on NULL List reference\n");
      exit(1);
   }
   if(L->cursor == NULL){
	  printf("List Error: calling delete() on NULL cursor reference\n");
      exit(1);
   }
	L->cursor->previous->next = L->cursor->next;
	L->cursor->next->previous = L->cursor->previous;	
	
	Node N = L->cursor;
	L->cursor = NULL;
	free(N);
	
	L->length--;
	L->listIndex = -1;

}

void deleteFront(List L)
{
	if( L==NULL ){
      printf("List Error: calling delete() on NULL List reference\n");
      exit(1);
	}
	if( L->length==0 ){
		printf("List Error: calling deleteFront() on an empty List\n");
		exit(1);
	}
	if(L->length > 1){
		Node N = L->front;
		L->front = L->front->next;
		N->next->previous = NULL;
		N->next = NULL;	
		free(N);
	}
	if(L->length == 1){
		Node N = L->front;
		free(N);
		L->front = NULL;
	}
	L->length--;
	L->listIndex--;
}

void deleteBack(List L)
{
	if( L==NULL ){
      printf("List Error: calling delete() on NULL List reference\n");
      exit(1);
   }
   if( L->length==0 ){
		printf("List Error: calling deleteBack() on an empty List\n");
		exit(1);
   }
	Node N = L->back;
   
	L->back->previous->next = NULL;
	L->back->previous = NULL;	
	
	free(N);
	
	L->length--;
	
	if(L->listIndex == L->length){
		L->cursor = NULL;
		L->listIndex = -1;
	}
}

// Other Functions ------------------------------------------------------------

// printList()
// Prints data elements in L on a single line to stdout.
void printList(FILE* out, List L){
   Node N = NULL;

   if( L==NULL ){
      printf("List Error: calling printList() on NULL List reference\n");
      exit(1);
   }

   for(N = L->front; N != NULL; N = N->next){
      printf("%d ", N->data);
   }
   printf("\n");
}

List copyList(List L)
{
	List M;
	M = malloc(sizeof(ListObj));
	M->front = NULL;
	M->back = NULL;
	M->length = 0;
	M->cursor = NULL;
	
	Node N = L->front;
	while(M->length < L->length){
		append(M,N->data);
		N = N->next;
	}
	
	return(M);
}

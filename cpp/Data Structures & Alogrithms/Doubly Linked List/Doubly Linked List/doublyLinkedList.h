#ifndef DOUBLYLINKEDLIST_H_
#define DOUBLYLINKEDLIST_H_

#include "baseList.h"

/*
  Part 2: Data Structures
*/

class DoublyLinkedList : public BaseList
{
  private:
  
    class Node
    {
      public:
        int data;
        Node * previous; // add a new pointer to the precedent node
        Node * next;
        
        Node(int data, Node * previous, Node * next); // add the new pointer
        ~Node();
    };

    Node * head;
    Node * tail; // add a new pointer to the end of the list

  public:
    DoublyLinkedList();
    ~DoublyLinkedList();
  
    void prepend(int data);
    void append(int data);
    void display();
    void reverse_display(); // add a function to display list's data reversely
};

#endif

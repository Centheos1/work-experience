//
//  linkedList.hpp
//  LinkedList
//
//  Created by Clint Sellen on 16/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef linkedList_h
#define linkedList_h

#include <stdio.h>
#include "baseList.h"

class LinkedList : public BaseList // inheritance
{
  private:
  
    class Node // an inner class of LinkedList
    {
      private:
        int data;
        Node * next;

      public:
        Node();
        ~Node();
    
        // getters and setters
        Node(Node * next, int data); // use constructer as setters
        int getData();
        Node * getNext();
        void setNext(Node *);
    };

    Node * head;

  public:
    LinkedList();
    ~LinkedList();
  
    void prepend(int data);
    void append(int data);
    void display();
};

#endif /* linkedList_hpp */

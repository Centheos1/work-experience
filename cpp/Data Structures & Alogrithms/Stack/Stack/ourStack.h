//
//  ourStack.h
//  Stack
//
//  Created by Clint Sellen on 23/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef OURSTACK_H_
#define OURSTACK_H_

#include "baseStack.h"

/* File 2: Data Structures */

class OurStack : public BaseStack
{
  private:
    int length;
  
    class Node
    {
      public:
        int data;
        Node * next;
        
        Node(int data, Node * next);
        ~Node();
    };

    Node * head;

  public:
    OurStack();
    ~OurStack();
  
    bool empty();
    int top();
    void push(int data);
    void pop();
    void display();
    int getLength();
    void setLength(int length);
    
};

#endif


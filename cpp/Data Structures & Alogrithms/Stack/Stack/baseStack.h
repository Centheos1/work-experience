//
//  baseStack.h
//  Stack
//
//  Created by Clint Sellen on 23/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef BASESTACK_H_
#define BASESTACK_H_

/* File 1: Interfaces */

class BaseStack
{
  public:
    virtual bool empty() = 0;
    virtual int top() = 0;
    virtual void push(int data) = 0;
    virtual void pop() = 0;
    virtual void display() = 0;
};

#endif

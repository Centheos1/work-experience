//
//  baseList.h
//  LinkedList
//
//  Created by Clint Sellen on 16/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef baseList_h
#define baseList_h

class BaseList
{
  public:
    // constructor cannot be declared 'virtual'
    virtual ~BaseList() {};
  
    virtual void prepend(int data) = 0;
    virtual void append(int data) = 0;
    virtual void display() = 0;
};


#endif /* baseList_h */

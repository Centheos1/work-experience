#ifndef BASELIST_H_
#define BASELIST_H_

/*
  Part 1: Interfaces
*/
class BaseList
{
  public:
    virtual void prepend(int data) = 0;
    virtual void append(int data) = 0;
    virtual void display() = 0;
    virtual void reverse_display() = 0;
};

#endif

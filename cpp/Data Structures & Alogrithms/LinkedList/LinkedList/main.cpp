//
//  main.cpp
//  LinkedList
//
//  Created by Clint Sellen on 16/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include "linkedList.h"

using namespace std;

int main(){
    
  LinkedList * list = new LinkedList();
  
  // adding nodes
  for (int i = 0; i < 6; i++)
  {
    if (i < 3) {
        list->prepend(i);
    }else{
      list->append(i);
    }
  }
  
  // display data in all nodes one by one
  list->display();
  
  // prepare to exit by clearing allocations
  delete list;
  return 0;
}

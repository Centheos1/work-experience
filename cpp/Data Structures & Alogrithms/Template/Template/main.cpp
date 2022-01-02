//
//  main.cpp
//  Template
//
//  Created by Clint Sellen on 28/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include "stack.h"
#include "queue.h"

int main(){
    
//    Stack
//    stack<int> s;
//    int i = 0;
//    while (i<12) {
//        s.push(i);
//        i++;
//    }
//
//
//    while (!s.empty()){
//        cout << s.pop() << endl;
//    }
    
//    Queue
    int capacity;
    cout << "Enter initial queue length: " << endl;
    cin >> capacity;
    
    queue<int> q(capacity);
    
    for (int i = 0; i < 3*capacity; i++){
        
//        q.push(i);
        
        int rand = 0 + ( std::rand() % ( 100 - 0 + 1 ) );
            
        if (rand % 3 == 0) {
            q.pop();
        } else {
            q.push(rand);
        }
        
//        if (i % 3 == 0) {
//            q.pop();
//        } else {
//            q.push(i);
//        }
    }
    
//    q.pop();
    
    q.displayInfo();
    
    while (!q.empty()){
        cout << q.pop() << endl;
    }
}

//
//  stack.h
//  Template
//
//  Created by Clint Sellen on 28/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

using namespace std;

template <typename T>
class stack {
    
    private:
    
    T data[10];
    // You may want to add other class variables here.
    const int MAX_SIZE = sizeof(data)/sizeof(*data);
    int num_items = 0;
    
    public:
    
    //constructor
    stack(){}
    
    //destructor
    ~stack(){}
    
    //add an element to the top of the stack
    void push(T t){
        
        if (num_items == MAX_SIZE) {
            return;
        } else if (num_items == 0) {
            data[0] = t;
        } else {
            for (int i = num_items - 1; i >= 0; i--) {
                data[i+1] = data[i];
            }
        }
        data[0] = t;
        num_items++;
    }
    
    //remove an element from the top of the stack
    T pop(){
        
        if (num_items == 0) {
            return NULL;
        }
        
        T t = data[0];
        
        for (int i=0; i < num_items - 1; i++) {
            data[i] = data[i+1];
        }
        
        num_items--;
        return t;
    }
    
    //check if the stack is empty
    bool empty(){
        return num_items == 0;
    }
};

////As this is a templated class, you should implement the
////class in the header.
//
//template <typename T>
//class stack {
//
//    private:
//        T data[10];
//        // You may want to add other class variables here.
//        int length;
//
//    public:
//        stack(){} //constructor
//        ~stack(){} //destructor
//
//        void push(T t){
//
//        } //add an element to the top of the stack
//        T pop(){
//
//        } //remove an element from the top of the stack
//        bool empty(){
//
//        } //check if the stack is empty
//};

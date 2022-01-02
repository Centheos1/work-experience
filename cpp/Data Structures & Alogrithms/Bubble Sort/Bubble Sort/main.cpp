//
//  main.cpp
//  Bubble Sort
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <vector>
using namespace std;

void swap(int* e1, int* e2){
    int temp = *e1;
    *e1 = *e2;
    *e2 = temp;
}

/*
    Note that this function does not use "const" and "&" for the argument.
    Therefore, a copy of v will be created and passed to this function.
    The function can modify and return this copy of v.
*/
vector<int> bubble_sort(vector<int> v_copy){
    
    bool swapped; // can terminate early if no swap occurs
    do {
        swapped = false;
        
        for (int i = 0; i < v_copy.size() - 1; i++){
            // for each of {0, 1, ..., v.size()-2} in the vector,
            // compare it with its next and swap them if the next is smaller.
            if (v_copy[i] > v_copy[i+1]){
                swap(&v_copy[i], &v_copy[i+1]);
                swapped = true;
            }
        }
        // As such, each while iteration ensures to move the largest value
        //      among the unsorted to the end of the vector
        //                (precedent of the values larger than it).
        
    } while(swapped);
    
    return v_copy;
}


int main(){
    
    vector<int> v {15, 4, 1, 3, 14, 7, 18, 9, 5, 6, 8, 17, 10, 2, 16, 13, 11, 12};
    
    vector<int> sorted = bubble_sort(v);
    
    cout << "Soring result:";
    for (int i = 0; i < sorted.size(); i++){
        cout << ' ' << sorted[i];
    }
    cout << endl;
}

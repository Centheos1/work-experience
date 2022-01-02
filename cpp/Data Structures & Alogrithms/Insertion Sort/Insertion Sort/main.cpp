//
//  main.cpp
//  Insertion Sort
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <vector>
using namespace std;

void insertion_sort(vector<int>& v){
    
    // for each of the positions {1, 2, ..., v.size()-1} in the vector,
    //    each time get a value sorted to left of the vector,
    //       ensuring all the values before this positoin is sorted.
//    for (int i=1; i<v.size(); i++){
//
//        int x = v[i];
//        int pos = i - 1;
//        while (v[pos]>x && pos>=0){
//            v[pos + 1] = v[pos];
//            pos--;
//        }
//        v[pos + 1] = x; // put x to the right position
//                        // v[i] remain unchanged if no swap occurred
//    }
    
    /*
        Below is an alternative implementation of the above using iterator.
    */
    
     for (vector<int>::iterator i = v.begin()+1; i<v.end(); i++){
         int x = *i;
         auto pos = next(i, -1);
         while (*pos > x && pos >= v.begin()){
             *(pos+1) = *pos;
             pos--;
         }
         *(pos+1) = x;
     }
    
}

int main(){
    
    vector<int> v {15, 4, 1, 3, 14, 7, 18, 9, 5, 6, 8, 17, 10, 2, 16, 13, 11, 12};
    
    insertion_sort(v);
    
    cout << "Soring result:";
    for (vector<int>::iterator i = v.begin(); i != v.end(); i++){
        cout << ' ' << *i;
    }
    cout << endl;
}

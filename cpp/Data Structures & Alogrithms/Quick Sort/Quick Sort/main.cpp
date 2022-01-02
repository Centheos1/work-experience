//
//  main.cpp
//  Quick Sort
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <vector>
using namespace std;

void swap(int& e1, int&e2){
    int temp = e1;
    e1 = e2;
    e2 = temp;
}

/*
    Use the last element in the vector as "pivot";
    Places all smaller elements to its left and others to its right.
*/
int partition(vector<int>& v, int low, int high){
    
    int pivot = v[high]; // use the last element as the pivot.
    int i = low - 1; // this indicates the position of left_part's last element.
    
    for (int j = low; j < high; j++){
        if (v[j] < pivot){
            i++; // track the ending position of the left_part
            swap(v[i], v[j]); // move all elements smaller then the pivot element
                              // to the left part
        }
    }
    
    swap(v[i+1], v[high]); // place the pivot element to its right position
    
    return i+1; // return the pivot element's position
}

void qsort(vector<int>& v, int low, int high){
    
    if (low < high){
        // devide the vector into two parts:
        // left contains smaller and right contains larger values than the pivot.
        int p = partition(v, low, high);
        
        // sort the two parts, respectively. Once done, the sorting is ready.
        qsort(v, low, p-1);
        qsort(v, p+1, high);
    }
    
}

void quick_sort(vector<int>& v){
    
    qsort(v, 0, v.size()-1); // 0 is the first element's index
                             // v.size()-1 is the last element's index

}

int main(){
    
    vector<int> v {15, 4, 1, 3, 14, 7, 18, 9, 5, 6, 8, 17, 10, 2, 16, 13, 11, 12};
    
    quick_sort(v);
    
    cout << "Soring result:";
    for (vector<int>::iterator i = v.begin(); i != v.end(); i++){
        cout << ' ' << *i;
    }
    cout << endl;
}

//
//  main.cpp
//  Merge Sort
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <vector>
using namespace std;

vector<int> merge(const vector<int>& v1, const vector<int>& v2){
    
//    cout << "merge v1: " << v1.size() << " v2: " << v2.size() << endl;
    
    vector<int> merged;
    
    auto it1 = v1.begin(); // get an iterator pointing to the 1st element in v1
    auto it2 = v2.begin(); // get an iterator pointing to the 1st element in v2
    
    while (it1 < v1.end() && it2 < v2.end()){
        // if v1 and v2 both have more elements, compare thier current element,
        //     and add the smaller one to the result.
        if (*it1 < *it2){
            merged.push_back(*it1);
            ++it1;
        }else{
            merged.push_back(*it2);
            ++it2;
        }
    }
    
    // if not all elements of v1 are added to the result,
    if (it1 < v1.end()){
        copy(it1, v1.end(), back_inserter(merged));
    }
    
    // if not all elements of v2 are added to the result,
    if (it2 < v2.end()){
        copy(it2, v2.end(), back_inserter(merged));
    }
    
    return merged; // return the result
}

vector<int> merge_sort(const vector<int>& v){
    
//    cout << "merge_sort v: " << v.size() << endl;
    
//    Break condition of the recursive call
    if (v.size() == 1){
        return v; // there is only one value, no need to sort
    }
    
    // divide the vector into two sub-vectors
    vector<int> v1, v2;
    copy(v.begin(), v.begin() + v.size()/2, back_inserter(v1));
    copy(v.begin() + v.size()/2, v.end(), back_inserter(v2));
    
    // merge of the sorting results of the two sub-vectors
    return merge(merge_sort(v1), merge_sort(v2));
    
}

int main(){
    
    vector<int> v {15, 4, 1, 3, 14, 7, 18, 9, 5, 6, 8, 17, 10, 2, 16, 13, 11, 12};
    
    vector<int> sorted = merge_sort(v);
    
    cout << "Soring result:";
    for (vector<int>::iterator i = sorted.begin(); i != sorted.end(); i++){
        cout << ' ' << *i;
    }
    cout << endl;
}

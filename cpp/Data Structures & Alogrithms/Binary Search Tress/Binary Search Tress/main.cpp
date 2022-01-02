//
//  main.cpp
//  Binary Search Tress
//
//  Created by Clint Sellen on 10/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
//#include "bst.h"
#include "bst.hpp"
using namespace std;

//int main(){
//
//    bst t;
//
//    t.insert(10);
//    t.insert(13);
//    t.insert(3);
//    t.insert(1);
//    t.insert(11);
//    t.insert(12);
//    t.insert(15);
//
//    cout << boolalpha;
//    cout << "The tree contains the value " << 10 << " at position " << t.index_of(10) << endl;
//    cout << "The tree contains the value " << 3 << " at position " << t.index_of(13) <<  endl;
//    cout << "The tree contains the value " << 11 << " at position " << t.index_of(3) <<  endl;
//    cout << "The tree contains the value " << 9 << " at position " << t.index_of(9) <<  endl;
//    cout << "The tree contains the value " << 2 << " at position " << t.index_of(2) <<  endl;
//
//}



//int main(){
//
//    // create a BST
//    bst<int> t;
//
//    // insert some values to the tree
//    t.insert(1);
//    t.insert(3);
//    t.insert(10);
//    t.insert(13);
//    t.insert(11);
//    t.insert(12);
//    t.insert(15);
//
//    // check if some values are in the tree
//    cout << boolalpha; // set to show bool data as true/false instead of 1/0
//    cout << "The tree contains the value " << 10 << " at position " << t.index_of(10) << endl;
//    cout << "The tree contains the value " << 13 << " at position " << t.index_of(13) <<  endl;
//    cout << "The tree contains the value " << 3 << " at position " << t.index_of(3) <<  endl;
//    cout << "The tree contains the value " << 1 << " at position " << t.index_of(1) <<  endl;
//    cout << "The tree contains the value " << 11 << " at position " << t.index_of(11) <<  endl;
//    cout << "The tree contains the value " << 12 << " at position " << t.index_of(12) <<  endl;
//    cout << "The tree contains the value " << 15 << " at position " << t.index_of(15) <<  endl;
//    cout << "The tree contains the value " << 9 << " at position " << t.index_of(9) <<  endl;
//    cout << "The tree contains the value " << 2 << " at position " << t.index_of(2) <<  endl;
//
//}


int main(){
    
    // create a BST
    bst<int> t;
    
    // insert some values to the tree
    t.insert(1);
    t.insert(3);
    t.insert(10);
    t.insert(20);
    t.insert(5);
    t.insert(30);
    t.insert(15);
    t.insert(17);
    t.insert(25);
    t.insert(21);
    t.insert(26);
    
    // check if some values are in the tree
    cout << boolalpha; // set to show bool data as true/false instead of 1/0
    cout << "The tree contains the value " << 1 << " at position " << t.index_of(1) << endl;
    cout << "The tree contains the value " << 10 << " at position " << t.index_of(10) <<  endl;
    cout << "The tree contains the value " << 15 << " at position " << t.index_of(15) <<  endl;
    cout << "The tree contains the value " << 20 << " at position " << t.index_of(20) <<  endl;
    cout << "The tree contains the value " << 26 << " at position " << t.index_of(26) <<  endl;

    // remove some nodes form BST
    cout << endl << "Removing values 5, 10 and 20!" << endl << endl;
    
    t.remove(5); // case 1
    t.remove(10); // case 2
    t.remove(20); // case 3
    
    // re-check what is left in the tree
    cout << "The tree contains the value " << 1 << " at position " << t.index_of(1) << endl;
    cout << "The tree contains the value " << 10 << " at position " << t.index_of(10) <<  endl;
    cout << "The tree contains the value " << 15 << " at position " << t.index_of(15) <<  endl;
    cout << "The tree contains the value " << 20 << " at position " << t.index_of(20) <<  endl;
    cout << "The tree contains the value " << 26 << " at position " << t.index_of(26) <<  endl;

}

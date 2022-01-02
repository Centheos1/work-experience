//
//  bst.hpp
//  Binary Search Tress
//
//  Created by Clint Sellen on 10/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef BST_HPP
#define BST_HPP

#include <iostream>
#include <vector>
using namespace std;

template<typename T> void swap(T* x, T* y) {
    auto temp = *x;
    *x = *y;
    *y = temp;
}

template <typename T> // define a template for the class
class bst {
    
    private:
    
        vector<T> tree; // a vector to store values
        vector<bool> in_tree; // an auxilary vector to indicate which units are used/unused in tree
        
        bool has_left_child(const int&) const; // if the value has a left child
        bool has_right_child(const int&) const;// if the value has a right child
        void move_subtree_up(const int&, const int&); // move a subtree up
    
    public:
    
        bst(); // constructor, which initialise the above vectors
    
        void insert(const T&); // add a node/value to the tree
        int index_of(const T&) const; // get the index of a value in the tree
        void remove(const T&); // remove a node/value from the tree
    
};

// because the class is templated, all member functions of the class should be templated as well.
template <typename T> bst<T>::bst(){
    // default tree capacity to 10, which can be later extended by insert(.)
    tree.resize(10);
    in_tree.resize(10);
}


template <typename T> void bst<T>::insert(const T& t){
    
    int pos = 0; // first check root
    
    while (pos < tree.size() && in_tree[pos]){
        // find the appropriate vacant position for t by checking tree nodes in a top-down manner
        pos = t < tree[pos] ? 2*pos + 1 : 2*pos +2;
    }
    
    // if no vacant position is found, meaning the tree is full
    if (pos >= tree.size()){
        tree.resize(2*tree.size()); // double the tree size
        in_tree.resize(2*in_tree.size(), false); //according, double in-tree size
    }
    
    tree[pos] = t; // store t in at pos
    in_tree[pos] = true; // mark this position (i.e., pos) as occupied.
    
}

// index_of(.) is a constant function, which cannot modify any objects on which it operates.
template <typename T> int bst<T>::index_of(const T& t) const {
    
    int pos = 0; // search from the root
    
    while (pos < tree.size() && in_tree[pos] && tree[pos] != t){
        // search occupied positions within the tree
        pos = t < tree[pos] ? 2*pos + 1 : 2*pos +2;
    }
    
    // if found, return the position/index; otherwise, return -1.
    return pos < tree.size() && in_tree[pos] ? pos : -1;
}

template<typename T> bool bst<T>::has_left_child(const int& pos) const {
    
    // pos has a left child iff 2*pos+1 is occupied
    return pos>=0 && 2*pos+1<tree.size() ? in_tree[2*pos + 1] : false;
    
}

template<typename T> bool bst<T>::has_right_child(const int& pos) const {
    
    return pos >= 0 && 2*pos+2 < tree.size() ? in_tree[2*pos+ 2] : false;
}

// This function move a node up to "pos_to_move" from "its_child"
template <typename T> void bst<T>::move_subtree_up(const int& its_child, const int& pos_to_move) {
         
    // move a node from "its_child" to "pos_to_move"
    tree[pos_to_move] = tree[its_child];
    in_tree[pos_to_move] = true;
    in_tree[its_child] = false;

    // recursively move the nodes contained in the subtree led by the child
    if (has_left_child(its_child)){
        move_subtree_up(2*its_child + 1, 2*pos_to_move + 1);
    }
    
    if (has_right_child(its_child)){
        move_subtree_up(2*its_child + 2, 2*pos_to_move + 2);
    }
}

template <typename T> void bst<T>::remove(const T& t){
    
    int pos_to_remove = index_of(t); // get index of the input value
    
    if (pos_to_remove != -1){ // check if pos_to_remove is valid
        
        // post_to_remove can be removed directly if it has no children
        if (!has_left_child(pos_to_remove) && !has_right_child(pos_to_remove)){
            in_tree[pos_to_remove] = false;
        
        // move up the left subtree if post_to_remove only has a left subtree
        } else if (!has_left_child(pos_to_remove)){
            move_subtree_up(2*pos_to_remove+2, pos_to_remove);
        
        // move up the right subtree if post_to_remove only has a right subtree
        } else if (!has_right_child(pos_to_remove)){
            move_subtree_up(2*pos_to_remove +1, pos_to_remove);
            
        } else { // in this case, post_to_remove has both left and right subtrees
            // find the smallest value in the right subtree
            int replacement_pos = 2*pos_to_remove + 2; // start from right chid
            
            while (has_left_child(replacement_pos)){
                // find the left most child in the right subtree via iteration,
                // this child contains the smallest value in the right subtree
                replacement_pos = 2*replacement_pos + 1;
            }
            /*
                The following preserves the elements' order after the removal:
                (1) swap the values at replacement_pos and pos_to_remove;
                (2) remove replacement_val as it was the node to remove.
            */
            swap(tree[replacement_pos], tree[pos_to_remove]);
            remove(t);
        }
    }
}

#endif
//
//#ifndef BST_HPP
//#define BST_HPP
//
//#include <iostream>
//#include <vector>
//using namespace std;
//
//template <typename T> // define a template for the class
//class bst {
//
//    private:
//
//        vector<T> tree; // a vector to store values
//        vector<bool> in_tree; // an auxilary vector to indicate
//                             // which units are used/unused in tree
//
//    public:
//
//        bst(); // constructor, which initialise the above vectors
//
//        void insert(const T&); // add a node/value to the tree
//        int index_of(const T&) const; // get the index of a value in the tree
//
//};
//
//template <typename T> // because the class is templated, all member functions
//                      //    of the class should be templated as well.
//bst<T>::bst(){
//    // default tree capacity to 10, which can be later extended by insert(.)
//    tree.resize(10);
//    in_tree.resize(10);
//}
//
//
//template <typename T>
//void bst<T>::insert(const T& t){
//
//    // Your code goes here
//
//}
//
//// index_of(.) is a constant function, which cannot modify
////    any objects on which it operates.
//template <typename T>
//int bst<T>::index_of(const T& t) const {
//
//    // Your code goes here
//
//}
//
//#endif

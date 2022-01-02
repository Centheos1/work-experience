//
//  simple_min_heap.h
//  Min Heap
//
//  Created by Clint Sellen on 15/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef SIMPLE_MIN_HEAP
#define SIMPLE_MIN_HEAP

class min_heap {

    private:

        int * array; // the array to hold heap elements
        int next_leaf; // the next available position/node to hold new elements
        int capacity; // the maximun number of elements (i.e., length of array)

        bool has_left(int p){ // check if p has a left child
            return left(p) < next_leaf;
        }
    
        bool has_right(int p) { // check if p has a right child
            return right(p) < next_leaf;
        }
        
        bool has_parent(int p){ // check if p has parent
            return p > 0; // p does not have a parent only when p==0
        }
    
        int left(int p) { // get the left child
            return 2*p + 1;
        }
    
        int right(int p) { // get the right child
            return 2*p + 2;
        }
    
        int parent(int p) { // get the parent of the current node (i.e., p)
            return (p-1)/2;
        }

        void swap(int p1, int p2) { // swap elements at positions p1 & p2
            int temp = array[p1];
            array[p1] = array[p2];
            array[p2] = temp;
        }

/*
 recursively compare a node with its parentto reorder the heap
 used upon insertion
 if position p contains a bigger value than its parent, swap them
 */
        void bubble_up(int p){ // iteratively compare a node with its parent to reorder the heap; used upon insertion
            
//            If p is less than its parent, swap with parent
            if (has_parent(p) && array[p] < array[parent(p)]) {
                swap(p, parent(p));
                bubble_up(parent(p));
            }
        }

        void bubble_down(int p){
            /* no way that p has a right child without having a left child */
            if (has_left(p)) {
                // the smaller between the left and (possibly existing) right children
                int the_smaller = (has_right(p) && array[right(p)] < array[left(p)]) ? right(p) : left(p);
                swap(p, the_smaller);
                bubble_down(the_smaller);
            }
            
        }

        void heapify() { // transform an array of elmenet into a heap
            for (int i = next_leaf-1; i >= 0; i--){
                bubble_down(i);
            }
        }

        void double_size() { // double the size of the heap
            int * new_array = new int[capacity*2];
            for (int i = 0; i < capacity; i++){
                new_array[i] = array[i];
            }
            delete[] array;
            array = new_array;
            capacity *= 2;
        }

    public:

        min_heap() : array(new int[10]), next_leaf(0), capacity(10) {
            // initialise capacity to 10, next_leaf to 0
        }
    
        min_heap(int capacity) : array(new int [capacity]), next_leaf(0), capacity(capacity) {
            // initialise capacity using the augment value
        }
    
        min_heap(int * a, int num_elements, int capacity) : array(a), next_leaf(num_elements), capacity(capacity) {
            heapify();
        }
        
        ~min_heap() { // destroy array because it was created dynamically
            delete[] array;
        }

        // this function inserts a new element to heap
        void insert(int i) {
//            Check capacity and increase if full
            if (next_leaf >= capacity) {
                double_size();
            }
//            Store i to next_leaf, then update next_leaf to the next position
            array[next_leaf++] = i;
//            Check bottom-up to maintain order of heap following the path from leaf to root
            bubble_up(next_leaf-1);
        }

        // this function removes an element from heap
        int remove() {
             // Heap only permits removing the root element
            if (next_leaf > 0) {
                // move the last element to root, then update next_leaf position.
                int temp = array[0];
                array[0] = array[--next_leaf];
                // check top-down to maitain order of heap
                bubble_down(0);
                // return the removed element
                return temp;
            }
            return -1;
        }

        int size() { // get the current size (<=capacity) of heap
            return next_leaf;
        }
        
        bool empty() { // check if heap is empty
            return next_leaf == 0;
        }

};

#endif

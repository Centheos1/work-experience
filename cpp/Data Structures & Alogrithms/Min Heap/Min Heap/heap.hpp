//
//  heap.h
//  Min Heap
//
//  Created by Clint Sellen on 20/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef HEAP_H
#define HEAP_H

#include <vector>
#include <iostream>
#include <string>

using namespace std;

template <typename Key_type, typename Value_type, class Compare=less<pair<Key_type, Value_type>>>
class heap {

    typedef pair<Key_type,Value_type> Entry;

    private:

        vector<Entry> array;
        Compare comp = Compare();

        bool has_left(const int& p) const{ // check if p has a left child
            return left(p) < array.size();
        }

        bool has_right(const int& p) const{ // check if p has a right child
            return right(p) < array.size();
        }

        bool has_parent(const int& p) const{ // check if p has parent
            return p > 0; // p does not have a parent only when p==0
        }

        int left(const int& p) const{ // get the left child
            return 2*p + 1;
        }

        int right(const int& p) const{ // get the right child
            return 2*p + 2;
        }

        int parent(const int& p) const{ // get the parent of the current node (i.e., p)
            return (p-1)/2;
        }

        void swap(const int& pos1, const int& pos2) {
            Entry temp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = temp;
        }

        void bubble_up(const int& p) {
            if (has_parent(p) && comp(array[p], array[parent(p)])) {
                swap(p, parent(p));
                bubble_up(parent(p));
            }
        }

        void bubble_down(const int& p) {

            if (has_left(p)) {
                auto the_smaller = has_right(p) && comp(array[right(p)], array[left(p)]) ? right(p) : left(p);
                if (comp(array[the_smaller], array[p])) {
                    swap(p, the_smaller);
                    bubble_down(the_smaller);
                }
            }

        }

        void heapify() {
            for (int i = array.size() - 1; i >= 0; i--){
                bubble_down(i);
            }
        }


    public:

        heap() {
        }

        heap(const vector<Entry>& a) : array(a) {
            heapify();
        }

        heap(const Entry* a) {
            for (Entry e : a){
                array.push_back(e);
            }
            heapify();
        }

        void insert(const Key_type& key, const Value_type& value) {
        
          array.push_back({key, value});
          bubble_up(array.size()-1);
          
        }

        Value_type remove() {

          if (array.size() > 0) {
            Entry temp = array[0];
            array[0] = array.back();
            array.pop_back();
            bubble_down(0);
            return temp.second;
          }
          return Value_type();
          
        }
        
        bool empty(){
            return array.size()==0;
        }

};
  
#endif

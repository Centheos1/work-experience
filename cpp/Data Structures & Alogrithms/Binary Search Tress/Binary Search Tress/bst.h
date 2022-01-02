//
//  bst.h
//  Binary Search Tress
//
//  Created by Clint Sellen on 10/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef BST_H
#define BST_H

class bst {
    
    private:
    
        int tree[50];
        bool in_tree[50] = { false };
    
    public:
    
        void insert(const int&);
        int index_of(const int&);
    
};

void bst::insert(const int& t) {
    
    int pos = 0;
    
    while (pos < 50 && in_tree[pos]){
        pos = t < tree[pos] ? pos = 2*pos+1 : 2*pos+2;
    }
    
    if (pos < 50){
        tree[pos] = t;
        in_tree[pos] = true;
    }
}

int bst::index_of(const int& t) {
    
    int pos = 0;
    
    while (pos < 50 && tree[pos] != t && in_tree[pos]){
        pos = t < tree[pos] ? pos = 2*pos+1 : 2*pos+2;
    }
    
    return pos < 50 && in_tree[pos] ? pos : -1;

}

#endif


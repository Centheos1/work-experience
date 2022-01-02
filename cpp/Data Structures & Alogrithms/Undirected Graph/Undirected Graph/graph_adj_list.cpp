//
//  graph_adj_list.cpp
//  Undirected Graph
//
//  Created by Clint Sellen on 1/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include "graph_adj_list.h"

graph::graph(const size_t& size){
    this->size = size;
    adj_list = new vector<int>[this->size];
}

graph::~graph(){
    delete[] adj_list;
}

void graph::add_edge(const int& u, const int& v){
//    range check
    if (u >= 0 && u < size && v >= 0 && v < size) {
//    check if existing edge
        if (!is_adjacent(u, v)) {
//    add edge to both vectors to maintain symetry
            adj_list[u].push_back(v);
            adj_list[v].push_back(u);
        }
    }
}

void graph::remove_edge(const int& u, const int& v){
//    range check
    if (u >= 0 && u < size && v >= 0 && v < size) {
//    check if in u
        for (int i=0; i<adj_list[u].size(); i++) {
//    remove from u
            if (adj_list[u][i] == v) {
                adj_list[u].erase(adj_list[u].begin() + i);
            }
        }
//    check if in v
        for (int i=0; i<adj_list[v].size(); i++) {
            if (adj_list[v][i] == u) {
//    remove from v
                adj_list[v].erase(adj_list[v].begin() + i);
            }
        }
    }
}

bool graph::is_adjacent(const int& u, const int& v){
    
//    range check
    if (u >= 0 && u < size && v >= 0 && v < size) {
//    traverse the matrix
        for (int i=0; i<adj_list[u].size(); i++) {
//    if found return true
            if (adj_list[u][i] == v) {
                return true;
            }
        }
    }
//    not found return false
    return false;
}

//// Depth first traversal
////vector<int> Graph::dft(const int& start) {
//vector<int> graph::dft(const int& start) {
//    // Your code goes here
//    bool visited[size];
//    for (unsigned int i = 0; i<size; i++) {
//        
//    }
//    
//    
//    vector<int> x = {};
//    return x;
//}
//
//// Breath first traversal
////vector<int> Graph::bft(const int& start){
//vector<int> graph::bft(const int& start){
//    
//    // Your code goes here
//    
//    vector<int> x = {};
//    return x;
//}

//
//  graph.cpp
//  Undirected Graph
//
//  Created by Clint Sellen on 1/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include "graph.h"

graph_arr::graph_arr(const size_t& size){
    cout << "Graph Constructor, size: " << size << endl;
    this->size = size;
    adj_matrix_arr = new bool* [size];
    
    for (int i=0; i<size; i++) {
        adj_matrix_arr[i] = new bool [size];
        for (int j=0; j<size; j++) {
            adj_matrix_arr[i][j] = false;
        }
    }
}

graph_arr::~graph_arr(){
//    cout << "Graph Denstructor" << endl;
    for (int i=0; i<size; i++) {
        delete adj_matrix_arr[i];
    }
    delete[] adj_matrix_arr;
}

void graph_arr::add_edge(const int& u, const int& v){
//    cout << "Graph add_edge, u: " << u << " | v: " << v << endl;
    if (u >= 0 && u < size && v>= 0 && v < size) {
        adj_matrix_arr[u][v] = true;
        adj_matrix_arr[v][u] = true;
    }
//    cout << adj_matrix[u][v] << endl;
    
}

void graph_arr::remove_edge(const int& u, const int& v){
//    cout << "Graph remove_edge, u: " << u << " | v: " << v << endl;
    if (u >= 0 && u < size && v>= 0 && v < size) {
        adj_matrix_arr[u][v] = false;
        adj_matrix_arr[v][u] = false;
    }
}

bool graph_arr::is_adjacent(const int& u, const int& v){
//    cout << "Graph is_adjacent, u: " << u << " | v: " << v << endl;
    if (u >= 0 && u < size && v>= 0 && v < size) {
        return adj_matrix_arr[u][v];
    }
    return false;
}

// Depth first traversal - same as breath first BUT stack in place of queue i.e. LIFO
vector<int> graph_arr::dft(const int& start) {
//    visited nodes
    bool visited[size];
    for (unsigned int i = 0; i<size; i++) {
        visited[i] = false;
    }
    
//    unprocessed nodes
    stack<int> unprocessed;
    unprocessed.push(start);
    
//    ordered graph to return
    vector<int> ordered;
    
//    traverse the graph
    while (!unprocessed.empty()) {
//    start from the top
        int n = unprocessed.top();
//    pop the top of unprocessed stack
        unprocessed.pop();
//    if unvisited mark as visited
        if (!visited[n] == true) {
            visited[n] = true;
//    add to tail of ordered
            ordered.push_back(n);
            for (unsigned int i = int(size); i != 0; i--) {
//    push unvisited nodes in the vertex to the unprocessed stack
                if (adj_matrix_arr[n][i-1]) {
                    unprocessed.push(i-1);
                }
            }
        }
//    repeat
    }
    return ordered;
}

// Breath first traversal - same as depth first BUT queue in place of stack i.e. FIFO
vector<int> graph_arr::bft(const int& start){
//    check range
    if (start < 0 || start > size) {
//        throw out of bounds error
        
    }
    
//    visited nodes
    bool visited[size];
    for (unsigned int i = 0; i<size; i++) {
        visited[i] = false;
    }
    
//    unprocessed nodes
    queue<int> unprocessed;
    unprocessed.push(start);
    
//    ordered graph to return
    vector<int> ordered;
    
//    traverse the graph
    while (!unprocessed.empty()) {
//    start from the top
        int n = unprocessed.front();
//    pop the top of unprocessed stack
        unprocessed.pop();
//    if unvisited mark as visited
        if (!visited[n] == true) {
            visited[n] = true;
//    add to tail of ordered
            ordered.push_back(n);
            for (unsigned int i = 0; i < int(size); i++) {
//    push unvisited nodes in the vertex to the unprocessed stack
                if (adj_matrix_arr[n][i]) {
                    unprocessed.push(i);
                }
            }
        }
//    repeat
    }

    return ordered;
}


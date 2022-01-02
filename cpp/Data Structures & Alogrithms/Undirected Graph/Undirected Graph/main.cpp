//
//  main.cpp
//  Undirected Graph
//
//  Created by Clint Sellen on 1/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <iomanip>
//#include "graph_adj_list.h"
#include "graph.h"

int main(){
//    list driver
//    cout << boolalpha;
//
//    graph g(6);
//
//    g.add_edge(0,1);
//    g.add_edge(0,3);
//    g.add_edge(0,4);
//    g.add_edge(0,5);
//    g.add_edge(1,2);
//    g.add_edge(1,3);
//    g.add_edge(2,4);
//    g.add_edge(3,4);
//    g.add_edge(3,5);
//    g.add_edge(4,5);
//
//    cout << "Is vertex 0 adjacent to vertex 1? " << g.is_adjacent(0,1) << endl;
//    cout << "Is vertex 0 adjacent to vertex 2? " << g.is_adjacent(0,2) << endl;
//    cout << "Is vertex 0 adjacent to vertex 3? " << g.is_adjacent(0,3) << endl;
//    cout << "Is vertex 0 adjacent to vertex 4? " << g.is_adjacent(0,4) << endl;
//    cout << "Is vertex 0 adjacent to vertex 5? " << g.is_adjacent(0,5) << endl;
//    cout << "Is vertex 0 adjacent to itself? " << g.is_adjacent(0,0) << endl;
//
//    cout << "Removing some edges." << endl;
//    g.remove_edge(0,4);
//    g.remove_edge(0,5);
//
//    cout << "Is vertex 0 adjacent to vertex 1? " << g.is_adjacent(0,1) << endl;
//    cout << "Is vertex 0 adjacent to vertex 2? " << g.is_adjacent(0,2) << endl;
//    cout << "Is vertex 0 adjacent to vertex 3? " << g.is_adjacent(0,3) << endl;
//    cout << "Is vertex 0 adjacent to vertex 4? " << g.is_adjacent(0,4) << endl;
//    cout << "Is vertex 0 adjacent to vertex 5? " << g.is_adjacent(0,5) << endl;
//    cout << "Is vertex 0 adjacent to itself? " << g.is_adjacent(0,0) << endl;
    
    
//    array driver
//     set bool variables to show as true/false" rather than "1/0"
    cout << boolalpha;

    graph_arr g(6);

    g.add_edge(0,1);
    g.add_edge(0,3);
    g.add_edge(0,4);
    g.add_edge(0,5);
    g.add_edge(1,2);
    g.add_edge(1,3);
    g.add_edge(2,4);
    g.add_edge(3,4);
    g.add_edge(3,5);
    g.add_edge(4,5);

    cout << "Is vertex 0 adjacent to vertex 1? " << g.is_adjacent(0,1) << endl;
    cout << "Is vertex 0 adjacent to vertex 2? " << g.is_adjacent(0,2) << endl;
    cout << "Is vertex 0 adjacent to vertex 3? " << g.is_adjacent(0,3) << endl;
    cout << "Is vertex 0 adjacent to vertex 4? " << g.is_adjacent(0,4) << endl;
    cout << "Is vertex 0 adjacent to vertex 5? " << g.is_adjacent(0,5) << endl;
    cout << "Is vertex 0 adjacent to itself? " << g.is_adjacent(0,0) << endl;

    cout << "Removing some edges." << endl;
    g.remove_edge(0,4);
    g.remove_edge(0,5);

    cout << "Is vertex 0 adjacent to vertex 1? " << g.is_adjacent(0,1) << endl;
    cout << "Is vertex 0 adjacent to vertex 2? " << g.is_adjacent(0,2) << endl;
    cout << "Is vertex 0 adjacent to vertex 3? " << g.is_adjacent(0,3) << endl;
    cout << "Is vertex 0 adjacent to vertex 4? " << g.is_adjacent(0,4) << endl;
    cout << "Is vertex 0 adjacent to vertex 5? " << g.is_adjacent(0,5) << endl;
    cout << "Is vertex 0 adjacent to itself? " << g.is_adjacent(0,0) << endl;
    
//    g.add_edge(0,1);
//    g.add_edge(0,3);
//    g.add_edge(0,4);
//    g.add_edge(0,5);
//    g.add_edge(1,2);
//    g.add_edge(1,3);
//    g.add_edge(2,4);
//    g.add_edge(3,4);
//    g.add_edge(3,5);
//    g.add_edge(4,5);
    
    vector<int> ord = g.dft(0);
    cout << "Depth-first order starting at 0:" << endl;
    for (auto i : ord){
        cout << i << ' ';
    }
    cout << endl;
    
    ord = g.dft(3);
    cout << "Depth-first order starting at 3:" << endl;
    for (auto i : ord){
        cout << i << ' ';
    }
    cout << endl;
    
    ord = g.bft(2);
    cout << "Breadth-first order starting at 2:" << endl;
    for (auto i : ord){
        cout << i << ' ';
    }
    cout << endl;
    
    ord = g.bft(-1);
    cout << "Breadth-first order starting at 2:" << endl;
    for (auto i : ord){
        cout << i << ' ';
    }
    cout << endl;
}

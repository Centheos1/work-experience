//
//  main.cpp
//  Assignment_1
//
//  Created by Clint Sellen on 5/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
//#include "directed_graph.hpp"
//#include "src/service/graph.hpp"
//#include "graph.hpp"

#include "directed_graph_algorithms.cpp"

#include <unordered_set>

//using namespace std;

//#include "test.h"
//#include <cxxtest/TestSuite.h>




void testBasicConstructor(void){

    cout << "__testBasicConstructor__" << endl;

    cout << endl << "directed_graph<int> g" << endl;
    directed_graph<vertex<int>> g;
    cout << "num_vertices: " << (g.num_vertices() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_vertices(), 0);
    cout << "num_edges: " << (g.num_edges() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_edges(), 0);

    cout << endl  << "directed_graph<std::string> h" << endl;
    directed_graph<vertex<std::string>> h;
    cout << "num_vertices: " << (h.num_vertices() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(h.num_vertices(), 0);
    cout << "num_edges: " << (h.num_edges() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(h.num_edges(), 0);

    cout << endl << "directed_graph<double> i" << endl;
    directed_graph<vertex<double>> i;
    cout << "num_vertices: " << (i.num_vertices() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(i.num_vertices(), 0);
    cout << "num_edges: " << (i.num_edges() == 0 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(i.num_edges(), 0);
}

void testAddVertex(){

    cout << endl << "__testAddVertex__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    for (int i = 0; i < r; ++i) {
        vertex<int> v(i,i);
        g.add_vertex(v);

    }
//    g.get_vertices();
    cout << "num_vertices: " << (g.num_vertices() == r ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_vertices(),r);
}
//
void testContains(){

    cout << endl << "__testContains__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    for (int i = 0; i < r; ++i) {
        int w = rand()%1000;
        vertex<int> v(i, w);
        g.add_vertex(v);
    }

    for (int i = 0; i < r; i++){
        cout << "contains: " << (g.contains(i) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.contains(i));
    }
}

void testRemoveVertex(){

    cout << endl << "__testRemoveVertex__" << endl;

    directed_graph<int> g;
    std::vector<int> in;
    std::vector<int> out;

    int r = std::rand()%20 + 1;

    for (int i = 0; i < r; ++i) {
        int w = rand()%1000;
        vertex<int> v(i, w);
        g.add_vertex(v);
    }

    cout << "num_vertices: " << (g.num_vertices() == r ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_vertices(),r);
    for (int i = 0; i < r; i++){
        cout << "contains: " << (g.contains(i) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.contains(i));
    }

    for (int i = 0; i < r; ++i) {
        if (std::rand()%2 == 0) {
            in.push_back(i);
        }
        else {
            out.push_back(i);
        }
    }

    for (auto i : out) g.remove_vertex(i);
    cout << "remove_vertex - num_vertices: " << (g.num_vertices() == r - out.size()? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_vertices(), r - out.size());
    for (auto i : out){
        cout << "OUT contains: " << (!g.contains(i) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(!g.contains(i));
    }
    for (auto i : in){
        cout << "IN contains: " << (g.contains(i) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.contains(i));
    }

}

void testAddEdge(){
    cout << endl << "__testAddEdge__" << endl;
    directed_graph<int> g;
    int r = std::rand()%20 + 2;
    int m = 0;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
                m++;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    vector<vertex<int>> list = g.get_vertices();

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r; ++j){
            if (re[i][j]){
                g.add_edge(list[i].id, list[j].id, list[j].weight);
            }
        }
    }
    cout << "num_edges: " << (g.num_edges() == m ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_edges(), m);

}


void testAreAdjacent() {

    cout << endl << "__testAreAdjacent__" << endl;

    directed_graph<std::string> h;

    int vertex_size = 4;
    string random_weight;

    cout << "random_weight:" << endl;
    for (int i=1; i <= vertex_size; i++) {

        random_weight = "vertex" + to_string(i) ;
        cout << " " << random_weight << " " ;

        vertex<string> v(i,random_weight);
        h.add_vertex(v);
    }

    h.add_edge(2, 3, "2->3");
    h.add_edge(4, 1, "4->1");
    h.add_edge(1, 3, "1->3");

    cout << "num_edges: " << h.num_edges() << (h.num_edges() == 3 ? " PASS" : " !___FAIL___!\n\n") << endl;

    cout << "2->3 adjacent: " << (h.adjacent(2, 3) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(h.adjacent("vertex2", "vertex3"));

    cout << "4->1 adjacent: " << (h.adjacent(4, 1) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(h.adjacent("vertex4", "vertex1"));

    cout << "1->3 adjacent: " << (h.adjacent(4, 1) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(h.adjacent("vertex1", "vertex3"));

    cout << "NOT 1->2 adjacent: " << (!h.adjacent(1, 2) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex1", "vertex2"));

    cout << "NOT 1->4 adjacent: " << (!h.adjacent(1, 4) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex1", "vertex4"));

    cout << "NOT 2->1 adjacent: " << (!h.adjacent(2, 1) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex2", "vertex1"));

    cout << "NOT 2->4 adjacent: " << (!h.adjacent(2, 4) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex2", "vertex4"));

    cout << "NOT 3->1 adjacent: " << (!h.adjacent(3, 1) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex3", "vertex1"));

    cout << "NOT 3->2 adjacent: " << (!h.adjacent(3, 2) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex3", "vertex2"));

    cout << "NOT 3->4 adjacent: " << (!h.adjacent(3, 4) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex3", "vertex4"));

    cout << "NOT 4>2 adjacent: " << (!h.adjacent(4, 2) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex4", "vertex2"));

    cout << "NOT 4->3 adjacent: " << (!h.adjacent(4, 3) ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT(!h.adjacent("vertex4", "vertex3"));

    directed_graph<int> g;
    int r = std::rand()%20 + 2;
    int m = 0;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
                m++;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%100);
            }
        }
    }

    cout << "num_edges: m: " << m << " num_edges: " << g.num_edges() << (g.num_edges() == m ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_edges(), m);

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
//                cout << "EXISTS: " << i << " -> "<< j << endl;
                cout << i << "->"<< j << " adjacent: " << (g.adjacent(i, j) ? " PASS" : " !___FAIL___!\n\n") << endl;
//                TS_ASSERT(g.adjacent(i,j));
            }
            else {
//                cout << "NOT EXISTS: " << i << " -> "<< j << endl;
                cout << "NOT " << i << "->"<< j <<  "adjacent: " << (!g.adjacent(3, 4) ? " PASS" : " !___FAIL___!\n\n") << endl;
            }
        }
    }
}


void testRemoveEdge(){

    cout << endl << "__testRemoveEdge__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;
    int m = 0;
    int total_weight = 0;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
                m++;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    struct PairHash{
        size_t operator()(const std::pair<int, int> &key) const{
            return std::hash<int>()(key.first) * std::hash<int>()(key.second);
        }
    };

    std::unordered_set<std::pair<int, int>, PairHash > edges;
    std::unordered_set<std::pair<int, int>, PairHash > removed_edges;

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j,rand()%100);

                if (std::rand()%2 == 0) removed_edges.insert({i,j});
                else edges.insert({i,j});
            }
        }
    }

    cout << "Before Remove num_edges: m: " << m << " num_edges: " << g.num_edges() << (g.num_edges() == m ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_edges(), m);

    for (auto e : removed_edges){
        g.remove_edge(e.first, e.second);
    }

    cout << "After Remove num_edges: m: " << m - removed_edges.size() << " num_edges: " << g.num_edges() << (g.num_edges() == m - removed_edges.size() ? " PASS" : " !___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(g.num_edges(), m - removed_edges.size());

    for (auto e : edges){
        cout << e.first << "->"<< e.second << " adjacent: " << (g.adjacent(e.first, e.second) ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.adjacent(e.first, e.second));
    }

    for (auto e : removed_edges){
        cout << "NOT " << e.first << "->"<< e.second <<  "adjacent: " << (!g.adjacent(e.first, e.second) ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT(!g.adjacent(e.first, e.second));
    }

}


void testOutDegree(){

    cout << endl << "__testOutDegree__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r; ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){
        int edge_count = 0;
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                edge_count++;
            }
        }

        cout << "out_degree: g.out_degree(i): " << g.out_degree(i) << " edge_count: "<< edge_count << (g.out_degree(i) == edge_count ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT_EQUALS(g.out_degree(i), edge_count);
    }
}


void testInDegree(){

    cout << endl << "__testInDegree__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r; ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
            }
        }
    }

    for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){
        int edge_count = 0;
        for (int j = 0; j < r;  ++j){
            if (re[j][i]){
                edge_count++;
            }
        }

        cout << "vertex: " << i << " in_degree: " << g.in_degree(i) << " edge_count: "<< edge_count << (g.in_degree(i) == edge_count ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT_EQUALS(g.in_degree(i), edge_count);
    }

}


void testDegree(){

    cout << endl << "__testDegree__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r; ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){
        int edge_count = 0;
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                edge_count++;
            }
            if (re[j][i]) {
              edge_count++;
            }
        }

        cout << "vertex: " << i << " degree: " << g.degree(i) << " edge_count: "<< edge_count << (g.degree(i) == edge_count ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT_EQUALS(g.degree(i), edge_count);
    }

}


void testGetVertices(){

    cout << endl << "__testGetVertices__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    int s = std::rand()%r;

    for (int i = 0; i < r; ++i) g.add_vertex(vertex<int>(i, rand()%10000));;

    vector<vertex<int>> v = g.get_vertices();

    cout << "testGetVertices: num_vertices: " << (r == v.size() ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(r, v.size());

    for (int i = 0; i < v.size(); ++i){
        cout << "testGetVertices contains: " << (g.contains(v[i].id) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.contains(v[i]));
    }

    for (int i = 0; i < v.size(); ++i){
        for (int j = i + 1; j < v.size(); ++j){
            cout << "testGetVertices differs: " << (v[i].id != v[j].id ? "PASS" : "!___FAIL___!\n\n") << endl;
//            TS_ASSERT_DIFFERS(v[i], v[j]);
        }
    }
//
    for (int i = 0; i < s; ++i) g.remove_vertex(i);
//
    v = g.get_vertices();

    cout << "testGetVertices removed: num_vertices: " << (r-s == v.size() ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS(r-s, v.size());

    for (int i = 0; i < v.size(); ++i){
        cout << "testGetVertices removed contains: " << (g.contains(v[i].id) ? "PASS" : "!___FAIL___!\n\n") << endl;
//        TS_ASSERT(g.contains(v[i]));
    }

    for (int i = 0; i < v.size(); ++i){
        for (int j = i + 1; j < v.size(); ++j){
            cout << "testGetVertices removed differs: " << (v[i].id != v[j].id ? "PASS" : "!___FAIL___!\n\n") << endl;
//            TS_ASSERT_DIFFERS(v[i], v[j]);
        }
    }
}


void testGetNeighbours(){

    cout << endl << "__testGetNeighbours__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){
        vector<vertex<int>> n_i = g.get_neighbours(i);
//        std::vector<int> n_i = g.get_neighbours(i);

        cout << "testGetNeighbours: " << g.out_degree(i) << " edge_count: "<< n_i.size() << (g.out_degree(i) == n_i.size() ? " PASS" : " !___FAIL___!\n\n") << endl;
//        TS_ASSERT_EQUALS(g.out_degree(i), n_i.size());

        for (int j = 0; j < n_i.size(); ++j){
            cout << "testGetNeighbours adjacent: " << i << "->"<< n_i[j].id << " adjacent: " << (g.adjacent(i, n_i[j].id) ? " PASS" : " !___FAIL___!\n\n") << endl;
//            TS_ASSERT(g.adjacent(i, n_i[j]));

            cout << "testGetNeighbours re[i][n_i[j]]: " << (re[i][n_i[j].id] ? " PASS" : " !___FAIL___!\n\n") << endl;
//            TS_ASSERT(re[i][n_i[j]]);
        }

        for (int j = 0; j < n_i.size(); ++j){
            for (int k = j + 1; k < n_i.size(); ++k){
                cout << "testGetNeighbours j:" << n_i[j].id << " k: " << n_i[k].id << " differs: " << (n_i[j].id != n_i[k].id ? "PASS" : "!___FAIL___!\n\n") << endl;
//                TS_ASSERT_DIFFERS(n_i[j], n_i[k]);
            }
        }
    }
}


void testGetSecondOrderNeighbours(){

    cout << endl << "__testGetSecondOrderNeighbours__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 2;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%2 == 1 && i != j){
                re[i][j] = true;
            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){
        cout << "Vertex: " << i << endl;
        vector<vertex<int>> n_i = g.get_second_order_neighbours(i);

        for (auto& v : n_i) {
            cout << "Second order neighbours: " << v.id << endl;
        }

    }

}

void testDepthFirst(){

    cout << endl << "__testDepthFirst__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    std::unordered_map<int, std::unordered_set<int> > adj_list;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%7 == 1 && i != j){
                re[i][j] = true;

            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%10000);
            }
        }
    }

    for (int i = 0; i < r; ++i){

        std::unordered_set<int> n;
        for (int j = 0; j < r; ++j){
            if (re[i][j]) n.insert(j);
        }
        adj_list.insert({i,n});

    }

    g.remove_vertex(6);

    int start_vertex = std::rand()%r;
    cout << "Start: " <<  start_vertex <<endl;

    vector<vertex<int>> graph_dfs = g.depth_first(start_vertex);

    cout << endl << "Retruned to __testDepthFirst__" << endl;

    for (auto v : graph_dfs) {
        cout << v.id<< " " << v.weight << endl;
    }

//    vector<vertex<int>> reference_dfs = dfs(adj_list, start_vertex);
//
//    TS_ASSERT_EQUALS(graph_dfs, reference_dfs);

}

void testBreadthFirst(){

    cout << "__testBreadthFirst__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    std::unordered_map<int, std::unordered_set<int> > adj_list;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 1; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()%7 == 1 && i != j){
                re[i][j] = true;

            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%100);
            }
        }
    }

    for (int i = 0; i < r; ++i){

        std::unordered_set<int> n;
        for (int j = 0; j < r; ++j){
            if (re[i][j]) n.insert(j);
        }
        adj_list.insert({i,n});

    }

    int start_vertex = std::rand()%r;
    cout << "Start: " <<  start_vertex <<endl;

    vector<vertex<int>> graph_bfs = g.breadth_first(start_vertex);
//    std::vector<int> reference_bfs = bfs(adj_list, start_vertex);

    cout << endl << "Retruned to __testBreadthFirst__" << endl;

    for (auto v : graph_bfs) {
        cout << v.id<< " " << v.weight << endl;
    }

//    TS_ASSERT_EQUALS(graph_bfs, reference_bfs);

}

void testContainsCycles() {

    cout << "__testContainsCycles__" << endl;

    directed_graph<int> g;
    int r = std::rand()%20 + 1;
    std::unordered_map<int, std::unordered_set<int> > adj_list;

    bool re[r][r];

    for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
            re[i][j] = false;
        }
    }

    for (int i = 1; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (std::rand()% 3 == 1 && i != j){
                re[i][j] = true;

            }
        }
    }

    for (int i = 0; i < r; ++i){
        g.add_vertex(vertex<int>(i, rand()%10000));
    }

    for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
            if (re[i][j]){
                g.add_edge(i,j, rand()%100);
            }
        }
    }

    for (int i = 0; i < r; ++i){

        std::unordered_set<int> n;
        for (int j = 0; j < r; ++j){
            if (re[i][j]) n.insert(j);
        }
        adj_list.insert({i,n});

    }

    bool is_cyclic = g.contain_cycles();
    cout << "\nis_cyclic = " << is_cyclic << endl;

}

void testReachable() {

     cout << "__testReachable__" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 3 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10000));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }

     for (int i = 0; i < r; ++i) {
         for (int j = 0; j < r; ++j) {
//             bool reachable =  g.reachable(i,j);
             string reachable =  g.reachable(i,j) ? " TRUE " : " FALSE";
             cout << i << " reachable " << j << " = " << reachable << endl;

//           TS_ASSERT_EQUALS(g.reachable(i,j), is_reachable(g, i, j));
         }
     }

   }

void testOutTree(){

    cout << "__testOutTree__" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 4 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10000));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }

     directed_graph<int> mst = g.out_tree(0);

    cout << "MST: size = " << mst.get_vertices().size() << endl;
    for (auto x : mst.get_vertices()) {
        cout << x.id << " " << x.weight << " edges: ";
        for (auto y : mst.get_edges(x.id)) {
//            cout << y;
//            cout << " -> " << y.first << " | " << y.second << ", ";
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }
//
//     TS_ASSERT_EQUALS(t.num_vertices(), verts.size());
//     TS_ASSERT_EQUALS(t.num_edges(), t.num_vertices() - 1);
//     for (auto i = 1; i < r; ++i) TS_ASSERT(is_reachable(t, 0, i));
//     for (auto i = 0; i < r; ++i) {
//         for (auto j = 0; j < r; ++j) {
//             if (!re[i][j]) TS_ASSERT(!t.adjacent(i,j));
//         }
//     }

}

void testTraversals(){

    cout << "__testTraversals__" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 3 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10000));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }

     directed_graph<int> mst = g.out_tree(0);

    cout << "MST: size = " << mst.get_vertices().size() << endl;
    for (auto x : mst.get_vertices()) {
        cout << x.id << " " << x.weight << " edges: ";
        for (auto y : mst.get_edges(x.id)) {
//        for (auto y : mst.get_neighbours(x.id)) {
//            cout << y;
//            cout << " -> " << y.first << " | " << y.second << ", ";
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }

    vector<vertex<int>> pre_order_traversal = g.pre_order_traversal(0, mst);

    cout << endl << "pre_order_traversal: ";
    for(auto i : pre_order_traversal) {
        cout  << i.id << ", ";
    }
    cout << endl;

    vector<vertex<int>> in_order_traversal = g.in_order_traversal(0, mst);

    cout << endl << "in_order_traversal: ";
    for(auto i : in_order_traversal) {
        cout  << i.id << ", ";
    }
    cout << endl;

    vector<vertex<int>> post_order_traversal = g.post_order_traversal(0, mst);

    cout << endl << "post_order_traversal: ";
    for(auto i : post_order_traversal) {
        cout  << i.id << ", ";
    }
    cout << endl;

    g.significance_sorting();
}




std::unordered_set<int> generate_random_set(std::size_t size, std::size_t offset = 0) {
  std::unordered_set<int> set;
  while (set.size() < size) {
    set.insert(offset + rand()%1000);
  }
  return set;
}


/*
UP TO HERE
*/
void testDistancesSingleVertex() {
    
    cout << "\n__testDistancesSingleVertex__" << endl;
    
    directed_graph<int> d;
    vertex<int> v = vertex<int>(0, 99);
    d.add_vertex(v);
    auto distances = shortest_path(d, v.id, v.id);
    
    cout << "TS_ASSERT_EQUALS([1], [" << distances.size() << "]" << (distances.size() == 1 ? " PASS" : "!___FAIL___!\n\n") << endl;
    
    bool flag = false;
    for (auto &i : distances) {
        if (i.id == v.id) flag = true;
    }
    
    if (flag) cout << "TS_ASSERT(distances.count(v.id)) PASS" << endl;
    else  cout << "TS_ASSERT(distances.count(v.id)) !___FAIL___!\n\n" << endl;
    
    cout << "TS_ASSERT_EQUALS(0, distances.at(v) == " << (distances.at(v.id).id == 0 ? " PASS" : "!___FAIL___!\n\n") << endl;;
}


void testDistances() {
    
    cout << "\n__testDistances__" << endl;
    
    int i = 0;
    
    auto verts = generate_random_set(15 + rand()%15);
    
    cout << verts.size() << " => verts\n";
    
    vector<vertex<int>> ordered_verts;
    
    
    for (auto &x : verts) {
        ordered_verts.push_back(vertex<int>(i, x));
        i++;
    }
    
    cout << ordered_verts.size() << " => ordered_verts: ";
    for (auto &x : ordered_verts) {
        cout << x.id << "|" << x.weight << " ";
    }
    
    vector<vector<vertex<int>>> layers;

    vector<vertex<int>> layer;
    for (auto i = 5; i < verts.size(); ++i) {
        layer.push_back(ordered_verts[i]);
        if (!(rand() % 5)) {
            layers.push_back(layer);
            layer.clear();
        }
    }

    if (layer.size() > 0) layers.push_back(layer);

    vertex<int> root = ordered_verts[4];

    directed_graph<int> d;
    i = 0;
    for (auto v : verts) {
        d.add_vertex(vertex<int>(i, v));
        i++;
    }

    for (auto v : layers[0]) d.add_edge(root.id, v.id, rand() % 100);

    for (auto i = 1; i < layers.size(); ++i) {

        for (auto v : layers[i]) {

            auto from = layers[i-1][rand()%layers[i-1].size()];
            d.add_edge(from.id, v.id, rand() % 100);

            for (auto u : layers[i-1]) if (!(rand()%4)) d.add_edge(u.id, v.id, rand() % 100);

            for (auto u : layers[i]) if (!(rand()%4)) d.add_edge(u.id, v.id, rand() % 100);
        }
    }

    for (auto i = 0; i < 4; ++i) {
        for (auto j = i + 1; j < verts.size(); ++j) {
            if (!(rand() % 5)) {
                d.add_edge(ordered_verts[i].id, ordered_verts[j].id, rand() % 100);
            }
        }
    }

    int v_id = d.get_size() - rand() % 10;
//    int to_id = 24;

    auto distances = shortest_path(d, root.id, v_id);

//    TS_ASSERT_EQUALS(verts.size(), distances.size());
    cout << "\nTS_ASSERT_EQUALS(verts.size() ["<< verts.size() << "], distances.size() [" << distances.size() << "]): " << (distances.size() == verts.size() ? "PASS" : "!___FAIL___!\n\n") << endl;
//
////    for (auto v : verts) {
////        TS_ASSERT(distances.count(v));
////    }
//    for (auto v : verts) {
//        bool flag = false;
//        for (auto &i : distances) {
//            if (i.id == v) flag = true;
//        }
//
//        if (flag) cout << "TS_ASSERT(distances.count(v.id)) PASS" << endl;
//        else  cout << "TS_ASSERT(distances.count(v.id)) !___FAIL___!\n\n" << endl;
//    }

//    TS_ASSERT_EQUALS(0, distances.at(root));
//    cout << "TS_ASSERT_EQUALS(0, distances[root]): " << (distances.at(root.id) == root.weight ? "PASS" : "!___FAIL___!\n\n") << endl;

//    for (auto i = 0; i < layers.size(); ++i) {
//        for (auto v : layers[i]) {
//            TS_ASSERT_EQUALS(i+1, distances.at(v));
//        }
//    }
//
//    for (auto i = 0; i < 4; ++i) {
//        TS_ASSERT_EQUALS(verts.size() + 1, distances.at(ordered_verts[i]));
//    }
  
}

void testSCCEmptyGraph() {
    cout << "\n__testSCCEmptyGraph__" << endl;
    directed_graph<std::string> d;
    auto c = strongly_connected_components(d);
//  TS_ASSERT(c.empty());
    cout << "is_empty: " << (c.empty() ? "PASS" : "!___FAIL___!\n\n") << endl;
}

void testSCCSingleVertex() {
    
    cout << "\n__testSCCSingleVertex__" << endl;
    
    directed_graph<std::string> d;
    d.add_vertex(vertex<string>( 0, "Only Vertex"));

    vector<vector<vertex<string>>> c = strongly_connected_components(d);

//    TS_ASSERT_EQUALS(1, c.size());
    cout << "\nis_single: " << (c.size() == 1 ? "PASS" : "!___FAIL___!\n\n") << endl;

    auto v = c[0];
//    TS_ASSERT_EQUALS(1, v.size());
    cout << "c[0] is_single: " << (v.size() == 1 ? "PASS" : "!___FAIL___!\n\n") << endl;
//    TS_ASSERT_EQUALS("Only Vertex", v[0]);
    cout << "TS_ASSERT_EQUALS('Only Vertex', v[0]): " << (v[0].weight == "Only Vertex" ? "PASS" : "!___FAIL___!\n\n") << endl;
}


void testSCCEdgelessGraph() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);
    
    cout << "\n________________________________\n";
    std::cout << "verts size: " << verts.size() << " contains: ";
    for (auto i = verts.begin(); i != verts.end(); ++i) {
        std::cout << (*i) << ", ";
    }
    
    std::cout << '\n';

    int i = 0;
    for (auto v : verts) {
        d.add_vertex(vertex<int>(i, v));
        i++;
    }

  vector<vector<vertex<int>>> c = strongly_connected_components(d);

//  TS_ASSERT_EQUALS(verts.size(), c.size());
    cout << "\nTS_ASSERT_EQUALS(verts.size() ["<< verts.size() << "], c.size() [" << c.size() << "]): " << (c.size() == verts.size() ? "PASS" : "!___FAIL___!\n\n") << endl;

    std::unordered_set<int> c_joined;
    for (auto v : c) {
//        TS_ASSERT_EQUALS(1, v.size());
//        cout << v[0].id << " | " << v[0].weight << endl;
        cout << "\nTS_ASSERT_EQUALS([1], v.size() [" << v.size() << "]): " << (v.size() == 1 ? "PASS" : "!___FAIL___!\n\n") << endl;
      
        c_joined.insert(v[0].weight);
    }

//    TS_ASSERT_EQUALS(verts, c_joined);
    cout << "\nTS_ASSERT_EQUALS(verts, c_joined)" << (verts == c_joined ? "PASS" : "!___FAIL___!\n\n") << endl;
    
//    i = 0;
//    for (auto v : verts) {
//        cout << v << ", ";
//        i++;
//    }
//
    for (auto i = verts.begin(); i != verts.end(); ++i) {
        cout << (*i) << ", ";
    }
    cout << endl;
    for (auto i = c_joined.begin(); i != c_joined.end(); ++i) {
        cout << (*i) << ", ";
    }
  
}

template <typename T>
vector<pair<vertex<T>, vertex<T>> > random_tree(const vector<vertex<T>>& vertices){
  
    vector<pair<vertex<T>, vertex<T>> > tree_edges;
    if (!vertices.empty()) {
        vector<vertex<T>> connected;
        vector<vertex<T>> unconnected;
        connected.push_back(vertices[0]);
    
        for (int i = 1; i < vertices.size(); ++i) { unconnected.push_back(vertices[i]); }
    
        while (connected.size() < vertices.size()) {
    
            int index1 = rand()%connected.size();
            int index2 = rand()%unconnected.size();
            vertex<T> u = connected[index1];
            vertex<T> v = unconnected[index2];
            tree_edges.push_back({u,v});
            unconnected.erase(unconnected.begin() + index2);
            connected.push_back(v);
        }
    }
    return tree_edges;
}

template <typename T>
void make_dag(directed_graph<T> & d) {
    
//    vector<T> vertices(d.get.begin(), d.end());
    vector<vertex<T>> vertices = d.get_vertices();

    auto tree_edges = random_tree(vertices);

    for (auto e : tree_edges) d.add_edge(e.first.id, e.second.id, rand()%100 );

    for (auto i = 0; i < vertices.size(); ++i) {
        
        for (auto j = 0; j < vertices.size(); ++j) {
            if (d.reachable(vertices[i].id, vertices[j].id) && rand()%2 == 0)
                d.add_edge(vertices[i].id, vertices[j].id, rand()%100 );
        }
    }
}

void testSCCSingleDagComponent() {
    
    cout << "\n__testSCCSingleDagComponent__" << endl;
    
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    int i = 0;
    for (auto v : verts) {
        d.add_vertex(vertex<int>(i, v));
        i++;
    }

    make_dag(d);

    auto c = strongly_connected_components(d);

//    TS_ASSERT_EQUALS(verts.size(), c.size());
    cout << "\nTS_ASSERT_EQUALS(verts.size() ["<< verts.size() << "], c.size() [" << c.size() << "]): " << (c.size() == verts.size() ? "PASS" : "!___FAIL___!\n\n") << endl;
    
    for (auto comp : c) {//TS_ASSERT_EQUALS(1, comp.size());
        cout << "\nTS_ASSERT_EQUALS([1], v.size() [" << comp.size() << "]): " << (comp.size() == 1 ? "PASS" : "!___FAIL___!\n\n") << endl;
    }

    std::unordered_set<int> c_joined;
//    for (auto comp : c) c_joined.insert(comp[0]);
//        std::unordered_set<int> c_joined;
    for (auto v : c) {
        c_joined.insert(v[0].weight);
    }

//    TS_ASSERT_EQUALS(verts, c_joined);
    cout << "\nTS_ASSERT_EQUALS(verts, c_joined)" << (verts == c_joined ? "PASS" : "!___FAIL___!\n\n") << endl;
}


void testStronglyConnectedComponents() {
    
 cout << "testStronglyConnectedComponents" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 5 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

//     for (int i = 0; i < r; ++i){
//         g.add_vertex(vertex<int>(i, rand()%10));
//     }
//
//     for (int i = 0; i < r; ++i){
//         for (int j = 0; j < r;  ++j){
//             if (re[i][j]){
//                 g.add_edge(i,j, rand()%100);
//             }
//         }
//     }

    g.add_vertex(vertex<int>(0, 800)); // A
    g.add_vertex(vertex<int>(1, 300)); // B
    g.add_vertex(vertex<int>(2, 400)); // C
    g.add_vertex(vertex<int>(3, 710)); // D
    g.add_vertex(vertex<int>(4, 221)); // E
    
    g.add_edge(0, 1, 600);  // A -> B | 600
    g.add_edge(0, 2, 900);  // A -> c | 900
    g.add_edge(1, 4, 3000); // B -> E | 3000
    g.add_edge(2, 3, 4000); // A -> B | 600
    g.add_edge(3, 0, 1);    // D -> A | 1
    g.add_edge(3, 2, 700);    // D -> A | 1
    g.add_edge(3, 4, 500);  // D -> E | 500
    
    
    cout << "Graph: size = " << g.get_vertices().size() << endl;
    for (auto x : g.get_vertices()) {
        cout << x.id << "\tweight: " << x.weight << "\tedges: ";
        for (auto y : g.get_edges(x.id)) {
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }
    
    vector<vector<vertex<int>>> components = strongly_connected_components(g);
    
    int i = 1;
    for (auto &c : components) {
        cout << "component " << i << ": ";
        for (auto &x : c) {
            cout << x.id << ", ";
        }
        i++;
        cout << endl;
    }

}


void testTopologicalSort() {
    
 cout << "testTopologicalSort" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 3 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }

    cout << "Graph: size = " << g.get_vertices().size() << endl;
    for (auto x : g.get_vertices()) {
        cout << x.id << "\tweight: " << x.weight << "\tedges: ";
        for (auto y : g.get_edges(x.id)) {
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }
    
    directed_graph<int> mst = g.out_tree(0);

//    cout << "MST: size = " << mst.get_vertices().size() << endl;
//    for (auto x : mst.get_vertices()) {
//        cout << "id:\t" << x.id << "\tweight:\t" << x.weight << "\tedges:\t";
//        for (auto y : mst.get_edges(x.id)) {
//            cout << " -> " << y.id << " | " << y.weight << ", ";
//        }
//        cout << endl;
//    }
    
    vector<vertex<int>> topo_order = topological_sort(mst);
    
    cout << "topo order: ";
    for (auto &x : topo_order) {
        cout << x.id << ", ";
    }

}


void testLowCostDelivery() {
    
 cout << "testLowCostDelivery" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 2000;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
//         if (std::rand() % 3 == 1 && i != j){
           if (std::rand() % (r/3) == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%100));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }
    
//    g.add_vertex(vertex<int>(0, 800)); // A
//    g.add_vertex(vertex<int>(1, 300)); // B
//    g.add_vertex(vertex<int>(2, 400)); // C
//    g.add_vertex(vertex<int>(3, 710)); // D
//    g.add_vertex(vertex<int>(4, 221)); // E
//
//    g.add_edge(0, 1, 600);  // A -> B | 600
//    g.add_edge(0, 2, 900);  // A -> c | 900
//    g.add_edge(1, 4, 3000); // B -> E | 3000
//    g.add_edge(2, 3, 4000); // A -> B | 600
//    g.add_edge(3, 0, 1);    // D -> A | 1
//    g.add_edge(3, 2, 700);    // D -> A | 1
//    g.add_edge(3, 4, 500);  // D -> E | 500

    cout << "Graph: size = " << g.get_vertices().size() << endl;
//    for (auto x : g.get_vertices()) {
//        cout << x.id << "\tweight: " << x.weight << "\tedges: ";
//        for (auto y : g.get_edges(x.id)) {
//            cout << " -> " << y.id << " | " << y.weight << ", ";
//        }
//        cout << endl;
//    }
    
    int u = 0;
    
    int result = low_cost_delivery(g, u);
    
    cout << "low_cost_delivery = " << result << endl;
    
    cout << "num_edges: " << g.num_edges() << " num_vertex: " << g.num_vertices() << endl;

}

void testShortestPath() {
    
 cout << "testShortestPath" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 20;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 9 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

//     for (int i = 0; i < r; ++i){
//         g.add_vertex(vertex<int>(i, rand()%10));
//     }
//
//     for (int i = 0; i < r; ++i){
//         for (int j = 0; j < r;  ++j){
//             if (re[i][j]){
//                 g.add_edge(i,j, rand()%100);
//             }
//         }
//     }
    
    for (int i = 1; i <= 9; i++){
        g.add_vertex(vertex<int>(i, rand()%10));
    }

    g.add_edge(1,2,8);
    g.add_edge(1,3,3);
    g.add_edge(1,6,13);
    g.add_edge(2,3,2);
    g.add_edge(2,4,1);
    g.add_edge(3,2,3);
    g.add_edge(3,4,9);
    g.add_edge(3,5,2);
    g.add_edge(4,5,4);
    g.add_edge(4,7,6);
    g.add_edge(4,8,2);
    g.add_edge(5,1,5);
    g.add_edge(5,4,6);
    g.add_edge(5,6,5);
    g.add_edge(5,9,4);
    g.add_edge(6,7,1);
    g.add_edge(6,9,7);
    g.add_edge(7,5,3);
    g.add_edge(7,8,4);
    g.add_edge(8,9,1);
    g.add_edge(9,7,5);

    cout << "Graph: size = " << g.get_vertices().size() << endl;
    for (auto x : g.get_vertices()) {
        cout << x.id << "\tweight: " << x.weight << "\tedges: ";
        for (auto y : g.get_edges(x.id)) {
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }
    
//    vector<vertex<int>> edges;
//    for (int i = 0; i < g.num_vertices(); i++) {
//        cout << "{ ";
//        edges = g.get_edges(i);
//        for (int j = 0; j < g.num_vertices(); j++) {
//            auto weight = INT_MIN;
//            for (auto &e : edges) {
//                if (e.id == j) weight = e.weight;
//            }
//            cout << weight << ", ";
//        }
//        cout << "},\n";
//    }
    
//    cout << wtf_test();
    
    int u = 9;
    int v = 2;
    vector<vertex<int>> sp = shortest_path(g, u, v);


    cout  << endl << "Shortest Path = ";
    for (auto &x : sp) {
        cout << x.id << " ";
    }
    cout << endl;
    
////    for (int u = 0; u < g.num_vertices(); u++) {
//    for (int u = 1; u <= g.num_vertices(); u++) {
////    int u = 0;
//
//        for (int v = 0; v < g.num_vertices(); v++) {
//
//            vector<vertex<int>> sp = shortest_path(g, u, v);
//
////                cout << "\nwtf_test()\n";
//
//                cout  << endl << "Shortest Path " << u << " -> " << v << " = ";
//                for (auto &x : sp) {
//                    cout << x.id << "|" << x.weight << " ";
//                }
//                cout << endl;
//        }
//
//    }

}


void testShortestPathAssignmentGraph() {
    
 cout << "testShortestPathAssignmentGraph" << endl;

     directed_graph<int> g;
     int r = std::rand()%20 + 20;

     bool re[r][r];

     for (int i = 0; i < r; ++i){
       for (int  j = 0; j < r; ++j){
         re[i][j] = false;
       }
     }

     for (int i = 0; i < r; ++i){
       for (int j = 0; j < r; ++j){
         if (std::rand() % 9 == 1 && i != j){
             re[i][j] = true;
         }
       }
     }

     for (int i = 0; i < r; ++i){
         g.add_vertex(vertex<int>(i, rand()%10));
     }

     for (int i = 0; i < r; ++i){
         for (int j = 0; j < r;  ++j){
             if (re[i][j]){
                 g.add_edge(i,j, rand()%100);
             }
         }
     }
    
//    g.add_vertex(vertex<int>(0, 800)); // A
//    g.add_vertex(vertex<int>(1, 300)); // B
//    g.add_vertex(vertex<int>(2, 400)); // C
//    g.add_vertex(vertex<int>(3, 710)); // D
//    g.add_vertex(vertex<int>(4, 221)); // E
//
//    g.add_edge(0, 1, 600);  // A -> B | 600
//    g.add_edge(0, 2, 900);  // A -> c | 900
//    g.add_edge(1, 4, 3000); // B -> E | 3000
//    g.add_edge(2, 3, 4000); // A -> B | 600
//    g.add_edge(3, 0, 1);    // D -> A | 1
//    g.add_edge(3, 2, 700);    // D -> A | 1
//    g.add_edge(3, 4, 500);  // D -> E | 500

    cout << "Graph: size = " << g.get_vertices().size() << endl;
    for (auto x : g.get_vertices()) {
        cout << x.id << "\tweight: " << x.weight << "\tedges: ";
        for (auto y : g.get_edges(x.id)) {
            cout << " -> " << y.id << " | " << y.weight << ", ";
        }
        cout << endl;
    }
    
//    cout << wtf_test();
//    vector<vertex<int>> sp = shortest_path(g, 0, 4);
//    int u = 2;
//    int v = 4;
    
    for (int u = 0; u < g.num_vertices(); u++) {
        
        for (int v = 0; v < g.num_vertices(); v++) {
            
            vector<vertex<int>> sp = shortest_path(g, u, v);
            
//                cout << "\nwtf_test()\n";
                
                cout  << endl << "Shortest Path " << u << " -> " << v << " = ";
                for (auto &x : sp) {
                    cout << x.id << " ";
                }
                cout << endl;
        }
        
    }
    
        
    //    a            0
    //    b            600
    //    c            900
    //    d            4900
    //    e            3600
        
    //    Vertex   Distance from Source Dijkstra
    //    0          0
    //    1          600
    //    2          900
    //    3          4900
    //    4          3600

}

// MAIN
int main(int argc, const char * argv[]) {
    // insert code here...
    std::cout << "Hello, World!\n";

//    testBasicConstructor();
//    testAddVertex();
//    testContains();
//    testRemoveVertex();
//    testAddEdge();
//    testAreAdjacent();
//    testRemoveEdge();
//    testOutDegree();
//    testInDegree();
//    testDegree();
//    testGetVertices();
//    testGetNeighbours();
//    testGetSecondOrderNeighbours();
//    testDepthFirst();
//    testBreadthFirst();
//    testContainsCycles();
//    testReachable();
//    testOutTree();
//    testTraversals();
    
//    testShortestPath();
//    testShortestPathAssignmentGraph();
//    testDistancesSingleVertex();
//    testDistances();
    
//    testSCCEmptyGraph();
//    testSCCSingleVertex();
//    testSCCEdgelessGraph();
//    testSCCSingleDagComponent();
    
//    testStronglyConnectedComponents();
    
//    testTopologicalSort();
    testLowCostDelivery();

    std::cout << "\nBye, World!\n";
    return 0;
}

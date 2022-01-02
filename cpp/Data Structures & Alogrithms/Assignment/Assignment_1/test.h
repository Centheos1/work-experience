//
//  test.h
//  Assignment_1
//
//  Created by Clint Sellen on 5/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#define CXXTEST_HAVE_EH
#define CXXTEST_ABORT_TEST_ON_FAIL
#include <cxxtest/TestSuite.h>
#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <sstream>
#include <ctime>
#include <unordered_set>

#include "directed_graph.hpp"
#include "test_helper.cpp"

class Management : public CxxTest::GlobalFixture{

    public:

    bool setUpWorld(){
        std::srand(std::time(0));
        return true;
    }

    bool tearDownWorld(){
        return true;
    }

};

static Management management;

class Assignment1Tests : public CxxTest::TestSuite{

    public:
//passed
    void testBasicConstructor(void){

        directed_graph<int> g;
        TS_ASSERT_EQUALS(g.num_vertices(), 0);
        TS_ASSERT_EQUALS(g.num_edges(), 0);

        directed_graph<std::string> h;
        TS_ASSERT_EQUALS(h.num_vertices(), 0);
        TS_ASSERT_EQUALS(h.num_edges(), 0);

        directed_graph<double> i;
        TS_ASSERT_EQUALS(i.num_vertices(), 0);
        TS_ASSERT_EQUALS(i.num_edges(), 0);
    }
//passed
    void testAddVertex(){

        directed_graph<int> g;
        int r = std::rand()%20 + 1;
        for (int i = 0; i < r; ++i) g.add_vertex(i);

        TS_ASSERT_EQUALS(g.num_vertices(),r);
    }
//passed
    void testContains(){

        directed_graph<int> g;
        int r = std::rand()%20 + 1;
        for (int i = 0; i < r; ++i) g.add_vertex(i);

        for (int i = 0; i < r; i++){
            TS_ASSERT(g.contains(i));
        }
    }
//passed
    void testRemoveVertex(){

        directed_graph<int> g;
        std::vector<int> in;
        std::vector<int> out;

        int r = std::rand()%20 + 1;

        for (int i = 0; i < r; ++i) g.add_vertex(i);

        TS_ASSERT_EQUALS(g.num_vertices(),r);
        for (int i = 0; i < r; i++){
            TS_ASSERT(g.contains(i));
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

        TS_ASSERT_EQUALS(g.num_vertices(), r - out.size());
        for (auto i : out){
            TS_ASSERT(!g.contains(i));
        }
        for (auto i : in){
            TS_ASSERT(g.contains(i));
        }

    }
//passed
    void testAddEdge(){

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r; ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
                }
            }
        }

        TS_ASSERT_EQUALS(g.num_edges(), m);

    }
//passed
    void testAreAdjacent() {
        
        directed_graph<std::string> h;
        
        h.add_vertex("vertex1");
        h.add_vertex("vertex2");
        h.add_vertex("vertex3");
        h.add_vertex("vertex4");
        
        h.add_edge("vertex2", "vertex3");
        h.add_edge("vertex4", "vertex1");
        h.add_edge("vertex1", "vertex3");
        
        TS_ASSERT(h.adjacent("vertex2", "vertex3"));
        TS_ASSERT(h.adjacent("vertex4", "vertex1"));
        TS_ASSERT(h.adjacent("vertex1", "vertex3"));
        TS_ASSERT(!h.adjacent("vertex1", "vertex2"));
        TS_ASSERT(!h.adjacent("vertex1", "vertex4"));
        TS_ASSERT(!h.adjacent("vertex2", "vertex1"));
        TS_ASSERT(!h.adjacent("vertex2", "vertex4"));
        TS_ASSERT(!h.adjacent("vertex3", "vertex1"));
        TS_ASSERT(!h.adjacent("vertex3", "vertex2"));
        TS_ASSERT(!h.adjacent("vertex3", "vertex4"));
        TS_ASSERT(!h.adjacent("vertex4", "vertex2"));
        TS_ASSERT(!h.adjacent("vertex4", "vertex3"));

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
                }
            }
        }

        TS_ASSERT_EQUALS(g.num_edges(), m);

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    TS_ASSERT(g.adjacent(i,j));
                }
                else {
                    TS_ASSERT(!g.adjacent(i,j));
                }
            }
        }

    }
//passed
    void testRemoveEdge(){

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
            g.add_vertex(i);
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
                    g.add_edge(i,j);

                    if (std::rand()%2 == 0) removed_edges.insert({i,j});
                    else edges.insert({i,j});
                }
            }
        }

        TS_ASSERT_EQUALS(g.num_edges(), m);

        for (auto e : removed_edges){
            g.remove_edge(e.first, e.second);
        }

        TS_ASSERT_EQUALS(g.num_edges(), m - removed_edges.size());

        for (auto e : edges){
            TS_ASSERT(g.adjacent(e.first, e.second));
        }

        for (auto e : removed_edges){
            TS_ASSERT(!g.adjacent(e.first, e.second));
        }

    }
//passed
    void testOutDegree(){

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
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
            TS_ASSERT_EQUALS(g.out_degree(i), edge_count);
        }

    }
//passed
    void testInDegree(){

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
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
            TS_ASSERT_EQUALS(g.in_degree(i), edge_count);
        }

    }
//passed
    void testDegree(){

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
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
            TS_ASSERT_EQUALS(g.degree(i), edge_count);
        }

    }
//passed
    void testGetVertices(){

        directed_graph<int> g;
        int r = std::rand()%20 + 1;
        int s = std::rand()%r;

        for (int i = 0; i < r; ++i) g.add_vertex(i);

        std::vector<int> v = g.get_vertices();
        TS_ASSERT_EQUALS(r, v.size());
        for (int i = 0; i < v.size(); ++i){
            TS_ASSERT(g.contains(v[i]));
        }

        for (int i = 0; i < v.size(); ++i){
            for (int j = i + 1; j < v.size(); ++j){
                TS_ASSERT_DIFFERS(v[i], v[j]);
            }
        }

        for (int i = 0; i < s; ++i) g.remove_vertex(i);

        v = g.get_vertices();
        TS_ASSERT_EQUALS(r-s, v.size());
        for (int i = 0; i < v.size(); ++i){
            TS_ASSERT(g.contains(v[i]));
        }

        for (int i = 0; i < v.size(); ++i){
            for (int j = i + 1; j < v.size(); ++j){
                TS_ASSERT_DIFFERS(v[i], v[j]);
            }
        }
    }
//passed
    void testGetNeighbours(){

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
                }
            }
        }

        for (int i = 0; i < r; ++i){
            std::vector<int> n_i = g.get_neighbours(i);
            TS_ASSERT_EQUALS(g.out_degree(i), n_i.size());
            for (int j = 0; j < n_i.size(); ++j){
                TS_ASSERT(g.adjacent(i, n_i[j]));
                TS_ASSERT(re[i][n_i[j]]);
            }

            for (int j = 0; j < n_i.size(); ++j){
                for (int k = j + 1; k < n_i.size(); ++k){
                    TS_ASSERT_DIFFERS(n_i[j], n_i[k]);
                }
            }
        }
    }
//passed
    void testDepthFirst(){

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
                if (std::rand()%2 == 1 && i != j){
                    re[i][j] = true;
                    
                }
            }
        }

        for (int i = 0; i < r; ++i){
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
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

        std::vector<int> graph_dfs = g.depth_first(start_vertex);
        std::vector<int> reference_dfs = dfs(adj_list, start_vertex);
        TS_ASSERT_EQUALS(graph_dfs, reference_dfs);

    }
//passed
    void testBreadthFirst(){

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
                if (std::rand()%2 == 1 && i != j){
                    re[i][j] = true;
                    
                }
            }
        }

        for (int i = 0; i < r; ++i){
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
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

        std::vector<int> graph_bfs = g.breadth_first(start_vertex);
        std::vector<int> reference_bfs = bfs(adj_list, start_vertex);

        TS_ASSERT_EQUALS(graph_bfs, reference_bfs);

    }

    void testOutTree(){
      
      directed_graph<int> g;
      std::vector<int> verts;
      int r = std::rand()%20 + 1;
      
      bool re[r][r];
      
      for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
          re[i][j] = false;
        }
      }
      
      for (int i = 0; i < r; ++i){
        g.add_vertex(i);
        verts.push_back(i);
      }
      
      std::vector<std::pair<int, int> > tree_edges = random_tree(verts);
      
      for (auto edge : tree_edges){
        g.add_edge(edge.first, edge.second);
        re[edge.first][edge.second] = true;
      }
      
      for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
          if (std::rand()%2 == 1 && re[i][j] == 0 && i != j){
            re[i][j] = true;
            g.add_edge(i,j);
          }
        }
      }
      
      directed_graph<int> t = g.out_tree(0);

      TS_ASSERT_EQUALS(t.num_vertices(), verts.size());
      TS_ASSERT_EQUALS(t.num_edges(), t.num_vertices() - 1);
      for (auto i = 1; i < r; ++i) TS_ASSERT(is_reachable(t, 0, i));
      for (auto i = 0; i < r; ++i) {
          for (auto j = 0; j < r; ++j) {
              if (!re[i][j]) TS_ASSERT(!t.adjacent(i,j));
          }
      }
      
    }

    void testInTree() {
      
      directed_graph<int> g;
      std::vector<int> verts;
      int r = std::rand()%20 + 1;
      
      bool re[r][r];
      
      for (int i = 0; i < r; ++i){
        for (int  j = 0; j < r; ++j){
          re[i][j] = false;
        }
      }
      
      for (int i = 0; i < r; ++i){
        g.add_vertex(i);
        verts.push_back(i);
      }
      
      std::vector<std::pair<int, int> > tree_edges = random_tree(verts);
      
      for (auto edge : tree_edges){
        g.add_edge(edge.second, edge.first);
        re[edge.second][edge.first] = true;
      }
      
      for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
          if (std::rand()%2 == 1 && re[i][j] == 0 && i != j){
            re[i][j] = true;
            g.add_edge(i,j);
          }
        }
      }
      
      directed_graph<int> t = g.in_tree(0);

      TS_ASSERT_EQUALS(t.num_vertices(), verts.size());
      TS_ASSERT_EQUALS(t.num_edges(), t.num_vertices() - 1);
      for (auto i = 1; i < r; ++i) TS_ASSERT(is_reachable(t, i, 0));
      for (auto i = 0; i < r; ++i) {
          for (auto j = 0; j < r; ++j) {
              if (!re[i][j]) TS_ASSERT(!t.adjacent(i,j));
          }
      }
    }

    void testReachable() {
      
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
        g.add_vertex(i);
      }
      
      for (int i = 0; i < r; ++i){
        for (int j = 0; j < r;  ++j){
          if (re[i][j]){
        g.add_edge(i,j);
          }
        }
      }

      for (int i = 0; i < r; ++i) {
        for (int j = 0; j < r; ++j) {
          TS_ASSERT_EQUALS(g.reachable(i,j), is_reachable(g, i, j));
        }
      }
      
    }

    void testGraphIterator(){

        directed_graph<int> g;
        int r = std::rand()%20 + 1;

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
                }
            }
        }

        std::unordered_set<int> it_vertices;
        for (auto v : g){
            it_vertices.insert(v);
        }

        TS_ASSERT_EQUALS(it_vertices.size(), r);

        for (auto u : g.get_vertices()){
            TS_ASSERT_LESS_THAN(0, it_vertices.count(u));
        }
    }

    void testNeighbourIterator(){

        directed_graph<int> g;
        int r = std::rand()%20 + 1;

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
            g.add_vertex(i);
        }

        for (int i = 0; i < r; ++i){
            for (int j = 0; j < r;  ++j){
                if (re[i][j]){
                    g.add_edge(i,j);
                }
            }
        }

        for (auto u = 0; u < r; u++){

            std::unordered_set<int> it_neighbours;
            for (auto n = g.nbegin(u); n != g.nend(u); ++n){
                it_neighbours.insert(*n);
            }

            int u_degree = 0;
            for (int i = 0; i < r; i++){
                if (re[u][i] > 0) u_degree++;
            }

            TS_ASSERT_EQUALS(it_neighbours.size(), u_degree);
            for (auto v : g.get_neighbours(u)){
                TS_ASSERT_LESS_THAN(0, it_neighbours.count(v));
            }

        }

    }

};

/*
 Assignment 2
 */

#define CXXTEST_HAVE_EH
#define CXXTEST_ABORT_TEST_ON_FAIL
#include <cxxtest/TestSuite.h>
#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <sstream>
#include <ctime>
#include <unordered_set>

#include "directed_graph.hpp"
#include "directed_graph_algorithms.cpp"

template <typename T>
std::unordered_set<T> vec_to_u_set(std::vector<T> & v) {
  return std::unordered_set<T>(v.begin(), v.end());
}

template <typename vertex>
std::vector<std::pair<vertex, vertex> > random_tree(const std::vector<vertex>& vertices){
  
  std::vector<std::pair<vertex, vertex> > tree_edges;
  if (!vertices.empty()){
    std::vector<vertex> connected;
    std::vector<vertex> unconnected;
    connected.push_back(vertices[0]);
    
    for (int i = 1; i < vertices.size(); ++i) unconnected.push_back(vertices[i]);
    
    while (connected.size() < vertices.size()){
    
      int index1 = std::rand()%connected.size();
      int index2 = std::rand()%unconnected.size();
      vertex u = connected[index1];
      vertex v = unconnected[index2];
      tree_edges.push_back({u,v});
      unconnected.erase(unconnected.begin() + index2);
      connected.push_back(v);
    
    }
  }
  return tree_edges;

}

template <typename vertex>
bool is_reachable(directed_graph<vertex>& g, vertex a, vertex b) {

  bool neighbours_added = true;
  std::unordered_set<vertex> n;
  n.insert(a);
  
  while (neighbours_added) {
    auto start_size = n.size();
    for (auto u : n) {
      for (auto nit = g.nbegin(u); nit != g.nend(u); ++nit) {
    n.insert(*nit);
      }
    }
    neighbours_added = n.size() > start_size;
  }

  return n.count(b) > 0;
    
}

std::unordered_set<int> generate_random_set(std::size_t size, std::size_t offset = 0) {
  std::unordered_set<int> set;
  while (set.size() < size) {
    set.insert(offset + rand()%1000);
  }
  return set;
}

template <typename T>
void make_dag(directed_graph<T> & d) {
  std::vector<T> vertices(d.begin(), d.end());
  
  auto tree_edges = random_tree(vertices);

  for (auto e : tree_edges) d.add_edge(e.first, e.second);
  
  for (auto i = 0; i < vertices.size(); ++i) {
    for (auto j = 0; j < vertices.size(); ++j) {
      if (is_reachable(d, vertices[i], vertices[j]) && rand()%2 == 0)
    d.add_edge(vertices[i], vertices[j]);
    }
  }
  
}

template <typename T>
void make_strongly_connected(directed_graph<T> & d) {
  std::vector<T> vertices(d.begin(), d.end());

  for (auto i = 0; i < vertices.size(); ++i) {
    d.add_edge(vertices[i%vertices.size()], vertices[(i+1)%vertices.size()]);
  }

  for (auto u : vertices) {
    for (auto v : vertices) {
      if (rand()%2) d.add_edge(u,v);
    }
  }
}

template <typename T>
void merge_digraph(directed_graph<T> & to, directed_graph<T> & from) {

  for (auto u : from) to.add_vertex(u);

  for (auto u : from) {
    for (auto nit = from.nbegin(u); nit != from.nend(u); ++nit) {
      to.add_edge(u, *nit);
    }
  }
  
}

template <typename T>
directed_graph<T> random_digraph(std::size_t num_vertices) {

  auto verts = generate_random_set(15 + rand()%15);

  directed_graph<T> d;

  for (auto v : verts) d.add_vertex(v);

  for (auto u : verts) {
    for (auto v : verts) {
      if (rand()%2) d.add_edge(u,v);
    }
  }

  return d;
  
}

template <typename T>
std::unordered_set<T> find_roots(const directed_graph<T> d) {
  std::unordered_set<T> roots;
  for (auto v : d) if (d.in_degree(v) == 0) roots.insert(v);
  return roots;
}

class Management : public CxxTest::GlobalFixture{

public:

  bool setUpWorld(){
    std::srand(std::time(0));
    CxxTest::setAbortTestOnFail(true);
    return true;
  }

  bool tearDownWorld(){
    return true;
  }

};

static Management management;

class Assignment2Tests : public CxxTest::TestSuite{

public:

  void testIsDagEmptyGraph() {
    directed_graph<std::string> d;
    TS_ASSERT(is_dag(d));
  }

  void testIsDagSingleVertex() {
    directed_graph<std::string> d;
    d.add_vertex("I'm a vertex");
    TS_ASSERT(is_dag(d));
  }

  void testIsDagCompletelyDisconnected() {
    directed_graph<int> d;
    auto verts = generate_random_set(5 + rand()%20);
    for (auto v : verts) d.add_vertex(v);
    TS_ASSERT(is_dag(d));
  }

  void testIsDagSimpleDag() {
    auto verts = generate_random_set(5 + rand()%20);
    std::vector<int> ordered_verts(verts.begin(), verts.end());

    directed_graph<int> d;
    for (auto v : ordered_verts) d.add_vertex(v);

    auto tree_edges = random_tree(ordered_verts);

    for (auto e : tree_edges) d.add_edge(e.first, e.second);

    TS_ASSERT(is_dag(d));
  }

  void testIsDagComplicatedDag() {
    auto verts = generate_random_set(5 + rand()%20);

    directed_graph<int> d;

    for (auto v : verts) d.add_vertex(v);

    make_dag(d);

    TS_ASSERT(is_dag(d));
  }

  void testIsDagDisconnectedDag() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());

    directed_graph<int> d;

    int start = 0;
    int end = 5 + rand()%10;

    while (start < verts.size()) {
      directed_graph<int> component;
      for (auto i = start; i < end && i < verts.size(); ++i)
    component.add_vertex(*(ordered_verts.begin() + i));
      make_dag(component);
      merge_digraph(d, component);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }
    
    TS_ASSERT(is_dag(d));

  }

  void testIsDagSimpleNonDag() {

    directed_graph<std::string> d;

    d.add_vertex("vertex 1");
    d.add_vertex("vertex 2");

    d.add_edge("vertex 1", "vertex 2");
    d.add_edge("vertex 2", "vertex 1");

    TS_ASSERT(!is_dag(d));
  }

  void testIsDagBiggerNonDag() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    
    for (auto i = 2; i < verts.size(); ++i) d.add_vertex(ordered_verts[i]);

    make_strongly_connected(d);

    d.add_vertex(ordered_verts[0]);
    d.add_vertex(ordered_verts[1]);
    d.add_edge(ordered_verts[0], ordered_verts[rand()%13 + 2]);
    d.add_edge(ordered_verts[rand()%13 + 2], ordered_verts[1]);

    for (auto i = 1; i < verts.size(); ++i) if (rand()%2) d.add_edge(ordered_verts[0], ordered_verts[i]);
    for (auto i = 2; i < verts.size(); ++i) if (rand()%2) d.add_edge(ordered_verts[i], ordered_verts[1]);
    
    TS_ASSERT(!is_dag(d));

    directed_graph<int> e;
    for (auto v : verts) e.add_vertex(v);
    make_strongly_connected(e);

    TS_ASSERT(!is_dag(e));

    directed_graph<int> f;
    std::unordered_set<int> connections;
    auto assured_element = 1 + rand()%(verts.size() - 1);
    connections.insert(ordered_verts[assured_element]);
    for (auto i = 1; i < ordered_verts.size(); ++i) {
      f.add_vertex(ordered_verts[i]);
      if (!(rand()%10)) connections.insert(ordered_verts[i]);
    }
    make_strongly_connected(f);
    f.add_vertex(ordered_verts[0]);
    for (auto v : connections) f.add_edge(ordered_verts[0], v);

    TS_ASSERT(!is_dag(f));
    
  }

  void testIsDagDisconnectedNonDag() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    make_strongly_connected(d);

    int r = 1 + rand()%5;
    for (auto i = 0; i < r; ++i) {
      auto t = random_digraph<int>(5 + rand()%10);
      merge_digraph(d, t);
    }

    TS_ASSERT(!is_dag(d));
    
  }

  void testTopologicalSortEmptyGraph() {
    directed_graph<std::string> d;
    auto topo_order = topological_sort(d);
    TS_ASSERT_EQUALS(0, topo_order.size());
  }

  void testTopologicalSortConnectedGraph() {
    directed_graph<int> d;

    auto verts = generate_random_set(15 + rand()%15);
    for (auto v : verts) d.add_vertex(v);

    make_dag(d);

    auto topo_order = topological_sort(d);

    TS_ASSERT_EQUALS(d.num_vertices(), topo_order.size());

    for (auto i = topo_order.begin(); i != topo_order.end(); ++i) {
      for (auto j = topo_order.begin(); j != i; ++j) {
    TS_ASSERT(!d.adjacent(*i, *j));
      }
    }
  }

  void testTopologicalSortDisconnectedGraph() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    
    directed_graph<int> d;
    
    int start = 0;
    int end = 5 + rand()%10;
    
    while (start < verts.size()) {
      directed_graph<int> component;
      for (auto i = start; i < end && i < verts.size(); ++i)
    component.add_vertex(*(ordered_verts.begin() + i));
      make_dag(component);
      merge_digraph(d, component);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }

    auto topo_order = topological_sort(d);

    TS_ASSERT_EQUALS(d.num_vertices(), topo_order.size());

    for (auto i = topo_order.begin(); i != topo_order.end(); ++i) {
      for (auto j = topo_order.begin(); j != i; ++j) {
    TS_ASSERT(!d.adjacent(*i, *j));
      }
    }
    
  }

  void testIsHamiltonianDagEmptyGraph() {
    directed_graph<std::string> d;
    TS_ASSERT(is_hamiltonian_dag(d));
  }

  void testIsHamiltonianDagOneVertex() {
    directed_graph<std::string> d;
    d.add_vertex("Only Vertex");
    TS_ASSERT(is_hamiltonian_dag(d));
  }

  void testIsHamiltonianDagPath() {

    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());

    directed_graph<int> d;

    for (auto v : verts)
      d.add_vertex(v);

    for (auto i = 0; i < ordered_verts.size() - 1; ++i)
      d.add_edge(ordered_verts[i], ordered_verts[i+1]);

    TS_ASSERT(is_hamiltonian_dag(d));
    
  }

  void testIsHamiltonianDagWithPath() {

    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());

    directed_graph<int> d;

    for (auto v : verts)
      d.add_vertex(v);

    for (auto i = 0; i < ordered_verts.size() - 1; ++i)
      d.add_edge(ordered_verts[i], ordered_verts[i+1]);

    for (auto i = 0; i < ordered_verts.size(); ++i) {
      for (auto j = i + 1; j < ordered_verts.size(); ++j) {
    if (rand()%2) d.add_edge(ordered_verts[i], ordered_verts[j]);
      }
    }

    TS_ASSERT(is_hamiltonian_dag(d));
    
  }

  void testIsHamiltonianDagNoPath() {

    auto verts = generate_random_set(30 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    
    directed_graph<int> left;
    directed_graph<int> right;

    for (auto i = 0; i < ordered_verts.size(); ++i) {
      if (rand()%2) left.add_vertex(ordered_verts[i]);
      else right.add_vertex(ordered_verts[i]);
    }

    make_dag(left);
    make_dag(right);

    directed_graph<int> d;

    merge_digraph(d, left);
    merge_digraph(d, right);

    TS_ASSERT(!is_hamiltonian_dag(d));

    auto root = 1001 + rand()%1000;
    d.add_vertex(root);
    d.add_edge(root, *find_roots(left).begin());
    d.add_edge(root, *find_roots(right).begin());

    TS_ASSERT(!is_hamiltonian_dag(d));

    auto extras = generate_random_set(10, 2001);

    for (auto v : extras) {
      d.add_vertex(v);
      for (auto u : verts) {
    if (!(rand()%10)) d.add_edge(v,u);
      }
    }

    TS_ASSERT(!is_hamiltonian_dag(d));
    
  }

  void testComponentsEmptyGraph() {
    directed_graph<std::string> d;
    auto c = components(d);
    TS_ASSERT(c.empty());
  }

  void testComponentsSingleVertex() {
    directed_graph<std::string> d;
    d.add_vertex("Only Vertex");

    auto c = components(d);
    
    TS_ASSERT_EQUALS(1, c.size());

    auto v = c[0];
    TS_ASSERT_EQUALS(1, v.size());
    TS_ASSERT_EQUALS("Only Vertex", v[0]);
  }

  void testComponentsEdgelessGraph() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    auto c = components(d);

    TS_ASSERT_EQUALS(verts.size(), c.size());

    std::unordered_set<int> c_joined;
    for (auto v : c) {
      TS_ASSERT_EQUALS(1, v.size());
      c_joined.insert(v[0]);
    }

    TS_ASSERT_EQUALS(verts, c_joined);
    
  }

  void testComponentsSingleDagComponent() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    make_dag(d);

    auto c = components(d);

    TS_ASSERT_EQUALS(1, c.size());
    TS_ASSERT_EQUALS(verts.size(), c[0].size());

    std::unordered_set<int> c_joined;
    for (auto v : c[0]) c_joined.insert(v);

    TS_ASSERT_EQUALS(verts, c_joined);
  }

  void testComponentsSingleStronglyConnectedComponent() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    make_strongly_connected(d);

    auto c = components(d);

    TS_ASSERT_EQUALS(1, c.size());
    TS_ASSERT_EQUALS(verts.size(), c[0].size());

    std::unordered_set<int> c_joined;
    for (auto v  : c[0]) c_joined.insert(v);

    TS_ASSERT_EQUALS(verts, c_joined);
  }

  void testComponentsSeveralComponents() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    std::vector<std::unordered_set<int>> comps;
    
    directed_graph<int> d;
    
    int start = 0;
    int end = 5 + rand()%10;
    
    while (start < verts.size()) {
      directed_graph<int> component_graph;
      std::unordered_set<int> component_vertices;
      for (auto i = start; i < end && i < verts.size(); ++i) {
    component_graph.add_vertex(*(ordered_verts.begin() + i));
    component_vertices.insert(*(ordered_verts.begin() + i));
      }
      if (rand()%2) make_dag(component_graph);
      else make_strongly_connected(component_graph);
      merge_digraph(d, component_graph);
      comps.push_back(component_vertices);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }

    auto c = components(d);

    TS_ASSERT_EQUALS(comps.size(), c.size());

    bool found[comps.size()];

    for (auto i = 0; i < comps.size(); ++i) {
      for (auto comp : c) {
    found[i] = found[i] || (comps[i] == vec_to_u_set(comp));
      }
    }

    for (auto b : found) {
      TS_ASSERT(b);
    }
  }

// passed
  void testSCCEmptyGraph() {
    directed_graph<std::string> d;
    auto c = strongly_connected_components(d);
    TS_ASSERT(c.empty());
  }

//    passed
  void testSCCSingleVertex() {
    directed_graph<std::string> d;
    d.add_vertex("Only Vertex");

    auto c = strongly_connected_components(d);
    
    TS_ASSERT_EQUALS(1, c.size());

    auto v = c[0];
    TS_ASSERT_EQUALS(1, v.size());
    TS_ASSERT_EQUALS("Only Vertex", v[0]);
  }

 //    passed
  void testSCCEdgelessGraph() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(verts.size(), c.size());

    std::unordered_set<int> c_joined;
    for (auto v : c) {
      TS_ASSERT_EQUALS(1, v.size());
      c_joined.insert(v[0]);
    }

    TS_ASSERT_EQUALS(verts, c_joined);
    
  }

  void testSCCSingleDagComponent() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    make_dag(d);

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(verts.size(), c.size());
    for (auto comp : c) TS_ASSERT_EQUALS(1, comp.size());

    std::unordered_set<int> c_joined;
    for (auto comp : c) c_joined.insert(comp[0]);

    TS_ASSERT_EQUALS(verts, c_joined);
  }

  void testSCCSingleStronglyConnectedComponent() {
    directed_graph<int> d;
    auto verts = generate_random_set(15 + rand()%15);

    for (auto v : verts) d.add_vertex(v);

    make_strongly_connected(d);

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(1, c.size());
    TS_ASSERT_EQUALS(verts.size(), c[0].size());

    TS_ASSERT_EQUALS(verts, vec_to_u_set(c[0]));
  }

  void testSCCSeveralSCCs() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    std::vector<std::unordered_set<int>> comps;
    
    directed_graph<int> d;
    
    int start = 0;
    int end = 5 + rand()%10;
    
    while (start < verts.size()) {
      directed_graph<int> component_graph;
      std::unordered_set<int> component_vertices;
      for (auto i = start; i < end && i < verts.size(); ++i) {
    component_graph.add_vertex(*(ordered_verts.begin() + i));
    component_vertices.insert(*(ordered_verts.begin() + i));
      }
      make_strongly_connected(component_graph);
      merge_digraph(d, component_graph);
      comps.push_back(component_vertices);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(comps.size(), c.size());

    bool found[comps.size()];

    for (auto i = 0; i < comps.size(); ++i) {
      for (auto comp : c) {
    found[i] = found[i] || (comps[i] == vec_to_u_set(comp));
      }
    }

    for (auto b : found) {
      TS_ASSERT(b);
    }
  }

  void testSCCSeveralDags() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    
    directed_graph<int> d;
    
    int start = 0;
    int end = 5 + rand()%10;
    
    while (start < verts.size()) {
      directed_graph<int> component_graph;
      for (auto i = start; i < end && i < verts.size(); ++i)
    component_graph.add_vertex(*(ordered_verts.begin() + i));
      make_dag(component_graph);
      merge_digraph(d, component_graph);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(verts.size(), c.size());

    std::unordered_set<int> c_joined;
    
    for (auto comp : c) {
      TS_ASSERT_EQUALS(1, comp.size());
      c_joined.insert(comp[0]);
    }

    TS_ASSERT_EQUALS(verts, c_joined);
  }

  void testSCCTreeOfSCC() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    std::vector<std::vector<int>> comps;
    
    directed_graph<int> d;
    
    int start = 0;
    int end = 5 + rand()%10;
    
    while (start < verts.size()) {
      directed_graph<int> component_graph;
      std::vector<int> component_vertices;
      for (auto i = start; i < end && i < verts.size(); ++i) {
    component_graph.add_vertex(*(ordered_verts.begin() + i));
    component_vertices.push_back(*(ordered_verts.begin() + i));
      }
      make_strongly_connected(component_graph);
      merge_digraph(d, component_graph);
      comps.push_back(component_vertices);
      start = end;
      if (verts.size() - start > 0) end = 5 + start + rand()%(verts.size() - start);
    }

    for (auto i = 1; i < comps.size(); ++i) {
      auto other_comp = rand()%i;
      auto from = comps[other_comp][rand()%comps[other_comp].size()];
      auto to = comps[i][rand()%comps[i].size()];
      d.add_edge(from, to);
    }

    auto c = strongly_connected_components(d);

    TS_ASSERT_EQUALS(comps.size(), c.size());

    bool found[comps.size()];

    for (auto i = 0; i < comps.size(); ++i) {
      for (auto comp : c) {
    found[i] = found[i] || (vec_to_u_set(comps[i]) == vec_to_u_set(comp));
      }
    }

    for (auto b : found) {
      TS_ASSERT(b);
    }
  }

  void testDistancesSingleVertex() {
    directed_graph<std::string> d;
    std::string v = "Only Vertex";
    d.add_vertex(v);
    auto distances = shortest_distances(d, v);
    TS_ASSERT_EQUALS(1, distances.size());
    TS_ASSERT(distances.count(v));
    TS_ASSERT_EQUALS(0, distances.at(v));
  }

  void testDistances() {
    auto verts = generate_random_set(15 + rand()%15);
    std::vector<int> ordered_verts(verts.begin(), verts.end());
    std::vector<std::vector<int>> layers;
    
    std::vector<int> layer;
    for (auto i = 5; i < verts.size(); ++i) {
      layer.push_back(ordered_verts[i]);
      if (!(rand()%5)) {
    layers.push_back(layer);
    layer.clear();
      }
    }

    if (layer.size() > 0) layers.push_back(layer);

    auto root = ordered_verts[4];

    directed_graph<int> d;
    for (auto v : verts) d.add_vertex(v);

    for (auto v : layers[0]) d.add_edge(root, v);

    for (auto i = 1; i < layers.size(); ++i) {
      for (auto v : layers[i]) {
         auto from = layers[i-1][rand()%layers[i-1].size()];
         d.add_edge(from, v);
         for (auto u : layers[i-1])
           if (!(rand()%4)) d.add_edge(u,v);

         for (auto u : layers[i])
           if (!(rand()%4)) d.add_edge(u,v);
      }
    }

    for (auto i = 0; i < 4; ++i) {
      for (auto j = i + 1; j < verts.size(); ++j) {
    if (!(rand()%5)) {
      d.add_edge(ordered_verts[i], ordered_verts[j]);
    }
      }
    }

    auto distances = shortest_distances(d, root);

    TS_ASSERT_EQUALS(verts.size(), distances.size());
    for (auto v : verts) {
      TS_ASSERT(distances.count(v));
    }

    TS_ASSERT_EQUALS(0, distances.at(root));

    for (auto i = 0; i < layers.size(); ++i) {
      for (auto v : layers[i]) {
         TS_ASSERT_EQUALS(i+1, distances.at(v));
      }
    }

    for (auto i = 0; i < 4; ++i) {
      TS_ASSERT_EQUALS(verts.size() + 1, distances.at(ordered_verts[i]));
    }
    
  }
  
};

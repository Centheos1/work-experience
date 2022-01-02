//
//  main.cpp
//  Dijkstras Algorithm
//
//  Created by Clint Sellen on 3/6/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <limits.h>
#include <cstddef>
#include <string>
#include <utility>
#include <map>
#include <vector>

using namespace std;

//#define V 27
#define V 9

int minDistance(int dist[], bool sptSet[]) {
   int min = INT_MAX, min_index = INT_MIN;
   for (int v = 0; v < V; v++)
       if (sptSet[v] == false && dist[v] <= min) {
           min = dist[v];
           min_index = v;
       }
   return min_index;
    
}

void printSolution(int dist[], map<int,int> paths, int src) {
    cout << endl;
    
//    char v[] = { 'a', 'b', 'c', 'd', 'e' };
    cout << "Vertex \t\tDistance from Source\n";
    for (int i = 0; i < V; i++) {
      printf("%d \t\t %d\n", i, dist[i]);
//        cout << v[i] << "\t\t\t" << dist[i] << endl;
    }

    cout << endl;
    
//    for (auto &i : paths) {
//        cout << i.first << " -> " << i.second << endl;
//    }
    
    //    if (paths[to].weight == INT_MAX) {
    //    if (paths.size() != (g.num_vertices() -1) ) {
    if (paths[src] == INT_MAX) {

        cout << "\n\t" << paths[src] << endl;
        cout << "NO SHORTEST PATH\n";

    }
    cout << endl;

    vector<int> sp;
    
    for (int i=0; i<V; i++) {
        
        sp.clear();
            
//        int to = 1;
        int to = i;
        sp.push_back(to);
        
        if (paths[to] == INT_MAX || paths[to] == INT_MIN) {

//            if (logs) cout << "\n\tto: " << to << " # " << paths[to].to_id << "->" << paths[to].from_id << "|" << paths[to].weight << endl;
//            cout << "\nNO SHORTEST PATH: " << src << "->" << to;
            cout << "\nNO SHORTEST PATH: " << (src + 1) << "->" << (to + 1);
        } else {
        
//            cout << endl << "Cost [" << dist[to] << "] Shortest Path " << " " << src << "->" << to << " = ";
            cout << endl << "Cost [" << dist[to] << "] Shortest Path " << " " << (src + 1) << "->" << (to + 1) << " = ";
            
            while (to != src) {

                int temp = paths[to];
                
//                cout << "to: " << to ;
//                cout << " edge: " << temp << endl;
                
                sp.push_back(temp);
                to = temp;
            }
            
            reverse(sp.begin(),sp.end());

            
            for (auto &x : sp) {
                
                if (x == INT_MAX) {
                    cout << " inf ";
                } else {
//                    cout << x << " ";
                    cout << (x + 1) << " ";
                }
            }
        }
    }
    cout << endl;
}

void dijkstra(int graph[V][V], int src) {
    
    map<int,int> paths;
//    map<int,map<int, int>> paths;
    map<int, int> temp;
    map<int,int>::iterator it;
    
//    
//    paths.at(src).insert({src, INT_MIN});
    
//    for (auto &v : g.get_vertices()) {
    for (int i=0; i < V; i++){
//        map<int, int> temp;
//        temp.clear();
//        if (i == src) {
//            temp.insert({i, INT_MIN});
//            paths.insert({i, temp});
//        }
//        else {
////            temp.insert({i, INT_MAX});
////            map<int, int>
//            paths.insert({i, temp});
//        }
        
        if (i == src) {
            paths.insert({i, INT_MIN});
        } else {
            paths.insert({i, INT_MAX});
        }
        
    }
    
//    for (int i=0; i<V; i++) {
//
//        it = paths.at(i).find(i);
//        if (it != paths[i].end()) {
//            cout << "FOUND " << i << endl;
//            cout << i << ": " << paths.at(i).size() << endl;
//            for (auto &j : paths.at(i)) {
//                cout << j.first << " " << j.second << endl;
//            }
//        }
//    }
    
    int dist[V];
    bool shortestpathSet[V];
    for (int i = 0; i < V; i++) {
        dist[i] = INT_MAX;
        shortestpathSet[i] = false;
    }
    dist[src] = 0;
    for (int count = 0; count < V - 1; count++) {
        int u = minDistance(dist, shortestpathSet);
        shortestpathSet[u] = true;
        for (int v = 0; v < V; v++)
//            if (!shortestpathSet[v] && graph[u][v] != INT_MIN && dist[u] != INT_MAX && dist[u] + graph[u][v] < dist[v]) {
            if (!shortestpathSet[v] && graph[u][v] && dist[u] != INT_MAX && dist[u] + graph[u][v] < dist[v]) {
                dist[v] = dist[u] + graph[u][v];
                
                paths[v] = u;
                
//                if (adj_list[u_id].find(v_id) == adj_list[u_id].end()) {
//                    adj_list[u_id].insert({v_id, uv_weight});
//                }
            }
    }
   printSolution(dist, paths, src);
}

int main() {
    
    int graph[V][V] = {
//        1  2  3  4  5  6  7  8  9
        { 0, 8, 3, 0, 0, 13, 0, 0, 0 }, // 1
        { 0, 0, 2, 1, 0, 0, 0, 0, 0 }, // 2
        { 0, 3, 0, 9, 2, 0, 0, 0, 0 }, // 3
        { 0, 0, 0, 0, 4, 0, 6, 2, 0 }, // 4
        { 5, 0, 0, 6, 0, 5, 0, 0, 4 }, // 5
        { 0, 0, 0, 0, 0, 0, 1, 0, 7 }, // 6
        { 0, 0, 0, 0, 3, 0, 0, 4, 0 }, // 7
        { 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 8
        { 0, 0, 0, 0, 0, 0, 5, 0, 0 } // 9
    };
    
//    int graph[V][V] = {
//        { 0, 0, 0, 3, 0, 0, 0, 0 },
//        { 0, 0, 4, 9, 0, 0, 9, 0 },
//        { 0, 4 ,0 ,0, 6, 4, 1, 0 },
//        { 0, 0, 0, 0, 0, 0, 0, 0 },
//        { 0, 0, 8, 0, 0, 0, 0, 0 },
//        { 0, 8, 0, 4, 0, 0, 0, 0 },
//        { 0, 0, 0, 0, 0, 0, 0, 3 },
//        { 0, 0, 0, 0, 0, 0, 1, 0 }
//    };
    
//    int graph[V][V] = {
////        0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17
//        { 0, 0, 0, 0, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 0
//        { 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 1
//        { 0, 0, 0, 9, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 2
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0 }, // 3
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 4
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 5
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0 }, // 6
//        { 8, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0 }, // 7
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0 }, // 8
//        { 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 3, 0, 0, 7, 0, 0, 0, 0 }, // 9
//        { 0, 0, 0, 9, 0, 0, 2, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0 }, // 10
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 11
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0 }, // 12
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2 }, // 13
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 14
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 9, 0, 0, 5 }, // 15
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0 }, // 16
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  // 17
//    };
    
//    int graph[V][V] = {
//        { 0, 0, 0, 0, 0, 0, 0, 0, 81, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65, 0, 2, 0, 0, 0, 0, 0, 0  },     // 0
//        { 0, 0, 0, 0, 0, 32, 0, 0, 32, 54, 0, 0, 59, 0, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 62  },// 1
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },      // 2
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0  },      // 3
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 77, 0, 0, 0, 0, 41, 0, 0, 0, 0, 36, 20, 0  },  // 4
//        { 0, 0, 0, 0, 0, 0, 0, 0, 99, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 59, 0, 0, 0, 0 },     // 5
//        { 0, 0, 56, 0, 0, 0, 0, 0, 0, 0, 0, 90, 0, 0, 0, 0, 0, 0, 52, 0, 0, 0, 0, 48, 0, 0, 0  },   // 6
//        { 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0, 0, 0, 0, 0  },     // 7
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 50, 0, 0, 1, 0, 0  },     // 8
//        { 0, 95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 31, 1, 0, 0, 0  },    // 9
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },       // 10
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 0, 0, 0, 0, 0, 0, 0  },      // 11
//        { 61, 0, 0, 0, 0, 0, 0, 0, 57, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28, 0, 0, 0  },   // 12
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0, 0, 0, 0, 0  },      // 13
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 69, 96, 0, 0, 0, 3  },    // 14
//        { 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },      // 15
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },       // 16
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 18, 0, 0, 0  },     // 17
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 62  },     // 18
//        { 0, 0, 0, 0, 0, 0, 0, 0, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },      // 19
//        { 0, 0, 63, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 0, 0, 0, 0, 89, 0, 0, 0, 0, 0, 50, 0, 0, 0  },   // 20
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },      // 21
//        { 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },     // 22
//        { 0, 0, 0, 0, 94, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  },     // 23
//        { 0, 0, 58, 0, 0, 0, 0, 0, 0, 0, 0, 52, 0, 0, 0, 0, 0, 0, 92, 0, 0, 16, 0, 0, 0, 0, 0  },   // 24
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 0, 0, 0, 0  },    // 26
//        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 68, 0, 41, 0, 0, 0, 0, 0  }     // 27
//    };
    
//    int graph[V][V] = {
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 81, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 65, -2147483648, 2, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 32, -2147483648, -2147483648, 32, 54, -2147483648, -2147483648, 59, -2147483648, 47, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 77, 62 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 10, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 19, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 50, -2147483648, 77, -2147483648, -2147483648, -2147483648, -2147483648, 41, -2147483648, -2147483648, -2147483648, -2147483648, 36, 20, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 99, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 12, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 59, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, 56, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 90, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 52, -2147483648, -2147483648, -2147483648, -2147483648, 48, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, 14, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 44, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 18, -2147483648, 50, -2147483648, -2147483648, 1, -2147483648, -2147483648 },
//    { -2147483648, 95, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 21, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 31, 1, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 45, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { 61, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 57, -2147483648, -2147483648, 10, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 28, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 5, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 73, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 37, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 69, 96, -2147483648, -2147483648, -2147483648, 3 },
//    { 21, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 75, 18, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, 0, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 89, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 62 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 54, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, 63, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 43, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 89, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 50, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 35, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 15, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 64, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, 94, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 63, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, 58, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 52, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 92, -2147483648, -2147483648, 16, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 14, -2147483648, 20, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 60, -2147483648, -2147483648, -2147483648, -2147483648 },
//    { -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648, 50, -2147483648, -2147483648, -2147483648, 68, -2147483648, 41, -2147483648, -2147483648, -2147483648, -2147483648, -2147483648 }
//    };
    
//    int graph[V][V] = {
//        { 0, 600, 900, 0, 0 },
//        { 0, 0, 0, 0, 3000 },
//        { 0, 0, 0, 4000, 0 },
//        { 800, 0, 700, 0, 0 },
//        { 0, 0, 0, 0, 0 }
//    };
    
//    dijkstra(graph, 0);
    
    for (int i=0; i < V; i++) {
//    for (int i=1; i <= V; i++) {
        dijkstra(graph, i);
    }
    
    return 0;
}

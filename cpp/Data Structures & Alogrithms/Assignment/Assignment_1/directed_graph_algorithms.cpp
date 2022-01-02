//
//  directed_graph_algorithms.cpp
//  Assignment_1
//
//  Created by Clint Sellen on 27/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <unordered_set>
#include <unordered_map>
#include <set>
#include <array>
#include <list>
#include <forward_list>
#include <deque>
#include <map>
#include <cstddef>
#include <string>
#include <utility>
#include <algorithm>
#include <limits>
#include <optional>
#include <exception>
#include <stdexcept>
#include <queue>

#include "directed_graph.hpp"

using namespace std;

//Turn on/off console logs
static bool logs = true;

/*
 Reference: Erickson, J. (2019). Algorithms (pp. 283-287)
 Reference: https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html
 Reference: https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-using-priority_queue-stl/
 Reference: https://www.geeksforgeeks.org/find-paths-given-source-destination/
 
 pseduo code from: Algorithms, Jeff Erickson
 NonnegativeDijkstra(s):
    InitSSSP(s)
    for all vertices v
        Insert(v,dist(v))
    while the priority queue is not empty
        u ← ExtractMin( )
        for all edges u->􏰘v
            if u􏰘v is tense Relax(u􏰘v) => tense := dist(u)+w(u􏰘v) < dist(v)
                DecreaseKey(v,dist(v))
 
 params:
    directed_graph<T>
    int - source vertex
 returns:
    unordered_map<int, edge<T>>
 
 Time complexity: O(E log V)
 */
template <typename T>
unordered_map<int, edge<T>> dijkstra(directed_graph<T>& g, int src) {
    
    if (logs) cout << "Inside Dijkstra" << endl;
    
    unordered_map<int, edge<T>> paths;
    
// Create a map for distances and initialize all distances as infinite (INF)
    unordered_map<int, T> dist;
    
    for (auto &v : g.get_vertices()) {
        if (v.id == src) {
            paths.insert({v.id, edge<T>(v.id, v.id, INT_MIN)});
            dist.insert({v.id, INT_MIN});
        } else {
            paths.insert({v.id, edge<T>(v.id, INT_MAX, INT_MAX)});
            dist.insert({v.id, INT_MAX});
        }
    }
    
// iPair =>  Pair<weight, vertex id>
    typedef pair<T, int> iPair;
    
// Create a priority queue to store vertices that are being preprocessed. This is weird syntax in C++.
    priority_queue< iPair, vector <iPair> , greater<iPair> > pq;
 
// Insert source itself in priority queue and initialize its distance as 0.
   pq.push(make_pair(0, src));
   dist[src] = 0;
 
// Looping till priority queue becomes empty (or all distances are not finalized)
   while (!pq.empty())
   {
//       Extract minimum vertex from priority queue.
       int u = pq.top().second;
       pq.pop();
 
//       For each adjacent vertex
       for (auto &i : g.get_edges(u)) {
           
           if (logs) cout << i.id << "|" << i.weight << "\n";
           
           int v = i.id;
           T weight = i.weight;
 
//           If there is shorter path to v through u.
           if (dist[v] > dist[u] + weight)
           {
               // Update distance of v
               dist[v] = dist[u] + weight;
               pq.push(make_pair(dist[v], v));
               
               if (logs) cout << "\tUpdating distance of v: "<< i.id << "|" << i.weight << "\n";
               
//               Update path to v
//               This reutrns a map with the path weight from u to v
               paths[v] = edge<T>({u, v, weight});
               
//               This reutrns a map with the total path weight to v
//               paths[v] = edge<T>({u, v, dist[u] + weight});
           }
       }
   }
 
    if (logs) {
        // Print shortest distances stored in dist[]
        printf("Vertex   Distance from Source Dijkstra\n");
        for (int i = 0; i < g.num_vertices(); ++i)
           printf("%d \t\t %d\n", i, dist[i]);
    }
    return paths;
}


/*
 Computes the shortest distance from u to v in graph g.
 The shortest path corresponds to a sequence of vertices starting from u and ends at v,
 which has the smallest total weight of edges among all possible paths from u to v.
 
 dependant:
    dijkstra()
 params:
    directed_graph<T>
    int - source vertex
    int - destination vertex
 returns:
    vector<vertex<T>> - vector containing shortest path from source to desination
 */
template <typename T>
vector<vertex<T>> shortest_path(directed_graph<T>& g, int& u_id, int& v_id) {
    
    if (logs) cout << "Shortest Path: " << u_id << " -> " << v_id << endl;
    
    unordered_map<int, edge<T>> paths;
    vector<vertex<T>> sp;

    paths = dijkstra(g, u_id);

    if (logs) cout << "\nPaths [" << paths.size() << "] == Vertices [" << g.num_vertices() << "]\n\t: " << u_id << "->" << v_id << " = " << paths[u_id].weight << endl;

    if (logs) {
        cout << "\nDijkstra Result: " << paths.size() << endl;
        for (auto &i : paths) {
            cout << i.first << ": " << i.second.from_id << " -> " << i.second.to_id << " | " << i.second.weight << ", " << endl;
        }
    }

    int to = v_id;

//    No shortest path from u to v
    if (paths[to].to_id == INT_MAX || paths[to].to_id != v_id) {

        if (logs) cout << "\n\tto: " << to << " # " << paths[to].to_id << "->" << paths[to].from_id << "|" << paths[to].weight << endl;
        if (logs) cout << "NO SHORTEST PATH\n";

        return vector<vertex<T>>();
    }

//    Build shortest path vertex
    while (to != u_id) {

        edge<T> temp = paths[to];

        if (logs) cout << "to: " << "edge: " << temp.to_id << " -> " << temp.from_id << " | " << temp.weight << endl;

        sp.push_back(g.get_vertex(temp.to_id));
        to = temp.from_id;
    }

    sp.push_back(g.get_vertex(u_id));
    reverse(sp.begin(),sp.end());

    return sp;
}

/*
 Tarjan Part 1
 Reference: Erickson, J. (2019). Algorithms (pp. 235-242)
 Reference: https://www.geeksforgeeks.org/tarjan-algorithm-find-strongly-connected-components/
 
 pseduo code from from: lecture slides
 set<set<vertex<T>>> tarjan(directed_graph<T> d) {
     int index = 0;
     stack<T> s;
     for (each vertex v in d) {
         if (index[v] is undefined) {
             components.add(strongconnect(v));
         }
     }
 }

 pseduo code from from: Algorithms, Jeff Erickson
 Tarjan(G):
     clock ← 0
     S ← new empty stack
     for all vertices v
         unmark v
         v.root ← None
     for all vertices v
         if v is unmarked
             TarjanDFS(v)
 
 dependanacies:
    tarjan_dfs()
    get_size()
    get_vertices()
 params:
    directed_graph<T>
 returns:
    vector<vector<vertex<T>>> - vector of vectors containing all strongly connected components
 
 Time Complexity: O(V + E)
 */
template <typename T>
vector<vector<vertex<T>>> tarjan(directed_graph<T> g) {
    
    if (logs) cout << "\ntarjan: g.size(): " << g.get_size() << endl;
    
//    Initialise
    vector<vector<vertex<T>>> components;
    stack<int> *s = new stack<int>;
    
    int *index = new int[g.get_size()];
    fill_n(index, g.get_size(), INT_MAX);
    
    int *low = new int[g.get_size()];
    fill_n(low, g.get_size(), INT_MIN);
    
    bool *on_stack = new bool[g.get_size()];
    fill_n(on_stack, g.get_size(), false);
    
    int idx = 0;
    
//    Enter the depth first traversal
    for (auto &v : g.get_vertices()) {
        
        if (index[v.id] == INT_MAX) {
            
            if (logs) cout << "\ntarjan: v: " << v.id << endl;
            
            components = tarjan_dfs(v, idx, index, low, on_stack, s, g, components);
        }
    }
    
    if (logs) {
        cout << "Components: ";
        for (auto &i : components){
            for (auto &j : i) {
                cout << j.id << ", ";
            }
        }
        cout<< endl;
    }
    
    return components;
}

/*
 Tarjan Part 2
 Reference: Erickson, J. (2019). Algorithms (pp. 235-242)
 Reference: https://www.geeksforgeeks.org/tarjan-algorithm-find-strongly-connected-components/
 
 pseduo code from from: lecture slides
 set<vertex<T>> strongconnect(vertex<T> v) {
    index[v] = index;
    low[v] = index;
    index++;
    s.push(v);
    on_stack[v] = true;

    for (each edge<T> (v, w) in d) {
        if (index[w] is undefined) {
            strongconnect(w);
            low[v] = min(low[v], low[w])
        } else if (w.onStack) {
            low[v] = min(low[v], index[w])
        }
    }

    if (low[v] = index[v]) {
        set<vertex<T>> new_component;
        do {
            w = s.pop();
            on_stack[w] = false;
            new_compontent.add(w)
        } while (w != v);
        return new_component;
    }
}

 pseduo code from from: Algorithms, Jeff Erickson
 TarjanDFS(v):
    mark v
    clock ← clock + 1
    v.pre ← clock
    v.low ← v.pre
    Push(S, v )
    for each edge v􏰘w
        if w is unmarked
          TarjanDFS(w)
          v.low ← min{v.low, w.low}
        else if w.root = None
            v.low ← min{v.low, w.pre}
    if v.low = v.pre
        repeat
          w ← Pop(S)
          w.root ← v
        until w = v
 
 dependancies:
    get_neighbours()
    get_size() - for logs
 params:
    vertex<T>
    int - index id
    int[] - array of indices
    int[] - array of low indices
    bool[] - array to check if on stack
    stack<int> - stack of traversed vertices
    vector<vector<vertex<T>>> - return entity
    directed_graph<T>
 returns:
    vector<vector<vertex<T>>> - vector of vectors containing all strongly connected components
 */

template<typename T>
vector<vector<vertex<T>>> tarjan_dfs(vertex<T> v, int idx, int index[], int low[], bool on_stack[], stack<int> *s, directed_graph<T> g, vector<vector<vertex<T>>> components) {
    
    vector<vertex<T>> component;
    
    if (logs) cout << "\ntarjan_dfs: v.id: " << v.id << endl;
    
    index[v.id] = idx;
    low[v.id] = idx;
    idx++;
    s->push(v.id);
    on_stack[v.id] = true;
    int w;
    
//    for each adjacent vertex w
    for (auto &w: g.get_neighbours(v.id)) {
        
        if (logs) cout << "\nfor each neighbour: v: " << v.id << " -> " << w.id << endl;
        
//         If w is not visited yet, then recur for it
        if (index[w.id] == INT_MAX) {
            
            if (logs) cout << "Recur (DFS): w [" << w.id << "], index[" << idx << "]\n";
            
            components = tarjan_dfs(w, idx, index, low, on_stack, s, g, components);
            
            if (logs) cout << "Unwind recursion at: " << v.id ;
            
//             Check if the subtree rooted with 'v' has a connection to one of the ancestors of 'u'
            low[v.id] = min(low[v.id], low[w.id]);
            
            if (logs) cout << "\tlow[v.id] = " << low[v.id] << endl;
            
        } else if (on_stack[w.id]) {
            
            if (logs) cout << "\t on_stack[" << w.id << "] min(" << low[v.id] << ", " << index[w.id] << ") = " << min(low[v.id], index[w.id]) << endl;
            
//             Update low value of 'v' only of 'w' is still in stack
            low[v.id] = min(low[v.id], index[w.id]);
        }
    }
    
    if (logs) {
        cout << "\nUnwind Stack: v.id: " << v.id << " low[v.id] = " << low[v.id] << " index[v.id] = " << index[v.id] << " stack.size(): " << s->size() << endl;
        
        cout << "index[]:\t";
        for (int j = 0; j < g.get_size(); j++) {
            cout << "[" << j << "] = " << index[j] << "\t";
        }
        cout << endl;
        
        cout << "low[]:\t\t";
        for (int j = 0; j < g.get_size(); j++) {
            cout << "[" << j << "] = " << low[j] << "\t";
        }
        cout << endl;
        
        cout << "on_stack[]:\t";
        for (int j = 0; j < g.get_size(); j++) {
            cout << "[" << j << "] = " << on_stack[j] << "\t";
        }
        cout << endl;
    }
    
//     Head node found, unwind the stack and build the component vertex because a strongly connected compoent has been found
    if (low[v.id] == index[v.id]) {
        do {
            
            w = s->top();
            s->pop();
            on_stack[w] = false;
            
            if (logs) cout << "v.id = " << v.id << " new_component: w = " << w << endl;
            
            try {
                component.push_back(g.get_vertex(w));
            } catch (exception & e) {
                cout << e.what();
            }
            
        } while (w != v.id);
        
        components.push_back(component);
    }
    return components;
}

/*
 Computes the strongly connected components of the graph.
 A strongly connected component is a subset of the vertices
 such that for every pair u, v of vertices in the subset,
 v is reachable from u and u is reachable from v.
*/
template <typename T>
vector<vector<vertex<T>>> strongly_connected_components(directed_graph<T>& g) {
    return tarjan(g);
}


/*
 Depth First Topological Sort
 pseduo code from from: lecture slides
 visit(vertex<T> v) {
     if (v is permanently marked) return;
     if (v is temporily marked) return error;
     mark v temporarily;
     for (each (v, u) in edges) {
         visit(u);
     }
     clear temporary mark from v;
     permanently mark v;
     topo_order.append_front(v);
 }
 
 depandancies:
    out_degree()
    get_edges()
 params:
    vertex<T> v - current vertex
    bool[] - track perminatently marked verteices
    bool[] - track temporarily marked verteices
    vector<vertex<T>>* - pointer to return entity
    directed_graph<T>
 returns:
    vector<vector<vertex<T>>> - vector of vectors containing all strongly connected components
 */
template <typename T>
void topological_sort_dfs(vertex<T> v, bool permanent_mark[], bool temp_mark[], vector<vertex<T>> *topo_order, directed_graph<T>& g) {
    
    if (logs) cout << "\n\ttopological_sort_dfs | v: " << v.id << " g.out_degree(v.id): " << g.out_degree(v.id)<< endl;
    
    if (permanent_mark[v.id]) return;

    if (temp_mark[v.id]) throw "Graph is Cyclic";

    temp_mark[v.id] = true;

//    If v has neighbours
    if (g.out_degree(v.id) > 0) {
        
        if (logs) cout << "\t\t" << v.id << " has out edges" << endl;
        
//        For each neighbour u
        for (auto &u : g.get_edges(v.id)) {

            if (logs) cout << "About to recur at u: " << u.id << endl;

//            recursive call
            topological_sort_dfs(u, permanent_mark, temp_mark, topo_order, g);
        }
    }

    temp_mark[v.id] = false;
    permanent_mark[v.id] = true;
    topo_order->push_back(v);
    
}


/*
 * Computes a topological ordering of the vertices.
 * For every vertex u in the order, and any of its
 * neighbours v, v appears later in the order than u.
 * You will be given a DAG as the argument.
 
 Reference: Erickson, J. (2019). Algorithms (pp. 230-232)
 Reference: https://www.hackerearth.com/practice/algorithms/graphs/topological-sort/tutorial/
 
 pseduo code from from: lecture slides
 list<vertex<T> topo_order;
 while (not all vertices permanently marked) {
     v = next unmarked vertex<T>;
     visit(v);
 }

 dependancies:
    topological_sort_dfs()
    get_vertices()
    get_size()
    get_edges()
 params:
    directed_graph<T>
 returns:
    vector<vector<vertex<T>>> - vector of vectors containing all strongly connected components
 
 Time Complexity: O(V + E)
 */
template <typename T>
vector<vertex<T>> topological_sort(directed_graph<T>& g) {
    
    if (logs) cout << "topological_sort" << endl;
    
//    Initialise
    vector<vertex<T>> *topo_order = new vector<vertex<T>>;
    
    bool *temp_mark = new bool[g.get_size()];
    fill_n(temp_mark, g.get_size(), false);
    
    bool *permanent_mark = new bool[g.get_size()];
    fill_n(permanent_mark, g.get_size(), false);
    
    if (logs) {
        cout << "DAG: size = " << g.get_vertices().size() << endl;
        for (auto x : g.get_vertices()) {
            cout << "id:\t" << x.id << "\tweight:\t" << x.weight << "\tedges:\t";
            for (auto y : g.get_edges(x.id)) {
                cout << " -> " << y.id << " | " << y.weight << ", ";
            }
            cout << endl;
        }
    }
    
//    Iterate over graph
    for (auto &v : g.get_vertices()) {
        
//        If unprocessed enter depth first traveral
        if (!permanent_mark[v.id]) {
            topological_sort_dfs(v, permanent_mark, temp_mark, topo_order, g);
        }
    }
    reverse(topo_order->begin(),topo_order->end());
    return *topo_order;
}


/*
 Computes the lowest cost-per-person for delivery over the graph.
 u is the source vertex, which send deliveries to all other vertices.
 vertices denote cities; vertex weights denote cities' population;
 edge weights denote the fixed delivery cost between cities, which is irrelevant to
 the amount of goods being delivered.
 i.e. ( cost(A->B) + cost (A->C) + cost(C->D) + cost(B->E) ) / (the total population of B,C,D,E) = (600+900+4000+3000)/(300+400+710+221) = 5
 
 dependancies:
    out_tree() OR dijkstra()
    get_vertex()
 params:
    directed_graph<T>
    int - source id
 returns:
    vector<vector<vertex<T>>> - vector of vectors containing all strongly connected components
 */
template <typename T>
T low_cost_delivery(directed_graph<T>& g, int& u_id) {
    
    if (logs) cout << endl << "low_cost_delivery | u_id: " << u_id << endl;
    
    T sum_population = T{};
    T sum_delivery_cost = T{};
    T result;
    double d_res;
    double mst_res;
    double diff;
    
//    Using dijkstra's to get shortest path
    unordered_map<int, edge<T>> paths =  dijkstra(g, u_id);
    
//    Calculate the average delievery cost
    for (auto &e : paths) {
//        if (logs) cout << "edge: " << e.second.from_id << "->" << e.second.to_id << " | " << e.second.weight << " vertex: " << (g.get_vertex(e.second.to_id).weight) << endl;
        
        if (e.second.to_id != u_id && e.second.weight != INT_MAX) {
            sum_population += g.get_vertex(e.second.to_id).weight;
            sum_delivery_cost += e.second.weight;
        }
    }
    
//    Handle exceptions in case of undefined behavour caused by division
    try {
        result = sum_delivery_cost / sum_population;
        
        d_res = static_cast<double>(sum_delivery_cost) / static_cast<double>(sum_population);
        
        if (logs) cout << "Dijsktra: sum_delivery_cost: " << sum_delivery_cost << " / sum_population: " << sum_population << " = " << result << endl;
    } catch (exception &e) {
        cout << e.what();
        return T{};
    }
    
//    Using Minimum Spanning Tree
    sum_population = T{};
    sum_delivery_cost = T{};
    result = T{};
    
//    Get minimum spanning tree
    directed_graph<T> mst = g.out_tree(u_id);
    
//    if (logs) cout << "MST: size = " << mst.get_vertices().size() << endl;
    
//    Calculate the average delievery cost
    for (auto x : mst.get_vertices()) {
//        if (logs) cout << "id:\t" << x.id << "\tweight:\t" << x.weight << "\tedges:\t";
        
        if (x.id != u_id) {
            sum_population += x.weight;
        }
        
        for (auto y : mst.get_edges(x.id)) {
//            if (logs) cout << " -> " << y.id << " | " << y.weight << ", ";
            sum_delivery_cost += y.weight;
        }
        
//        if (logs) cout << endl;
    }
    
//    Handle exceptions in case of undefined behavour caused by division
    try {
        result = sum_delivery_cost / sum_population;
        
        mst_res = static_cast<double>(sum_delivery_cost) / static_cast<double>(sum_population);
        
        if (logs) cout << "MST: sum_delivery_cost: " << sum_delivery_cost << " / sum_population: " << sum_population << " = " << result << endl;
    } catch (exception &e) {
        cout << e.what();
        return T{};
    }
    
    diff = (d_res - mst_res) / d_res;
    if (logs) cout << "Proportial Difference of dijkstra to mst: "<< d_res << " " << mst_res   << " = " << diff << endl;

    return result;

}


/*
 Graveyard
 */

//
//template <typename T>
////void tarjan_scc(directed_graph<vertex<T>> &d, vertex<T> &u, size_t &index_counter, stack<vertex<T>> &s, unordered_map<vertex<T>, size_t> &index, unordered_map<vertex<T>, size_t> &low, unordered_map<vertex<T>, bool> &in_stack, vector<vector<vertex<T>>> &result) {
//void tarjan_scc(directed_graph<vertex<T>> d, vertex<T> u, size_t index_counter, stack<int> s, unordered_map<int, size_t> index, unordered_map<int, size_t> low, unordered_map<int, bool> in_stack, vector<vector<vertex<T>>> result) {
//
//    index[u.id] = index_counter;
//    low[u.id] = index_counter;
//    index_counter++;
//    s.push(u.id);
//    in_stack[u.id] = true;
//
//    for (auto nit = d.nbegin(u); nit != d.nend(u); ++nit) {
//        if (index.count(*nit) == 0) {
//            tarjan_scc(d, *nit, index_counter, s, index, low, in_stack, result);
//
//            low[u] = min(low.at(u), low.at(*nit));
//
//        } else if (in_stack.at(*nit)) {
//            low[u] = min(low.at(u), index.at(*nit));
//        }
//    }
//
//    if (low.at(u) == index.at(u)) {
//        vector<vertex<T>> component;
//        vertex<T> w;
//        do {
//            w = s.top();
//            s.pop();
//            in_stack[w] = false;
//            component.push_back(w);
//        }
//        while (w != u);
//        result.push_back(component);
//    }
//
//}
//
//template <typename T>
//vector<vector<vertex<T>>> scc(directed_graph<T>& d) {
////vector<vector<vertex<T>>> scc(const directed_graph<vertex<T>> & d) {
//
//    size_t index_counter = 0;
////    stack<vertex<T>> s;
//    stack<int> s;
////    unordered_map<vertex<T>, size_t> index;
//    unordered_map<int, size_t> index;
////    unordered_map<vertex<T>, size_t> low;
//    unordered_map<int, size_t> low;
////    unordered_map<vertex<T>, bool> in_stack;
//    unordered_map<int, bool> in_stack;
//    vector<vector<vertex<T>>> result;
//
//    for (auto v : d.get_vertices()) in_stack[v.id] = false;
//
//    for (auto &v : d.get_vertices()) {
//
////        cout << v.id << " | " << v.weight;
//
//        if (index.count(v.id) == 0) {
//
////            tarjan_scc(d, v, index_counter, s, index, low, in_stack, result);
//
////            tarjan_scc(d, v.id, index_counter, s, index, low, in_stack, result);
////            tarjan_scc(d, v, index_counter, s, index, low, in_stack, result);
////       tarjan_scc(directed_graph<vertex<T>> &d,
////            vertex<T> &u,
////            size_t &index_counter,
////            stack<vertex<T>> &s,
////            unordered_map<vertex<T>, size_t> &index,
////            unordered_map<vertex<T>, size_t> &low,
////            unordered_map<vertex<T>, bool> &in_stack,
////            vector<vector<vertex<T>>> &result)
//        }
//    }
//    return result;
//}
//
//template <typename T>
//vector<vector<vertex<T>>> SCC(directed_graph<T>& g) {
//
//    vector<vector<vertex<T>>> result;
//    int *index = new int[g.get_size()];
//    int *low = new int[g.get_size()];
//    bool *stackMember = new bool[g.get_size()];
//    stack<int> *st = new stack<int>();
//
//    for (int i = 0; i < g.get_size(); i++) {
//        index[i] = INT_MAX;
//        low[i] = INT_MAX;
//        stackMember[i] = false;
//    }
//
//    for (int i = 0; i < g.get_size(); i++) {
//        if (index[i] == INT_MAX) {
//            SCCUtil(i, index, low, st, stackMember, g);
//        }
//    }
//
//    return vector<vector<vertex<T>>>();
//}
//
//template <typename T>
//vector<vector<vertex<T>>> SCCUtil(int u, int index[], int low[], stack<int> *st, bool stackMember[], directed_graph<T>& g) {
//    static int time = 0;
//    index[u] = low[u] = ++time;
//    st->push(u);
//    stackMember[u] = true;
////    list<int>::iterator i;
//
////    for (i = adj[u].begin(); i != adj[u].end(); ++i) {
////        int v = *i;
//    for (auto &i : g.get_edges(u)) {
//        int v = i.id;
//        if (index[v] == INT_MAX) {
//            SCCUtil(v, index, low, st, stackMember, g);
//            low[u] = min(low[u], low[v]);
//        } else if (stackMember[v] == true) {
//            low[u] = min(low[u], index[v]);
//        }
//    }
//
//
//    if (low[u] == index[u]) {
//
//        int w = 0;
//        vector<vertex<T>> component;
//        vertex<T> v;
//
//        while (st->top() != u) {
//            w = (int) st->top();
//            cout << w << " ";
//            stackMember[w] = false;
//            st->pop();
//        }
//
//        w = (int) st->top();
//        cout << w << ", ";
//        stackMember[w] = false;
//        st->pop();
//    }
//
//    return vector<vector<vertex<T>>>();
//}
/*
 Bellman-Ford
 */
    
//
//    for (auto &v : nodes) {
//        if (v.id != v_id) { // Prune edges out of desination
//            for (auto &e : g.get_edges(v.id)) {
//                if (e.id != u_id) { // Prune edges into source
//                    edges.push_back(edge<T>(v.id, e.id, e.weight));
//                }
//            }
//        }
//    }
//
//    try {
//        paths = bellman_ford(edges, u_id, g.num_vertices());
//    } catch (exception &e) {
//        cout << e.what();
//    }
//
//    if (logs) {
//        cout << "\nBellman-Ford Result: " << paths.size() << endl;
//        for (auto &i : paths) {
//            cout << i.first << ": ";
////            for (auto &j : i.second) {
//            cout << i.second.from_id << " -> " << i.second.to_id << " | " << i.second.weight << ", ";
////            }
//            cout << endl;
//        }
//    }
//
//
/*
 Reference: https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
 Reference: Algorithms, Jeff Ericson
 
   BellmanFordFinal(s) dist[s]←0
    for every vertex v ̸= s
        dist[v]←∞
    for i ← 1 to V − 1
        for every edge u􏰘v
            if dist[v] > dist[u] + w(u􏰘v)
                dist[v] ← dist[u] + w(u􏰘v
 
 */
// The main function that finds shortest distances from src to
// all other vertices using Bellman-Ford algorithm.  The function
// also detects negative weight cycle
//template <typename T>
//map<int, edge<T>> bellman_ford(vector<edge<T>>& edges, int src, size_t num_vertices) {
//
//    if (logs) cout << "Inside Bellman Ford" << endl;
//
//    int iterations = 0;
//
//    size_t V = num_vertices;
//    size_t E = edges.size();
//    int dist[num_vertices];
//    int u;
//    int v;
//    T weight;
////    unordered_map<int, unordered_map<int, T>> paths;
//    map<int, edge<T>> paths;
//
//    // Step 1: Initialize distances from src to all other vertices as INFINITE
//    for (int i = 0; i < V; i++)
//        dist[i] = INT_MAX;
//    dist[src] = 0;
////    paths[src].push_back(edge<T>(0, 0, 0));
//    paths[src] = edge<T>(0, 0, 0);
//
//    // Step 2: Relax all edges |V| - 1 times. A simple shortest
//    // path from src to any other vertex can have at-most |V| - 1 edges
//    for (int i = 1; i <= V; i++) {
//        iterations++;
//
////        if (logs) cout << "\ni: " << i << "\n______________________\n";
//        for (int j = 0; j < E; j++) {
////            cout << "j: " << j << ", ";
//            u = edges[j].from_id;
//            v = edges[j].to_id;
//            weight = edges[j].weight;
//
////            if (logs) cout << "u: " << u << " -> v: " << v << " weight: " << weight << "\n";
//
//            if (dist[u] != INT_MAX && dist[u] + weight < dist[v]) {
//
//                if (logs) {
//                    int tmp = dist[v];
//                    cout << "\tShorter - i: " << i << ", j: " << j << "\t" << u << " -> " << v << " | " << weight << " :: dist[" << u << "] + " << weight << " (" << dist[u] + weight << ")" << " < dist["<< v << "]: (" << tmp << ")" << endl;
//                }
//
//                dist[v] = dist[u] + weight;
//                paths[v] = edges[j];
//            }
//
//            iterations++;
//        }
////        if (logs) cout  << "______________________\n";
//    }
//
//
//    // Step 3: check for negative-weight cycles.  The above step
//    // guarantees shortest distances if graph doesn't contain
//    // negative weight cycle.  If we get a shorter path, then there
//    // is a cycle.
//    for (int i = 0; i < E; i++) {
//        u = edges[i].from_id;
//        v = edges[i].to_id;
//        weight = edges[i].weight;
//        if (dist[u] != INT_MAX && dist[u] + weight < dist[v]) {
////            throw negative_weight_cycle_exception;
//            throw "Graph contains negative weight cycle";
////            cout << "Graph contains negative weight cycle";
////            return map<int, edge<T>>(); // If negative cycle is detected, simply return
//        }
//    }
//
//    if (logs) {
//        cout << "Vertex   Distance from Source    iterations = " << iterations << " Bellman-Ford \n";
//        for (int i = 0; i < V; ++i)
//            printf("%d \t\t %d\n", i, dist[i]);
//    }
//
//    return paths;
//}


// Tutorial slides
//#define V 6
//template <typename T>
//int minDistance(directed_graph<T>& g, int dist[], bool sptSet[], size_t V) {
//
//    int min = INT_MAX;
//    int min_index = INT_MIN;
//
//    for (int v = 0; v < V; v++) {
//        if (sptSet[v] == false && dist[v] <= min) {
//            min = dist[v];
//            min_index = v;
//        }
//    }
//    return min_index;
//}
//
//template <typename T>
//void printSolution(directed_graph<T>& g, int dist[], size_t V) {
//
//    printf("Vertex \t\t Distance from Source\n");
//
//    for (int i = 0; i < V; i++) {
//        printf("%d \t\t %d\n", i, dist[i]);
//    }
//
//
//
//}
//
//
//template <typename T>
//map<int, edge<T>> dijkstra_zero(directed_graph<T>& g, int src) {
//
//    size_t V = g.num_vertices();
//    map<int,edge<T>> paths;
//
//    for (auto &v : g.get_vertices()) {
//
//        if (v.id == src) {
//            paths.insert({v.id, edge<T>(v.id, v.id, INT_MIN)});
//        } else {
//            paths.insert({v.id, edge<T>(v.id, INT_MAX, INT_MAX)});
//        }
//    }
//
//    int graph[V][V];
//
//    vector<vertex<int>> temp_edges;
//    for (int i = 0; i < V; i++) {
////        cout << "{ ";
//        temp_edges = g.get_edges(i);
//        for (int j = 0; j < g.num_vertices(); j++) {
////            auto weight = INT_MIN;
//            auto weight = 0;
//            for (auto &e : temp_edges) {
//                if (e.id == j) weight = e.weight;
//            }
////            cout << weight << ", ";
//            graph[i][j] = weight;
//        }
////        cout << "},\n";
//    }
//
//
//    int dist[V];
//    bool sptSet[V];
//    for (int i = 0; i < V; i++) {
//        dist[i] = INT_MAX;
//        sptSet[i] = false;
//    }
//    dist[src] = 0;
//    for (int count = 0; count < V - 1; count++) {
//        int u = minDistance(g, dist, sptSet, V);
//        sptSet[u] = true;
//        for (int v = 0; v < V; v++) {
//
////            This is where a zero edge will be interepeted as undefined
//            if (!sptSet[v] && graph[u][v] && dist[u] != INT_MAX && dist[u] + graph[u][v] < dist[v]) {
//                dist[v] = dist[u] + graph[u][v];
//
////                paths[v] = edge<T>({u, v, dist[u] + graph[u][v]});
//                paths[v] = edge<T>({u, v, graph[u][v]});
//            }
//        }
//    }
//    printSolution(g, dist, V);
//
////    build shortest path
//    if (logs) {
//        cout << "\nPath Map\n";
//        for (auto &i : paths) {
//            cout << i.first << ": " << i.second.from_id << "->" << i.second.to_id << " | " << i.second.weight << "\n";
//        }
//    }
//
//    return paths;
//}


//  direct_graph.h
//  Assignment_1
//
//  Created by Clint Sellen on 5/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//


#ifndef DIRECTED_GRAPH_H
#define DIRECTED_GRAPH_H

#include <iostream>
#include <string>
#include <vector>
#include <list>
#include <unordered_map>
#include <stack>
#include <queue>

// include more libraries here if you need to
#include <algorithm>
#include<climits>

using namespace std; // the standard namespace are here just in case.



/*
DOMAIN: Edge Class
*/
template <typename T>
class edge {

public:
    int from_id;
    int to_id;
    T weight;

    edge(int f_id, int t_id, T e_weight) : from_id(f_id), to_id(t_id), weight(e_weight) {}
    edge() {};
};

/*
DOMAIN: Vertex Class
*/
template <typename T>
class vertex {

public:
    int id;
    T weight;

    vertex(int v_id, T v_weight) : id(v_id), weight(v_weight) {}

    // add more functions here if you need to
    vertex() {};
};


/*
INTERFACE: Directed Graph Class
*/
template <typename T>
class directed_graph {

private:
    
    bool logs;
    size_t size;

    //You will need to add some data members here
    //to actually represent the graph internally,
    //and keep track of whatever you need to.
    
    /* THIS IS DEMO CODE*/
    // vertex_weights stores all vertices in the graph, as well as the vertices' weights
    unordered_map<int, T> vertex_weights; // each element is a pair(id, weight) for a vertex

    // adj_list stores all edges in the graph, as well as the edges' weights.
    // each element is a pair(vertex, the neighbours of this vertex)
    // each neighbour is also a pair (neighbour_vertex, weight for edge from vertex to neighbour_vertex)
    unordered_map<int, unordered_map<int, T>> adj_list;
    /* THIS IS DEMO CODE*/

public:

    directed_graph(); //A constructor for directed_graph. The graph should start empty.
    ~directed_graph(); //A destructor. Depending on how you do things, this may not be necessary.

    bool contains(const int&) const; //Returns true if the graph contains the given vertex_id, false otherwise.
    bool adjacent(const int&, const int&) const; //Returns true if the first vertex is adjacent to the second, false otherwise.

    void add_vertex(const vertex<T>&); //Adds the passed in vertex to the graph (with no edges).
    void add_edge(const int&, const int&, const T&); //Adds a weighted edge from the first vertex to the second.

    void remove_vertex(const int&); //Removes the given vertex. Should also clear any incident edges.
    void remove_edge(const int&, const int&); //Removes the edge between the two vertices, if it exists.

    size_t in_degree(const int&) const; //Returns number of edges coming in to a vertex.
    size_t out_degree(const int&) const; //Returns the number of edges leaving a vertex.
    size_t degree(const int&) const; //Returns the degree of the vertex (both in edges and out edges).

    size_t num_vertices() const; //Returns the total number of vertices in the graph.
    size_t num_edges() const; //Returns the total number of edges in the graph.
    
//    unordered_map<int, T> get_edges(int v_id);
    vector<vertex<T>> get_edges(int v_id);
    vector<vertex<T>> get_vertices(); //Returns a vector containing all the vertices.
    vector<vertex<T>> get_neighbours(const int&); //Returns a vector containing all the vertices reachable from the given vertex. The vertex is not considered a neighbour of itself.
    vector<vertex<T>> get_second_order_neighbours(const int&); // Returns a vector containing all the second_order_neighbours (i.e., neighbours of neighbours) of the given vertex.
                                                              // A vector cannot be considered a second_order_neighbor of itself.
    bool reachable(const int&, const int&) const; //Returns true if the second vertex is reachable from the first (can you follow a path of out-edges to get from the first to the second?). Returns false otherwise.
    
    bool contain_cycles() const; // Return true if the graph contains cycles (there is a path from any vertices directly/indirectly to itself), false otherwise.
    
    vector<vertex<T>> depth_first(const int&); //Returns the vertices of the graph in the order they are visited in by a depth-first traversal starting at the given vertex.
    vector<vertex<T>> breadth_first(const int&); //Returns the vertices of the graph in the order they are visisted in by a breadth-first traversal starting at the given vertex.

    directed_graph<T> out_tree(const int&); //Returns a spanning tree of the graph starting at the given vertex using the out-edges. This means every vertex in the tree is reachable from the root.

    vector<vertex<T>> pre_order_traversal(const int&, directed_graph<T>&); // returns the vertices in the visiting order of a pre-order traversal of the minimum spanning tree starting at the given vertex.
    vector<vertex<T>> in_order_traversal(const int&, directed_graph<T>&); // returns the vertices in the visiting order of an in-order traversal of the minimum spanning tree starting at the given vertex.
    vector<vertex<T>> post_order_traversal(const int&, directed_graph<T>&); // returns the vertices in ther visitig order of a post-order traversal of the minimum spanning tree starting at the given vertex.

    vector<vertex<T>> significance_sorting(); // Return a vector containing a sorted list of the vertices in descending order of their significance.

    vector<vertex<T>> dfs_util(int v, bool visited[], vector<vertex<T>>);
    vector<vertex<T>> bfs_util(int v, bool visited[], vector<vertex<T>>);
    bool is_reachable(int v, bool visited[], int v_id) const;
    vector<vertex<T>> in_order_traversal_util(const int&, vector<vertex<T>>, directed_graph<T>&);
    vector<vertex<T>> post_order_traversal_util(const int& v, vector<vertex<T>> traversed, directed_graph<T>& mst);
    size_t get_size();
    vertex<T> get_vertex(int id);
    
//    Reference: https://stackoverflow.com/questions/19535644/how-to-use-the-priority-queue-stl-for-objects
    struct less_weight {
        bool operator()(vertex<T> const & v1, vertex<T> const & v2) {
            return v1.weight < v2.weight;
        }
    } less_weight;
    
    struct more_weight {
        bool operator()(vertex<T> const & v1, vertex<T> const & v2) {
            return v1.weight > v2.weight;
        }
    } more_weight;
    
//    This is backwards
    struct compair_edge_weight {
        bool operator()(edge<T> const & e1, edge<T> const & e2) {
            return e1.weight > e2.weight;
        }
    };
};
#endif


/*
IMPLEMENTATION: Directed Graph Implementation
 
 Implementation is contained in the header file because this is a Template class
 c++ restricts the ability to seperated Template classes into seperate files
 Reference: https://stackoverflow.com/questions/1724036/splitting-templated-c-classes-into-hpp-cpp-files-is-it-possible
 
*/

/*
 Constructor
 */
template <typename T>
directed_graph<T>::directed_graph() {
//    This is to manage the size of the visits array
    size = 1;
    
//    Turn on/off console logs
    logs = false;
}

/*
 Deconstructor
 */
template <typename T>
directed_graph<T>::~directed_graph() {
}

/*
 Getter for vertex index size
 returns
    size_t
 */
template <typename T>
size_t directed_graph<T>::get_size() {
    return size;
}

/*
Getter for vertex
params:
    int - vertex id to get
returns
   vertex
*/
template <typename T>
vertex<T> directed_graph<T>::get_vertex(int id) {
    if (contains(id)) {
        return vertex<T>({id, vertex_weights[id]});
    } else {
//        throw "Vertex not found.";
        return vertex<T>();
    }
}

/*
 Vertex look up
 params:
    int - this id of the vertex to look up
 returns
    bool
 */
template <typename T>
bool directed_graph<T>::contains(const int& u_id) const {

    /* THIS IS DEMO CODE*/
    if(vertex_weights.find(u_id) != vertex_weights.end()){ // u_id is found in vertex_weights's keys
        return true;
    }
    return false;
    /* THIS IS DEMO CODE*/
}

/*
 Check to see if vertex u has an edge to vertex v
 params:
    int - the id of the from vertex
    int - the id of the to vertex
 returns:
    bool
*/
template <typename T>
bool directed_graph<T>::adjacent(const int& u_id, const int& v_id) const {

    for (auto &e : adj_list.at(u_id)){
//        if (logs) cout << "Vertex id: " << e.first << endl;
        if (e.first == v_id) return true;
    }
    return false;
}

/*
 Add a vertex to the graph
 params:
    vertex<T>
 returns:
    void
 */
template <typename T>
void directed_graph<T>::add_vertex(const vertex<T>& u) {

    if (logs) cout << "id: "<< u.id << " weight : " << u.weight << " : " << &u << endl;

    /* THIS IS DEMO CODE*/
    if(!contains(u.id)){
        vertex_weights.insert({u.id, u.weight}); // step 1: add the new vertex to all_vertices
        adj_list[u.id] = unordered_map<int, T>(); // step 2: add an entry for this vertex in adj_list but add no edge
    }
    /* THIS IS DEMO CODE*/

/*
    Visits are marked using a boolean array where the array intex it the vertex id.
    This has the benefit of O(1) look up time however a cost is paid in space if the
    list of vertexs has many deleted vertexs because a boolean value will be held
    for a non-existing vertex.
 
    TODO - This would be better if it an unordered_map was used inplace of an array
*/
    if (u.id > size) size = u.id + 1;
//    size++;
}


/*
 Add an edge to the graph
 params:
    int - from vertx id
    int - to vertex id
    T - weight of the edge
 returns:
    void
 */
template <typename T>
void directed_graph<T>::add_edge(const int& u_id, const int& v_id, const T& uv_weight) {

    if (logs) cout << "add_edge: " << u_id << " -> " << v_id << " weight: " << uv_weight << endl;

    /* THIS IS DEMO CODE*/
    if (contains(u_id) && contains(v_id)) { // Step 1: make sure both vertices are in the graph
        if (adj_list[u_id].find(v_id) == adj_list[u_id].end()) { // Step 2: make sure the edge is not already in the graph
            adj_list[u_id].insert({v_id, uv_weight}); // Step 3: add this edge to adj_list
        }
    }
    /* THIS IS DEMO CODE*/
}

/*
 Remove a vertex from the graph
 params:
    int - vertx id
 returns:
    void
*/
template <typename T>
void directed_graph<T>::remove_vertex(const int& u_id) {

    if (logs) cout << "remove_vertex id: " << u_id << endl;

    /* THIS IS DEMO CODE*/
    vertex_weights.erase(u_id); // step 1: remove the vertex from all_vertices

    adj_list.erase(u_id); // step 2: remove all edges starting from this vertex

    for (auto& x: adj_list){ // Step 3: iterate adj_list to remove all edges ending at this vertex
        x.second.erase(u_id);
    }
    /* THIS IS DEMO CODE*/

//    Leave turned off to avoid out of bounds error because the vertex id is the visits array index
//    size--;
}

/*
 Remove an edge from the graph
 params:
    int - from vertx id
    int - to vertex id
 returns:
    void
 */
template <typename T>
void directed_graph<T>::remove_edge(const int& u_id, const int& v_id) {

    if (logs) cout << "u_id: " << u_id << " v_id: " << v_id << endl;

//    Look if edge exists
    if(adj_list.find(u_id) != adj_list.end()){
        if (logs) cout << "FOUND VERTEX" << endl;
//        Remove edge if found
        adj_list.at(u_id).erase(v_id);
    }
}

/*
 Count the number of edges into the vertex
 params:
    int - vertx id
 returns:
    size_t - number of edges into the vertex
 */
template <typename T>
size_t directed_graph<T>::in_degree(const int& u_id) const {

    size_t in_degree = 0;

    if (logs) cout << "\nin_degree u_id: " << u_id << endl;
    // Iterate over the adjancy list
    for (auto &x : adj_list){
//      If the vertex is not itself
        if (x.first != u_id) {
            if (logs) cout << "Vertex not u_id " << x.first << endl;
//            Look for an edge in
            if (x.second.find(u_id) != x.second.end()) {
                if (logs) cout << "FOUND EDGE IN" << endl;
                in_degree++;
            }
        }
    }
    return in_degree;
}

/*
 Count the number of edges out of the vertex
 params:
    int - vertx id
 returns:
    size_t - number of edges out of the vertex
*/
template <typename T>
size_t directed_graph<T>::out_degree(const int& u_id) const {
    
//    If vertex exists return number of edges
    if (adj_list.find(u_id) != adj_list.end()) {
        return adj_list.at(u_id).size();
    }
//    Vertex does not exist
    return 0;
}


/*
 Count the number of edges in and out of the vertex
 params:
    int - vertx id
 returns:
    size_t - number of edges in and out of the vertex
 */
template <typename T>
size_t directed_graph<T>::degree(const int& u_id) const {

    size_t degree = 0;
    if (adj_list.find(u_id) != adj_list.end()) {
        degree += out_degree(u_id);
        degree += in_degree(u_id);
    }
    return degree;
}

/*
 Look up the total number of vertices in the graph
 params:
    int - vertex id
 returns:
    size_t
 */
template <typename T>
size_t directed_graph<T>::num_vertices() const {
    return vertex_weights.size();
}


/*
 Look up the total number of edges in the graph
 params:
   int - vertex id
 returns:
   size_t
*/
template <typename T>
size_t directed_graph<T>::num_edges() const {

    size_t num_edges = 0;

// Iterate over the adjancy list and sum the number of edges
    for (auto &x : adj_list){
        num_edges += x.second.size();
    }
    return num_edges;

}


/*
 Get all vertices in the graph
 returns:
   vector<vertex<T>> - a vector of all the vertices
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::get_vertices() {

    /* THIS IS DEMO CODE*/
    vector<vertex<T>> v;

    for(auto x: vertex_weights){ // iterate vertex_weight to get all vertex_ids
        v.push_back(vertex<T>(x.first, x.second)); // and then build a vertex class for each vertex_id
    }
    return v; // return a vector of the vertex classes for all vertex_ids
    /* THIS IS DEMO CODE*/
}

/*
 Get all the edges for vertex v
 params:
    int - vertex id
 returns:
    vector<vertex<T>> - a vector of all the edges for vertex
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::get_edges(int v_id) {

    vector<vertex<T>> edges;

    if (contains(v_id)) {
        for (auto &e : adj_list[v_id]) {
            edges.push_back(vertex<T>(e.first, e.second));
        }
    }
    return edges;
}

/*
 Get all the edges for vertex u
 params:
    int - vertex id
 returns:
    vector<vertex<T>> - a vector of all the edges for vertex
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::get_neighbours(const int& u_id) {

    /* THIS IS DEMO CODE*/
    vector<vertex<T>> v;

    if(contains(u_id)){ // Step 1: make sure the vertex is in the graph
        for (auto x: adj_list[u_id]){ // Step 2: find all edges starting from the vertex of u_id
            v.push_back(vertex<T>(x.first, vertex_weights[x.first])); // Step 3: add the end_node of each edge to the result
        }
    }
    return v;
    /* THIS IS DEMO CODE*/
}


/*
 Get all the vertices that are 2 steps from vertex u
 params:
    int - vertex id
 returns:
    vector<vertex<T>>
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::get_second_order_neighbours(const int& u_id) {

    if (logs) cout << endl << "get_second_order_neighbours: " << u_id << endl;

    vector<vertex<T>> second;
    vector<vertex<T>> temp;
    bool found;

//    Get all neighbours
    vector<vertex<T>> first = get_neighbours(u_id);

//    Get all neighbours of neighbours
    for (auto& v : first) {
        if (logs) cout << "First order neighbours: " << v.id << endl;
        temp = get_neighbours(v.id);

        for (auto& t : temp) {
            if (logs) cout << "Temp neighbours: " << t.id << " weight: " << t.weight << endl;

            if (t.id != u_id) {
                found = false;
                for (auto& s : second) {
                    if (logs) cout << "Look Up Second Order: " << s.id << endl;
                    if (s.id == t.id) found = true;
                }
                if (!found) second.push_back(t);
            }
        }
    }
    return second;
}

/*
 Utility function to look if vertex v can be reached from vertex u
 params:
    int - from id
    bool[] - array of visited vertices where the array index is the vertex id
    int - to id
 returns:
    bool
 Reference: https://www.geeksforgeeks.org/find-all-reachable-nodes-from-every-node-present-in-a-given-set/
 */
template <typename T>
bool directed_graph<T>::is_reachable(int u_id, bool visited[], int v_id) const {

    if (logs) cout << "is_reachable: " << u_id << " -> " << v_id << endl;

    vector<int> neighbours;
    queue<int> unprocessed;

    unprocessed.push(u_id);

    while (!unprocessed.empty()) {

        int v = unprocessed.front();
        unprocessed.pop();

        if (!visited[v]) {
            if (logs) cout << endl << "Looking at : " << v << endl;
//            Mark vertex as visited
            visited[v] = true;
//            Get neightbours
            if (adj_list.find(v) != adj_list.end()) {
                for (auto &x : adj_list.at(v)) {
//                    if (!visited[x.first])
                    neighbours.push_back(x.first);
                }
            }

//            Iterate over neighbours to see if to vertex is there
//            If get_neighbours returned a map this could be O(1) retrieval and eliminate need an iteration over the vertex
            for (int w : neighbours) {
                if (logs) cout << " w: " << w << " | ";
                if (w == v_id) {
                    if (logs) cout << "FOUND" << endl;
                    return true;
                }
                unprocessed.push(w);
            }
            if (logs) cout << endl;
        }
    }
    return false;
}


/*
 Look if vertex v can be reached from vertex u
 dependant: utilises is_reachable()
 params:
    int - from id
    int - to id
 returns:
    bool
 */
template <typename T>
bool directed_graph<T>::reachable(const int& u_id, const int& v_id) const {

    if (logs) cout << "check: " << u_id << " -> " << v_id << endl;

//    TODO: Make this unordered_map
    bool *visited = new bool[size];
    fill_n(visited, size, false);

    return is_reachable(u_id, visited, v_id);
}

/*
 Look if vertex v can be returned to via a traversal
 dependant: utilises is_reachable()
 returns:
    bool
*/
template <typename T>
bool directed_graph<T>::contain_cycles() const {

//    TODO: Make this unordered_map
    bool *visited = new bool[size];

    for (auto v : vertex_weights) {
        
        fill_n(visited, size, false);
        
        if (is_reachable(v.first, visited, v.first)) return true;
    }
    return false;
}


/*
 Utility function to traversers the graph in a depth first order
 That is to look at neighbours of neighbours before moving on the the next edge
 params:
    int - from vertex
    bool[] -  array of visited vertices where the array index is the vertex id
    vector<vertex<T>> - to contain the travers vertices
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::dfs_util(int v, bool visited[], vector<vertex<T>> traversed) {

    visited[v] = true;

    if (logs) cout << endl << "Entered dfs_util looking at : " << v << " marked = " << visited[v] << endl;
    traversed.push_back(vertex<T>(v, vertex_weights[v]));

    vector<vertex<T>> neighbours = get_neighbours(v);
    for (auto u : neighbours) {
        if (logs) cout << " u: " << u.id << " visited = " << visited[u.id] << " | ";

        if (!visited[u.id]) {
            traversed = dfs_util(u.id, visited, traversed);
        }
    }
    return traversed;
}

/*
    Reference: https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
    pseduo code from lecture slides
    Recurive
    dft(Vertex v) {
        mark v as visited
        visit(v)
        for each neightbour u of v {
            if (u is unmarked) {
                dft(u)
        }
    }

    Iterative
    dft(starting vertex v) {
        Stack unprocessed = new Stack()
        unprocessed.push(v)

        while (!unprocessed.isEmpty())
        Vertex u = unprocessed.pop()
        if (u is unmarked) {
            visit(u)
            mark u as visited
            for each neighbour w of u {
                unprocessed.push(w)
            }
        }
    }
 This is the recursive variation
 dependant: dfs_util()
 params:
    int - from vertex
 returns:
    vector<vertex<T>>
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::depth_first(const int& u_id) {

    vector<vertex<T>> traversed;
//    static size_t size = vertex_weights.size();
    
//    TODO: Make this unordered_map
    bool *visited = new bool[size];
    fill_n(visited, size, false);

//    Call the recursive helper function to print DFS traversal
    traversed = dfs_util(u_id, visited, traversed);

//    Handle disconnect graphs
    bool complete;
    do {
        complete = true;
        for (auto &v : vertex_weights) {
            if (visited[v.first] == false) {
                complete = false;
                traversed = dfs_util(v.first, visited, traversed);
            }
        }
    } while (!complete);

    return traversed;
}


/*
 Utility function to ttaversers the graph in a breath first order
 That is to look at neighbours of the vertex before moving on the the next neighbour
 params:
    int - from vertex
    bool[] -  array of visited vertices where the array index is the vertex id
    vector<vertex<T>> - to contain the travers vertices
 returns:
    vector<vertex<T>>
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::bfs_util(int u, bool visited[], vector<vertex<T>> traversed) {

    vector<vertex<T>> neighbours;
    queue<int> unprocessed;

    unprocessed.push(u);

    while (!unprocessed.empty()) {

        u = unprocessed.front();
        unprocessed.pop();

        if (logs) cout << endl << "Entered bfs looking at : " << u << " marked = " << visited[u] << endl;
        if (!visited[u]) {

            visited[u] = true;
            traversed.push_back(vertex<T>(u, vertex_weights[u]));
            neighbours = get_neighbours(u);

            for (auto w : neighbours) {
                if (logs) cout << " w: " << w.id << " visited = " << visited[w.id] << " | ";
                unprocessed.push(w.id);
            }
        }
    }
    return traversed;
}

/*
 pseduo code from lecture slides
 bft(starting vertex v) {
   Queue unprocessed = new Queue()
   unprocessed.offer(v)

   while (!unprocessed.isEmpty())
       Vertex u = unprocessed.poll()
       if (u is unmarked)
         visit(u)
         mark u
         for each neighbour w of u
             unprocessed.offer(w)
 
 dependant: bfs_util()
 params:
    int - from vertex
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::breadth_first(const int& u_id) {

    vector<vertex<T>> traversed;

//    static size_t size = vertex_weights.size();
    bool *visited = new bool[size];
    fill_n(visited, size, false);

//    Traverse graph
    traversed = bfs_util(u_id, visited, traversed);

//    Handle disconnect graphs
    bool complete;
    do {
        complete = true;
        for (auto &v : vertex_weights) {
            if (visited[v.first] == false) {
                complete = false;
                traversed = bfs_util(v.first, visited, traversed);
            }
        }
    } while (!complete);

    return traversed;
}


/*
 Span the graph to find all vertices connected to the starting vertex with a minimum edge weight comparision
 
 Reference: https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
 Reference: https://www.geeksforgeeks.org/travelling-salesman-problem-set-2-approximate-using-mst/
 Reference: https://www.geeksforgeeks.org/binary-heap/
 Reference: http://www.cplusplus.com/reference/queue/priority_queue/
 Reference: https://stackoverflow.com/questions/19535644/how-to-use-the-priority-queue-stl-for-objects
 psudo code
 MST(current)
     tree.add_vertex(current)
     do
         visited[current.id] = true
         get_neighbours(current.id)
         for each neighbour
             IF NOT visited heap.push(neighbour)
         next = heap.pop()
         tree.add_vertex(next)
         tree.add_edge(next)
         current = next
     while( num_edge < num_vertex - 1 )
 
 dependant: compair_edge_weight Comparitor
 params:
    int - from vertex
 returns:
    directed_graph<T>
 */
template <typename T>
directed_graph<T> directed_graph<T>::out_tree(const int& u_id) {

    if (logs) cout << "out_tree("<< u_id <<")" << endl;

    priority_queue<edge<T>, vector<edge<T>>, compair_edge_weight> heap;

    directed_graph<T> mst;
    
//    TODO: Make this unordered_map
    bool *visited = new bool[size];
    bool has_visited;
    vector<vertex<T>> neighbours;
    vertex<T> current;
    edge<T> next;

    fill_n(visited, size, false);

    current = vertex<T>(u_id, vertex_weights[u_id]);
    mst.add_vertex(current);

    do {
        if (logs) cout << endl;
        if (logs) cout << "current: " << current.id << endl;

        visited[current.id] = true;

        neighbours = get_neighbours(current.id);
        
        if (logs) cout << "neighbours:\n";
        
        for (auto u : neighbours) {

            if (logs) cout << " u: " << u.id << " | " << adj_list[current.id][u.id] << " | ";
            if (logs) cout << "\n\theap.push( " << current.id << "->" << u.id << " | " << adj_list[current.id][u.id] << " )\n";

            heap.push(edge<T>(current.id, u.id, adj_list[current.id][u.id]));
        }

        if (logs) cout << "-- heap size = " << heap.size() << endl;

        if (!heap.empty()) {
            do {
                if (logs) cout << "\t-- heap top = " << heap.top().from_id << "->" << heap.top().to_id << " -- heap size = " << heap.size() << " -- heap empty = " << heap.empty() << endl;
                
                has_visited = visited[heap.top().to_id];
                next = heap.top();
                heap.pop();
                
            } while(has_visited && !heap.empty());

            if (logs) cout << "current: " << current.id << " | next: " << next.from_id << "->" << next.to_id << " edge: " << next.weight << endl;
            
            mst.add_vertex(vertex<T>(next.to_id, vertex_weights[next.to_id]));
            mst.add_edge(next.from_id, next.to_id, next.weight );

            if (logs) cout << "\t-- heap size = " << heap.size() << endl;

            current = vertex<T>(next.to_id, vertex_weights[next.to_id]);

            if (logs) cout << "mst: edges = " << mst.num_edges() << " mst.vertex = " << mst.num_vertices() << endl;
            if (logs) cout << "mst: vertex = " << mst.num_vertices() << " vertex = " << num_vertices() << endl;
        }
        
    } while (num_vertices() > mst.num_vertices() && !heap.empty());

    if (logs) cout << endl;

//    If disconnected graph return empty graph
//    if (num_vertices() != mst.num_vertices()) {
//        mst = directed_graph<T>();
//    }

    return mst;
}

/*
 Traverse the graph in a pre-order fashion
 
 Reference: https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/amp/
 pseduo code
 push(root)
 WHILE shunting_yard IS EMPTY
     pop()
     add_vertex()
     IF parent
         push(children)
 
 dependant:
    - less_weight Comparitor
    - get_edges() Utility OR
    - get_neighbours() Utility
 params:
    int - from vertex
    directed_graph<T> - graph to traverse
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::pre_order_traversal(const int& u_id, directed_graph<T>& mst) {

    stack<int> shunting_yard;
    vector<vertex<T>> elems;
    vector<vertex<T>> traversed;
    int top;

    shunting_yard.push(u_id);

    int i = 0;
    while (!shunting_yard.empty()) {

        if (logs) cout << "_______ Loop: " << i <<" _______" << endl;

        top = shunting_yard.top();
        shunting_yard.pop();

        if (logs) cout << "top: " << top << " yard size: " << shunting_yard.size()<< endl;

        traversed.push_back(vertex<T>(top, mst.vertex_weights[top]));

        if (mst.out_degree(top) > 0) {
//            vector label weight comparison
//            elems = mst.get_neighbours(top);
            
//            vector edge weight comparison
            elems = mst.get_edges(top);
            sort(elems.begin(), elems.end(), less_weight);

            if (logs) {
                cout << "\tsorted elems: ";
                for (auto &e : elems) {
                    cout << e.id << " " << e.weight << ", ";
                }
            }

            if (logs) cout << "\n\tPush children: ";
            while (!elems.empty()) {
                if (logs) cout << elems.back().id << ", ";
                shunting_yard.push(elems.back().id);
                elems.pop_back();
            }
        }

        i++;
        if (logs) cout << endl;
    }
    return traversed;
}

/*
 Recursively traverse the graph in a in-order fashion
 
 pseduo code
 in_order_traversal(vertex)
 IF vertex has no edges
     add_vertex()
     return
 ELSE
     first_edge = true
     FOR EACH edge IN edges
         in_order_traversals(neighbour)
         add_vertex()
         IF first_edge
             first_edge = false
         first_edge = false
 
 dependant:
    - less_weight Comparitor
    - get_edges() Utility OR
    - get_neighbours() Utility
 params:
    int - from vertex
    directed_graph<T> - graph to traverse
 returns:
    vector<vertex<T>>
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::in_order_traversal_util(const int& v, vector<vertex<T>> traversed, directed_graph<T>& mst) {

//    IF leaf
    if (mst.out_degree(v) == 0) {
        if (logs) cout << v << ", ";
        traversed.push_back(vertex<T>(v, mst.vertex_weights[v]));
        return traversed;
    } else {
        vector<vertex<T>> elems;
        bool first_edge = true;
    //    Vertex weight comparision
        elems = mst.get_neighbours(v);

    //    Edge weight comparision
//        elems = mst.get_edges(v);

        sort(elems.begin(), elems.end(), less_weight);

        for (auto &e : elems) {
            traversed = in_order_traversal_util(e.id, traversed, mst);
            if (first_edge) {
                if (logs) cout << v << ", ";
                traversed.push_back(vertex<T>(v, mst.vertex_weights[v]));
            }
            first_edge = false;
        }
    }
    return traversed;
}

/*
 Controller function to handle in order traversa;
 
 dependant: in_order_traversal_util()
 params:
    int - from vertex
    directed_graph<T> - graph to traverse
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::in_order_traversal(const int& u_id, directed_graph<T>& mst) {

    vector<vertex<T>> traversed;
    return in_order_traversal_util(u_id, traversed, mst);
}

/*
 Recursively traverse the graph in a post-order fashion
 
 pseduo code
 in_order_traversal(vertex)
 IF vertex has no edges
     add_vertex()
     return
 ELSE
     FOR EACH edge IN edges
         post_order_traversals(neighbour)
         add_vertex()
 
 dependant:
    - less_weight Comparitor
    - get_edges() Utility OR
    - get_neighbours() Utility
 params:
    int - from vertex
    directed_graph<T> - graph to traverse
 returns:
    vector<vertex<T>>
 */
template <typename T>
vector<vertex<T>> directed_graph<T>::post_order_traversal_util(const int& v, vector<vertex<T>> traversed, directed_graph<T>& mst) {

//    IF leaf
    if (mst.out_degree(v) == 0) {
        if (logs) cout << v << ", ";
        traversed.push_back(vertex<T>(v, mst.vertex_weights[v]));
        return traversed;
    } else {
        vector<vertex<T>> elems;

    //    Vertex weight comparision
//        elems = mst.get_neighbours(v);

    //    Edge weight comparision
            elems = mst.get_edges(v);

        sort(elems.begin(), elems.end(), less_weight);

        for (auto &e : elems) {
            traversed = post_order_traversal_util(e.id, traversed, mst);
        }

        if (logs) cout << v << ", ";
        traversed.push_back(vertex<T>(v, mst.vertex_weights[v]));

    }
    return traversed;
}

/*
 Controller function to handle post order traversa;
 
 dependant: post_order_traversal_util()
 params:
    int - from vertex
    directed_graph<T> - graph to traverse
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::post_order_traversal(const int& u_id, directed_graph<T>& mst) {

    vector<vertex<T>> traversed;
    return post_order_traversal_util(u_id, traversed, mst);
}

/*
 Reference: https://www.cplusplus.com/articles/NhA0RXSz/
 Reference: https://www.geeksforgeeks.org/sorting-a-vector-in-c/
 Reference: https://cplusplus.com/reference/algorithm/sort/
 Reference: https://www.geeksforgeeks.org/internal-details-of-stdsort-in-c/
 
 Sort vertices in ascending or based on vertex weight comparison
 Uses STL sort alogrithm which uses IntroSort and has O(n log n) time complexity
 
 dependant:
    - less_weight() Comparitor
    - get_vertices() Utility
 returns:
    vector<vertex<T>>
*/
template <typename T>
vector<vertex<T>> directed_graph<T>::significance_sorting() {

    vector<vertex<T>> elems = get_vertices();
    sort(elems.begin(), elems.end(), less_weight);

    if (logs) {
        cout << "\nsorted elems" << endl;
        for (auto &e : elems) {
            cout << e.id << " " << e.weight << ", ";
        }
    }

    return elems;

}

/*
IMPLEMENTATION: End
*/


// ********************************************************** GRAVEYARD

//   Post order traversal Shunting Yard
//    stack<int> shunting_yard;
//    vector<vertex<T>> elems;
//    vector<vertex<T>> traversed;
//    int top;
//
//    shunting_yard.push(u_id);
//
////        TESTING
//    int i = 0;
////    while (i < 4) {
//    while (!shunting_yard.empty()) {
//
//        if (logs) cout << "_______ Loop: " << i <<" _______" << endl;
//
//        top = shunting_yard.top();
//        shunting_yard.pop();
//
//        if (logs) cout << "top: " << top << " yard size: " << shunting_yard.size()<< endl;
//
//        traversed.push_back(vertex<T>(top, mst.vertex_weights[top]));
//
//        if (mst.out_degree(top) > 0) {
////            elems = mst.get_neighbours(top);
//            elems = mst.get_edges(top);
//            sort(elems.begin(), elems.end(), more_weight);
//
//            if (logs) {
//                cout << "\tsorted elems: ";
//                for (auto &e : elems) {
//                    cout << e.id << " " << e.weight << ", ";
//                }
//            }
//
//            if (logs) cout << "\n\tPush children: ";
//            while (!elems.empty()) {
//                if (logs) cout << elems.back().id << ", ";
//                shunting_yard.push(elems.back().id);
//                elems.pop_back();
//            }
//        }
//
////        TESTING
//        i++;
//        if (logs) cout << endl;
//    }
//
//    reverse(traversed.begin(),traversed.end());
//    return traversed;

//    Iterate over the Adjacent List and all Edges
//    for (auto &x : adj_list){
//        if (logs) cout << "Vertex id: " << x.first << "\n Adjacent List: ";
//        for (auto &y : x.second) {
//            if (logs) cout << y.first << " ";
//        }
//        if (logs) cout << endl;
//    }
//    if (logs) cout << endl;


//    Read elements in a heap
//    for (auto v : vertex_weights) {
//        cout << v.first << " " << v.second << endl;
//        heap.push(vertex<T>(v.first, v.second));
//    }

//    cout << "\nPopping out elements...";
//    while (!heap.empty()) {
//        next = heap.top();
//        cout << " " << next.id << " -> " << next.weight << ", ";
//
//        visited[next.id] = true;
//
//        heap.pop();
//    }
//    cout << endl;


//    DFS
//    vector<vertex<T>> traversed;
//    static size_t size = vertex_weights.size();
//    bool *visited = new bool[size];
//
//    for (auto v : vertex_weights) {
//        visited[v.first] = false;
//    }
//
////    Call the recursive helper function to print DFS traversal
//    traversed = dfs_util(u_id, visited, traversed);


//// Reference: https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
//template <typename T>
////bool directed_graph<T>::is_cyclic(int v, bool visited[], int parent) const {
//bool directed_graph<T>::is_cyclic(int v, bool visited[], int parent) const {
//
//    visited[v] = true;
////    vector<vertex<T>> neighbours = get_neighbours(v);
//    vector<int> neighbours;
////    Get neightbours
//    if (adj_list.find(v) != adj_list.end()) {
//        for (auto &x : adj_list.at(v)) {
//            neighbours.push_back(x.first);
//        }
//    }
//
//    if (logs) cout << endl << "Entered is_cyclic looking at : " << v << " marked = " << visited[v] << endl;
//
//    for (auto u : neighbours) {
//        if (logs) cout << " u: " << u << " | ";
////        If an adjacent is not visited, then recur for that adjacent
//        if (!visited[u]) {
//            if (is_cyclic(u, visited, v)) return true;
//
////       If an adjacent is visited and not parent of current vertex, then there is a cycle. IF UNDIRECTED
//        } else if (u != parent) return true;
//    }
//    return false;
//}


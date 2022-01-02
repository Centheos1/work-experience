//
//  main.cpp
//  Tarjans Algorithm
//
//  Created by Clint Sellen on 3/6/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//


#include <iostream>
#include <list>
#include <stack>
#define NIL -1
using namespace std;

class Graph {
    int V;
    list<int> *adj;

    void SCCUtil(int u, int disc[], int low[], stack<int> *st, bool stackMember[]);
    
public:
    Graph(int V);
    void addEdge(int v, int w);
    void SCC(); };

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V]; }

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w); }


void Graph::SCC() {
    int *disc = new int[V];
    int *low = new int[V];
    bool *stackMember = new bool[V];
    stack<int> *st = new stack<int>();
    
    for (int i = 0; i < V; i++) {
      disc[i] = NIL;
      low[i] = NIL;
      stackMember[i] = false;
    }
    
    for (int i = 0; i < V; i++) {
        if (disc[i] == NIL) {
            SCCUtil(i, disc, low, st, stackMember);
        }
    }
}

void Graph::SCCUtil(int u, int disc[], int low[], stack<int> *st, bool stackMember[]) {
    static int time = 0;
    disc[u] = low[u] = ++time;
    st->push(u);
    stackMember[u] = true;
    list<int>::iterator i;
    
    for (i = adj[u].begin(); i != adj[u].end(); ++i) {
        int v = *i;
        if (disc[v] == -1) {
            SCCUtil(v, disc, low, st, stackMember);
            low[u] = min(low[u], low[v]);
        } else if (stackMember[v] == true) {
            low[u] = min(low[u], disc[v]);
        }
    }
    
    int w = 0;
    if (low[u] == disc[u]) {
        while (st->top() != u) {
            w = (int) st->top();
            cout << w << " ";
            stackMember[w] = false;
            st->pop();
        }
        
        w = (int) st->top();
        cout << w << "\n";
        stackMember[w] = false;
        st->pop();
    }
}

int main() {
    cout << "\nSCCs in the graph \n";
//    Graph g(6);
//    g.addEdge(0, 3);
//    g.addEdge(1, 2);
//    g.addEdge(1, 4);
//    g.addEdge(1, 5);
//    g.addEdge(2, 5);
//    g.addEdge(3, 2);
//    g.addEdge(4, 0);
//    g.addEdge(4, 1);
//    g.addEdge(5, 0);
    
    Graph g(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 4);
    g.addEdge(2, 3);
    g.addEdge(3, 0);
    g.addEdge(3, 2);
    
    g.SCC();
    return 0;
}

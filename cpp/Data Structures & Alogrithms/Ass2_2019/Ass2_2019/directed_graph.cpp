#include <vector>
#include <queue>
#include <stack>
#include <unordered_set>
#include <unordered_map>
#include <list>
#include <exception>
#include <stdexcept>

#include "directed_graph.hpp"
#include "disjoint_set.hpp"

template <typename vertex> std::list<vertex> topological_sort(const directed_graph<vertex>&);

/*
 * Computes whether the input is a Directed Acyclic Graph (DAG).
 * A digraph is a DAG if there is no vertex that has a cycle.
 * A cycle is a non-empty set of [out-]edges that starts at one
 * vertex, and returns to it.
 */
template <typename vertex>
bool is_dag(const directed_graph<vertex> & d) {
  try {
    topological_sort(d);
  }
  catch (std::invalid_argument & e) {
    return false;
  }

  return true;
}

template <typename vertex>
void extend_topological_order(const directed_graph<vertex> & d, const vertex & v, std::list<vertex> & ordering, std::unordered_set<vertex> & unused, std::unordered_set<vertex> & temp_mark) {
  if (unused.count(v) != 0) {
    if (temp_mark.count(v) > 0) throw std::invalid_argument("Input graph is not a DAG");
    temp_mark.insert(v);
    for (auto nit = d.nbegin(v); nit != d.nend(v); ++nit){
      extend_topological_order(d, *nit, ordering, unused, temp_mark);
    }
    temp_mark.erase(v);
    unused.erase(v);
    ordering.push_front(v);
  }
}

/*
 * Computes a topological ordering of the vertices.
 * For every vertex u in the order, and any of its
 * neighbours v, v appears later in the order than u.
 */
template <typename vertex>
std::list<vertex> topological_sort(const directed_graph<vertex> & d) {
  std::list<vertex> ordering;
  std::unordered_set<vertex> unused(d.begin(), d.end());
  std::unordered_set<vertex> temp_mark;

  while (!unused.empty()) {
    auto v = *unused.begin();
    extend_topological_order(d, v, ordering, unused, temp_mark);
  }
  
  return ordering;
}

/*
 * Given a DAG, computes whether there is a Hamiltonian path.
 * a Hamiltonian path is a path that visits every vertex
 * exactly once.
 */
template <typename vertex>
bool is_hamiltonian_dag(const directed_graph<vertex> & d) {
  auto topo_order = topological_sort(d);

  auto v = topo_order.begin();
  for (auto it = ++topo_order.begin(); it != topo_order.end(); ++it) {
    if (!d.adjacent(*v, *it)) return false;
    v = it;
  }

  return true;
}


/*
 * Computes the weakly connected components of the graph.
 * A [weak] component is the smallest subset of the vertices
 * such that the in and out neighbourhood of each vertex in
 * the set is also contained in the set.
 */
template <typename vertex>
std::vector<std::vector<vertex>> components(const directed_graph<vertex> & d) {

  std::vector<std::vector<vertex>> all_components;
  std::unordered_set<vertex> unused(d.begin(), d.end());
  disjoint_set<vertex> component_set;

  for (auto v : d) {
    component_set.make_set(v);
  }

  for (auto v : d) {
    for (auto nit = d.nbegin(v); nit != d.nend(v); ++nit) {
      component_set.set_union(v, *nit);
    }
  }

  std::unordered_map<vertex, std::vector<vertex>> gathered_sets;

  for (auto v : d) {
    auto r = component_set.find(v);
    if (!gathered_sets.count(r)) gathered_sets.insert({r, std::vector<vertex>()});
    gathered_sets.at(r).push_back(v);
  }

  for (auto p : gathered_sets) all_components.push_back(p.second);

  return all_components;
  
}

/*
 * Computes the strongly connected components of the graph.
 * A strongly connected component is a subset of the vertices
 * such that for every pair u, v of vertices in the subset,
 * v is reachable from u and u is reachable from v.
 */

template <typename vertex>
void tarjan_scc(const directed_graph<vertex> & d, const vertex & u, std::size_t & index_counter, std::stack<vertex> & s, std::unordered_map<vertex, std::size_t> & index, std::unordered_map<vertex, std::size_t> & low, std::unordered_map<vertex, bool> & in_stack, std::vector<std::vector<vertex>> & result) {
  index[u] = index_counter;
  low[u] = index_counter;
  index_counter++;
  s.push(u);
  in_stack[u] = true;

  for (auto nit = d.nbegin(u); nit != d.nend(u); ++nit) {
    if (index.count(*nit) == 0) {
      tarjan_scc(d, *nit, index_counter, s, index, low, in_stack, result);
      low[u] = std::min(low.at(u), low.at(*nit));
    }
    else if (in_stack.at(*nit)) {
      low[u] = std::min(low.at(u), index.at(*nit));
    }
  }

  if (low.at(u) == index.at(u)) {
    std::vector<vertex> component;
    vertex w;
    do {
      w = s.top();
      s.pop();
      in_stack[w] = false;
      component.push_back(w);
    }
    while (w != u);
    result.push_back(component);
  }
  
}

template <typename vertex>
std::vector<std::vector<vertex>> strongly_connected_components(const directed_graph<vertex> & d) {

  std::size_t index_counter = 0;
  std::stack<vertex> s;
  std::unordered_map<vertex, std::size_t> index;
  std::unordered_map<vertex, std::size_t> low;
  std::unordered_map<vertex, bool> in_stack;
  std::vector<std::vector<vertex>> result;

  for (auto v : d) in_stack[v] = false;

  for (auto v : d) {
    if (index.count(v) == 0) {
      tarjan_scc(d, v, index_counter, s, index, low, in_stack, result);
    }
  }
  return result;
}

/*
 * Computes the shortest distance from u to every other vertex
 * in the graph d. The shortest distance is the smallest number
 * of edges in any path from u to the other vertex.
 * If there is no path from u to a vertex, set the distance to
 * be the number of vertices in d plus 1.
 */
template <typename vertex>
std::unordered_map<vertex, std::size_t> shortest_distances(const directed_graph<vertex> & d, const vertex & u) {
    
    std::unordered_map<vertex, std::size_t> distances;

    std::queue<std::pair<vertex, std::size_t>> to_process;
    to_process.push({u,0});

    while (!to_process.empty()) {
        auto p = to_process.front();
        to_process.pop();
        if (!distances.count(p.first)) {
            distances[p.first] = p.second;
            for (auto nit = d.nbegin(p.first); nit != d.nend(p.first); ++nit) {
                to_process.push({*nit, p.second + 1});
            }
        }
    }

    for (auto v : d) {
        if (!distances.count(v)) distances[v] = d.num_vertices() + 1;
    }

    return distances;
}


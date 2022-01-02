#ifndef DISJOINT_SET
#define DISJOINT_SET

#include <vector>
#include <unordered_map>

template <typename T>
struct entry {
  T element;
  std::size_t parent;
  std::size_t size;

  entry(const T& t, std::size_t p) : element(t), parent(p), size(1) {}
};

template <typename T, class Hash = std::hash<T>>
class disjoint_set {

private:

  std::unordered_map<T, std::size_t, Hash> index_map;
  std::vector<entry<T>> set_tree;
  
public:
  
  void make_set(const T& t) {
    if (!index_map.count(t)) {
      auto e = entry(t, set_tree.size());
      set_tree.push_back(e);
      index_map.insert({t, e.parent});
    }
  }

  T find(const T& t) {
    auto x = index_map.at(t);
    while (set_tree.at(x).parent != x) {
      auto n = set_tree.at(x).parent;
      set_tree.at(x).parent = set_tree.at(n).parent;
      x = n;
    }
    return set_tree.at(x).element;
  }

  void set_union(const T& t, const T& v) {
    auto tRoot = index_map.at(find(t));
    auto vRoot = index_map.at(find(v));

    if (tRoot != vRoot) {

      if (set_tree.at(tRoot).size < set_tree.at(vRoot).size) {
    tRoot ^= vRoot;
    vRoot ^= tRoot;
    tRoot ^= vRoot;
      }

      set_tree.at(vRoot).parent = tRoot;
      set_tree.at(tRoot).size += set_tree.at(vRoot).size;
    }
  }
};

#endif

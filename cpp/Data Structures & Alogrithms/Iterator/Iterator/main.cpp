#ifndef MY_FIRST_ARRAY_CLASS
#define MY_FIRST_ARRAY_CLASS

#include <cstddef>
#include <string>

template <typename T>
class array_iterator;

template <typename T>
class array_holder {

private:
  const T * const array;
  const std::size_t array_size;

public:

  array_holder(const T * const a = nullptr, const std::size_t s = 0) : array(a), array_size(s) {}
  ~array_holder() {}

  const T& operator[](const std::size_t idx){ return array[idx]; }
  const T& operator[](const std::size_t idx) const { return array[idx]; }

  array_iterator<T> begin() { return array_iterator<T>(array, 0); }
  array_iterator<T> end() { return array_iterator<T>(array, array_size); }

  const array_iterator<T> begin() const { return array_iterator<T>(array, 0); }
  const array_iterator<T> end() const { return array_iterator<T>(array, array_size); }

  std::string to_string() const {
    std::string s = "[";
    for (auto i = 0; i < array_size; ++i) {
      s += std::to_string(array[i]);
      if (i < array_size - 1) s += ", ";
    }
    s += "]";
    return s;
  }
  
};

template <typename T>
std::ostream& operator<<(std::ostream& stream, array_holder<T> ah) {
  return stream << ah.to_string();
}

#endif

#ifndef ARRAY_ITER
#define ARRAY_ITER

#include <cstddef>

template <typename T>
class array_iterator {

private:
  const T * const base_array;
  std::size_t position;

public:

  array_iterator(const T * const ba = nullptr, std::size_t pos = 0) : base_array(ba), position(pos) {}
  array_iterator(const array_iterator<T> & other) : base_array(other.base_array), position(other.position) {}
  ~array_iterator() {}

  array_iterator<T>& operator=(const array_iterator<T>& other) {
    base_array = other.base_array;
    position = other.position;
  }

  bool operator==(const array_iterator<T>& other) {
    return (base_array == other.base_array && position == other.position);
  }

  bool operator!=(const array_iterator<T>& other) {
    return (base_array != other.base_array || position != other.position);
  }

  array_iterator<T>& operator++() {
    ++position;
    return *this;
  }

  array_iterator<T>& operator++(int) {
    auto temp = this;
    ++position;
    return temp;
  }

  const T& operator*() {
    return base_array[position];
  }

  T* operator->() {
    return base_array + position;
  }
  
};

#endif

#include <iostream>

int main() {

  int a[] = {1,2,3};
  array_holder<int> ah(a,3);
  std::cout << ah << std::endl;

  for (auto it = ah.begin(); it != ah.end(); ++it) {
    std::cout << *it << std::endl;
  }

}


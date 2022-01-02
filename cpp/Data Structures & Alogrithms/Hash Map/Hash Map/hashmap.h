//
//  Header.h
//  Hash Map
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef hashmap_h
#define hashmap_h
//#include <bits/stdc++.h>
#include <iostream>
#include <list>
#include <vector>
#include <cmath>
#include <utility>
#include <unordered_map>
using namespace std;

//Advanced Implementation
/*
    This function checks whether a given number is a prime number or not.
*/
bool is_prime(const size_t& p) {
    
    // if p can be divided exactly by any value, it is not prime
    for (auto f = 2; f*f < p; f++) {
        if (!p%f){ // "!p%f" equals "p%f == 0"
            return false;
        }
    }
    // otherwise, it is a prime number
    return true;
}

/*
    A template for the hashmap class.
*/
template <typename T>
class hashmap{

    private:

        vector<list<pair<int, T>>> map; // a vector of lists of pairs
        size_t size; // the size of "map"

        // this function gives a bigger prime number than the input
        void new_prime(size_t& size);
    
        // an internal function used by rehash();
        //    it simply call the other add(.) function
        void add(pair<int, T>& p);
    
        // a hash function to map a key to a hashkey (or a location index)
        size_t hash(const int& key) const;
    
        // the function to expand and restructure to hashmap
        void rehash();
    
    public:
    
        // constructor,
        hashmap(size_t initial_size = 20);

        // this function addes a pair to "map"
        void add(const int& key, const T& value);

        // get value by key
        T get(const int& key) const;
    
        // get key by value
        int get_key(const T& value) const;
        
        // check if a key exists in "map"
        bool has_key(const int& key) const;

        // check if a value is stored in "map"
        bool has_value(const T& value) const;

};


// this function gives a bigger prime number than the input
template <typename T>
void hashmap<T>::new_prime(size_t& size) {

    do{
        size += 2; // prime numbers must be odd numbers
    } while (!is_prime(size));

}

// an internal function used by rehash();
//    it simply call the other add(.) function
template <typename T>
void hashmap<T>::add(pair<int, T>& p) {
    add(p.first, p.second);
}

// a hash function to map a key to a hashkey (or a location index)
template <typename T>
size_t hashmap<T>::hash(const int& key) const {

    // make sure key is a positive number
    auto hashed_key = key < 0 ? -1*key : key;

    // use simple division to get the hashed_key
    return hashed_key % size;

}

// the function to expand and restructure to hashmap
template <typename T>
void hashmap<T>::rehash() {

    // temporarily move everything in "map" to a list
    list<pair<int, T>> entries;
    for (auto l : map) { // every list in vector "map"
        for (auto p : l) { // every pair in the list
            entries.push_front(p); // add it to the list
        }
    }
    map.resize(0); // and clear "map".

    // move everything back to a bigger "map"
    new_prime(size); // get a new size, which is a prime number
    map.resize(size); // set the map to the new size
    for (auto p : entries){
        add(p); // copy everything in the old "map" to the new "map"
    }
}

// constructor
template <typename T>
hashmap<T>::hashmap(size_t initial_size) {

    size = initial_size; // initialise size
    while (!is_prime(size)){
        size++; // increase size until it becomes a prime number
    }
    // create "map" of the given size
    map = vector<list<pair<int, T>>>(size);

}

// this function addes a pair to "map"
template <typename T>
void hashmap<T>::add(const int& key, const T& value) {

    auto hashed_key = hash(key);

    // add this pair to "map"
    map[hashed_key].push_front({key,value});

    // we ensure each hashed_key has no more than log10(size) values
    if (map[hashed_key].size() > log10(size)){
        rehash(); // via expanding the hashmap size (using rehash()).
    }

}

// get value by key
template <typename T>
T hashmap<T>::get(const int& key) const {

    for (auto p : map[hash(key)]) {
        if (p.first == key){
            return p.second; // return the value if found;
        }
    }
    return T(); // return an empty object otherwise.

}

// get key by value
template <typename T>
int hashmap<T>::get_key(const T& value) const {

    for (auto l : map) {
        for (auto p : l) {
            if (p.second == value){
                return p.first; // return the key if found;
            }
        }
    }
    return -1; // -1 indicates "not found".

}

// check if a key exists in "map"
template <typename T>
bool hashmap<T>::has_key(const int& key) const {

    for (auto p : map[hash(key)]) {
        if (p.first == key){
            return true;
        }
    }
    return false;

}

// check if a value is stored in "map"
template <typename T>
bool hashmap<T>::has_value(const T& value) const {

    for (auto l : map) {
        for (auto p : l) {
            if (p.second == value){
                return true;
            }
        }
    }
    return false;

}


// Basic Implementation
//template <typename T> // allow us to decide what data type to store later
//class hashmap{
//
//    private:
//
//        // A static member is shared by all objects of the class.
//        static const size_t SIZE = 5; // define a fixed-size map
//        pair<int, T> map[SIZE]; // use a pair to store (int, T)
//        bool in_use[SIZE] {false}; // monitor the use of map
//
//        unsigned hash(const int&) const; // get the hash value of a key
//
//    public:
//
//        void add(const int&, const T&); // add a new pair to the hashmap
//
//        const T get(const int&) const; // get a value by key
//        int get_key(const T&) const; // get the key of a value
//
//        bool has_key(const int&) const; // check if hashmap contains a key
//        bool has_value(const T&) const; // check if hashmap contains a value
//
//};
//
//// a simple function (a MOD function) to generate a hash value of an int
//template <typename T>
//unsigned hashmap<T> :: hash(const int& key) const {
//
//    return key % SIZE;
//
//}
//
//// add a (key, value) pair to the hashmap
//template <typename T>
//void hashmap<T> :: add(const int& key, const T& value){
//
//    auto hashed_key = hash(key); // get hashed_key of key
//
//    unsigned probes = 0;
//    while (in_use[hashed_key] && map[hashed_key].first != key && probes < SIZE){
//        // if the position indicated by the hashed_kay is taken and
//        // the value there cannot be override, keep checking the next position.
//        hashed_key = (hashed_key+1) % SIZE;
//        probes++;
//    }
//
//    // possibility 1: if a vacant position is found,
//    //         store {key, value} to that position.
//    if (probes < SIZE){
//        map[hashed_key] = {key, value};
//        in_use[hashed_key] = true; // make that position as taken
//        cout << "{" << key << ", " << value << "} added." << endl;
//    }else{
//        // the map is full and not element can be overriden.
//        cout << "Adding failed!" << endl;
//    }
//
//}
//
//// get value by key
//template <typename T>
//const T hashmap<T> :: get(const int& key) const{
//
//    auto hashed_key = hash(key); // get hashed_key of the key
//
//    // look up key in the map, from the position indicated by hashed_key.
//    unsigned attempts = 0;
//    while (attempts < SIZE && in_use[hashed_key]){
//        if (map[hashed_key].first == key){
//            // if found, return the value of the key
//            return map[hashed_key].second;
//        }
//        hashed_key = (hashed_key + 1) % SIZE;
//        attempts++;
//    }
//
//    // otherwise, return an empty T object
//    return T();
//}
//
//// get key by value
//template <typename T>
//int hashmap<T> :: get_key(const T& value) const {
//
//    for (int i = 0; i < SIZE; ++i){
//        // check all positions in use to look up the key of the value
//        if (in_use[i] && map[i].second == value){
//            return map[i].first;
//        }
//    }
//
//    return -1; // if not found, return -1.
//
//}
//
//// check if a key exists in map
//template <typename T>
//bool hashmap<T> :: has_key(const int& key) const {
//
//    auto hashed_key = hash(key); // get hashed_key from key
//
//    // look for key in the map, from the position indicated by hashed_key.
//    unsigned attempts = 0;
//    while (attempts < SIZE && in_use[hashed_key]){
//        if (map[hashed_key].first == key){
//            return true; // if found, return true;
//        }
//        hashed_key = (hashed_key + 1) % SIZE;
//        attempts++;
//    }
//
//    return false; // return false, if not found.
//
//}
//
//// check if a value exists in map
//template <typename T>
//bool hashmap<T> :: has_value(const T& value) const {
//
//    // check all positions in use to look up the value
//    for (int i = 0; i < SIZE; ++i){
//        if (in_use[i] && map[i].second == value){
//            return true; // if found, return true;
//        }
//    }
//
//    return false; // return false, if not found.
//
//}

#endif /* hashmap_h */

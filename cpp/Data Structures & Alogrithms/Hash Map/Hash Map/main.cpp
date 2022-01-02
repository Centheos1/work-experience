//
//  main.cpp
//  Hash Map
//
//  Created by Clint Sellen on 26/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include "hashmap.h"

//Advanced implementation Driver
int main(){
    
    // prepare test data
    
    unordered_map<int, int> test_data; // use unordered_map to store test data
    
    srand(time(NULL)); // get a seed (dependent on computer clock) for generating random values
    auto n = rand()%1000; // randomly decide the number of elements within [0, 1000]
    auto current_key = 0;
    for (auto i = 0; i < n; i++){
        current_key += rand()%50 + 1;
        test_data[current_key] = rand()%100; // add a number smaller than 100
    }
    
    // add the test data to "map"
    hashmap<int> map;
    for (auto d : test_data){
        map.add(d.first, d.second);
    }
    
    cout << boolalpha; // configure to show "true/false" for bools
    
    unsigned count = 0;
    for (auto i = 0; i < current_key + 1; i++){ // current_key now stores the largest key in "map"
        if (map.has_key(i)){
            count++;
        }
    }
    
    cout << "Does hashmap and test_data contain the same number of values? " << (count==test_data.size()) << endl;
}

// Basic implementation Driver
//int main(){
//
//    hashmap<string> map;
//
//    map.add(5, "First"); // first attempt at positoin 5%5 = 0 successful.
//    map.add(8, "Second"); // first attempt at positoin 8%5 = 3 successful.
//    map.add(12, "Third"); // first attempt at positoin 12%5 = 2 successful.
//    map.add(45, "Fourth"); // first attempt at 45%5 = 0 fails; then succeed at the next positoin 0+1 = 1.
//    map.add(2, "Fifth"); // first attempt at 2%5 = 2 fails, then fails at 3, and finally, succeed at 4.
//    map.add(24, "Sixth"); // first attempt at 24%5 fails; then all atempts to other positions fail because map is full.
//    map.add(5, "Seventh"); // first attempt at 5%5=0. update the value of the key 5 into "Seventh".
//
//    cout << boolalpha;
//    cout << "Is there something with key " << 5 << "? " << map.has_key(5) << endl;
//    cout << "Is there something with key " << 8 << "? " << map.has_key(8) << endl;
//    cout << "Is there something with key " << 24 << "? " << map.has_key(24) << endl;
//    cout << "Is there something with key " << -46 << "? " << map.has_key(-46) << endl;
//
//    cout << "Does it have a value " << "First" << "? " << map.has_value("First") << endl;
//    cout << "Does it have a value " << "Second" << "? " << map.has_value("Second") << endl;
//    cout << "Does it have a value " << "Third" << "? " << map.has_value("Third") << endl;
//    cout << "Does it have a value " << "Fourth" << "? " << map.has_value("Fourth") << endl;
//
//    cout << "What is the position of value " << "First" << "? " << map.get_key("First") << endl;
//    cout << "What is the position of value " << "Second" << "? " << map.get_key("Second") << endl;
//    cout << "What is the position of value " << "Third" << "? " << map.get_key("Third") << endl;
//    cout << "What is the position of value " << "Fourth" << "? " << map.get_key("Fourth") << endl;
//
//    cout << "Getting something with key " << 5 << "... We got " << map.get(5) << endl;
//    cout << "Getting something with key " << 8 << "... We got " << map.get(8) << endl;
//    cout << "Getting something with key " << 24 << "... We got " << map.get(24) << endl;
//    cout << "Getting something with key " << -46 << "... We got " << map.get(-46) << endl;
//}

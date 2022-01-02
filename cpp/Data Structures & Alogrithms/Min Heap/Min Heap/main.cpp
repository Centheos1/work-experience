//
//  main.cpp
//  Min Heap
//
//  Created by Clint Sellen on 15/4/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>

#include "heap.hpp"

int main() {

    heap<int, string> h;

    int test_data[] = {8, 12, 40, 5, 10, 20, 19, 13, 17, 6};

    for (int i = 0; i < 10; i++){ // insert the test data
        h.insert(test_data[i], "entry-" + to_string(test_data[i]));
    }

    while (!h.empty()) {
        cout << " " << h.remove();
    }
    cout << endl;
  
}

//
//#include "simple_min_heap.hpp"
//
//using namespace std;
//
//int main() {
//
//    min_heap h;
//
//    int test_data[] = {8, 12, 40, 5, 10, 20, 19, 13, 17, 6};
//  
//    for (int i = 0; i < 10; i++){ // insert test data into heap
//        h.insert(test_data[i]);
//    }
//    
//    while (!h.empty()) { // print out the removal sequence;
//                         // for a min-heap, it should be in ascending order.
//        cout << " " << h.remove();
//    }
//    cout << endl;
//  
//}

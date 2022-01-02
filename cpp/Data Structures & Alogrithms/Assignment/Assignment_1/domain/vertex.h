//
//  vertex.h
//  Assignment_1
//
//  Created by Clint Sellen on 20/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef vertex_h
#define vertex_h

/*
    the vertex class
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


#endif /* vertex_h */

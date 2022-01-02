//
//  edge.h
//  Assignment_1
//
//  Created by Clint Sellen on 20/5/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#ifndef edge_h
#define edge_h

/*
    the edge class
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


#endif /* edge_h */

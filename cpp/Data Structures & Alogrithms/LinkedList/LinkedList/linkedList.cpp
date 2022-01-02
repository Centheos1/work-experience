//
//  linkedList.cpp
//  LinkedList
//
//  Created by Clint Sellen on 16/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include "linkedList.h"

using namespace std;

LinkedList::Node::Node(Node * next, int data) {
    this->next = next;
    this->data = data;
}

LinkedList::Node::~Node() {
  //You don't have to do anything for this one.
}

int LinkedList::Node::getData(){
    return data;
}

LinkedList::Node * LinkedList::Node::getNext(){
    return next;
}

void LinkedList::Node::setNext(Node * next){
    this->next = next;
}

LinkedList::LinkedList(){
    this->head = nullptr;
}

LinkedList::~LinkedList(){
}

void LinkedList::prepend(int data){
    
//    cout << "prepend(" << data << ")\n";
    
    Node* n = new Node(head, data);
    head = n;
}

void LinkedList::append(int data){
    Node* tail_node = head;
    
    while (tail_node->getNext() != nullptr) {
        tail_node = tail_node->getNext();
    }
    
//    cout << "tail_node = " << tail_node->getData() << endl;
    
    Node* n = new Node(nullptr, data);
    tail_node->setNext(n);
    
}

void LinkedList::display(){
    Node* n = head;
    int cnt = 1;
    
    if (n == nullptr) {
        cout << "display(): Emplty List";
        return;
    }
    
    while (n->getNext() != nullptr) {
        cout << cnt << ": " << n->getData() << endl;
        n = n->getNext();
        cnt++;
    };
    
    cout << cnt << ": " << n->getData() << endl;
}


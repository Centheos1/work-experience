//
//  main.cpp
//  Doubly Linked List
//
//  Created by Clint Sellen on 24/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include "doublyLinkedList.h"
using namespace std;

/*
  Part 3: Implementations
*/

DoublyLinkedList::Node::Node(int data, Node * previous, Node * next)
{
    this->data = data;
    this->previous = previous; // add a new pointer to the precedent node
    this->next = next;
}

DoublyLinkedList::Node::~Node() {}

DoublyLinkedList::DoublyLinkedList()
{
    this->head = nullptr;
    this->tail = nullptr;
}

DoublyLinkedList::~DoublyLinkedList() {}

void DoublyLinkedList::prepend(int data)
{
    Node* n = new Node(data, nullptr, head);
    
    if (head != nullptr) {
        head->previous = n;
    }
    
    if (tail == nullptr) {
        tail = n;
    }
    
    head = n;
}

void DoublyLinkedList::append(int data)
{
    Node* n = new Node(data, tail, nullptr);
    
    if (head == nullptr) {
        head = n;
    }
    
    if (tail != nullptr) {
        tail->next = n;
    }
    
    tail = n;
}

void DoublyLinkedList::display()
{
    Node * ptr = this->head;
    cout << "The list is:";

    while(ptr != nullptr) {
        cout << ' ' << ptr->data;
        ptr = ptr->next;
    }
    cout << endl;
}

/*
  Note how the backward pointer makes the reverse traverse of the list easier.
  We simply replace this->head by this->tail, and ptr->next by ptr->previous,
  and then it works!
*/
void DoublyLinkedList::reverse_display()
{
    Node * ptr = this->tail;
    cout << "The list is:";

    while(ptr != nullptr) {
        cout << ' ' << ptr->data;
        ptr = ptr->previous;
    }
    cout << endl;
}

int main()
{
    DoublyLinkedList * list = new DoublyLinkedList();
  
    for (int i = 0; i < 6; i++) {
        if(i<3) { // First, add three nodes to the front of the list
            list->prepend(i);
        } else { // Then, add three nodes to the end of the list
            list->append(i);
        }
    }

    list->display(); // print all nodes' data sequentially
    list->reverse_display(); // print all nodes' data in reverse order

    delete list; // prepare to exit by clearing allocations

    return 0; // terminate the program
}

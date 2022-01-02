#include <iostream>
#include "ourStack.h"
using namespace std;

/* File 3: Implementations */

OurStack::Node::Node(int data, Node * next){
    this->next = next;
    this->data = data;
}

OurStack::Node::~Node() {
}

OurStack::OurStack(){
  this->head = nullptr;
  this->length = 0;
}

OurStack::~OurStack(){
}

bool OurStack::empty(){
    return length == 0;
}

int OurStack::top(){
    if (empty()) {
        cout << "top() is empty = " << endl;
        return 0;
    }
    return head->data;
}

void OurStack::push(int data){
    // do something here
    cout << "Add " << data << endl;
    Node* newNode = new Node(data, this->head);
    head = newNode;
    setLength(getLength() + 1);
    display();
}

void OurStack::pop(){
    // do something here
    if (empty()) {
        return;
    }
    cout << "Remove " << this->top() << endl;
    Node* temp = head;
    temp = temp->next;
    head = temp;
//    delete temp;
    setLength(getLength() - 1);
    display();
}

void OurStack::display(){
    // do something
//    cout << "OurStack::display()" << endl;
    if (head == nullptr) {
        return;
    }
    Node* n = head;
    while (n->next != nullptr) {
        cout << n->data << " ";
        n = n->next;
    }
    cout << n->data << endl;
}

int OurStack::getLength() {
    return length;
}

void OurStack::setLength(int length) {
    this->length = length;
}

int main(){
    
    OurStack * st = new OurStack();

    // first adding then removing nodes
    for (int i=1; i<=10; i++){
        if(i <= 4){
          st->push(i);
        }else{
          st->pop();
        }
    }
    st->display(); // display data in all nodes one by one

    delete st;// prepare to exit via deallocation
    return 0;
}

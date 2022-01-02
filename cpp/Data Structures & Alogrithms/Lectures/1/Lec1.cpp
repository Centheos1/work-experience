//
//  main.cpp
//  DSA Lectures
//
//  Created by Clint Sellen on 16/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>

using namespace std;

// Lecture 1 - Question 2
struct student
{
    char first_name[20];
    char last_name[20];
    int sid;
    float score;
};

// Lecture 1 - Question 3
class Node
{
    public:
        int data; // the date stored in this node
        Node* next; // pointer to the next node in the list
};

class LinkedList
{
    public:
        int length; // length of the list
        Node* head; // pointer to the list

        LinkedList(); // constructor
        ~LinkedList(); // destructor
    
        void prepend(int data); // add an element (or node) to the font of the list
        void append(int data); // add an element (or node) to the end of the list
        void display(); // display the element values one by one
};

LinkedList::LinkedList() // Constructor
{
    this->length = 0;
    this->head = NULL;
}

LinkedList::~LinkedList() // Destructor
{
    cout << "Linked list deteled!" << endl;
}

void LinkedList::prepend(int data)
{
//    cout << "Inside prepend " << data << endl;
    Node* n = new Node();
    n->data = data;
    
    n->next = head;
    head = n;
    length++;
}

void LinkedList::append(int data)
{
//    cout << "Inside append " << data << endl;
    int cnt = 1;
//    Node* tail_node = l->head;
    Node* tail_node = head;
    
    while (tail_node->next != nullptr) {
        tail_node = tail_node->next;
        cnt++;
    }
    
//    cout << "tail_node " << cnt << " = " << tail_node->data << endl;
    
    Node* n = new Node();
    n->data = data;
    tail_node->next = n;
    n->next = nullptr;
    length++;
}

void LinkedList::display()
{
    Node* n = head;
    int cnt = 1;
    
    if (n == nullptr) {
        return;
    }
    
    for (int i = 0; i < length; i++) {
        cout << cnt << ": " << n->data << endl;
        n = n->next;
        cnt++;
    }
    
//  Other traversal options
//    while (n->next != nullptr) {
//        cout << cnt << ": " << n->data << endl;
//        n = n->next;
//        cnt++;
//    };
    
//    cout << cnt << ": " << n->data << endl;
//
//    do {
//        n = n->next;
//        cout << cnt << ": " << n->data << endl;
//        cnt++;
//    } while (n->next != nullptr);
    
}


int main(int argc, const char * argv[]) {
//  Lecture 1 - Question 1
//    cout << "Hello, World!\n";
//    return 0;
    
//     Lecture 1 - Question 2
//    This is dynamically assigned
//    student* s = new student();
//    string n;
//
//    cout << "Enter student information,"<< endl;
//    cout << "Enter first name: ";
////    getline(cin, n);
////    strcpy(s->first_name, n.c_str());
//    cin >> s->first_name;
//    cin.ignore();
//    cin.clear();
//
//    cout << "Enter last name: ";
////    getline(cin, n);
////    strcpy(s->last_name, n.c_str());
//    cin >> s->last_name;
//    cin.ignore();
//    cin.clear();
//
//    cout << "Enter student id: ";
//    cin >> s->sid;
//
//    cout << "Enter score: ";
//    cin >> s->score;
//
//    cout << "Displaying student information," << endl;
//    cout << "Name: " << s->first_name << " " << s->last_name << endl;
//    cout << "id: " << s->sid << endl;
//    cout << "Score: " << s->score << endl;
//    return 0;
    
//     Lecture 1 - Question 3
    
    // declare a pointer to an object/instance of class
    LinkedList* list = new LinkedList();
    
    for (int i = 0; i < 6; i++)
    {
        if(i<3){
            list->prepend(i);
        }else{
            list->append(i);
        }
    }
    list->display();
    cout << "List Length: " << list->length << endl;
    delete list;
    return 0;
    
}

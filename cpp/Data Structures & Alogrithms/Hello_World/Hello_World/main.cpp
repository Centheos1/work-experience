//
//  main.cpp
//  Hello_World
//
//  Created by Clint Sellen on 9/3/20.
//  Copyright © 2020 Clint Sellen. All rights reserved.
//

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <list>

using namespace std;

int func(int n){
    cout << "func" << endl;
    if (n <= 0) return -1; // case1
    if (n == 1) return 1;  // case2
    return func(n/2) + func(n-4); // case3
}

int main(){
//    func(4);
    
//    Question 6
//    int mark[4] = {19, 10, 8, 17, 9}; //  Error
//    int mark[] = {19, 10, 8, 17, 9}; // Good
//    int[4] mark; // Error
//    int mark[] = new int[4]; // Error
    
//    Question 17
//    vector<int>::iterator i; // Good
    
//    vector<int> v = {1,2,3};  // Error
//    for (auto i; v.begin(); ;i++) {
//        // pass
//    }
    
//    vector<char> v = {'a','b','c'}; // Error
//    if (vector<char>::iterator i != v.end()) {
//        cout << *i << " ";
//    }
    
//    vector<string> v = {"a","b","c"};
//    vector<string>::iterator i = v.end();
//    auto it1 = i - 5;
    
//    Question 24
    int x = 1;
    int * p1 = &x;
    int ** p2 = &p1;
    
    cout << "1.\t" << p2 << endl; // True
    cout << "2.\t" << p1 << " == " << &x << endl; // True
    cout << "3.\t" << &p1 << " " << p2 << endl; // False
    cout << "4.\t" << *p2 << " == " << p1 << endl; // True
    
}

//int mystery(std::vector<int> x1, std::vector<int> x2) {
//    int i1 = 0; // indicates a position in array x1
//    int i2 = 0; // indicates a position in array x2
//    int count = 0;
//    while ((i1 < x1.size()) || (i2 < x2.size())) {
//        if (i1 == x1.size()) {
//            ++count;
//            ++i2;
//        } else if (i2 == x2.size()) {
//            ++count;
//            ++i1;
//        } else if (x1[i1] == x2[i2]) {
//            ++i1;
//            ++i2;
//        } else if (x1[i1] < x2[i2]) {
//            ++count;
//            ++i1;
//        } else {
//            ++count;
//            ++i2;
//        }
//    }
//    return count;
//}
//
//int main() {
//    std::vector<int> x1 = {1, 3};
//    std::vector<int> x2 = {1, 2, 5};
//    std::cout << mystery(x1,x2) << std::endl;
//}

//int main(){
//
//int i = 5;
//int * p = &i;
//p += 10;
//cout << *p;
//
//}

//// Week 3 - Question 4
//
//vector<int> delete_element(vector<int> v, int num, vector<int>::iterator it){
//
////    vector<int>::iterator it;
//    for (it = v.begin(); it < v.end(); it++) {
//        if (*it == num) {
//            v.erase(it).base();
//        }
//    }
//    return v;
//}
//
//void display(vector<int> v, vector<int>::iterator it) {
//    for (it = v.begin(); it < v.end(); it++) {
//        cout << " " << *it;
//    }
//}
//
//int main() {
//
//    vector<int> m_vector;
//    vector<int>::iterator it;
//
//    for (int i=1; i<=5; i++) {
//        m_vector.push_back(i);
//    }
//
//    cout << "The vector elements are :";
//    display(m_vector, it);
////    for (int x: m_vector){
////        cout << " " << x;
////    }
//    cout << endl;
//
//    for (int i=1; i<=3; i++) {
//        m_vector = delete_element(m_vector, i, it);
//    }
//
//    cout << "The vector elements are :";
//    display(m_vector, it);
////    for (int x: m_vector){
////        cout << " " << x;
////    }
//    cout << endl;
//
////    The new vector after inserting elements is : 1 2 3 10 20 4 5
//    cout << "The new vector after inserting elements is :";
//
//    vector<int> b_vector;
//
//    b_vector.push_back(1);
//    b_vector.push_back(2);
//    b_vector.push_back(3);
//    b_vector.push_back(10);
//    b_vector.push_back(20);
//    b_vector.push_back(4);
//    b_vector.push_back(5);
//
//    display(b_vector, it);
////    for (int x: b_vector){
////        cout << x << " ";
////    }
//    cout << endl;
//
//    return 0;
//}


////Week 3 - Question 1
//
//class Node {
//    public:
//        string data[2];
//        Node * next;
//};
//
//int main(){
//
//    /* Step 1: Create an empty list */
//    Node * head = nullptr;
//
//    /* Step 2: Create the 1st node without using the "new" keyword */
//    Node node;
//    node.data[0] = "John";
//    node.data[1] = "Smith";
//    node.next = nullptr;
//    head = &node; // Add the 1st node to list
//
//    /* Step 3: Create the 2nd node using the "new" keyword */
//    Node * node_ptr = new Node();
//    node_ptr->data[0] = "Barack";
//    node_ptr->data[1] = "Obama";
//    node_ptr->next = nullptr;
//    head->next = node_ptr; // Add the 2nd node to list
//
//    /* Step 4: Traverse the list to print out all full_names */
//    Node * curr = head;
//    while (curr != nullptr){
//        cout << curr->data[0] << " " << curr->data[1];
//        if(curr->next != nullptr){
//            cout << " -> ";
//        }
//        curr = curr->next;
//    }
//    cout << endl;
//}


////Week 2 - Question 2
///*
//    @Name: add
//    @Return: int
//    @Parameters: int, int
//*/
//int add(int num1, int num2) {
//    return num1 + num2;
//}
//
///*
//    @Name: display
//    @Parameters: string
//*/
//void display(int num){
//    cout << "Their sum is: " << num << endl;
//}
//
//int main() {
//
//    int num1, num2;
//
//    /*
//        Handlng unexpected user input for age
//    */
//    cout << "Enter an integer:" << endl;
//
//    try {
//        cin >> num1;
//        if (cin.fail()){ // Exception 1 (input being not an int) is handled locally.
//            cout << "Not an integer:" << endl;
//            return -1;
//        } else if (num1 < 0){ // Excaption 2 (input being a negative number)
//            throw(num1); // To be handled by the outer "catch"
//        } else {
//            // Input is good; do nothing
//        }
//
//    } catch(int num1){
//        cout << "Your number, " << num1 << ", is not in the legal range." << endl;
//        return -1;
//    }
//
//    /*
//        Handle the block below in a similar way to the above
//    */
//    cout << "Enter a second integer: " << endl;
//    try{
//        cin >> num2;
//        if (cin.fail()){
//            cout << "The number is not an int." << endl;
//            return -1;
//        } else if (num1 + num2 < 0){
//            // it is impossible that user age was or will be a negative number
//            throw(num2);
//        } else {
//            // do nothing
//        }
//    } catch (int num2){
//        cout << "The number, " << num2 << ", is too small." << endl;
//        return -1;
//    }
//
//    // call function add and disply the returned result.
//    display(add(num1,num2));
//
//    return 0;
//}


//void swap(double& x, double& y) {
//    cout << "swapping\n";
//    double temp = x;
//    x = y;
//    y = temp;
//}

//Week 2 - Question 1
//int main() {
//
//    const string FILENAME = "/Users/clintsellen/Desktop/Uni/UTS/Semester 8/Data Structures & Alogrithms/demofile.txt";
//
//    cout << "What is your name? ";
//    string str;
//    getline(cin, str, '\n');
//    str = "Hello, " + str;
//
//    ofstream outputFile;
//    outputFile.open(FILENAME);
//
//    // Write four names to the file.
//    outputFile << str;
//
//    // Close the file
//    outputFile.close();
//
//    ifstream inputFile;
//    inputFile.open(FILENAME);
//
//    string input = "";
//    while (inputFile >> str) {
////        cout << str; // << " ";
//        input += str + " ";
//    }
////    cout << input.size();
//    input = input.substr(0, input.size()-1);
//    cout << input << endl;
//    inputFile.close();
//
//    return 0;
//}



//int main(int argc, const char * argv[]) {
//    Strings and io
//    cout << "Enter your name\n";
//    string name;
//    getline(cin, name, '\n');
//    cout << "Hello " << name << " - welcome to Data Strutures & Algrithms"<< endl;
//
//    struct student
//    {
//        char name[20];
//        int sid;
//        float score;
//    };
//
//    This is dynamically assigned
//    student* s = new student();
//
//    string n;
//
//    cout << "Enter Your Name: ";
//    getline(cin, n);
//    strcpy(s->name, n.c_str());
//
//    cout << "Student Id: ";
//    cin >> s->sid;
//
//    cout << "What is your score? ";
//    cin >> s->score;
//
//    cout << "Student info:\n" << s->name << " " << s->sid << " " << s->score << endl;
    
//    Loops
//    int num = 5;
//    int foo[5] = {1,2,3,4,5};
//    for(int f : foo){
//        cout << f;
//    }
//    cout << endl;
//    cout << sizeof(foo) << endl;
//    cout << sizeof(num) << endl;
//    cout << sizeof(foo) / sizeof(num) << endl;
//
//    for(int i=0; i < sizeof(foo) / sizeof(num); i++){
//        cout << foo[i];
//    }
//    cout << endl;
    
////    Pointers
////    Dereferrencing Operator: *
////    Address-Of Operator: &
//
////    Declare a pointer to a double
//    double* pointer_x = nullptr;
////    Will be null because not yet assigned
//    cout << pointer_x << endl;
//
////    Desclare double value
//    double x = 3.14;
//    cout << "x = " << x << endl;
//    cout << "Memory space of x " << &x << endl;
//
////    Assign value to memory space of pointer by address-of operator
//    pointer_x = &x;
//    cout << "pointer_x = " << pointer_x << endl;
//
//    cout << "dereferencing value *pointer_x = " << *pointer_x << endl;
//
//    double y = 2.71;
//    cout << "y = " << y << endl;
//    pointer_x = &y;
//    cout << "pointer_x = " << pointer_x << endl;
//    cout << "dereferencing value *pointer_x = " << *pointer_x << endl;
//
////    Use call by reference to mutate the variable
//    cout << "x = " << x << ", y = " << y << endl;
//    swap(x, y);
//    cout << "x = " << x << ", y = " << y << endl;
    
//    return 0;
//}

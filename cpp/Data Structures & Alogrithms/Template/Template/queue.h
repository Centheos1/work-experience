#include <iostream>
#include <new>

using namespace std;

template <typename T>
class queue {
    
    private:
        //Add some internal data members here
        //What ones do you need?
        //An array like thing to store data...
        //How do we handle arrays where we don't know the size?
        //You'll also want to do something to keep track of where
        //in the queue you're up to (there's a couple of ways
        //to do this).
        
        T* ptr;
        int capacity;
        int num_items;
        int front;
    
    public:
    
        //constructors
        queue(){
            this->capacity = 10;
            ptr = new T[capacity];
            num_items = 0;
            front = 0;
        }
    
        queue(int capacity){
            this->ptr = new (nothrow) T[capacity];
            if (ptr == nullptr)
                cout << "Error: memory could not be allocated";
            else {
                this->capacity = capacity;
                this->num_items = 0;
                this->front = 0;
            }
        }
    
        ~queue(){
            delete ptr;
        }

        void push(T t){
            
//            Solution Code
            if (num_items >= capacity){
                //Move everything to a new array
                int newCapacity = int(capacity * 1.5);
                T * newData = new T[newCapacity];

                for (int i = 0 ; i < num_items; i++){
                    newData[i] = ptr[(front + i)%capacity];
                }

                delete[] ptr;
                capacity = newCapacity;
                ptr = newData;
                front = 0;
            }

            ptr[(front+num_items)%capacity] = t;
            num_items++;
            
//            My Code
//            cout << "push() num_items = " << num_items << " capacity = " << capacity << " data = " << t << endl;
//            if (num_items >= capacity) {
//                capacity = int(capacity * 1.5);
//                T* temp = new (nothrow) T[capacity];
//                if (temp == nullptr) {
//                    cout << "Error: memory could not be allocated";
//                    return;
//                } else {
////                    cout << "Capacity of " << capacity << " reached. increasing capacity to " << int(capacity * 1.5) << endl;;
//                    for (int i=0; i< num_items; i++) {
//                        temp[i] = ptr[i];
//                    }
//                    temp[num_items] = t;
//                    delete ptr;
//                    ptr = temp;
//                }
//
//            } else if (num_items == 0) {
////                cout << "Empty queue, adding value: " << t << endl;
//                ptr[0] = t;
//            } else {
////                cout << "Adding value: " << t << endl;
//                ptr[num_items] = t;
//            }
//            num_items++;
//            displayInfo();
        } //add something to the back of the queue
    
        T pop(){
            
//            Solution Code
            T ret = ptr[front];
            num_items--;
            front = (front + 1)%capacity;
            return ret;

//            My Code
//            T return_T = ptr[0];
//            for (int i=0; i<capacity; i++) {
//                ptr[i] = ptr[i+1];
//            }
//            num_items--;
//            return return_T;
            
        } //remove something from the front of the queue
    
        bool empty(){
            return num_items == 0;
        } //is the queue empty?
    
        void displayInfo(){
//            cout << "displayInfo()";
//            for (int i = 0; i<num_items; i++) {
//                cout << ' ' << ptr[i];
//            }
            cout << endl;
            
            cout << "capacity = " << this->capacity << endl;
            cout << "size = " << this->num_items << endl;
            cout << "front_position = " << this->front << endl;
            
        } // show attribute values of the queue
    
};

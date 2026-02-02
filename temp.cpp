#include <iostream>
#include <string>
using namespace std;

int main() {
	string s;
    cout << "Hello, World!" << endl;
    cout<<"Enter Your Name:";
    getline(cin,s);
    cout << "Welcome , "<<s<<" to C++ Programming." << endl;
    return 0;
}

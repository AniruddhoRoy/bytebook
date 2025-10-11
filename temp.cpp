#include <iostream>

using namespace std;
void h(){
	cout<<"99"<<endl;
}
int main() {
    string name ;
    cout << "Enter your name: "<<endl;
    cin>>name;
    cout << "Hello, " << name << "!" << endl;
	h();
    return 0;
}

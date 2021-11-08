
#include <iostream>
#include<map>
#include <iterator>
#include <array>
#include<vector>

using namespace std;
int getMaxLength(const map<int, int>& temp_map);
int* getLengthOfSequences(const vector<char> sequence);

int main()
{
    /*
    cout << "Enter the number of flips";
    int numberOfFlips;
    cin >> numberOfFlips;
    */

    // Test : IFFIIIFFIFIII
    cout << "Enter the result of the flips: ";
    string sequenceOfCoinFlips;
    cin >> sequenceOfCoinFlips;
    
 
    vector<char> coinFlipsVector;
    copy(sequenceOfCoinFlips.begin(), sequenceOfCoinFlips.end(), std::back_inserter(coinFlipsVector));
    int* lengths = getLengthOfSequences(coinFlipsVector);

    cout << lengths[0] << endl << lengths[1] << endl;
}

int* getLengthOfSequences(const vector<char> sequences) {
 
    // Store the length of the current subsequence
    int currentHeadLength = 0, currentTailLength = 0;

    // Key: length of the subsequence, VALUE: number of occurences in the sequence
    map<int, int> headSequences;
    map<int, int> tailSequences;
    

    for (int i = 0; i < sequences.size(); i++) {
       
        // Increment the length of the current HEAD subsequence and "close" the previous Tail subsequence.
        if ('f' == tolower(sequences[i])){

            currentHeadLength++;

            // If there is a tail subsequence put its length and quantity into the proper map.
            if (currentTailLength != 0){
                // if already exists update its quantity
                if (tailSequences.count(currentTailLength) != 0){
                    int quantity = tailSequences.at(currentTailLength);
                    tailSequences.at(currentTailLength) = quantity + 1;
                }
                else { // if not exists create new, quantitiy: 1
                    tailSequences.insert(pair<int, int>(currentTailLength, 1));
                }
                // reset the current length of the Tail subsequence to 0.
                currentTailLength = 0;
            }
        }

        // Increment the length of the current TAIL subsequence and "close" the previous Head subsequence.
        if ('i' == tolower(sequences[i])) {

            currentTailLength++;

            if (currentHeadLength != 0){
                if (headSequences.count(currentHeadLength) > 0){
                    int quantity = headSequences.at(currentHeadLength);
                    tailSequences.at(currentHeadLength) = quantity + 1;
                }
                else {
                    tailSequences.insert(pair<int, int>(currentHeadLength, 1));
                }
                currentHeadLength = 0;
            }
        }
        
    }
    int headMaxLength;
    headMaxLength = getMaxLength(headSequences);
    int tailMaxLength;
    tailMaxLength = getMaxLength(tailSequences);

    return new int[] {headMaxLength, tailMaxLength };
}

int getMaxLength(const map<int, int>& temp_map) {
    int maxQuantity = 0, maxLength = 0;

    map<int, int>::iterator it;
    for (const pair<const int, int>& entry : temp_map) {
        int length = entry.first;
        int quantity = entry.second;

        if (maxQuantity < quantity){
            maxQuantity = quantity;
            maxLength = length;
        }
        else if (maxQuantity == quantity){
            maxLength = (maxLength < length) ? length : maxLength;
        }
    }
    return maxLength;
}


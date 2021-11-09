
#include <iostream>
#include<map>
#include <iterator>
#include <array>
#include<vector>

using namespace std;

int getMaxLength(const map<int, int>& temp_map);
int* getLengthOfSequences(const vector<char> sequence);
void updateMap(map<int, int>& map, int currentLength);

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
        if ('F' == sequences[i]){
            currentHeadLength++;
            if (currentTailLength != 0){
                updateMap(tailSequences, currentTailLength);
                currentTailLength = 0;
            }
            if (currentHeadLength > 0 && i == sequences.size() - 1) {
                updateMap(headSequences, currentHeadLength);
                currentHeadLength = 0;
            }
        }

        // Increment the length of the current TAIL subsequence and "close" the previous Head subsequence.
        if ('I' == sequences[i]) {
            currentTailLength++;
            if (currentHeadLength != 0){
                updateMap(headSequences, currentHeadLength);
                currentHeadLength = 0;
            }
            if (currentTailLength > 0 && i == sequences.size() - 1){
                updateMap(tailSequences, currentTailLength);
                currentTailLength = 0;
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

void updateMap(map<int, int> &map, int currentLength) {
    if (map.count(currentLength) > 0) {
        int quantity = map.at(currentLength);
        map.at(currentLength) = quantity + 1;
    }
    else {
        map.insert({ currentLength, 1 });
    }
}
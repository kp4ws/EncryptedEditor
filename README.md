# EncryptedEditor

## About ##
A simple text editor that allows the user to encrypt their messages. If you do not have encrypted mode enabled, the program will not encrypt your messages when you save them as a file. If encrypted mode is enabled, the program will encrypt the message using a custom built encryption algorithm.

## Backstory ##
I originally started this project in my 2nd semester of my Software Development program at college. After graduation I came back to this project and refactored my code to make it more efficient and remove unwanted spaghetti code.

## Project Goal ##
Improving my GUI skills with Java and having a fun time doing so.

## Encryption Algorithm ##
- A constant array of chars called VALID_CHARACTERS is declared and initialized. The randomString variable contains values from this array.
- The user enters a key as an int data type which they use to encrypt/decrypt messages.
- The program will continually loop until the entire message has been encoded.
- With each iteration of the loop, a pseudorandom int is generated and set as the randNum variable and the randomString variable is reset to a blank String.
- From here, an inner for loop iterates through the VALID_CHARACTERS array length.
- Within the for loop, if the randNum variable is divisble by the key that the user entered, and the position is at the randNum's value, then a character from the message is encoded into the randomString. Otherwise, a completely randomString will be generated.
- Lastly, the randomString is appended to the randNum which is then appended to the cipherText. The results are shown in the second screenshot below.

---

#### Application Window #### 
<img src="https://user-images.githubusercontent.com/58745400/117493602-6e5fdf80-af30-11eb-897f-c30e9b3edfc5.png" alt="Plain Text" />

#### Encrypted Text ####
<img src="https://user-images.githubusercontent.com/58745400/117494935-3eb1d700-af32-11eb-8469-961655797da7.png" alt="Encrypted Text" />

---

## Release Notes ##

#### v1.0 - 2020-05-19 ####
- Original release of the project

#### v1.1 - 2020-05-7 ####
- Refactored code
- Improved application functionality

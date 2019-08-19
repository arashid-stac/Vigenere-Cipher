# Vigenere-Cipher
As defined by wikipedia, a "Vigenere cipher is a method of encrypting alphabetic text by using a series of interwoven Caesar ciphers, based on the letters of a keyword".

Example:
Let's say you'd like to encode the word *Hello* using the keyword *hi*. First the keyword would have to be looped so that its length is the same as the desired word to encode.

*hi* -> *hihih*

The first character in the desired word(*Hello*) is *h* and the first character in the newly made keyword is *h*. Therefore to find the first letter in your encoded phrase you would have to look for the letter in the table below that resides in the row *h* and column *h*.

Next you'd have to look for the letter in the table that resides in the row *e* and column *i*. 

The result would be: *omstv*

# Details
A program that uses a vigenere cipher to encode a message.
The vigenere table is created by using a 2 Dimensional Array whereas the message-to-be-encoded and the keyword are created as regular char arrays.

# To-do list
1) Error control
- User is currently able to input symbols and numbers, but they shouldn't. For now they are ignored and treated as spaces.

# Idea
1) Add a method to decode a message

# Vigenere Table
![Alt Text](https://upload.wikimedia.org/wikipedia/commons/9/9a/Vigen%C3%A8re_square_shading.svg)

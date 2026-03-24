#pragma once

#include <iostream>
#include <string>
#include <vector>

using namespace std;

bool CheckStream();

inline string EncryptPassword(string password)
{
	vector<char> word(password.begin(), password.end());
	string oo = "ÿşø÷öõôóòñğïîíìëêèçæåäãâáàßŞØ×Ö×ÔÓÅÑĞÏÎÍÌËÊÈÇÆÅÄÃÂÁÀ!@#$?^&*()";
	string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	for (int i = 0; i < (int)password.length(); i++)
		for (int j = 0; j < (int)symbols.length(); j++)
			if (word[i] == symbols[j])
			{
				word[i] = oo[j];
				break;
			}

	string encrypted(word.begin(), word.end());
	return encrypted;
}

inline string DecryptPassword(string password)
{
	vector<char> word(password.begin(), password.end());
	string oo = "ÿşø÷öõôóòñğïîíìëêèçæåäãâáàßŞØ×Ö×ÔÓÅÑĞÏÎÍÌËÊÈÇÆÅÄÃÂÁÀ!@#$?^&*()";
	string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	for (int i = 0; i < (int)password.length(); i++)
		for (int j = 0; j < (int)oo.length(); j++)
			if (word[i] == oo[j])
			{
				word[i] = symbols[j];
				break;
			}

	string encrypted(word.begin(), word.end());
	return encrypted;
}
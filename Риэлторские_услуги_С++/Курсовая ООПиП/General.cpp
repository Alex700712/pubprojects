#include "General.h"

bool CheckStream()
{
	if (cin.fail())
	{
		cout << "Ошибка ввода. Попробуйте ещё раз" << endl;
		cin.clear();
		cin.ignore(256, '\n');
		return false;
	}
	else return true;
}
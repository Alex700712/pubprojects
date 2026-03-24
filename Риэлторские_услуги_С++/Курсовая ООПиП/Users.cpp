#include "Users.h"

void ReadAccounts(vector<User>& users, vector<Admin>& admins, Main_Admin& main_admin)
{
	ifstream fin("Users.txt", ios::binary);
	if (!fin.is_open())
	{
		cout << "Файл Users.txt не может быть открыт" << endl;
	}
	else
	{
		User temp;
		while (fin >> temp)
			users.push_back(temp);
	}
	fin.close();

	fin.open("Admins.txt", ios::in);
	if (!fin.is_open())
	{
		cout << "Файл Admins.txt не может быть открыт" << endl;
	}
	else
	{
		Admin temp;
		while (fin >> temp)
		{
			string temp_login = temp.getLogin();
			if (temp_login[(int)temp_login.size() - 1] == '%')
			{
				main_admin.setLogin(temp.getLogin().erase((int)temp.getLogin().size() - 1));
				main_admin.setPassword(temp.getPassword());
			}
			else
				admins.push_back(temp);
		}
	}
	fin.close();
}

void WriteUsers(vector<User>& users, vector<Admin>& admins, Main_Admin main_admin)
{
	ofstream fout;
	string temp_pass;
	fout.open("Users.txt", ios::trunc);
	fout.close();
	fout.open("Admins.txt", ios::trunc);
	fout.close();



	fout.open("Users.txt", ios::out);
	if (!fout.is_open())
	{
		cout << "Файл Users.txt не может быть открыт" << endl;
	}
	else
	{
		for (int i = 0; i < (int)users.size(); i++)
		{
			temp_pass = users[i].getPassword();
			users[i].setPassword(EncryptPassword(temp_pass));
			fout << users[i];
		}
	}
	fout.close();

	fout.open("Admins.txt", ios::out);
	if (!fout.is_open())
	{
		cout << "Файл Admins.txt не может быть открыт" << endl;
	}
	else
	{
		int main_out = -1;
		if ((int)admins.size() != 0)
		{
			main_out = rand() % (int)admins.size();
		}
		else
		{
			main_admin.setLogin(main_admin.getLogin() + '%');
			temp_pass = main_admin.getPassword();
			main_admin.setPassword(EncryptPassword(temp_pass));
			fout << main_admin;
		}

		for (int i = 0; i < (int)admins.size(); i++)
		{
			temp_pass = admins[i].getPassword();
			admins[i].setPassword(EncryptPassword(temp_pass));
			fout << admins[i];
			if (i == main_out)
			{
				main_admin.setLogin(main_admin.getLogin() + '%');
				temp_pass = main_admin.getPassword();
				main_admin.setPassword(EncryptPassword(temp_pass));
				fout << main_admin;
			}
		}
	}
	fout.close();
}

void ChangeRole(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins)
{
	int change;
	do
	{
		cout << "1) Повысить пользователя (User -> Admin)\n2) Понизить пользователя (Admin -> User)\n0) Назад" << endl;
		do
		{
			cin >> change;
		} while (!CheckStream());
		if (change == 1)
		{
			int increase, size = (int)users.size();
			do
			{
				cout << "Введите номер изменяемого пользователя" << endl;
				do
				{
					cin >> increase;
				} while (!CheckStream());
				if (increase < 0 || increase > size)
					cout << "Такого пункта меню нет. Выберите другой" << endl;
				else
				{
					Admin new_admin;
					main_admin.changeRole(users[--increase], new_admin);
					users.erase(users.begin() + increase);
					admins.push_back(new_admin);
					cout << "Роль пользователя " << new_admin.getLogin() << "повышена до 'Admin" << endl;
					increase++;
				}
			} while (increase < 0 || increase > size);
		}
		else if (change == 2)
		{
			int demote, size = (int)admins.size();
			do
			{
				cout << "Введите номер изменяемого пользователя" << endl;
				do
				{
					cin >> demote;
				} while (!CheckStream());
				if (demote < 0 || demote > size)
					cout << "Такого пункта меню нет. Выберите другой" << endl;
				else
				{
					User new_user;
					main_admin.changeRole(admins[--demote], new_user);
					admins.erase(admins.begin() + demote);
					users.push_back(new_user);
					cout << "Роль пользователя " << new_user.getLogin() << "понижена до 'User'" << endl;
					demote++;
				}
			} while (demote < 0 || demote > size);
		}
	} while (change < 0 || change > 2);
}
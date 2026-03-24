#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>
#include <vector>
#include "General.h"

using namespace std;

namespace Accounts
{

	class Account
	{
	protected:
		string login;
		string password;
		bool for_delete = false;
		bool is_blocked = false;
	public:
		Account() : login("-"), password("-") {}
		virtual void setLogin(string login) = 0;
		virtual void setPassword(string password) = 0;
		virtual string getLogin() = 0;
		virtual string getPassword() = 0;
		virtual void deleteAccount() = 0;
		virtual bool getDeleteStatus() = 0;
		virtual void accountInfo() = 0;
	};

}

using namespace Accounts;

class User : public Account
{
	shared_ptr<User> ptr;
public:
	void createPtr() { this->ptr = shared_ptr<User>(new User); }
	void setLogin(string login) override { this->login = login; }
	void setPassword(string password) override { this->password = password; }
	void deleteAccount() override { this->for_delete = true; }
	string getLogin() override { return this->login; };
	string getPassword() override { return this->password; }
	bool getDeleteStatus() override { return this->for_delete; }
	shared_ptr<User> getPrt() { return this->ptr; }
	void accountInfo() override { cout << "|" << setw(20) << this->login << "|" << endl; }
	void blockAccount() { this->is_blocked = true; }
	void unblockAccount() { this->is_blocked = false; }
	bool getBlockStatus() { return this->is_blocked; }
	friend ofstream& operator<<(ofstream& fout, User& user)
	{
		fout << user.getLogin() << " " << user.getPassword() << endl;
		return fout;
	}
	friend ifstream& operator>>(ifstream& fin, User& user)
	{
		string temp_str;
		fin >> temp_str;
		user.setLogin(temp_str);
		fin >> temp_str;
		user.setPassword(temp_str);
		return fin;
	}
	friend bool operator==(User account1, User account2)
	{
		if (account1.login != account2.login)
			return false;
		else return true;
	}
	friend bool operator>(User account1, User account2)
	{
		if (account1.login > account2.login)
			return true;
		else return false;
	}
	friend bool operator<(User account1, User account2)
	{
		if (account1.login < account2.login)
			return true;
		else return false;
	}
};

class Admin : public Account
{
	shared_ptr<Admin> ptr;
public:
	void createPtr() { this->ptr = shared_ptr<Admin>(new Admin); }
	void setLogin(string login) override { this->login = login; }
	void setPassword(string password) override { this->password = password; }
	void deleteAccount() override { this->for_delete = true; }
	string getLogin() override { return this->login; };
	string getPassword() override { return this->password; }
	bool getDeleteStatus() { return this->for_delete; }
	shared_ptr<Admin> getPrt() { return this->ptr; }
	void accountInfo() override { cout << "|" << setw(20) << this->login << "|" << endl; }
	friend ofstream& operator<<(ofstream& fout, Admin& admin)
	{
		fout << admin.getLogin() << " " << admin.getPassword() << endl;
		return fout;
	}
	friend ifstream& operator>>(ifstream& fin, Admin& admin)
	{
		string temp_str;
		fin >> temp_str;
		admin.setLogin(temp_str);
		fin >> temp_str;
		admin.setPassword(temp_str);
		return fin;
	}
	friend bool operator==(Admin account1, Admin account2)
	{
		if (account1.login != account2.login)
			return false;
		else return true;
	}
	void blockAccount() { this->is_blocked = true; }
	void unblockAccount() { this->is_blocked = false; }
	bool getBlockStatus() { return this->is_blocked; }
	friend bool operator>(Admin account1, Admin account2)
	{
		if (account1.login > account2.login)
			return true;
		else return false;
	}
	friend bool operator<(Admin account1, Admin account2)
	{
		if (account1.login < account2.login)
			return true;
		else return false;
	}
};

class Main_Admin : public Account
{
public:
	void setLogin(string login) override { this->login = login; }
	void setPassword(string password) override { this->password = password; }
	void deleteAccount() override { this->for_delete = true; }
	string getLogin() override { return this->login; };
	string getPassword() override { return this->password; }
	bool getDeleteStatus() override { return this->for_delete; }
	void accountInfo() override { cout << "|" << setw(20) << this->login << "|" << endl; }
	template<class T, class U>
	void changeRole(T& account1, U& account2)
	{
		account2.setLogin(account1.getLogin());
		account2.setPassword(account1.getPassword());
	}
	friend ofstream& operator<<(ofstream& fout, Main_Admin& main_admin)
	{
		fout << main_admin.getLogin() << " " << main_admin.getPassword() << endl;
		return fout;
	}
	friend ifstream& operator>>(ifstream& fin, Main_Admin& main_admin)
	{
		string temp_str;
		fin >> temp_str;
		main_admin.setLogin(temp_str);
		fin >> temp_str;
		main_admin.setPassword(temp_str);
		return fin;
	}
	friend bool operator==(Main_Admin account1, Main_Admin account2)
	{
		if (account1.login != account2.login)
			return false;
		else return true;
	}
};

template<class T>
int findAccountByLogin(vector<T>& accounts, string login)
{
	for (int i = 0; i < (int)accounts.size(); i++)
	{
		if (accounts[i].getLogin() == login)
			return i;
	}

	return -1;
}

void ReadAccounts(vector<User>& users, vector<Admin>& admins, Main_Admin& main_admin);

void WriteUsers(vector<User>& users, vector<Admin>& admins, Main_Admin main_admin);

template<class T>
void editAccount(T& account)
{
	int select;
	do
	{
		cout << "Выберите редактируемый параметр\n1) Логин\n2) Пароль\n0) Выйти из меню редактирования" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 1)
		{
			string temp;
			cout << "Введите новый логин" << endl;
			cin.get();
			do
			{
				getline(cin, temp);
			} while (!CheckStream());
			account.setLogin(temp);
		}
		else if (select == 2)
		{
			string temp;
			do
			{
				cout << "Введите старый пароль" << endl;
				cin.get();
				do
				{
					getline(cin, temp);
				} while (!CheckStream());

				if (temp != DecryptPassword(account.getPassword()))
				{
					int bad_pass;
					do
					{
						cout << "Пароль не верный\n1) Повторить попытку\n2) Отменить редактировние" << endl;
						do
						{
							cin >> bad_pass;
						} while (!CheckStream());
						if (bad_pass == 2)
							return;
						else if (bad_pass != 1)
							cout << "Такого пункта меню нет. Выберите другой" << endl;
					} while (bad_pass != 1 && bad_pass != 2);
					cin.get();
				}
			} while (temp != DecryptPassword(account.getPassword()));

			cout << "Введите новый пароль" << endl;
			do
			{
				getline(cin, temp);
			} while (!CheckStream());
			temp = EncryptPassword(temp);
			account.setPassword(temp);
		}
		else if (select == 0)
			return;
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select < 0 || select > 2);
}

void ChangeRole(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins);
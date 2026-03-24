#pragma once
#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <memory>
#include <typeinfo>
#include <iomanip>
#include <conio.h>
#include "General.h"
#include "Users.h"
#include "Properties.h"

using namespace std;

template <class T>
bool AccountMenu(T& account)
{
	int select;
	string temp = typeid(account).name();
	string view = temp.erase(0, 6);
	do
	{
		cout << "1) Данные об аккаунте\n2) Редактировать аккаунт\n3) Удалить аккаунт\n4) Выйти из аккаунта\n0) Назад" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		switch (select)
		{
		case 1:
			cout << "МОЙ АККАУНТ" << endl;
			cout << "\t|==========|====================|" << endl;
			cout << "\t|" << setw(10) << "Роль" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|==========|====================|" << endl;
			cout << "\t|" << setw(10) << view;
			account.accountInfo();
			cout << "\t|==========|====================|" << endl;
			break;
		case 2:
			editAccount(account);
			break;
		case 3:
			if (temp == "class Main_Admin")
			{
				cout << "Невозможно удалить аккаунт главного администратора" << endl;
			}
			else
			{
				int del;
				do
				{
					cout << "Вы уверены, что хотите удалить аккаунт?\n1) Подтвердить удаление\n2) Отменить удаление" << endl;
					do
					{
						cin >> del;
					} while (!CheckStream());
					if (del == 1)
					{
						string temp = typeid(account).name();
						if (temp != "class Main_Admin")
						{
							account.deleteAccount();
							return true;
						}
						else
							cout << "Удаление главного админа невозможно" << endl;
					}
					else if (del != 2)
						cout << "Такого пункта меню нет. Выберите другой" << endl;
				} while (del != 2);
			}
			break;
		case 4:
			return true;
			break;
		case 0:
			break;
		default:
			cout << "Такого пункта меню нет. Выберите другой" << endl;
		}
	} while (select != 0);

	return false;
}

void UserPropertyMenu(User user);

bool UserMenu(User& user);

void AdminUsersMenu(vector<User>& users, vector<Admin>& admins);

void AdminPropertyMenu(Admin admin);

bool AdminMenu(Admin& admin, vector<User>& users, vector<Admin>& admins);

void MainAdminPropertyMenu();

void MainAdminUsersMenu(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins);

bool MainAdminMenu(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins);
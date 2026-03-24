#include "Start.h"

void Registration(vector<User>& users, vector<Admin>& admins, Main_Admin main_admin)
{
	string temp_str;
	User temp;
	bool new_login = true;
	cout << "РЕГИСТРАЦИЯ" << endl << "Логин: ";
	do
	{
		new_login = true;
		do
		{
			getline(cin, temp_str);
		} while (!CheckStream());

		string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < (int)temp_str.length(); i++)
			for (int j = 0; j < (int)symbols.length(); j++)
			{
				if (temp_str[i] == symbols[j])
				{
					break;
				}
				else if (j == (int)symbols.length() - 1 && temp_str[i] != symbols[(int)symbols.length() - 1])
					throw(i);
			}

		for (int i = 0; i < (int)users.size(); i++)
			if (users[i].getLogin() == temp_str)
			{
				cout << "Пользователь с таким логином уже существует. Выберите другой" << endl;
				new_login = false;
				break;
			}

		if (new_login)
		{
			for (int i = 0; i < (int)admins.size(); i++)
				if (admins[i].getLogin() == temp_str)
				{
					cout << "Пользователь с таким логином уже существует. Выберите другой" << endl;
					new_login = false;
					break;
				}
		}

		if (new_login)
			if (main_admin.getLogin() == temp_str)
			{
				cout << "Пользователь с таким логином уже существует. Выберите другой" << endl;
				new_login = false;
			}

	} while (!new_login);
	temp.setLogin(temp_str);

	cout << "Пароль: ";
	do
	{
		getline(cin, temp_str);
	} while (!CheckStream());

	string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	for (int i = 0; i < (int)temp_str.length(); i++)
		for (int j = 0; j < (int)symbols.length(); j++)
		{
			if (temp_str[i] == symbols[j])
			{
				break;
			}
			else if (j == (int)symbols.length() - 1 && temp_str[i] != symbols[(int)symbols.length() - 1])
				throw(i);
		}

	temp.setPassword(temp_str);
	users.push_back(temp);
}

bool Autorization(vector<User>& users, vector<Admin>& admins, Main_Admin& main_admin)
{
	string temp_str, role, password;
	int index = -1;
	bool exit = false;

	cin.get();

	do
	{
		cout << "АВТОРИЗАЦИЯ" << endl << "Введите логин: ";
		do
		{
			getline(cin, temp_str);
		} while (!CheckStream());

		string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < (int)temp_str.length(); i++)
			for (int j = 0; j < (int)symbols.length(); j++)
			{
				if (temp_str[i] == symbols[j])
				{
					break;
				}
				else if (j == (int)symbols.length() - 1 && temp_str[i] != symbols[(int)symbols.length() - 1])
					throw(i);
			}

		index = findAccountByLogin(users, temp_str);
		if (index == -1)
		{
			index = findAccountByLogin(admins, temp_str);
			if (index == -1)
			{
				if (main_admin.getLogin() == temp_str)
				{
					index = 0;
					role = "Main_Admin";
				}
			}
			else
				role = "Admin";
		}
		else
			role = "User";


		if (index == -1)
		{
			int select = 0;
			do
			{
				cout << "Ошибка. Пользователя с таким логином не существует. Хотите создать аккаунт?\n1) Да\n2) Нет" << endl;
				do
				{
					cin >> select;
				} while (!CheckStream());
				cin.get();
				if (select > 2 || select < 1)
					cout << "Ошибка. Такого пункта меню нет. Повторите попытку" << endl;
				else if (select == 1)
				{
					system("cls");
					Registration(users, admins, main_admin);
					return false;
				}
				else if (select == 2)
				{
					system("cls");
					break;
				}
			} while (select > 2 || select < 1);
		}
	} while (index == -1);

	temp_str.clear();

	if (role == "User")
	{
		password = DecryptPassword(users[index].getPassword());
		do
		{
			cout << "Введите пароль: ";
			while (true) {
				char c = _getch();
				if (c == '\r') {
					break;
				}
				else if (c == '\b')
				{
					if (temp_str.size() > 0)
					{
						temp_str.erase(temp_str.size() - 1, temp_str.size());
						cout << "\b \b";
					}
				}
				else
				{
					temp_str += c;
					cout << "*";
				}
			}

			cout << endl;

			string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			for (int i = 0; i < (int)temp_str.length(); i++)
				for (int j = 0; j < (int)symbols.length(); j++)
				{
					if (temp_str[i] == symbols[j])
					{
						break;
					}
					else if (j == (int)symbols.length() - 1 && temp_str[i] != symbols[(int)symbols.length() - 1])
						throw(i);
				}

			if (password != temp_str)
			{
				cout << "Неверный пароль. Повторите попытку" << endl;
				temp_str.clear();
			}
		} while (password != temp_str);

		password.clear();

		if (users[index].getBlockStatus() == true)
			cout << "Данный аккаунт заблокирован. Доступ запрещён" << endl;
		else
		{
			system("cls");
			exit = UserMenu(users[index]);
		}
	}

	else if (role == "Admin")
	{
		password = DecryptPassword(admins[index].getPassword());
		do
		{
			cout << "Введите пароль: ";
			while (true) {
				char c = _getch();
				if (c == '\r') {
					break;
				}
				else if (c == '\b')
				{
					if (temp_str.size() > 0)
					{
						temp_str.erase(temp_str.size() - 1, temp_str.size());
						cout << "\b \b";
					}
				}
				else
				{
					temp_str += c;
					cout << "*";
				}
			}

			cout << endl;

			string symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			for (int i = 0; i < (int)temp_str.length(); i++)
				for (int j = 0; j < (int)symbols.length(); j++)
				{
					if (temp_str[i] == symbols[j])
					{
						break;
					}
					else if (j == (int)symbols.length() - 1 && temp_str[i] != symbols[(int)symbols.length() - 1])
						throw(i);
				}

			if (password != temp_str)
			{
				cout << "Неверный пароль. Повторите попытку" << endl;
				temp_str.clear();
			}
		} while (password != temp_str);

		password.clear();

		if (admins[index].getBlockStatus() == true)
			cout << "Данный аккаунт заблокирован. Доступ запрещён" << endl;
		else
		{
			system("cls");
			exit = AdminMenu(admins[index], users, admins);
		}
	}

	else if (role == "Main_Admin")
	{
		password = DecryptPassword(main_admin.getPassword());
		do
		{
			cout << "Введите пароль: ";
			while (true) {
				char c = _getch();
				if (c == '\r') {
					break;
				}
				else if (c == '\b')
				{
					if (temp_str.size() > 0)
					{
						temp_str.erase(temp_str.size() - 1, temp_str.size());
						cout << "\b \b";
					}
				}
				else
				{
					temp_str += c;
					cout << "*";
				}
			}

			cout << endl;

			if (password != temp_str)
			{
				cout << "Неверный пароль. Повторите попытку" << endl;
				temp_str.clear();
			}
		} while (password != temp_str);
		password.clear();

		system("cls");
		exit = MainAdminMenu(main_admin, users, admins);
	}

	if (exit == true)
		return true;
	else
		return false;
}

void StartMenu()
{
	vector<Admin> admins;
	vector<User> users;
	Main_Admin main_admin, clear_main_admin;
	int select = -1;
	bool exit = false;

	ReadAccounts(users, admins, main_admin);

	if ((int)users.size() == 0 && (int)admins.size() == 0 && main_admin == clear_main_admin)
	{
		cout << "Добро пожаловть в систему. Создайте первый главный аккаунт" << endl;
		string temp_str;
		bool new_login = true;
		cout << "РЕГИСТРАЦИЯ" << endl << "Логин: ";
		do
		{
			getline(cin, temp_str);
		} while (!CheckStream());
		main_admin.setLogin(temp_str);

		cout << "Пароль: ";
		do
		{
			getline(cin, temp_str);
		} while (!CheckStream());
		main_admin.setPassword(temp_str);
		WriteUsers(users, admins, main_admin);
	}

	do
	{
		try
		{
			cout << "ГЛАВНОЕ МЕНЮ" << endl << "1) Войти в аккаунт\n2) Зарегистрироваться\nВведите 0 для выхода из программы" << endl;
			do
			{
				cin >> select;
			} while (!CheckStream());
			if (select == 0)
				return;
			else if (select == 1)
			{
				system("cls");
				exit = Autorization(users, admins, main_admin);
				WriteUsers(users, admins, main_admin);
				if (exit == true)
					return;

			}
			else if (select == 2)
			{
				system("cls");
				cin.get();
				Registration(users, admins, main_admin);
				WriteUsers(users, admins, main_admin);
			}
			else
				cout << "Ошибка. Такого пункта меню нет. Повторите попытку" << endl;

			for (int i = 0; i < (int)users.size(); i++)
				if (users[i].getDeleteStatus() == true)
					users.erase(users.begin() + i);

			for (int i = 0; i < (int)admins.size(); i++)
				if (admins[i].getDeleteStatus() == true)
					admins.erase(admins.begin() + i);
			system("cls");
		}
		catch (int)
		{
			cout << "При вводе данных были введены недоступные символы. Вы перемещены в главное меню.\nВсе действия в предыдущем меню отменены." << endl;
		}
	} while (select != 0);
}
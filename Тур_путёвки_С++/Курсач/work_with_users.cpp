#include "work_with_users.h"
#include "general.h"
using namespace std;

void FromFileToVector(vector<Users>& vector_of_users, int& count_of_users)
{
	ifstream fin("Users.txt", ios::in);
	if (!fin.is_open())
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mОшибка открытия файла Users.txt\x1b[0m" << endl;
	}
	else
	{
		Users temp;
		while (fin >> temp.login >> temp.password >> temp.role)
		{
			vector_of_users.push_back(temp);
			count_of_users++;
		}
	}
	fin.close();
}

void FromVectortoFile(vector<Users> vector_of_users, int count_of_users)
{
	ofstream fout("Users.txt", ios::out);
	for (int i = 0; i < count_of_users; i++)
	{
		fout << vector_of_users.at(i).login << " ";
		fout << vector_of_users.at(i).password << " ";
		fout << vector_of_users.at(i).role << endl;
	}
}

void AllUsers(vector<Users> vector_of_users, int count_of_users)
{
	cout << "|---|--------------------|--------------------|--------------------|" << endl;
	cout << "|" << setw(4) << "№|" << setw(21) << "Логин|" << setw(21) << "Пароль|" << setw(21) << "Роль|" << endl;
	cout << "|---|--------------------|--------------------|--------------------|" << endl;
	for (int i = 0; i < count_of_users; i++)
	{
		cout << "|" << setw(3) << i + 1;
		cout << "|" << setw(20) << vector_of_users[i].login;
		cout << "|" << setw(20) << vector_of_users[i].password;
		cout << "|" << setw(20) << vector_of_users[i].role << "|" << endl;
	}
	cout << "|---|--------------------|--------------------|--------------------|" << endl;
}

void AccountsForDelete(vector<Users> vector_of_users, int count_of_users)
{
	cout << "|----|--------------------|--------------------|--------------------|" << endl;
	cout << "|" << setw(5) << "№|" << setw(21) << "Логин|" << setw(21) << "Пароль|" << setw(21) << "Роль|" << endl;
	cout << "|----|--------------------|--------------------|--------------------|" << endl;
	for (int i = 0; i < count_of_users; i++)
	{
		if (vector_of_users[i].for_delete == true)
		{
			cout << "|" << setw(4) << i + 1;
			cout << "|" << setw(20) << vector_of_users[i].login;
			cout << "|" << setw(20) << vector_of_users[i].password;
			cout << "|" << setw(20) << vector_of_users[i].role << "|" << endl;
		}
	}
	cout << "|----|--------------------|--------------------|--------------------|" << endl;
}

void DeleteUser(vector<Users>& vector_of_users, int& count_of_users, string login)
{
	int deleted_user;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Введите номер удаляемого пользователя (от 1 до " << count_of_users << ")" << endl;
		deleted_user = ErrorImputInt();
		deleted_user--;
		if (deleted_user > count_of_users || deleted_user < 0)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mПользователя под этим номером не существует. Выберите другого\x1b[0m" << endl;
			cout << "-------------------------------------------" << endl;
		}
		else if (login == vector_of_users[deleted_user].login)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mОшибка. Нельзя удалить самого себя\x1b[0m" << endl;
			cout << "-------------------------------------------" << endl;
			return;
		}
	} while (deleted_user > count_of_users || deleted_user < 0);
	ClearAvailability(vector_of_users, count_of_users, deleted_user);
	vector_of_users.erase(vector_of_users.begin() + deleted_user);
	count_of_users--;
	cout << "-------------------------------------------" << endl;
	cout << "\x1b[32mПользователь успешно удалён\x1b[0m" << endl;
	fstream file("Users.txt", ios::trunc);
	file.close();
	FromVectortoFile(vector_of_users, count_of_users);
}

void ChangePassword(vector<Users>& vector_of_users, int& count_of_users, string login)
{
	bool change = false;
	string password;
	for (int i = 0; i < count_of_users; i++)
		if (vector_of_users[i].login == login)
		{
			change = true;
			cin.ignore(256, '\n');
			do
			{
				cout << "__________________________________________________________________" << endl;
				cout << "Введите новый пароль" << endl;
				password = ErrorImputString();
				if (password.length() < 4)
				{
					cout << "\x1b[31mПароль слишком короткий. Введите другой\x1b[0m" << endl;
					change = false;
				}
				else change = true;
			} while (change == false);
			vector_of_users.at(i).password = password;
			cout << "__________________________________________________________________" << endl;
			cout << "\x1b[32mПароль успешно изменён\x1b[0m" << endl;
			break;
		}
	FromVectortoFile(vector_of_users, count_of_users);
}

void ChangeRole(vector<Users>& vector_of_users, int count_of_users, string login)
{
	int change_user, change_role;
	cout << "__________________________________________________________________" << endl;
	cout << "Укажите пользователя для изменения роли (от 1 до " << count_of_users << ")" << endl;
	change_user = ErrorImputInt();
	change_user--;
	if (change_user < 0 || change_user > count_of_users - 1)
	{
		cout << "__________________________________________________________________" << endl;
		cout << "\x1b[31mОшибка. Пользователя под таким номером не существует\x1b[0m" << endl;
	}
	else if (login == vector_of_users[change_user].login)
	{
		cout << "__________________________________________________________________" << endl;
		cout << "\x1b[31mОшибка. Невозможно поменять роль самому себе\x1b[0m" << endl;
		return;
	}
	else
	{
		Users temp;
		cout << "__________________________________________________________________" << endl;
		cout << "Какую роль назначить данному пользователю? " << endl << "1) Admin" << endl << "2) User" << endl;
		change_role = ErrorImputInt();
		if (change_role == 1)
		{
			vector_of_users.at(change_user).role = "Admin";
			cout << "__________________________________________________________________" << endl;
			cout << "\x1b[32mДанные успешно изменены\x1b[0m" << endl;

		}
		else if (change_role == 2)
		{
			vector_of_users.at(change_user).role = "User";
			cout << "__________________________________________________________________" << endl;
			cout << "\x1b[32mДанные успешно изменены\x1b[0m" << endl;
		}
		else
		{
			cout << "__________________________________________________________________" << endl;
			cout << "\x1b[31mОшибка. Неверный пункт выбора. Роль не изменена\x1b[0m" << endl;
		}

		fstream file("Users.txt", ios::trunc);
		file.close();
		FromVectortoFile(vector_of_users, count_of_users);
	}
}

void WorkWithUsers(vector<Users>& vector_of_users, int& count_of_users, string login)
{
	int admin_menu;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Меню работы с пользователями\n\n" << "1) Список всех пользователей" << endl << "2) Сменить роль пользователю" << endl << "3) Удалить пользователя" << endl
			<< "4) Список аккаунтов на удаление" << endl << "5) Вернуться в главное меню" << endl;
		cout << "__________________________________________________________________" << endl;
		admin_menu = ErrorImputInt();
		switch (admin_menu)
		{
		case 1: AllUsers(vector_of_users, count_of_users); break;
		case 2: ChangeRole(vector_of_users, count_of_users, login); break;
		case 3: DeleteUser(vector_of_users, count_of_users, login); break;
		case 4: AccountsForDelete(vector_of_users, count_of_users); break;
		case 5: break;
		default: cout << "\x1b[31mНеверный пункт меню. Выберите другой.\x1b[0m" << endl;
		}
	} while (admin_menu != 5);
}

bool User_display(string login, bool& for_delete)
{
	int user_menu, count_of_tours = 0;
	vector<Tour> vector_of_tours;
	FromFileToVector(vector_of_tours, count_of_tours);
	cout << "__________________________________________________________________" << endl;
	cout << "Вы вошли как пользователь" << endl;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Главное меню\n\n" << "1) Список всех (имеющихся) туров" << endl << "2) Список туров по типу" << endl << "3) Список занятых туров" << endl << "4) Найти тур"
			<< endl << "5) Забронировать тур" << endl << "6) Отсотрировать туры" << endl << "7) Отправить запрос на удаление аккаунта" << endl <<
			"8) Данные об аккаунте" << endl << "9) Выйти из аккаунта" << endl << "10) Выйти из программы" << endl;
		cout << "__________________________________________________________________" << endl;
		user_menu = ErrorImputInt();
		switch (user_menu)
		{
		case 1: AllTours(vector_of_tours, count_of_tours); break;
		case 2: ToursByType(vector_of_tours, count_of_tours); break;
		case 3: BookedTours(vector_of_tours, count_of_tours); break;
		case 4: Search(vector_of_tours, count_of_tours); break;
		case 5: BookATour(vector_of_tours, count_of_tours, login); break;
		case 6: Sort(vector_of_tours, count_of_tours); break;
		case 7: DeleteAccount(for_delete);
			if (for_delete == true)
				return false;
			break;
		case 8: AccountInfo(login); break;
		case 9: return false; break;
		case 10: break;
		default: cout << "\x1b[31mНеверный пункт меню. Выберите другой.\x1b[0m" << endl;
		}
	} while (user_menu < 9 || user_menu > 10);
	return true;
}

bool Admin_display(vector<Users>& vector_of_users, int& count_of_users, string login)
{
	int admin_menu;
	cout << "__________________________________________________________________" << endl;
	cout << "Вы вошли как администартор" << endl;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Главное меню\n\n" << "1) Работа с пользователями" << endl << "2) Работа с турами и компаниями" << endl <<
			"3) Данные об аккаунте" << endl << "4) Выйти из аккаунта" << endl << "5) Выйти из программы" << endl;
		cout << "__________________________________________________________________" << endl;
		admin_menu = ErrorImputInt();
		switch (admin_menu)
		{
		case 1: WorkWithUsers(vector_of_users, count_of_users, login); break;
		case 2: WorkWithToursAndCompanies(); break;
		case 3: AccountInfo(login); break;
		case 4: return false; break;
		case 5: return true; break;
		default: cout << "\x1b[31mНеверный пункт меню. Выберите другой.\x1b[0m" << endl;
		}
	} while (admin_menu < 4 || admin_menu > 5);
	return true;
}

void registration(vector<Users>& vector_of_users, int& count_of_users, bool from_autorization)
{
	system("cls");
	ofstream fout("Users.txt", ios::app);
	Users new_user;
	bool create = true;
	cout << "---------------------------------------------------------------------------------------------------------------------" << endl;
	cout << "\t\t\t\t\t\t\tРЕГИСТРАЦИЯ" << endl;
	if (from_autorization == false) cin.ignore(256, '\n');
	do
	{
		cout << "Введите логин" << endl;
		new_user.login = ErrorImputString();
		for (int i = 0; i < vector_of_users.size(); i++)
		{
			if (new_user.login == vector_of_users[i].login)
			{
				cout << "\x1b[31mЭтот логин уже занят. Выберите другой\x1b[0m" << endl;
				create = false;
				break;
			}
			else create = true;
		}
	} while (create == false);

	do
	{
		cout << "Введите пароль" << endl;
		new_user.password = ErrorImputString();
		if (new_user.password.length() < 4)
		{
			cout << "\x1b[31mПароль слишком короткий. Введите другой\x1b[0m" << endl;
			create = false;
		}
		else create = true;
	} while (create == false);
	new_user.role = "User";
	fout << new_user.login << " " << new_user.password << " " << new_user.role << endl;
	vector_of_users.push_back(new_user);
	fout.close();
	count_of_users++;
	system("cls");
	cout << "---------------------------------------------------------------------------------------------------------------------" << endl;
	cout << "\t\t\t\t\t\t\tАВТОРИЗАЦИЯ" << endl;
}

bool autorization(vector<Users>& vector_of_users, int& count_of_users)
{
	int  pos = 0;
	bool find = false;
	string login, password;

	bool quit = false;
	system("cls");
	cout << "------------------------------------------------------------------------------------------------------------------" << endl;
	cout << "\t\t\t\t\t\t\tАВТОРИЗАЦИЯ" << endl;
	do {
		cout << "Введите логин" << endl;
		login = ErrorImputString();
		for (int i = 0; i < count_of_users; i++)
		{
			pos = i;
			if (login == vector_of_users[pos].login)
			{
				find = true;
				break;
			}
			else continue;
		}
		if (!find)
		{
			cout << "\x1b[31mНе существует аккаунта с таким логином.\x1b[0m" << endl << "Для создания нового аккаунта нажмите 1. Для продолжения - любую другую клавишу" << endl;
			if (_getch() == '1')
			{
				registration(vector_of_users, count_of_users, true);
				return false;
			}
		}
	} while (login != vector_of_users[pos].login);

	do
	{
		cout << "Введите пароль" << endl;
		password = ErrorImputString();

		if (password != vector_of_users[pos].password)
		{
			cout << "\x1b[31mНеверный пароль. Попробуйте снова\x1b[0m" << endl;
		}
	} while (password != vector_of_users[pos].password);

	system("cls");

	if (vector_of_users[pos].role == "Admin") quit = Admin_display(vector_of_users, count_of_users, login);
	else if (vector_of_users[pos].role == "User") quit = User_display(login, vector_of_users[pos].for_delete);
	FromVectortoFile(vector_of_users, count_of_users);
	find = false;
	cin.ignore(256, '\n');
	system("cls");
	return quit;
}

void StartMenu()
{
	int count_of_users = 0, start_menu;
	bool quit = false;
	vector<Users>vector_of_users;
	FromFileToVector(vector_of_users, count_of_users);

	if (count_of_users == 0)
	{
		ofstream fout("Users.txt", ios::out);
		Users new_user;
		new_user.login = "Admin";
		new_user.password = "Admin";
		new_user.role = "Admin";
		fout << new_user.login << " " << new_user.password << " " << new_user.role << endl;
		fout.close();
		vector_of_users.push_back(new_user);
		count_of_users++;
	}
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "1) Авторизация" << endl << "2) Регистрация" << endl << "Для выхода из программы укажите любую другую цифру" << endl;
		cout << "__________________________________________________________________" << endl;
		start_menu = ErrorImputInt();
		if (start_menu == 1) {
			cin.ignore(256, '\n');
			quit = autorization(vector_of_users, count_of_users);
		}
		else if (start_menu == 2) registration(vector_of_users, count_of_users, false);
		else quit = true;
	} while (quit == false);

}
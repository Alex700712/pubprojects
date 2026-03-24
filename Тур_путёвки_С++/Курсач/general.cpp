#include "general.h"

using namespace std;

int ErrorImputInt()
{
	int value;
	bool input = true;
	do
	{
		cin >> value;
		if (!cin)
		{
			input = false;
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mОшибка ввода. Повторите попытку\x1b[0m" << endl;
			cin.clear();
			cin.ignore(256, '\n');
		}
		else input = true;
	} while (input == false);
	return value;
}

string ErrorImputString()
{
	string value;
	bool input = true;
	do
	{
		getline(cin, value);
		if (!cin)
		{
			input = false;
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mОшибка ввода. Повторите попытку\x1b[0m" << endl;
			cin.clear();
			cin.ignore(256, '\n');
		}
		else input = true;
	} while (input == false);
	return value;
}

void MakeTourAvailable(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	if (count_of_tours == 0)
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mОшибка. Список туров пуст\x1b[0m" << endl;
		cout << "-------------------------------------------" << endl;
		return;
	}
	else
	{
		int change_tour;
		cout << "Укажите тур для освобождения (от 1 до " << count_of_tours << ")" << endl;
		cout << "-------------------------------------------" << endl;
		change_tour = ErrorImputInt();
		if ((change_tour < 1 || change_tour > count_of_tours)) cout << "Ошибка. Тур не существует" << endl;
		else
		{
			change_tour--;
			vector_of_tours.at(change_tour).available = true;
			vector_of_tours.at(change_tour).client = "";
			fstream file("Users.txt", ios::trunc);
			file.close();
			FromVectortoFile(vector_of_tours, count_of_tours);
		}
	}
}

void ClearAvailability(vector<Users>& vector_of_users, int& count_of_users, int deleted_user)
{
	int count_of_tours = 0;
	vector<Tour> vector_of_tours;
	FromFileToVector(vector_of_tours, count_of_tours);
	for (int i = 0; i < count_of_tours; i++)
		if ((vector_of_tours[i].available == false) && (vector_of_tours[i].client == vector_of_users[deleted_user].login))
		{
			vector_of_tours.at(i).available = true;
			vector_of_tours.at(i).client = "-";
		}
	FromVectortoFile(vector_of_tours, count_of_tours);
}

void DeleteAccount(bool& for_delete)
{
	int change;
	cout << "__________________________________________________________________" << endl;
	cout << "1) Отправить запрос на удаление аккаунта" << endl << "2) Отменить удаление" << endl;
	cout << "__________________________________________________________________" << endl;
	change = ErrorImputInt();
	if (change == 1)
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mВы уверены, что хотите отправить запрос на удаление? Будет произведён автоматический выход из аккаунта. Вы сможете в него войти до подтверждения удаления администратором\x1b[0m"
			<< endl << "Для подтверждения нажмите 0." << endl << "Для отмены - любую другую клавишу" << endl;
		if (_getch() == '0')
		{
			system("cls");
			cout << "-------------------------------------------" << endl;
			for_delete = true;
			cout << "\x1b[32mЗапрос отправлен. Администратор удалит ваш аккаунт через некоторое время\x1b[0m" << endl;
			cout << "__________________________________________________________________" << endl;
		}
		return;
	}
	if (change == 2)
	{
		for_delete = false;
		cout << "Запрос на удаление отменён." << endl;
	}
}

void AccountInfo(string login)
{
	int count_of_users = 0;
	bool view = false;
	vector<Users>vector_of_users;
	FromFileToVector(vector_of_users, count_of_users);
	for (int i = 0; i < count_of_users; i++)
		if (vector_of_users[i].login == login)
		{
			view = true;
			cout << "__________________________________________________________________" << endl;
			cout << "Данные об аккаунте" << endl << "-------------------------------------------" << endl << "Логин: " << vector_of_users[i].login << endl << "Пароль: " << vector_of_users[i].password
				<< endl << "Роль: " << vector_of_users[i].role << endl;
			break;
		}
	if (view == false)
	{
		cout << "__________________________________________________________________" << endl;
		cout << "\x1b[31mОшибка вывода данных об аккаунте.\x1b[0m" << endl;
		return;
	}

	cout << "-------------------------------------------" << endl;
	cout << "Нажмите 1, чтобы сменить пароль" << endl << "Для возрата в меню нажмите любую клавишу" << endl;
	if (_getch() == '1') ChangePassword(vector_of_users, count_of_users, login);
	return;
}

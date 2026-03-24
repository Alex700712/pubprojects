#pragma once

#include <string>
#include <iostream>
#include <Windows.h>
#include <vector>
#include <iterator>
#include <algorithm>
#include <fstream>
#include <conio.h>
#include <iomanip>
using namespace std;

struct Users
{
	string login;
	string password;
	string role;
	bool for_delete = false;
};

// Функции для работы с аккаунтами
void FromFileToVector(vector<Users>& vector_of_users, int& count_of_users); //Импорт аккаунтов из файла в вектор

void FromVectortoFile(vector<Users> vector_of_users, int count_of_users);  //Экспорт аккаунтов из вектора в файл

void AllUsers(vector<Users> vector_of_users, int count_of_users);  //Список всех аккаунтов

void AccountsForDelete(vector<Users> vector_of_users, int count_of_users); //Список аккаунтов для удаления

void ChangePassword(vector<Users>& vector_of_users, int& count_of_users, string login); //Смена пароля

void DeleteUser(vector<Users>& vector_of_users, int& count_of_users, string login); //Удаление пользователя

void ChangeRole(vector<Users>& vector_of_users, int count_of_users, string login); //Смена роли

void WorkWithUsers(vector<Users>& vector_of_users, int& count_of_users, string login); //Меню работы с пользователями

bool User_display(string login, bool& for_delete); //Модуль пользователя

bool Admin_display(vector<Users>& vector_of_users, int& count_of_users, string login); //Модуль администратора

void registration(vector<Users>& vector_of_users, int& count_of_users, bool from_autorization); //Регистрация

bool autorization(vector<Users>& vector_of_users, int& count_of_users); //Авторизация

void StartMenu(); //Стартовое меню
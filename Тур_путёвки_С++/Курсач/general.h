#pragma once
#include "work_with_users.h"
#include "work_with_tours_and_companies.h"
using namespace std;

//Общие функции и функции, работающие с несколькими векторами
int ErrorImputInt(); //Проверка на нечисловой ввод

string ErrorImputString(); //Проверка на ошибки ввода строки

void MakeTourAvailable(vector<Tour>& vector_of_tours, int& count_of_tours); //Освобождение тура

void ClearAvailability(vector<Users>& vector_of_users, int& count_of_users, int deleted_user); //Очистка занятости тура(при удалении пользователя)

void DeleteAccount(bool& for_delete); //Удаление аккаунта

void AccountInfo(string login); //Информация об аккаунте
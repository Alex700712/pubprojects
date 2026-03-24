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

struct Tour
{
	string type;
	string country;
	string transport;
	string fead;
	int price = 0;
	int days = 0;
	bool available = true;
	string client = "-";
};

struct Hotel
{
	int stars = 0;
	bool rooms = false;
};

struct Company
{
	string name;
	string country;
	string type;
	Hotel hotel{};
};

//Функции для работы с турами и компаниями 
void FromFileToVector(vector<Tour>& vector_of_tours, int& count_of_tours); //Импорт туров из файла в вектор

void FromFileToVector(vector<Company>& vector_of_companies, int& count_of_companies); //Импорт компаний из файла в вектор

void FromVectortoFile(vector<Tour> vector_of_tours, int count_of_tours); //Экспорт туров из вектора в файл

void FromVectortoFile(vector<Company> vector_of_companies, int count_of_companies); //Экспорт компаний из вектора в файл

bool ComparatorForPrice(Tour tour1, Tour tour2); //компаратор для сортировки

bool ComparatorForTourCountries(Tour tour1, Tour tour2); //компаратор для сортировки

bool ComparatorForDays(Tour tour1, Tour tour2); //компаратор для сортировки

bool ComparatorForCompCountries(Company comp1, Company comp2); //компаратор для сортировки

bool ComparatorForCompName(Company comp1, Company comp2); //компаратор для сортировки

bool ComparatorForCompType(Company comp1, Company comp2); //компаратор для сортировки

void SortToursForPrice(vector<Tour>& vector_of_tours, int& count_of_tours); //Сортировка туров по цене

void SortToursForCountries(vector<Tour>& vector_of_tours, int& count_of_tours); //Сортировка туров по странам

void SortToursForDays(vector<Tour>& vector_of_tours, int& count_of_tours); //Сортировка туров по количеству дней

void SortCompaniesForCountries(vector<Company>& vector_of_companies, int& count_of_companies); //Сортировка компаний по странам

void SortCompaniesForName(vector<Company>& vector_of_companies, int& count_of_companies); //Сортировка компаний по названию

void SortCompaniesForType(vector<Company>& vector_of_companies, int& count_of_companies); //Сортировка компаний по типу

void Sort(vector<Tour>& vector_of_tours, int& count_of_tours); //Меню сортировки туров

void Sort(vector<Company>& vector_of_companies, int& count_of_companies); //Меню сортировки компаний

void AllTours(vector<Tour> vector_of_tours, int count_of_tours); //Список всех туров

void BookedTours(vector<Tour> vector_of_tours, int count_of_tours); // список забронированных туров

void AllCompanies(vector<Company> vector_of_companies, int count_of_companies); //Список всех компаний

void Edit(vector<Tour>& vector_of_tours, int& count_of_tours, vector<Company> vector_of_companies, int count_of_companies); //Редактирование тура

void Edit(vector<Company>& vector_of_companies, int& count_of_companies); //Редактирование компании

void AddNewTour(vector<Tour>& vector_of_tours, int& count_of_tours, vector<Company> vector_of_companies, int count_of_companies); //Добавление нового тура

void AddNewCompany(vector<Company>& vector_of_companies, int& count_of_companies); //Добавление новой компании

void BookATour(vector<Tour>& vector_of_tours, int& count_of_tours, string login); //Бронирование тура

void ToursByType(vector<Tour> vector_of_tours, int count_of_tours); //Список всех туров по типу до указанной стоимости (индивидуальное задание)

void Search(vector<Tour>& vector_of_tours, int& count_of_tours); // Поиск тура

void DeleteCompany(vector<Company>& vector_of_companies, int& count_of_companies); // Удаление компании

void DeleteTour(vector<Tour>& vector_of_tours, int& count_of_tours); // Удаление тура

void WorkWithToursAndCompanies(); // Меню работы с турами и компаниями
#include "work_with_tours_and_companies.h"
#include "general.h"
using namespace std;

void FromFileToVector(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	ifstream fin("Tours.txt", ios::in);
	if (!fin.is_open())
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mОшибка открытия файла Tours.txt\x1b[0m" << endl;
	}
	else
	{
		Tour temp;
		while (fin >> temp.type >> temp.country >> temp.days >> temp.fead >> temp.price >> temp.transport >> temp.available >> temp.client)
		{
			vector_of_tours.push_back(temp);
			count_of_tours++;
		}
	}
	fin.close();
}

void FromFileToVector(vector<Company>& vector_of_companies, int& count_of_companies)
{
	ifstream fin("Companies.txt", ios::in);
	if (!fin.is_open())
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mОшибка открытия файла Companies.txt\x1b[0m" << endl;
	}
	else
	{
		Company temp;

		while (fin >> temp.type >> temp.name >> temp.country >> temp.hotel.stars >> temp.hotel.rooms)
		{
			if (temp.type == "Автомобильная_компания" || temp.type == "Авиакомпания")
			{
				temp.hotel.rooms = false;
			}
			vector_of_companies.push_back(temp);
			count_of_companies++;
		}
	}
	fin.close();
}

void FromVectortoFile(vector<Tour> vector_of_tours, int count_of_tours)
{
	ofstream fout("Tours.txt", ios::out);
	for (int i = 0; i < count_of_tours; i++)
	{
		fout << vector_of_tours.at(i).type << " ";
		fout << vector_of_tours.at(i).country << " ";
		fout << vector_of_tours.at(i).days << " ";
		fout << vector_of_tours.at(i).fead << " ";
		fout << vector_of_tours.at(i).price << " ";
		fout << vector_of_tours.at(i).transport << " ";
		fout << vector_of_tours.at(i).available << " ";
		fout << vector_of_tours.at(i).client << endl;
	}
}

void FromVectortoFile(vector<Company> vector_of_companies, int count_of_companies)
{
	ofstream fout("Companies.txt", ios::out);
	for (int i = 0; i < count_of_companies; i++)
	{
		fout << vector_of_companies.at(i).type << " ";
		fout << vector_of_companies.at(i).name << " ";
		fout << vector_of_companies.at(i).country << " ";
		fout << vector_of_companies.at(i).hotel.stars << " ";
		fout << vector_of_companies.at(i).hotel.rooms << endl;
	}
}

bool ComparatorForPrice(Tour tour1, Tour tour2)
{
	return tour1.price > tour2.price;
}

bool ComparatorForTourCountries(Tour tour1, Tour tour2)
{
	return tour1.country < tour2.country;
}

bool ComparatorForDays(Tour tour1, Tour tour2)
{
	return tour1.days < tour2.days;
}

bool ComparatorForCompCountries(Company comp1, Company comp2)
{
	return comp1.country < comp2.country;
}

bool ComparatorForCompName(Company comp1, Company comp2)
{
	return comp1.name < comp2.name;
}

bool ComparatorForCompType(Company comp1, Company comp2)
{
	return comp1.type < comp2.type;
}

void SortToursForPrice(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	sort(vector_of_tours.begin(), vector_of_tours.end(), ComparatorForPrice);
	FromVectortoFile(vector_of_tours, count_of_tours);
}

void SortToursForCountries(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	sort(vector_of_tours.begin(), vector_of_tours.end(), ComparatorForTourCountries);
	FromVectortoFile(vector_of_tours, count_of_tours);
}

void SortToursForDays(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	sort(vector_of_tours.begin(), vector_of_tours.end(), ComparatorForDays);
	FromVectortoFile(vector_of_tours, count_of_tours);
}

void SortCompaniesForCountries(vector<Company>& vector_of_companies, int& count_of_companies)
{
	sort(vector_of_companies.begin(), vector_of_companies.end(), ComparatorForCompCountries);
	FromVectortoFile(vector_of_companies, count_of_companies);
}

void SortCompaniesForName(vector<Company>& vector_of_companies, int& count_of_companies)
{
	sort(vector_of_companies.begin(), vector_of_companies.end(), ComparatorForCompName);
	FromVectortoFile(vector_of_companies, count_of_companies);
}

void SortCompaniesForType(vector<Company>& vector_of_companies, int& count_of_companies)
{
	sort(vector_of_companies.begin(), vector_of_companies.end(), ComparatorForCompType);
	FromVectortoFile(vector_of_companies, count_of_companies);
}

void Sort(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	int sort;
	do
	{
		cout << "По какому критерию отсортировать:" << endl << "1) По цене(убывание)" << endl << "2) По странам(в алфавитном порядке)" << endl << "3) По кол-ву дней(возрастание)" << endl;
		sort = ErrorImputInt();
		if (sort == 1) SortToursForPrice(vector_of_tours, count_of_tours);
		else if (sort == 2) SortToursForCountries(vector_of_tours, count_of_tours);
		else if (sort == 3) SortToursForDays(vector_of_tours, count_of_tours);
		else cout << "\x1v[31mТакой сортировки нет. Выберите другую\x1v[0m" << endl;
	} while (sort < 0 && sort > 3);
}

void Sort(vector<Company>& vector_of_companies, int& count_of_companies)
{
	int sort;
	do
	{
		cout << "По какому критерию отсортировать:" << endl << "1) По названию(в алфавитном порядке)" << endl << "2) По странам(в алфавитном порядке)" << endl << "3) По типу(в алфавитном порядке)" << endl;
		sort = ErrorImputInt();
		if (sort == 1) SortCompaniesForName(vector_of_companies, count_of_companies);
		else if (sort == 2) SortCompaniesForCountries(vector_of_companies, count_of_companies);
		else if (sort == 3) SortCompaniesForType(vector_of_companies, count_of_companies);
		else cout << "\x1v[31mТакой сортировки нет. Выберите другую\x1v[0m" << endl;
	} while (sort < 0 && sort > 3);
	FromVectortoFile(vector_of_companies, count_of_companies);
}

void AllTours(vector<Tour> vector_of_tours, int count_of_tours)
{
	cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
	cout << "|" << setw(4) << "№|" << setw(29) << "Тип тура|" << setw(17) << "Страна|" << setw(16) << "Питание|" << setw(13) << "Кол-во дней|" << setw(13) << "Стоимость|"
		<< setw(16) << "Транспорт|" << setw(13) << "Доступность|" << setw(16) << "Клиент|" << endl;
	cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
	for (int i = 0; i < count_of_tours; i++)
	{
		cout << "|" << setw(3) << i + 1;
		cout << "|" << setw(28) << vector_of_tours[i].type;
		cout << "|" << setw(16) << vector_of_tours[i].country;
		cout << "|" << setw(15) << vector_of_tours[i].fead;
		cout << "|" << setw(12) << vector_of_tours[i].days;
		cout << "|" << setw(11) << vector_of_tours[i].price << "$";
		cout << "|" << setw(15) << vector_of_tours[i].transport;
		if (vector_of_tours[i].available)
		{
			cout << "|" << setw(12) << "Свободен";
			vector_of_tours[i].client = "-";
		}
		else cout << "|" << setw(12) << "Занят ";
		cout << "|" << setw(15) << vector_of_tours[i].client << "|" << endl;
	}
	cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
}

void BookedTours(vector<Tour> vector_of_tours, int count_of_tours)
{
	cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
	cout << "|" << setw(4) << "№|" << setw(29) << "Тип тура|" << setw(17) << "Страна|" << setw(16) << "Питание|" << setw(13) << "Кол-во дней|" << setw(13) << "Стоимость|"
		<< setw(16) << "Транспорт|" << setw(13) << "Доступность|" << setw(16) << "Клиент|" << endl;
	cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
	for (int i = 0; i < count_of_tours; i++)
	{
		if (!vector_of_tours[i].available)
		{
			cout << "|" << setw(3) << i + 1;
			cout << "|" << setw(28) << vector_of_tours[i].type;
			cout << "|" << setw(16) << vector_of_tours[i].country;
			cout << "|" << setw(15) << vector_of_tours[i].fead;
			cout << "|" << setw(12) << vector_of_tours[i].days;
			cout << "|" << setw(11) << vector_of_tours[i].price << "$";
			cout << "|" << setw(15) << vector_of_tours[i].transport;
			cout << "|" << setw(12) << "Занят" << "|" << setw(15) << vector_of_tours[i].client << "|" << endl;
			cout << "|---|----------------------------|----------------|---------------|------------|------------|---------------|------------|---------------|" << endl;
		}
	}
}

void AllCompanies(vector<Company> vector_of_companies, int count_of_companies)
{
	cout << "|---|----------------------------|---------------|----------------|---------------------|-----------------|" << endl;
	cout << "|" << setw(4) << "№|" << setw(29) << "Тип компании|" << setw(16) << "Название|" << setw(17) << "Страна|" << setw(22) << "Уровень отеля|" << setw(18) << "Комнаты|" << endl;
	cout << "|---|----------------------------|---------------|----------------|---------------------|-----------------|" << endl;
	for (int i = 0; i < count_of_companies; i++)
	{
		cout << "|" << setw(3) << i + 1;
		cout << "|" << setw(28) << vector_of_companies[i].type;
		cout << "|" << setw(15) << vector_of_companies[i].name;
		cout << "|" << setw(16) << vector_of_companies[i].country;
		if (vector_of_companies[i].type == "Отель")
		{
			cout << "|" << setw(15) << vector_of_companies[i].hotel.stars << " звёзд";
			if (vector_of_companies[i].hotel.rooms == true)
				cout << "|" << setw(18) << "В наличии|" << endl;
			else cout << "|" << setw(18) << "Заняты|" << endl;
		}
		else
		{
			cout << "|" << setw(22) << "-|" << setw(18) << "-|" << endl;
		}
	}
	cout << "|---|----------------------------|---------------|----------------|---------------------|-----------------|" << endl;
}

void Edit(vector<Tour>& vector_of_tours, int& count_of_tours, vector<Company> vector_of_companies, int count_of_companies)
{
	int i, edit, change_tour;
	bool create = false;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Введите номер изменяемого тура (от 1 до " << count_of_tours << ")" << endl;
		change_tour = ErrorImputInt();
		change_tour--;
		if (change_tour > count_of_tours && change_tour <= 0)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mТура под этим номером не существует. Выберите другой\x1b[0m" << endl;
		}
	} while (change_tour > count_of_tours && change_tour <= 0);

	cout << "Какой критерий вы хотите изменить: " << endl << "1) Тип тура" << endl << "2) Страна" << endl << "3) Питание" <<
		endl << "4) Транспорт" << endl << "5) Кол-во дней" << endl << "6) Стоимость" << endl;
	edit = ErrorImputInt();
	switch (edit)
	{
	case 1:
		cin.ignore(256, '\n');
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Тип тура(Рекреационный_тур(отдых), Экскурсионный_тур, Лечебно-Оздоровительный_тур, Шоп-тур, Историко-Культурный_тур, Бизнес-тур, Спортивный_тур)" << endl;
			cout << "__________________________________________________________________" << endl;
			vector_of_tours.at(change_tour).type = ErrorImputString();
			if ((vector_of_tours[change_tour].type == "Рекреационный_тур") || (vector_of_tours[change_tour].type == "Экскурсионный_тур") || (vector_of_tours[change_tour].type == "Лечебно-Оздоровительный_тур") ||
				(vector_of_tours[change_tour].type == "Шоп-тур") || (vector_of_tours[change_tour].type == "Историко-Культурный_тур") || (vector_of_tours[change_tour].type == "Бизнес-тур")
				|| (vector_of_tours[change_tour].type == "Спортивный_тур"))
				create = true;
			else create = false;
			if (create == false)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mНевозможно добавить данный тип. Проверьте правильность ввода приведённых типов и повторите попытку\x1b[0m" << endl;
			}
		} while (create == false);

		break;
	case 2:
		cin.ignore(256, '\n');
		do
		{
			bool hotel = false, transport = false;
			cout << "__________________________________________________________________" << endl;
			cout << "Страна посещения" << endl;
			cout << "__________________________________________________________________" << endl;
			vector_of_tours.at(change_tour).country = ErrorImputString();
			for (i = 0; i < count_of_companies; i++)
			{
				if (vector_of_tours[change_tour].country == vector_of_companies[i].country && vector_of_companies[i].type == "Отель")
				{
					hotel = true;
					break;
				}
				else create = false;
			}
			for (i = 0; i < count_of_companies; i++)
			{
				if (vector_of_tours[change_tour].country == vector_of_companies[i].country && (vector_of_companies[i].type == "Авиакомпания" || vector_of_companies[i].type == "Автомобильная_компания"))
				{
					transport = true;
					break;
				}
				else create = false;
			}

			if (hotel == true && transport == true) create = true;
			else create = false;

			if (create == false)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mНет способа добраться или проживать в этой стране\x1b[0m" << endl;
			}
		} while (create == false);

		break;
	case 3:
		cin.ignore(256, '\n');
		cout << "__________________________________________________________________" << endl;
		cout << "Питание(Например, Все_включено, Утром)" << endl;
		cout << "__________________________________________________________________" << endl;
		vector_of_tours.at(change_tour).fead = ErrorImputString();

		break;
	case 4:
		cin.ignore(256, '\n');
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Транспорт тура(Автобус, Самолёт)" << endl;
			cout << "__________________________________________________________________" << endl;
			vector_of_tours.at(change_tour).transport = ErrorImputString();
			for (int i = 0; i < count_of_companies; i++)
			{
				if (vector_of_tours[change_tour].country == vector_of_companies[i].country && ((vector_of_companies[i].type == "Автомобильная_компания" && vector_of_tours[change_tour].transport == "Автобус")
					|| vector_of_companies[i].type == "Авиакомпания" && vector_of_tours[change_tour].transport == "Самолёт"))
				{
					create = true;
					break;
				}
				else create = false;
			}
			if (create == false)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mВ выбранной стране нет сотрудничающей компании по данному транспорту\x1b[0m" << endl;
			}
		} while (create == false);

		break;
	case 5:
		cout << "__________________________________________________________________" << endl;
		cout << "Количество дней" << endl;
		cout << "__________________________________________________________________" << endl;
		vector_of_tours.at(change_tour).days = ErrorImputInt();

		break;
	case 6:
		cout << "__________________________________________________________________" << endl;
		cout << "Стоимость (в долларах)" << endl;
		cout << "__________________________________________________________________" << endl;
		vector_of_tours.at(change_tour).price = ErrorImputInt();

		break;
	default: cout << "\x1b[31mНеверно выбран критерий для редактирования. Данные остались прежними\x1b[0m";
	}
}

void Edit(vector<Company>& vector_of_companies, int& count_of_companies)
{
	int edit, change_company;
	bool add = false;

	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Введите номер изменяемой компании (от 1 до " << count_of_companies << ")" << endl;
		change_company = ErrorImputInt();
		change_company--;
		if (change_company > count_of_companies && change_company <= 0)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mТура под этим номером не существует. Выберите другой\x1b[0m" << endl;
		}
	} while (change_company > count_of_companies && change_company <= 0);

	cout << "Какой критерий вы хотите изменить: " << endl << "1) Тип компании" << endl << "2) Название" << endl << "3) Страна" << endl << "4) Уровень отеля" << endl;
	edit = ErrorImputInt();
	switch (edit)
	{
	case 1:
		cin.ignore(256, '\n');
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Введите тип компании (Автомобильная_компания, Авиакомпания, Отель)" << endl;
			vector_of_companies.at(change_company).type = ErrorImputString();
			if ((vector_of_companies[change_company].type == "Автомобильная_компания") || (vector_of_companies[change_company].type == "Авиакомпания") || (vector_of_companies[change_company].type == "Отель"))
				add = true;
			else
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mНевозможно добавить данный тип. Проверьте правильность ввода приведённых типов и повторите попытку\x1b[0m" << endl;
				add = false;
			}
		} while (add == false);

		break;
	case 2:
		cin.ignore(256, '\n');
		cout << "__________________________________________________________________" << endl;
		cout << "Введите название компании" << endl;
		vector_of_companies.at(change_company).name = ErrorImputString();

		break;
	case 3:
		cin.ignore(256, '\n');
		cout << "__________________________________________________________________" << endl;
		cout << "Введите страну нахождения компании" << endl;
		vector_of_companies.at(change_company).country = ErrorImputString();

		break;
	case 4:
		cin.ignore(256, '\n');
		if (vector_of_companies[change_company].type == "Отель")
		{
			do
			{
				cout << "__________________________________________________________________" << endl;
				cout << "Введите уровень отеля (в звёздах)" << endl;
				vector_of_companies.at(change_company).hotel.stars = ErrorImputInt();
				if (vector_of_companies[change_company].hotel.stars < 0 || vector_of_companies[change_company].hotel.stars > 5)
				{
					cout << "-------------------------------------------" << endl;
					cout << "\x1b[31mНеверно указан уровень отеля. Повторите попытку\x1b[0m" << endl;
				}
			} while (vector_of_companies[change_company].hotel.stars < 0 || vector_of_companies[change_company].hotel.stars >= 5);
		}
		else
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mОшибка. Уровень отеля нельзя можно изменить только у отеля. Данные не изменены\x1b[0m" << endl;
			cout << "-------------------------------------------" << endl;
		}

		break;
	default:
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mНеверно выбран критерий для редактирования. Данные остались прежними\x1b[0m";
		cout << "-------------------------------------------" << endl;
	}
}

void AddNewTour(vector<Tour>& vector_of_tours, int& count_of_tours, vector<Company> vector_of_companies, int count_of_companies)
{
	int i, hotels = 0, avia = 0, aut = 0;
	bool create = true;
	ofstream fout("Tours.txt", ios::app);
	Tour new_tour;
	cin.ignore(256, '\n');
	if (vector_of_companies.size() == 0)
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mНе найдено ни одной компании. Добавить тур невозмножно\x1b[0m" << endl;
		return;
	}
	else
	{
		for (i = 0; i < count_of_companies; i++)
		{
			if (vector_of_companies[i].type == "Отель") hotels++;
			else if (vector_of_companies[i].type == "Автомобольная_компания") aut++;
			else if (vector_of_companies[i].type == "Авиакомпания") avia++;
		}
		if ((hotels < 0 && avia < 0) || (hotels < 0 && aut < 0))
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mТур не создан. Недостаточно компаний нужных типов\x1b[0m" << endl;
			return;
		}
	}
	cout << "__________________________________________________________________" << endl;
	cout << "Введите требуемые данные:" << endl;
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Тип тура(Рекреационный_тур(отдых), Экскурсионный_тур, Лечебно-Оздоровительный_тур, Шоп-тур, Историко-Культурный_тур, Бизнес-тур, Спортивный_тур)" << endl;
		cout << "__________________________________________________________________" << endl;
		new_tour.type = ErrorImputString();
		if ((new_tour.type == "Рекреационный_тур") || (new_tour.type == "Экскурсионный_тур") || (new_tour.type == "Лечебно-Оздоровительный_тур") ||
			(new_tour.type == "Шоп-тур") || (new_tour.type == "Историко-Культурный_тур") || (new_tour.type == "Бизнес-тур") || (new_tour.type == "Спортивный_тур"))
			create = true;
		else create = false;
		if (create == false)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mНевозможно добавить данный тип. Проверьте правильность ввода приведённых типов и повторите попытку\x1b[0m" << endl;
		}
	} while (create == false);

	do
	{
		bool hotel = false, transport = false;
		cout << "__________________________________________________________________" << endl;
		cout << "Страна посещения" << endl;
		cout << "__________________________________________________________________" << endl;
		new_tour.country = ErrorImputString();
		for (i = 0; i < count_of_companies; i++)
		{
			if (new_tour.country == vector_of_companies[i].country && vector_of_companies[i].type == "Отель")
			{
				hotel = true;
				break;
			}
			else create = false;
		}
		for (i = 0; i < count_of_companies; i++)
		{
			if (new_tour.country == vector_of_companies[i].country && (vector_of_companies[i].type == "Авиакомпания" || vector_of_companies[i].type == "Автомобильная_компания"))
			{
				transport = true;
				break;
			}
			else create = false;
		}

		if (hotel == true && transport == true) create = true;
		else create = false;

		if (create == false)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mНет способа добраться или проживать в этой стране\x1b[0m" << endl;
		}
	} while (create == false);

	cout << "__________________________________________________________________" << endl;
	cout << "Питание(Например, Все_включено, Утром)" << endl;
	cout << "__________________________________________________________________" << endl;
	new_tour.fead = ErrorImputString();

	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Транспорт тура(Автобус, Самолёт)" << endl;
		cout << "__________________________________________________________________" << endl;
		new_tour.transport = ErrorImputString();
		for (int i = 0; i < count_of_companies; i++)
		{
			if (new_tour.country == vector_of_companies[i].country && ((vector_of_companies[i].type == "Автомобильная_компания" && new_tour.transport == "Автобус")
				|| vector_of_companies[i].type == "Авиакомпания" && new_tour.transport == "Самолёт"))
			{
				create = true;
				break;
			}
			else create = false;
		}
		if (create == false)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mВ выбранной стране нет сотрудничающей компании по данному транспорту\x1b[0m" << endl;
		}
	} while (create == false);

	cout << "__________________________________________________________________" << endl;
	cout << "Количество дней" << endl;
	cout << "__________________________________________________________________" << endl;
	new_tour.days = ErrorImputInt();

	cout << "__________________________________________________________________" << endl;
	cout << "Стоимость (в долларах)" << endl;
	cout << "__________________________________________________________________" << endl;
	new_tour.price = ErrorImputInt();

	fout << new_tour.type << " " << new_tour.country << " " << new_tour.days << " " << new_tour.fead << " "
		<< new_tour.price << " " << new_tour.transport << " " << new_tour.available << " " << new_tour.client << endl;
	vector_of_tours.push_back(new_tour);
	fout.close();
	count_of_tours++;
	SortToursForPrice(vector_of_tours, count_of_tours);
}

void AddNewCompany(vector<Company>& vector_of_companies, int& count_of_companies)
{
	ofstream fout("Companies.txt", ios::app);
	Company new_company;
	bool add = false;
	cin.ignore(256, '\n');
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Введите тип компании (Автомобильная_компания, Авиакомпания, Отель)" << endl;
		new_company.type = ErrorImputString();
		if ((new_company.type == "Автомобильная_компания") || (new_company.type == "Авиакомпания") || (new_company.type == "Отель"))
			add = true;
		else
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mНевозможно добавить данный тип. Проверьте правильность ввода приведённых типов и повторите попытку\x1b[0m" << endl;
			add = false;
		}
	} while (add == false);

	cout << "__________________________________________________________________" << endl;
	cout << "Введите название компании" << endl;
	new_company.name = ErrorImputString();

	cout << "__________________________________________________________________" << endl;
	cout << "Введите страну нахождения компании" << endl;
	new_company.country = ErrorImputString();

	if (new_company.type == "Автомобильная_компания" || new_company.type == "Авиакомпания")
	{
		new_company.hotel.rooms = false;
	}
	else if (new_company.type == "Отель")
	{
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Введите уровень отеля (в звёздах)" << endl;
			new_company.hotel.stars = ErrorImputInt();
			if (new_company.hotel.stars < 0 || new_company.hotel.stars > 5)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mНеверно указан уровень отеля. Повторите попытку\x1b[0m" << endl;
			}
		} while (new_company.hotel.stars < 0 || new_company.hotel.stars >= 5);
		new_company.hotel.rooms = true;
	}
	fout << new_company.type << " " << new_company.name << " " << new_company.country << " " << new_company.hotel.stars << " " << new_company.hotel.rooms << " " << endl;
	vector_of_companies.push_back(new_company);
	fout.close();
	count_of_companies++;
	SortCompaniesForCountries(vector_of_companies, count_of_companies);
}

void BookATour(vector<Tour>& vector_of_tours, int& count_of_tours, string login)
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
		cout << "__________________________________________________________________" << endl;
		cout << "Укажите желаемый тур (от 1 до " << count_of_tours << ")" << endl;
		change_tour = ErrorImputInt();
		change_tour--;
		if ((change_tour < 0 || change_tour > count_of_tours) || vector_of_tours[change_tour].available == false)
		{
			cout << "-------------------------------------------" << endl;
			cout << "\x1b[31mОшибка. Тур занят либо не существует\x1b[0m" << endl;
			cout << "-------------------------------------------" << endl;
		}
		else
		{
			vector_of_tours.at(change_tour).available = false;
			vector_of_tours.at(change_tour).client = login;
			fstream file("Users.txt", ios::trunc);
			file.close();
			FromVectortoFile(vector_of_tours, count_of_tours);
		}
	}
}

void ToursByType(vector<Tour> vector_of_tours, int count_of_tours)
{
	int change = 0, cost, count_temp = 0;
	string type;
	vector<Tour> temp;

	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Какой тип тура вывести?" << endl << "1) Рекреационный_тур(отдых)" << endl << "2) Экскурсионный_тур" << endl << "3) Лечебно-Оздоровительный_тур" << endl
			<< "4) Шоп-тур" << endl << "5) Историко-Культурный_тур" << endl << "6) Бизнес-тур" << endl << "7) Спортивный_тур" << endl;
		cout << "__________________________________________________________________" << endl;
		change = ErrorImputInt();
		switch (change)
		{
		case 1: type = "Рекреационный_тур"; break;
		case 2: type = "Экскурсионный_тур"; break;
		case 3: type = "Лечебно-Оздоровительный_тур"; break;
		case 4: type = "Шоп-тур"; break;
		case 5: type = "Историко-Культурный_тур"; break;
		case 6: type = "Бизнес-тур"; break;
		case 7: type = "Спортивный_тур"; break;
		default: cout << "\x1b[31mОшибка. Тип не найден\x1b[0m";
			cout << "__________________________________________________________________" << endl;
		}
	} while (change < 0 || change > 7);
	cout << "__________________________________________________________________" << endl;

	do
	{
		cout << "Укажите максимальную стоимость тура. Если ограничение не требуется - введите 0" << endl;
		cout << "__________________________________________________________________" << endl;
		cost = ErrorImputInt();
		if (cost < 0)
		{
			cout << "__________________________________________________________________" << endl;
			cout << "\x1b[31mОшибка. Стоимость не может быть отрицательной. Повторите попытку\x1b[0m" << endl;
		}
	} while (cost < 0);

	if (cost == 0) cost = MAXINT;

	for (int i = 0; i < count_of_tours; i++)
		if (vector_of_tours[i].type == type && vector_of_tours[i].price <= cost)
		{
			temp.push_back(vector_of_tours[i]);
			count_temp++;
		}
	AllTours(temp, count_temp);
}

void Search(vector<Tour>& vector_of_tours, int& count_of_tours)
{
	vector<Tour> search;
	int srch, cost, count_search = 0;
	string search_string;
	cout << "По какому критерию найти тур?" << endl << "1) По выбранной стране" << endl << "2) По выбранному типу" << endl << "3) По ограниченной цене" << endl;
	srch = ErrorImputInt();
	if (srch == 1)
	{
		cout << "Введите страну для поиска" << endl;
		cin.ignore(256, '\n');
		search_string = ErrorImputString();
		for (int i = 0; i < count_of_tours; i++)
			if (search_string == vector_of_tours[i].country)
			{
				search.push_back(vector_of_tours[i]);
				count_search++;
			}
		AllTours(search, count_search);
	}
	else if (srch == 2)
	{
		cout << "__________________________________________________________________" << endl;
		do
		{
			cout << "Какой тип тура найти?" << endl << "1) Рекреационный_тур(отдых)" << endl << "2) Экскурсионный_тур" << endl << "3) Лечебно-Оздоровительный_тур" << endl
				<< "4) Шоп-тур" << endl << "5) Историко-Культурный_тур" << endl << "6) Бизнес-тур" << endl << "7) Спортивный_тур" << endl;
			cout << "__________________________________________________________________" << endl;
			srch = ErrorImputInt();
			switch (srch)
			{
			case 1: search_string = "Рекреационный_тур"; break;
			case 2: search_string = "Экскурсионный_тур"; break;
			case 3: search_string = "Лечебно-Оздоровительный_тур"; break;
			case 4: search_string = "Шоп-тур"; break;
			case 5: search_string = "Историко-Культурный_тур"; break;
			case 6: search_string = "Бизнес-тур"; break;
			case 7: search_string = "Спортивный_тур"; break;
			default: cout << "\x1b[31mОшибка. Тип не найден\x1b[0m";
				cout << "__________________________________________________________________" << endl;
			}
		} while (srch < 0 || srch > 7);

		cout << "__________________________________________________________________" << endl;

		for (int i = 0; i < count_of_tours; i++)
			if (search_string == vector_of_tours[i].type)
			{
				search.push_back(vector_of_tours[i]);
				count_search++;
			}
		AllTours(search, count_search);
	}
	else if (srch == 3)
	{
		cout << "1) Более указанной цены" << endl << "2) Менее указанной цены" << endl;
		srch = ErrorImputInt();
		cout << "Укажите цену ограничения" << endl;
		cost = ErrorImputInt();

		if (srch == 1)
		{
			for (int i = 0; i < count_of_tours; i++)
				if (cost < vector_of_tours[i].price)
				{
					search.push_back(vector_of_tours[i]);
					count_search++;
				}
			AllTours(search, count_search);
		}
		else if (srch == 2)
		{
			for (int i = 0; i < count_of_tours; i++)
				if (cost > vector_of_tours[i].price)
				{
					search.push_back(vector_of_tours[i]);
					count_search++;
				}
			AllTours(search, count_search);
		}
	}
	else
	{
		cout << "\x1b[31mНеверно выбран критерий поиска. Поиск не произведён\x1b[0m" << endl;
		return;
	}
}

void DeleteCompany(vector<Company>& vector_of_companies, int& count_of_companies)
{
	if (count_of_companies == 0)
	{
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[31mОшибка. Список компаний пуст\x1b[0m" << endl;
		cout << "-------------------------------------------" << endl;
		return;
	}
	else
	{
		SortCompaniesForCountries(vector_of_companies, count_of_companies);
		int deleted_company;
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Введите номер удаляемой компании" << endl;
			deleted_company = ErrorImputInt();
			if (deleted_company > count_of_companies && deleted_company < 0)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mКомпании под этим номером не существует. Выберите другую\x1b[0m" << endl;
			}
		} while (deleted_company > count_of_companies && deleted_company < 0);
		vector_of_companies.erase(vector_of_companies.begin() + deleted_company - 1);
		count_of_companies--;
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[32mКомпания успешно удалёна\x1b[0m" << endl;
		fstream file("Companies.txt", ios::trunc);
		file.close();
		FromVectortoFile(vector_of_companies, count_of_companies);
	}
}

void DeleteTour(vector<Tour>& vector_of_tours, int& count_of_tours)
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
		SortToursForPrice(vector_of_tours, count_of_tours);
		int deleted_tour;
		do
		{
			cout << "__________________________________________________________________" << endl;
			cout << "Введите номер удаляемого тура" << endl;
			deleted_tour = ErrorImputInt();
			if (deleted_tour > count_of_tours && deleted_tour <= 0)
			{
				cout << "-------------------------------------------" << endl;
				cout << "\x1b[31mТура под этим номером не существует. Выберите другой\x1b[0m" << endl;
			}
		} while (deleted_tour > count_of_tours && deleted_tour <= 0);
		vector_of_tours.erase(vector_of_tours.begin() + deleted_tour - 1);
		count_of_tours--;
		cout << "-------------------------------------------" << endl;
		cout << "\x1b[32mТур успешно удалён\x1b[0m" << endl;
		fstream file("Companies.txt", ios::trunc);
		file.close();
		FromVectortoFile(vector_of_tours, count_of_tours);
	}
}

void WorkWithToursAndCompanies()
{
	int admin_menu, count_of_tours = 0, count_of_companies = 0;
	vector<Tour> vector_of_tours;
	FromFileToVector(vector_of_tours, count_of_tours);
	vector<Company> vector_of_companies;
	vector_of_tours.reserve(count_of_companies);
	FromFileToVector(vector_of_companies, count_of_companies);
	do
	{
		cout << "__________________________________________________________________" << endl;
		cout << "Меню работы с турами и компаниями-партнёрами\n\n" << "1) Список всех (имеющихся) туров" << endl << "2) Добавить новый тур" << endl << "3) Список туров по типу"
			<< endl << "4) Список занятых туров" << endl << "5) Найти тур" << endl << "6) Редактировать тур" << endl << "7) Освободить тур" << endl << "8) Удалить тур" << endl
			<< "9) Отсортировать туры" << endl << "10) Список всёх компаний-партнёров" << endl << "11) Добавить новую компанию-партнёра" << endl << "12) Редактировать компанию-партнёра"
			<< endl << "13) Удалить компанию-партнёра" << endl << "14) Отсортировать компании-партнёры" << endl << "15) Вернуться в главное меню" << endl;
		cout << "__________________________________________________________________" << endl;
		admin_menu = ErrorImputInt();
		switch (admin_menu)
		{
		case 1: AllTours(vector_of_tours, count_of_tours); break;
		case 2: AddNewTour(vector_of_tours, count_of_tours, vector_of_companies, count_of_companies); break;
		case 3: ToursByType(vector_of_tours, count_of_tours); break;
		case 4: BookedTours(vector_of_tours, count_of_tours); break;
		case 5: Search(vector_of_tours, count_of_tours); break;
		case 6: Edit(vector_of_tours, count_of_tours, vector_of_companies, count_of_companies);
		case 7: MakeTourAvailable(vector_of_tours, count_of_tours); break;
		case 8: DeleteTour(vector_of_tours, count_of_tours); break;
		case 9: Sort(vector_of_tours, count_of_tours); break;
		case 10: AllCompanies(vector_of_companies, count_of_companies); break;
		case 11: AddNewCompany(vector_of_companies, count_of_companies); break;
		case 12: Edit(vector_of_companies, count_of_companies); break;
		case 13: DeleteCompany(vector_of_companies, count_of_companies); break;
		case 14: Sort(vector_of_companies, count_of_companies); break;
		case 15: break;
		default: cout << "\x1b[31mНеверный пункт меню. Выберите другой.\x1b[0m" << endl;
		}
	} while (admin_menu != 15);
}
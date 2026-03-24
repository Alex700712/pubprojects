#include "Menu.h"

void UserPropertyMenu(User user)
{
	int select;
	vector<Property> properties;
	vector<Property> orders;
	vector<Property> advertments;
	Property clear;

	do
	{
		properties.clear();
		orders.clear();
		advertments.clear();
		cout << "1) Покупка\n2) Продажа\n0) Вернуться в меню " << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 1)
		{
			ReadProperties(properties);
			sort(properties.begin(), properties.end());
			DistributeProperties(properties, orders, advertments);

			Property prop = FindBuyerProperty(properties, orders, advertments, user);
			int select_buy;
			do
			{
				cout << "1) Просмотреть объявления\n2) Выбрать недвижимость для покупки\n3) Просмотреть выбранную недвижимость\n4) Подтвердить покупку\n5) Отменить выбор недвижимости\n0) Назад" << endl;
				do
				{
					cin >> select_buy;
				} while (!CheckStream());
				switch (select_buy)
				{
				case 1:
				{
					shared_ptr<Property> ptr;
					int count = 0;
					cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
						<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
					cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					for (int i = 0; i < (int)advertments.size(); i++)
					{
						cout << "|" << setw(4) << i + 1;
						advertments[i].propertyInfo();
						advertments[i].createPtr();
						ptr = advertments[i].getPrt();
						count += ptr.use_count() - 1;
					}
					cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
					cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
				}
				break;
				case 2:
					ChoosePurchase(advertments, properties, prop, user);
					break;
				case 3:
					cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					cout << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
						<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
					cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					prop.propertyInfo();
					cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
					break;
				case 4:
					ConfirmPurchase(properties, user);
					break;
				case 5:
					CancelPurchase(advertments, properties, user);
					prop = clear;
					break;
				case 0:
					UnitePropreties(properties, orders, advertments);
					WriteProperties(properties);
					break;
				default: cout << "Такого пункта меню нет. Выберите другой" << endl;
				}
			} while (select_buy != 0);
		}

		else if (select == 2)
		{
			ReadProperties(properties);
			sort(properties.begin(), properties.end());
			DistributeProperties(properties, orders, advertments);

			Property prop = FindUserProperty(properties, orders, advertments, user);
			int select_sell;

			do
			{
				cout << "1) Отправить запрос на продажу\n2) Просмотреть данные о недвижимости\n3) Просмотреть статус продажи недвижимости\n4) Отменить прожажу недвижимости\n0) Назад" << endl;
				do
				{
					cin >> select_sell;
				} while (!CheckStream());
				switch (select_sell)
				{
				case 1:
					if (prop == clear)
						CreateOrder(orders, prop, user);
					else
						cout << "Запрос уже существует. Отмените запрос или завершите продажу недвижимости для создания нового запроса" << endl;
					break;
				case 2:
					cout << "|====================|==============================|===============|===================|===================|====================|===================================|" << endl;
					cout << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
						<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
					cout << "|====================|==============================|===============|===================|===================|====================|===================================|" << endl;
					prop.propertyInfo();
					cout << "|====================|==============================|===============|===================|===================|====================|===================================|" << endl;
					break;
				case 3:
					if (prop == clear)
					{
						cout << "Ошибка. Для работы с недвижимостью необходимо создать какой-либо запрос" << endl;
						break;
					}
					cout << "Статус продажи закреплённого запроса: " << prop.getStatus() << endl;
					break;
				case 4:
					int cancel;
					do
					{
						cout << "Вы уверены, что хотите отменить продажу недвижимости? В случае подтверждения данные о вашей недвижимости будут удалены\n1) Подтвердить\n2) Отменить" << endl;
						do
						{
							cin >> cancel;
						} while (!CheckStream());
						if (cancel == 1)
							CancelOrder(properties, orders, advertments, prop);
						else if (cancel != 2)
							cout << "Такого пункта меню нет. Выберите другой" << endl;
					} while (cancel != 1 && cancel != 2);
					break;
				case 0:
					UnitePropreties(properties, orders, advertments);
					WriteProperties(properties);
					break;
				default: cout << "Такого пункта меню нет. Выберите другой" << endl;
				}
			} while (select_sell != 0);

		}
		else if (select != 0)
			cout << "Такого пункта меню нет. Выберите другой" << endl;

		system("cls");
	} while (select != 0);
}

bool UserMenu(User& user)
{
	int select;
	bool exit;
	do
	{
		cout << "1) Аккаунт\n2) Недвижимость\n0) Завершить программу" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());

		if (select == 1)
		{
			exit = AccountMenu(user);
			system("cls");
			if (exit == true)
				return false;
		}

		else if (select == 2)
		{
			UserPropertyMenu(user);
			system("cls");
		}

		else if (select == 0)
			return true;
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;

	} while (select != 0);

	return false;
}

void AdminUsersMenu(vector<User>& users, vector<Admin>& admins)
{
	int change;
	do
	{
		sort(users.begin(), users.end());
		sort(admins.begin(), admins.end());
		cout << "1) Список всех пользователей\n2) Список администраторов\n3) Список заблокированных аккаунтов\n4) Заблокировать пользователя\n5) Разблокировать пользователя" << endl
			<< "0) Назад" << endl;
		do
		{
			cin >> change;
		} while (!CheckStream());
		switch (change)
		{
		case 1:
		{
			shared_ptr<User> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)users.size(); i++)
			{
				cout << "\t|" << setw(4) << i + 1;
				users[i].accountInfo();
				users[i].createPtr();
				ptr = users[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 2:
		{
			shared_ptr<Admin> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)admins.size(); i++)
			{
				cout << "\t|" << setw(4) << i + 1;
				admins[i].accountInfo();
				admins[i].createPtr();
				ptr = admins[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 3:
		{
			shared_ptr<User> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)users.size(); i++)
				if (users[i].getBlockStatus() == true)
				{
					cout << "\t|" << setw(4) << i + 1;
					users[i].accountInfo();
					users[i].createPtr();
					ptr = users[i].getPrt();
					count += ptr.use_count() - 1;
				}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 4:
			SelectBlockingAccount(users);
			break;
		case 5:
			SelectUnblockingAccount(users);
			break;
		case 0: break;
		default: cout << "Такого пункта меню нет. Выберите другой" << endl;
		}
	} while (change != 0);
}

void AdminPropertyMenu(Admin admin)
{
	int select;
	vector<Property> properties;
	vector<Property> orders;
	vector<Property> advertments;
	Property clear;

	ReadProperties(properties);
	sort(properties.begin(), properties.end());
	DistributeProperties(properties, orders, advertments);
	Property prop = FindAdminProperty(properties, orders, advertments, admin);

	do
	{
		cout << "1) Просмотреть объявления\n2) Просмотреть запросы на продажу\n3) Принять запрос на продажу\n4) Просмотреть данные о закреплённой недвижимости\n5) Просмотреть статус продажи недвижимости\n6) Отказаться от продажи" << endl
			<< "7) Создать объявление\n8) Редактировать объявление\n9) Удалить объявление\n10) Завершить продажу\n0) Вернуться в меню" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		switch (select)
		{
		case 1:
		{
			shared_ptr<Property> ptr;
			int count = 0;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			for (int i = 0; i < (int)advertments.size(); i++)
			{
				cout << "|" << setw(4) << i + 1;
				advertments[i].propertyInfo();
				advertments[i].createPtr();
				ptr = advertments[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		}
		break;
		case 2:
		{
			shared_ptr<Property> ptr;
			int count = 0;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			for (int i = 0; i < (int)orders.size(); i++)
			{
				cout << "|" << setw(4) << i + 1;
				orders[i].propertyInfo();
				orders[i].createPtr();
				ptr = orders[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		}
		break;
		case 3:
			if (prop == clear)
				AcceptOrder(orders, properties, prop, admin);
			else
				cout << "Уже имеется закреплённая недвижимость" << endl;
			break;
		case 4:
			cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			prop.propertyInfo();
			cout << "|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			break;
		case 5:
			if (prop == clear)
			{
				cout << "Ошибка. Для работы с недвижимостью необходимо принять какой-либо запрос" << endl;
				break;
			}
			cout << "Статус продажи закреплённого запроса: " << prop.getStatus() << endl;
			break;
		case 6:
			int cancel;
			do
			{
				cout << "Вы уверены, что хотите отказаться от продажи этой недвижимости?\n1) Отказаться от недвижимости\n2) Назад" << endl;
				do
				{
					cin >> cancel;
				} while (!CheckStream());
				if (cancel == 1)
				{
					CancelAdvertment(orders, advertments, prop);
					prop = clear;
				}
			} while (cancel != 1 && cancel != 2);
			break;
		case 7:
			for (int i = 0; i < (int)orders.size(); i++)
				if (orders[i] == prop)
				{
					cout << "Объявление по закреплённому запросу уже существует" << endl;
					break;
				}
			CreateAdvertment(properties, advertments, prop);
			break;
		case 8:
			EditAdvertisement(prop, advertments);
			break;
		case 9:
			if (prop == clear)
			{
				cout << "Ошибка. Для работы с недвижимостью необходимо принять какой-либо запрос" << endl;
				break;
			}

			for (int i = 0; i < (int)advertments.size(); i++)
				if (advertments[i] == prop)
				{
					advertments.erase(advertments.begin() + i);
					prop.setStatus("Создание объявления");
					break;
				}
			if (prop.getStatus() != "Создание объявления")
			{
				cout << "Объявления по закреплённому запросу не найдено" << endl;
			}
			break;
		case 10:
			for (int i = 0; i < (int)properties.size(); i++)
				if (prop == properties[i])
					properties.erase(properties.begin() + i);
			CompleteProperty(prop);
			prop = clear;
			break;
		case 0: break;
		default: cout << "Такого пункта меню нет. Выберите другой" << endl;
		}
	} while (select != 0);

	UnitePropreties(properties, orders, advertments);
	WriteProperties(properties);
}

bool AdminMenu(Admin& admin, vector<User>& users, vector<Admin>& admins)
{
	int select;
	bool exit;
	do
	{
		cout << "1) Аккаунт\n2) Пользователи\n3) Недвижимость\n0) Завершить программу" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 1)
		{
			exit = AccountMenu(admin);
			system("cls");
			if (exit == true)
				return false;
		}
		else if (select == 2)
		{
			AdminUsersMenu(users, admins);
			system("cls");
		}
		else if (select == 3)
		{
			AdminPropertyMenu(admin);
			system("cls");
		}
		else if (select == 0)
		{
			return true;
		}
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select != 0);

	return false;
}

void MainAdminPropertyMenu()
{
	int select;
	vector<Property> properties;
	vector<Property> orders;
	vector<Property> advertments;

	ReadProperties(properties);
	sort(properties.begin(), properties.end());
	DistributeProperties(properties, orders, advertments);
	do
	{
		cout << "1) Просмотреть объявления\n2) Просмотреть запросы на продажу\n3) Просмотреть недвижимости с промежуточными статусами\n4) Просмотреть архив\n0) Назад" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		switch (select)
		{
		case 1:
		{
			shared_ptr<Property> ptr;
			int count = 0;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			for (int i = 0; i < (int)advertments.size(); i++)
			{
				cout << "|" << setw(4) << i + 1;
				advertments[i].propertyInfo();
				advertments[i].createPtr();
				ptr = advertments[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		}
		break;
		case 2:
		{
			shared_ptr<Property> ptr;
			int count = 0;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			for (int i = 0; i < (int)orders.size(); i++)
			{
				cout << "|" << setw(4) << i + 1;
				orders[i].propertyInfo();
				orders[i].createPtr();
				ptr = orders[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		}
		break;
		case 3:
		{
			shared_ptr<Property> ptr;
			int count = 0;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
				<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			for (int i = 0; i < (int)properties.size(); i++)
			{
				cout << "|" << setw(4) << i + 1;
				properties[i].propertyInfo();
				properties[i].createPtr();
				ptr = properties[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
			cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
			cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		}
		break;
		case 4:
			ViewArchive();
			break;
		case 0: break;
		default: cout << "Такого пункта меню нет. Выберите другой" << endl;
		}
	} while (select != 0);
}

void MainAdminUsersMenu(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins)
{
	int change;
	do
	{
		sort(users.begin(), users.end());
		sort(admins.begin(), admins.end());
		cout << "1) Список всех пользователей\n2) Список администраторов\n3) Список заблокированных аккаунтов\n4) Заблокировать пользователя/администратора" << endl
			<< "5) Разблокировать пользователя/администратора\n6) Сменить роль аккаунта\n0) Назад" << endl;
		do
		{
			cin >> change;
		} while (!CheckStream());
		switch (change)
		{
		case 1:
		{
			shared_ptr<User> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)users.size(); i++)
			{
				cout << "\t|" << setw(4) << i + 1;
				users[i].accountInfo();
				users[i].createPtr();
				ptr = users[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 2:
		{
			shared_ptr<Admin> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)admins.size(); i++)
			{
				cout << "\t|" << setw(4) << i + 1;
				admins[i].accountInfo();
				admins[i].createPtr();
				ptr = admins[i].getPrt();
				count += ptr.use_count() - 1;
			}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 3:
		{
			shared_ptr<User> ptr;
			int count = 0;
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << "№" << "|" << setw(20) << "Логин" << "|" << endl;
			cout << "\t|====|====================|" << endl;
			for (int i = 0; i < (int)users.size(); i++)
				if (users[i].getBlockStatus() == true)
				{
					cout << "\t|" << setw(4) << i + 1;
					users[i].accountInfo();
					users[i].createPtr();
					ptr = users[i].getPrt();
					count += ptr.use_count() - 1;
				}
			cout << "\t|====|====================|" << endl;
			cout << "\t|" << setw(4) << count << "|--------------------|" << endl;
			cout << "\t|====|====================|" << endl;
		}
		break;
		case 4:
			int block;
			do
			{
				cout << "Укажите роль пользователя для блокировки:\n1) Пользователь\n2) Администратор\n0) Назад" << endl;
				do
				{
					cin >> block;
				} while (!CheckStream());
				if (block == 1)
					SelectBlockingAccount(users);
				else if (block == 2)
					SelectBlockingAccount(admins);
			} while (block < 0 || block > 2);
			break;
		case 5:
			int unblock;
			do
			{
				cout << "Укажите роль пользователя для разблокировки:\n1) Пользователь\n2) Администратор\n0) Назад" << endl;
				do
				{
					cin >> unblock;
				} while (!CheckStream());
				if (unblock == 1)
					SelectUnblockingAccount(users);
				else if (unblock == 2)
					SelectUnblockingAccount(admins);
			} while (unblock < 0 || unblock > 2);
			break;
		case 6:
			ChangeRole(main_admin, users, admins);
			break;
		case 0: break;
		default: cout << "Такого пункта меню нет. Выберите другой" << endl;
		}
	} while (change != 0);
}

bool MainAdminMenu(Main_Admin& main_admin, vector<User>& users, vector<Admin>& admins)
{
	int select;
	bool exit;
	do
	{
		cout << "1) Аккаунт\n2) Пользователи\n3) Недвижимость\n0) Завершить программу" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 1)
		{
			exit = AccountMenu(main_admin);
			system("cls");
			if (exit == true)
				return false;
		}
		else if (select == 2)
		{
			MainAdminUsersMenu(main_admin, users, admins);
			system("cls");
		}
		else if (select == 3)
		{
			MainAdminPropertyMenu();
			system("cls");
		}
		else if (select == 0)
		{
			return true;
		}
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select != 0);

	return false;
}

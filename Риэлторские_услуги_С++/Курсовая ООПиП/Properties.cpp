#include "Properties.h"

void ReadProperties(vector<Property>& properties)
{
	Property prop;
	ifstream fin("Properties.txt", ios::binary);
	if (!fin.is_open())
	{
		cout << "Файл Prorerties.txt не может быть открыт" << endl;
	}
	else
	{
		while (fin >> prop)
			properties.push_back(prop);
	}
}

void WriteProperties(vector<Property>& properties)
{
	ofstream fout;
	fout.open("Properties.txt", ios::trunc);
	fout.close();

	fout.open("Properties.txt", ios::out);
	if (!fout.is_open())
	{
		cout << "Файл Properties.txt не может быть открыт" << endl;
	}
	else
	{
		for (int i = 0; i < (int)properties.size(); i++)
			fout << properties[i];
	}
}

Property FindAdminProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, Admin realtor)
{
	int prop_num = -1;
	for (int i = 0; i < (int)properties.size(); i++)
		if (properties[i].getRealtor() == realtor.getLogin())
			return properties[i];

	for (int i = 0; i < (int)orders.size(); i++)
		if (orders[i].getRealtor() == realtor.getLogin())
			return orders[i];

	for (int i = 0; i < (int)advertments.size(); i++)
		if (advertments[i].getRealtor() == realtor.getLogin())
			return advertments[i];

	Property clear;
	return clear;
}

Property FindUserProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, User user)
{
	int prop_num = -1;
	for (int i = 0; i < (int)properties.size(); i++)
		if (properties[i].getSeller() == user.getLogin())
			return properties[i];

	for (int i = 0; i < (int)orders.size(); i++)
		if (orders[i].getSeller() == user.getLogin())
			return orders[i];

	for (int i = 0; i < (int)advertments.size(); i++)
		if (advertments[i].getSeller() == user.getLogin())
			return advertments[i];

	Property clear;
	return clear;
}

Property FindBuyerProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, User user)
{
	int prop_num = -1;
	for (int i = 0; i < (int)properties.size(); i++)
		if (properties[i].getBuyer() == user.getLogin())
			return properties[i];

	for (int i = 0; i < (int)orders.size(); i++)
		if (orders[i].getBuyer() == user.getLogin())
			return orders[i];

	for (int i = 0; i < (int)advertments.size(); i++)
		if (advertments[i].getBuyer() == user.getLogin())
			return advertments[i];

	Property clear;
	return clear;
}

void EditAdvertisement(Property& prop, vector<Property>& advertments)
{
	int select, ad_num = 0;

	for (int i = 0; i < (int)advertments.size(); i++)
		if (advertments[i] == prop)
		{
			ad_num = i;
			break;
		}

	do
	{
		cout << "Какой параметр изменить?\n1) Тип недвижимости\n2) Площать недвижимости\n3) Контактный номер клиента\n0) Выйти из меню редактирования " << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 0)
			return;
		else if (select == 1)
		{
			string temp_str;
			cout << "Укажите тип недвижимости: ";
			cin.get();
			do
			{
				getline(cin, temp_str);
			} while (!CheckStream());
			prop.setType(temp_str);
		}
		else if (select == 2)
		{
			int area;
			cout << "Введите площадь недвижимости в м^2: ";
			do
			{
				cin >> area;
			} while (!CheckStream());
			prop.setAreaSize(area);
		}
		else if (select == 3)
		{
			string temp_str, num_part;
			cout << endl << "Номер телефона\nСтрана мобильного оператора - Беларусь (+375)" << endl;
			temp_str = "+375";
			do
			{
				cout << endl << "Выберите код оператора\n1) 29\n2) 44\n3) 33\n4) 25" << endl;
				do
				{
					cin >> select;
				} while (!CheckStream());
				if (select == 1)
					temp_str += "29";
				else if (select == 2)
					temp_str += "44";
				else if (select == 3)
					temp_str += "33";
				else if (select == 4)
					temp_str += "25";
				else
					cout << "Такого пункта меню нет. Выберите другой" << endl;
			} while (select < 1 || select > 4);
			cin.get();

			do
			{
				cout << "Введите 7-ми значный номер телефона" << endl;
				do
				{
					getline(cin, num_part);
				} while (!CheckStream());
				if (num_part.size() != 7)
					cout << "Номер телефона не может иметь такое количество цифр. Повторите попытку" << endl;
			} while (num_part.size() != 7);
			temp_str += num_part;
			prop.setContactNum(temp_str);
		}
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select < 1 || select > 4);

	advertments.erase(advertments.begin() + ad_num);
	advertments.push_back(prop);
}

void CreateOrder(vector<Property>& orders, Property& prop, User user)
{
	Property temp_prop;
	string temp_str, num_part;
	int select;
	cout << "Укажите тип недвижимости: ";
	cin.get();
	do
	{
		getline(cin, temp_str);
	} while (!CheckStream());
	temp_prop.setType(temp_str);

	cout << endl << "Номер телефона\nСтрана мобильного оператора - Беларусь (+375)" << endl;
	temp_str = "+375";
	do
	{
		cout << endl << "Выберите код оператора\n1) 29\n2) 44\n3) 33\n4) 25" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select == 1)
			temp_str += "29";
		else if (select == 2)
			temp_str += "44";
		else if (select == 3)
			temp_str += "33";
		else if (select == 4)
			temp_str += "25";
		else
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select < 1 || select > 4);
	cin.get();

	do
	{
		cout << "Введите 7-ми значный номер телефона" << endl;
		do
		{
			getline(cin, num_part);
		} while (!CheckStream());
		if (num_part.size() != 7)
			cout << "Номер телефона не может иметь такое количество цифр. Повторите попытку" << endl;
	} while (num_part.size() != 7);
	temp_str += num_part;
	temp_prop.setContactNum(temp_str);
	temp_prop.setStatus("Ожидание риэлтора");
	temp_prop.setSeller(user.getLogin());
	orders.push_back(temp_prop);
	prop = temp_prop;
}

void AcceptOrder(vector<Property>& orders, vector<Property>& properties, Property& prop, Admin admin)
{
	int select;
	do
	{
		cout << "Какой запрос принять: ";
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select < 1 || select >(int)orders.size())
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select < 1 || select >(int)orders.size());

	select--;
	orders[select].setRealtorLogin(admin.getLogin());
	orders[select].setStatus("Создание объявления");
	properties.push_back(orders[select]);
	prop = orders[select];
	orders.erase(orders.begin() + select);
}

void CancelOrder(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments, Property& prop)
{
	if (prop.getAcceptStatus() == true)
	{
		cout << "Покупка недвижимости уже подтверждена. Подтверждённые запросы удалить нельзя." << endl;
		return;
	}

	Property clear;
	if (prop.getBuyer() != "-")
	{
		for (int i = 0; i < (int)properties.size(); i++)
			if (properties[i] == prop)
				properties.erase(properties.begin() + i);
	}
	else if (prop.getRealtor() != "-")
	{
		if (prop.getAreaSize() != 0)
		{
			for (int i = 0; i < (int)advertments.size(); i++)
				if (advertments[i] == prop)
					advertments.erase(advertments.begin() + i);
		}
		else
		{
			for (int i = 0; i < (int)properties.size(); i++)
				if (properties[i] == prop)
					properties.erase(properties.begin() + i);
		}
	}
	else
	{
		for (int i = 0; i < (int)orders.size(); i++)
			if (orders[i] == prop)
				orders.erase(orders.begin() + i);
	}
	prop = clear;
	cout << "Ваш запрос на продажу отменён" << endl;
}

void CreateAdvertment(vector<Property>& properties, vector<Property>& advertments, Property& prop)
{
	int area;
	for (int i = 0; i < (int)properties.size(); i++)
		if (prop == properties[i])
			properties.erase(properties.begin() + i);
	cout << "Введите площадь недвижимости в м^2: ";
	cin >> area;
	prop.setAreaSize(area);
	prop.setStatus("В списке объявлений");
	advertments.push_back(prop);
}

void CancelAdvertment(vector<Property>& orders, vector<Property>& advertments, Property prop)
{
	for (int i = 0; i < (int)advertments.size(); i++)
		if (advertments[i] == prop)
		{
			advertments.erase(advertments.begin() + i);
			break;
		}
	prop.setAreaSize(0);
	prop.setRealtorLogin("-");
	prop.setStatus("Ожидание риэлтора");
	orders.push_back(prop);
	Property clear;
	prop = clear;
}

void CompleteProperty(Property& prop)
{
	if (prop.getBuyer() == "-")
	{
		cout << "Недвижимость не имеет покупателя" << endl;
		return;
	}

	prop.setStatus("Продано");
	ofstream fout("Archive.txt", ios::binary);
	if (!fout.is_open())
	{
		cout << "Файл Archive.txt не может быть открыт" << endl;
	}
	else
	{
		fout << prop;
	}
}

void ChoosePurchase(vector<Property>& advertments, vector<Property>& properties, Property& prop, User user)
{
	int select;
	do
	{
		cout << "Выберите номер приобретаемой недвижимости из списка объявлений" << endl;
		do
		{
			cin >> select;
		} while (!CheckStream());
		if (select < 0 || select >(int)advertments.size())
			cout << "Такого пункта меню нет. Выберите другой" << endl;
	} while (select < 0 || select >(int)advertments.size());
	select--;
	advertments[select].setBuyer(user.getLogin());
	advertments[select].setStatus("Ожидание подтверждения покупателя");
	properties.push_back(advertments[select]);
	prop = advertments[select];
	advertments.erase(advertments.begin() + select);
}

void ConfirmPurchase(vector<Property>& properties, User user)
{
	for (int i = 0; i < (int)properties.size(); i++)
		if (properties[i].getBuyer() == user.getLogin())
		{
			properties[i].acceptPurchase();
			break;
		}
	cout << "Покупка успешно подтверждена" << endl;
}

void CancelPurchase(vector<Property>& advertments, vector<Property>& properties, User user)
{
	for (int i = 0; i < (int)properties.size(); i++)
		if (properties[i].getBuyer() == user.getLogin())
		{
			if (properties[i].getAcceptStatus() == true)
			{
				cout << "Покупка недвижимости уже подтверждена. Подтверждённые запросы отменить нельзя." << endl;
				return;
			}
			else
			{
				properties[i].setBuyer("-");
				properties[i].setStatus("В списке объявлений");
				advertments.push_back(properties[i]);
				properties.erase(properties.begin() + i);
			}
		}
	cout << "Выбор недвижимости отменён" << endl;
}

void ViewArchive()
{
	int count = 0;
	ifstream fin("Archive.txt", ios::binary);
	if (!fin.is_open())
	{
		cout << "Файл Archive.txt не может быть открыт" << endl;
	}
	else
	{
		Property temp;
		cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		cout << "|" << setw(4) << "№" << "|" << setw(20) << "Тип недвижимости" << "|" << setw(30) << "Контактный номер телефона" << "|" << setw(15)
			<< "Площадь в м^2" << "|" << setw(20) << "Продавец" << "|" << setw(20) << "Риэлтор" << "|" << setw(21) << "Покупатель" << "|" << setw(35) << "Статус продажи" << "|" << endl;
		cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		while (fin >> temp)
		{
			cout << "|" << setw(4) << count;
			temp.propertyInfo();
			count++;
		}
		cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
		cout << "|" << setw(4) << count << "|--------------------|------------------------------|---------------|--------------------|--------------------|---------------------|-----------------------------------|" << endl;
		cout << "|====|====================|==============================|===============|====================|====================|=====================|===================================|" << endl;
	}
}

void DistributeProperties(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments)
{
	vector<Property> temp;
	for (int i = 0; i < (int)properties.size(); i++)
	{
		if (properties[i].getStatus() == "Ожидание риэлтора")
			orders.push_back(properties[i]);
		else if (properties[i].getStatus() == "В списке объявлений")
			advertments.push_back(properties[i]);
		else
			temp.push_back(properties[i]);
	}
	properties = temp;
}

void UnitePropreties(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments)
{
	int i;
	for (i = 0; i < (int)orders.size(); i++)
		properties.push_back(orders[i]);
	for (i = 0; i < (int)advertments.size(); i++)
		properties.push_back(advertments[i]);
}
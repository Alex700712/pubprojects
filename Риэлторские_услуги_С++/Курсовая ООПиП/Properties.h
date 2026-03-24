#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <memory>
#include <typeinfo>
#include <iomanip>
#include "General.h"
#include "Users.h"


using namespace std;

class Property
{
	string type;
	string cont_num;
	string status;
	string user_login;
	string realtor_login;
	string buyer_login;
	shared_ptr<Property> ptr;
	int area_size = 0;
	bool is_accepted = false;
public:
	Property() : type("-"), cont_num("-"), status("-"), user_login("-"), realtor_login("-"), buyer_login("-"), area_size(0), is_accepted(false) {}
	void createPtr() { this->ptr = shared_ptr<Property>(new Property); }
	void setType(string type) { this->type = type; }
	void setContactNum(string num) { this->cont_num = num; }
	void setStatus(string status) { this->status = status; }
	void setAreaSize(int area_size) { this->area_size = area_size; }
	void setSeller(string login) { this->user_login = login; }
	void setRealtorLogin(string login) { this->realtor_login = login; }
	void setBuyer(string login) { this->buyer_login = login; }
	void setAcceptStatus(bool acc_status) { this->is_accepted = acc_status; }
	void acceptPurchase() { this->is_accepted = true; }
	string getType() { return this->type; }
	string getContactNum() { return this->cont_num; }
	string getStatus() { return this->status; }
	string getSeller() { return this->user_login; }
	string getRealtor() { return this->realtor_login; }
	string getBuyer() { return this->buyer_login; }
	int getAreaSize() { return this->area_size; }
	bool getAcceptStatus() { return this->is_accepted; }
	shared_ptr<Property> getPrt() { return this->ptr; }
	void propertyInfo()
	{

		cout << "|" << setw(20) << this->type << "|" << setw(30) << this->cont_num << "|" << setw(15) << this->area_size << "|" << setw(20) << this->user_login
			<< "|" << setw(20) << this->realtor_login << "|" << setw(21) << this->buyer_login << "|" << setw(35) << this->status << "|" << endl;
	}
	friend ofstream& operator<<(ofstream& fout, Property property)
	{
		fout << property.getType() << '\t' << property.getContactNum() << '\t' << property.getStatus() << '\t' << property.getSeller() << '\t' << property.getRealtor()
			<< '\t' << property.getBuyer() << '\t' << property.getAreaSize() << '\t' << property.getAcceptStatus() << endl;
		return fout;
	}
	friend ifstream& operator>>(ifstream& fin, Property& property)
	{
		string temp_str;
		int temp_int;
		bool temp_bool;
		fin >> temp_str;
		property.setType(temp_str);
		fin >> temp_str;
		property.setContactNum(temp_str);
		fin.get();
		getline(fin, temp_str, '\t');
		property.setStatus(temp_str);
		fin >> temp_str;
		property.setSeller(temp_str);
		fin >> temp_str;
		property.setRealtorLogin(temp_str);
		fin >> temp_str;
		property.setBuyer(temp_str);
		fin >> temp_int;
		property.setAreaSize(temp_int);
		fin >> temp_bool;
		property.setAcceptStatus(temp_bool);
		return fin;
	}
	friend bool operator==(Property prop1, Property prop2)
	{
		if (prop1.type != prop2.type)
			return false;
		else if (prop1.cont_num != prop2.cont_num)
			return false;
		else if (prop1.status != prop2.status)
			return false;
		else if (prop1.realtor_login != prop2.realtor_login)
			return false;
		else if (prop1.buyer_login != prop2.buyer_login)
			return false;
		else if (prop1.area_size != prop2.area_size)
			return false;
		else if (prop1.is_accepted != prop2.is_accepted)
			return false;
		else return true;
	}
	Property& operator=(const Property& prop1)
	{
		if (this != &prop1)
		{
			type = prop1.type;
			cont_num = prop1.cont_num;
			status = prop1.status;
			user_login = prop1.user_login;
			realtor_login = prop1.realtor_login;
			buyer_login = prop1.buyer_login;
			area_size = prop1.area_size;
			is_accepted = prop1.is_accepted;
		}
		return*this;
	}
	friend bool operator>(Property prop1, Property prop2)
	{
		if (prop1.type > prop2.type)
			return true;
		else return false;
	}
	friend bool operator<(Property prop1, Property prop2)
	{
		if (prop1.type < prop2.type)
			return true;
		else return false;
	}
};

void ReadProperties(vector<Property>& properties);

void WriteProperties(vector<Property>& properties);

Property FindAdminProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, Admin realtor);

Property FindUserProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, User user);

Property FindBuyerProperty(vector<Property> properties, vector<Property> orders, vector<Property> advertments, User user);

void EditAdvertisement(Property& prop, vector<Property>& advertments);

void CreateOrder(vector<Property>& orders, Property& prop, User user);

void AcceptOrder(vector<Property>& orders, vector<Property>& properties, Property& prop, Admin admin);

void CancelOrder(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments, Property& prop);

void CreateAdvertment(vector<Property>& properties, vector<Property>& advertments, Property& prop);

void CancelAdvertment(vector<Property>& orders, vector<Property>& advertments, Property prop);

void CompleteProperty(Property& prop);

void ChoosePurchase(vector<Property>& advertments, vector<Property>& properties, Property& prop, User user);

void ConfirmPurchase(vector<Property>& properties, User user);

void CancelPurchase(vector<Property>& advertments, vector<Property>& properties, User user);

void ViewArchive();

void DistributeProperties(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments);

void UnitePropreties(vector<Property>& properties, vector<Property>& orders, vector<Property>& advertments);

template<class T>
void SelectBlockingAccount(vector<T>& accounts)
{
	int block;
	do
	{
		cout << "Введите номер блокируемого аккаунта" << endl;
		do
		{
			cin >> block;
		} while (!CheckStream());
		if (block < 1 || block >(int)accounts.size())
			cout << "Такого пункта меню нет. Выберите другой" << endl;
		else
		{
			vector<Property> properties;
			ReadProperties(properties);

			accounts[--block].blockAccount();
			for (int i = 0; i < (int)properties.size(); i++)
			{
				if (typeid(accounts[block]).name() == "class User")
				{
					if (accounts[block].getLogin() == properties[i].getSeller())
						properties.erase(properties.begin() + i);
					else if (accounts[block].getLogin() == properties[i].getBuyer())
					{
						properties[i].setBuyer("-");
						properties[i].setStatus("В списке объявлений");
					}
				}

				else if (typeid(accounts[block]).name() == "class Admin")
					if (accounts[block].getLogin() == properties[i].getRealtor())
					{
						properties[i].setRealtorLogin("-");
						properties[i].setStatus("Ожидание риэлтора");
					}
			}

			WriteProperties(properties);

			cout << "Аккаунт пользователя " << accounts[block].getLogin() << " заблокирован" << endl;
			block++;
		}

	} while (block < 1 || block >(int)accounts.size());
}

template<class T>
void SelectUnblockingAccount(vector<T>& accounts)
{
	int unblock;
	do
	{
		cout << "Введите номер аккаунта для разблокировки" << endl;
		do
		{
			cin >> unblock;
		} while (!CheckStream());
		if (unblock < 1 || unblock >(int)accounts.size())
			cout << "Такого пункта меню нет. Выберите другой" << endl;
		else
		{
			accounts[--unblock].unblockAccount();
			cout << "Аккаунта пользователя " << accounts[unblock].getLogin() << "разблокирован" << endl;
			unblock++;
		}
	} while (unblock < 1 || unblock >(int)accounts.size());
}

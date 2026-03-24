#pragma once

#include <iostream>
#include <string>
#include <typeinfo>
#include <conio.h>
#include <vector>
#include "General.h"
#include "Users.h"
#include "Menu.h"

using namespace std;

void Registration(vector<User>& users, vector<Admin>& admins, Main_Admin main_admin);

bool Autorization(vector<User>& users, vector<Admin>& admins, Main_Admin& main_admin);

void StartMenu();
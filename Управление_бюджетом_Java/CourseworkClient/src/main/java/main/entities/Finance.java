package main.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static main.enums.RequestType.FIND_ALL_DEPARTMENTS;
import static main.enums.RequestType.FIND_FINANCE;

public class Finance
{
    private int financeId;

    private String direction;

    private int money;

    private Date date;

    private String description;

    private Department department;

    public Finance() {}

    public Finance(String direction, int money, Date date)
    {
        this.direction = direction;
        this.money = money;
        this.date = date;
        this.setDepartment(null);
    }

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() { return new SimpleDateFormat("dd.MM.yyyy").format(this.date); }


    public int getDepartmentId() {
        if(this.department != null)
            return this.department.getDepartmentId();
        else return -1;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void cutRelation()
//    {
//        this.department = null;
//    }
//
//    public void findRelation() throws IOException {
//        Type departmentListType = new TypeToken<ArrayList<Department>>() {}.getType();
//        List<Department> departments =(new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_DEPARTMENTS), departmentListType));
//        for(Department dep: departments)
//        {
//            Finance finance = new GetService<>(Finance.class).GetEntityById(FIND_FINANCE, String.valueOf(this.getFinanceId()));
//            if(finance.getDepartment().getDepartmentId() == dep.getDepartmentId()) {
//                this.department = dep;
//                break;
//            }
//        }
//    }
}
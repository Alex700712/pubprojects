package entities;

import jakarta.persistence.*;
import service.ClientService;
import service.DepartmentService;
import service.FinanceService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "finance")
public class Finance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finance_id", nullable = false)
    private int financeId;

    @Column(name = "direction", length = 45)
    private String direction;

    @Column(name = "money")
    private int money;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    public Finance() {}

    public Finance(String direction, int money, Date date)
    {
        this.direction = direction;
        this.money = money;
        this.date = date;
        this.department = null;
    }

    public int getFinanceId() {
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

    public Department getDepartments() {
        return department;
    }

    public void setDepartments(Department department) { this.department = department; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void cutRelation()
    {
        this.department = null;
    }

//    public void findRelation()
//    {
//        List<Department> departments = new DepartmentService().findAllEntities();
//        for(Department dep: departments)
//        {
//            Finance finance = new FinanceService().findEntity(this.getFinanceId());
//            if(finance.getDepartments().getDepartmentId() == dep.getDepartmentId()) {
//                this.department = dep;
//                break;
//            }
//        }
//    }
}

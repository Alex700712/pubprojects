package main.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.connection.GetService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static main.enums.RequestType.*;

public class Employee {

    private int employeeId;
    private String name;

    private User user;
    private Post post;
    private Department department;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public Post getPosts() {
        return post;
    }

    public void setPosts(Post post) {
        this.post = post;
    }

    public Department getDepartments() {
        return department;
    }

    public void setDepartments(Department department) {
        this.department = department;
    }

    public int getUserId() {
        if (this.user != null)
            return this.user.getUserId();
        else return -1;
    }


    public int getPostId() {
        if (this.post != null)
            return this.post.getPostId();
        else return -1;
    }

    public int getDepartmentId() {
        if (this.department != null)
            return this.department.getDepartmentId();
        else return -1;
    }

    public void cutRelation() {
        this.user = null;
    }

    public void findRelation() {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        List<User> users = (new Gson().fromJson(new GetService<>(User.class)
                .GetEntities(FIND_ALL_USERS), userListType));
        for (User user : users) {
            if (user.getEmployee().getEmployeeId() == this.getEmployeeId() ||
                    user.getEmployee() == null) {
                this.setUsers(user);
                break;
            }
        }

//        Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
//        List<Post> posts =(new Gson().fromJson(new GetService<>(Post.class)
//                .GetEntities(FIND_ALL_POSTS), postListType));
//        for(Post post : posts)
//        {
//            if(post.getEmployees().contains(this) ||
//                    post.getEmployees() == null) {
//                this.setPosts(post);
//                break;
//            }
//        }
//
//        Type departmentListType = new TypeToken<ArrayList<Department>>() {}.getType();
//        List<Department> departments =(new Gson().fromJson(new GetService<>(Client.class)
//                .GetEntities(FIND_ALL_DEPARTMENTS), departmentListType));
//        for(Department dep : departments)
//        {
//            if(dep.getEmployees().contains(this) ||
//                    dep.getEmployees() == null) {
//                this.setDepartments(dep);
//                break;
//            }
//        }
    }
}


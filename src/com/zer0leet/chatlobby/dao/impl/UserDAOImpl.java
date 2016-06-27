package com.zer0leet.chatlobby.dao.impl;

import com.zer0leet.chatlobby.dao.UserDAO;
import com.zer0leet.chatlobby.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {
    }

    @Override
    public User login(String userName, String password) {
        for (User u : getAll()) {
            if (u.getUserName().equalsIgnoreCase(userName) && u.getPassword().equalsIgnoreCase(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1", "123", true));
        userList.add(new User(2, "user2", "1234", true));
        userList.add(new User(3, "user3", "12345", true));
        userList.add(new User(4, "user4", "321", true));
        userList.add(new User(5, "user5", "4321", true));
        userList.add(new User(6, "user6", "54321", true));
        userList.add(new User(7, "Manish", "shakya", true));
        userList.add(new User(8, "Prince", "Thakuri", true));
        userList.add(new User(9, "Prajwol", "Gautam", true));
        

        return userList;

    }

}

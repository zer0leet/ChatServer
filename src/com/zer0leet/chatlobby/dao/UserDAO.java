/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zer0leet.chatlobby.dao;

import com.zer0leet.chatlobby.entity.User;
import java.util.List;

/**
 *
 * @author Deepika
 */
public interface UserDAO {
      User login(String userName, String password); // Returns user u
    
    List<User> getAll(); // Returns ArrayList of registered users
}

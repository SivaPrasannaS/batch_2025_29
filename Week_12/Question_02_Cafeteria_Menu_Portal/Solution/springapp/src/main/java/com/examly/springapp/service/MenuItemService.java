package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.MenuItem;

public interface MenuItemService {

    List<MenuItem> getAllMenuItems();

    MenuItem getMenuItemById(Long id);

    MenuItem addMenuItem(MenuItem menuItem);
}

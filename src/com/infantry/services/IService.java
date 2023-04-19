/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

import java.util.List;

/**
 *
 * @author dorraayari
 */
//T retourne  un seul objet
public interface IService<T> {
    void insert(T t);
     boolean edit(T t);
          boolean add(T t);

    void delete(int coachId);
    void update(T t);
    List<T>readAll();
    T readById(int id);
}

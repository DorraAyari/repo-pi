/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mrdje
 */
public interface IServiceP<T> {
    void ajouter(T t) throws SQLException ;

    void modifier(T t) throws SQLException ;

    void supprimer(T t) throws SQLException ;

    List<T> recuperer() throws SQLException ;
}

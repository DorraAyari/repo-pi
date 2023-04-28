package com.infantry.services;

import com.infantry.entities.User;
import com.infantry.utils.DatabaseConnection;
import com.mysql.cj.xdevapi.JsonString;
import de.mkammerer.argon2.Argon2Factory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private static UserService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public UserService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User checkUser(String email, String password) {

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE `email` LIKE ?");

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("numero"),
                        resultSet.getString("photo")

                );

                if (checkPassword(password, user.getPassword())) {
                    return user;
                }

            }
        } catch (SQLException e) {
            System.out.println("Aucun email : " + e.getMessage());
        }

        return null;
    }

    private Boolean checkPassword(String password, String passwordFromDatabase) {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id).verify(
                passwordFromDatabase,
                password.toCharArray()
        );
    }

    private String encodePassword(String password) {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id).hash(4, 65536, 1, password.toCharArray());
    }

    public List<User> getAll() {
        List<User> listUser = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listUser.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles"),
                        resultSet.getString("password"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("numero"),
                        resultSet.getString("photo")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) user : " + exception.getMessage());
        }
        return listUser;
    }


    public boolean checkExist(User user) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user` WHERE `email` = ?");

            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (getAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(User user) {

        if (checkExist(user)) {
            return false;
        }

        String request = "INSERT INTO `user`(`email`, `roles`, `password`, `nom`, `prenom`, `numero`, `photo`) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, user.getEmail());
            String rolesJson = String.valueOf(new JsonString().setValue(user.getRoles()));
            preparedStatement.setString(2, rolesJson);
            preparedStatement.setString(3, encodePassword(user.getPassword()));
            preparedStatement.setString(4, user.getNom());
            preparedStatement.setString(5, user.getPrenom());
            preparedStatement.setString(6, user.getNumero());
            preparedStatement.setString(7, user.getPhoto());

            preparedStatement.executeUpdate();
            System.out.println("User added");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) user : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(User user) {

        String request = "UPDATE `user` SET `email` = ?, `roles` = ?, `nom` = ?, `prenom` = ?, `numero` = ?, `photo` = ? WHERE `id`=" + user.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, user.getEmail());
            String rolesJson = String.valueOf(new JsonString().setValue(user.getRoles()));
            preparedStatement.setString(2, rolesJson);
            preparedStatement.setString(3, user.getNom());
            preparedStatement.setString(4, user.getPrenom());
            preparedStatement.setString(5, user.getNumero());
            preparedStatement.setString(6, user.getPhoto());

            preparedStatement.executeUpdate();
            System.out.println("User edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) user : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `user` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("User deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) user : " + exception.getMessage());
        }
        return false;
    }
}

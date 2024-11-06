package db_sys;

//interaction with MYSQL database.

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {

    //db configuration
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "8%g4J0Q=r/Yg2X80{!z>";


    public static User validateLogin(String username, String password) {
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            //parameter index refers to ?
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            //execute query and store it in result set
            ResultSet resultSet = preparedStatement.executeQuery();

            //next() returns true or false
            //true - query returned data and result set now points to the first row
            //false query returned no data and result set equals to null
            if(resultSet.next()){

                int userId = resultSet.getInt("idusers");

                String balanceStr = resultSet.getString("current_balance");
                BigDecimal currentBalance = (balanceStr != null) ? new BigDecimal(balanceStr) : BigDecimal.ZERO;


                return new User(userId,username,password,currentBalance);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //not valid user
        return  null;
    }


    public static boolean register (String username,String password) {
        try{
            if(!checkUser(username)) {
                Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (username, password,current_balance) VALUES (?, ?, ?)"
                );

                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                preparedStatement.setBigDecimal(3,BigDecimal.ZERO);
                preparedStatement.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //returns false if username does not exists.
    //returns true if username exists.
    private static boolean checkUser(String username){
        try {
            Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    //return true -- insertion was successful.
    //return false -- insertion was not successful.
    public static boolean addTransactionToDatabase(Transaction transaction) {
        try{
            Connection connection =  DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement insertTransaction = connection.prepareStatement("INSERT transactions(user_id," +
                    " transaction_type,transaction_amount,transaction_date)" +
                    "VALUES(?,?,?, NOW())");

            //NOW() will put in the current date

            insertTransaction.setInt(1,transaction.getUSER_ID());
            insertTransaction.setString(2,transaction.getTRANSACTION_TYPE());
            insertTransaction.setBigDecimal(3,transaction.getTRANSACTION_AMOUNT());


            insertTransaction.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try{
            Connection connection =  DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE idusers = ?"
            );

            updateBalance.setBigDecimal(1,user.getCurrentBalance());
            updateBalance.setInt(2,user.getId());

            updateBalance.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            }

        return false;
    }


    //return true if transfer is successful
    //return false otherwise
    public static boolean transfer(User user, String transferredUsername, float transferAmount){

        try{
            Connection connection =  DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement queryUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            queryUser.setString(1,transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();


            while(resultSet.next()){
                User transferredUser = new User(
                                resultSet.getInt("idusers"),transferredUsername,
                                resultSet.getString("password"),
                                resultSet.getBigDecimal("current_balance"));

                Transaction transactionTransfer = new Transaction(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null
                );

                Transaction receivedTransaction = new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null
                );

                //update transfer user
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);

                //update user current balance
                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);

                addTransactionToDatabase(transactionTransfer);
                addTransactionToDatabase(receivedTransaction);

                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static ArrayList<Transaction> getPastTransactions(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement selectAllTransaction = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?"
            );

            selectAllTransaction.setInt(1,user.getId());

            ResultSet resultSet = selectAllTransaction.executeQuery();

            //iterete throughout the results
            while(resultSet.next()) {
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date")
                );
                pastTransactions.add(transaction);
            }



        } catch (SQLException e){
            e.printStackTrace();
        }

        return pastTransactions;
    }
}

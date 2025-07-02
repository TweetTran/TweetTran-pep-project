package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// import org.eclipse.jetty.server.ConnectionFactory;
// import org.junit.runners.model.Statement;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.Statement;

public class AccountDAO implements AccountDAOInterface{

    // check username in database 
    public Boolean searchByUserName(String username){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = ("SELECT * FROM account WHERE username = ?");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //return true if the username exist 
        }
        catch (Exception e){
            return false; // return false if username does not exist
        }
    }
    // check uaccount ID in the database 
    public Boolean searchByAccountID(int account_id){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = ("SELECT * FROM account WHERE account_id= ?");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,account_id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //return true if the account exist 
        }
        catch (Exception e){
            return false; // return false if account does not exist
        }
    }
    
    // implement interface dao method
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // implement interface dao method
    @Override
    public Account searchAccount(String username, String password) {
        // TODO Auto-generated method stub
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password")
            );
            return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // implement interface dao method
    @Override
    public Account createAccount(Account account) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    account.setAccount_id(rs.getInt(1));
                    return account;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
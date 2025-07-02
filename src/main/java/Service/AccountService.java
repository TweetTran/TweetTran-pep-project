package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();

    // account register method
    public Account accountRegister(Account account){
        if (account.getUsername() == null || account.getUsername().isBlank()){
            return null;
        }
        if (account.getPassword() == null || account.getPassword().isBlank()|| account.getPassword().length() < 4){
            return null; 
        }
        // if the account exist and return from dao is true then return null
        if (accountDAO.searchByUserName(account.getUsername())){
            return null;
        }
        return accountDAO.createAccount(account);
    }

    // loggin in method
    public Account login(String username, String password) {
        return accountDAO.searchAccount(username, password);
    }
}

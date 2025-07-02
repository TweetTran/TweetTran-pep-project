package DAO;

import java.util.List;
import Model.Account;

public interface AccountDAOInterface {

    List<Account> getAllAccounts();
    Account searchAccount(String username, String password);
    Account createAccount(Account account);

}
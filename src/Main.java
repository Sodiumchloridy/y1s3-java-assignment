import java.util.ArrayList;
import java.util.Scanner;
import account.*;

import ui.LoginPage;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<UserAccount> users;
        LoginPage.printChoice();
<<<<<<< HEAD:src/GeneralSRC/Main.java
        int choice = LoginPage.chooseChoice();

        switch(choice)
        {
            case 1:
                ArrayList<Account> accounts;
                for(int i = 0; i < users.size(); i++)
                {
                    accounts.add(users.get(i));
                }
                UserAccount.login(accounts);
            case 2:
                users.add(UserAccount.register());
            case 3:
            case 4:
        }
=======
        LoginPage.chooseChoice(input);
>>>>>>> de02b6f2023db24b66cd0ee64525621f85e4c359:src/Main.java
    }
}
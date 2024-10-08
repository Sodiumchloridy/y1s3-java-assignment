import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import account.*;
import movie.*;
import report.*;
import ui.*;
import ui.booking.*;
import ui.systemAdmin.*;
import util.*;
import color.Color;

/**
 * Main method of the TVG Cinemas program. This method initializes the program by creating a Scanner
 * object, retrieving the user ,administrator accounts and movies from the data files.
 * 
 * @param args command line arguments (not used)
 */
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Fetch data from files
        ArrayList<UserAccount> users = UserAccount.getUsers();
        ArrayList<SystemAdminAccount> admins = SystemAdminAccount.getAdmins();
        ArrayList<Movie> movieList = MovieCRUDGeneralPage.getMovieList();

        boolean resumeProgram = true;
        int userIdx;
        while (resumeProgram) {

            boolean resumeMainMenu = true;
            LoginPage.printChoice(input);
            int choice = LoginPage.chooseChoice(input);

            if (choice == 1) // login
            {
                try {
                    Util.clearConsole(input);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<Account> accounts = new ArrayList<Account>();
                for (int i = 0; i < users.size(); i++) // convert UserAccount to Account type for
                                                       // verifying users because it only accept
                                                       // account type
                {
                    accounts.add(users.get(i));
                }
                userIdx = UserAccount.login(accounts);

                if (userIdx == -1) // back
                    continue;
                else if (userIdx == -2) // quit
                    break;

                while (resumeMainMenu) {
                    try {
                        Util.clearConsole(input);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    UserMainMenu.printMovies(movieList, userIdx, users);
                    UserMainMenu.printUserAction();
                    choice = UserMainMenu.chooseUserAction(input); // -1 means re-run main

                    if (choice == 5) // exit the program
                    {
                        resumeMainMenu = false;
                        resumeProgram = false;
                    } else if (choice == -1) // go back login page
                    {
                        resumeMainMenu = false;
                        userIdx = 0;
                    } else if (choice == 1) // view movie info
                    {
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        UserMainMenu.printMovies(movieList, userIdx, users);
                        SearchMoviePage searchMoviePage =
                                new SearchMoviePage(users, userIdx, input);
                        searchMoviePage.searchMovie();
                    } else if (choice == 2) // view bookings
                    {
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        BookingPage bookingPage = new BookingPage(users, userIdx, input);
                        bookingPage.display();
                    } else if (choice == 3) // view user profile
                    {
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        UserProfilePage profile = new UserProfilePage(users, userIdx, input);
                        profile.printUserInfo();
                    } else if (choice == 4) // view cinema location
                    {
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        CinemaLocationPage cinema = new CinemaLocationPage(userIdx, users, input);
                        cinema.printCinema();
                    }
                }

            } else if (choice == 2) // register
            {
                try {
                    Util.clearConsole(input);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                UserAccount user = UserAccount.register(input);
                if (user != null) {
                    users.add(user);
                    UserAccount.saveUsers(users);
                    SystemMessage.successMessage(5, input);
                }
            } else if (choice == 3) // login as admin
            {
                try {
                    Util.clearConsole(input);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<Account> adminAccounts = new ArrayList<Account>();
                for (int i = 0; i < admins.size(); i++) // convert SystemAdminAccount to Account
                                                        // type for verifying admins because it only
                                                        // accept account type
                {
                    adminAccounts.add(admins.get(i));
                }
                userIdx = SystemAdminAccount.login(adminAccounts);
                SystemAdminAccount admin;

                if (userIdx == -1) // back
                    continue;
                else if (userIdx == -2) // quit
                    break;
                else {
                    admin = (SystemAdminAccount) adminAccounts.get(userIdx);
                }
                while (resumeMainMenu) {
                    // admin page
                    try {
                        Util.clearConsole(input);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    CommonIcon.printAdminHeader(admin);
                    MainMenu adminMenu = new MainMenu();
                    adminMenu.printAdminAction();
                    choice = adminMenu.chooseAdminAction(input);

                    if (choice == 5) {
                        resumeMainMenu = false;
                        resumeProgram = false;
                    } else if (choice == -1) {
                        resumeMainMenu = false;
                        userIdx = 0;
                    } else if (choice == 1) {
                        // Manage Movies
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        MovieCRUDGeneralPage movieCRUDGeneralPage = new MovieCRUDGeneralPage(input);
                        movieCRUDGeneralPage.MainPage();
                    } else if (choice == 2) {
                        // Manage User
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        ManageUserPage manageUserPage = new ManageUserPage();
                        manageUserPage.adminManageUserPage(admin, users, input);
                    } else if (choice == 3) {
                        // Manage Bookings
                        try {
                            Util.clearConsole(input);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        userIdx = SystemAdminAccount.accessUser(users, input);
                        if (userIdx == -1) // back
                            continue;
                        else if (userIdx == -2) // quit
                            CommonIcon.adminQuit(input);
                        else {
                            BookingPage bookingPage = new BookingPage(users, userIdx, input);
                            bookingPage.display();
                        }
                    } else if (choice == 4) {
                        // Generate Report
                        ArrayList<Report> data = new ArrayList<Report>();
                        Report.generateTicketSalesReport(users, data);
                        GenerateReportPage.printReport(users, data, input);
                    }
                }
            } else if (choice == 4) // exit
                resumeProgram = false;
        }

        try {
            Util.clearConsole(input);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // End of program
        CommonIcon.printHeader();
        input.close();
        System.out.print(Color.LIME);
        System.out.println("Thank you for using TVG Cinemas.");
        System.out.println("Vist Us Next Time.");
        System.out.print(Color.RESET);
    }
}

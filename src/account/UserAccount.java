package account;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.lang.reflect.Type;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import booking.Booking;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializationContext;

import color.Color;
import util.*;

public class UserAccount extends Account {
  // instance variables
  private ArrayList<Booking> bookings;


  /**
   * Returns the list of bookings associated with the user account.
   * 
   * @return the list of bookings associated with the user account
   */
  public ArrayList<Booking> getBookings() {
    return bookings;
  }

  /**
   * Sets the list of bookings associated with the user account.
   * 
   * @param bookings the list of bookings to set
   */
  public void setBookings(ArrayList<Booking> bookings) {
    this.bookings = bookings;
  }

  // overloaded constructor
  public UserAccount(String accountId, String name, String password, String registerDate,
      String email, String phoneNo) {
    super(accountId, name, password, registerDate, email, phoneNo);
    this.bookings = new ArrayList<Booking>();
  }

  public UserAccount(String accountId, String name, String password, String registerDate,
      ArrayList<Booking> bookings, String email, String phoneNo) {
    super(accountId, name, password, registerDate, email, phoneNo);
    setBookings(bookings);
  }

  // static methods
  /**
   * Create a new UserAccount
   */
  public static UserAccount register(Scanner input) {
    CommonIcon.printHeader();
    System.out.println("Register: \n");
    // entering details
    String id = generateAccountId();
    String username2;
    do{
      System.out.print("Enter your name (':b' to back, ':q to quit'): ");
      username2 = input.nextLine();
  
      if (Validation.isBack(username2))
        return null;
      if (Validation.isQuit(username2)) {
        try {
          Util.clearConsole(input);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
        CommonIcon.printHeader();
        input.close();
        System.out.print(Color.LIME);
        System.out.println("Thank you for using TVG Cinemas.");
        System.out.println("Vist Us Next Time.");
        System.out.print(Color.RESET);
        System.exit(0);
      }

      if(Validation.isDuplicateUsername(username2))
        SystemMessage.errorMessage(23, input);
    }while(Validation.isDuplicateUsername(username2));

    System.out.print("Enter your password (':b' to back, ':q to quit'): ");
    String pass = input.next();

    if (Validation.isBack(pass))
      return null;
    if (Validation.isQuit(pass)) {
      try {
        Util.clearConsole(input);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
      CommonIcon.printHeader();
      input.close();
      System.out.print(Color.LIME);
      System.out.println("Thank you for using TVG Cinemas.");
      System.out.println("Vist Us Next Time.");
      System.out.print(Color.RESET);
      System.exit(0);
    }

    String email2;
    do{
      System.out.print("Enter your email (':b' to back, ':q to quit'): ");
      email2 = input.next();
  
      if (Validation.isBack(email2))
        return null;
      if (Validation.isQuit(email2)) {
        try {
          Util.clearConsole(input);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
        CommonIcon.printHeader();
        input.close();
        System.out.print(Color.LIME);
        System.out.println("Thank you for using TVG Cinemas.");
        System.out.println("Vist Us Next Time.");
        System.out.print(Color.RESET);
        System.exit(0);
      }
      if(!Validation.isValidEmail(email2))
      {
        SystemMessage.errorMessage(21, input);
      }

    }while(!Validation.isValidEmail(email2));

    String phoneNo2;
    do{
      System.out.print("Enter your phone number (':b' to back, ':q to quit'): ");
      phoneNo2 = input.next();
  
      if (Validation.isBack(phoneNo2))
        return null;
      if (Validation.isQuit(phoneNo2)) {
        try {
          Util.clearConsole(input);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
        CommonIcon.printHeader();
        input.close();
        System.out.print(Color.LIME);
        System.out.println("Thank you for using TVG Cinemas.");
        System.out.println("Vist Us Next Time.");
        System.out.print(Color.RESET);
        System.exit(0);
      }

      if(!Validation.isValidMalaysiaPhoneNumber(phoneNo2))
      {
        SystemMessage.errorMessage(22, input);
      }
    }while(!Validation.isValidMalaysiaPhoneNumber(phoneNo2));

    // generating simple date formate in string
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dateString = simpleDateFormat.format(date);

    UserAccount user = new UserAccount(id, username2, pass, dateString, email2, phoneNo2);

    return user;
  }

  /**
   * Returns a list of all user accounts stored in the system.
   *
   * @return a list of all user accounts stored in the system
   */
  public static ArrayList<UserAccount> getUsers() {
    Scanner input = new Scanner(System.in);
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
          @Override
          public LocalDateTime deserialize(JsonElement json, Type typeOfT,
              JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
          }
        }).create();
    Type type = new TypeToken<ArrayList<UserAccount>>() {}.getType();

    String line = "";
    try {
      File inFile = new File("src\\resources\\user.json");
      Scanner inputFile = new Scanner(inFile);
      while (inputFile.hasNextLine()) {
        line = inputFile.nextLine();
      }
      inputFile.close();
    } catch (IOException e) {
      SystemMessage.errorMessage(4, input);
    }

    ArrayList<UserAccount> userList = gson.fromJson(line, type);
    if (userList == null) {
      userList = new ArrayList<UserAccount>();
    }
    return userList;
  }

  /**
   * Saves the list of user accounts to a file in JSON format.
   *
   * @param users the list of user accounts to save
   */
  public static void saveUsers(ArrayList<UserAccount> users) {
    Scanner scanner = new Scanner(System.in);
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
          @Override
          public JsonElement serialize(LocalDateTime src, Type typeOfSrc,
              JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
          }
        }).create();
    String toWrite = gson.toJson(users);

    try {
      File outFile = new File("src\\resources\\user.json");
      PrintWriter outputFile = new PrintWriter(outFile);
      outputFile.println(toWrite);
      outputFile.close();
    } catch (FileNotFoundException error) {
      SystemMessage.errorMessage(4, scanner);
    }
  }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.beust.jcommander.JCommander;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

// main class to process commands and communicate with the database
public class Driver {

  // replace url with your local address of the imported database
  private static final String url = "jdbc:mysql://127.0.0.1:3306/cook_me?allowMultiQueries=true&useSSL=false";
  // replace the user with root id
  private static final String user = "root";
  // replace password with root password
  private static final String password = "sesame";

  // JDBC variables for opening and managing connections
  private static Connection con;
  private static Connection con1;
  private static Connection con2;
  private static Statement stmt;
  private static Statement stmt1;
  private static Statement stmt2;
  private static ResultSet rs;
  private static ResultSet rs1;
  private static ResultSet rs2;

  // main method to run program
  public static void main(String args[]) throws SQLException {
    // arguments class added
    Args params = new Args();
    // queries class added
    Query queries = new Query();

    // creates jcommander object
    JCommander jc = JCommander.newBuilder()
        .addObject(params)
        .build();

    // parses inputs
    jc.parse(args);

    con = DriverManager.getConnection(url, user, password);
    stmt = con.createStatement();
    con1 = DriverManager.getConnection(url, user, password);
    stmt1 = con1.createStatement();
    con2 = DriverManager.getConnection(url, user, password);
    stmt2 = con2.createStatement();

    System.out.println("(input --help for more information)");

    // prints out helpful information for using the program
    if (params.help) {
      jc.usage();
      System.exit(0);
    }
    // calls query for minimal ingredients recipes
    if (params.minimal) {
      rs = stmt.executeQuery(queries.searchRMinIng());
    }
    // calls query for least active time recipes
    if (params.activeTime) {
      rs = stmt.executeQuery(queries.searchRatime());
    }
    // calls query for least total time recipes
    if (params.totalTime) {
      rs = stmt.executeQuery(queries.searchRTtime());
    }
    // calls query for cheapest recipes
    if (params.cheap) {
      rs = stmt.executeQuery(queries.searchRCheap());
    }
    // calls query for most popular recipes
    if (params.popular) {
      rs = stmt.executeQuery(queries.searchRrating());
    }
    // calls query to search for recipes with input ingredients
    if (params.ingredients != null) {
      rs = stmt.executeQuery(queries.ingredientSearch(params.ingredients));
    }
    // calls query to display detailed recipe information
    if (params.chooseRecipe != null) {
      rs = stmt.executeQuery(queries.chooseRecipe1(params.chooseRecipe));
      rs1 = stmt1.executeQuery(queries.chooseRecipe2(params.chooseRecipe));
    }
    // calls query to display ingredient information
    if (params.ingredient != null) {
      rs = stmt.executeQuery(queries.seeIngredient(params.ingredient));
    }
    // updates the database to add a user, if exists do nothing
    if (params.user != null) {
      try {
        stmt.executeUpdate(queries.addUser(params.user));
        System.out.println(params.user + " has been added!");
      } catch (MySQLIntegrityConstraintViolationException e) {
        System.out.println(e.getMessage());
      }
    }
    // updates the database to add a rating by a user for a recipe, if exists prints out error msg
    if (params.rating != null) {
      try {
        stmt.executeUpdate(queries.addRating
            (params.rating.get(0), params.rating.get(1), params.rating.get(2)));
        System.out.println(params.rating.get(0) + " has rated " + params.rating.get(1) + " a " +
            params.rating.get(2) + "!");
      } catch (MySQLIntegrityConstraintViolationException e) {
        System.out.println(e.getMessage());
      }
    }
    // calls query to find recipes matching the input
    if (params.searchRecipe != null) {
      rs = stmt.executeQuery(queries.searchRecipeName(params.searchRecipe));
    }
    // calls query to find recipes that have the most of the given macro
    if (params.macro != null) {
      rs = stmt.executeQuery(queries.searchMacro(params.macro));
    }
    // updates the database to delete the user if exists, else do nothing
    if (params.delUser != null) {
      try {
        stmt.executeUpdate(queries.deleteUser(params.delUser));
        System.out.println(params.delUser + " has been deleted.");
      } catch (MySQLIntegrityConstraintViolationException e) {
        System.out.println(e.getMessage());
      }
    }

    // goes through if not updating/inserting to database AKA calling procedures
    if (rs != null) {
      while (rs.next()) {
        if (params.searchRecipe != null) {
          String name = rs.getString(1);
          float price = rs.getFloat(2);
          int totalTime = rs.getInt(3);
          String rating = rs.getString(4);
          if (rating == null) {
            rating = "N/A";
          } else {
            rating = rating + "/5";
          }

          System.out.printf("%-10s | $%-5.2f | total time: %d | rating: %s%n",
              name, price, totalTime, rating);
        }

        if (params.macro != null) {
          String name = rs.getString(1);
          int count = rs.getInt(2);
          float price = rs.getFloat(3);
          int totalTime = rs.getInt(4);
          float carb = rs.getFloat(5);
          float protein = rs.getFloat(6);
          float fat = rs.getFloat(7);
          System.out.printf(
              "%-20s | ingredient count: %d | $%-5.2f | total time: %-2d minutes | carbs: %-5.2fg | protein: %-7.2fg | fat: %-5.2fg%n",
              name, count, price, totalTime, carb, protein, fat);

        }

        if (params.ingredient != null) {
          String name = rs.getString(2);
          String type = rs.getString(3);
          float price = rs.getFloat(4);
          int carb = rs.getInt(5);
          int protein = rs.getInt(6);
          int fat = rs.getInt(7);
          System.out.printf(
              "%-15s | type: %-9s | $%-5.2f | carbs: %dg | protein: %dg | fat: %dg%n",
              name, type, price, carb, protein, fat);
        }

        if (params.ingredients != null) {
          if (params.ingredients.size() > 4) {
            System.out.println(
                "Our program only supports up to 4 ingredients searched at a time right now." +
                    "Please support our project and we'll extend this functionality soon!");
            System.exit(1);
          }
          String name = rs.getString(1);
          int numIng = rs.getInt(2);
          float price = rs.getFloat(3);
          int tTime = rs.getInt(4);
          String rating = rs.getString(5);
          if (rating == null) {
            rating = "N/A";
          } else {
            rating = rating + "/5";
          }
          System.out.printf(
              "%-20s | %d ingredients | $%-5.2f to buy rest of ingredients | %-2d minutes | Rating: %s%n",
              name, numIng, price, tTime, rating);
        }

        if (params.minimal || params.cheap || params.popular) {
          String name = rs.getString(1);
          int numIng = rs.getInt(2);
          float price = rs.getFloat(3);
          int tTime = rs.getInt(4);
          String rating = rs.getString(5);
          if (rating == null) {
            rating = "N/A";
          } else {
            rating = rating + "/5";
          }
          System.out.printf("%-20s | %d ingredients | $%-5.2f | %-2d minutes | Rating: %s%n",
              name, numIng, price, tTime, rating);
        }

        if (params.totalTime || params.activeTime) {
          String name = rs.getString(1);
          int numIng = rs.getInt(2);
          float price = rs.getFloat(3);
          int aTime = rs.getInt(4);
          int tTime = rs.getInt(5);
          String rating = rs.getString(6);
          if (rating == null) {
            rating = "N/A";
          } else {
            rating = rating + "/5";
          }
          System.out.printf(
              "%-20s | %d ingredients | $%-5.2f | Active Time: %-3d minutes | Total Time: %-3d minutes | Rating: %s%n",
              name, numIng, price, aTime, tTime, rating);
        }

        if (params.chooseRecipe != null) {
          String name = rs.getString(1);
          int aTime = rs.getInt(2);
          int iTime = rs.getInt(3);
          int tTime = rs.getInt(4);
          float tPrice = rs.getFloat(5);
          float tCarb = rs.getFloat(6);
          float tProtein = rs.getFloat(7);
          float tFat = rs.getFloat(8);
          String url = rs.getString(9);
          System.out.println("Recipe: " + name + "\nActive Time: " + aTime
              + " min\nInactive Time: " + iTime + " min\nTotal Time: " + tTime
              + " min\n");
          System.out.println("Ingredients:");
          // Ingredients
          while (rs1.next()) {
            String iName = rs1.getString(1);
            float iCount = rs1.getFloat(2);
            String uName = rs1.getString(3);
            float iPrice = rs1.getFloat(4);
            float iCarb = rs1.getFloat(5);
            float iProtein = rs1.getFloat(6);
            float iFat = rs1.getFloat(7);
            System.out.printf(
                "%-15s | %-5.2f %-1s | $%-5.2f | Carbs: %-5.2fg | Protein %-5.2fg | Fat %-5.2fg%n",
                iName, iCount, uName, iPrice, iCarb, iProtein, iFat);
          }
          System.out.printf("\nTotal Cost: $%-5.2f", tPrice);
          System.out.println(
              "Total Carbs: " + tCarb + "g | " + "Total Protein: " + tProtein + "g | "
                  + "Total Fat: " + tFat + "g");
          System.out.println(url);
        }
      }
    }
  }
}

import java.util.List;

/**
 * query class with methods to query on the recipes DB.
 */
public class Query {

  // displays Recipe information - name price etc.
  public String chooseRecipe1(String keyword) {
    return "call chooseRecipe1('" + keyword + "')";
  }
  // displays Recipe information - ingredient values.
  public String chooseRecipe2(String keyword) {
    return "call chooseRecipe2('" + keyword + "')";
  }

  // searches for recipes with the given ingredients
  public String ingredientSearch(List<String> ingredients) {
    if (ingredients.size() == 1){
      return "call searchUsingIng('" + ingredients.get(0) +"'," + "'', '', '')";
    } else if (ingredients.size() == 2){
      return "call searchUsingIng('" + ingredients.get(0) +"', '" + ingredients.get(1) + "', '', '')";
    } else if (ingredients.size() == 3) {
      return "call searchUsingIng('" + ingredients.get(0) +"', '" + ingredients.get(1) + "', '" + ingredients.get(2) + "', '')";
    } else if (ingredients.size() == 4) {
      return "call searchUsingIng('" + ingredients.get(0) +"', '" + ingredients.get(1) + "', '" + ingredients.get(2) + "', '"
          + ingredients.get(3) +"')";
    } else {
      return "call searchUsingIng('', '', '', '')";
    }
  }

  // adds the rating by the user on the recipe if exists
  public String addRating(String username, String recipe, String rating) {
    return "call addRating('" + username + "', '" + recipe + "', " + rating + ")";
  }

  // displays detailed ingredient info if exists
  public String seeIngredient(String ing) {
    return "call searchIngredientInfo('" + ing + "')";
  }

  // adds the user to the database
  public String addUser(String user) {
    return "call addUser('" + user + "')";
  }

  // ranks recipe less ingredient first.
  public String searchRMinIng() {
    return "call minIngredients()";
  }

  // ranks recipe cheapest first.
  public String searchRCheap() {
    return "call cheapRecipe()";
  }

  // ranks recipe less total time first.
  public String searchRTtime() {
    return "call minTime(false)";
  }

  // ranks recipe less active time first.
  public String searchRatime() {
    return "call minTime(true)";
  }

  // ranks recipe user rating first.
  public String searchRrating() {
    return "call highestRating()";
  }

  public String searchRecipeName(String r) {
    return "call searchRecipeName('" + r + "')";
  }

  // searches databsae for recipes with the most of the given macronutrient
  public String searchMacro(String m) {
    return "call mostMacro('" + m + "')";
  }

  // deletes the user from the database
  public String deleteUser(String u) {
    return "call delete_user('" + u + "')";
  }

}

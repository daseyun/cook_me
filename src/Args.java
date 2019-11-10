import com.beust.jcommander.Parameter;

import java.util.List;

// arguments class for storing commands
public class Args {
  @Parameter(names = {"--recipe", "-r"}, description = "the recipe name to be queried on")
  String searchRecipe;

  @Parameter(names = {"--show", "-s"}, description = "display information about chosen recipe")
  String chooseRecipe;

  @Parameter(names = {"--ingredient", "-i"}, description = "the ingredient(s) to be queried on", variableArity = true)
  List<String> ingredients;

  @Parameter(names = {"--minimal", "-min"}, description = "flag to sort recipes by least number of ingredients")
  boolean minimal = false;

  @Parameter(names = {"--activetime", "-at"}, description = "flag to sort recipes by active time")
  boolean activeTime = false;

  @Parameter(names = {"--totaltime", "-tt"}, description = "flag to sort recipes by total time")
  boolean totalTime = false;

  @Parameter(names = {"--cheap", "-c"}, description = "flag to sort recipes by total cost")
  boolean cheap = false;

  @Parameter(names = {"--popular", "-p"}, description = "flag to sort recipes by average user rating AKA most popular")
  boolean popular = false;

  @Parameter(names = {"--macro", "-mac"},
      description = "option to sort recipes by most of these three macronutrients: fat, protein, carbohydrate" +
          " eg. -r 'beef -mac protein' would return recipes that use leaner beef (more protein)")
  String macro;

  @Parameter (names = {"-u", "--user"}, description = "adds the user to the database eg. -u rachlin, does nothing if already exists")
  String user;

  @Parameter (names = {"-delete"}, description = "deletes the user from the database, does nothing if not existing")
  String delUser;

  @Parameter (names = {"--rate"}, arity = 3,
      description = "adds rating by the user for a recipe eg. --rate hobin oatmeal 1")
  List<String> rating;

  @Parameter (names = {"-ii", "--ingInfo"}, description = "displays information about the ingredient")
  String ingredient;

  @Parameter (names = {"--help"}, help = true, description = "displays command info about the program")
  boolean help = false;
}
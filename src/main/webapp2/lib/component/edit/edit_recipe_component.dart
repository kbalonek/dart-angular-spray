library edit_recipe_component;

import '../../service/recipe.dart';
import 'package:angular/angular.dart';

@NgComponent(
    selector: 'edit-recipe',
    templateUrl: 'packages/angular_dart_demo/component/edit/edit_recipe_component.html',
    cssUrl: 'packages/angular_dart_demo/component/edit/edit_recipe_component.css',
    publishAs: 'ctrl'
)

class EditRecipeComponent {
  @NgTwoWay('recipe-map')
  Map<String, Recipe> recipeMap;
  
  @NgOneWay('categories')
  List<String> categories;
  
  String _recipeId;

  String newIngredient;
  
  Recipe get recipe {
    return recipeMap[_recipeId];
  }

  void removeIngredient(String ingredient){
    recipe.ingredients.remove(ingredient);
  }
  
  void addIngredient(){
    recipe.ingredients.add(newIngredient.trim());
    newIngredient = "";
  }
  EditRecipeComponent(RouteProvider routeProvider) {
    _recipeId = routeProvider.parameters['recipeId'];
  }
}

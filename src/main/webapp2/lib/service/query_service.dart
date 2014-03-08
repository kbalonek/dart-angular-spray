library query_service;

import 'package:angular/angular.dart';

import 'dart:async';
import 'recipe.dart';


class QueryService {
  String _recipesUrl = 'http://localhost:3000/api/v1/recipes';
  String _categoriesUrl = 'categories.json';

  Future _loaded;

  Map<String, Recipe> _recipesCache;
  List<String> _categoriesCache;

  Http _http;

  QueryService(Http this._http) {
    _loaded = Future.wait([_loadRecipes(), _loadCategories()]);
  }

  Future _loadRecipes() {
    return _http.get(_recipesUrl).then((HttpResponse response) {
      _recipesCache = new Map();
      for (Map recipe in response.data) {
        Recipe r = new Recipe.fromJsonMap(recipe);
        _recipesCache[r.id] = r;
      }
    });
  }

  Future _loadCategories() {
    return _http.get(_categoriesUrl).then((HttpResponse response) {
      _categoriesCache = new List();
      for (String category in response.data) {
        _categoriesCache.add(category);
      }
    });
  }

  void updateRecipe(Recipe r) {
    _recipesCache[r.id] = r;
    //_http.post(url, data)
  }

  Future<Recipe> getRecipeById(String id) {
    if (_recipesCache == null) {
      return _loaded.then((_) {
        return _recipesCache[id];
      });
    }
    return new Future.value(_recipesCache[id]);
  }

  Future<Map<String, Recipe>> getAllRecipes() {
    if (_recipesCache == null) {
      return _loaded.then((_) {
        return _recipesCache;
      });
    }
    return new Future.value(_recipesCache);
  }

  Future<List<String>> getAllCategories() {
    if (_categoriesCache == null) {
      return _loaded.then((_) {
        return _categoriesCache;
      });
    }
    return new Future.value(_categoriesCache);
  }
}

#!/bin/bash
DB=recipesapp
RECIPES_COLL=recipes
CATEGORIES_COLL=categories
mongoimport --jsonArray --db $DB --collection $RECIPES_COLL < recipes.json
mongoimport --jsonArray --db $DB --collection $CATEGORIES_COLL < categories.json

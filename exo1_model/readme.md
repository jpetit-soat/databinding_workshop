# Exercice 1 : Data Binding et Variable

Pour cet exercice et les suivants, nous avons au préalable mis en place la couche logique dans le module moviesmodel, qui est inclus dans chaque module d'exercice, afin que nous puissions nous concentrer uniquement sur la mise en place du data binding.

l'ensemble de la couche se trouve dans le package `fr.soat.demo.moviesmodel`

Dans cet exercice, nous allons afficher un poster de film ainsi que quelques informations concernant le concernant.

## Objectif

Nous verrons dans cet exercice :

* Comment utiliser un binding sur un ensemble de vue.
* Comment mettre de la logique directement dans votre vue.

## Rappel

//todo rappel sur les bindings expression

## Exercice

1. Créer le binding de l'objet `posterModel` pour que celà continue à fonctionner exactement comme actuellement en supprimant les deux methodes suivantes :
    * `initFields();` 
    * `initData(posterModel);`
    
2. Pour aller plus loin, n'utiliser plus les méthodes qui permettent le fomattage :
    * `getFormattedDate` 
    * `getFormattedGenres` 
    * `getFormattedActors`
    
   Formatter les propriétés suivantes directement depuis votre vue.
   * `date`
   * `genres`
   * `actors`
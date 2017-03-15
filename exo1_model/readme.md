# Exercice 1 : Data Binding et Variable

Pour cet exercice et les suivants, nous avons au préalable mis en place la couche logique dans le module `moviesmodel`, qui est inclus dans chaque module d'exercice, afin que nous puissions nous concentrer uniquement sur la mise en place du data binding.

L'ensemble de la couche se trouve dans le package `fr.soat.demo.moviesmodel`

Dans cet exercice, nous allons afficher un poster de film ainsi que quelques informations concernant le concernant.

## Objectif

Nous verrons dans cet exercice :

* Comment utiliser un binding sur un ensemble de vue.
* Comment mettre de la logique directement dans votre vue.

## Rappels

### Syntaxe des ressources

Il est possible d'utiliser les ressources de manière plus avancée dans le layout avec le Data Binding :

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{@string/a_text_resource}"/>
```

Cette méthode peut sembler inutile (on peut très bien faire `android:text="@string/a_text_resource"`, sans databinding) mais procéder de la sorte permet d’utiliser des String paramétrés :

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{@string/nameFormat(firstName, lastName)}"/>
```

Avec une ressource “plurals”, il faudra passer en paramètre le nombre concerné par le pluriel, et également en second paramètre s’il est présent dans le format du texte :

```xml
Have an orange
Have %d oranges

android:text="@{@plurals/orange(orangeCount, orangeCount)}"
```

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#expression_language

## Exercice

1. Créez le binding de l'objet `PosterModel` pour que cela continue à fonctionner exactement comme actuellement en supprimant les deux methodes suivantes :
    * `initFields();` 
    * `initData(posterModel);`
    
2. Pour aller plus loin, n'utilisez plus les méthodes qui permettent le fomattage :
    * `getFormattedDate` 
    * `getFormattedGenres` 
    * `getFormattedActors`
    
   A la place, formattez les propriétés suivantes directement depuis fichier de layout :
   * `date`
   * `genres`
   * `actors`
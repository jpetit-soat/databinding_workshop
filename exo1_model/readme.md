# Exercice 1 : Data Binding et Variable

Pour cet exercice et les suivants, nous avons au préalable mis en place la couche logique dans le module `moviesmodel`, qui est inclus dans chaque module d'exercice, afin que nous puissions nous concentrer uniquement sur la mise en place du data binding.

L'ensemble de la couche se trouve dans le package `fr.soat.demo.moviesmodel`

Dans cet exercice, nous allons afficher un poster de film ainsi que quelques informations concernant le concernant.

## Objectif

Nous verrons dans cet exercice :

* Comment utiliser un binding sur un ensemble de vue.
* Comment mettre de la logique directement dans votre vue.
* Comment utiliser des méthodes statiques ou propriétés statiques depuis la vue.

## Rappels

### Utilisation du Context dans le fichier de layout

Si vous devez utiliser une méthode d'une variable qui possède un paramètre, il est tout à fait possible d'appeler cette méthode avec son nom complet et une valeur en paramètre :

```java
public class User {

    private String value;

    public void getTextWithInt(int intValue){
        return value + intValue;
    }
}
```

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{user.getTextWithInt(12)}"/>
```

Il est également possible de passer un objet `Context` en paramètre, depuis n'importe quel layout bindé : il suffit pour cela d'utiliser le mot-clé `context` :

```java
public class User {

    private @StringRes int valueRes;

    public void getText(Context context){
        return context.getText(valueRes);
    }
}
```

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{user.getText(context)}"/>
```

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

### Imports

Le data binding permet également d'utiliser les méthodes ou propriétés statiques d'une classe. Pour celà, il suffit juste d'ajouter référence la classe :

```xml
<data>
    <import type="com.example.MyStringUtils"/>
    <variable name="user" type="com.example.User"/>
</data>
…
<TextView
   android:text="@{MyStringUtils.capitalize(user.lastName)}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```

## Exercice

1. Remplacer les méthodes qui permettent le fomattage :
    * `getFormattedDate` 
    * `getFormattedGenres` 
    * `getFormattedActors`
    
   A la place, formattez les propriétés suivantes directement depuis fichier de layout, grâce aux ressources et aux imports de classes :
   * `date`
   * `genres`
   * `actors`
   
   (Tips : Essayez de mettre en référence la/les classe(s) contenant les méthodes utilisés)

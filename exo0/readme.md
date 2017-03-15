# Introduction : Mon premier Binding

## Objectif

Nous verrons dans cet exercice :

* Comment mettre en place l'environnment permettant d'utiliser le "**Data Binding**"
* Comment initialiser une vue pour que celle-ci puisse être liée à une donnée
* La création de la donnée ainsi que la mise en place de la liaison à une vue

## Rappel

### Prérequis

La bibliothèque "**Data Binding**" est une "**Support library**", celle-ci est donc rétrocompatible avec les versions antérieurs jusqu'à la version d'**Android 2.1 (Api 7+)**
Pour pouvoir utiliser le data binding, les prérequis sont :

* une version du plugin de gradle supérieur ou égale à **1.5.0-alpha1**
* une version d'Android Studio supérieur à **Android Studio 1.3**
* activer le **Data Binding** dans la configuration du projet (le fichier build.gradle dans le module de l'application) 

```groovy
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

### Data Binding dans les fichiers layout

Pour pouvoir lier une donnée à une vue grâce au **Data Binding** d'Android,
Il est important de comprendre que, pour qu'une vue soit liée à une donnée :

* L'élement racine possède un élément `layout`
* Que le tag `layout` contienne l'élement `data`
* Que le tag `layout` contienne votre vue tel que vous l'auriez fait sans le Data Binding

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="Hello World !"/>
   </LinearLayout>
</layout>
```

Cela génerera une classe dite de "Binding" contenant les références sur les éléments du layout. Celle-ci hérite de la classe `ViewDataBinding`,
Elle se nommera de la même manière que le fichier de layout, formatté en pascal case et suffixé par `Binding`.

Par exemple  :

* si votre fichier layout se nomme `detail_activity.xml` la classe générée sera `DetailActivityBinding`
* si votre fichier layout se nomme `view_detail.xml` la classe générée sera `ViewDetailBinding`

Toutes les données doivent être sous forme d'une variable à déclarer dans `data`, elle est décrite par un nom et son type 

```xml
<variable name="user" type="com.example.User"/>
<variable name="title" type="String"/>
```

La variable peut avoir n'importe quel type tant que celle-ci est un `Object` et non une primitive.

les attributs des vues y accèdent en utilisant la syntaxe `@{}`.

Exemple :
* Pour un object simple

```xml
<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{title}"/>
```

* Pour un objet complexe

```xml
<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{user.firstName}"/>
```

Pour que la propriété d'un objet soit accessible, il faut :

* soit qu'elle soit publique
* soit qu'elle soit encapsulée, dans notre exemple, il faut qu'elle contienne une méthode `getFirstName()` ou bien `firstName()` 

Dans un premier temps nous allons nous intéresser seulement au binding de la vue de l'activity.

Pour créer le binding il suffit d'appeler une méthode du SDK d'android `DataBindingUtil.setContentView(Activity activity, int layoutId)`
Celle-ci instanciera l'objet Binding associé, à qui il faudra par la suite setter les variables.

Exemple : étant donné une vue avec pour nom de fichier `main_activity.xml` et contenant deux variables ayant pour noms `user` et `title` :

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
   User user = new User();
   String title = "Titre";
   binding.setUser(user);
   binding.setTitle(title);
}
```

## Exercice

1. Créez un Binding sur une chaine de caractères

    Pour ce faire, il faudra :
    * Modifier le fichier layout `view_poster.xml` pour que la `TextView` avec l'identifiant `textview` soit lié à une `String`
2. Créez un Binding sur un objet plus complexe

    Pour ce faire, il faudra :
    * Créez une classe `PosterModel` ayant une propriété `title`
    * Modifiez le fichier layout `view_poster.xml` pour que la `TextView` avec l'identifiant `textview` soit lié (bind) à la propriété `title` de type `PosterModel`
    * N'oubliez pas de bien mettre en place la liaison de la donnée (Binding data) au sein de la `MainActivity`
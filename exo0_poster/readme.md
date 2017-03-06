# Introduction : Mon premier Binding

## Objectif

Nous verrons dans cet exercice :

* Comment mettre en place l'environnment permettant d'utiliser le "*Data Binding*"
* Comment initialiser une vue pour que celle-ci puisse être lié à une donnée
* La création de la donnée ainsi que la mise en place de la liaison à une vue

## Rappel

### Prérequis

La bibliothèque *"Data Binding"* est une *"Support library"*, celle-ci est donc rétrocompatible avec les versions antérieurs jusqu'à la version d'*Android 2.1 (Api 7+)*
Pour pouvoir utiliser le data binding, les prérequis sont :

* une version du plugin de gradle supérieur ou égale à *1.5.0-alpha1*
* une version d'Android Studio supérieur à *Android Studio 1.3*
* activer le *Data Binding* dans la configuration du projet (le fichier build.gradle dans le module de l'application) 

```groovy
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

### Data Binding dans les fichiers layout

Pour pouvoir lié une donnée à une vue grâce au *Data Binding* d'Android, 
Il est important de comprendre que pour qu'une vue soit lié à une donnée que l'élement racine :

* ait pour tag `layout`
* Contenant l'élement `data`
* Contenant votre vue tel que vous l'auriez fait sans le data Binding

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
           android:text=Hello World !"/>
   </LinearLayout>
</layout>
```

Celà gènererai une classe contenant les bindings associés. celle-ci hérite de la classe `ViewDataBinding`,
elle se nommera de la même manière que le fichier de layout formatter en pascal case, suffixée de Binding.

Par exemple  :

* si votre fichier layout se nomme detail_activity.xml la classe générée sera DetailActivityBinding
* si votre fichier layout se nomme view_detail.xml la classe générée sera ViewDetailBinding

Toutes les données doivent être sous forme d'une variable à déclarer dans `data`, elle est décrite par un nom et son type 

```xml
<variable name="user" type="com.example.User"/>
<variable name="title" type="String"/>
```

La variable peut peut avoir n'importe quel tant que celle-ci est un Object et non une primitive

les attributs des vues y accèdent en utilisant la syntaxe `@{}` 

exemple :
* pour un object simple

```xml
<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{title}"/>
```

* pour un objet complexe

```xml
<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{user.firstName}"/>
```

pour que la propriété d'un objet soit accessible, il faut soit :

* qu'elle soit publique
* soit qu'elle soit encapsulé, dans nore exemple il faut sot quel ait une methode `getFirstName()` ou bien `firstName()` 

Dans un premier temps nous allons nous intérésser seulement au binding d'une activity de la vue de l'activity.

Pour créer le binding il suffit d'appeler une méthode du SDK d'android `DataBindingUtil.setContentView(Activity activity, int layoutId)`
celle ci instanciera l'objet Binding associé, il faudra par la suite setter les variables.

Exemple: étant donné, une vue avec pour nom de fichier `main_activity.xml`est contenant deux variables avec pour noms `user` et `title`

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

1. Créer un Binding sur une chaine de caractère
    Pour ce faire, il faudra :
    * Modifier le fichier layout `view_poster.xml` pour que la textview avec l'identifiant `textview` soit lié à une `String`
2. Créer un Binding sur un objet plus complexe
    Pour ce faire, il faudra :
    * Créer une classe `PosterModel` ayant une propriété `title`
    * Modifier le fichier layout `view_poster.xml` pour que la textview avec l'identifiant `textview` soit lié (bind) à la propriété `title` de type `PosterModel`
    * N'oubliez pas de bien mettre en place la liaison de la donnée (Binding data) au sein de la `MainActivity`
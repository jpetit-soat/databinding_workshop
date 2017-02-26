# Introduction : Mon premier Binding

## Objectif

Nous verrons dans cet exercice :

* Comment mettre en place l'environnment permettant d'utiliser le "`Data Binding`"
* Comment initialiser une vue pour que celle-ci puisse être lié à une donnée
* La création de la donnée ainsi que la mise en place de la liaison à une vue

## Rappel

### Prérequis

La bibliothèque "`Data Binding`" est une "`Support library`", celle-ci est donc rétrocompatible avec les versions antérieurs jusqu'à la version d'`Android 2.1 (Api 7+)`
Pour pouvoir utiliser le data binding, les prérequis sont :

* une version du plugin de gradle supérieur ou égale à `1.5.0-alpha1`
* une version d'Android Studio supérieur à `Android Studio 1.3` 
* activer le `Data Binding` dans la configuration du projet (le fichier build.gradle dans le module de l'application) 

```groovy
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

### Data Binding dans les fichiers layout

//todo rappel sur comment mettre en place le data binding
//ne pas oublié l'importance de l'ordre 

## Exercice

Créer votre propre Binding Pour ce faire il faudra :

* Créer un object ayant une propriété `title`
* Modifier le fichier layout `view_poster.xml` pour que la textview avec l'identifiant `textview` soit lié (bind) à la propriété `title` d'un objet de type `Poster`
* N'oubliez pas de bien mettre en place la liason de la donnée (Binding data)

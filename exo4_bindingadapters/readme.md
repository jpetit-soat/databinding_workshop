# Exercice 4 : Binding Adapters

## Objectif

A présent que nous avons vu comment rendre une vue dynamique, nous allons découvrir ici comment effectuer, depuis son fichier de layout, des actions qui auparavant étaient impossibles à faire, grâce aux **Binding Adapters.**
 
Pour cela, nous allons reprendre les vues créé dans l'exercice 2 et 3 et les coupler, en leur ajoutant des comportements supplémentaires qui seront géré grâce à des Binding Adapters.

## Rappel

Les **Binding Adapters** permettent d’effectuer des actions depuis le fichier de layout, là où il était auparavant nécessaire d’ajouter du code dans l’activité. Ils permettent également de créer des comportements personnalisés sur la vue.

La syntaxe d’un Binding Adapter est la suivante :

<br/>

```java
@BindingAdapter("margin")
public static void setMargin(View view, float margin) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    marginLayoutParams.setMargins(margin, margin, margin, margin);
    view.setLayoutParams(marginLayoutParams);
}
```



```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:margin="@{20.0f}"/>
```

<br/>

Il est nécessaire de déclarer le namespace de l’application (*“xmlns:app="http://schemas.android.com/apk/res-auto””*) dans la balise “layout”, et de l’utiliser à l’appel du BindingAdapter.

*Note : l'auto-complétion n’est pas disponible pour l’utilisation des Binding Adapters).*

<br/>

Il est possible de déclarer plusieurs paramètres dans un Binding Adapter : 

<br/>

```java
@BindingAdapter(value = {"marginLeft", "marginTop", "marginRight", "marginBottom"}, requireAll = false)
public static void setMargin(View view, float marginLeft, float marginTop, float marginRight, float marginBottom) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    marginLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
    view.setLayoutParams(marginLayoutParams);
}
```

<br/>

L’attribut “*requireAll*” est facultatif, il permet de définir qu’il n’est pas nécessaire d’avoir tous les paramètres pour pouvoir utiliser le BindingAdapter (dans le cas contraire, si on utilise qu’un seul des paramètres une erreur apparaîtra à la compilation).

*Note : dans notre exemple, vu qu'on utilise des primitifs, leur valeur seront de 0 s'ils n'ont pas été renseigné dans le layout. S'il s'agit d'un objet, la valeur sera null.*

<br/>

Enfin, il est également possible de réutiliser la valeur précédemment passé au Binding Adapter : 

<br/>

```java
@BindingAdapter("margin")
public static void setMargin(View view, float oldMargin, float newMargin) {
    if (oldMargin != newMargin) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(newMargin, newMargin, newMargin, newMargin);
        view.setLayoutParams(marginLayoutParams);
    }
}
```

<br/>

L’utilité principale est d’éviter de redéfinir une nouvelle fois une valeur pour une vue, pour éviter de répéter une opération coûteuse (faire un `invalidate()` par exemple) ou ajouter une seconde fois un listener.

<br/>

**Documentation** : https://developer.android.com/topic/libraries/data-binding/index.html#custom_setters

## Exercice

1. En vous basant sur le code écrit dans la méthode `updateViewWithDetails()`, écrire les Binding Adapters correspondant à chaque changement dans la classe `BindingAdapterUtils`
2. Utilisez les Binding Adapters ainsi créé dans les fichiers de layout `view_filters` et `view_detailed_movie`. 
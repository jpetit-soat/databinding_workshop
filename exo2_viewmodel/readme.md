# Exercice 2 : Première approche MVVM (Model-View-ViewModel)

## Objectif

Dans le premier exercice nous avons mis en place du Data Binding, nous avons pu observer que la logique de présentation de la vue (formattage de données) était soit défini dans la vue, soit dans le modèle.
* Le **modèle** a dans le premier cas la responsabilité de décrire la donnée mais également de décrire comment afficher une donnée
* La **vue** contient dans le second cas une logique

Une des bonnes pratiques en conception d'application et de respecter le principe de responsabilité unique.
* Le **modèle** ne doit donc qu'avoir la responsabilité de décrire la donnée
* La **vue** ne doit pas contenir de logique

Pour répondre à cette problèmatique il y a plusieurs patrons de conception MVC, MVP mais nous allons nous concentrer sur celui qui s'appuie sur le binding.

## Rappels
### MVVM (Model-View-ViewModel)
#### Model
La donnée modèle (ici par exemple, l’objet `MovieSeriesModel`)
#### View
La vue (ici, `view_detailed_movie`)
#### ViewModel
C'est une abstraction de la vue qui **fournit un intermédiaire entre le modèle et la vue** et qui s’appuie sur la puissance du Data Binding pour mettre à disposition de la vue les données du modèle.

Cet intermédiaire a deux rôles : 
* **Formater la donnée modèle** pour qu’elle soit correctement géré par la vue. Par exemple, en concaténant une liste de String en un seul String, un id de drawable en objet Drawable ou encore 
* **Gérer les évènements utilisateurs**. La gestion du clic sur un bouton, de l’ajout d’un texte dans un EditText etc.

Dans cet exercice, nous nous intéresserons au premier rôle. Le second sera développé dans l’exercice 3.


### Utilisation des ressources


Il n’est pas possible d’utiliser directement les id de ressources via le Data Binding. Par exemple le code suivant ne fonctionne pas :


```java
public class TextViewModel {
    public @StringRes int textValue;
}
```
```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{model.textValue}"/>
```

<br/>

Le compilateur interprétera ce code en formattant l’id (un integer) comme un string, et affichera l’id au lieu du texte.

Pour avoir la bonne valeur, il faut fournir, par le biais du ViewModel, non pas l’id de ressource, mais la ressource elle-même :

<br/>

```java
public class TextViewModel {
    private  @StringRes int textValue;

    private Context context;

    public TextViewModel(Context context) {
        this.context = context;
    }

    public String getTextValue() {
        return context.getString(textValue);
    }
}
```
```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{model.textValue}"/>
```

<br/>

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#expression_language

<br/>

### Inclusion de layouts

Il est possible d’utiliser la balise `<include>` avec du DataBinding :

<br/>

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <include layout="@layout/view_name"
           bind:user="@{user}"/>
   </LinearLayout>
</layout>
```

<br/>

**view_name.xml**
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <Button
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:text=”@{user.name}”>
</layout>
```

<br/>

Dans cet exemple, on “inclut” le layout `@layout/name` qui lui-même utilise le Data Binding, avec comme variable un objet User appelé `user` (comme pour le layout parent).

On lui fournit la variable depuis le layout parent en utilisant l’attribut issue du nom de la variable à setter (`bind:user` pour le nom de variable `user` dans `view_name.xml`).

<br/>

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#includes

## Exercice 

1. Créez les méthodes au sein du ViewModel `DetailedMovieViewModel` pour présenter les données du `MovieSeriesModel` comme le fait la méthode `initDetailedInfo`.
2. Faire également que la vue `view_poster` soit liée par le biais de la variable.
3. **Bonus** : Modifier la classe `DetailedMovieViewModel` ainsi que `view_detailed_movie` pour que le ViewModel ne soit pas construit avec un `Context`.
    
    Pour vous aidez : https://developer.android.com/topic/libraries/data-binding/index.html#resources

# Exercice 2

## Rappels

### Objectif d’un ViewModel

L’objectif principal d’un ViewModel est de **fournir un intermédiaire entre la donnée modèle** (ici par exemple, l’objet MovieSeriesModel) **et la vue** (ici, view_detailed_movie).

Cet intermédiaire a deux rôles : le premier est de **formater la donnée modèle** pour qu’elle soit correctement géré par la vue. Par exemple, en concaténant une liste de String en un seul String, un id de drawable en objet Drawable ou encore 

Le second rôle est la **gestion des évènements utilisateurs**. La gestion du clic sur un bouton, de l’ajout d’un texte dans un EditText etc.

<br/>

Dans cet exercice, nous nous intéresserons au premier rôle. Le second sera développé dans l’exercice 3.

Cette exercice porte surtout sur des questions d’architecture, en vous faisant utiliser les principe du **MVVM**, et donc l’aspect technique est moins.

Néanmoins certains points technique peuvent être mentionnés, notamment au niveau des ressources.


### Utilisation des ressources


Il n’est pas possible d’utiliser directement les id de ressources via le databinding. Par exemple le code suivant ne fonctionne pas :

<br/>

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

Il est possible d’utiliser la balise \<include\> avec du DataBinding : 

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

Dans cet exemple, on “inclut” le layout *“@layout/name”* qui lui-même utilise le DataBinding, avec comme variable un objet User appelé “user” (comme pour le layout parent). 

On lui fournit la variable depuis le layout parent en utilisant l’attribut issue du nom de la variable à setter (bind:user pour le nom de variable “User” dans *view_name.xml*).

<br/>

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#includes
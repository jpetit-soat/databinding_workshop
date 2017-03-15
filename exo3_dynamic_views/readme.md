# Exercice 3 : MVVM et vues dynamiques

## Objectif

Nous allons voir ici comment **gérer des évenements** dans une architecture de type MVVM, et comment mettre à jour la vue lorsque la donnée change.

## Rappel

### Gestion d'évenement dans un ViewModel  

Pour la gestion d'évenement, un ViewModel s'appuie sur la puissance du Data Binding d'Android.

Elle peut être faite de trois manières : 
* Par référence de méthode
* Par binding de listener
* Par binding à double sens

#### Référence de méthode

La référence de méthode permet de lier l’action d’une vue à une méthode existante dans une variable du binding : 

<br/>

```java
public class Presenter {
    void onSaveClick(View view) {...}
} 
```
```xml
<Button android:layout_width="wrap_content" 
                android:layout_height="wrap_content"
                android:onClick="@{presenter::onSaveClick}" />
```

<br/>

La signature de la méthode doit correspondre exactement à celle attendu pour l’action demandé (par exemple pour un `onClick`, on attend une méthode ayant un View en premier paramètre).
La méthode a besoin d’être implémenté (ne peut pas être une référence sur une méthode abstraite).

#### Binding de listener

Le binding de listener permet de créer un listener à la façon d’une lambda, qui appellera une fonction dans une variable du binding :

<br/>

```java
public interface Presenter {
    void onSaveClick();
} 
```
```xml
<Button android:layout_width="wrap_content" 
                android:layout_height="wrap_content"
                android:onClick="@{() -> presenter.onSaveClick()}" />
```

<br/>

La différence principale entre la référence de méthode et le binding de listener est le moment où l’implémentation du listener est évalué : 

* Pour la **référence de méthode**, l’implémentation est évalué lorsque le binding reçoit la variable contenant la méthode. 
* Pour le **binding de listener**, l’implémentation est évalué lorsque l'événement survient (au clic, par exemple).

Un autre intérêt du binding de listener est sa souplesse : il permet en effet de passer les paramètres de son choix depuis le XML : 

<br/>

```java
public interface Presenter {
    void onSaveClick(Task task);
} 
```
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="presenter" type="com.example.Presenter"/>
       <variable name="task" type="com.example.Task"/>
   </data>
   <Button android:layout_width="wrap_content" 
                android:layout_height="wrap_content"
                android:onClick="@{() -> presenter.onSaveClick(task)}" />
</layout>
```

<br/>

Il est également possible de récupérer les paramètres du listener appelant (ici, le `OnClickListener`, avec comme paramètre la vue) :

<br/>

```xml
<Button android:layout_width="wrap_content" 
                android:layout_height="wrap_content"
                android:onClick="@{(view) -> presenter.onSaveClick(view)}" />
```

<br/>

Enfin, il est également possible de faire référence à une autre vue existant dans le layout. Il suffit d’y faire appel en paramètre en indiquant l’id de la vue en Pascal Case : 

<br/>

```xml
<LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:id="@+id/name_text_view" 
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content" />
       <Button android:layout_width="wrap_content" 
               android:layout_height="wrap_content"
               android:onClick="@{() -> presenter.onSaveClick(nameTextView)}" />
   </LinearLayout>
```

<br/>

**Documentation** : https://developer.android.com/topic/libraries/data-binding/index.html#event_handling

#### Binding à double sens

Le binding à double sens permet de récupérer les données rentrées par un utilisateur, en liant une variable à un attribut qui gère à la fois la lecture et l’écriture : 

<br/>

```java
public class TextViewModel {

   private String textValue;

   public String getTextValue() {
       return this.textValue;
   }
   public void setTextValue(String value) {
       this.textValue = value;
   }
} 
```
```xml
<EditText android:layout_width="wrap_content" 
                  android:layout_height="wrap_content" 
                  android:text="@={model.textValue}" />
```

<br/>

Dans les faits, cette syntaxe crée dans le binding un listener implicite qui modifiera la valeur de la variable bindé. En l'occurrence pour cet exemple, cela créera un listener *onTextChanged* qui mettra à jour la variable.

Seul quelques attributs permettent aujourd’hui d’utiliser le binding à double sens :

* **AbsListView**       android:selectedItemPosition
* **CalendarView** 	    android:date
* **CompoundButton**	android:checked
* **DatePicker** 		android:year, android:month, android:day
* **NumberPicker**      android:value
* **RadioGroup** 		android:checkedButton
* **RatingBar** 		android:rating
* **SeekBar** 		    android:progress
* **TabHost** 		    android:currentTab
* **TextView** 		    android:text
* **TimePicker**		android:hour, android:minute

<br/>

**Documentation** : cette option n’est pas encore expliqué dans la documentation officielle, mais voici un bon article détaillant son fonctionnement et fournissant des informations complémentaires : https://halfthought.wordpress.com/2016/03/23/2-way-data-binding-on-android/

(Note : les build tools gradle doivent être minimum en 2.1-alpha3 pour accéder au binding à double sens)

### Les données

Lorsqu'un objet est mis à jour dans le ViewModel après l'affichage de l'écran (quand l'utilisateur a changé un texte par exemple), cela ne rafraîchit pas automatiquement la vue.

Pour que la vue se rafraîchisse, il faut que le ViewModel fournisse des données **"observable"**.

Il y a trois manières pour que la donnée soit observable :

* que le ViewModel hérite de **BaseObservable**
* que les valeurs du ViewModel soient contenue dans des **ObservableFields**, ou des **Observable Collections** s'il s'agit de collections (List, Map etc.).

#### Observable Objects

Le moyen le plus complet de gérer les mises à jour est de faire hériter son ViewModel de la classe `BaseObservable`.

Pour gérer la mise à jour d'un champ du ViewModel, il est nécessaire de lui appliquer l'annotation `@Bindable` (soit sur le champ s'il est public, soit sur son getter s'il est encapsulé).

A la compilation, les champs annotés `@Bindable` génèrent un id associé à la classe BR (équivalent de la classe R pour le Data Binding).
Cet identifiant, enfin, pourra être utilisé pour effectuer la mise à jour grâce à la méthode `notifyPropertyChanged(int)`, provenant de la classe `BaseObservable`.


```java
public class User extends BaseObservable {

   private String name;

   @Bindable
   public String getName() {
       return this.name;
   }

   public void setLastName(String lastName) {
       this.lastName = lastName;
       notifyPropertyChanged(BR.lastName);
   }
}
```

Il est également possible de mettre à jour tous les champs `@Bindable` d'un seul coup, grâce à la méthode globale `notifyChange()`

#### ObservableFields

La classe `ObservableField` hérite de la classe `BaseObservable` et utilise les mêmes mécanismes que ceux décrit précédemment, à la différence qu'ils n'agissent que sur un seul champ.

Ce qui signifie qu'un objet `ObservableField` met à jour automatiquement la vue à chaque fois que la donnée change.


```java
private static class User {
   public final ObservableField<String> name = new ObservableField<>();
   public final ObservableInt age = new ObservableInt();
}
```

Comme la donnée utile est encapsulée, il est nécessaire de passer par des getters / setters pour y accéder.

Faire un `set()` de la donnée notifie la vue du changement.

```java
user.name.set("Google");
int age = user.age.get();
```

Il existe également des Observable Collections, qui se seront pas abordé dans ces exercices. Ils fonctionnent de la même façon que les `Collection`, et mettent à jour la vue à chaque modification de la collection.

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#observable_objects

## Exercice

1. Gérez les interactions (clic, input de texte...) dans la classe `FiltersViewModel` tel qu'elles sont gérées dans la méthode `initListeners()`.
2. Faire en sorte que la classe `FiltersViewModel` gère la mise à jour de la vue lorsque l'utilisateur a effectué une action sur l'écran, en faisant hériter la classe de `BaseObservable`.
3. **Bonus** : Supprimez l'héritage vers la classe `BaseObservable` et remplacez-le par une autre méthode.
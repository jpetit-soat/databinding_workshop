# Exercice 5

## Rappel

### L’objectif du binding de RecyclerView

L'objectif d'avoir un **RecyclerView utilisant le DataBinding** est d'avoir un moyen d'afficher une liste de vues liées à un ViewModel, 
indifféramment de leur nature, sans avoir à implémenter un *Adapter* et des *ViewHolders* spécifiques.

Pour cela, nous allons définir une interface "**BindableViewModel**", permettant de récupérer le layout associé à un ViewModel, ainsi qu'un identifiant de variable (expliqué plus loin).
Nous allons également définir un *Adapter* et un *ViewHolder* générique, qui traiteront une liste de "BindableViewModel" devant être affiché dans la RecyclerView.

Tout comme pour l’exercice 2, il s’agit plus ici de découvrir une méthode pour structurer son application. L’aspect technique pure est donc moins présent.

Néanmoins, afin de mettre en place le binding de RecyclerView, il est important de parler de quelques subtilités sur la création d’un binding.

### Les différentes méthodes de création de binding

Dans les premiers exercices nous avons vu la méthode de création suivante :

<br/>

```java
MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
```

<br/>

Cette méthode ne marche que pour faire le binding d’un layout à une activité. Si on avait voulu faire la même chose avec un fragment, nous aurions utilisé cette méthode :


```java
MainFragmentBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.main_fragment, parentView, false);
```

<br/>

Cette méthode est similaire à la méthode *inflate()* de la classe LayoutInflater. Cette méthode est également utilisé pour créer les vues de la RecyclerView (ou d’une ListView) en temps normal, nous pouvons donc l’appliquer à l’identique dans le cas du DataBinding.

<br/>

Enfin, il est également possible de créer un binding à partir de la classe généré à la compilation :


```java
MainFragmentBinding binding = MainFragmentBinding.inflate(getLayoutInflater(), parentView, false);
```

<br/>

La même classe permet également de faire le binding d’une vue existante :


```java
MainFragmentBinding binding = MainFragmentBinding.bind(view);
```

<br/>

En effet, les classes générés pour le binding sont créées avec des méthodes statique permettant d’instancier la classe correspondante, soit avec un LayoutInflater soit avec une vue existante.

<br/>

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#generated_binding


### Passer une variable à un binding inconnu

Comme précisé dans l’exercice d’introduction, toutes les classes de binding générés héritent de la classe ViewDataBinding. 

Lorsqu’un binding est créé à la compilation, un autre élément est créé : un identifiant lié à la variable présente dans le layout. L’identifiant est rattaché à la classe statique **BR**, qui fonctionne de la même manière que la classe R fonctionne pour les ressources.

Afin de donner sa variable à un binding de type inconnu, il faut utiliser la méthode setVariable() de la classe ViewDataBinding : 

<br/>

```java
private ViewDataBinding binding;

public void setViewModel(Object viewModel) {
        binding.setVariable(BR.aValue, viewModel);
}
```

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="aValue" type="com.example.AViewModel"/>
   </data>
   <Button ... />
</layout>
```

<br/>

Précision importante : lorsque l’on fournit une variable à un binding, il ne met pas immédiatement à jour la vue. L’instruction est placé dans un *”Scheduler”* qui exécutera la mise à jour à la frame suivante (de la même façon qu’un Runnable passé à un Handler). 

Pour que la vue soit immédiatement mise à jour (ce qui est voulu dans le cas d’un RecyclerView), il est nécessaire d’appeler la méthode *executePendingBindings()* : 

<br/>

```java
private ViewDataBinding binding;

public void setViewModel(Object viewModel) {
        binding.setVariable(BR.aValue, viewModel);
        binding.executePendingBindings();
}
```

<br/>

*(Note : Ce mécanisme est ce qui rend le DataBinding “Thread Safe” : il est tout à fait possible de modifier un ViewModel depuis un Thread en background, même si cette action doit modifier la vue du fait d’un BaseObservable ou d’un ObservableField.)*

<br/>

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#advanced_binding
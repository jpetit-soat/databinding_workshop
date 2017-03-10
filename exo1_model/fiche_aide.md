# Exercice 1

## Rappels

### Syntaxe des ressources

Il est possible d'utiliser les ressources de manière plus avancée dans le layout avec le DataBinding : 

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{@string/a_text_resource}"/>
```

<br/>

Cette méthode peut sembler inutile (on peut très bien faire *android:text="@string/a_text_resource"*, sans databinding) mais procéder de la sorte permet d’utiliser des String paramétrés : 

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{@string/nameFormat(firstName, lastName)}"/>
```

<br/>

Avec une ressource “plurals”, il faudra passer en paramètre le nombre concerné par le pluriel, et également en second paramètre s’il est présent dans le format du texte :

```xml
Have an orange
Have %d oranges

android:text="@{@plurals/orange(orangeCount, orangeCount)}"
```

Documentation : https://developer.android.com/topic/libraries/data-binding/index.html#expression_language
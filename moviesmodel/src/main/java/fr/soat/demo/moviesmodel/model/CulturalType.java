package fr.soat.demo.moviesmodel.model;

/**
 * Created by yann_huriez on 02/02/17.
 */

public enum CulturalType {
    MOVIE("movie"),
    SERIES("series");


    private final String type;

    CulturalType(String type) {
        this.type = type;
    }

    public static CulturalType typeFromString(String type){
        for (CulturalType culturalType : CulturalType.values()) {
            if(culturalType.type.equalsIgnoreCase(type)){
                return culturalType;
            }
        }
        return null;
    }
}

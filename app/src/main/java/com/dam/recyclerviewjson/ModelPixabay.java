package com.dam.recyclerviewjson;

import android.net.Uri;

public class ModelPixabay {
    private String imageUrl;
    private String auteur;
    private int nbLikes;


    public ModelPixabay() {
    }

    public ModelPixabay(String imageUrl, String auteur, int nbLikes) {
        this.imageUrl = imageUrl;
        this.auteur = auteur;
        this.nbLikes = nbLikes;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

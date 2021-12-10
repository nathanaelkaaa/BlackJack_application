package com.fr.blackjack;

public class User {
    private String mdp;
    private String mail;
    private String pseudo;
    private String photo;
    private double solde;
    private long rewardClock;
    private int gagne;
    private int perdu;


    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public long getRewardClock() { return rewardClock; }

    public void setRewardClock(long rewardClock) { this.rewardClock = rewardClock; }

    public int getGagne() { return gagne; }

    public void setGagne(int gagne) { this.gagne = gagne; }

    public int getPerdu() { return perdu; }

    public void setPerdu(int perdu) { this.perdu = perdu; }

    public User(String mdp, String mail, String pseudo, String photo,double solde, long rewardClock, int gagne, int perdu) {

        this.mdp = mdp;
        this.mail = mail;
        this.pseudo = pseudo;
        this.photo = photo;
        this.solde = solde;
        this.rewardClock = rewardClock;
        this.gagne = gagne;
        this.perdu = perdu;
    }
}

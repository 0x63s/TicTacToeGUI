package ch.fhnw.stefan_kenan.tictactoegui.model;

import java.time.LocalDateTime;

public class User {
    private static User instance;
    private int wins;
    private int losses;
    private int draws;
    private String token;
    private LocalDateTime userExpiry;
    private String userName;

    private static boolean userCreated = false;

    public User() {
    }

    public void clearUser(){
        if(!userCreated){
            return;
        }
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.token = "";
        this.userExpiry = null;
        this.userName = "";
        userCreated = false;
    }

    public static boolean isUserCreated(){
        return userCreated;
    }

    public void createUser(String userName, String token, LocalDateTime userExpiry) {
        if(userCreated){
            return;
        }
        this.userName = userName;
        this.token = token;
        this.userExpiry = userExpiry;
        userCreated = true;
    }

    public int getWins() {
        return wins;
    }

    public void addWin(){
        this.wins++;
    }

    public void addLoss(){
        this.losses++;
    }

    public void addDraw(){
        this.draws++;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getUserExpiry() {
        return userExpiry;
    }

    public void setUserExpiry(LocalDateTime userExpiry) {
        this.userExpiry = userExpiry;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userName, String token, LocalDateTime userExpiry) {
        this.userName = userName;
        this.token = token;
        this.userExpiry = userExpiry;
    }

}

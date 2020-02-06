package com.example.demo.domain;

public class User {
    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;



    String id;
    String name;
    String password;

    Level level;
    int login;
    int recommend;

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if(nextLevel == null){
            throw new IllegalArgumentException(this.level + "은 업그레이드가 불가능 합니다.");
        }else{
            this.level = nextLevel;
        }
    }
    
    public User(){}

    public User(String id, String name, String password, Level level, int login, int recommend ){
        this.id= id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public Level getLevel() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return this.login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return this.recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}    

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
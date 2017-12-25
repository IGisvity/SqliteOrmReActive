package com.igisvity.sqliteormreactive;

import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;

/**
 * Created by Administrator on 2017/12/25.
 */

@Table(name = "user",database = AppData.class)
public class User extends Model {

    @PrimaryKey
    private long id;


    @Column(name = "userid")
    private String userid;

    @Column(name = "username")
    private String userName;

    @Column(name = "sex")
    private int sex;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private double weiht;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeiht() {
        return weiht;
    }

    public void setWeiht(double weiht) {
        this.weiht = weiht;
    }
}

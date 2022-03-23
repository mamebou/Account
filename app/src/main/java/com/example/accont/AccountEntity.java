package com.example.accont;

import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

@Entity(tableName = "accounts")
public class AccountEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private int id;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "income")
    private int income;

    @ColumnInfo(name = "outgo")
    private int outgo;

    @ColumnInfo(name = "time")
    private String time;

    public AccountEntity(String content, int income, int outgo, String time) {
        this.content = content;
        this.income = income;
        this.outgo = outgo;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIncome() {
        return income;
    }

    public int getOutgo() {
        return outgo;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}

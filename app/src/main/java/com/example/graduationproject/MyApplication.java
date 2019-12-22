package com.example.graduationproject;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.graduationproject.greendao.DaoMaster;
import com.example.graduationproject.greendao.DaoSession;

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MyApplication instances;
    @Override    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
    }
    public static MyApplication getInstances(){
        return instances;
    }
    /**
     * 设置greenDao
     * 通过 DaoMaster 的内部类 DevOpenHelper,创建一个名为person_db的数据库
     * 通过getWritableDatabase()得到一SQLiteOpenHelper 对象。
     */
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "person_db", null);
        db = mHelper.getWritableDatabase();
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    //获取DaoSession
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    //获取db
    public SQLiteDatabase getDb() {
        return db;
    }
}

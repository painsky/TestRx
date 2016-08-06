package com.test.learn.testormlimit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.test.learn.testormlimit.beans.Student;

import java.sql.SQLException;

/**
 * Created by pain on 16/8/6.
 */
public class DbUtils extends OrmLiteSqliteOpenHelper {
    private Dao<Student,Integer> studentDao;
    public DbUtils(Context context) {
        super(context, "a.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource,Student.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Student, Integer> getStudentDao() throws SQLException {
        if (studentDao==null){
            studentDao=getDao(Student.class);
        }
        return studentDao;
    }

}

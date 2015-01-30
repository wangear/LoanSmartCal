package wangjoo.pangloancalculator;

import android.app.Activity;
import android.app.Application;

import java.sql.SQLException;

/**
 * Created by wang on 2014-10-24.
 */
public class PangLoanCalApp extends Application {
//	private DBOpenHelper mDbOpenHelper;
//	private Activity showActivity;

	@Override
	public void onCreate() {
		super.onCreate();
		init();

	}

	private void init(){
//		if(mDbOpenHelper == null){
//			mDbOpenHelper = new DBOpenHelper(this);
//		}
//
//		try{
//			mDbOpenHelper.open();
//		}catch(SQLException e){
//			e.printStackTrace();
//		}


	}

//	public DBOpenHelper getDataBaseHelper() {
//
//		if (mDbOpenHelper == null) {
//			mDbOpenHelper = new DBOpenHelper(this);
//		}
//
//		try {
//			mDbOpenHelper.open();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return mDbOpenHelper;
//	}

//	public void arrayUpdate(){
//		((ShowAccountActivity)showActivity).setAccountArray();
//
//	}

//	public void setActivity(Activity act){
//		showActivity = act;
//	}
}

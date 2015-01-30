package wangjoo.pangloancalculator;

import android.provider.BaseColumns;

/**
 * Created by wang on 2014-10-24.
 */
public class LoanDataBases {
	public static final String DATABASE_NAME = "pangoan.db";
	public static final int DATA_BASE_VERSION = 1;

	public static final class LoanDB implements BaseColumns {
		public static final String ACCOUNT_TABLE = "account_table";
		public static final String HISTORY_TABLE = "history_table";


		public static final String ACCOUNT_ID = "account_id";
		public static final String ACCOUNT_NUMBER = "account_number";
		public static final String INTEREST_RATE = "interest_rate";
		public static final String AMOUNT = "amount";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String TEMP_PERIOD = "temp_period";

		public static final String REPAY_DATE = "repay_date";
		public static final String LAST_REPAY_DATE = "last_repay_date";
		public static final String INSERTED_COUNT = "inserted_count";
		public static final String TOTAL_COUNT = "total_count";
		public static final String INTEREST = "interest";
		public static final String REST_MONEY = "rest_moeny";
		public static final String REPAYMENT = "repayment";
		public static final String PRINCIPAL = "principal";

		public static final int N_ACCOUNT_ID = 0;
		public static final int N_ACCOUNT_NUMBER = 1;
		public static final int N_AMOUNT = 2;
		public static final int N_INTEREST_RATE = 3;
		public static final int N_START_DATE = 4;
		public static final int N_END_DATE = 5;
		public static final int N_TEMP_PERIOD = 6;

		//		public static final int N_LAST_REPAY_DATE = 7;
//		public static final int N_INSERTED_COUNT = 8;
		public static final int N_TOTAL_COUNT = 7;

		public static final int N_REPAY_DATE = 1;
		public static final int N_REPAYMENT = 2;
		public static final int N_PRINCIPAL = 3;
		public static final int N_INTEREST = 4;
		public static final int N_INTEREST_RATE_IN_REPAYMENT = 5;
		public static final int N_REST_MONEY = 6;


		public static final String HISTORY_PROJECTION = "( " + REPAY_DATE + ","
				+ REPAYMENT + ", " + PRINCIPAL + "," + INTEREST + "," + REST_MONEY
				+ "," + INTEREST_RATE + ")";

		public static final String CREATE_ACCOUNT_TABLE =
				"create table " + ACCOUNT_TABLE + "("
						+ ACCOUNT_ID + " integer primary key autoincrement, "
						+ ACCOUNT_NUMBER + " text not null , "
						+ AMOUNT + " double , "
						+ INTEREST_RATE + " double , "
						+ START_DATE + " text not null, "
						+ END_DATE + " text not null, "
						+ TEMP_PERIOD + " integer ,"
//						+ LAST_REPAY_DATE + " text null,"
//						+ INSERTED_COUNT + " integer , "
						+ TOTAL_COUNT + " integer ,"


						+ REPAYMENT + " double );";
		public static final String CREATE_HISTORY_TABLE =
				"create table " + HISTORY_TABLE + "("
						+ _ID + " integer primary key autoincrement, "
//						+ ACCOUNT_ID + " text not null ,"
						+ REPAY_DATE + " text not null,"
						+ REPAYMENT + " double ,"
						+ PRINCIPAL + " double , "
						+ INTEREST + " double , "
						+ INTEREST_RATE + " double , "
						+ REST_MONEY + " double ); ";
//						+ "FOREIGN KEY(" + ACCOUNT_ID + ") REFERENCES "
//						+ ACCOUNT_TABLE + "(" + ACCOUNT_ID + "));";


	}
}



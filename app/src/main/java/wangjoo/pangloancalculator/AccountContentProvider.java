package wangjoo.pangloancalculator;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

import java.sql.SQLException;

/**
 * Created by wang on 2014-11-03.
 */
public class AccountContentProvider extends ContentProvider {
	public static final String AUTHORITY = "wangjoo.pangloancalculator";
	public static SQLiteDatabase mDb;
	private DatabaseHelper mDBHelper;
	private Context mCtx;
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	public static final Uri HISTORY_URI = Uri.parse("content://" + AUTHORITY + "/history");
	private static final UriMatcher uriMatcher;

	public static final int ACCOUNT = 1;
	public static final int HISTORY = 2;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "account", ACCOUNT);
		;
		uriMatcher.addURI(AUTHORITY, "history", HISTORY);
		;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
							  int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(LoanDataBases.LoanDB.CREATE_ACCOUNT_TABLE);
			db.execSQL(LoanDataBases.LoanDB.CREATE_HISTORY_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + LoanDataBases.LoanDB.CREATE_ACCOUNT_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + LoanDataBases.LoanDB.CREATE_HISTORY_TABLE);
		}
	}

	@Override
	public boolean onCreate() {
		mCtx = getContext();

		mDBHelper = new DatabaseHelper(mCtx, LoanDataBases.DATABASE_NAME, null, LoanDataBases.DATA_BASE_VERSION);
		mDb = mDBHelper.getWritableDatabase();
		if (mDb == null)
			return false;
		else return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
			case ACCOUNT:
				qb.setTables(LoanDataBases.LoanDB.ACCOUNT_TABLE);
				break;
			case HISTORY:
				qb.setTables(LoanDataBases.LoanDB.HISTORY_TABLE);
				break;
			default:
				qb.setTables(LoanDataBases.LoanDB.ACCOUNT_TABLE);
				break;
		}
		Cursor c = qb.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;

	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		long row = 0;
		Uri newUri = null;
		switch (uriMatcher.match(uri)) {
			case ACCOUNT:
				row = mDb.insert(LoanDataBases.LoanDB.ACCOUNT_TABLE, "", contentValues);
				newUri = ContentUris.withAppendedId(CONTENT_URI, row);
				break;
			case HISTORY:
				row = mDb.insert(LoanDataBases.LoanDB.HISTORY_TABLE, "", contentValues);
				newUri = ContentUris.withAppendedId(HISTORY_URI, row);
				break;
			default:
				row = mDb.insert(LoanDataBases.LoanDB.ACCOUNT_TABLE, "", contentValues);
				newUri = ContentUris.withAppendedId(CONTENT_URI, row);
				break;
		}

		if (row > 0) {
//			 newUri = ContentUris.withAppendedId(CONTENT_URI, row);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;
		}
		try {
			throw new SQLException("Fail to add a new record into " + uri);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		SQLiteStatement statement;
		String table;

		int row = 0;
		Uri newUri = null;
		switch (uriMatcher.match(uri)) {
			case ACCOUNT:
				table = LoanDataBases.LoanDB.ACCOUNT_TABLE;
				break;
			case HISTORY:
				table = LoanDataBases.LoanDB.HISTORY_TABLE;
				break;
			default:
				table = LoanDataBases.LoanDB.ACCOUNT_TABLE;
				break;
		}
		try {
			if (values != null && values.length > 0) {
				mDb.beginTransaction();
				statement = mDb.compileStatement("INSERT INTO " + table + LoanDataBases.LoanDB.HISTORY_PROJECTION
						+ " values(?,?,?,?,?,?)");

				for (ContentValues value : values) {
					int column = 1;
					statement.bindString(column++, value.getAsString(LoanDataBases.LoanDB.REPAY_DATE));
					statement.bindDouble(column++, value.getAsDouble(LoanDataBases.LoanDB.REPAYMENT));
					statement.bindDouble(column++, value.getAsDouble(LoanDataBases.LoanDB.PRINCIPAL));
					statement.bindDouble(column++, value.getAsDouble(LoanDataBases.LoanDB.INTEREST));
					statement.bindDouble(column++, value.getAsDouble(LoanDataBases.LoanDB.REST_MONEY));
					statement.bindDouble(column++, value.getAsDouble(LoanDataBases.LoanDB.INTEREST_RATE));

					statement.execute();

					row = row + 1;
				}
				statement.close();
				mDb.setTransactionSuccessful();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			row = -1;
		} finally {
			if (mDb != null) {
				mDb.endTransaction();
			}
		}
		getContext().getContentResolver().notifyChange(uri, null);

		return row;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
			case ACCOUNT:
				count = mDb.delete(LoanDataBases.LoanDB.ACCOUNT_TABLE, selection, selectionArgs);
				break;
			case HISTORY:
				count = mDb.delete(LoanDataBases.LoanDB.HISTORY_TABLE, selection, selectionArgs);
				break;
			default:
				count = mDb.delete(LoanDataBases.LoanDB.ACCOUNT_TABLE, selection, selectionArgs);
				break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
		return 0;
	}
}

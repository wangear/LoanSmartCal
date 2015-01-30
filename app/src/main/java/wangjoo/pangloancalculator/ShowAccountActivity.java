package wangjoo.pangloancalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.security.KeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ShowAccountActivity extends Activity {

	private ListView lvAccountList;
	private TextView tvAccountNumber;
	private TextView tvAmount;
	private TextView tvInterestRate;
	private TextView tvStartDate;
	private TextView tvEndDate;
	private TextView tvTempPeriod;
	private TextView tvRepaymentThisMonth;
	private TextView tvInsertedCount;
	private TextView tvTotalCount;
	private TextView tvInterest;
	private TextView tvRepayment;
	private MyObserver observerAccount;
	private MyObserver observerHistory;
	private RelativeLayout rlAccountLayout;

	private AccountListAdapter mListAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_account);

		lvAccountList = (ListView) findViewById(R.id.lv_account_list);
		tvAccountNumber = (TextView) findViewById(R.id.tv_account_number_value);
		tvAmount = (TextView) findViewById(R.id.tv_amount_value);
		tvInterestRate = (TextView) findViewById(R.id.tv_interest_rate_value);
		tvStartDate = (TextView) findViewById(R.id.tv_start_date_value);
		tvEndDate = (TextView) findViewById(R.id.tv_end_date_value);
		tvTempPeriod = (TextView) findViewById(R.id.tv_temp_period);
//		tvLastRepayDate = (TextView) findViewById(R.id.tv_last_repay_date_value);
		tvInsertedCount = (TextView) findViewById(R.id.tv_inserted_count_value);
		tvTotalCount = (TextView) findViewById(R.id.tv_total_count_value);
//		tvInterest = (TextView) findViewById(R.id.tv_interest_value);
		tvRepayment = (TextView) findViewById(R.id.tv_repayment_this_month_value);
		tvRepaymentThisMonth = (TextView) findViewById(R.id.tv_repayment_this_month_value);
		rlAccountLayout = (RelativeLayout) findViewById(R.id.rl_account_layout);
		mListAdapter = new AccountListAdapter(this);
		lvAccountList.setAdapter(mListAdapter);

		// db observer 등록
		observerAccount = new MyObserver(new Handler(), AccountContentProvider.ACCOUNT);
		observerHistory = new MyObserver(new Handler(), AccountContentProvider.HISTORY);
		getContentResolver().registerContentObserver(AccountContentProvider.CONTENT_URI, true, observerAccount);
		getContentResolver().registerContentObserver(AccountContentProvider.HISTORY_URI, true, observerHistory);
		// 화면 갱신
		invalidateAccountInfo();
		// 리스트 갱신
		invalidateList();


		// 화면 갱신
		// 리스트 갱신
		// db 가져오기


		// array 갱신
//		setAccountArray();


//		lvAccountList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(ShowAccountActivity.this);
//				final int position = i;
//				builder.setTitle("Aciton");
//				final CharSequence[] items = {getResources().getString(R.string.modify_account), getResources().getString(R.string.delete_account)};
//				builder.setItems(items, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int item) {
//						if (item == Constants.OPTION_MODIFY) {
//							Intent intent = new Intent(ShowAccountActivity.this, CreateAccountActivity.class);
//							intent.putExtra("type", Constants.TYPE_MODIFY);
//							intent.putExtra("accountId", mAccountArray.get(position).getAccountId());
//							finish();
//							startActivity(intent);
//
//						} else if (item == Constants.OPTION_DELETE) {
//							int result = ((PangLoanCalApp) getApplication()).getDataBaseHelper().delete(mAccountArray.get(position).getAccountId());
//							if (result > 0) {
//								mAccountArray.remove(position);
//
//								showData(position);
//							}
//						}
//					}
//				});
//				AlertDialog alert = builder.create();
//				alert.show();
//
//				return false;
//			}
//		});

//		showData(mAccountPosition);


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_account, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		boolean hasAccount = hasAccount();
		if (hasAccount) {
			menu.getItem(Constants.ACTION_CREATE_ACCOUNT).setEnabled(false);
			menu.getItem(Constants.ACTION_REPAYMENT).setEnabled(true);
			menu.getItem(Constants.ACTION_REPAYMENT_CANCEL).setEnabled(true);
			menu.getItem(Constants.ACTION_MODIFY_INTEREST).setEnabled(true);
			menu.getItem(Constants.ACTION_DELETE_ACCOUNT).setEnabled(true);
		} else {
			menu.getItem(Constants.ACTION_CREATE_ACCOUNT).setEnabled(true);
			menu.getItem(Constants.ACTION_REPAYMENT).setEnabled(false);
			menu.getItem(Constants.ACTION_REPAYMENT_CANCEL).setEnabled(false);
			menu.getItem(Constants.ACTION_MODIFY_INTEREST).setEnabled(false);
			menu.getItem(Constants.ACTION_DELETE_ACCOUNT).setEnabled(false);
		}
//		if (mAccountArray.size() > 0)
//			menu.getItem(0).setEnabled(true);
//		else
//			menu.getItem(0).setEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_repayment) {
			showRepayment();
			return true;
		} else if (id == R.id.action_create_account) {
//			finish();
			startActivity(new Intent(ShowAccountActivity.this, CreateAccountActivity.class));
		} else if (id == R.id.action_delete_account) {
			showDeleteDialog();
//			deleteAccount();
		} else if (id == R.id.action_modify_interest) {
			showModifyInterest();
		} else if (id == R.id.action_repayment_cancel) {
			cancelRepayment();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onResume() {
		super.onResume();
//		Log.d("wangear","size = " + mAccountArray.size());
//		if (mAccountArray.size() > 0) {
//			checkTodayRepay();
//		}
//		showData(mAccountPosition);
	}


	private void setEmptyVisible(boolean visible) {
		LinearLayout emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
		LinearLayout contentLayout = (LinearLayout) findViewById(R.id.content_layout);

		if (visible) {
			emptyLayout.setVisibility(View.VISIBLE);
			contentLayout.setVisibility(View.INVISIBLE);
		} else {
			emptyLayout.setVisibility(View.INVISIBLE);
			contentLayout.setVisibility(View.VISIBLE);

		}
	}

	private void showRepayment() {
		DialogFragment repaymentFragment = new RepaymentFragment();
		repaymentFragment.show(getFragmentManager(), "repayment");
	}

	private void showModifyInterest() {
		DialogFragment repaymentFragment = new ModifyInterestFragment();
		repaymentFragment.show(getFragmentManager(), "modifyInterest");
	}

	public void calculateTempRepayment(double tempRepayment, String repayDate, int deletedRow) {
		try {
			ArrayList<RepaymentInfo> listData = mListAdapter.getDataList();
			int lastPosition = listData.size() - 1 - deletedRow;

			double amount = listData.get(lastPosition).getRestMoney() - tempRepayment;
			double interestRate = listData.get(lastPosition).getInterestRate();

			// 이자계산산

			// 환급액 리스트에 더하기
			RepaymentInfo tempRepaymentInfo = new RepaymentInfo();
			tempRepaymentInfo.setRepayment(0);
			tempRepaymentInfo.setDate(repayDate);
			tempRepaymentInfo.setPrincipal(tempRepayment);
			tempRepaymentInfo.setRestMoney(amount);
			tempRepaymentInfo.setInterestRate(interestRate);
			tempRepaymentInfo.setInterest(0);

			// 다시 계산!
			String lastStartDateString = listData.get(lastPosition).getDate();
			int totalCount = Integer.parseInt(tvTotalCount.getText().toString());
			int tempPeriod = Integer.parseInt(tvTempPeriod.getText().toString());
			int remainCount = deletedRow;
			int insertedCount = totalCount - remainCount;
			ArrayList<RepaymentInfo> list = new ArrayList<RepaymentInfo>();
			double repayment = Util.calculateRepayment(amount, insertedCount, totalCount, interestRate);

			double interest, principal, restMoney;

			// 총액 = rest money
			restMoney = amount;

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date lastStartDate, endDate;
			// + 1은 temp repay 때문이다.
			// 이자 도 추가될때 하나 더!
			ContentValues[] valuesArray = new ContentValues[remainCount + 1];
			ContentValues values;
			Calendar startCal = Calendar.getInstance();
			lastStartDate = format.parse(lastStartDateString);
			startCal.setTime(lastStartDate);

			list.add(tempRepaymentInfo);

			values = new ContentValues();
			values.put(LoanDataBases.LoanDB.REPAY_DATE, repayDate);
			values.put(LoanDataBases.LoanDB.INTEREST, 0);
			values.put(LoanDataBases.LoanDB.REPAYMENT, 0);
			values.put(LoanDataBases.LoanDB.PRINCIPAL, tempRepayment);
			values.put(LoanDataBases.LoanDB.REST_MONEY, amount);
			values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

			valuesArray[0] = values;

			for (int i = 0; i < remainCount; i++) {
				startCal.add(Calendar.MONTH, 1);
				RepaymentInfo info = new RepaymentInfo();

				interest = Util.calculateInterest(restMoney, interestRate);
				principal = repayment - interest;
				restMoney = restMoney - (repayment - interest);

				if (restMoney < 0) {
					repayment = repayment + restMoney;
					principal = principal + restMoney;
					restMoney = 0;
				}
				info.setDate(format.format(startCal.getTime()));
				info.setRepayment(repayment);
				info.setPrincipal(principal);
				info.setInterest(interest);
				info.setInterestRate(interestRate);
				info.setRestMoney(restMoney);

				// get interest

				list.add(info);

				// put db
				values = new ContentValues();
				values.put(LoanDataBases.LoanDB.REPAY_DATE, format.format(startCal.getTime()));
				values.put(LoanDataBases.LoanDB.INTEREST, interest);
				values.put(LoanDataBases.LoanDB.REPAYMENT, repayment);
				values.put(LoanDataBases.LoanDB.PRINCIPAL, principal);
				values.put(LoanDataBases.LoanDB.REST_MONEY, restMoney);
				values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

				valuesArray[i + 1] = values;

			}

			int row = getContentResolver().bulkInsert(AccountContentProvider.HISTORY_URI, valuesArray);

			Cursor historyCursor = getContentResolver().query(AccountContentProvider.HISTORY_URI, null, null, null, null);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public void calculateRepayment(double tempRepayment, String repayDate) {
		try {
			ArrayList<RepaymentInfo> listData = mListAdapter.getDataList();
			int lastPosition = mListAdapter.getDataPosition(repayDate);

			double amount = listData.get(lastPosition).getRestMoney() - tempRepayment;
			double lastRepaymnet = listData.get(lastPosition).getRepayment();
			double interestRate = listData.get(lastPosition).getInterestRate();

			// 이자계산산

			// 다시 계산!
			String lastStartDateString = listData.get(lastPosition).getDate();
			int totalCount = Integer.parseInt(tvTotalCount.getText().toString());
			int tempPeriod = Integer.parseInt(tvTempPeriod.getText().toString());
			int remainCount = totalCount - lastPosition - 1;
			int insertedCount = totalCount - remainCount;
			ArrayList<RepaymentInfo> list = new ArrayList<RepaymentInfo>();
			double repayment = lastRepaymnet;
			double interest, principal, restMoney;

			// 총액 = rest money
			restMoney = amount;

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date lastStartDate, endDate;
			// + 1은 temp repay 때문이다.
			// 이자 도 추가될때 하나 더!
			ContentValues[] valuesArray = new ContentValues[remainCount];
			ContentValues values;
			Calendar startCal = Calendar.getInstance();
			lastStartDate = format.parse(lastStartDateString);
			startCal.setTime(lastStartDate);

			for (int i = 0; i < remainCount; i++) {
				startCal.add(Calendar.MONTH, 1);
				RepaymentInfo info = new RepaymentInfo();

				interest = Util.calculateInterest(restMoney, interestRate);
				principal = repayment - interest;
				restMoney = restMoney - (repayment - interest);

				if (restMoney < 0) {
					repayment = repayment + restMoney;
					principal = principal + restMoney;
					restMoney = 0;
				}
				info.setDate(format.format(startCal.getTime()));
				info.setRepayment(repayment);
				info.setPrincipal(principal);
				info.setInterest(interest);
				info.setInterestRate(interestRate);
				info.setRestMoney(restMoney);

				// get interest

				list.add(info);

				// put db
				values = new ContentValues();
				values.put(LoanDataBases.LoanDB.REPAY_DATE, format.format(startCal.getTime()));
				values.put(LoanDataBases.LoanDB.INTEREST, interest);
				values.put(LoanDataBases.LoanDB.REPAYMENT, repayment);
				values.put(LoanDataBases.LoanDB.PRINCIPAL, principal);
				values.put(LoanDataBases.LoanDB.REST_MONEY, restMoney);
				values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

				valuesArray[i] = values;

			}

			int row = getContentResolver().bulkInsert(AccountContentProvider.HISTORY_URI, valuesArray);

			Cursor historyCursor = getContentResolver().query(AccountContentProvider.HISTORY_URI, null, null, null, null);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public void calculateModifiedInterestRate(double interestRate, String repayDate, int deletedRow) {
		try {
			ArrayList<RepaymentInfo> listData = mListAdapter.getDataList();
			int lastPosition = listData.size() - 1 - deletedRow;

			double amount = listData.get(lastPosition).getRestMoney();

			// 이자계산
			// 이자 변했다는걸 리스트에 추가!

			// 환급액 리스트에 더하기
			RepaymentInfo tempRepaymentInfo = new RepaymentInfo();
			tempRepaymentInfo.setRepayment(0);
			tempRepaymentInfo.setDate(repayDate);
			tempRepaymentInfo.setPrincipal(0);
			tempRepaymentInfo.setRestMoney(amount);
			tempRepaymentInfo.setInterestRate(interestRate);
			tempRepaymentInfo.setInterest(0);

			// 다시 계산!
			String lastStartDateString = listData.get(lastPosition).getDate();
			int totalCount = Integer.parseInt(tvTotalCount.getText().toString());
			int tempPeriod = Integer.parseInt(tvTempPeriod.getText().toString());
			int remainCount = deletedRow;
			int insertedCount = totalCount - remainCount;
			ArrayList<RepaymentInfo> list = new ArrayList<RepaymentInfo>();
			double repayment = Util.calculateRepayment(amount, insertedCount, totalCount, interestRate);

			double interest, principal, restMoney;

			// 총액 = rest money
			restMoney = amount;

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date lastStartDate, endDate;
			// + 1은 temp repay 때문이다.
			// 이자 도 추가될때 하나 더!
			ContentValues[] valuesArray = new ContentValues[remainCount + 1];
			ContentValues values;
			Calendar startCal = Calendar.getInstance();
			lastStartDate = format.parse(lastStartDateString);
			startCal.setTime(lastStartDate);

			list.add(tempRepaymentInfo);

			values = new ContentValues();
			values.put(LoanDataBases.LoanDB.REPAY_DATE, repayDate);
			values.put(LoanDataBases.LoanDB.INTEREST, 0);
			values.put(LoanDataBases.LoanDB.REPAYMENT, 0);
			values.put(LoanDataBases.LoanDB.PRINCIPAL, 0);
			values.put(LoanDataBases.LoanDB.REST_MONEY, amount);
			values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

			valuesArray[0] = values;

			for (int i = 0; i < remainCount; i++) {
				startCal.add(Calendar.MONTH, 1);
				RepaymentInfo info = new RepaymentInfo();

				interest = Util.calculateInterest(restMoney, interestRate);
				principal = repayment - interest;
				restMoney = restMoney - (repayment - interest);

				if (restMoney < 0) {
					repayment = repayment + restMoney;
					principal = principal + restMoney;
					restMoney = 0;
				}
				info.setDate(format.format(startCal.getTime()).toString());
				info.setRepayment(repayment);
				info.setPrincipal(principal);
				info.setInterest(interest);
				info.setInterestRate(interestRate);
				info.setRestMoney(restMoney);

				// get interest

				list.add(info);

				// put db
				values = new ContentValues();
				values.put(LoanDataBases.LoanDB.REPAY_DATE, format.format(startCal.getTime()).toLowerCase());
				values.put(LoanDataBases.LoanDB.INTEREST, interest);
				values.put(LoanDataBases.LoanDB.REPAYMENT, repayment);
				values.put(LoanDataBases.LoanDB.PRINCIPAL, principal);
				values.put(LoanDataBases.LoanDB.REST_MONEY, restMoney);
				values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

				valuesArray[i + 1] = values;

			}

			int row = getContentResolver().bulkInsert(AccountContentProvider.HISTORY_URI, valuesArray);

			Cursor historyCursor = getContentResolver().query(AccountContentProvider.HISTORY_URI, null, null, null, null);

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
//	private void checkTodayRepay() {
//		int monthGap = 0;
//		int insertedCount = 0;
//		int newInsertedCount = 0;
//		int totalCount = 0;
//		double newAmount = 0;
//		double amount = 0;
//		int interest = 0;
//		int repayment = 0;
//		double interestRate;
//		// 마지막 납입일이 이번달인가?
//		//1. get last repay date
//		//2. get today
//		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
//		Calendar todayCal = Calendar.getInstance();
//		Calendar lastRepayDateCal = Calendar.getInstance();
//		Date today = todayCal.getTime();
//		String todayString = format.format(today);
//		Log.d("wangear","today = " + todayString);
//		String lastRepayDateString = mAccountArray.get(mAccountPosition).getLastRepayDate();
//		String startDateString = mAccountArray.get(mAccountPosition).getStartDate();
//		String repayDateString = mAccountArray.get(mAccountPosition).getRepayDate();
//		Log.d("wangear","last repay date string = " + lastRepayDateString);
//		if (lastRepayDateString == null || lastRepayDateString.equalsIgnoreCase("")) {
//			Log.d("wangear","pass code");
//			// 마지막 납입일이 없다.
//			// 오늘과 시작일을 비교.
//			monthGap = Util.calculateMonthBetweenTwoDate(startDateString, todayString);
//			Log.d("wangear","monthGap = " + monthGap);
//			// 오늘 납입일이 지났는가?
//			if (!Util.checkPassedRepayDate(repayDateString)) {
//				// 마지막 납입일은 지난달
//				lastRepayDateCal.add(Calendar.MONTH, -1);
//			} else {
//				monthGap = monthGap + 1;
//			}
//			Log.d("wangear","monthGap2 = " + monthGap);
//			// 납입횟수 = gap
//			newInsertedCount = monthGap;
//		} else {
//			Log.d("wangear","monthGap3 = " + monthGap);
//			Log.d("wangear","last = " + lastRepayDateString);
//			Log.d("wangear","start = " + startDateString);
//			monthGap = Util.calculateMonthBetweenTwoDate(lastRepayDateString, todayString);
//
//			if (!Util.checkPassedRepayDate(repayDateString)) {
//				// 마지막 납입일은 지난달
//				lastRepayDateCal.add(Calendar.MONTH, -1);
//				monthGap = monthGap - 1;
//			} else {
////				monthGap = monthGap + 1;
//			}
//			// 납입 횟수 = 기존 insert + gap
//			insertedCount = mAccountArray.get(mAccountPosition).getInsertedCount();
////			insertedCount = Util.calculateMonthBetweenTwoDate(startDateString, lastRepayDateString);
//
//			Log.d("wangear","inserted count = " + insertedCount);
//			Log.d("wangear","monthGap count = " + monthGap);
//			newInsertedCount = insertedCount + monthGap;
//		}
//		lastRepayDateString = format.format(lastRepayDateCal.getTime());
////		lastRepayDateCal.setTime(today);
//
//		// 상환금 계산
//		interestRate = mAccountArray.get(mAccountPosition).getInterestRate();
//		amount = mAccountArray.get(mAccountPosition).getAmount();
//		totalCount = mAccountArray.get(mAccountPosition).getTotalCount();
//		repayment = Util.calculateRepayment(amount, newInsertedCount, totalCount, interestRate);
////		interest = Util.calculateInterest(amount, interestRate);
//
//
//
//		// TEST
//
//		// TEST 끝
//
//		// 변화가 있는지 확인
//		Log.d("wangear","last monthGap = " + monthGap);
//		if (monthGap > 0) {
//			ContentValues values = new ContentValues();
////			newAmount = amount - (repayment-interest);
//			for(int x = 0; x < monthGap;x++){
//				interest = Util.calculateInterest(amount, interestRate);
//				newAmount = amount - (repayment-interest);
//				amount = newAmount;
//			}
//
//			// db 업데이트
//			values.put(LoanDataBases.LoanDB.AMOUNT,Math.floor(newAmount));
//			values.put(LoanDataBases.LoanDB.INSERTED_COUNT,newInsertedCount);
//			values.put(LoanDataBases.LoanDB.TOTAL_COUNT,totalCount);
//			values.put(LoanDataBases.LoanDB.INTEREST,interest);
//			values.put(LoanDataBases.LoanDB.REPAYMENT,repayment);
//			values.put(LoanDataBases.LoanDB.LAST_REPAY_DATE,lastRepayDateString);
//			((PangLoanCalApp)getApplication()).getDataBaseHelper().update(mAccountArray.get(mAccountPosition).getAccountId(),values);
//			// array업데이트
//			// 총액
//			mAccountArray.get(mAccountPosition).setAmount(Math.floor(newAmount));
//
//			// 납입횟수
//			mAccountArray.get(mAccountPosition).setInsertedCount(newInsertedCount);
//			// 총횟수
//			mAccountArray.get(mAccountPosition).setTotalCount(totalCount);
//			// 이자
//			mAccountArray.get(mAccountPosition).setInterest(interest);
//			// 상환액
//			mAccountArray.get(mAccountPosition).setRepayment(repayment);
//			// 최종 납입 날짜
//			mAccountArray.get(mAccountPosition).setLastRepayDate(lastRepayDateString);
//			// view 업데이트
//
//
//
//		}
//
//		showData(mAccountPosition);
//	}


//	public void reCalculateRepayment(){
//		double interestRate;
//		double amount;
//		int totalCount;
//		double repayment;
//		double interest;
//		int insertedCount;
//		// 상환금 계산
//		interestRate = mAccountArray.get(mAccountPosition).getInterestRate();
//		amount = mAccountArray.get(mAccountPosition).getAmount();
//
//		Log.d("wangear","reCalculateRepayment amount = " + amount);
//		totalCount = mAccountArray.get(mAccountPosition).getTotalCount();
//		insertedCount = mAccountArray.get(mAccountPosition).getInsertedCount();
//		Log.d("wangear","reCalculateRepayment insertedCount = " + insertedCount);
//		Log.d("wangear","reCalculateRepayment totalCount = " + totalCount);
//		repayment = Util.calculateRepayment(amount, insertedCount, totalCount, interestRate);
//		Log.d("wangear","reCalculateRepayment repayment = " + repayment);
//		interest = Util.calculateInterest(amount, interestRate);
//
//			ContentValues values = new ContentValues();
//			// db 업데이트
//			values.put(LoanDataBases.LoanDB.AMOUNT,amount);
//			values.put(LoanDataBases.LoanDB.INSERTED_COUNT,insertedCount);
//			values.put(LoanDataBases.LoanDB.TOTAL_COUNT,totalCount);
//			values.put(LoanDataBases.LoanDB.INTEREST,interest);
//			values.put(LoanDataBases.LoanDB.REPAYMENT,repayment);
//			((PangLoanCalApp)getApplication()).getDataBaseHelper().update(mAccountArray.get(mAccountPosition).getAccountId(),values);
//			// array업데이트
//			// 총액
//			mAccountArray.get(mAccountPosition).setAmount(amount);
//			// 납입횟수
//			mAccountArray.get(mAccountPosition).setInsertedCount(insertedCount);
//			// 총횟수
//			mAccountArray.get(mAccountPosition).setTotalCount(totalCount);
//			// 이자
//			mAccountArray.get(mAccountPosition).setInterest(interest);
//			// 상환액
//			mAccountArray.get(mAccountPosition).setRepayment(repayment);
//			// view 업데이트
//
//			showData(mAccountPosition);
//
//	}

	private void invalidateAccountInfo() {
		// db 가져오기
		Cursor cursor = getContentResolver().query(AccountContentProvider.CONTENT_URI, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0) {
			setEmptyVisible(true);
			return;
		} else {
			setEmptyVisible(false);
		}
		cursor.moveToFirst();

		// 계산..repayment.
		// 화면 갱신 -layout
		tvAccountNumber.setText(cursor.getString(LoanDataBases.LoanDB.N_ACCOUNT_NUMBER));
		tvAmount.setText(Util.longDouble2String(0, cursor.getDouble(LoanDataBases.LoanDB.N_AMOUNT)));
		tvInterestRate.setText("" + cursor.getDouble(LoanDataBases.LoanDB.N_INTEREST_RATE));
		String startDateString = cursor.getString(LoanDataBases.LoanDB.N_START_DATE);
		tvStartDate.setText(startDateString);
		tvEndDate.setText("" + cursor.getInt(LoanDataBases.LoanDB.N_END_DATE));
		int tempPeriod = cursor.getInt(LoanDataBases.LoanDB.N_TEMP_PERIOD);
		int totalCount = cursor.getInt(LoanDataBases.LoanDB.N_TOTAL_COUNT);
		tvTempPeriod.setText("" + tempPeriod);
		tvTotalCount.setText("" + totalCount);

		// 이번달이 언제인지 계산
		Calendar thisMonth = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		String todayString = format.format(thisMonth.getTime());

		int monthGap = Util.calculateMonthBetweenTwoDate(startDateString, todayString);
		int insertedCount = monthGap - tempPeriod;
		if (insertedCount < 0)
			insertedCount = 0;
		tvInsertedCount.setText("" + insertedCount);
	}

	private void deleteAccount() {
		int row = getContentResolver().delete(AccountContentProvider.CONTENT_URI, null, null);
		int rowHistory = getContentResolver().delete(AccountContentProvider.HISTORY_URI, null, null);
		if (row > 0 || rowHistory > 0) {
			// 화면 갱신
			invalidateAccountInfo();
		}
	}

	@Override
	protected void onDestroy() {
		getContentResolver().unregisterContentObserver(observerAccount);
		getContentResolver().unregisterContentObserver(observerHistory);
		super.onDestroy();
	}

	public class MyObserver extends ContentObserver {
		private int type;

		public MyObserver(Handler handler, int type) {
			super(handler);
			this.type = type;
		}

		@Override
		public void onChange(boolean selfChange) {
			if (type == AccountContentProvider.ACCOUNT)
				invalidateAccountInfo();
			else if (type == AccountContentProvider.HISTORY)
				invalidateList();
			super.onChange(selfChange);
		}
	}

	private void invalidateList() {
		Log.d("wangear", "invalidateList");
		ArrayList<RepaymentInfo> list = new ArrayList<RepaymentInfo>();
		Cursor historyCursor = getContentResolver().query(AccountContentProvider.HISTORY_URI, null, null, null, null);
		if (historyCursor != null && historyCursor.getCount() != 0) {
			historyCursor.moveToFirst();
			Log.d("wangear", "cursor cont = " + historyCursor.getCount());
			RepaymentInfo info;
			do {
				info = new RepaymentInfo();
				info.setDate(historyCursor.getString(LoanDataBases.LoanDB.N_REPAY_DATE));
				info.setRepayment(historyCursor.getDouble(LoanDataBases.LoanDB.N_REPAYMENT));
				info.setPrincipal(historyCursor.getDouble(LoanDataBases.LoanDB.N_PRINCIPAL));
				info.setInterest(historyCursor.getDouble(LoanDataBases.LoanDB.N_INTEREST));
				info.setRestMoney(historyCursor.getDouble(LoanDataBases.LoanDB.N_REST_MONEY));
				info.setInterestRate(historyCursor.getDouble(LoanDataBases.LoanDB.N_INTEREST_RATE_IN_REPAYMENT));
				list.add(info);
			} while (historyCursor.moveToNext());
			mListAdapter.setDataList(list);
		}
		mListAdapter.notifyDataSetChanged();
		updateAccountInfoThisMonth();
	}

	private void updateAccountInfoThisMonth() {
		Calendar thisMonth = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		String todayString = format.format(thisMonth.getTime());

		RepaymentInfo info = mListAdapter.getData(todayString);
		if (info != null) {
			tvRepaymentThisMonth.setText("" + Util.longDouble2String(0, info.getRepayment()));
			tvInterestRate.setText("" + Util.longDouble2String(2, info.getInterestRate()));
		}
	}

	private boolean hasAccount() {
		Cursor cursor = getContentResolver().query(AccountContentProvider.CONTENT_URI, null, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void showDeleteDialog() {
		FragmentManager fm = getFragmentManager();
		DialogFragment dialog = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(getString(R.string.warning_delete)).setPositiveButton(getString(
						R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						deleteAccount();
					}
				}).setNegativeButton(getString(R.string.cancel)
						, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dismiss();
					}
				});
				return builder.create();
			}
		};
		dialog.setCancelable(true);
		dialog.show(fm, "DELETE_DIALOG_FRAGMENT");
	}

	private void cancelRepayment() {
		DialogFragment cancelRepaymentFragment = new CancelRepaymentFragment();
		cancelRepaymentFragment.show(getFragmentManager(), "cancelRepayment");
	}

	private void showExitFragment() {
		DialogFragment exitFragment = new AdmobFragment();
		exitFragment.show(getFragmentManager(), "exit");
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			showExitFragment();
			return false;
		} else
			return super.onKeyUp(keyCode, event);
	}
}

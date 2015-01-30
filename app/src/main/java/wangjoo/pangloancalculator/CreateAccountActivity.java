package wangjoo.pangloancalculator;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wang on 2014-10-24.
 */
public class CreateAccountActivity extends Activity {
	private int mAccountId;
	private EditText etAccountNumber;
	private EditText etAmount;
	private EditText etInterestRate;
	private Button btnStartDate;
	private EditText etTotalCount;
	private EditText etTempPeriod;
	private Button btnOk;
	private Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		etAccountNumber = (EditText) findViewById(R.id.et_account_number);
		etAmount = (EditText) findViewById(R.id.et_amount);
		etInterestRate = (EditText) findViewById(R.id.et_interest_rate);
		btnStartDate = (Button) findViewById(R.id.btn_start_date);
		etTempPeriod = (EditText) findViewById(R.id.et_temp_period);
		etTotalCount = (EditText) findViewById(R.id.et_total_count);
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);

//		Intent intent = getIntent();
//
//		int type = intent.getIntExtra("type", -1);
//		int accountId = intent.getIntExtra("accountId", -1);
//		Log.d("wangear", "type = " + type);
//		Log.d("wangear", "accountId = " + accountId);

		btnStartDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DialogFragment startDateFragment = new DatePickerFragment();

				showDatePickerDialog(view, startDateFragment, Constants.TYPE_START);
			}
		});


		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				createAccount();
			}
		});

//		if (type == Constants.TYPE_MODIFY) {
//			setAccountData(accountId);
//			btnOk.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					modifyAccount();
//				}
//			});
//		} else {

//		}
	}

//	private void modifyAccount() {
//		String accountNumber = String.valueOf(etAccountNumber.getText());
//		String amount = String.valueOf(etAmount.getText());
//		String interestRate = String.valueOf(etInterestRate.getText());
//
//		String startDate = btnStartDate.getText().toString();
//		int repaymentPeriod = Integer.parseInt(etTotalCount.getText().toString());
//		String repayDate = ettempPeriod.getText().toString();
//		String endDate= Util.calculateEndDate(startDate,repaymentPeriod);
//		ContentValues data = new ContentValues();
//
//		data.put(LoanDataBases.LoanDB.ACCOUNT_NUMBER, accountNumber);
//		data.put(LoanDataBases.LoanDB.AMOUNT, amount);
//		data.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);
//		data.put(LoanDataBases.LoanDB.START_DATE, startDate);
//		data.put(LoanDataBases.LoanDB.TOTAL_COUNT, repaymentPeriod);
//		data.put(LoanDataBases.LoanDB.END_DATE, endDate);
//		data.put(LoanDataBases.LoanDB.REPAY_DATE, repayDate);
//
//		((PangLoanCalApp) getApplication()).getDataBaseHelper().update(mAccountId, data);
//
//		((PangLoanCalApp) getApplication()).arrayUpdate();
//
//		finish();
//
//	}

//	private void setAccountData(int accountId) {
//
//		AccountInfo account = ((PangLoanCalApp) getApplication()).getDataBaseHelper().getAccount(accountId);
//		mAccountId = account.getAccountId();
//		etAccountNumber.setText(account.getAccountNumber());
//		etAmount.setText(""+account.getAmount());
//		etInterestRate.setText(""+account.getInterestRate());
//		String startDateString = account.getStartDate();
//		int repaymentPeriod = account.getTotalCount();
//		String repayDateString = account.getRepayDate();
//		btnStartDate.setText(startDateString);
//		etTotalCount.setText(repaymentPeriod);
//		etTempPeriod.setText(repayDateString);
//
//	}

	private void createAccount() {
		Log.d("wangear", "createAccount");

		String accountNumber = String.valueOf(etAccountNumber.getText());
		String amount = String.valueOf(etAmount.getText());
		String interestRate = String.valueOf(etInterestRate.getText());

		String startDate = btnStartDate.getText().toString();
		if(startDate == null || startDate.equalsIgnoreCase("") || startDate.equalsIgnoreCase(
				getResources().getString(R.string.set_date))){
			Toast.makeText(this, getResources().getString(R.string.invalid_date),Toast.LENGTH_LONG).show();
			return;
		}


		String tempPeriodStr = etTempPeriod.getText().toString();
		int tempPeriod = 0;
		if (tempPeriodStr == null || tempPeriodStr.equalsIgnoreCase("")) {
			tempPeriod = 0;
		} else {
			tempPeriod = Integer.parseInt(tempPeriodStr);
		}
		String totalCountString = etTotalCount.getText().toString();
		if(totalCountString == null || totalCountString.equalsIgnoreCase("") || totalCountString.equalsIgnoreCase(
				getResources().getString(R.string.set_date))){
			Toast.makeText(this, getResources().getString(R.string.invalid_total_count),Toast.LENGTH_LONG).show();
			return;
		}
		int totalCount = Integer.parseInt(totalCountString);
		int repaymentPeriod = totalCount - tempPeriod;
		String endDate = Util.calculateEndDate(startDate, totalCount);

		if(totalCount < repaymentPeriod){
			Toast.makeText(this, getString(R.string.invalid_total_count),Toast.LENGTH_LONG);
			return;
		}
		ContentValues data = new ContentValues();

//		Log.d("wangear", "accountNumber = " + accountNumber);
//		Log.d("wangear", "amount = " + amount);
//		Log.d("wangear", "interestRate = " + interestRate);

		data.put(LoanDataBases.LoanDB.ACCOUNT_NUMBER, accountNumber);
		data.put(LoanDataBases.LoanDB.AMOUNT, amount);
		data.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);
		data.put(LoanDataBases.LoanDB.START_DATE, startDate);
		data.put(LoanDataBases.LoanDB.TOTAL_COUNT, totalCount);
		data.put(LoanDataBases.LoanDB.END_DATE, endDate);
		data.put(LoanDataBases.LoanDB.TEMP_PERIOD, tempPeriod);
//		data.put(LoanDataBases.LoanDB.LAST_REPAY_DATE, startDate);

		getContentResolver().insert(AccountContentProvider.CONTENT_URI, data);
		calculateRepayment();
//		((PangLoanCalApp) getApplication()).getDataBaseHelper().insert(data);
//
//		((PangLoanCalApp) getApplication()).arrayUpdate();

		finish();
//		startActivity(new Intent(this, ShowAccountActivity.class));

	}


	private void showDatePickerDialog(View v, DialogFragment dateFragment, int type) {
		dateFragment.show(getFragmentManager(), "datePicker");
		Calendar calendar = Calendar.getInstance();
		String dateString = ((Button) v).getText().toString();

		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

		try {
			Bundle args = new Bundle();

			if (!dateString.equalsIgnoreCase(getResources().getString(R.string.set_date))) {
				Date date = format.parse(dateString);
				calendar.setTime(date);
			}
			args.putInt("year", calendar.get(Calendar.YEAR));
			args.putInt("month", calendar.get(Calendar.MONTH));
			args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
			args.putInt("type", type);
			dateFragment.setArguments(args);
			((DatePickerFragment) dateFragment).setButtonView(btnStartDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private void calculateRepayment() {
		try {
			double interestRate = Double.parseDouble(etInterestRate.getText().toString());
			double amount = Double.parseDouble(etAmount.getText().toString());
			int totalCount = Integer.parseInt(etTotalCount.getText().toString());

			ArrayList<RepaymentInfo> list = new ArrayList<RepaymentInfo>();
			double repayment = Util.calculateRepayment(amount, 0, totalCount, interestRate);
			double interest, principal, restMoney;
			int tempPeriod = Integer.parseInt(etTempPeriod.getText().toString());

			restMoney = amount;

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date startDate, endDate;
			ContentValues[] valueArray = new ContentValues[totalCount];

			ContentValues values;
			Calendar startCal = Calendar.getInstance();
			try {
				startDate = format.parse(btnStartDate.getText().toString());

				startCal.setTime(startDate);

				for (int i = 0; i < tempPeriod; i++) {
					startCal.add(Calendar.MONTH, 1);

					RepaymentInfo info = new RepaymentInfo();
					info.setDate(format.format(startCal.getTime()));

					interest = Util.calculateInterest(restMoney, interestRate);

					info.setInterest(interest);
					info.setInterestRate(interestRate);
					info.setRepayment(0);
					info.setPrincipal(0);
					info.setRestMoney(restMoney);
					list.add(info);

					// put db
					values = new ContentValues();
					values.put(LoanDataBases.LoanDB.REPAY_DATE, format.format(startCal.getTime().toString()));
					values.put(LoanDataBases.LoanDB.INTEREST, interest);
					values.put(LoanDataBases.LoanDB.REPAYMENT, 0);
					values.put(LoanDataBases.LoanDB.PRINCIPAL, 0);
					values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);
					values.put(LoanDataBases.LoanDB.REST_MONEY, restMoney);

					valueArray[i] = values;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < totalCount - tempPeriod; i++) {
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
				info.setRestMoney(restMoney);
				info.setInterestRate(interestRate);

				list.add(info);

				// pub db
				values = new ContentValues();
				values.put(LoanDataBases.LoanDB.REPAY_DATE, format.format(startCal.getTime()));
				values.put(LoanDataBases.LoanDB.INTEREST, interest);
				values.put(LoanDataBases.LoanDB.REPAYMENT, repayment);
				values.put(LoanDataBases.LoanDB.PRINCIPAL, principal);
				values.put(LoanDataBases.LoanDB.REST_MONEY, restMoney);
				values.put(LoanDataBases.LoanDB.INTEREST_RATE, interestRate);

				valueArray[i + tempPeriod] = values;
			}

			int row = getContentResolver().bulkInsert(AccountContentProvider.HISTORY_URI, valueArray);
			Log.d("wangear","bulk row = " + row);

			Cursor historyCursor = getContentResolver().query(AccountContentProvider.HISTORY_URI, null, null, null, null);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
package wangjoo.pangloancalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wang on 2014-10-29.
 */
public class ModifyInterestFragment extends DialogFragment {
	private AccountInfo mAccount;
	private EditText mEtInterestRate;
	private Button mBtnModifyDate;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
		View inflateView = mLayoutInflater.inflate(R.layout.modify_interest_frag, null);
		mBuilder.setView(inflateView);

		Button btnRepayment = (Button) inflateView.findViewById(R.id.btn_repayment);
		mBtnModifyDate = (Button) inflateView.findViewById(R.id.btn_repay_date);
		mBtnModifyDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DialogFragment startDateFragment = new DatePickerFragment();
				showDatePickerDialog(view, startDateFragment);
			}
		});
		mEtInterestRate = (EditText) inflateView.findViewById(R.id.et_interest_rate_value);
//		mEtInterestRate.setText(Util.longDouble2String(1, mAccount.getRepayment()));
		btnRepayment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				double interestRate = Double.parseDouble(mEtInterestRate.getText().toString());
				modifyInterestRate(interestRate);

			}
		});
		mBuilder.setTitle(getResources().getString(R.string.repayment));
		mBuilder.setMessage(getResources().getString(R.string.repayment_msg));
		return mBuilder.create();

	}

	private void modifyInterestRate(double interestRate) {
		// repay date 기준 이하 db data 삭제
		String where = LoanDataBases.LoanDB.REPAY_DATE + " > '" + mBtnModifyDate.getText() + "'";

		int rowHistory = getActivity().getContentResolver().delete(AccountContentProvider.HISTORY_URI, where, null);
		String modifyDate = mBtnModifyDate.getText().toString();
		if(modifyDate == null || modifyDate.equalsIgnoreCase("") || modifyDate.equalsIgnoreCase(getString(R.string.set_date))){
			Toast.makeText(getActivity().getBaseContext(), getString(R.string.invalid_date),Toast.LENGTH_LONG).show();
			return;
		}

		// amount를 바꿔서 다시 계산
		((ShowAccountActivity) getActivity()).calculateModifiedInterestRate(interestRate, modifyDate, rowHistory);

		// list update
		dismiss();
	}

	@Override
	public void onStop() {
		super.onStop();

	}

	private void showDatePickerDialog(View v, DialogFragment dateFragment) {
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
			((DatePickerFragment) dateFragment).setButtonView(mBtnModifyDate);
			dateFragment.setArguments(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onCancel(DialogInterface dialog) {
		mBtnModifyDate.setText(getString(R.string.set_date));
		super.onCancel(dialog);
	}
}

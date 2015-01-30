package wangjoo.pangloancalculator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * Created by wang on 2014-10-29.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	private int year, month, day;
	private int initYear, initMonth;
	private String title = "date picker";
	private Button btnView;

//	public DatePickerFragment() {
//		super();
//	}

//	public DatePickerFragment(int type) {
//		this.type = type;
//		new DatePickerFragment();
//
//	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		initYear = year;
		initMonth = month;


		Dialog dialog = new CustomDatePickerDialog(getActivity(), this, year, month, day);
		((CustomDatePickerDialog)dialog).setFixedTitle("DatePicker");;
			return dialog;

		}

		@Override
		public void onDateSet (DatePicker datePicker,int year, int month, int day){

			btnView.setText(year + "-" + Util.addZeroNumber(month+1));

//			if (type == Constants.TYPE_REPAY) {
//				((Button) getActivity().findViewById(R.id.btn_repay_date)).setText("" + day);
//			}
		}
		@Override
		public void setArguments (Bundle args){
			super.setArguments(args);
			year = args.getInt("year");
			month = args.getInt("month");
			day = args.getInt("day");

		}

	public void setButtonView(Button view){
		btnView = view;
	}
		@Override
		public void onCancel (DialogInterface dialog){
			super.onCancel(dialog);
				btnView.setText(getResources().getString(R.string.set_date));

		}


}

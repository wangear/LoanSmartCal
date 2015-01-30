package wangjoo.pangloancalculator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by wang on 2014-11-03.
 */
public class CustomDatePickerDialog extends DatePickerDialog {
	private String TITLE = null;

	public CustomDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);

		try {
			Field[] datePickerDialogFields = DatePickerDialog.class.getDeclaredFields();
			for (Field datePickerDialogField : datePickerDialogFields) {
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField.get(this);

					Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();

					for (Field datePickerField : datePickerFields) {
						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField.getName())) {
//						||"mMonthPicker".equals(
//								datePickerField.getName())
//								|| "mMonthSpinner".equals(datePickerField.getName())){
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (
				Exception ex
				)

		{
			ex.printStackTrace();

		}

		if (TITLE != null)

			setTitle(TITLE);

	}

	public void setFixedTitle(String title) {
		TITLE = title;
		setTitle(TITLE);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		if (TITLE != null)
			setTitle(TITLE);
	}
}

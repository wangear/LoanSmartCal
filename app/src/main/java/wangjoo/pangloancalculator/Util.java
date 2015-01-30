package wangjoo.pangloancalculator;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wang on 2014-10-30.
 */
public class Util {

	public static boolean checkValidateDate(String startStr, String endStr) {
		try {
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

			Date startDate = format.parse(startStr);
			Date endDate = format.parse(endStr);

			startCal.setTime(startDate);
			endCal.setTime(endDate);
			return startCal.before(endCal);

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static String calculateEndDate(String startStr, int repaymentPriod) {
		try {
			String endStr;
			Calendar startCal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date startDate = format.parse(startStr);

			startCal.setTime(startDate);
			startCal.add(Calendar.MONTH, repaymentPriod);
			endStr = format.format(startCal.getTime());
			return endStr;

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int calculateMonthBetweenTwoDate(String srcStr, String targetStr) {
		try {
			int srcYear, srcMonth, targetYear, targetMonth;
			Calendar srcCal = Calendar.getInstance();
			Calendar targetCal = Calendar.getInstance();

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date srcDate = format.parse(srcStr);
			Date targetDate = format.parse(targetStr);

			srcCal.setTime(srcDate);
			targetCal.setTime(targetDate);

			srcYear = srcCal.get(Calendar.YEAR);
			targetYear = targetCal.get(Calendar.YEAR);
			srcMonth = srcCal.get(Calendar.MONTH);
			targetMonth = targetCal.get(Calendar.MONTH);

			return (targetYear - srcYear) * 12 + (targetMonth - srcMonth);


		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static boolean checkPassedRepayDate(String repayDate) {
		Calendar todayCal = Calendar.getInstance();
		if (todayCal.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(repayDate)) {
			return true;
		}
		return false;
	}

	public static int calculateRepayment(double amount, int insertedCount, int totalCount, double interestRate) {
		int remainMonth = 0;
		double monthlyInterestRate = interestRate / 100 / 12;
		double a, b;
		a = (1 + monthlyInterestRate);
		remainMonth = totalCount - insertedCount;
		Log.d("wangear","remainMonth = " + remainMonth);
		double c = Math.pow(a, remainMonth);
		int repayment = (int) (amount * monthlyInterestRate * c / (c - 1));

		Log.d("wangear", "repayment = " + repayment);

		return repayment;
	}

	public static String addZeroNumber(int number){
		String value;
		if(number <10)
			return value = "0"+number;
		else
			return value = String.valueOf(number);
	}
	public static int calculateInterest(double amount, double interestRate) {
		double interest = 0;
		double monthlyInterestRate = interestRate / 100 / 12;
		interest = amount * monthlyInterestRate;
		Log.d("wangear","interest = " + interest);
		return (int)(Math.floor(interest/10)*10);
	}

	public static String longDouble2String(int size, double value) {

		NumberFormat nf = NumberFormat.getNumberInstance();

		nf.setMaximumFractionDigits(size);
		nf.setMinimumFractionDigits(size);

		nf.setGroupingUsed(false);

		return nf.format(value);

	}

}

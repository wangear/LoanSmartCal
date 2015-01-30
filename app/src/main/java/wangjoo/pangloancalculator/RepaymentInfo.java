package wangjoo.pangloancalculator;

/**
 * Created by wang on 2014-11-04.
 */
public class RepaymentInfo {
	private String date;
	private double repayment;
	private double interest;
	private double principal;
	private double restMoney;
	private double interestRate;


	public double getRepayment() {
		return repayment;
	}

	public void setRepayment(double repayment) {
		this.repayment = repayment;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(double restMoney) {
		this.restMoney = restMoney;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}

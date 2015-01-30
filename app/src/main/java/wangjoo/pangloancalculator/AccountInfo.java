package wangjoo.pangloancalculator;

/**
 * Created by wang on 2014-10-26.
 */
public class AccountInfo {

	private int accountId;
	private String accountNumber;
	private double amount;
	private double interestRate;
	private double interest;
	private String startDate;
	private String endDate;
	private String lastRepayDate;
	private String repayDate;
	private int insertedCount;
	private int totalCount;
	private double repayment;



	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {

			this.startDate = startDate;

	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getLastRepayDate() {
		return lastRepayDate;
	}

	public void setLastRepayDate(String lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}


	public int getInsertedCount() {
		return insertedCount;
	}

	public void setInsertedCount(int insertedCount) {
		this.insertedCount = insertedCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public double getRepayment() {
		return repayment;
	}

	public void setRepayment(double repayment) {
		this.repayment = repayment;
	}
}

package wangjoo.pangloancalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CalculateActivity extends Activity {
	private TextView tvRepayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_culate);

		tvRepayment = (TextView)findViewById(R.id.tv_repayment_value);

		Button btnCalculate = (Button) findViewById(R.id.btn_calculate);
		btnCalculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				calculteRepayment();
			}
		});

    }

	private void calculteRepayment(){
		EditText etAmount = (EditText) findViewById(R.id.et_amount);
		EditText etTotalCount = (EditText) findViewById(R.id.et_remained_period);


		EditText etInterestRate = (EditText) findViewById(R.id.et_interest_rate);

		double amount = Double.parseDouble(etAmount.getText().toString());
		int totalCount = Integer.parseInt(etTotalCount.getText().toString());
		double interestRate = Double.parseDouble(etInterestRate.getText().toString());

		double repayment = Util.calculateRepayment(amount, 0, totalCount, interestRate);
		tvRepayment.setText(Util.longDouble2String(1,repayment));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cal_culate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_repayment) {
		
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (tvRepayment.getText() != null && !tvRepayment.getText().toString().equalsIgnoreCase(""))
			menu.getItem(0).setEnabled(true);
		else
			menu.getItem(0).setEnabled(false);

		return true;
	}
}

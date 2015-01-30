package wangjoo.pangloancalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		Button btnCreate = (Button) findViewById(R.id.btn_create);
		btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
			}
		});
		Button btnShow = (Button) findViewById(R.id.btn_show);
		btnShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, ShowAccountActivity.class));
			}
		});
		Button btnModify = (Button) findViewById(R.id.btn_modify);
		btnModify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
			}
		});
		Button btnDelete = (Button) findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
			}
		});

//		// check account
//		if(checkExistAccount())
//			startActivity(new Intent(this, ShowAccountActivity.class));
//		else
//			startActivity(new Intent(this, CreateAccountActivity.class));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//	private boolean checkExistAccount(){
//		return ((PangLoanCalApp)getApplication()).getDataBaseHelper().isExistAccount();
//	}
}

package wangjoo.pangloancalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by wang on 2014-11-12.
 */
public class AdmobFragment extends DialogFragment{

	private AdView mAdview;
	private Button mBtnOk;
	private Button mBtnCancel;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
		View inflateView = mLayoutInflater.inflate(R.layout.adview_layout,null);
		mAdview = (AdView)inflateView.findViewById(R.id.adView);
		AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("80F61B373FAF34A7DD4F6A88E274E02").build();
		mAdview.loadAd(request);

		mBuilder.setView(inflateView);

		mBtnOk = (Button)inflateView.findViewById(R.id.btn_ok);
		mBtnCancel = (Button)inflateView.findViewById(R.id.btn_cancel);
		mBtnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().finish();
			}
		});
		mBtnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
			}
		});

		mBuilder.setTitle(getResources().getString(R.string.exit));
		mBuilder.setMessage(getString(R.string.warinit_exit));
		return mBuilder.create();
	}


	@Override
	public void onResume() {
		super.onResume();
		if(mAdview != null){
			mAdview.resume();
		}
	}

	@Override
	public void onPause() {

		if(mAdview!= null)
			mAdview.pause();

		super.onPause();
	}

	@Override
	public void onDestroy() {
		if(mAdview != null)
			mAdview.destroy();
		super.onDestroy();

	}
}

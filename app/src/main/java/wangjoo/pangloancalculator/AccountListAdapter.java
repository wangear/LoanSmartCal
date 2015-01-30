package wangjoo.pangloancalculator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wang on 2014-11-02.
 */
public class AccountListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private ArrayList<RepaymentInfo> mData = new ArrayList<RepaymentInfo>();
	private ViewHolder viewholder;

	public AccountListAdapter(Context ctx){
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addData(RepaymentInfo data){
		mData.add(data);
	}

	public void setDataList(ArrayList<RepaymentInfo> list){
		mData.clear();
		mData = (ArrayList<RepaymentInfo>) list.clone();
		Log.d("wangear","list count = " + list.size() );
	}

	public ArrayList<RepaymentInfo> getDataList(){
		return mData;
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int i) {
		return mData.get(i);
	}

	public RepaymentInfo getData(String date){
		if(mData!= null && mData.size() > 0){
			for(int i = 0; i < mData.size(); i++){
				RepaymentInfo info = mData.get(i);
				if(date.equalsIgnoreCase(info.getDate())){
					return info;
				}
			}
		}
		return null;
	}

	public int getDataPosition(String date){
		if(mData.size() > 0){
			for (int i = 0; i < mData.size(); i++){
				RepaymentInfo info = mData.get(i);
				if(date.equalsIgnoreCase(info.getDate())){
					return i;
				}
			}
		}
		return 0;
	}
	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		if(convertView == null){
			viewholder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_layout,null);

			viewholder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			viewholder.tvRepayment = (TextView) convertView.findViewById(R.id.tv_repayment);
			viewholder.tvPrincipal = (TextView) convertView.findViewById(R.id.tv_principal);
			viewholder.tvInterest = (TextView) convertView.findViewById(R.id.tv_interest);
			viewholder.tvInterestRate = (TextView) convertView.findViewById(R.id.tv_interest_rate);
			viewholder.tvRestMoney = (TextView) convertView.findViewById(R.id.tv_rest_money);
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder)convertView.getTag();
		}
		RepaymentInfo info = mData.get(position);
		viewholder.tvDate.setText(info.getDate());
		viewholder.tvRepayment.setText("" + Util.longDouble2String(0,info.getRepayment()));
		viewholder.tvPrincipal.setText("" + Util.longDouble2String(0,info.getPrincipal()));
		viewholder.tvInterest.setText("" + Util.longDouble2String(0,info.getInterest()));
		viewholder.tvInterestRate.setText("" + Util.longDouble2String(2,info.getInterestRate()));
		viewholder.tvRestMoney.setText("" + Util.longDouble2String(0,info.getRestMoney()));
		return convertView;
	}

	class ViewHolder{
		public TextView tvDate = null;
		public TextView tvRepayment = null;
		public TextView tvInterest = null;
		public TextView tvInterestRate = null;
		public TextView tvPrincipal = null;
		public TextView tvRestMoney = null;


	}
}

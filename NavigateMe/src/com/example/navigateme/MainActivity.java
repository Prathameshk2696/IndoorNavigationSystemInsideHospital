
package com.example.navigateme;

import java.util.ArrayList;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter mBluetoothAdapter;
	private Button wai,nm;
	private Spinner sp;
	private Handler mHandler;
	private ArrayList<BluetoothDevice> mDevice;
	private ArrayList<Float> mSS;
	private String src,dst,sl;
	
	SharedPreferences spmain;
	SharedPreferences.Editor ed;
	
	@Override  //annotation
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		mHandler=new Handler();
		mDevice=new ArrayList<BluetoothDevice>();
		mSS=new ArrayList<Float>();
		
		spmain = getSharedPreferences("spmain", 0);
		
		
		if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
		{
			Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		final BluetoothManager bluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter=bluetoothManager.getAdapter();
		
		if(mBluetoothAdapter==null||!mBluetoothAdapter.isEnabled())
		{
			Intent enableBtIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		wai=(Button) findViewById(R.id.button1);
		sp=(Spinner) findViewById(R.id.spinner1);
		nm=(Button) findViewById(R.id.button2);
		wai.setBackgroundColor(Color.CYAN);
		nm.setBackgroundColor(Color.CYAN);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this	, R.array.DestinationList, android.R.layout.simple_dropdown_item_1line);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(this);
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		wai.setOnClickListener(this);
		nm.setOnClickListener(this);
		
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	public void showLocation(String loc)
	{
		src=loc;
		Toast.makeText(this,loc, Toast.LENGTH_SHORT);
	}
	
	private void scanLeDevice(final boolean enable)
	{
		
		//mBluetoothAdapter.startLeScan(mLeScanCallback);
		
		if(enable)
		{
			mHandler.postDelayed(new Runnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					findMax();
				}
				
			},5000);
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		}
		else
		{
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		
	}
	
	protected void findMax() {
		int max=0;
		// TODO Auto-generated method stub
		for(int i=1;i<mDevice.size();i++)
		{
			if(mSS.get(i)>mSS.get(max))
			{
				max=i;
			}
		}
		
		if(mDevice.get(max).getAddress().equals("00:A0:50:E6:93:D1"))	showLocation("Reception");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:27")) showLocation("OT");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:88:1A")) showLocation("Recovery Room");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:21")) showLocation("Lift(Ground Floor)");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:21")) showLocation("Lift(First Floor)");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:21")) showLocation("General Ward");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:21")) showLocation("Upstairs");
		else if(mDevice.get(max).getAddress().equals("00:A0:50:E6:89:21")) showLocation("Semi-special Room 1");
		else showLocation("Click Button again");
	}

	private LeScanCallback mLeScanCallback=new LeScanCallback() {
		
		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			int ind;
			Float nss;
			// TODO Auto-generated method stub
			/*if(device.getAddress().equals("00:A0:50:E6:93:D1"))	showLocation("93:D1 Labs 8,Lab 9,Lab 10");
			else if(device.getAddress().equals("00:A0:50:E6:89:27")) showLocation("89:27 Lab 7,Tut room");
			else if(device.getAddress().equals("00:A0:50:E6:88:1A")) showLocation("88:1A HOD cabin,Server room");
			else if(device.getAddress().equals("00:A0:50:E6:89:21")) showLocation("89:21 Lab 6");
			else showLocation("Click Button again");
			mBluetoothAdapter.stopLeScan(mLeScanCallback);*/
			if(device.getName().equals("TCZ"))
			{
				if(mDevice.contains(device))
				{
					ind=mDevice.indexOf(device);
					nss=mSS.get(ind);
					nss=(nss+rssi)/2;
					mSS.set(ind, nss);
				}
				else
				{
					mDevice.add(device);
					mSS.add((float)rssi);
				}	
			}
			
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.button1)
		{
			scanLeDevice(true);
			//Toast.makeText(this,"Reception",Toast.LENGTH_SHORT).show();
		}
		else
		if(arg0.getId()==R.id.button2)
		{
			//d=(String) sp.getSelectedItem();
			openActivity2();
		}
	}

	public void openActivity2()
	{	
		String s=src;
		String d=dst;
		//String c="Lift";
		ed=spmain.edit();
    	ed.putString("source", s);
    	ed.putString("destination", d);
    	//ed.putString("choice", c);
    	ed.commit();

		Intent intent = new Intent(this, ActivityTwo.class);
  	    startActivity(intent);  
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		dst=sp.getSelectedItem().toString();
		//Toast.makeText(this,dst,Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

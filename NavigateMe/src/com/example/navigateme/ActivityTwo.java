package com.example.navigateme;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTwo extends Activity {
	String[] name ;
	
	SharedPreferences spmain;
	SharedPreferences.Editor ed;
	int src=0,dest=0;
	List<String> pathdir;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_two);
		
		Resources res = getResources();
		name = res.getStringArray(R.array.DestinationList);
		
		spmain=getSharedPreferences("spmain", 0);
		String s = spmain.getString("source","");
		String d = spmain.getString("destination","");
		//String c = spmain.getString("choice","");
		pathdir=new ArrayList<String>();
		
		for(int i=0;i<name.length;i++)
		{
			if(name[i].equals(s))
				src=i;
			if(name[i].equals(d))
				dest=i;
		}
		ShortestPath.path=new ArrayList<Integer>();
		ShortestPath.process(src, dest);
		
		String sentences[][]=
			{
				{"1","2","Go straight towards lift"},
				{"1","5","Take the stairs"},
				{"2","1","Go straight towards reception"},
				{"2","3","Go towards the door and take right"},
				{"2","4","Go towards the door and take left"},
				{"2","5","Take the stairs"},
				{"2","6","Take the lift to 1st floor"},
				{"3","2","Go outside the door"},
				{"3","4","Go straight towards recovery room"},
				{"4","3","Go straight towards OT"},
				{"4","2","Go outside the door"},
				{"5","1","Go downstairs"},
				{"5","2","Go downstairs and take left"},
				{"5","6","Go upstairs and take left"},
				{"6","2","Take the lift to the GF"},
				{"6","5","Go downstairs"},
				{"6","7","Go straight towards General ward"},
				{"7","6","Go straight towards the lift"},
				{"7","8","Take right towards semi special room"},
				{"8","7","Go straight towards General ward"}
			};
		
		for(int i=0;i<ShortestPath.path.size()-1;i++)
		{
			int start=ShortestPath.path.get(i);
			int end=ShortestPath.path.get(i+1);
			
			for(String str[]:sentences)
			{
				int from=Integer.parseInt(str[0]);
				int to=Integer.parseInt(str[1]);
				String sent=str[2];
				if(start==(from-1) && end==(to-1))
				{
					pathdir.add(sent);
				}
			}
		}
		for(int i=0;i<pathdir.size();i++)
		{
			String str=pathdir.get(i);
			final TextView tv1 = new TextView(this);
			tv1.setText((i+1)+")"+"  "+str);
			tv1.setTextColor(Color.WHITE);
			
			tv1.setTextSize(22);
			tv1.setGravity(Gravity.CENTER_VERTICAL);
			LinearLayout ll = (LinearLayout) findViewById(R.id.displ);
			ll.addView(tv1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_two, menu);
		return true;
	}

}


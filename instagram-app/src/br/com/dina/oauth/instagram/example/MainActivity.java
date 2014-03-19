package br.com.dina.oauth.instagram.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.dina.oauth.instagram.InstagramApp;
import br.com.dina.oauth.instagram.InstagramApp.OAuthAuthenticationListener;

public class MainActivity extends Activity {

	private InstagramApp mApp;
	private Button btnConnect;
//	private TextView tvSummary;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
				ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
		mApp.setListener(listener);

//		tvSummary = (TextView) findViewById(R.id.tvSummary);

		btnConnect = (Button) findViewById(R.id.btnConnect);
		
		btnConnect.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {

				final AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage("Disconnect from Instagram?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialog, int id) {
										mApp.resetAccessToken();
										btnConnect.setBackgroundResource(R.drawable.instagram_icon);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
				final AlertDialog alert = builder.create();
				alert.show();
			
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mApp.hasAccessToken()) {
					Toast.makeText(MainActivity.this, mApp.getAccessToken(), Toast.LENGTH_SHORT).show();
				} else {
					mApp.authorize();
				}
			}
		});

		if (mApp.hasAccessToken()) {
			Toast.makeText(MainActivity.this, mApp.getAccessToken(), Toast.LENGTH_SHORT).show();
			btnConnect.setBackgroundResource(R.drawable.instagram_con_connected);
		}

	}
	

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			btnConnect.setBackgroundResource(R.drawable.instagram_icon);
		}

		@Override
		public void onFail(String error) {
			if (mApp.hasAccessToken()) {
				Toast.makeText(MainActivity.this, mApp.getAccessToken(), Toast.LENGTH_SHORT).show();
				btnConnect.setBackgroundResource(R.drawable.instagram_con_connected);
			}
			System.out.println(error);

//			Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onResume() {
		if (mApp.hasAccessToken()) {
			Toast.makeText(MainActivity.this, mApp.getAccessToken(), Toast.LENGTH_SHORT).show();
			btnConnect.setBackgroundResource(R.drawable.instagram_con_connected);
		}
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
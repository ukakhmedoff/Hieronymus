package ru.snatcher.hieronymus.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.snatcher.hieronymus.other.App;

/**
 * {@link NetworkChangeReceiver}
 * <p>
 * Here we check the network connection
 * If the network state changes, the system notifies us
 * </p>
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

	public static ConnectionReceiverListener connectionReceiverListener;

	public NetworkChangeReceiver() {
		super();
	}

	public static boolean isConnected() {
		ConnectivityManager
				cm = (ConnectivityManager) App.getInstance().getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
	}

	@Override
	public void onReceive(Context pContext, Intent arg1) {
		ConnectivityManager cm = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();

		if (connectionReceiverListener != null)
			connectionReceiverListener.onNetworkConnectionChanged(isConnected);
	}


	public interface ConnectionReceiverListener {
		void onNetworkConnectionChanged(boolean isConnected);
	}
}

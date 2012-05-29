package org.blitzortung.android.app;

import org.blitzortung.android.alarm.AlarmManager;
import org.blitzortung.android.data.Provider;
import org.blitzortung.android.map.overlay.StrokesOverlay;
import org.blitzortung.android.map.overlay.color.StrokeColorHandler;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationManager;


public class PersistedData {

	private TimerTask timerTask;
	
	private Provider provider;
	
	private AlarmManager alarmManager;
	
	private StrokesOverlay strokesOverlay;
	
	public PersistedData(Resources resources, LocationManager locationManager, SharedPreferences preferences) {
		provider = new Provider(preferences);
		timerTask = new TimerTask(resources, preferences, provider);
		alarmManager = new AlarmManager(locationManager, preferences, timerTask);
		strokesOverlay = new StrokesOverlay(new StrokeColorHandler(preferences));
	}

	public StrokesOverlay getStrokesOverlay() {
		return strokesOverlay;
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public Provider getProvider() {
		return provider;
	}

	public AlarmManager getAlarmManager() {
		return alarmManager;
	}
	
}
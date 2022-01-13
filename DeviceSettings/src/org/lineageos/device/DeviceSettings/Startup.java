/*
* Copyright (C) 2013 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.lineageos.device.DeviceSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.preference.PreferenceManager;

import org.lineageos.device.DeviceSettings.TouchscreenGestureSettings;
import org.lineageos.device.DeviceSettings.R;

public class Startup extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";
    private static final String ONE_TIME_TUNABLE_RESTORE = "hardware_tunable_restored";
    
    private boolean mHBM = false;

    @Override
    public void onReceive(final Context context, final Intent bootintent) {

        VibratorStrengthPreference.restore(context);

        boolean enabled = false;
        TouchscreenGestureSettings.MainSettingsFragment.restoreTouchscreenGestureStates(context);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);

        if (enabled) {
        mHBM = false;
        restore(SRGBModeSwitch.getFile(), enabled);
		}
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_HBM_SWITCH, false);
        if (enabled) {
        mHBM = true;
        restore(HBMModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DC_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCI_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(DCIModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_ADAPTIVE_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(AdaptiveModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_ONEPLUS_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(OnePlusModeSwitch.getFile(), enabled);
        }
        enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);
        if (enabled) {
        mHBM = false;
        restore(SRGBModeSwitch.getFile(), enabled);
        }
        Utils.enableService(context);
    }

    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            Utils.writeValue(file, mHBM ? "5" : "1");
        }
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }
        private boolean hasRestoredTunable(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(ONE_TIME_TUNABLE_RESTORE, false);
    }

    private void setRestoredTunable(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(ONE_TIME_TUNABLE_RESTORE, true).apply();
    }
}

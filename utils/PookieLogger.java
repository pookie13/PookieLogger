package com.cbistech.app.views;

import android.os.Build;
import android.os.Environment;

import com.cbistech.app.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PookieLogger {
    private String url;//
    private String uuid;//
    private String userName;//
    private String deviceName;//
    private String deviceManufacture;//
    private String networkName;
    private String networkType;//
    private String errorCode;//
    private String throwable;//

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;//

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String getDeviceName() {
        return deviceName;
    }

    private void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private String getDeviceManufacture() {
        return deviceManufacture;
    }

    private void setDeviceManufacture(String deviceManufacture) {
        this.deviceManufacture = deviceManufacture;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    private String getNetworkType() {
        return networkType;
    }

    private void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    public static void saveLogs(PookieLogger PookieLogger, BaseActivity baseActivity) {
        try {
            PookieLogger.setUuid(baseActivity.connectionService.databaseService.getCurrentUser().getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PookieLogger.setDeviceName(JanusDevice.getDeviceName());
        PookieLogger.setNetworkType(JanusDevice.getConnectedNetworkType(baseActivity));
        PookieLogger.setDeviceManufacture(Build.MANUFACTURER);
        PookieLogger.setTime(System.currentTimeMillis() + "");
        generateNoteOnSD(baseActivity, getReadableLogs(PookieLogger));

    }


    private static String getReadableLogs(PookieLogger PookieLogger) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", PookieLogger.getUrl());
            jsonObject.put("uuid", PookieLogger.getUuid());
            jsonObject.put("userName", PookieLogger.getUserName());
            jsonObject.put("deviceName", PookieLogger.getDeviceName());
            jsonObject.put("deviceManufacture", PookieLogger.getDeviceManufacture());
            jsonObject.put("networkType", PookieLogger.getNetworkType());
            jsonObject.put("errorCode", PookieLogger.getErrorCode());
            jsonObject.put("time", DateFormat.getDateTimeInstance().format(new Date()));
            jsonObject.put("throwable", PookieLogger.getThrowable());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    private static void generateNoteOnSD(BaseActivity context, String sBody) {
        if (!checkForWritePermission(context)) {
            return;
        }
        String encriptmessage = sBody;

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "pookie");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "file" + JanusDevice.getDeviceName() + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
            if (gpxfile.exists()) {
                try {
                    FileOutputStream fOut = new FileOutputStream(gpxfile, true);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append("\n" + encriptmessage);
                    myOutWriter.close();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                FileWriter writer = new FileWriter(gpxfile);
                writer.append("\n" + encriptmessage);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readActivity(BaseActivity baseActivity, Object activity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DateAndTime", DateFormat.getDateTimeInstance().format(new Date()) + "");
            jsonObject.put("Activity", activity.toString());
            generateNoteOnSD(baseActivity, jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

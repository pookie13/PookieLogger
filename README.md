# PookieLogger

A simple Repo that help to create log file of your Application.
This is a single file Class library for android. It has no dependencies. Just copy paste the file and use it.

## Step to Use this :
```
   PookieLogger pookieLogger= new PookieLogger();
   pookieLogger.setThrowable(throwable.toString());
   pookieLogger.setUrl(lastUrl);
   pookieLogger.setErrorCode(AppConstant.getErrorCode(baseActivity, throwable, getRequestType(), "02"));
   pookieLogger.setUuid(baseActivity.connectionService.databaseService.getCurrentUser().getUsername());
   pookieLogger.setDeviceName(JanusDevice.getDeviceName());
   pookieLogger.setNetworkType(JanusDevice.getConnectedNetworkType(baseActivity));
   pookieLogger.setDeviceManufacture(Build.MANUFACTURER);
   pookieLogger.setTime(System.currentTimeMillis() + "");
   generateNoteOnSD(baseActivity, getReadableLogs(shukranLogger));

```

## Output file :
```
{
  "url":"https://www.google.co.in/",
  "uuid":"user id",
  "userName":"Pookie",
  "deviceName":"SM-I01s",
  "deviceManufacture":"SAMSUNG",
  "networkType":"WI-FI",
  "errorCode":"Error code",
  "time":"12-01-2003 8:12 PM",
  "throwable":"error messsage"
}

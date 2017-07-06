package amrmustafa.com.findmyphone;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class ServiceNotification
  extends IntentService
{
  public static boolean ServiceIsRun = false;
  
  public ServiceNotification()
  {
    super("MyWebRequestService");
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    ServiceIsRun = true;
    try
    {
      new SettingSaved(getApplicationContext()).LoadData();
      paramIntent = paramIntent.getExtras().getString("senderNum");
      new SettingSaved(getApplicationContext()).sendm(paramIntent);
      ServiceIsRun = false;
      return;
    }
    catch (Exception paramIntent)
    {
      for (;;) {}
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\ServiceNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
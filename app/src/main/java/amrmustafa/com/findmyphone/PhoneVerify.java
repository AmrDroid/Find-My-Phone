package amrmustafa.com.findmyphone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import java.util.Random;

public class PhoneVerify
  extends AppCompatActivity
{
  EditText EDTNumber;
  private boolean IsRunning;
  private int NumberTry = 0;
  private final int REQUEST_CODE_ASK_PERMISSIONSReadPhoneNumber = 441;
  private final int REQUEST_CODE_ASK_PERMISSIONSSMS = 444;
  Button buNext;
  String mPhoneNumber = "";
  ProgressBar progressBar1;
  
  public void BuNext(View paramView)
  {
    if ((this.EDTNumber.getText().toString().length() < SettingSaved.NumberDigit) || (this.EDTNumber.getText().equals("empty")))
    {
      ShowAlert(getResources().getString(2131165255));
      return;
    }
    CheckSMSPermission();
  }
  
  void CheckReadPhoneNumberPermission()
  {
    try
    {
      if ((Build.VERSION.SDK_INT >= 23) && (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0))
      {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_PHONE_STATE")) {
          requestPermissions(new String[] { "android.permission.READ_PHONE_STATE" }, 441);
        }
      }
      else
      {
        ReadNumber();
        return;
      }
    }
    catch (Exception localException) {}
  }
  
  void CheckSMSPermission()
  {
    if ((Build.VERSION.SDK_INT >= 23) && (ActivityCompat.checkSelfPermission(this, "android.permission.RECEIVE_SMS") != 0))
    {
      requestPermissions(new String[] { "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.SEND_SMS" }, 444);
      return;
    }
    SendSMS();
  }
  
  void DownloadApp()
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(Uri.parse("market://details?id=com.alrubaye.familyfinder"));
    localIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.alrubaye.familyfinder"));
    startActivity(localIntent);
  }
  
  void DownloadAppSuggest()
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(getResources().getString(2131165268)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        PhoneVerify.this.DownloadApp();
      }
    });
    localBuilder.create().show();
  }
  
  void OpenNewActivity(String paramString)
  {
    if (SharedData.PhoneNumber.equalsIgnoreCase("empty"))
    {
      ShowAlert(getResources().getString(2131165255));
      return;
    }
    new SettingSaved(this).SavePhoneNumberOnly();
    startActivity(new Intent(getApplicationContext(), ContactList.class));
    finish();
  }
  
  void ReadNumber()
  {
    try
    {
      this.mPhoneNumber = ((TelephonyManager)getSystemService("phone")).getLine1Number();
      if (this.mPhoneNumber.length() > 0) {
        this.EDTNumber.setText(this.mPhoneNumber);
      }
      return;
    }
    catch (Exception localException)
    {
      this.mPhoneNumber = "";
    }
  }
  
  void SendSMS()
  {
    if ((this.mPhoneNumber != null) && (this.mPhoneNumber.length() > 0) && (this.mPhoneNumber.equals(this.EDTNumber.getText().toString())))
    {
      SharedData.PhoneNumber = ManagmentOperations.FormatPhoneNumber(this.mPhoneNumber);
      OpenNewActivity("SendSMS:" + SharedData.PhoneNumber + ",mPhoneNumber:" + this.mPhoneNumber);
    }
    for (;;)
    {
      return;
      MyBroadcastReceiver.UserPhoneNumber = this.EDTNumber.getText().toString();
      try
      {
        SettingSaved.HashCode = String.valueOf(new Random().nextInt(8000) + 1000);
        SmsManager.getDefault().sendTextMessage(this.EDTNumber.getText().toString(), null, SettingSaved.HashCode, null, null);
        this.progressBar1.setVisibility(0);
        this.buNext.setVisibility(8);
        if (!this.IsRunning)
        {
          new MyThread().start();
          return;
        }
      }
      catch (Exception localException) {}
    }
  }
  
  void ShowAlert(String paramString)
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(paramString).setCancelable(false).setNegativeButton(getResources().getString(2131165274), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
    localBuilder.create().show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903071);
    this.EDTNumber = ((EditText)findViewById(2131624076));
    this.progressBar1 = ((ProgressBar)findViewById(2131624078));
    this.progressBar1.setVisibility(8);
    this.buNext = ((Button)findViewById(2131624077));
    ((AdView)findViewById(2131624079)).loadAd(new AdRequest.Builder().build());
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    switch (paramInt)
    {
    case 442: 
    case 443: 
    default: 
      super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
    }
    do
    {
      do
      {
        return;
      } while (paramArrayOfInt[0] != 0);
      SendSMS();
      return;
    } while (paramArrayOfInt[0] != 0);
    ReadNumber();
  }
  
  protected void onResume()
  {
    super.onResume();
    CheckReadPhoneNumberPermission();
  }
  
  class MyThread
    extends Thread
  {
    private int counter = 0;
    
    public MyThread()
    {
      PhoneVerify.access$002(PhoneVerify.this, true);
    }
    
    public void run()
    {
      for (;;)
      {
        if (PhoneVerify.this.IsRunning)
        {
          if ((SharedData.PhoneNumber != null) && (!SharedData.PhoneNumber.equals("empty")))
          {
            PhoneVerify.access$002(PhoneVerify.this, false);
            PhoneVerify.this.OpenNewActivity("Thread," + SharedData.PhoneNumber);
          }
        }
        else {
          return;
        }
        try
        {
          Thread.sleep(1000L);
          if (this.counter == 15)
          {
            PhoneVerify.this.runOnUiThread(new Runnable()
            {
              public void run()
              {
                PhoneVerify.this.progressBar1.setVisibility(8);
                PhoneVerify.this.buNext.setVisibility(0);
                Toast.makeText(PhoneVerify.this.getApplicationContext(), PhoneVerify.this.getResources().getString(2131165281), 1).show();
                PhoneVerify.access$108(PhoneVerify.this);
                if (PhoneVerify.this.NumberTry == 2) {
                  PhoneVerify.this.DownloadAppSuggest();
                }
              }
            });
            PhoneVerify.access$002(PhoneVerify.this, false);
          }
          this.counter += 1;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\PhoneVerify.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
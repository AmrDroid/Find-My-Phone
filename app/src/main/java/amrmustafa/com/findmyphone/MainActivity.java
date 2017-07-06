package amrmustafa.com.findmyphone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MainActivity
  extends AppCompatActivity
{
  Boolean ISsignIn = Boolean.valueOf(false);
  boolean IsShowing = false;
  private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
  ArrayList<ListItem> list;
  ListView listv;
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private DatabaseReference mDatabase;
  
  private void showGPSDisabledAlertToUser()
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(getApplicationContext().getResources().getString(2131165282)).setCancelable(false).setPositiveButton(getApplicationContext().getResources().getString(2131165242), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          paramAnonymousDialogInterface = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
          MainActivity.this.startActivity(paramAnonymousDialogInterface);
          MainActivity.this.IsShowing = false;
          return;
        }
        catch (Exception paramAnonymousDialogInterface) {}
      }
    });
    localBuilder.create().show();
  }
  
  private void signInAnonymously()
  {
    this.mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener()
    {
      public void onComplete(@NonNull Task<AuthResult> paramAnonymousTask)
      {
        Log.d("Auth", "signInAnonymously:onComplete:" + paramAnonymousTask.isSuccessful());
      }
    });
  }
  
  void CheckUserPermission()
  {
    if ((Build.VERSION.SDK_INT >= 23) && (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0))
    {
      Builder localBuilder = new Builder(this);
      localBuilder.setMessage(getResources().getString(2131165243)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          MainActivity.this.requestPermissions(new String[] { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.READ_CONTACTS" }, 123);
        }
      });
      localBuilder.create().show();
      return;
    }
    StartUpServices();
  }
  
  void DownloadApp()
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(getResources().getString(2131165268)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = new Intent("android.intent.action.VIEW");
        paramAnonymousDialogInterface.setData(Uri.parse("market://details?id=com.alrubaye.familyfinder"));
        paramAnonymousDialogInterface.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.alrubaye.familyfinder"));
        MainActivity.this.startActivity(paramAnonymousDialogInterface);
        SettingSaved.SeenNewUpdates = 1;
        new SettingSaved(MainActivity.this.getApplicationContext()).SaveData();
      }
    }).setNegativeButton(getResources().getString(2131165253), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
        SettingSaved.SeenNewUpdates = 1;
        new SettingSaved(MainActivity.this.getApplicationContext()).SaveData();
      }
    });
    localBuilder.create().show();
  }
  
  public void HelpMe()
  {
    try
    {
      Location localLocation = new SettingSaved(getApplicationContext()).getLocation();
      Iterator localIterator = SettingSaved.WhoFindMeIN.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Entry localEntry = (Entry)localIterator.next();
        String str = getResources().getString(2131165245) + ", http://maps.google.com/maps?q=loc:" + localLocation.getLatitude() + "," + localLocation.getLongitude();
        try
        {
          SmsManager.getDefault().sendTextMessage(localEntry.getKey().toString(), null, str, null, null);
          MessageSend(getResources().getString(2131165252));
        }
        catch (Exception localException2) {}
      }
      return;
    }
    catch (Exception localException1)
    {
      MessageSend(getResources().getString(2131165251));
    }
  }
  
  void HelpMessage()
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(getResources().getString(2131165244)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        MainActivity.this.HelpMe();
      }
    }).setNegativeButton(getResources().getString(2131165253), new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.create().show();
  }
  
  void HideActivity()
  {
    Intent localIntent = new Intent("android.intent.action.MAIN");
    localIntent.addCategory("android.intent.category.HOME");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void MessageSend(String paramString)
  {
    Toast.makeText(this, paramString, 1).show();
  }
  
  void ShareApp()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    if (Build.VERSION.SDK_INT >= 24) {}
    for (Spanned localSpanned = Html.fromHtml(getResources().getString(2131165288) + "  https://play.google.com/store/apps/details?id=phonelocation.example.asuss550c.phonelocationphone", 0);; localSpanned = Html.fromHtml(getResources().getString(2131165288) + "  https://play.google.com/store/apps/details?id=phonelocation.example.asuss550c.phonelocationphone"))
    {
      localIntent.putExtra("android.intent.extra.TEXT", localSpanned);
      startActivity(Intent.createChooser(localIntent, "Share using"));
      return;
    }
  }
  
  void StartUpServices()
  {
    if (SharedData.PhoneNumber.equals("empty")) {
      startActivity(new Intent(this, PhoneVerify.class));
    }
    do
    {
      return;
      if (!LocationService.ServiceIsRun)
      {
        LocationService.ServiceIsRun = true;
        new ManagmentOperations(this).PrepareUser(SharedData.PhoneNumber, SharedData.PhoneNumber);
        startService(new Intent(getBaseContext(), LocationService.class));
      }
      UpdetListContact();
    } while ((((LocationManager)getSystemService("location")).isProviderEnabled("gps")) || (this.IsShowing));
    this.IsShowing = true;
    showGPSDisabledAlertToUser();
  }
  
  void UpdetListContact()
  {
    this.list = new ArrayList();
    this.list.add(new ListItem("Loading", "no_desc", 2130837619));
    this.listv.setAdapter(new UserListAdapter(this.list));
    this.mDatabase.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Finders").addValueEventListener(new ValueEventListener()
    {
      public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
      
      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
      {
        Object localObject1 = (HashMap)paramAnonymousDataSnapshot.getValue();
        MainActivity.this.list.clear();
        if (localObject1 == null)
        {
          MainActivity.this.list.add(new ListItem("NoTicket", "no_desc", 2130837619));
          MainActivity.this.listv.setAdapter(new UserListAdapter(MainActivity.this, MainActivity.this.list));
          return;
        }
        paramAnonymousDataSnapshot = new ArrayList();
        Object localObject2 = MainActivity.this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (((Cursor)localObject2).moveToNext()) {
          paramAnonymousDataSnapshot.add(new ListItem(((Cursor)localObject2).getString(((Cursor)localObject2).getColumnIndex("display_name")), ManagmentOperations.FormatPhoneNumber(((Cursor)localObject2).getString(((Cursor)localObject2).getColumnIndex("data1"))), 2130837612));
        }
        localObject1 = ((Map)localObject1).keySet().iterator();
        label275:
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (String)((Iterator)localObject1).next();
          Iterator localIterator = paramAnonymousDataSnapshot.iterator();
          for (;;)
          {
            if (!localIterator.hasNext()) {
              break label275;
            }
            ListItem localListItem = (ListItem)localIterator.next();
            try
            {
              if ((localListItem.PhoneNumber.length() >= SettingSaved.NumberDigit) && (((String)localObject2).contains(localListItem.PhoneNumber))) {
                MainActivity.this.list.add(new ListItem(localListItem.UserName, (String)localObject2, 2130837619));
              }
            }
            catch (Exception localException) {}
          }
        }
        if (MainActivity.this.list.size() > 0) {
          MainActivity.this.list.add(new ListItem("AdsBanner", "AdsBanner", 2130837619));
        }
        MainActivity.this.listv.setAdapter(new UserListAdapter(MainActivity.this, MainActivity.this.list));
      }
    });
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903068);
    new SettingSaved(this).LoadData();
    SettingSaved.ISLocForLocation = true;
    this.mDatabase = FirebaseDatabase.getInstance().getReference();
    this.listv = ((ListView)findViewById(2131624063));
    this.listv.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (MainActivity.this.listv.getCount() == 1)
        {
          MainActivity.this.ShareApp();
          return;
        }
        paramAnonymousAdapterView = (ListItem)MainActivity.this.list.get(paramAnonymousInt);
        ManagmentOperations.updateLocation(paramAnonymousAdapterView.PhoneNumber);
        paramAnonymousView = new Intent(MainActivity.this.getApplicationContext(), MapDisplay.class);
        paramAnonymousView.putExtra("Mode", "Online");
        paramAnonymousView.putExtra("PhoneNumber", paramAnonymousAdapterView.PhoneNumber);
        MainActivity.this.startActivity(paramAnonymousView);
      }
    });
    this.mAuth = FirebaseAuth.getInstance();
    this.mAuthListener = new FirebaseAuth.AuthStateListener()
    {
      public void onAuthStateChanged(@NonNull FirebaseAuth paramAnonymousFirebaseAuth)
      {
        paramAnonymousFirebaseAuth = paramAnonymousFirebaseAuth.getCurrentUser();
        if (paramAnonymousFirebaseAuth != null) {
          Log.d("Auth", "onAuthStateChanged:signed_in:" + paramAnonymousFirebaseAuth.getUid());
        }
        while (MainActivity.this.ISsignIn.booleanValue()) {
          return;
        }
        MainActivity.this.ISsignIn = Boolean.valueOf(true);
        MainActivity.this.signInAnonymously();
      }
    };
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131689472, paramMenu);
    return true;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      HideActivity();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == 2131624127) {
      HelpMessage();
    }
    if (i == 2131624128) {
      startActivity(new Intent(this, ContactList.class));
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    switch (paramInt)
    {
    default: 
      super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
      return;
    }
    if (paramArrayOfInt[0] == 0)
    {
      StartUpServices();
      return;
    }
    Toast.makeText(this, getResources().getString(2131165236), 0).show();
  }
  
  protected void onResume()
  {
    super.onResume();
    if (!SharedData.PhoneNumber.equals("empty")) {
      CheckUserPermission();
    }
  }
  
  public void onStart()
  {
    super.onStart();
    this.mAuth.addAuthStateListener(this.mAuthListener);
  }
  
  public void onStop()
  {
    super.onStop();
    if (this.mAuthListener != null) {
      this.mAuth.removeAuthStateListener(this.mAuthListener);
    }
  }
  
  class UserListAdapter
    extends BaseAdapter
  {
    ArrayList<ListItem> mylist;
    
    UserListAdapter()
    {
      ArrayList localArrayList;
      this.mylist = localArrayList;
    }
    
    public int getCount()
    {
      return this.mylist.size();
    }
    
    public Object getItem(int paramInt)
    {
      return this.mylist.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)MainActivity.this.getSystemService("layout_inflater");
      paramView = (ListItem)this.mylist.get(paramInt);
      if (paramView.UserName.equals("Loading")) {
        return localLayoutInflater.inflate(2130903089, paramViewGroup, false);
      }
      if (paramView.UserName.equals("NoTicket")) {
        return localLayoutInflater.inflate(2130903090, paramViewGroup, false);
      }
      if (paramView.UserName.equals("AdsBanner"))
      {
        paramView = localLayoutInflater.inflate(2130903073, paramViewGroup, false);
        ((AdView)paramView.findViewById(2131624079)).loadAd(new AdRequest.Builder().build());
        return paramView;
      }
      if (paramView.UserName.equals("Ads"))
      {
        paramView = localLayoutInflater.inflate(2130903088, paramViewGroup, false);
        ((NativeExpressAdView)paramView.findViewById(2131624079)).loadAd(new AdRequest.Builder().build());
        return paramView;
      }
      paramViewGroup = localLayoutInflater.inflate(2130903110, paramViewGroup, false);
      ((TextView)paramViewGroup.findViewById(2131624072)).setText(paramView.UserName);
      ((TextView)paramViewGroup.findViewById(2131624117)).setText(paramView.PhoneNumber);
      ((ImageView)paramViewGroup.findViewById(2131624116)).setImageResource(paramView.ImageURL);
      return paramViewGroup;
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
package amrmustafa.com.findmyphone;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class VisitLocations
  extends FragmentActivity
  implements OnStreetViewPanoramaReadyCallback
{
  double latitude;
  double longitude;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903072);
    paramBundle = getIntent().getExtras();
    this.latitude = paramBundle.getDouble("latitude");
    this.longitude = paramBundle.getDouble("longitude");
    ((StreetViewPanoramaFragment)getFragmentManager().findFragmentById(2131624067)).getStreetViewPanoramaAsync(this);
  }
  
  public void onStreetViewPanoramaReady(StreetViewPanorama paramStreetViewPanorama)
  {
    try
    {
      paramStreetViewPanorama.setPosition(new LatLng(this.latitude, this.longitude));
      return;
    }
    catch (Exception paramStreetViewPanorama) {}
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\VisitLocations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
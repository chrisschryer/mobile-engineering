package com.cschryer.eaterofjsons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cschryer.eaterofjsons.classes.OfferItem;
import com.cschryer.eaterofjsons.classes.SpecialOffer;

/**
 * A list fragment representing a list of Special Offers. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SpecialOfferDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SpecialOfferListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SpecialOfferListFragment() {
    }
    
    public ArrayList<SpecialOffer> offersList = new ArrayList<SpecialOffer>();
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run task to load offers

        new LoadOffersTask().execute();
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        context = activity.getApplicationContext();
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(OfferItem.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
//    
    //Start LoadOffersTask
    private class LoadOffersTask extends AsyncTask<Void, Void, Void>
	{
		
		protected void onPreExecute()
		{
			//TODO: show 'Loading' message (Toast or ProgressDialog) if unit testing calls for it
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
//			android.os.Debug.waitForDebugger();
			try {
				String json_Url = getString(R.string.json_source_url);
				

				HttpClient hc = new DefaultHttpClient();
				HttpGet get = new HttpGet(json_Url);
				HttpResponse rp = hc.execute(get);

				JSONObject jsonObject = null;
				JSONArray offers = null;

				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(rp.getEntity());
					result = "{\"offers\":" + result + "}";

					jsonObject = new JSONObject(result);
					offers = jsonObject.getJSONArray("offers");

					for (int i = 0; i < offers.length(); i++) {
						SpecialOffer offer = new SpecialOffer(offers.getJSONObject(i));
						//Send JSON values to their... FINAL DESTINATION Dun dun dunnnnnn
						offersList.add(offer);
						
					}

				}
			}
			catch (Exception e) {
				Log.e("SpecialOfferListFragment", "Error loading JSON for Special Offer list", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			setListAdapter(new OfferListAdapter(context, R.layout.activity_specialoffer_list, offersList));
		}
	}

    private class OfferListAdapter extends ArrayAdapter<SpecialOffer>
	{
		private ArrayList<SpecialOffer> listOfOffers;

		public OfferListAdapter(Context context, int textViewResourceId, ArrayList<SpecialOffer> items)
		{
			super(context, textViewResourceId, items);
			this.listOfOffers = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null && context != null) {
				LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = li.inflate(R.layout.activity_specialoffer_detail, null, false);
			}
			SpecialOffer so = listOfOffers.get(position);
			
			//Make the image all clicky-clicky
			final String href= so.getStrOfferHREF();
			ImageView offerImg = (ImageView) v.findViewById(R.id.actofferDetailImg);
			offerImg.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(href!=""){
			        	Intent i = new Intent(Intent.ACTION_VIEW);
			        	i.setData(Uri.parse(href));
			        	startActivity(i);
			        }
					
				}});
			new DownloadImageTask(offerImg).execute(so.getStrOfferImgURL());
			//Can convert this to above format to support user profile view 			
			new DownloadImageTask((ImageView) v.findViewById(R.id.actuserImg)).execute(so.getUserImageUrl());

			
			((TextView) v.findViewById(R.id.acttvUserDisplayName)).setText(so.getStrUserDisplayName());
			TextView tt = (TextView) v.findViewById(R.id.actspecialoffer_sponsor);
			tt.setText(so.getStrOfferSponsor());
			TextView tx = (TextView) v.findViewById(R.id.actspecialoffer_detail);
			tx.setText(so.getStrOfferDescription());
			
			return v;

		}
		
		
	}
	
    //modified version of StackOverflow code follows
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
			  //android.os.Debug.waitForDebugger();
		      String urldisplay = urls[0];
		      if (urldisplay != "") {
				Bitmap image = null;
				URLConnection conn = null;
				HttpsURLConnection secureConn = null;
				try {
					conn = (new URL(urldisplay)).openConnection();
					if(urldisplay.startsWith("https")){
						secureConn =(HttpsURLConnection) conn;					
					} 
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					if(secureConn != null){
						secureConn.setRequestMethod("GET");
						secureConn.connect();
						if(secureConn.getResponseCode() == HttpsURLConnection.HTTP_OK || secureConn.getResponseCode() == HttpsURLConnection.HTTP_NOT_MODIFIED){
							InputStream in = secureConn.getInputStream();
							image = BitmapFactory.decodeStream(in);
						}
					} else {
						InputStream in = new URL(urldisplay).openStream();
			            image = BitmapFactory.decodeStream(in);
					}
					
				} catch (Exception e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
				return image;
			} else {
				return BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				}
		  }

		  protected void onPostExecute(Bitmap result) {
		      if(bmImage != null){
			  bmImage.setImageBitmap(result);
		      } else {
		    	  Log.e("DownloadImageTask", "Error - image object is null");
		      }
		  }
		}
}



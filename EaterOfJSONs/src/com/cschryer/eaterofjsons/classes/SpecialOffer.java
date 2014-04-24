package com.cschryer.eaterofjsons.classes;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cschryer.eaterofjsons.R;

public class SpecialOffer {

    
    // JSON keys
    public static final String SpecialOffer_USER_DISPLAY_NAME = "name";//inside user/
    public static final String SpecialOffer_USER_name = "username";//inside user/
    public static final String SpecialOffer_TEXT_DESCRIPTION = "attrib";
    public static final String SpecialOffer_TEXT_SPONSOR = "desc";
    public static final String SpecialOffer_USER_IMAGE_URL = "src"; //inside user/avatar/
    public static final String SpecialOffer_USER_IMAGE_WIDTH = "width"; //inside user/avatar/
    public static final String SpecialOffer_USER_IMAGE_HEIGHT = "height"; //inside user/avatar/
    public static final String SpecialOffer_OFFER_IMAGE_URL = "src";
    public static final String SpecialOffer_HREF = "href";
    

    private String strUserDisplayName = "";
    private String strUserName = "";
    private String strOfferDescription = "";
    private String strOfferSponsor = "";
    private String strUserImgURL = "";
    private Integer userImgHeight = 0;
    private Integer userImgWidth = 0;
    private String strOfferImgURL = "";
    private String strOfferHREF = "";
    
    
    public SpecialOffer(JSONObject jsonObj) {
        if(jsonObj != null){
        	
            try {
            	JSONObject user = jsonObj.getJSONObject("user");
				setStrUserDisplayName(user.optString(SpecialOffer_USER_DISPLAY_NAME, ""));
				setStrUserName(user.optString(SpecialOffer_USER_name, ""));
				
				JSONObject userAvatar = user.getJSONObject("avatar");
				setUserImageUrl(userAvatar.optString(SpecialOffer_USER_IMAGE_URL, ""));
				setUserImgHeight(userAvatar.optInt(SpecialOffer_USER_IMAGE_HEIGHT, 250));
				setUserImgWidth(userAvatar.optInt(SpecialOffer_USER_IMAGE_WIDTH, 250));
				
				setStrOfferDescription(jsonObj.optString(SpecialOffer_TEXT_DESCRIPTION, ""));
				setStrOfferSponsor(jsonObj.optString(SpecialOffer_TEXT_SPONSOR));
				setStrOfferImgURL(jsonObj.optString(SpecialOffer_OFFER_IMAGE_URL));
				setStrOfferHREF(jsonObj.optString(SpecialOffer_HREF));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    public SpecialOffer(String strUserDisplayName, String strUserName, String strOfferDescription, String strOfferSponsor, 
    		String strUserImgURL, Integer userImgHeight, Integer userImgWidth, String strOfferImgURL, String strOfferHREF) {
    	setStrUserDisplayName(strUserDisplayName);
		setStrUserName(strUserName);
		setUserImageUrl(strUserImgURL);
		setUserImgHeight(userImgHeight);
		setUserImgWidth(userImgWidth);
		
		setStrOfferDescription(strOfferDescription);
		setStrOfferSponsor(strOfferSponsor);
		setStrOfferImgURL(strOfferImgURL);
		setStrOfferHREF(strOfferHREF);
    }
    
    public SpecialOffer() {
    }
    
    public void loadOffer(Context con, Integer id){
    	try {
			String json_Url = con.getString(R.string.json_source_url);
			

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

				//Fetch the nth result from the JSON data.  Will return the wrong value if the remote data changes after home list loads,
				//but I'm not persisting data
				for (int i = 0; i < offers.length(); i++) {
					if(i==id){
					SpecialOffer temp = new SpecialOffer(offers.getJSONObject(i));
					setStrUserDisplayName(temp.getStrUserDisplayName());
					setStrUserName(temp.getStrUserName());
					setUserImageUrl(temp.getUserImageUrl());
					setUserImgHeight(temp.getUserImgHeight());
					setUserImgWidth(temp.getUserImgWidth());
					
					setStrOfferDescription(temp.getStrOfferDescription());
					setStrOfferSponsor(temp.getStrOfferSponsor());
					setStrOfferImgURL(temp.getStrOfferImgURL());
					setStrOfferHREF(temp.getStrOfferHREF());
					}
										
				}

			}
		}
		catch (Exception e) {
			Log.e("SpecialOfferListFragment", "Error loading JSON for Special Offer list", e);
		}
    }
    
    
    //Getters & Setters
    

    public String getUserImageUrl() {
        return strUserImgURL;
    }

    public void setUserImageUrl(String imageUrl) {
        this.strUserImgURL = imageUrl;
    }

	public String getStrOfferSponsor() {
		return strOfferSponsor;
	}

	public void setStrOfferSponsor(String strOfferSponsor) {
		this.strOfferSponsor = strOfferSponsor;
	}

	public Integer getUserImgHeight() {
		return userImgHeight;
	}

	public void setUserImgHeight(Integer userImgHeight) {
		this.userImgHeight = userImgHeight;
	}

	public Integer getUserImgWidth() {
		return userImgWidth;
	}

	public void setUserImgWidth(Integer userImgWidth) {
		this.userImgWidth = userImgWidth;
	}

	public String getStrOfferImgURL() {
		return strOfferImgURL;
	}

	public void setStrOfferImgURL(String strOfferImgURL) {
		this.strOfferImgURL = strOfferImgURL;
	}

	public String getStrOfferHREF() {
		return strOfferHREF;
	}

	public void setStrOfferHREF(String strOfferHREF) {
		this.strOfferHREF = strOfferHREF;
	}

	public String getStrUserDisplayName() {
		return strUserDisplayName;
	}

	public void setStrUserDisplayName(String strUserDisplayName) {
		this.strUserDisplayName = strUserDisplayName;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrOfferDescription() {
		return strOfferDescription;
	}

	public void setStrOfferDescription(String strOfferDescription) {
		this.strOfferDescription = strOfferDescription;
	}


}




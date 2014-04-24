package com.cschryer.eaterofjsons;

import com.cschryer.eaterofjsons.classes.SpecialOffer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * An activity representing a list of Special Offers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SpecialOfferDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SpecialOfferListFragment} and the item details
 * (if present) is a {@link SpecialOfferDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link SpecialOfferListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class SpecialOfferListActivity extends FragmentActivity
        implements SpecialOfferListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SpecialOffer so = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialoffer_list);

//        if (findViewById(R.id.specialoffer_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-large and
//            // res/values-sw600dp). If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//
//            // In two-pane mode, list items should be given the
//            // 'activated' state when touched.
//            ((SpecialOfferListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.specialoffer_list))
//                    .setActivateOnItemClick(true);
//        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link SpecialOfferListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

        so.loadOffer(this, Integer.parseInt(id));
    	
        if(so.getStrOfferHREF()!=""){
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(so.getStrOfferHREF()));
        	startActivity(i);
        }
    	//Decided I don't care about panes for this project
//        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(SpecialOfferDetailFragment.ARG_ITEM_ID, id);
//            SpecialOfferDetailFragment fragment = new SpecialOfferDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.specialoffer_detail_container, fragment)
//                    .commit();
//
//        } else {
//            // In single-pane mode, simply start the detail activity
//            // for the selected item ID.
//            Intent detailIntent = new Intent(this, SpecialOfferDetailActivity.class);
//            detailIntent.putExtra(SpecialOfferDetailFragment.ARG_ITEM_ID, id);
//            
//            startActivity(detailIntent);
//        }
    }
}

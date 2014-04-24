package com.cschryer.eaterofjsons;
//dead code
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cschryer.eaterofjsons.classes.OfferItem;
import com.cschryer.eaterofjsons.classes.SpecialOffer;

/**
 * A fragment representing a single Special Offer detail screen.
 * This fragment is either contained in a {@link SpecialOfferListActivity}
 * in two-pane mode (on tablets) or a {@link SpecialOfferDetailActivity}
 * on handsets.
 */
public class SpecialOfferDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private SpecialOffer so = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SpecialOfferDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            so.loadOffer(getActivity(), getArguments().getInt(ARG_ITEM_ID,0));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialoffer_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (so != null) {
            ((TextView) rootView.findViewById(R.id.specialoffer_detail)).setText(so.getStrOfferDescription());
        }

        return rootView;
    }
}

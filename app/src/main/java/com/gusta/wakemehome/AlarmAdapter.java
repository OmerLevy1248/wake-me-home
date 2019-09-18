package com.gusta.wakemehome;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gusta.wakemehome.database.AlarmEntry;
import com.gusta.wakemehome.utilities.WakeMeHomeUnitsUtils;

import java.util.List;

/**
 * {@link AlarmAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<AlarmEntry> mAlarmEntries;
    private Context mContext;

    /**
     * Constructor for the AlarmAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public AlarmAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                 can use this viewType integer to provide a different layout. See
     *                 {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                 for more details.
     * @return A new AlarmAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the alarm_list_item to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.alarm_list_item, parent, false);

        return new AlarmViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the alarm
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        // Determine the values of the wanted data
        AlarmEntry taskEntry = mAlarmEntries.get(position);
        String location = taskEntry.getLocation();
        String latitude = String.valueOf(taskEntry.getLatitude());
        String longitude = String.valueOf(taskEntry.getLongitude());
        String radius = WakeMeHomeUnitsUtils.formatLength(mContext, taskEntry.getRadius());
        String enabled = String.valueOf(taskEntry.isEnabled());
        String vibrate = String.valueOf(taskEntry.isVibrate());
        String message = taskEntry.getMessage();
        String alert = taskEntry.getAlert();

        //Set values
        holder.locationView.setText(location);
//        holder.latitudeView.setText(latitude);
//        holder.longitudeView.setText(longitude);
        holder.radiusView.setText(radius);
        holder.enabledView.setText(enabled);
//        holder.vibrateView.setText(vibrate);
//        holder.messageView.setText(message);
//        holder.alertView.setText(alert);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our alarm list
     */
    @Override
    public int getItemCount() {
        if (null == mAlarmEntries) return 0;
        return mAlarmEntries.size();
    }

    public List<AlarmEntry> getAlarms() {
        return mAlarmEntries;
    }

    /**
     * This method is used to set the alarm on an AlarmAdapter if we've already
     * created one. This is handy when we get new data but don't want to create a
     * new AlarmAdapter to display it.
     *
     * @param alarmEntries The new alarm data to be displayed.
     */
    public void setAlarms(List<AlarmEntry> alarmEntries) {
        mAlarmEntries = alarmEntries;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    /**
     * Inner class for creating ViewHolders
     */
    public class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables
        TextView locationView;
        TextView latitudeView;
        TextView longitudeView;
        TextView radiusView;
        TextView enabledView;
        TextView vibrateView;
        TextView messageView;
        TextView alertView;

        /**
         * Constructor for the AlarmViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public AlarmViewHolder(View itemView) {
            super(itemView);

            locationView = itemView.findViewById(R.id.location);
//            latitudeView = itemView.findViewById(R.id.latitude);
//            longitudeView = itemView.findViewById(R.id.longitude);
            radiusView = itemView.findViewById(R.id.radius);
            enabledView = itemView.findViewById(R.id.enabled);
//            vibrateView = itemView.findViewById(R.id.vibrate);
//            messageView = itemView.findViewById(R.id.message);
//            alertView = itemView.findViewById(R.id.alert);

            itemView.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param view The View that was clicked
         */
        @Override
        public void onClick(View view) {
            int elementId = mAlarmEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}

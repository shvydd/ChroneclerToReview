package nodomain.shvydkoy.chronicler.channelsScreen;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Locale;

import nodomain.shvydkoy.chronicler.R;
import nodomain.shvydkoy.chronicler.api.subcribtions.SubsChannel;



public final class ChannelRecyclerViewAdapter extends RecyclerView.Adapter<ChannelRecyclerViewAdapter.ViewHolder>
{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.US);

    private final LinkedList<SubsChannel> AllSubscriptions;


    ChannelRecyclerViewAdapter(LinkedList<SubsChannel> AllSubscriptions)
    {
        this.AllSubscriptions = AllSubscriptions;
    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder (final ViewHolder holder, int position)
    {
        Resources resources = holder.itemView.getContext().getResources();

        holder.channel = AllSubscriptions.get(position);

        holder.title.setText(holder.channel.getTitle());
        holder.updateDateTime.setText( dateFormat.format(holder.channel.getLastUpdateDate()) );


        if (holder.channel.isLastUpdateSuccessful())
        {
            holder.updateStatus.setText(resources.getString(R.string.last_update_successful));
            holder.updateStatus.setTextColor(resources.getColor(R.color.last_update_successful_color));
            holder.updateDateTime.setTextColor(resources.getColor(R.color.last_update_successful_color));
        }
        else
        {
            holder.updateStatus.setText(resources.getString(R.string.last_update_unsuccessful));
            holder.updateStatus.setTextColor(resources.getColor(R.color.last_update_unsuccessful_color));
            holder.updateDateTime.setTextColor(resources.getColor(R.color.last_update_unsuccessful_color));
        }

        if ( !holder.channel.isConfirmed() )
        {
            holder.unconfirmedTag.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.unconfirmedTag.setVisibility(View.GONE);
        }

        if (holder.channel.getUnreadItemsNumber()>0)
        {

            holder.unreadItemsNumber.setText(resources.getString(R.string.channel_item_unread_items_number_prefix, holder.channel.getUnreadItemsNumber()));
        }
        else
        {
            holder.unreadItemsNumber.setText("");
        }

        /*holder.updateChannelButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View parent)
            {
                holder.channel.makeConfirmed();
            }
        });*/

    }


    @Override
    public int getItemCount()
    {
        return AllSubscriptions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        final View parentView;

        SubsChannel channel;

        final TextView title;
        final TextView updateStatus;
        final TextView updateDateTime;
        final ImageView unconfirmedTag;
        final TextView unreadItemsNumber;
        final ImageButton updateChannelButton;



        ViewHolder(View view)
        {
            super(view);

            parentView = view;
            title = view.findViewById(R.id.channel_item_title);
            updateStatus = view.findViewById(R.id.channel_item_update_status);
            updateDateTime = view.findViewById(R.id.channel_item_last_update_date_and_time);
            unconfirmedTag = view.findViewById(R.id.channel_item_unconfirmed_channel_tag);
            unreadItemsNumber = view.findViewById(R.id.channel_item_unread_items_number);
            updateChannelButton = view.findViewById(R.id.channel_item_update_button);
        }




        @Override
        @NonNull
        public String toString()
        {
            //TODO
            return super.toString() + " '" + title.getText() + "'";
        }

    }





}

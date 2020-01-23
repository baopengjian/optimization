package com.bpj.optimization.optimization.lsn06;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bpj.optimization.optimization.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ray on 2020-1-22.
 */
public class ChatsFragment extends Fragment {


    public static String OPTIMIZATION = "optimization";

    protected static int MILLISECONDS_PER_SECOND = 1000;
    protected static int SECONDS_PER_MINUTE = 60;


    public ChatsFragment() {
    }

    public static ChatsFragment newInstance(boolean optimization) {
        Bundle args = new Bundle();
        ChatsFragment fragment = new ChatsFragment();
        args.putBoolean(OPTIMIZATION, optimization);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create a list of chats and populate it with hardcoded data.
        ArrayList<Chat> chats = new ArrayList<>();
        populateChats(chats);
        boolean optimization = false;
        if (getArguments() != null) {
            optimization = getArguments().getBoolean(OPTIMIZATION, false);
        }
        // Create the adapter that provides values to the UI Widgets.
        ChatAdapter adapter = new ChatAdapter(optimization, getActivity(), chats);
        View rootView;
        if (optimization) {
            rootView = inflater.inflate(R.layout.fragment_chats_op, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_chats, container, false);
        }

        // Find the ListView that holds all the chat messages and attach it to the adapter,
        ListView listView = (ListView) rootView.findViewById(R.id.listview_chats);

        listView.setAdapter(adapter);
        return rootView;

    }

    // Creates hardcoded chat objects.
    private void populateChats(ArrayList<Chat> chats) {
        Resources res = getResources();
        Droid alex = new Droid("alex", res.getColor(R.color.alex_color));
        Droid joanna = new Droid("joanna", res.getColor(R.color.joanna_color), R.drawable.joanna);
        Droid shailen = new Droid("shailen", res.getColor(R.color.shailen_color),
                R.drawable.shailen);

        chats.add(new Chat(alex, "Lorem ipsum dolor sit amet, orci nullam cra",
                getTimeInPast(15)));

        chats.add(new Chat(joanna, "Omnis aptent magnis suspendisse ipsum, semper egestas " +
                "magna auctor maecenas",
                getTimeInPast(11)));

        chats.add(new Chat(shailen, "eu nibh, rhoncus wisi posuere lacus, ad erat egestas " +
                "quam, magna ante ultricies sem",
                getTimeInPast(9)));

        chats.add(new Chat(alex, "rhoncus wisi posuere lacus, ad erat egestas quam, magna " +
                "ante ultricies sem lacus",
                getTimeInPast(8)));

        chats.add(new Chat(shailen, "Enim justo nisl sit proin, quis vestibulum vivamus " +
                "suscipit penatibus et id, tempus mauris a lacus blandit, aenean praesent " +
                "arcu scelerisque sociosqu. Nonummy at ut ullamcorper nulla, ligula id, " +
                "nullam donec nisl ante turpis duis mauris, dolor imperdiet a inceptos aliquam",
                getTimeInPast(8)));

        chats.add(new Chat(joanna, "Omnis aptent magnis.",
                getTimeInPast(7)));

        chats.add(new Chat(alex, "Metus tincidunt sit in urna.",
                getTimeInPast(6)));

        chats.add(new Chat(shailen, "Non blandit nulla dapibus, vitae quisque sed cras mi " +
                "leo condimentum sociosqu quis sed pharetra",
                getTimeInPast(4)));

        chats.add(new Chat(joanna, "Enim justo nisl sit proin, quis vestibulum vivamus " +
                "suscipit penatibus et id, tempus mauris a lacus blandit, aenean praesent " +
                "arcu scelerisque sociosqu. Nonummy at ut ullamcorper nulla, ligula id, " +
                "nullam donec nisl ante turpis duis mauris, dolor imperdiet a inceptos.",
                getTimeInPast(3)));
    }

    private Date getTimeInPast(int minutesAgo) {
        return new Date(new Date().getTime() -
                (minutesAgo * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND));
    }
}

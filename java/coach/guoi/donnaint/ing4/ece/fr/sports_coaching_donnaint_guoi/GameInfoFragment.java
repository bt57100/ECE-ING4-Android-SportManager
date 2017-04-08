package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Fragment to display match
 */
public class GameInfoFragment extends Fragment {
    // Interface view containing this fragment
    private Iview mainView;

    /* Match information */
    private int getMatchId = 0;
    private String team1 = "";
    private String team2 = "";
    private String date = "";
    private String type = "";
    private String score = "";

    /**
     * Empty constructor
     */
    public GameInfoFragment() {
    }

    /**
     * On create fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Get arguments received by creation */
        if (getArguments() != null) {
            getMatchId = getArguments().getInt(MyGlobalVars.TAG_ID);
            team1 = getArguments().getString(MyGlobalVars.TAG_TEAM1);
            team2 = getArguments().getString(MyGlobalVars.TAG_TEAM2);
            type = getArguments().getString(MyGlobalVars.TAG_TYPE);
            date = getArguments().getString(MyGlobalVars.TAG_DATE);
            score = getArguments().getString(MyGlobalVars.TAG_SCORE);
        }
        setRetainInstance(true);
    }

    /**
     * On create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_info, null);

        /* View components of the fragment */
        GridLayout gridGame = (GridLayout) view.findViewById(R.id.gridGame);
        TextView textTeam1 = (TextView) view.findViewById(R.id.textTeam1);
        TextView textTeam2 = (TextView) view.findViewById(R.id.textTeam2);
        TextView textDate = (TextView) view.findViewById(R.id.textDate);
        TextView textType = (TextView) view.findViewById(R.id.textType);
        TextView textScore = (TextView) view.findViewById(R.id.textScore);

        /* Set view content to match information */
        textTeam1.setText(team1);
        textTeam2.setText(team2);
        textDate.setText(date);
        textType.setText(type);
        textScore.setText(score);

        /* Move camera to Match on click */
        gridGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMarker();
            }
        });

        /* Remove fragment on long click */
        gridGame.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                removeFragment();
                return true;
            }
        });
        return view;
    }

    /**
     * Remove this fragment from view
     */
    public void removeFragment() {
        mainView.removeFragment(this);
    }

    /**
     * Remove this fragment from view
     */
    public void moveToMarker() {
        mainView.moveToMarker(this);
    }


    /**
     * Get match ID of the matc this fragment displays
     * @return
     */
    public int getMatchId() {
        return getMatchId;
    }

    /**
     * Set main view containing this fragment
     * @param mainView
     */
    public void setMainView(Iview mainView) {
        this.mainView = mainView;
    }

}

package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.PlusOneButton;

import coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.database.MatchDB;

/**
 * create an instance of this fragment.
 */
public class GameInfoFragment extends Fragment {

    private Iview mainView;

    private MatchDB matchDB;
    private FragmentTransaction fragmentTransaction;
    private int getMatchId = 0;
    private String team1 = "";
    private String team2 = "";
    private String date = "";
    private String type = "";
    private String score = "";

    public GameInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getMatchId = getArguments().getInt(MyGlobalVars.TAG_ID);
            team1 = getArguments().getString(MyGlobalVars.TAG_TEAM1);
            team2 = getArguments().getString(MyGlobalVars.TAG_TEAM2);
            type = getArguments().getString(MyGlobalVars.TAG_TYPE);
            date = getArguments().getString(MyGlobalVars.TAG_DATE);
            score = getArguments().getString(MyGlobalVars.TAG_SCORE);
            matchDB = (MatchDB) getArguments().get(MyGlobalVars.TAG_MATCH_DB);
            fragmentTransaction = (FragmentTransaction) getArguments().get(MyGlobalVars.TAG_FRAGMENT_TRANSACTION);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_info, null);
        GridLayout gridGame = (GridLayout) view.findViewById(R.id.gridGame);
        TextView textTeam1 = (TextView) view.findViewById(R.id.textTeam1);
        TextView textTeam2 = (TextView) view.findViewById(R.id.textTeam2);
        TextView textDate = (TextView) view.findViewById(R.id.textDate);
        TextView textType = (TextView) view.findViewById(R.id.textType);
        TextView textScore = (TextView) view.findViewById(R.id.textScore);
        textTeam1.setText(team1);
        textTeam2.setText(team2);
        textDate.setText(date);
        textType.setText(type);
        textScore.setText(score);
        gridGame.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                removeFragment();
                return true;
            }
        });
        return view;
    }

    public void removeFragment() {
        mainView.removeFragment(this);
    }

    public int getMatchId() {
        return getMatchId;
    }

    public void setMainView(Iview mainView) {
        this.mainView = mainView;
    }
}

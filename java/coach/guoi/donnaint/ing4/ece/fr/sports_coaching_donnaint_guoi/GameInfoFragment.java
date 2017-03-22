package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;

/**
 * create an instance of this fragment.
 */
public class GameInfoFragment extends Fragment {
    private TextView textPlayer1;
    private TextView textPlayer2;
    private TextView textTeam1;
    private TextView textTeam2;
    private TextView textScore;

    private String player1 = "";
    private String player2 = "";
    private String team1 = "";
    private String team2 = "";
    private String score = "";

    public GameInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player1 = getArguments().getString(MyGlobalVars.TAG_PLAYER1);
            player2 = getArguments().getString(MyGlobalVars.TAG_PLAYER2);
            team1 = getArguments().getString(MyGlobalVars.TAG_TEAM1);
            team2 = getArguments().getString(MyGlobalVars.TAG_TEAM2);
            score = getArguments().getString(MyGlobalVars.TAG_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_info, null);
        textPlayer1 = (TextView) view.findViewById(R.id.textPlayer1);
        textPlayer2 = (TextView) view.findViewById(R.id.textPlayer2);
        textTeam1 = (TextView) view.findViewById(R.id.textTeam1);
        textTeam2 = (TextView) view.findViewById(R.id.textTeam2);
        textScore = (TextView) view.findViewById(R.id.textScore);
        textPlayer1.setText(player1);
        textPlayer2.setText(player2);
        textTeam1.setText(team1);
        textTeam2.setText(team2);
        textScore.setText(score);

        return view;
    }

}

package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Kevin on 08/04/2017.
 */

public class ImageFragment extends Fragment {
    // Interface view containing this fragment
    private Iimage mainView;

    private Bitmap imageBitmap;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private EditText editText;
    /**
     * Empty constructor
     */
    public ImageFragment() {
    }

    /**
     * On create fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Get arguments received by creation */
        if (getArguments().getByteArray(MyGlobalVars.TAG_ID) != null) {
            byte[] byteArray = getArguments().getByteArray(MyGlobalVars.TAG_ID);
            imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
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
        View view = inflater.inflate(R.layout.fragment_image_view, null);

        /* View components of the fragment */
        linearLayout = (LinearLayout) view.findViewById(R.id.imageViewFragment);
        editText = (EditText) view.findViewById(R.id.editTextFrag);
        imageView = (ImageView) view.findViewById(R.id.imageViewFrag);
        if(imageBitmap != null)
            imageView.setImageBitmap(imageBitmap);

        /* Remove fragment on long click */
        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
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
     * Set main view containing this fragment
     * @param mainView
     */
    public void setMainView(Iimage mainView) {
        this.mainView = mainView;
    }

}

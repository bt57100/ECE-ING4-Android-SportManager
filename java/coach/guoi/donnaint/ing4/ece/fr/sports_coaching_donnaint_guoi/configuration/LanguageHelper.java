package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi.configuration;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;

import java.util.Locale;

/**
 * Created by Kevin on 05/04/2017.
 */

public class LanguageHelper extends ContextWrapper {

    public LanguageHelper(Context base) {
        super(base);
    }

    @SuppressWarnings("deprecation")
    public static ContextWrapper wrap(Context context, Locale newLocale) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        configuration.setLocale(newLocale);
        context = context.createConfigurationContext(configuration);
        return new ContextWrapper(context);
    }
}

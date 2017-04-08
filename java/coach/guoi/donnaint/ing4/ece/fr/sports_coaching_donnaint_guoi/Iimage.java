package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

/**
 * Iimage interface to enable link between fragment ImageFragment and any view
 * or activity implementing this interface
 * Created by Kevin on 08/04/2017.
 */

public interface Iimage {
    void removeFragment(ImageFragment fragment);

    void onSimpleClick(ImageFragment fragment);
}

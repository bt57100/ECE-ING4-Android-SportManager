package coach.guoi.donnaint.ing4.ece.fr.sports_coaching_donnaint_guoi;

/**
 * Created by Kevin on 20/03/2017.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connect to distant database and return string value of response
 */
public class ConnectToDB {
    // url to connect
    private String url = "";

    /**
     * Constructor with URL
     * @param url
     */
    public ConnectToDB(String url) {
        this.url = url;
    }

    /**
     * Connect to distant database and retrun response as String
     * @return
     */
    public String connectJSON() {
        String response="";
        HttpURLConnection connection = null;
        int status=0;
        try {
            // Replace special characters here ' ' to %20 for URL
            url = url.replaceAll(" ", "%20");
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            /* Connection initialization */
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setAllowUserInteraction(false);
            connection.setConnectTimeout(MyGlobalVars.TIMEOUT);
            connection.setReadTimeout(MyGlobalVars.TIMEOUT);
            /* Connect */
            connection.connect();
            /* Get and act depending on connection status */
            status = connection.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    InputStream responseStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(responseStream), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    response = sb.toString();
                    return response;
            }
        } catch (MalformedURLException ex) {
            /* Handle bad URL */
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            status=100;
            response=ex.getMessage();
        } catch (IOException ex) {
            /* Handle IOException */
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            status=101;
            response=ex.getMessage();
        } finally {
            if (connection != null) {
                try {
                    // Disconnect
                    connection.disconnect();
                } catch (Exception ex) {
                    /* Handle disconnection error */
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return String.valueOf(status)+response;
    }
}

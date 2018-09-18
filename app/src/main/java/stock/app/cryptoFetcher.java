package stock.app;

import android.os.AsyncTask;
import android.util.Log;

import org.patriques.AlphaVantageConnector;
import org.patriques.DigitalCurrencies;
import org.patriques.input.digitalcurrencies.Market;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.digitalcurrencies.IntraDay;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;

import java.util.List;
import java.util.Map;

public class cryptoFetcher extends AsyncTask {

    String TAG = "API-KUTSUJEN TESTAUSTA";
    String apiKey = "3475H17";
    String selectedCurrency = "";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    DigitalCurrencies digitalCurrencies = new DigitalCurrencies(apiConnector);

    public cryptoFetcher(String currency){
        this.selectedCurrency = currency;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            IntraDay response = digitalCurrencies.intraDay(selectedCurrency, Market.EUR);
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("Digital Currency Code: " + metaData.get("2. Digital Currency Code"));

            List<SimpelDigitalCurrencyData> digitalData = response.getDigitalData();
            SimpelDigitalCurrencyData latestEntry = digitalData.get(0);
            Log.d(TAG, "date:       " + latestEntry.getDateTime());
            Log.d(TAG, "price A:    " + latestEntry.getPriceA());
            Log.d(TAG, "price B:    " + latestEntry.getPriceB());
            Log.d(TAG, "volume:     " + latestEntry.getVolume());
            Log.d(TAG, "market cap: " + latestEntry.getMarketCap());
        } catch (AlphaVantageException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

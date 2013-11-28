import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class SentimentalAnalysis {

	public String excutePost(String targetURL, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void main(String args[])
	{
		SentimentalAnalysis builder = new SentimentalAnalysis();
		//String baseurl = "http://access.alchemyapi.com/calls/text/TextGetTextSentiment?apikey=44edfe996b09602d3ef589c510e5d460a9e3a99f&sentiment=1&outputMode=json&showSourceText=1&text=";
		String baseurl = "https://api.sentigem.com/external/get-sentiment?api-key=c7b7029934a1159ae3e2a9c3f80d3f99PiXQg6kuBN_0dp2Z8E5lrWTD4-oAqJbY&text=";
		List<String> reviewList = new LinkedList();
		reviewList.add("Mohan will be mastering in testing");
		reviewList.add("There's a lot many things we need to do to be a graduate");
		reviewList.add("It feels great in US");
		
		String param = "";
		for(String review : reviewList){
			String finalurl = baseurl+review;
			String response = builder.excutePost(finalurl,param);
			//System.out.println(i);
			System.out.println(response);
				
		}
				
	}
}
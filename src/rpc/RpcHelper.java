package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import entity.Item.ItemBuilder;

public class RpcHelper {
	// Writes a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException{
		response.setContentType("application/json");
		response.getWriter().print(array);
	}

	// Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {		
		response.setContentType("application/json");
		response.getWriter().print(obj);
	}

	public static JSONObject readJSONObject(HttpServletRequest request) {
		StringBuilder sBuilder = new StringBuilder();
		try (BufferedReader reader = request.getReader()) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
			return new JSONObject(sBuilder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	public static Item parseFavoriteItem(JSONObject favoriteItem) throws JSONException {
		ItemBuilder builder = new ItemBuilder();
		builder.setItemId(favoriteItem.getString("item_id"))
			  .setName(favoriteItem.getString("name"))
			  .setRating(favoriteItem.getDouble("rating"))
			  .setDistance(favoriteItem.getDouble("distance"))
			  .setImageUrl(favoriteItem.getString("image_url"))
			  .setUrl(favoriteItem.getString("url"))
			  .setAddress(favoriteItem.getString("address"));
		
		Set<String> categories = new HashSet<String>();
		if (!favoriteItem.isNull("categories")) {
			JSONArray array = favoriteItem.getJSONArray("categories");
			for (int i = 0; i < array.length(); i++) {
				categories.add(array.getString(i));
			}
 		}
		builder.setCategories(categories);
		return builder.build();
	}
	
	  // Converts a list of Item objects to JSONArray.
	  public static JSONArray getJSONArray(List<Item> items) {
	    JSONArray result = new JSONArray();
	    try {
	      for (Item item : items) {
	        result.put(item.toJSONObject());
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return result;
	  }


}

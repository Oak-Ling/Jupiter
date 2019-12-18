package rpc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import entity.Item;

public class RpcHelperTest {

	@Test
	public void testGetJSONArray() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
		Item one = new Item.ItemBuilder().setItemId("one").setDistance(33.33).setRating(5).setCategories(category).build();
		Item two = new Item.ItemBuilder().setItemId("two").setDistance(33.33).setRating(5).setCategories(category).build();
		List<Item> listItem = new ArrayList<Item>();
		listItem.add(one);
		listItem.add(two);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());
		
		JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);
	}
	
	@Test
	public void testGetJSONArrayCornerCases() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
		
		List<Item> listItem = new ArrayList<Item>();
		JSONArray jsonArray = new JSONArray();
		JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);

		Item one = new Item.ItemBuilder().setItemId("one").setDistance(33.33).setRating(5).setCategories(category).build();
		Item two = new Item.ItemBuilder().setItemId("two").setDistance(33.33).setRating(5).setCategories(category).build();
		listItem.add(one);
		listItem.add(two);
		
		jsonArray.put(one.toJSONObject());
		jsonArray.put(two.toJSONObject());	
		JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);
		
		Item empty = new Item.ItemBuilder().build();
		jsonArray.put(empty.toJSONObject());
		listItem.add(empty);
		JSONAssert.assertEquals(jsonArray, RpcHelper.getJSONArray(listItem), true);
	}


}

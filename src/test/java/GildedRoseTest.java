import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}

	@Test
	public void testNormalQuality()
	{
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("+5 Dexterity Vest", 10, 20));
		rose.updateQuality();

		Item expectedItem = new Item("+5 Dexterity Vest", 9, 19);
		assertItem("Normal Test", rose.getItems().get(0), expectedItem);

	}
	/**
	 *  Test the boundaries for Quality
	 */
	@Test
	public void testQualityLowerBoundary(){
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Dexterity Vest", 10, 0));
		rose.updateQuality();

		Item expectedItem = new Item("+5 Dexterity Vest", 9, 0);
		assertItem("Quality lower boundary", rose.getItems().get(0), expectedItem);
	}

	@Test
	public void testQualityUpperBoundary(){
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Aged Brie", 10, 50));
		rose.updateQuality();

		Item expectedItem = new Item("Aged Brie", 9, 50);
		assertItem("Quality Upper boundary", rose.getItems().get(0), expectedItem);
	}

	@Test
	public void testQualityUpperBoundary2(){
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 49));
		rose.updateQuality();

		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 8, 50);
		assertItem("Quality Upper boundary2", rose.getItems().get(0), expectedItem);
	}

	/**
	 * Test SellIn = 0 quality behavior
	 */
	@Test
	public void testSellInExpired(){
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("+5 Dexterity Vest", 0, 20));
		rose.updateQuality();

		Item expectedItem = new Item("+5 Dexterity Vest", -1, 18);
		assertItem("SellIn expired", rose.getItems().get(0), expectedItem);
	}


	/**
	 * Test "Aged Brie" quality behavior
	 */
	@Test
	public void testAgedBrie() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Aged Brie", 10, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Aged Brie", 9, 21);
		assertItem("Aged Brie", rose.getItems().get(0), expectedItem);
	}

	/**
	 * Test "Sulfuras" quality behavior
	 */
	@Test
	public void testSulfuras() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		rose.updateQuality();

		Item expectedItem = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
		assertItem("Sulfuras", rose.getItems().get(0), expectedItem);
	}

	/**
	 * Test "Backstage Passes" quality behavior
	 */

	@Test
	public void testBackstagePassesMoreThanTenDays() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 14, 21);
		assertItem("Backstage Passes more than 10 days", rose.getItems().get(0), expectedItem);
	}

	@Test
	public void testBackstagePassesMoreThanFiveLessThanTenDays() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 7, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 22);
		assertItem("Backstage Passes more than 5, less than 10 days", rose.getItems().get(0), expectedItem);
	}

	@Test
	public void testBackstagePassesLessThanFiveDays() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", 3, 23);
		assertItem("Backstage Passes less than 5 days", rose.getItems().get(0), expectedItem);
	}

	@Test
	public void testBackstagePassesLastDay() {
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 0);
		assertItem("Backstage Passes Last Day", rose.getItems().get(0), expectedItem);
	}

	/**
	 * Test "Conjured" items quality behavior
	 */
	@Test
	public void testConjured(){
		GildedRose rose = new GildedRose();
		rose.setItems(new ArrayList<Item>());
		rose.addItem(new Item("Conjured item", 4, 20));
		rose.updateQuality();

		Item expectedItem = new Item("Conjured item", 3, 18);
		assertItem("Conjured ", rose.getItems().get(0), expectedItem);
	}

	private void assertItem(String message, Item expected, Item actual)	{
		assertEquals(message+" : Not same SellIn", actual.getSellIn(), expected.getSellIn());
		assertEquals(message+" : Not same Quality", actual.getQuality(), expected.getQuality());
	}
}

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GildedRose {

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    private List<Item> items = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        GildedRose rose = new GildedRose();
        rose.items = new ArrayList<Item>();
        rose.addItem(new Item("+5 Dexterity Vest", 10, 20));
        rose.addItem(new Item("Aged Brie", 2, 0));
        rose.addItem(new Item("Elixir of the Mongoose", 5, 7));
        rose.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        rose.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        rose.addItem(new Item("Conjured Mana Cake", 3, 6));

        System.out.println("-------------------------- Initial items:");
        rose.printItems();

        rose.updateQuality();

        System.out.println("-------------------------- Updated items:");
        rose.printItems();
}

    private void printItems()
    {
        for(Item item : this.items)
        {
            System.out.println("Item name:\'" + item.getName() +
                    "\', SellIn: " + item.getSellIn() +
                    ", Quality: " + item.getQuality());
        }
    }
	
    public void updateQuality()
    {
        for (Item item:items) {
            if(isSulfuras(item.getName())) continue;
            if(isAgedBrie(item.getName())){
                updateAgedBrie(item);
                continue;
            }
            if(isBackstagePass(item.getName())){
                updateBackstagePass(item);
                continue;
            }
            if(isConjured(item.getName())){
                updateConjured(item);
                continue;
            }

            int quality = item.getQuality();
            int sellIn = item.getSellIn();

            if(sellIn<=0) quality -= 2;
            else quality -= 1;

            item.setQuality ( (quality < 0) ? 0 : quality);
            item.setSellIn(sellIn - 1);
        }
    }
    private void updateBackstagePass(Item item){
        int sellIn = item.getSellIn();
        int quality = item.getQuality();

        if(sellIn == 0 ) quality = 0;
        else {
            if (sellIn > 10) quality++;
            if (sellIn <= 10 && sellIn > 5) quality += 2;
            if (sellIn <= 5) quality += 3;
        }
        if(quality>50) quality = 50;

        item.setSellIn(sellIn - 1);
        item.setQuality(quality);
    }
    private void updateAgedBrie(Item item){
        item.setSellIn(item.getSellIn() - 1);
        if ( item.getQuality() < 50 ) {
            item.setQuality( item.getQuality() + 1 );
        }
    }
    private void updateConjured(Item item){
        int quality = item.getQuality();
        int sellIn = item.getSellIn();

        if(sellIn<=0) quality -= 4;
        else quality -= 2;

        item.setQuality ( (quality < 0) ? 0 : quality);
        item.setSellIn(sellIn - 1);
    }



    private static final String SULFURAS_PATTERN = "Sulfuras";
    private static final String AGED_BRIE_PATTERN = "Aged Brie";
    private static final String BACKSTAGE_PASS_PATTERN= "Backstage passes";
    private static final String CONJURED_PATTERN = "Conjured";

    private boolean isSulfuras(String name){
        return matchPattern(name, SULFURAS_PATTERN);
    }

    private boolean isAgedBrie(String name){
        return matchPattern(name, AGED_BRIE_PATTERN);
    }

    private boolean isBackstagePass(String name){
        return matchPattern(name, BACKSTAGE_PASS_PATTERN);
    }

    private boolean isConjured(String name){
        return matchPattern(name, CONJURED_PATTERN);
    }

    private boolean matchPattern(String name, String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(name);
        return m.find();
    }

}